::#! 2> /dev/null                                   #
@ 2>/dev/null # 2>nul & echo off & goto BOF         #
if [ -z ${SIREUM_HOME} ]; then                      #
  echo "Please set SIREUM_HOME env var"             #
  exit -1                                           #
fi                                                  #
exec ${SIREUM_HOME}/bin/sireum slang run "$0" "$@"  #
:BOF
setlocal
if not defined SIREUM_HOME (
  echo Please set SIREUM_HOME env var
  exit /B -1
)
%SIREUM_HOME%\bin\sireum.bat slang run "%0" %*
exit /B %errorlevel%
::!#
// #Sireum

import org.sireum._

/*
 * Runs a proc from project's home directory. Cmd file must be in BUILD subdirectory.
 */
def run(proc: ISZ[String]): Os.Proc.Result = {
  val home = Os.slashDir.up.canon
  assert((home / "BUILD" / "SHELLSHOCK").exists)
  val prefix: ISZ[String] = if (Os.kind == Os.Kind.Win) ISZ("cmd", "/c") else ISZ[String]()
  return Os.proc(prefix ++ proc).at(home).console.runCheck()
}

run(ISZ("sireum", "proyek", "compile", "."))