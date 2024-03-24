package it.unibo.pps.business

import it.unibo.pps.model.{Category, Game, User}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

import java.time.LocalDateTime

class GameRepositoryTests extends AsyncFlatSpec with should.Matchers:
  import Category.*
  private val gameRepository = new GameRepository
  private val categories: List[Category] = List(Storia, Scienze, Geografia)
  private val players = List(new User("user1", "PASSWORDDD"), new User("user2", "PASSWORDDD"))
  private val game = new Game(
    players,
    false,
    LocalDateTime.now(),
    categories
  )

  "A game" should "eventually be inserted in the database" in {
    gameRepository
      .create(game)
      .map(_ shouldBe a[Unit])
  }

  it should "be read from the database" in {
    gameRepository
      .readById(game.getID)
      .map(_.exists(_.getID == game.getID) should be(true))
  }

  it should "contains both the inserted users" in {
    gameRepository
      .readById(game.getID)
      .map(
        _.exists(g =>
          g.players.size == players.size &&
            g.players.map(_.username).forall(players.map(_.username).contains)
        )
          should be(true)
      )
  }

//  it should "contains both the inserted users" in {
//    gameRepository
//      .readById(game.getID)
//      .map(
//        _.exists(g => (g.players.head.username == "user1" || g.players.head.username == "user2")
//          && (g.players.last.username == "user1" || g.players.last.username == "user2"))
//          should be(true)
//      )
//  }

  it should "contains all the inserted categories" in {
    gameRepository
      .readById(game.getID)
      .map(_.exists(u => u.categories.forall(categories.contains)) should be(true))
  }

end GameRepositoryTests
