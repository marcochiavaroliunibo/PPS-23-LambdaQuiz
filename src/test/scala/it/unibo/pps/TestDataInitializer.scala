package it.unibo.pps

import it.unibo.pps.business.{GameRepository, QuestionRepository, RoundRepository, UserRepository}
import it.unibo.pps.controller.CategoryController
import it.unibo.pps.model.*
import it.unibo.pps.model.Category.Storia
import reactivemongo.api.bson.BSONDocument

import java.time.{LocalDateTime, Month}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Random

object TestDataInitializer:
  val gameRepository = new GameRepository
  val userRepository = new UserRepository
  val roundRepository = new RoundRepository
  val questionRepository = new QuestionRepository

  def categories: List[Category] = CategoryController.getRandomCategories(3)
  val players: List[User] = (1 to 2).map(i => User(s"User$i", s"passwd${i + 1}")).toList
  val games: List[Game] = generateGames
  val rounds: List[Round] = generateRounds
  val question: Question = Question(
    "Chi è stato il primo presidente degli Stati Uniti?",
    List("Thomas Jefferson", "Abraham Lincoln", "John Adams", "George Washington"),
    3,
    Storia
  )

  private def generateGames: List[Game] =
    val currentGame: Game = Game(
      players,
      false,
      LocalDateTime.now(),
      categories
    )
    val completedGames =
      (1 to 5)
        .map(_ => Game(players, true, generateRandomDateTime, categories))
        .toList
    currentGame :: completedGames

  private def generateRounds: List[Round] =
    games.flatMap { game =>
      (1 to 3).map { i =>
        val scoreList = game.players.zipWithIndex.map { case (user, index) =>
          Score(user, Random.between(0, 3))
        }
        Round(game.id, scoreList, i)
      }
    }

  private def generateRandomDateTime: LocalDateTime =
    val year = Random.between(2020, 2024)
    val month = Random.between(1, 12)
    val day = Random.between(1, 28)
    val hour = Random.between(0, 23)
    val minute = Random.between(0, 59)
    LocalDateTime.of(year, month, day, hour, minute)

  def initData: Future[Unit] =
    Future
      .sequence(players.map(userRepository.create))
      .flatMap(_ => Future.sequence(games.map(gameRepository.create)))
      .flatMap(_ => Future.sequence(rounds.map(roundRepository.create)))
      .map(_ => {})

  def cleanData(): Future[Unit] =
    val selectAll = BSONDocument()
    userRepository
      .delete(selectAll)
      .andThen(_ => gameRepository.delete(selectAll))
      .andThen(_ => roundRepository.delete(selectAll))
//    .andThen(_ => questionRepository.delete(selectAll))
      .andThen(_ => Thread.sleep(10))

end TestDataInitializer
