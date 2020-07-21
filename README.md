### AntlrDT

Antlr v4 grammar editor and builder for Eclipse

* Syntax-assisted editing with syntax highlighting, smart editing actions, and folding
* Incremental grammar builder 
* Grammar formatter
* Graphical parse tree display with full token and parser/lexer error reporting
* Graphical rule path (connectivity) analysis

#### Use

The folders containing the 'g4' grammars and the generated files must be on the project classpath, i.e., a Java source folder. Otherwise AntlrDT has no reliable way of finding them.


##### Exemplary project layout:

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

    @header {
        package some.non.default.package.parser.gen;
    }

After opening the token view, be sure to verify the values in the 'Integration' dialog.

Also see, [Documentation](https://www.certiv.net/projects/antlrdt.html).

#### Install

Update site:

  * Name: Certiv Tools
  * Location: [www.certiv.net/updates](www.certiv.net/updates)

#### License

EPL v1.0