// #Sireum

package lib

import org.sireum._

@ext object Hacks {

  def identity[T](t: T): T = $
  def identityHashCode[T](t: T): Z = $

  // The predicate """forall t : Hacks.owned(t) != Hacks.unowned(t)""" is FALSE because t.getClass
  // may subtype or equal ImmutableMarker. Hence we need both "owned" and "unowned".
  def owned[T](t: T): B = $
  def unowned[T](t: T): B = $

  def test(scenario: () => org.sireum.MBox[org.sireum.Z]): B = $

}
