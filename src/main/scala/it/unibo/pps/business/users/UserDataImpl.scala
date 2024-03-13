package it.unibo.pps.business.users

import it.unibo.pps.business.ConnectionMongoDB
import reactivemongo.api.bson.BSONDocument

import scala.concurrent.*
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*
import scala.util.{Failure, Success}

abstract class UserDataImpl extends UserData {

//    override def createUser(nickname: String, password: String): Unit = {
//        val newDocument: Document = Document(
//            "nickname" -> nickname,
//            "password" -> password
//        )
//        val insertObservable = collection.insertOne(newDocument)
//        val insertResult = insertObservable.toFuture()
//        val result = Await.result(insertResult, 10.seconds)
//    }
//
//    override def recoveryPassword(nickname: String): Option[Document] = {
//        val result = collection.find(equal("nickname", nickname)).first().headOption()
//        // Attende il risultato e restituisce l'utente trovato, se presente
//        Await.result(result, Duration.Inf)
//        // todo: convertire de ottenere la stringa
//        // ...
//    }
//
//    override def deleteUser(nickname: String): Unit = {
//        // Esegue la query per trovare l'utente con il nickname specificato
//        val result = collection.deleteOne(equal("nickname", nickname)).toFuture()
//        // Attende il risultato dell'operazione di eliminazione
//        Await.result(result, Duration.Inf)
//    }
}
