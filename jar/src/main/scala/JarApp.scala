// #Sireum
package jar

import org.sireum._
import org.sireum.ops.ISZOps

object JarApp extends App {
  def main(args: ISZ[String]): Z = {
    ISZOps(ISZ(z"1", z"2", z"3"))
      .mParMapCores((z: Z) => st"$z", z"2")
      .foreach((st: ST) => println(st.render))
    return z"0"
  }
}