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
import org.sireum.project.{Module, Project, Target}

val home = Os.slashDir.up.canon

val lib = Module(
  id = "lib",
  basePath = (home / "lib").string,
  subPathOpt = None(),
  deps = ISZ(),
  targets = ISZ(Target.Jvm),
  ivyDeps = ISZ("org.sireum.kekinian::library:"),
  sources = ISZ((Os.path("src") / "main" / "scala").string),
  resources = ISZ(),
  testSources = ISZ((Os.path("src") / "test" / "scala").string),
  testResources = ISZ(),
  publishInfoOpt = None()
)


val ive = Module(
  id = "ive",
  basePath = (home / "ive").string,
  subPathOpt = None(),
  deps = ISZ("lib"),
  targets = ISZ(Target.Jvm),
  ivyDeps = ISZ(),
  sources = ISZ((Os.path("src") / "main" / "scala").string),
  resources = ISZ(),
  testSources = ISZ((Os.path("src") / "test" / "scala").string),
  testResources = ISZ(),
  publishInfoOpt = None()
)

val cli = Module(
  id = "cli",
  basePath = (home / "cli").string,
  subPathOpt = None(),
  deps = ISZ("lib"),
  targets = ISZ(Target.Jvm),
  ivyDeps = ISZ(),
  sources = ISZ((Os.path("src") / "main" / "scala").string),
  resources = ISZ(),
  testSources = ISZ((Os.path("src") / "test" / "scala").string),
  testResources = ISZ(),
  publishInfoOpt = None()
)

val puzzlers = Module(
  id = "puzzlers",
  basePath = (home / "puzzlers").string,
  subPathOpt = None(),
  deps = ISZ("lib"),
  targets = ISZ(Target.Jvm),
  ivyDeps = ISZ(),
  sources = ISZ((Os.path("src") / "main" / "scala").string),
  resources = ISZ(),
  testSources = ISZ((Os.path("src") / "test" / "scala").string),
  testResources = ISZ(),
  publishInfoOpt = None()
)
println(project.JSON.fromProject(Project.empty+lib+ive+cli+puzzlers, T))
