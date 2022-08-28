package puzzlers.ownership

object OwnershipExts_Ext {

  def create[T](factory: () => T): T = {
    factory.apply()
  }

  def create_evilTwin[T](factory: () => T): T = {
    val statement: T = factory.apply()
    statement // <-- not allowed in Slang
  }

  def createNarrow[T](factory: () => T): T = {
    val guard: Any = factory.apply()
    guard.asInstanceOf[T]
  }

  def createByName[T](callByName: => T): T = {
    callByName
  }

  def createByName_evilTwin[T](callByName: => T): T = {
    val statement: T = callByName
    statement // <-- not allowed in Slang
  }

  def createKnown(): org.sireum.MBox[org.sireum.Z] = {
    return org.sireum.MBox(org.sireum.randomInt())
  }

  def createKnown_evilTwin(): org.sireum.MBox[org.sireum.Z] = {
    val statement: org.sireum.MBox[org.sireum.Z] = org.sireum.MBox(org.sireum.randomInt())
    return statement
  }

  def createKnown_veryEvilTwin(): org.sireum.MBox[org.sireum.Z] = {
    val any: Any = org.sireum.MBox(org.sireum.randomInt())
    val erased: org.sireum.MBox[Any] = any.asInstanceOf[org.sireum.MBox[Any]]
    val known: org.sireum.MBox[org.sireum.Z] = erased.asInstanceOf[org.sireum.MBox[org.sireum.Z]]
    return known // <-- not allowed in Slang
  }

}
