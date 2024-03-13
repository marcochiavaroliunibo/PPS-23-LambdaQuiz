package it.unibo.pps.business.users

import it.unibo.pps.business.Repository
import it.unibo.pps.model.User

trait UserData extends Repository[User] {

  def createUser(nickname: String, password: String): Unit
  def recoveryPassword(nickname: String): Option[?]
  def deleteUser(nickname: String): Unit

}
