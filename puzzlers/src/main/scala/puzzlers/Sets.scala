// #Sireum

package puzzlers

import org.sireum._

// see SetsTest
object Sets {

  type ZS = ISZ[Z]
  type SZ = Set[Z]

  // no duplicates
  val v1: ZS = ISZ(z"1")
  val v2: ZS = ISZ(z"1", z"2")
  val v3: ZS = ISZ(z"1", z"2", z"3")
  val v4: ZS = ISZ(z"1", z"2", z"3", z"4")
  val v5: ZS = ISZ(z"1", z"2", z"3", z"4", z"5")
  val v6: ZS = ISZ(z"1", z"2", z"3", z"4", z"5", z"6")
  val v7: ZS = ISZ(z"1", z"2", z"3", z"4", z"5", z"6", z"7")

  // duplicates
  val w1: ZS = v1 ++ v1
  val w2: ZS = v2 ++ v2
  val w3: ZS = v3 ++ v3
  val w4: ZS = v4 ++ v4
  val w5: ZS = v5 ++ v5
  val w6: ZS = v6 ++ v6
  val w7: ZS = v7 ++ v7

  @pure def set(values: ZS): SZ = {
    return Set(values)
  }

  @pure def safe_set(values: ZS): SZ = {
    return Set.empty[Z] ++ values
  }

  @pure def assert_equals(expected: SZ, actual: SZ): Unit = {
    assert(expected == actual)
    assert(actual == expected)
  }

  @pure def assert_not_equals(expected: SZ, actual: SZ): Unit = {
    assert(expected != actual)
    assert(actual != expected)
  }
}