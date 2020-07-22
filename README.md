# AntlrDT

Antlr v4 grammar editor and builder for Eclipse

## Features

* Syntax-assisted editing with syntax highlighting, smart editing actions, and folding
* Incremental grammar builder 
* Grammar formatter
* Graphical parse tree display with full token and parser/lexer error reporting
* Graphical rule path (connectivity) analysis


## Screenshot

<figure>
<figcaption>Parse Tree Graph</figcaption>
<img src="./doc/Expr.g4.png">
</figure>

## Use

The folders containing the 'g4' grammars and the generated files must be on the project classpath, i.e., 
a Java source folder. Otherwise, AntlrDT has no reliable way of finding them.

### Keys

|Key         |Function                          |
|:-----------|:---------------------------------|
|Ctrl-Space  |Open quick assist popup           |
|Ctrl-/      |Toggle commenting of selected text|
|Ctrl-Shift-f|Format full page or selected text |


### Graphs

From the Antlr tools section of the platform toolbar ![Antlr Tools](./doc/AntlrTools.png):

- Tokens View: select the ![tokens view](./doc/grammarAst.png) icon.
- Paths View: select the ![paths view](./doc/hierarchy_co.png) icon.

### Exemplary project layout:

~~~
[workspace root]
  [project]
     /src/main/java
       /your/package/path
         /parser
            YourParser.g4
            YourLexer.g4
            YourTokenFactory.java
            YourToken.java
            /gen
               [[generated files produced here]]
     /test.snippets
       /basic
           [[source snippets]]
       /complex 
           [[source snippets]]
~~~

Add the appropriate `@header` to the lexer and parser grammars:

    @header { package your.package.path.parser.gen; }

After opening the token view, be sure to verify the values in the 'Integration' dialog.

Also see, [Documentation](https://www.certiv.net/projects/antlrdt.html).

#### Install

Update site:

  * Name: Certiv Tools
  * Location: [www.certiv.net/updates](www.certiv.net/updates)

#### License

EPL v2.0