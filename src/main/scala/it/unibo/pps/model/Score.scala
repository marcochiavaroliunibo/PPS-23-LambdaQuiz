package it.unibo.pps.model

import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import scala.util.Try

case class Score(user: User, var score: Int = -1)

object Score:
  implicit object ScoreReader extends BSONDocumentReader[Score]:
    def readDocument(doc: BSONDocument): Try[Score] =
      for
        user <- doc.getAsTry[User]("user")
        score <- doc.getAsTry[Int]("score")
      yield Score(user, score)

  implicit object ScoreWriter extends BSONDocumentWriter[Score]:
    override def writeTry(score: Score): Try[BSONDocument] =
      for
        user <- Try(score.user)
        score <- Try(score.score)
      yield BSONDocument(
        "user" -> user,
        "score" -> score
      )
end Score
