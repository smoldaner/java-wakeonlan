@echo off
rem Stolen from tomcat
set CMD_LINE_ARGS=
:setArgs
if ""%1""=="""" goto doneSetArgs
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto setArgs
:doneSetArgs

javaw -jar wakeonlan.jar %CMD_LINE_ARGS%
