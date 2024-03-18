package it.unibo.pps.model

import it.unibo.pps.business.{CategoryRepository, UserRepository}
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import java.time.LocalDateTime
import java.util.UUID
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Try

case class Game(
    user1: User,
    user2: User,
    completed: Boolean,
    lastUpdate: LocalDateTime,
    categories: List[Category],
    id: Option[UUID] = None
) {
  private val _id: UUID = id.getOrElse(UUID.randomUUID())

  def getID: String = _id.toString
  def getUser1: User = user1
  def getUser2: User = user2
  def getCompleted: Boolean = completed
  def getLastUpdate: LocalDateTime = lastUpdate

  def getCategories: List[Category] = categories

}

object Game {
  implicit object GameReader extends BSONDocumentReader[Game]:

    protected val userRepository = new UserRepository
    protected val categoryRepository = new CategoryRepository

    def readDocument(doc: BSONDocument): Try[Game] =
      // read users
      var user1, user2: User = null
      val futureUser1 = Await.result(userRepository.read(doc.getAsTry[String]("user1").get), Duration.Inf)
      val futureUser2 = Await.result(userRepository.read(doc.getAsTry[String]("user2").get), Duration.Inf)
      if futureUser1.isDefined then user1 = futureUser1.get
      if futureUser2.isDefined then user2 = futureUser2.get
      // read categories
      var category1, category2, category3: Category = null
      val futureCategory1 = Await.result(categoryRepository.read(doc.getAsTry[String]("category1").get), Duration.Inf)
      val futureCategory2 = Await.result(categoryRepository.read(doc.getAsTry[String]("category2").get), Duration.Inf)
      val futureCategory3 = Await.result(categoryRepository.read(doc.getAsTry[String]("category3").get), Duration.Inf)
      if futureCategory1.isDefined then category1 = futureCategory1.get
      if futureCategory2.isDefined then category2 = futureCategory2.get
      if futureCategory3.isDefined then category3 = futureCategory3.get

      for
        id <- doc.getAsTry[String]("_id")
        completed <- doc.getAsTry[Boolean]("completed")
        lastUpdate <- doc.getAsTry[LocalDateTime]("lastUpdate")
      yield Game(user1, user2, completed, lastUpdate, List(category1, category2, category3))

  implicit object GameWriter extends BSONDocumentWriter[Game]:
    override def writeTry(game: Game): Try[BSONDocument] = for
      id <- Try(game.getID)
      user1 <- Try(game.getUser1.getID)
      user2 <- Try(game.getUser2.getID)
      completed <- Try(game.getCompleted)
      lastUpdate <- Try(game.getLastUpdate)
    yield BSONDocument(
      "_id" -> id,
      "user1" -> user1,
      "user2" -> user2,
      "completed" -> completed,
      "lastUpdate" -> lastUpdate,
      "category1" -> game.getCategories.head.getID,
      "category2" -> game.getCategories(1).getID,
      "category3" -> game.getCategories(2).getID
    )
}
