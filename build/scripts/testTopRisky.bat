@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  testTopRisky startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and TEST_TOP_RISKY_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\testTopRisky-1.0-SNAPSHOT.jar;%APP_HOME%\lib\log4j-to-slf4j-2.14.0.jar;%APP_HOME%\lib\slf4j-api-2.0.0-alpha1.jar;%APP_HOME%\lib\jackson-annotations-2.12.0-rc2.jar;%APP_HOME%\lib\elasticsearch-rest-high-level-client-7.10.0.jar;%APP_HOME%\lib\elasticsearch-7.10.0.jar;%APP_HOME%\lib\elasticsearch-x-content-7.10.0.jar;%APP_HOME%\lib\jackson-core-2.12.0-rc2.jar;%APP_HOME%\lib\jackson-databind-2.12.0-rc2.jar;%APP_HOME%\lib\commons-lang3-3.11.jar;%APP_HOME%\lib\log4j-api-2.14.0.jar;%APP_HOME%\lib\elasticsearch-cli-7.10.0.jar;%APP_HOME%\lib\elasticsearch-core-7.10.0.jar;%APP_HOME%\lib\elasticsearch-secure-sm-7.10.0.jar;%APP_HOME%\lib\elasticsearch-geo-7.10.0.jar;%APP_HOME%\lib\lucene-core-8.7.0.jar;%APP_HOME%\lib\lucene-analyzers-common-8.7.0.jar;%APP_HOME%\lib\lucene-backward-codecs-8.7.0.jar;%APP_HOME%\lib\lucene-grouping-8.7.0.jar;%APP_HOME%\lib\lucene-highlighter-8.7.0.jar;%APP_HOME%\lib\lucene-join-8.7.0.jar;%APP_HOME%\lib\lucene-memory-8.7.0.jar;%APP_HOME%\lib\lucene-misc-8.7.0.jar;%APP_HOME%\lib\lucene-queries-8.7.0.jar;%APP_HOME%\lib\lucene-queryparser-8.7.0.jar;%APP_HOME%\lib\lucene-sandbox-8.7.0.jar;%APP_HOME%\lib\lucene-spatial-extras-8.7.0.jar;%APP_HOME%\lib\lucene-spatial3d-8.7.0.jar;%APP_HOME%\lib\lucene-suggest-8.7.0.jar;%APP_HOME%\lib\hppc-0.8.1.jar;%APP_HOME%\lib\joda-time-2.10.4.jar;%APP_HOME%\lib\t-digest-3.2.jar;%APP_HOME%\lib\HdrHistogram-2.1.9.jar;%APP_HOME%\lib\jna-5.5.0.jar;%APP_HOME%\lib\elasticsearch-rest-client-7.10.0.jar;%APP_HOME%\lib\mapper-extras-client-7.10.0.jar;%APP_HOME%\lib\parent-join-client-7.10.0.jar;%APP_HOME%\lib\aggs-matrix-stats-client-7.10.0.jar;%APP_HOME%\lib\rank-eval-client-7.10.0.jar;%APP_HOME%\lib\lang-mustache-client-7.10.0.jar;%APP_HOME%\lib\jackson-dataformat-cbor-2.12.0-rc2.jar;%APP_HOME%\lib\jackson-dataformat-smile-2.12.0-rc2.jar;%APP_HOME%\lib\jackson-dataformat-yaml-2.12.0-rc2.jar;%APP_HOME%\lib\snakeyaml-1.26.jar;%APP_HOME%\lib\jopt-simple-5.0.2.jar;%APP_HOME%\lib\httpclient-4.5.10.jar;%APP_HOME%\lib\httpcore-4.4.12.jar;%APP_HOME%\lib\httpasyncclient-4.1.4.jar;%APP_HOME%\lib\httpcore-nio-4.4.12.jar;%APP_HOME%\lib\commons-codec-1.11.jar;%APP_HOME%\lib\commons-logging-1.1.3.jar;%APP_HOME%\lib\compiler-0.9.6.jar


@rem Execute testTopRisky
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %TEST_TOP_RISKY_OPTS%  -classpath "%CLASSPATH%" com.example %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable TEST_TOP_RISKY_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%TEST_TOP_RISKY_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
