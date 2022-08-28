// #Sireum #Logika

package puzzlers.sets

import org.sireum._
import puzzlers.sets.Sets._

object SetsHarness {

  // originally meant to show that SMT solvers can sometimes prevent large swaths of fuzzing by solving for
  // inequalities. Since logika and testing suite are separate tools (static verification vs runtime tests) it may
  // not be wise to write both in tandem for combined coverage. (my original plan)

  /////////////////
  // apply tests //
  /////////////////

  // (goal is for logika to correctly identify erroneous inputs and throw an error -- works, but confusing premise)

  // expect error during logika analysis OR
  // expect assertion failure at runtime
  def apply_test_1: Set[Z] = {
    // switch commented line for failure case:
    set_by_impl(duplicate1)
    //Set(assert_set(duplicate1))
  }

  def apply_test_2: Set[Z] = {
    set_by_impl(duplicate2)
    //Set(assert_set(duplicate2))
  }

  def apply_test_3: Set[Z] = {
    set_by_impl(duplicate3)
    //Set(assert_set(duplicate3))
  }

  def apply_test_4: Set[Z] = {
    set_by_impl(duplicate4)
    //Set(assert_set(duplicate4))
  }

  def apply_test_5: Set[Z] = {
    set_by_impl(duplicate5)
    //Set(assert_set(duplicate5))
  }

  def apply_test_6: Set[Z] = {
    set_by_impl(duplicate6)
    //Set(assert_set(duplicate6))
  }

  def apply_test_7: Set[Z] = {
    set_by_impl(duplicate7)
    //Set(assert_set(duplicate7))
  }

  //////////////////
  // concat tests //
  //////////////////

//  val unique1b: ISZ[Z] = {
//    val r = ISZ(z"1")
//    assert(Set.Elements.unique(r))
//    r
//  }
  val unique1b: ISZ[Z] = ISZ(z"1")

  // expect equal at runtime
  @pure def concat_test_1_lhs: Set[Z] = {
//
////    val zs = ISZ(z"1")
////    Contract(
////      Requires(
////        forall_unique(zs),
////      ),
////      Ensures(
////        ∀(zs.indices)(i => Set.Elements.contain(Set.elementsOf(Res), zs(i))),
////        forall_unique(zs),
////        Res == Set(zs),
////        SeqUtil.IS.sizeEq(Set.elementsOf(Res), zs.size),
////      )
////    )
////    assert(forall_unique(zs))
////    val result = Set(zs)
////    assert(identical(Set.elementsOf(result), Set(zs).elements))
////    assert(SeqUtil.IS.sizeEq(Set.elementsOf(result), zs.size))
////    assert(identical(Set.elementsOf(result), zs))
////    assert(interchangeable(Set.elementsOf(result), zs))
//
//
//
//    //
//
//
//
////    assert(Set.Elements.unique(unique1b))
//    // must be first statement in block
//    Contract(
//      Requires(
//        SeqUtil.IS.unique(unique1),
//      ),
//      Ensures(
//        SeqUtil.IS.unique(unique1), // requirement
//        Res == Set(unique1), //
////        ∀(unique1.indices)(i => Set.Elements.contain(Set.elementsOf(Res), unique1(i))),
//        identical(Set.elementsOf(Res), Set.elementsOf(Set(unique1))),
//        interchangeable(Set.elementsOf(Res), unique1),
//      )
//    )
////    assert(forall_unique(unique1))
//    val result = Set(unique1)
////    assert(result == Set(unique1))
////    assert(identical(Set.elementsOf(result), Set(unique1).elements))
////    assert(SeqUtil.IS.sizeEq(Set.elementsOf(result), unique1.size))
////    assert(∀(unique1.indices)(i => ∃(Set.elementsOf(result))(e => unique1(i) == e)))
////    assert(identical(Set.elementsOf(result), unique1))
////    assert(interchangeable(Set.elementsOf(result), unique1))
//
//    assert(SeqUtil.IS.unique(unique1))
//    assert(result == Set(unique1))
//    assert(identical(Set.elementsOf(result), Set.elementsOf(Set(unique1))))
//    assert(interchangeable(Set.elementsOf(result), unique1))

//    Contract(Requires(forall_unique(unique1)))
    Contract(
      Requires(SeqUtil.IS.unique(unique1)),
      Ensures(
        Res == Set(unique1),
        identical(Set.elementsOf(Res), Set.elementsOf(Set(unique1))),
        interchangeable(Set.elementsOf(Res), unique1),
      )
    )
//    assert(forall_unique(unique1))
    val result = set_by_contract(unique1)
    return result
  }

  def concat_test_1_rhs: Set[Z] = {
    set_by_impl(duplicate1)
  }

  // expect equality (runtime)
  def concat_test_2_lhs: Set[Z] = {
    set_by_contract(unique2)
  }

  def concat_test_2_rhs: Set[Z] = {
    set_by_impl(duplicate2)
  }

  // expect equality (runtime)
  def concat_test_3_lhs: Set[Z] = {
    set_by_contract(unique3)
  }

  def concat_test_3_rhs: Set[Z] = {
    set_by_impl(duplicate3)
  }

  // expect equality (runtime)
  def concat_test_4_lhs: Set[Z] = {
    set_by_contract(unique4)
  }

  def concat_test_4_rhs: Set[Z] = {
    set_by_impl(duplicate4)
  }

  // expect equality (runtime)
  def concat_test_5_lhs: Set[Z] = {
    set_by_contract(unique5)
  }

  def concat_test_5_rhs: Set[Z] = {
    set_by_impl(duplicate5)
  }

  // expect equality (runtime)
  def concat_test_6_lhs: Set[Z] = {
    set_by_contract(unique6)
  }

  def concat_test_6_rhs: Set[Z] = {
    set_by_impl(duplicate6)
  }

  // expect equality (runtime)
  def concat_test_7_lhs: Set[Z] = {
    set_by_contract(unique7)
  }

  def concat_test_7_rhs: Set[Z] = {
    set_by_impl(duplicate7)
  }
}
