package it.unibo.pps.controller

import it.unibo.pps.business.{GameRepository, RoundRepository, UserRepository}
import it.unibo.pps.model.{Game, Round, User}
import reactivemongo.api.bson.BSONDocument
import java.time.LocalDateTime
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*
import scala.concurrent.{Await, Future}

object GameController:

  private var _gameOfLoggedUsers: Option[Game] = None
  private val gameRepository = new GameRepository
  private val roundRepository = new RoundRepository
  private val userRepository = new UserRepository
  private val ROUND_FOR_GAME: Int = 3
  
  def gameOfLoggedUsers: Option[Game] = _gameOfLoggedUsers
  def gameOfLoggedUsers_=(g: Game): Unit = _gameOfLoggedUsers = Some(g)
  
  /** ottiene ultimo round di una partita */
  def getLastRoundByGame: Option[Round] =
    Await.result(roundRepository.getAllRoundsByGame(gameOfLoggedUsers.get), 5.seconds).map(_.last)

  /** ottiene l'eventuale partita in corso fra due utenti */
  def getCurrentGameFromPlayers(users: List[User]): Option[Game] =
    if users.isEmpty then None
    else
      gameOfLoggedUsers match
        case Some(g: Game) => Some(g)
        case None =>
          Await.result(gameRepository.getCurrentGameFromPlayers(users), 5.seconds) match
            case Some(g) =>
              gameOfLoggedUsers = g.head
              Some(g.head)
            case None => None

  /** ottiene tutte le partite in corso di un utente */
  def getCurrentGamesFromSinglePlayer(user: User): Option[List[Game]] =
    Await.result(gameRepository.getCurrentGameFromPlayers(List(user)), 5.seconds)

  /** ottiene le ultime partite completate di un utente di default estrae le ultime 5 partite (valore parametrizzabile
    * da chi esegue la chiamata)
    */
  private val NUMBER_LAST_GAME: Int = 5
  def getLastGameCompletedByUser(user: User, limit: Int = NUMBER_LAST_GAME): Option[List[Game]] =
    Await.result(gameRepository.getLastGameCompletedByUser(user, NUMBER_LAST_GAME), 5.seconds)

  /** verifica se si è arrivati alla conclusione di una partita eventualmente aggiorna lo stato in completed = true
    */
  def checkFinishGame(): Unit = {
    if (
      RoundController.round.get.numberRound == ROUND_FOR_GAME && RoundController.round.get.scores.forall(_.score != -1)
    )
      val gameEdited: Game = gameOfLoggedUsers.get
      gameEdited.completed = true
      gameEdited.lastUpdate = LocalDateTime.now()
      gameRepository.update(gameEdited, gameEdited.getID)
  }

  /** ottiene le partite vinte da un utente */
  def getGameWonByUser(user: User): List[Game] =
    val games: List[Game] = getLastGameCompletedByUser(user, Int.MaxValue).getOrElse(List())
    games.filter(game => {
      RoundController.computePartialPointsOfUser(user, game) > RoundController.computePartialPointsOfUser(
        game.players.filter(u => u.username != user.username).head,
        game
      )
    })

  /** ottiene le partite perse da un utente */
  def getGameLostByUser(user: User): List[Game] =
    val games: List[Game] = getLastGameCompletedByUser(user, Int.MaxValue).getOrElse(List())
    games.filter(game => {
      RoundController.computePartialPointsOfUser(user, game) < RoundController.computePartialPointsOfUser(
        game.players.filter(u => u.username != user.username).head,
        game
      )
    })

  /** ottiene la posizione in classifica (per numero di partite vinte) di un utente */
  def getRankingUser(user: User): Int =
    val myPoint = getGameWonByUser(user).length
    val otherUsers: List[User] = Await
      .result(userRepository.readMany(BSONDocument("_id" -> BSONDocument("$ne" -> user.getID))), 5.seconds)
      .getOrElse(List())
    otherUsers.count(u => getGameWonByUser(u).length > myPoint) + 1

  /** ottiene la lista degli utenti ordinata partendo da chi ha più vittorie di default estrae i migliori 5 utenti
    * (valore parametrizzabile da chi esegue la chiamata)
    */
  private val BEST_PLAYERS: Int = 5
  def getGlobalRanking(max: Int = BEST_PLAYERS): Future[List[User]] =
    userRepository
      .readMany(BSONDocument())
      .flatMap(allUsers =>
        Future {
          allUsers.getOrElse(List.empty).sortBy(getGameWonByUser(_).length).reverse.take(max)
        }
      )

  /** crea un nuovo game */
  def createNewGame(): Unit =
    val newGame = new Game(
      UserController.loggedUsers.getOrElse(List.empty),
      false,
      LocalDateTime.now(),
      CategoryController.getRandomCategories(ROUND_FOR_GAME)
    )
    Await.result(gameRepository.create(newGame), 5.seconds)

  /** a fine giocata, resetto le variabili per la prossima giocata */
  def resetVariable(): Unit =
    _gameOfLoggedUsers = None

end GameController
