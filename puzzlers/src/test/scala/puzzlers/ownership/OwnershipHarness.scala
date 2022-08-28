// #Sireum

package puzzlers.ownership

import org.sireum._
import org.sireum.{App, B, F, ISZ, MBox, String, Z, pure}
import lib.Hacks

/*
 *
 */
object OwnershipHarness {

  //
  // Examples for teaching
  //

  /*
   *
   */
  def understand_copying(): Unit = {
    val a = MBox(z"0")
    val _a = a
    val __a = _a
    val ___a = __a
    a.value = z"1"
    _a.value = z"2"
    __a.value = z"3"
    ___a.value = z"4"
    println(a)
    println(_a)
    println(__a)
    println(___a)
  }

  @strictpure def identity_mboxz(o: MBox[Z]): MBox[Z] = o

  def understand_identity_1(): Unit = {
    val a = MBox(z"0")
    val _a = identity_mboxz(a)
    val __a = identity_mboxz(_a)
    val ___a = identity_mboxz(__a)
    a.value = z"1"
    _a.value = z"2"
    __a.value = z"3"
    ___a.value = z"4"
    println("IDENTITY1")
    println(st"${Hacks.identityHashCode(a)} --> ${a}".render)
    println(st"${Hacks.identityHashCode(_a)} --> ${_a}".render)
    println(st"${Hacks.identityHashCode(__a)} --> ${__a}".render)
    println(st"${Hacks.identityHashCode(___a)} --> ${___a}".render)
  }

  def understand_identity_2(): Unit = {
    val a = MBox(z"0")
    a.value = z"1"
    val _a = identity_mboxz(a)
    _a.value = z"2"
    val __a = identity_mboxz(_a)
    __a.value = z"3"
    val ___a = identity_mboxz(__a)
    ___a.value = z"4"
    println("IDENTITY2")
    println(st"${Hacks.identityHashCode(a)} --> ${a}".render)
    println(st"${Hacks.identityHashCode(_a)} --> ${_a}".render)
    println(st"${Hacks.identityHashCode(__a)} --> ${__a}".render)
    println(st"${Hacks.identityHashCode(___a)} --> ${___a}".render)
  }

  def understand_identity_3(): Unit = {
    val a = MBox(z"0")
    a.value = a.value.increase
    val _a = identity_mboxz(a)
    _a.value = _a.value.increase
    val __a = identity_mboxz(_a)
    __a.value = __a.value.increase
    val ___a = identity_mboxz(__a)
    ___a.value = ___a.value.increase
    println("IDENTITY3")
    println(st"${Hacks.identityHashCode(a)} --> ${a}".render)
    println(st"${Hacks.identityHashCode(_a)} --> ${_a}".render)
    println(st"${Hacks.identityHashCode(__a)} --> ${__a}".render)
    println(st"${Hacks.identityHashCode(___a)} --> ${___a}".render)
  }

  //
  // Low quality (partially generated) tests for potential special cases
  //

  def unreachable_test_no_ref(): B = {
    return Hacks.unowned(scenario_no_ref())
  }

  def unreachable_test_local_ref(): B = {
    return Hacks.unowned(scenario_local_ref())
  }

  def unreachable_test_field_ref(): B = {
    return Hacks.unowned(scenario_field_ref())
  }

  def unreachable_test_field_local_ref(): B = {
    return Hacks.unowned(scenario_field_local_ref())
  }

  def unreachable_test_no_ref_identity_specialized(): B = {
    return Hacks.unowned(scenario_no_ref_identity_specialized())
  }

  def unreachable_test_no_ref_identity_generic(): B = {
    return Hacks.unowned(scenario_no_ref_identity_generic())
  }

  def unreachable_test_no_ref_identity_upcast(): B = {
    return Hacks.unowned(scenario_no_ref_identity_upcast())
  }

  def reachable_test_no_ref(): B = {
    val a = scenario_no_ref();
    return Hacks.owned(a)
  }

  def reachable_test_local_ref(): B = {
    val b = scenario_local_ref();
    return Hacks.owned(b)
  }

  def reachable_test_field_ref(): B = {
    val c = scenario_field_ref();
    return Hacks.owned(c)
  }

  def reachable_test_field_local_ref(): B = {
    val d = scenario_field_local_ref();
    return Hacks.owned(d)
  }

  def reachable_test_no_ref_identity_specialized(): B = {
    val e = scenario_no_ref_identity_specialized();
    return Hacks.owned(e)
  }

  def reachable_test_no_ref_identity_generic(): B = {
    val f = scenario_no_ref_identity_generic();
    return Hacks.owned(f)
  }

  def reachable_test_no_ref_identity_upcast(): B = {
    val g = scenario_no_ref_identity_upcast();
    return Hacks.owned(g)
  }


  def mutable_objects_generic(): B = {
    val upcast = identity_upcast(scenario_no_ref_identity_generic())
    val generic = identity_generic(scenario_no_ref_identity_generic())
    val identity = Hacks.identity(scenario_no_ref_identity_generic())

    val upcast_ownership = Hacks.owned(upcast)
    val generic_ownership = Hacks.owned(generic)
    val identity_ownership = Hacks.owned(identity)

    println("mutable_objects_generic")
    println(upcast_ownership)
    println(generic_ownership)
    println(identity_ownership)
    return upcast_ownership && generic_ownership && identity_ownership
  }

  def mutable_objects_specialized(): B = {
    val upcast = identity_upcast(scenario_no_ref_identity_specialized())
    val generic = identity_generic(scenario_no_ref_identity_specialized())
    val identity = Hacks.identity(scenario_no_ref_identity_specialized())

    val upcast_ownership = Hacks.owned(upcast)
    val generic_ownership = Hacks.owned(generic)
    val identity_ownership = Hacks.owned(identity)

    println("mutable_objects_specialized")
    println(upcast_ownership)
    println(generic_ownership)
    println(identity_ownership)
    return upcast_ownership && generic_ownership && identity_ownership
  }

  def mutable_objects_upcast(): B = {
    val upcast = identity_upcast(scenario_no_ref_identity_upcast())
    val generic = identity_generic(scenario_no_ref_identity_upcast())
    val identity = Hacks.identity(scenario_no_ref_identity_upcast())

    val upcast_ownership = Hacks.owned(upcast)
    val generic_ownership = Hacks.owned(generic)
    val identity_ownership = Hacks.owned(identity)

    return upcast_ownership && generic_ownership && identity_ownership
  }

  def test_create_scenario_no_ref(): B = {
    return (Hacks.owned(OwnershipActuals.create(() => scenario_no_ref())) == Hacks.owned(OwnershipExts.create(() => scenario_no_ref()))) &&
       (Hacks.unowned(OwnershipActuals.create(() => scenario_no_ref())) == Hacks.unowned(OwnershipExts.create(() => scenario_no_ref())))
  }
  def test_create_scenario_local_ref(): B = {
     return (Hacks.owned(OwnershipActuals.create(() => scenario_local_ref())) == Hacks.owned(OwnershipExts.create(() => scenario_local_ref()))) &&
(Hacks.unowned(OwnershipActuals.create(() => scenario_local_ref())) == Hacks.unowned(OwnershipExts.create(() => scenario_local_ref())))
  }
  def test_create_scenario_field_ref(): B = {
     return (Hacks.owned(OwnershipActuals.create(() => scenario_field_ref())) == Hacks.owned(OwnershipExts.create(() => scenario_field_ref()))) &&
(Hacks.unowned(OwnershipActuals.create(() => scenario_field_ref())) == Hacks.unowned(OwnershipExts.create(() => scenario_field_ref())))
  }
  def test_create_scenario_field_local_ref(): B = {
     return (Hacks.owned(OwnershipActuals.create(() => scenario_field_local_ref())) == Hacks.owned(OwnershipExts.create(() => scenario_field_local_ref()))) &&
(Hacks.unowned(OwnershipActuals.create(() => scenario_field_local_ref())) == Hacks.unowned(OwnershipExts.create(() => scenario_field_local_ref())))
  }
  def test_create_scenario_no_ref_identity_upcast(): B = {
     return (Hacks.owned(OwnershipActuals.create(() => scenario_no_ref_identity_upcast())) == Hacks.owned(OwnershipExts.create(() => scenario_no_ref_identity_upcast()))) &&
(Hacks.unowned(OwnershipActuals.create(() => scenario_no_ref_identity_upcast())) == Hacks.unowned(OwnershipExts.create(() => scenario_no_ref_identity_upcast())))
  }
  def test_create_scenario_no_ref_identity_specialized(): B = {
     return (Hacks.owned(OwnershipActuals.create(() => scenario_no_ref_identity_specialized())) == Hacks.owned(OwnershipExts.create(() => scenario_no_ref_identity_specialized()))) &&
(Hacks.unowned(OwnershipActuals.create(() => scenario_no_ref_identity_specialized())) == Hacks.unowned(OwnershipExts.create(() => scenario_no_ref_identity_specialized())))
  }
  def test_create_scenario_no_ref_identity_generic(): B = {
     return (Hacks.owned(OwnershipActuals.create(() => scenario_no_ref_identity_generic())) == Hacks.owned(OwnershipExts.create(() => scenario_no_ref_identity_generic()))) &&
(Hacks.unowned(OwnershipActuals.create(() => scenario_no_ref_identity_generic())) == Hacks.unowned(OwnershipExts.create(() => scenario_no_ref_identity_generic())))
  }

  def ext_test_create_evilTwin_scenario_no_ref(): B = { Hacks.unowned(OwnershipExts.create_evilTwin(() => scenario_no_ref())) }
  def ext_test_create_evilTwin_scenario_local_ref(): B = { Hacks.unowned(OwnershipExts.create_evilTwin(() => scenario_local_ref())) }
  def ext_test_create_evilTwin_scenario_field_ref(): B = { Hacks.unowned(OwnershipExts.create_evilTwin(() => scenario_field_ref())) }
  def ext_test_create_evilTwin_scenario_field_local_ref(): B = { Hacks.unowned(OwnershipExts.create_evilTwin(() => scenario_field_local_ref())) }
  def ext_test_create_evilTwin_scenario_no_ref_identity_upcast(): B = { Hacks.unowned(OwnershipExts.create_evilTwin(() => scenario_no_ref_identity_upcast())) }
  def ext_test_create_evilTwin_scenario_no_ref_identity_specialized(): B = { Hacks.unowned(OwnershipExts.create_evilTwin(() => scenario_no_ref_identity_specialized())) }
  def ext_test_create_evilTwin_scenario_no_ref_identity_generic(): B = { Hacks.unowned(OwnershipExts.create_evilTwin(() => scenario_no_ref_identity_generic())) }

  def test_createNarrow_scenario_no_ref(): B = {
     return Hacks.unowned(OwnershipExts.createNarrow(() => scenario_no_ref()))
  }
  def test_createNarrow_scenario_local_ref(): B = {
     return Hacks.unowned(OwnershipExts.createNarrow(() => scenario_local_ref()))
  }
  def test_createNarrow_scenario_field_ref(): B = {
     return Hacks.unowned(OwnershipExts.createNarrow(() => scenario_field_ref()))
  }
  def test_createNarrow_scenario_field_local_ref(): B = {
     return Hacks.unowned(OwnershipExts.createNarrow(() => scenario_field_local_ref()))
  }
  def test_createNarrow_scenario_no_ref_identity_upcast(): B = {
     return Hacks.unowned(OwnershipExts.createNarrow(() => scenario_no_ref_identity_upcast()))
  }
  def test_createNarrow_scenario_no_ref_identity_specialized(): B = {
    // should fail now?
    val x = OwnershipExts.createNarrow(() => scenario_no_ref_identity_specialized())
    return Hacks.unowned[MBox[Z]](MBox((() => {
      println(OwnershipExts.createNarrow(() => scenario_no_ref_identity_specialized()))
      val y = OwnershipExts.createNarrow(() => scenario_no_ref_identity_specialized())
      println("test_createNarrow_scenario_no_ref_identity_specialized")
      println(x)
      println(y)
      y.value
    }).apply()))
  }
  def test_createNarrow_scenario_no_ref_identity_generic(): B = {
     return Hacks.unowned(OwnershipExts.createNarrow(() => scenario_no_ref_identity_generic()))
  }
  def test_createByName_scenario_no_ref(): B = {
     return (Hacks.owned(OwnershipActuals.createByName(scenario_no_ref())) == Hacks.owned(OwnershipExts.createByName(scenario_no_ref()))) &&
(Hacks.unowned(OwnershipActuals.createByName(scenario_no_ref())) == Hacks.unowned(OwnershipExts.createByName(scenario_no_ref())))
  }
  def test_createByName_scenario_local_ref(): B = {
     return (Hacks.owned(OwnershipActuals.createByName(scenario_local_ref())) == Hacks.owned(OwnershipExts.createByName(scenario_local_ref()))) &&
(Hacks.unowned(OwnershipActuals.createByName(scenario_local_ref())) == Hacks.unowned(OwnershipExts.createByName(scenario_local_ref())))
  }
  def test_createByName_scenario_field_ref(): B = {
     return (Hacks.owned(OwnershipActuals.createByName(scenario_field_ref())) == Hacks.owned(OwnershipExts.createByName(scenario_field_ref()))) &&
(Hacks.unowned(OwnershipActuals.createByName(scenario_field_ref())) == Hacks.unowned(OwnershipExts.createByName(scenario_field_ref())))
  }
  def test_createByName_scenario_field_local_ref(): B = {
     return (Hacks.owned(OwnershipActuals.createByName(scenario_field_local_ref())) == Hacks.owned(OwnershipExts.createByName(scenario_field_local_ref()))) &&
(Hacks.unowned(OwnershipActuals.createByName(scenario_field_local_ref())) == Hacks.unowned(OwnershipExts.createByName(scenario_field_local_ref())))
  }
  def test_createByName_scenario_no_ref_identity_upcast(): B = {
     return (Hacks.owned(OwnershipActuals.createByName(scenario_no_ref_identity_upcast())) == Hacks.owned(OwnershipExts.createByName(scenario_no_ref_identity_upcast()))) &&
(Hacks.unowned(OwnershipActuals.createByName(scenario_no_ref_identity_upcast())) == Hacks.unowned(OwnershipExts.createByName(scenario_no_ref_identity_upcast())))
  }
  def test_createByName_scenario_no_ref_identity_specialized(): B = {
     return (Hacks.owned(OwnershipActuals.createByName(scenario_no_ref_identity_specialized())) == Hacks.owned(OwnershipExts.createByName(scenario_no_ref_identity_specialized()))) &&
(Hacks.unowned(OwnershipActuals.createByName(scenario_no_ref_identity_specialized())) == Hacks.unowned(OwnershipExts.createByName(scenario_no_ref_identity_specialized())))
  }
  def test_createByName_scenario_no_ref_identity_generic(): B = {
     return (Hacks.owned(OwnershipActuals.createByName(scenario_no_ref_identity_generic())) == Hacks.owned(OwnershipExts.createByName(scenario_no_ref_identity_generic()))) &&
(Hacks.unowned(OwnershipActuals.createByName(scenario_no_ref_identity_generic())) == Hacks.unowned(OwnershipExts.createByName(scenario_no_ref_identity_generic())))
  }
  def ext_test_createByName_evilTwin_scenario_no_ref(): B = { Hacks.unowned(OwnershipExts.createByName_evilTwin(scenario_no_ref())) }
  def ext_test_createByName_evilTwin_scenario_local_ref(): B = { Hacks.unowned(OwnershipExts.createByName_evilTwin(scenario_local_ref())) }
  def ext_test_createByName_evilTwin_scenario_field_ref(): B = { Hacks.unowned(OwnershipExts.createByName_evilTwin(scenario_field_ref())) }
  def ext_test_createByName_evilTwin_scenario_field_local_ref(): B = { Hacks.unowned(OwnershipExts.createByName_evilTwin(scenario_field_local_ref())) }
  def ext_test_createByName_evilTwin_scenario_no_ref_identity_upcast(): B = { Hacks.unowned(OwnershipExts.createByName_evilTwin(scenario_no_ref_identity_upcast())) }
  def ext_test_createByName_evilTwin_scenario_no_ref_identity_specialized(): B = { Hacks.unowned(OwnershipExts.createByName_evilTwin(scenario_no_ref_identity_specialized())) }
  def ext_test_createByName_evilTwin_scenario_no_ref_identity_generic(): B = { Hacks.unowned(OwnershipExts.createByName_evilTwin(scenario_no_ref_identity_generic())) }

  def test_createKnown(): B = {
    return Hacks.unowned(OwnershipExts.createKnown())
  }

  def ext_test_createKnown(): B = {
    return Hacks.unowned(OwnershipExts.createKnown())
  }
  def test_createKnown_evilTwin(): B = {
    return Hacks.unowned(OwnershipExts.createKnown_evilTwin())
  }

  def ext_test_createKnown_evilTwin(): B = {
    return Hacks.unowned(OwnershipExts.createKnown_evilTwin())
  }
  def test_createKnown_veryEvilTwin(): B = {
    return Hacks.unowned(OwnershipExts.createKnown_veryEvilTwin())
  }

  def ext_test_createKnown_veryEvilTwin(): B = {
    return Hacks.unowned(OwnershipExts.createKnown_veryEvilTwin())
  }
  def test_zrandom(): B = {
    Hacks.unowned(OwnershipExts.create(() => MBox(Z.random)))
  }

  def ext_test_zrandom(): B = {
    Hacks.unowned(OwnershipExts.create(() => MBox(Z.random)))
  }

  def linked_test_reachable_no_ref(): B = {
    val a = scenario_no_ref()
    val _a = a
    val __a = _a
    val ___a = __a
    return Hacks.owned(a) && Hacks.owned(_a) && Hacks.owned(__a) && Hacks.owned(___a)
  }

  def linked_test_reachable_local_ref(): B = {
    var b = scenario_local_ref()
    var _b = b
    var __b = _b
    val ___b = __b
    b = scenario_local_ref()
    _b = scenario_local_ref()
    __b = _b
    return Hacks.owned(b) && Hacks.owned(_b) && Hacks.owned(__b) && Hacks.owned(___b)
  }

  def linked_test_reachable_field_ref(): B = {
    var c = scenario_field_ref()
    var _c = c
    var __c = _c
    val ___c = __c
    c = scenario_field_ref()
    _c = scenario_field_ref()
    __c = _c
    return Hacks.owned(c) && Hacks.owned(_c) && Hacks.owned(__c) && Hacks.owned(___c)
  }

  def linked_test_reachable_field_local_ref(): B = {
    var d = scenario_field_local_ref()
    var _d = d
    var __d = _d
    val ___d = __d
    d = scenario_field_local_ref()
    _d = scenario_field_local_ref()
    __d = _d
    return Hacks.owned(d) && Hacks.owned(_d) && Hacks.owned(__d) && Hacks.owned(___d)
  }

  def linked_test_reachable_no_ref_identity_specialized(): B = {
    var e = scenario_no_ref_identity_specialized()
    var _e = e
    var __e = _e
    val ___e = __e
    e = scenario_no_ref_identity_specialized()
    _e = scenario_no_ref_identity_specialized()
    __e = _e
    return Hacks.owned(e) && Hacks.owned(_e) && Hacks.owned(__e) && Hacks.owned(___e)
  }

  // A generic form of the method "linked_test_reachable_no_ref_identity_specialized" was added to Hacks and called "test"
  // Some behavioral/semantic differences are to be expected in extension methods (that's kinda the whole point!) and
  // are not of concern.
  def HACKS_IN_HOUSE_linked_test_reachable_no_ref_identity_generic(): B = {
    return Hacks.test(() => scenario_no_ref_identity_generic())
  }

  def linked_test_reachable_no_ref_identity_generic(): B = {
    var f = scenario_no_ref_identity_generic()
    var _f = f
    var __f = _f
    val ___f = __f
    f = scenario_no_ref_identity_generic()
    _f = scenario_no_ref_identity_generic()
    __f = _f
    return Hacks.owned(f) && Hacks.owned(_f) && Hacks.owned(__f) && Hacks.owned(___f)
  }

  def linked_test_reachable_no_ref_identity_upcast(): B = {
    var g = scenario_no_ref_identity_upcast()
    var _g = g
    var __g = _g
    val ___g = __g
    g = scenario_no_ref_identity_upcast()
    _g = scenario_no_ref_identity_upcast()
    __g = _g
    return Hacks.owned(g) && Hacks.owned(_g) && Hacks.owned(__g) && Hacks.owned(___g)
  }

  var field: MBox[Z] = MBox(randomInt())

  def scenario_no_ref(): MBox[Z] = {
    return MBox(randomInt())
  }

  def scenario_local_ref(): MBox[Z] = {
    val local = MBox(randomInt())
    return local
  }

  def scenario_field_ref(): MBox[Z] = {
    field = MBox(randomInt())
    field = MBox(randomInt())
    return field
  }

  def scenario_field_local_ref(): MBox[Z] = {
    val local = field
    return local
  }

  def scenario_no_ref_identity_specialized(): MBox[Z] = {
    def debug(box: MBox[Z]): Unit = {
      println(st"[ DEBUG - identity_inner ] $box (ownership status: ${Hacks.owned(box)}) (identity: ${Hacks.identityHashCode(box)})".render)
    }

    @pure def identity_inner(box: MBox[Z]): MBox[Z] = {
      debug(box)
      return box
    }

    val n: Z = randomInt()

    val box: MBox[Z] = MBox(n)
    identity_inner(box)
  }

  def identity_generic[T](box: MBox[T]): MBox[T] = {
    return box
  }

  def identity_upcast(mutableMarker: MBox[Z]): MBox[Z] = {
    return mutableMarker
  }

  def scenario_no_ref_identity_generic(): MBox[Z] = {
    identity_generic(MBox(randomInt()))
  }

  def scenario_no_ref_identity_upcast(): MBox[Z] = {
    identity_upcast(MBox(randomInt()))
  }

}
