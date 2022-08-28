package puzzlers.ownership

import lib.Hacks
import org.sireum._
import puzzlers.ownership.OwnershipHarness._

/**
 *
 */
class OwnershipTest extends test.TestSuite {

  val tests = Tests {

    "sanity" - assert(T)

    "unreachable_test_no_ref" - {
      * - assert(unreachable_test_no_ref())
      * - assert(Hacks.unowned(scenario_no_ref()))
    }
    "unreachable_test_local_ref" - {
      * - assert(unreachable_test_local_ref())
    }
    "unreachable_test_field_ref" - {
      * - assert(unreachable_test_field_ref())
    }
    "unreachable_test_field_local_ref" - {
      * - assert(unreachable_test_field_local_ref())
    }
    "unreachable_test_no_ref_identity_specialized" - {
      * - assert(unreachable_test_no_ref_identity_specialized())
    }
    "unreachable_test_no_ref_identity_generic" - {
      * - assert(unreachable_test_no_ref_identity_generic())
    }
    "unreachable_test_no_ref_identity_upcast" - {
      * - assert(unreachable_test_no_ref_identity_upcast())
    }
    "reachable_test_no_ref" - {
      * - assert(reachable_test_no_ref())
    }
    "reachable_test_local_ref" - {
      * - assert(reachable_test_local_ref())
    }
    "reachable_test_field_ref" - {
      * - assert(reachable_test_field_ref())
    }
    "reachable_test_field_local_ref" - {
      * - assert(reachable_test_field_local_ref())
    }
    "reachable_test_no_ref_identity_specialized" - {
      * - assert(reachable_test_no_ref_identity_specialized())
    }
    "reachable_test_no_ref_identity_generic" - {
      * - assert(reachable_test_no_ref_identity_generic())
    }
    "reachable_test_no_ref_identity_upcast" - {
      * - assert(reachable_test_no_ref_identity_upcast())
    }
    "linked_test_reachable_no_ref" - {
      * - assert(linked_test_reachable_no_ref())
    }
    "linked_test_reachable_local_ref" - {
      * - assert(linked_test_reachable_local_ref())
    }
    "linked_test_reachable_field_ref" - {
      * - assert(linked_test_reachable_field_ref())
    }
    "linked_test_reachable_field_local_ref" - {
      * - assert(linked_test_reachable_field_local_ref())
    }
    "linked_test_reachable_no_ref_identity_specialized" - {
      * - assert(linked_test_reachable_no_ref_identity_specialized())
    }
    // temp
    def IN_HOUSE_linked_test_reachable_no_ref_identity_generic(): B = {
      var f = scenario_no_ref_identity_generic()
      var _f = f
      var __f = _f
      var ___f = __f
      f = scenario_no_ref_identity_generic()
      _f = scenario_no_ref_identity_generic()
      __f = _f
      return Hacks.owned(f) && Hacks.owned(_f) && Hacks.owned(__f) && Hacks.owned(___f)
    }
    "linked_test_reachable_no_ref_identity_generic" - {
      * - assert(linked_test_reachable_no_ref_identity_generic())
//      * - assert(IN_HOUSE_linked_test_reachable_no_ref_identity_generic())
//      * - assert(HACKS_IN_HOUSE_linked_test_reachable_no_ref_identity_generic())
//      * - assert(Hacks.test(scenario_no_ref_identity_generic))
    }
    "linked_test_reachable_no_ref_identity_upcast" - {
      * - assert(linked_test_reachable_no_ref_identity_upcast())
    }
    "mutable_objects_generic" - {
      * - assert(mutable_objects_generic())
    }
    "mutable_objects_strict" - {
      * - assert(mutable_objects_specialized())
    }
    "mutable_objects_hack" - {
      * - assert(mutable_objects_upcast())
    }
    "test_create_scenario_no_ref" - {
      * - assert(test_create_scenario_no_ref())
    }
    "test_create_scenario_local_ref" - {
      * - assert(test_create_scenario_local_ref())
    }
    "test_create_scenario_field_ref" - {
      * - assert(test_create_scenario_field_ref())
    }
    "test_create_scenario_field_local_ref" - {
      * - assert(test_create_scenario_field_local_ref())
    }
    "test_create_scenario_no_ref_identity_upcast" - {
      * - assert(test_create_scenario_no_ref_identity_upcast())
    }
    "test_create_scenario_no_ref_identity_specialized" - {
      * - assert(test_create_scenario_no_ref_identity_specialized())
    }
    "test_create_scenario_no_ref_identity_generic" - {
      * - assert(test_create_scenario_no_ref_identity_generic())
    }
    "ext_test_create_evilTwin_scenario_no_ref" - {
      * - assert(ext_test_create_evilTwin_scenario_no_ref())
    }
    "ext_test_create_evilTwin_scenario_local_ref" - {
      * - assert(ext_test_create_evilTwin_scenario_local_ref())
    }
    "ext_test_create_evilTwin_scenario_field_ref" - {
      * - assert(ext_test_create_evilTwin_scenario_field_ref())
    }
    "ext_test_create_evilTwin_scenario_field_local_ref" - {
      * - assert(ext_test_create_evilTwin_scenario_field_local_ref())
    }
    "ext_test_create_evilTwin_scenario_no_ref_identity_upcast" - {
      * - assert(ext_test_create_evilTwin_scenario_no_ref_identity_upcast())
    }
    "ext_test_create_evilTwin_scenario_no_ref_identity_specialized" - {
      * - assert(ext_test_create_evilTwin_scenario_no_ref_identity_specialized())
    }
    "ext_test_create_evilTwin_scenario_no_ref_identity_generic" - {
      * - assert(ext_test_create_evilTwin_scenario_no_ref_identity_generic())
    }
    "test_createNarrow_scenario_no_ref" - {
      * - assert(test_createNarrow_scenario_no_ref())
    }
    "test_createNarrow_scenario_local_ref" - {
      * - assert(test_createNarrow_scenario_local_ref())
    }
    "test_createNarrow_scenario_field_ref" - {
      * - assert(test_createNarrow_scenario_field_ref())
    }
    "test_createNarrow_scenario_field_local_ref" - {
      * - assert(test_createNarrow_scenario_field_local_ref())
    }
    "test_createNarrow_scenario_no_ref_identity_upcast" - {
      * - assert(test_createNarrow_scenario_no_ref_identity_upcast())
    }
    "test_createNarrow_scenario_no_ref_identity_specialized" - {
      * - assert(test_createNarrow_scenario_no_ref_identity_specialized())
    }
    "test_createNarrow_scenario_no_ref_identity_generic" - {
      * - assert(test_createNarrow_scenario_no_ref_identity_generic())
    }
    "test_createByName_scenario_no_ref" - {
      * - assert(test_createByName_scenario_no_ref())
    }
    "test_createByName_scenario_local_ref" - {
      * - assert(test_createByName_scenario_local_ref())
    }
    "test_createByName_scenario_field_ref" - {
      * - assert(test_createByName_scenario_field_ref())
    }
    "test_createByName_scenario_field_local_ref" - {
      * - assert(test_createByName_scenario_field_local_ref())
    }
    "test_createByName_scenario_no_ref_identity_upcast" - {
      * - assert(test_createByName_scenario_no_ref_identity_upcast())
    }
    "test_createByName_scenario_no_ref_identity_specialized" - {
      * - assert(test_createByName_scenario_no_ref_identity_specialized())
    }
    "test_createByName_scenario_no_ref_identity_generic" - {
      * - assert(test_createByName_scenario_no_ref_identity_generic())
    }
    "ext_test_createByName_evilTwin_scenario_no_ref" - {
      * - assert(ext_test_createByName_evilTwin_scenario_no_ref())
    }
    "ext_test_createByName_evilTwin_scenario_local_ref" - {
      * - assert(ext_test_createByName_evilTwin_scenario_local_ref())
    }
    "ext_test_createByName_evilTwin_scenario_field_ref" - {
      * - assert(ext_test_createByName_evilTwin_scenario_field_ref())
    }
    "ext_test_createByName_evilTwin_scenario_field_local_ref" - {
      * - assert(ext_test_createByName_evilTwin_scenario_field_local_ref())
    }
    "ext_test_createByName_evilTwin_scenario_no_ref_identity_upcast" - {
      * - assert(ext_test_createByName_evilTwin_scenario_no_ref_identity_upcast())
    }
    "ext_test_createByName_evilTwin_scenario_no_ref_identity_specialized" - {
      * - assert(ext_test_createByName_evilTwin_scenario_no_ref_identity_specialized())
    }
    "ext_test_createByName_evilTwin_scenario_no_ref_identity_generic" - {
      * - assert(ext_test_createByName_evilTwin_scenario_no_ref_identity_generic())
    }
    "teach" - {
      * - {
        assert(T)
        understand_copying()
        understand_identity_1()
        understand_identity_2()
        understand_identity_3()
      }
    }
  }

}