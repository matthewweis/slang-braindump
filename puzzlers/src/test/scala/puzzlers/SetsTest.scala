package puzzlers

import org.sireum._
import puzzlers.Sets._

class SetsTest extends test.TestSuite {

  val tests = Tests {

    ///////////////////
    // sanity checks //
    ///////////////////
    * - assert_equals(set(v1), set(v1))
    * - assert_equals(set(v2), set(v2))
    * - assert_equals(set(v3), set(v3))
    * - assert_equals(set(v4), set(v4))
    * - assert_equals(set(v5), set(v5))
    * - assert_equals(set(v6), set(v6))
    * - assert_equals(set(v7), set(v7))
    * - assert_equals(set(w1), set(w1))
    * - assert_equals(set(w2), set(w2))
    * - assert_equals(set(w3), set(w3))
    * - assert_equals(set(w4), set(w4))
    * - assert_equals(set(w5), set(w5))
    * - assert_equals(set(w6), set(w6))
    * - assert_equals(set(w7), set(w7))
    * - assert_not_equals(set(v1), set(w1))
    * - assert_not_equals(set(v2), set(w2))
    * - assert_not_equals(set(v3), set(w3))
    * - assert_not_equals(set(v4), set(w4))
    * - assert_not_equals(set(v5), set(w5))
    * - assert_not_equals(set(v6), set(w6))
    * - assert_not_equals(set(v7), set(w7))

    /////////////////
    // apply tests //
    /////////////////
    * - assert_not_equals(set(v1), set(w1))
    * - assert_not_equals(set(v2), set(w2))
    * - assert_not_equals(set(v3), set(w3))
    * - assert_not_equals(set(v4), set(w4))
    * - assert_not_equals(set(v5), set(w5))
    * - assert_not_equals(set(v6), set(w6))
    * - assert_not_equals(set(v7), set(w7))

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