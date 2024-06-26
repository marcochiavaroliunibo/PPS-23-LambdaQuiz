package it.unibo.pps.controller

import it.unibo.pps.TestDataInitializer.{games, players, rounds}
import it.unibo.pps.model.{Game, User}
import org.scalatest.DoNotDiscover
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.*

@DoNotDiscover
@SuppressWarnings(Array("org.wartremover.warts.IterableOps"))
/** Classe di test per [[GameController]] */
class GameControllerTests extends AnyFlatSpec with should.Matchers:

  "GameController" should "be able to read the current game" in {
    val currentGame = GameController.getCurrentGameFromPlayers(players)
    val notCompletedGame = games.filterNot(_.completed).head
    currentGame.exists(_.id == notCompletedGame.id) should be(true)
  }

  it should "be able to calculate ranking positions" in {
    val user = players.head
    val otherUser = players.filterNot(_ == user).head
    val computePointsOfUser: User => Game => Int =
      u => g => rounds.filter(_.gameId == g.id).flatMap(_.scores).filter(_.user == u).map(_.score).sum

    val allCompletedGames = games.filter(_.completed)
    val wonGamesByUser = allCompletedGames.count(g => computePointsOfUser(user)(g) > computePointsOfUser(otherUser)(g))
    val wonGamesByOtherUser =
      allCompletedGames.count(g => computePointsOfUser(user)(g) < computePointsOfUser(otherUser)(g))

    val computedUserRanking = List(otherUser).count(_ => wonGamesByOtherUser > wonGamesByUser) + 1
    val computedOtherUserRanking = List(user).count(_ => wonGamesByUser > wonGamesByOtherUser) + 1
    val userRanking = GameController.getUserRanking(user)
    val otherUserRanking = GameController.getUserRanking(otherUser)

    // Per come sono stati generati i dati, tutte le partite sono state vinte da otherUser.
    // Quindi, la sua posizione in classifica sarà migliore.
    (userRanking == computedUserRanking && otherUserRanking == computedOtherUserRanking) should be(true)
  }

end GameControllerTests
