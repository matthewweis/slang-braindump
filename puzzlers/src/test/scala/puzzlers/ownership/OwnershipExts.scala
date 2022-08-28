// #Sireum

package puzzlers.ownership

import org.sireum._

@ext object OwnershipExts {

  def create[T](factory: () => T): T = $
  def create_evilTwin[T](factory: () => T): T = $
  def createNarrow[T](factory: () => T): T = $
  def createByName[T](callByName: => T): T = $
  def createByName_evilTwin[T](callByName: => T): T = $
  def createKnown(): org.sireum.MBox[org.sireum.Z] = $
  def createKnown_evilTwin(): org.sireum.MBox[org.sireum.Z] = $
  def createKnown_veryEvilTwin(): org.sireum.MBox[org.sireum.Z] = $

}
