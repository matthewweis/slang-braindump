package lib

import java.lang.invoke.{MethodHandle, MethodHandles}
import java.lang.reflect.Method

object Hacks_Ext {

  //
  private val HANDLE: MethodHandle = {
    val LOOKUP: MethodHandles.Lookup = MethodHandles.lookup()
    val CLASS: Class[_] = MethodHandles.lookup().in(classOf[org.sireum.$internal.MutableMarker]).lookupClass()
    val METHOD: Method = CLASS.getDeclaredMethod("$owned")
    METHOD.setAccessible(true)
    LOOKUP.unreflect(METHOD)
  }

  private def OWNED(mutableMarker: org.sireum.$internal.MutableMarker): org.sireum.B = {
    Boolean.unbox(HANDLE.invoke(mutableMarker)) match {
      case true => org.sireum.T
      case false => org.sireum.F
    }
  }

  // identity function for testing @ext functions conformance to Slang semantics
  def identity[T](t: T): T = t

  // returns the memory address of t
  def identityHashCode[T](t: T): org.sireum.Z = {
    return org.sireum.Z.apply(System.identityHashCode(t))
  }

  def owned[T](t: T): org.sireum.B = {
    t match {
      case mm: org.sireum.$internal.MutableMarker => OWNED(mm)
      case _ => org.sireum.F
    }
  }

  def unowned[T](t: T): org.sireum.B = {
    t match {
      case mm: org.sireum.$internal.MutableMarker => !OWNED(mm)
      case _ => org.sireum.T
    }
  }

  // temp
  def test(scenario: () => org.sireum.MBox[org.sireum.Z]): org.sireum.B = {
    var f = scenario()
    var _f = f
    var __f = _f
    var ___f = __f
    f = scenario()
    _f = scenario()
    __f = _f
    return Hacks.owned(f) && Hacks.owned(_f) && Hacks.owned(__f) && Hacks.owned(___f)
  }

}
