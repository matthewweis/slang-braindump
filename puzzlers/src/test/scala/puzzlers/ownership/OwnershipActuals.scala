// #Sireum

package puzzlers.ownership

import org.sireum._

object OwnershipActuals {

  def identity[T](t: T): T = {
    return t
  }

  def create[T](factory: () => T): T = {
    factory()
  }

  def createByName[T](callByName: => T): T = {
    return callByName
  }

  def createKnown(): org.sireum.MBox[org.sireum.Z] = {
    org.sireum.MBox(org.sireum.randomInt())
  }

}
