/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.format.TextEdit;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.load.ConfigMgr;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.tool.LangDescriptor;
import net.certiv.adept.tool.Level;
import net.certiv.adept.tool.Options;
import net.certiv.adept.tool.Options.OptionType;
import net.certiv.adept.tool.ToolBase;
import net.certiv.adept.util.Log;

public class Tool extends ToolBase {

	private static Options[] optionDefs = {

			// atomic commands

			new Options("check", "-C", OptionType.BOOL, "parse doc for suitability to format"),
			new Options("learn", "-L", OptionType.BOOL, "add doc to corpus training repo"),

			// command formatting qualifiers

			new Options("backup", "-b", OptionType.BOOL, "create doc backup before formatting"),
			new Options("save", "-s", OptionType.BOOL, "save formatted doc"),

			new Options("format", "-f", OptionType.BOOL, "format"),
			new Options("breakLongLines", "-fbreak", OptionType.BOOL, "break long lines"),
			new Options("alignFields", "-falign", OptionType.BOOL, "align fields"),
			new Options("alignComments", "-fcmtalign", OptionType.BOOL, "align comments"),
			new Options("formatComments", "-fcmt", OptionType.BOOL, "format comments"),
			new Options("formatHdrComment", "-fcmthdr", OptionType.BOOL, "format header comment"),
			new Options("rmvBlanksComment", "-fcmtrmvblanks", OptionType.BOOL, "remove blank lines in comments"),

			// command operators

			new Options("config", "-c", OptionType.STRING, "config settings file pathname"),
			new Options("corpusRoot", "-d", OptionType.STRING, "corpus root directory"),
			new Options("lang", "-g", OptionType.STRING, "grammar language ('antlr', 'java', 'xvisitor'"),
			new Options("rebuild", "-r", OptionType.BOOL, "force rebuild of the corpus model"),
			new Options("tabWidth", "-t", OptionType.INT, "tab width"),
			new Options("verbose", "-v", OptionType.STRING, "verbosity ('quiet', 'info', 'warn', 'error')"),

	};

	// fields set by option manager
	private Boolean backup;
	private Boolean check;

	private Boolean format;
	private Boolean breakLongLines;
	private Boolean alignFields;
	private Boolean alignComments;
	private Boolean formatComments;
	private Boolean formatHdrComment;
	private Boolean rmvBlanksComment;

	private Boolean learn;
	private Boolean rebuild;
	private Boolean save;

	private String corpusRoot;
	private String lang;
	private String config;
	private String verbose;
	private Integer tabWidth;

	// fields set by init/validate
	public static Settings settings;

	private CoreMgr mgr;

	private String version;
	private List<String> sourceFiles;
	private List<String> corpusFiles;
	private List<LangDescriptor> languages;

	public static void main(String[] args) {
		Tool tool = new Tool();
		if (args.length == 0) {
			tool.help();
			tool.exit(0);
		}
		boolean ok = tool.processFlags(args);
		ok = ok && tool.validateOptions();
		if (!ok) tool.exit(1);

		tool.execute(true);
		tool.exit(0);
	}

	/**
	 * Creates an embedded instance of the tool configured using command-line styled arguments. The
	 * options are validated and, if valid, the tool is executed.
	 *
	 * @param args command-line styled arguments
	 */
	public Tool(String[] args) {
		this();
		boolean ok = processFlags(args);
		ok = ok && validateOptions();
		if (ok) execute();
	}

	/**
	 * Creates an embedded instance of the tool, pending configuration. To use
	 * <ul>
	 * <li>call setters to configure
	 * <li>call loadResources()
	 * <li>call validateOptions()
	 * <li>call execute()
	 * <li>call getSources() to retrieve the results, if generated.
	 * </ul>
	 */
	public Tool() {
		super();
		mgr = new CoreMgr();
	}

	/**
	 * Configure the tool using command-line styled arguments.
	 *
	 * @param args command-line styled arguments
	 * @return true iff all of the command-line styled arguments are recognized
	 */
	public boolean processFlags(String[] args) {
		boolean ok = loadResources();
		ok = ok && Options.process(this, optionDefs, args);
		if (ok) sourceFiles = Options.getRemainder();
		return ok;
	}

	public boolean loadResources() {
		version = ConfigMgr.loadVersion();
		if (version == null) return false;

		languages = ConfigMgr.loadLanguages();
		if (languages == null) return false;

		return true;
	}

	public boolean validateOptions() {

		// ---- settings & config ----

		if (!chkSettings()) return false;

		// ---- lang & corpusRoot ----

		if (corpusRoot != null) {
			settings.corpusRoot = corpusRoot;
		}

		if (lang == null && settings.lang == null) {
			errMgr.toolError(ErrorType.INVALID_CMDLINE_ARG, "Must specify the language type");
			return false;
		} else if (lang != null) {
			settings.lang = lang;
		}

		boolean found = false;
		for (LangDescriptor language : languages) {
			if (language.name.equals(settings.lang)) {
				found = true;
				settings.corpusExt = language.ext;
				if (settings.corpusRoot == null) settings.corpusRoot = language.corpusRoot;
				settings.corpusTabWidth = language.tabWidth;
				break;
			}
		}

		if (!found) {
			errMgr.toolError(ErrorType.INVALID_CMDLINE_ARG, "Unrecognized language type: " + settings.lang);
			return false;
		}

		if (settings.corpusRoot == null) {
			errMgr.toolError(ErrorType.INVALID_CMDLINE_ARG, "Must specify a corpus root directory.");
			return false;
		}

		settings.corpusDir = Paths.get(settings.corpusRoot, settings.lang);
		File corpusFile = settings.corpusDir.toFile();
		if (!corpusFile.exists()) {
			corpusFile.mkdirs();
		}
		if (corpusFile.isFile()) {
			errMgr.toolError(ErrorType.INVALID_CMDLINE_ARG, "Model directory cannot be a file");
			return false;
		}

		// ---- backup ----

		if (backup == null && settings.backup == null) {
			settings.backup = true;
		} else if (backup != null) {
			settings.backup = backup;
		}

		// ---- check parser ----

		if (check == null && settings.check == null) {
			settings.check = false;
		} else if (check != null) {
			settings.check = check;
		}

		// ---- format ----

		if (format == null && settings.format == null) {
			settings.format = true;
		} else if (format != null) {
			settings.format = format;
		}
		if (breakLongLines == null && settings.breakLongLines == null) {
			settings.breakLongLines = true;
		} else if (breakLongLines != null) {
			settings.breakLongLines = breakLongLines;
		}
		if (alignFields == null && settings.alignFields == null) {
			settings.alignFields = true;
		} else if (alignFields != null) {
			settings.alignFields = alignFields;
		}
		if (formatComments == null && settings.formatComments == null) {
			settings.formatComments = true;
		} else if (formatComments != null) {
			settings.formatComments = formatComments;
		}
		if (alignComments == null && settings.alignComments == null) {
			settings.alignComments = true;
		} else if (alignComments != null) {
			settings.alignComments = alignComments;
		}
		if (formatHdrComment == null && settings.formatHdrComment == null) {
			settings.formatHdrComment = true;
		} else if (formatHdrComment != null) {
			settings.formatHdrComment = formatHdrComment;
		}
		if (rmvBlanksComment == null && settings.removeBlankLinesComment == null) {
			settings.removeBlankLinesComment = true;
		} else if (rmvBlanksComment != null) {
			settings.removeBlankLinesComment = rmvBlanksComment;
		}

		// ---- learn ----

		if (learn == null && settings.learn == null) {
			settings.learn = false;
		} else if (learn != null) {
			settings.learn = learn;
		}

		// ---- rebuild ----

		if (rebuild == null && settings.rebuild == null) {
			settings.rebuild = false;
		} else if (rebuild != null) {
			settings.rebuild = rebuild;
		}

		// ---- save ----

		if (save == null && settings.save == null) {
			settings.save = false;
		} else if (save != null) {
			settings.save = save;
		}

		// ---- tabWidth ----

		if (tabWidth == null && settings.tabWidth == null) {
			settings.tabWidth = 4;
		} else if (tabWidth != null) {
			settings.tabWidth = tabWidth;
		}
		if (settings.tabWidth < 1 || settings.tabWidth > 10) settings.tabWidth = 4;

		// ---- verbose ----

		if (verbose == null && settings.verbose == null) {
			settings.verbose = "warn";
		} else if (verbose != null) {
			settings.verbose = verbose;
		}

		try {
			getDefaultListener().setLevel(Level.valueOf(settings.verbose.trim().toUpperCase()));
		} catch (IllegalArgumentException e) {
			Log.error(this, ErrorType.INVALID_VERBOSE_LEVEL.msg + ": " + settings.verbose);
			errMgr.toolError(ErrorType.INVALID_VERBOSE_LEVEL, settings.verbose);
		}

		// ---- load and validate the corpus model ----

		boolean ok = mgr.loadCorpusModel(settings, corpusFiles);
		if (ok && mgr.getCorpusModel().isConsistent()) return true;

		Log.error(this, ErrorType.MODEL_VALIDATE_FAILURE.msg);
		errMgr.toolError(ErrorType.MODEL_VALIDATE_FAILURE, "Invalid corpus error.");
		return false;
	}

	private boolean chkSettings() {
		if (settings == null) {
			settings = ConfigMgr.loadSettings(config);
		}
		return settings != null;
	}

	/**
	 * Tool entry point processing one or more source documents. If the tool is set for learning, the
	 * source documents are added to the corpus document repository for subsequent inclusion into the
	 * corpus model. Otherwise, each source document is parsed to create a document model containing a
	 * set of features. Each such feature is matched against corpus model features to discern applicable
	 * formatting attributes. If formatting is enabled, the attributes are applied to produce a revised
	 * document that is written to disk.
	 */
	public void execute() {
		execute(false);
	}

	/**
	 * Execute and optionally wait (max 10 secs) for the corpus save thread to complete before exiting
	 * the application.
	 *
	 * @param wait for save to complete
	 */
	public void execute(boolean wait) {
		mgr.execute(settings, sourceFiles);

		if (wait) {
			for (int cnt = 0; mgr.getThreadGroup().activeCount() > 0 && cnt < 20; cnt++) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
			}
		}
	}

	public CoreMgr getMgr() {
		return mgr;
	}

	/** Retrieves the doc object produced through execution, or null */
	public Document getDocument() {
		return mgr.getDocModel().getDocument();
	}

	/**
	 * Returns the formatted source of the last document parsed. Can be equivalent to the original
	 * source if the formatting resulted in no changes.
	 */
	public String getFormatted() {
		Document doc = getDocument();
		if (doc.isModified()) return doc.getModified();
		return doc.getContent();
	}

	/**
	 * Returns a list of {@code ITextEdit} that can be used to apply the formatting changes created for
	 * the last document parsed.
	 */
	public List<TextEdit> getFormatEdits() {
		return mgr.getDocModel().getDocument().getEdits();
	}

	public Path getCorpusDir() {
		return settings.corpusDir;
	}

	/** Sets whether to create a backup file of a doc being saved */
	public void setBackup(boolean backup) {
		chkSettings();
		settings.backup = backup;
	}

	/** Sets whether to only perform a parse evaluation of the documents */
	public void setCheck(boolean check) {
		chkSettings();
		settings.check = check;
	}

	/** Sets the pathname of the directory containing the corpus root */
	public void setCorpusRoot(String corpusRoot) {
		chkSettings();
		settings.corpusRoot = corpusRoot;
	}

	/** Sets whether to apply the model to the doc to create a format modified document */
	public void setFormat(boolean format) {
		chkSettings();
		settings.format = format;
	}

	/** Sets the formatter to break long lines. */
	public void setFormatBreakLongLines(boolean breakLongLines) {
		this.breakLongLines = breakLongLines;
	}

	/** Sets the formatter to align fields */
	public void setFormatAlignFields(boolean alignFields) {
		this.alignFields = alignFields;
	}

	/** Sets the formatter to align consecutive line comments */
	public void setFormatAlignComments(boolean alignComments) {
		this.alignComments = alignComments;
	}

	/** Sets the formatter to format comments */
	public void setFormatComments(boolean comments) {
		this.formatComments = comments;
	}

	/** Sets the formatter to also format header comments */
	public void setFormatHdrComment(boolean header) {
		this.formatHdrComment = header;
	}

	/** Sets the formatter to remove blank lines in comments */
	public void setRemoveCommentBlankLines(boolean remove) {
		this.rmvBlanksComment = remove;
	}

	public void setLang(String lang) {
		chkSettings();
		settings.lang = lang;
	}

	/** Sets whether to add the current model to the training, and doc document to the corpus */
	public void setLearn(boolean learn) {
		chkSettings();
		settings.learn = learn;
	}

	public void setRebuild(boolean rebuild) {
		chkSettings();
		settings.rebuild = rebuild;
	}

	/** Sets whether to save a formated document */
	public void setSave(boolean save) {
		chkSettings();
		settings.save = save;
	}

	/** Sets the settings value object defining the formatted config settings. */
	public void setSettings(Settings obj) {
		settings = obj;
		config = null;
	}

	/** Sets the pathname of the file (**.json) containing the formatted config settings. */
	public void setSettingsLocation(String pathname) {
		config = pathname;
	}

	/** Provides the set of doc pathnames of the files to be processed */
	public void setSourceFiles(String... pathnames) {
		sourceFiles = Arrays.asList(pathnames);
	}

	/** Provides the set of doc pathnames of the files to be processed */
	public void setSourceFiles(List<String> pathnames) {
		sourceFiles = pathnames;
	}

	/** Provides the set of corpus filenames to be used. */
	public void setCorpusFiles(List<String> pathnames) {
		corpusFiles = pathnames;
	}

	public void setTabWidth(int tabWidth) {
		chkSettings();
		settings.tabWidth = tabWidth;
	}

	public void setVerbose(Level level) {
		chkSettings();
		settings.verbose = level.toString();
	}

	/** Sets the verbosity level of the tool */
	public void setVerbose(String verbose) {
		chkSettings();
		settings.verbose = verbose;
	}

	public void help() {
		version();
		Arrays.sort(optionDefs);
		for (Options o : optionDefs) {
			String name = o.name + (o.argType != OptionType.BOOL ? " ___" : "");
			String s = String.format(" %-19s %s", name, o.description);
			info(s);
		}
	}

	@Override
	public void version() {
		info("Adept Version " + version);
	}

	public void exit(int e) {
		System.exit(e);
	}
}
