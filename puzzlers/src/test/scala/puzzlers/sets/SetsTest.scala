package puzzlers.sets

import org.sireum._
import puzzlers.sets.Sets._
import puzzlers.sets.SetsHarness._

class SetsTest extends test.TestSuite {

  val tests = Tests {

    /////////////////
    // apply tests //
    /////////////////
    * - assert_not_equals(concat_test_1_lhs, apply_test_1) // 1a
    * - assert_not_equals(concat_test_2_lhs, apply_test_2) // 2a
    * - assert_not_equals(concat_test_3_lhs, apply_test_3) // 3a
    * - assert_not_equals(concat_test_4_lhs, apply_test_4) // 4a
    * - assert_not_equals(concat_test_5_lhs, apply_test_5) // 5a
    * - assert_not_equals(concat_test_6_lhs, apply_test_6) // 6a
    * - assert_not_equals(concat_test_7_lhs, apply_test_7) // 7a

    * - assert_not_equals(apply_test_1, concat_test_1_rhs) // 1b
    * - assert_not_equals(apply_test_2, concat_test_2_rhs) // 2b
    * - assert_not_equals(apply_test_3, concat_test_3_rhs) // 3b
    * - assert_not_equals(apply_test_4, concat_test_4_rhs) // 4b
    * - assert_not_equals(apply_test_5, concat_test_5_rhs) // 5b
    * - assert_not_equals(apply_test_6, concat_test_6_rhs) // 6b
    * - assert_not_equals(apply_test_7, concat_test_7_rhs) // 7b

    //////////////////
    // concat tests //
    //////////////////
    * - assert_equals(concat_test_1_lhs, concat_test_1_rhs)
    * - assert_equals(concat_test_2_lhs, concat_test_2_rhs)
    * - assert_equals(concat_test_3_lhs, concat_test_3_rhs)
    * - assert_equals(concat_test_4_lhs, concat_test_4_rhs)
    * - assert_equals(concat_test_5_lhs, concat_test_5_rhs)
    * - assert_equals(concat_test_6_lhs, concat_test_6_rhs)
    * - assert_equals(concat_test_7_lhs, concat_test_7_rhs)
  }
}
