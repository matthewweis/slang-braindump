package puzzlers

import org.sireum._
import puzzlers.Sets._

class SetsTest extends test.TestSuite {

  val tests = Tests {

    /////////////////
    // apply tests //
    /////////////////
    * - assertThrows[AssertionError](set(w1))
    * - assertThrows[AssertionError](set(w2))
    * - assertThrows[AssertionError](set(w3))
    * - assertThrows[AssertionError](set(w4))
    * - assertThrows[AssertionError](set(w5))
    * - assertThrows[AssertionError](set(w6))
    * - assertThrows[AssertionError](set(w7))

    //////////////////
    // concat tests //
    //////////////////
    * - assert_equals(set(v1), safe_set(w1))
    * - assert_equals(set(v2), safe_set(w2))
    * - assert_equals(set(v3), safe_set(w3))
    * - assert_equals(set(v4), safe_set(w4))
    * - assert_equals(set(v5), safe_set(w5))
    * - assert_equals(set(v6), safe_set(w6))
    * - assert_equals(set(v7), safe_set(w7))
  }
}