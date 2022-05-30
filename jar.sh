#!/bin/sh

# Non-standard build artifacts for "jar" module.
# Builds are created for java8 and java11 targets. Each build includes subdirectories:
##   /compile (project class files + corresponding javap disassembly)
##   /assemble (uberjar class files + corresponding javap disassembly + uberjar assembly .bat and .jar and unzipped .class files with javap disassembly)
##   /run (project compile artifacts + invoke proyek dry run with VM options to capture runtime hotspot JIT assembly)
#
# Hotspot optimizations are non-deterministic (and may be worth multisampling with JMH blackhole/harness).
#
# Slang enables prototyping of application logic on the JVM. The JMM is rarely (if ever) isomorphic to the memory model
# of a platform targeted by Slang. Within the JVM, Slang's macros may spawn threads, introduce memory fences,
# or synchronize code blocks for any reason it sees fit. Disassembled macros and runtime assemblies are intended only
# debugging.

# check we are in project dir
if [ ! -d "../$PROJECT" ]; then
    echo "script must be run from top-level project folder ($PROJECT)"
    exit 1
fi

rm -rf .jar
mkdir -p .jar/jar8/compile
mkdir -p .jar/jar8/assemble
mkdir -p .jar/jar8/run
mkdir -p .jar/jar11/compile
mkdir -p .jar/jar11/assemble
mkdir -p .jar/jar11/run

shopt -s nullglob

# compile (project class files + corresponding javap disassembly)
echo 'compile... (with ignore symbol file)'
sireum proyek compile --javac "-source, 1.8, -target, 1.8, -encoding, utf8, -XDignore.symbol.file, -Xlint:-options" --scalac "-target:jvm-1.8, -deprecation, -Yrangepos, -Ydelambdafy:method, -feature, -unchecked, -Xfatal-warnings, -language:postfixOps" --out .jar/jar8/compile --recompile --no-sources --fresh . > .jar/jar8/compile.txt
sireum proyek compile --javac "-source, 11, -target, 11, -encoding, utf8, -XDignore.symbol.file, -Xlint:-options" --scalac "-target:jvm-11, -deprecation, -Yrangepos, -Ydelambdafy:method, -feature, -unchecked, -Xfatal-warnings, -language:postfixOps" --out .jar/jar11/compile --recompile --no-sources --fresh . > .jar/jar11/compile.txt
echo 'javap...'
(cd .jar/jar8/compile; iter=""; find slang-braindump -type f -name "*.class" -exec sh -c 'iter=$1 && echo "javap:\n    $iter\n    ${iter%.*}.javap" && $SIREUM_HOME/bin/mac/java/bin/javap -c -s -p -verbose -classpath jar $iter > ${iter%.*}.javap' {} {} \;)
(cd .jar/jar11/compile; iter=""; find slang-braindump -type f -name "*.class" -exec sh -c 'iter=$1 && echo "javap:\n    $iter\n    ${iter%.*}.javap" && $SIREUM_HOME/bin/mac/java/bin/javap -c -s -p -verbose -classpath jar $iter > ${iter%.*}.javap' {} {} \;)

# assemble (uberjar class files + corresponding javap disassembly + uberjar assembly .bat and .jar and unzipped .class files with javap disassembly)
echo 'assemble... (with ignore symbol file)'
sireum proyek assemble --javac "-source, 1.8, -target, 1.8, -encoding, utf8, -XDignore.symbol.file, -Xlint:-options" --scalac "-target:jvm-1.8, -deprecation, -Yrangepos, -Ydelambdafy:method, -feature, -unchecked, -Xfatal-warnings, -language:postfixOps" --out .jar/jar8/assemble --main jar.JarApp --uber --recompile --no-sources --fresh . > .jar/jar8/assemble.txt
sireum proyek assemble --javac "-source, 11, -target, 11, -encoding, utf8, -XDignore.symbol.file, -Xlint:-options" --scalac "-target:jvm-11, -deprecation, -Yrangepos, -Ydelambdafy:method, -feature, -unchecked, -Xfatal-warnings, -language:postfixOps" --out .jar/jar11/assemble --main jar.JarApp --uber --recompile --no-sources --fresh . > .jar/jar11/assemble.txt
echo 'unjar...'
(cd .jar/jar8/assemble/slang-braindump; mkdir -p .jar/jar8/unjar; cp jar.jar .jar/jar8/unjar; cd .jar/jar8/unjar; jar xvf jar.jar)
(cd .jar/jar11/assemble/slang-braindump; mkdir -p .jar/jar11/unjar; cp jar.jar .jar/jar8/unjar; cd .jar/jar8/unjar; jar xvf jar.jar)
echo 'javap...'
(cd .jar/jar8/assemble; iter=""; find slang-braindump -type f -name "*.class" -exec sh -c 'iter=$1 && echo "javap:\n    $iter\n    ${iter%.*}.javap" && $SIREUM_HOME/bin/mac/java/bin/javap -c -s -p -verbose -classpath jar $iter > ${iter%.*}.javap' {} {} \;)
(cd .jar/jar11/assemble; iter=""; find slang-braindump -type f -name "*.class" -exec sh -c 'iter=$1 && echo "javap:\n    $iter\n    ${iter%.*}.javap" && $SIREUM_HOME/bin/mac/java/bin/javap -c -s -p -verbose -classpath jar $iter > ${iter%.*}.javap' {} {} \;)

# run (project compile artifacts + invoke proyek dry run with VM options to capture runtime hotspot JIT assembly)
echo 'run... (with ignore symbol file)'
sireum proyek run --javac "-source, 1.8, -target, 1.8, -encoding, utf8, -XDignore.symbol.file, -Xlint:-options" --scalac "-target:jvm-1.8, -deprecation, -Yrangepos, -Ydelambdafy:method, -feature, -unchecked, -Xfatal-warnings, -language:postfixOps" --java "-XX:+UnlockDiagnosticVMOptions, -XX:+PrintAssembly, -XX:+PrintOptoAssembly, -XX:CompileThreshold=#" --recompile --no-sources --fresh --out .jar/jar8/run --dir . . jar.JarApp foo bar > .jar/jar8/run.txt
sireum proyek run --javac "-source, 11, -target, 11, -encoding, utf8, -XDignore.symbol.file, -Xlint:-options" --scalac "-target:jvm-11, -deprecation, -Yrangepos, -Ydelambdafy:method, -feature, -unchecked, -Xfatal-warnings, -language:postfixOps" --java "-XX:+UnlockDiagnosticVMOptions, -XX:+PrintAssembly, -XX:+PrintOptoAssembly, -XX:CompileThreshold=#" --recompile --no-sources --fresh --out .jar/jar11/run --dir . . jar.JarApp foo bar > .jar/jar11/run.txt
echo 'javap...'
(cd .jar/jar8/run; iter=""; find slang-braindump -type f -name "*.class" -exec sh -c 'iter=$1 && echo "javap:\n    $iter\n    ${iter%.*}.javap" && $SIREUM_HOME/bin/mac/java/bin/javap -c -s -p -verbose -classpath jar $iter > ${iter%.*}.javap' {} {} \;)
(cd .jar/jar11/run; iter=""; find slang-braindump -type f -name "*.class" -exec sh -c 'iter=$1 && echo "javap:\n    $iter\n    ${iter%.*}.javap" && $SIREUM_HOME/bin/mac/java/bin/javap -c -s -p -verbose -classpath jar $iter > ${iter%.*}.javap' {} {} \;)
