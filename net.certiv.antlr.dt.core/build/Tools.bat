@rem ***************************************************************************
@rem Copyright (c) 2012, 2020 Certiv Analytics.
@rem
@rem This program and the accompanying materials
@rem are made available under the terms of the Eclipse Public License 2.0
@rem which accompanies this distribution, and is available at
@rem https://www.eclipse.org/legal/epl-2.0/
@rem
@rem SPDX-License-Identifier: EPL-2.0
@rem ***************************************************************************
@echo off
rem Execute the Antlr compiler/generator tool

SETLOCAL
set STARTTIME=%TIME%

rem lib jars directory:
set jlib=D:\DevFiles\Eclipse\Tools\Dsl\net.certiv.dsl\net.certiv.dsl.lib

rem grammars src directory:
set src=D:\DevFiles\Eclipse\Tools\Editors\net.certiv.antlr.dt\net.certiv.antlr.dt.core\src\main\java\net\certiv\antlr\dt\core\parser

rem generated class bin
set bin=D:\DevFiles\Eclipse\Tools\Editors\net.certiv.antlr.dt\net.certiv.antlr.dt.core\target\classes

set antlrjar=%jlib%\antlr4-4.12.0.jar
set arntmjar=%jlib%\antlr4-runtime-4.12.0.jar
set arnt3jar=%jlib%\antlr-runtime-3.5.3.jar
set sttmpjar=%jlib%\ST4-4.3.4.jar
set xvisitor=%jlib%\xvisitor-4.8.0.jar
set log4japi=%jlib%\log4j-api-2.20.0.jar
set log4jcor=%jlib%\log4j-core-2.20.0.jar


set CLASSPATH=%arnt3jar%;%arntmjar%;%sttmpjar%;%antlrjar%;%xvisitor%;%log4japi%;%log4jcor%;%bin%;%CLASSPATH%

echo %src%
cd %src%

java org.antlr.v4.Tool -visitor -o gen AntlrDT4Lexer.g4 AntlrDT4Parser.g4
echo Grammars generated

java net.certiv.antlr.xvisitor.Tool -v warn -o gen Structure.xv
echo XVisitors generated

set ENDTIME=%TIME%
set /A STARTTIME=(1%STARTTIME:~6,2%-100)*100 + (1%STARTTIME:~9,2%-100)
set /A ENDTIME=(1%ENDTIME:~6,2%-100)*100 + (1%ENDTIME:~9,2%-100)

if %ENDTIME% LSS %STARTTIME% (
	set /A DURATION=%STARTTIME%-%ENDTIME%
) else (
	set /A DURATION=%ENDTIME%-%STARTTIME%
)

set /A SECS=%DURATION% / 100
set /A REMAINDER=%DURATION% %% 100
echo %SECS%.%REMAINDER% s

ENDLOCAL

timeout 6
