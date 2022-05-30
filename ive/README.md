# Useful IVE Preferences

## Inspections
* Open preferences
* Navigate to `Editor | Inspections`
* Disable `Scala | General | Comparing unrelated types`
* Disable `Scala | Syntactic simplification | Redundant return`
* Disable `Scala | Syntactic simplification | Anonymous function convertible to a method value`

## Code Completion
* Open preferences
* Navigate to `Editor | General | Code Completion`
* Find the `Machine-Learning Assisted Completion` section
* Uncheck `Sort completion suggestions based on machine learning` for Scala

## Imports

### Retain Sireum during Optimize Imports
* Open preferences
* Navigate to `Editor | Code Style | Scala`
* Select the `Imports` tab
* Locate the `Imports always marked as used` table
* Add the entry: `org.sireum._`

### Prioritize Sireum during Optimize Imports
* Open preferences
* Navigate to `Editor | Code Style | Scala | Imports`
* Select the `Imports` tab
* Locate the `Import Layout` table
* Modify to match:

```
org.sireum._
_______ blank line _______
base package imports
_______ blank line _______
all other imports
_______ blank line _______
java
javax
scala
```

### (Experimental!) Qualify non-Slang types (useful for extension functions)
* Open preferences
* Navigate to `Editor | Code Style | Scala`
* Select the `Imports` tab
* Locate the `Classes to only use with prefix` table
* Remove the entries:
```
exclude:scala.collection.mutable.ArrayBuffer
exclude:scala.collection.mutable.ListBuffer
```
* Add the entries:
```
exclude:scala.Unit
scala._
scala._._
scala._._._
scala._._._._
scala._._._._._
scala._._._._._._
scala._._._._._._._
scala._._._._._._._._
scala._._._._._._._._._
scala._._._._._._._._._._
```

Note: won't fix type aliases,
e.g. `scala.collection.immutable.StringOps` isn't caught but `scala.collection.StringOps` is.

## File Templates

### Slang File (File Template)

Adds a "Slang File" option to the New File dialog.

* Open preferences
* Navigate to `Editor | File and Code Templates`
* Select the `Files` tab
* Add the following entry:
  * **Name**="Slang File"
  * **Extension**="scala"
  * **File Name**=""
  * **Reformat according to style**=CHECKED
  * **Enable live templates**=UNCHECKED
  * **Content**=
```
// #[[#Sireum]]#

#if ((${PACKAGE_NAME} && ${PACKAGE_NAME} != ""))package ${PACKAGE_NAME} #end

import org.sireum._

#parse("File Header.java")
@datatype class ${NAME}() {

}
```

If desired, duplicate this template for more precise types (e.g. `@sig`, `@msig`, `@record`, `@object`).

### Slash Script (File Template)

Adds a "Slash Script" option to the New File dialog.

* Open preferences
* Navigate to `Editor | File and Code Templates`
* Select the `Files` tab
* Add the following entry:
    * **Name**="Slash Script"
    * **Extension**="cmd"
    * **File Name**=""
    * **Reformat according to style**=UNCHECKED
    * **Enable live templates**=UNCHECKED
    * **Content**=
```
::#! 2> /dev/null                                   #
@ 2>/dev/null # 2>nul & echo off & goto BOF         #
if [ -z \${SIREUM_HOME} ]; then                      #
  echo "Please set SIREUM_HOME env var"             #
  exit -1                                           #
fi                                                  #
exec \${SIREUM_HOME}/bin/sireum slang run "$0" "$@"  #
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
```

Note: Ensure CRLF encoding for .cmd files in .gitattributes. The template's line-trailing `#` symbols should be 
misaligned (due to escaped characters `\$` used throughout). The IVE's syntax highlighter will erroneously mark `$0` 
as a Velocity template variable, despite the template being correct.

## Scala Live Templates

### Uncheck Defaults
* Open preferences
* Navigate to `Editor | Live Templates`
* Expand the `Scala` subcategory
* Uncheck all default Scala live templates:
  * apply
  * imc
  * itr
  * itry
  * main
  * opt
  * priv
  * T
  * todo
  * unapply

### Add Slang Live Templates
* Open preferences
* Navigate to `Editor | Live Templates`
* Expand the `Scala` subcategory
* Add the following entries:

<br></br>
#### `@datatype`
* **Abbreviation**="@datatype"
* **Description**="Template for Slang datatype"
* **Applicable in**="Scala; Scala: Code; blank line."
* **Template Text**=
```
@datatype class $NAME$($ARGS$) {
    $END$
}
```
<br></br>
#### `@enum`
* **Abbreviation**="@enum"
* **Description**="Template for Slang enum"
* **Applicable in**="Scala; Scala: Code; blank line."
* **Template Text**=
```
@enum object $NAME$ {
  '$A$
  '$B$
}
```
<br></br>
#### `@foreach`
* **Abbreviation**="@foreach"
* **Description**="Template for Slang for-loop"
* **Applicable in**="Scala; Scala: Code; blank line."
* **Template Text**=
```
for ($NAME$ <- $FOR$ $GUARD$) {
  $END$
}
```
<br></br>
#### `@foreach-selection`
* **Abbreviation**="@foreach-selection"
* **Description**="Template for Slang for-loop"
* **Applicable in**="Scala; Scala: Code; blank line."
* **Template Text**=
```
for ($NAME$ <- $SELECTION$) {
  $END$
}
```
<br></br>
#### `@foreachyield`
* **Abbreviation**="@foreachyield"
* **Description**="Template for Slang for-yield-loop"
* **Applicable in**="Scala; Scala: Code; blank line."
* **Template Text**=
```
for ($NAME$ <- $FOR$ $GUARD$) yield {
  $END$
}
```
<br></br>
#### `@main`
* **Abbreviation**="@main"
* **Description**="Template for Slang main method"
* **Applicable in**="Scala; Scala: Code; blank line."
* **Template Text**=
```
def main(args: ISZ[String]): Z = {
  $END$
}
```
<br></br>
#### `@matchopt`
* **Abbreviation**="@matchopt"
* **Description**="Template for matching org.sireum.Option type"
* **Applicable in**="Scala; Scala: Code; blank line."
* **Template Text**=
```
$OPTION$ match {
  case Some($NAME$) => $SOME$
  case _ => $NONE$
}$END$
```
<br></br>
#### `@range`
* **Abbreviation**="@range"
* **Description**="Template for Slang range"
* **Applicable in**="Scala; Scala: Code; blank line."
* **Template Text**=
```
$LIT$"$START$" $RANGE$ $LIT$"$STOP$"
```
<br></br>
#### `@rangeby`
* **Abbreviation**="@rangeby"
* **Description**="Template for Slang range-by"
* **Applicable in**="Scala; Scala: Code; blank line."
* **Template Text**=
```
$LIT$"$START$" $RANGE$ $LIT$"$STOP$" by $LIT$"$INCR$"
```
<br></br>
#### `@sig`
* **Abbreviation**="@sig"
* **Description**="Template for Slang sig"
* **Applicable in**="Scala; Scala: Code; blank line."
* **Template Text**=
```
@sig trait $NAME$ {
    $END$
}
```