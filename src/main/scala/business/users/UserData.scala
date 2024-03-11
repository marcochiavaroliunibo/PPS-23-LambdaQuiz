import org.mongodb.scala._

trait UserData {
  
    def createUser(nickname: String, password: String) : Unit
    def recoveryPassword(nickname: String) : Option[Document]
    def deleteUser(nickname: String) : Unit

}
