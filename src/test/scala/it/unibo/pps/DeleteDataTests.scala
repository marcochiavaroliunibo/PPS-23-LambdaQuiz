package it.unibo.pps

import it.unibo.pps.business.{ConnectionMongoDB, GameRepository, QuestionRepository, RoundRepository, UserRepository}
import org.scalatest.{Args, Status}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*
import reactivemongo.api.bson.BSONDocument

class DeleteDataTests extends AsyncFlatSpec with should.Matchers:

  val userRepository = new UserRepository
  val questionRepository = new QuestionRepository
  val roundRepository = new RoundRepository
  val gameRepository = new GameRepository

  override def run(testName: Option[String], args: Args): Status = {
    val status = super.run(testName, args)
    ConnectionMongoDB.getDatabase(true).map(db => db.name shouldEqual "LambdaQuiz-test")
    val selector = BSONDocument()
    userRepository.delete(selector)
    questionRepository.delete(selector)
    roundRepository.delete(selector)
    gameRepository.delete(selector)
    println("Clean of Test DB!")
    status
  }
  
end DeleteDataTests
