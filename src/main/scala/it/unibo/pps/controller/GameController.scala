package it.unibo.pps.controller

import it.unibo.pps.ECHandler
import it.unibo.pps.business.{GameRepository, RoundRepository, UserRepository}
import it.unibo.pps.model.{Game, Round, User}
import reactivemongo.api.bson.BSONDocument

import java.time.LocalDateTime
import scala.concurrent.duration.*
import scala.concurrent.{Await, ExecutionContext, Future}

/** Controller per la gestione delle partite */
@SuppressWarnings(
  Array("org.wartremover.warts.Var", "org.wartremover.warts.IterableOps", "org.wartremover.warts.DefaultArguments")
)
object GameController:

  private val gameRepository = new GameRepository
  private val roundRepository = new RoundRepository
  private val userRepository = new UserRepository

  /** variabile per la gestione della partita in corso */
  private var _gameOfLoggedUsers: Option[Game] = None
  def gameOfLoggedUsers: Option[Game] = _gameOfLoggedUsers

  /** Numero di round per partita. */
  private val ROUND_FOR_GAME: Int = 3

  /** Numero di partite da restituire. */
  private val NUMBER_LAST_GAME: Int = 5

  /** Numero di migliori giocatori da includere nella classifica globale. */
  private val BEST_PLAYERS: Int = 5

  /** Metodo per ottenere l'ultimo round della partita in corso fra gli utenti loggati.
    * @return
    *   l'ultimo round della partita in corso
    */
  def getLastRoundByGame: Option[Round] =
    gameOfLoggedUsers.flatMap(game =>
      Await.result(roundRepository.getAllRoundsByGame(game), 5.seconds).getOrElse(List.empty[Round]).lastOption
    )

  /** Metodo per ottenere la partita in corso fra gli utenti specificati.
    *
    * Nel caso in cui non ci sia una partita in corso salvata in memoria, viene effettuata una query al database e
    * salvato il risultato localmente.
    *
    * @param users
    *   lista degli utenti che partecipano alla partita
    * @return
    *   [[None]] se la lista di utenti è vuota o se non esiste una partita in corso fra gli utenti specificati,
    *   Altrimenti, la partita in corso
    */
  def getCurrentGameFromPlayers(users: List[User]): Option[Game] =
    if users.isEmpty then None
    else
      gameOfLoggedUsers match
        case Some(g: Game) => Some(g)
        case None =>
          Await.result(gameRepository.getCurrentGameFromPlayers(users), 5.seconds) match
            case Some(g) =>
              _gameOfLoggedUsers = g.headOption
              g.headOption
            case None => None

  /** Metodo per ottenere tutte le partite in corso di un utente.
    * @param user
    *   utente di cui si vogliono ottenere le partite in corso
    * @return
    *   lista delle partite in corso dell'utente specificato
    */
  def getCurrentGamesFromSinglePlayer(user: User): Option[List[Game]] =
    Await.result(gameRepository.getCurrentGameFromPlayers(List(user)), 5.seconds)

  /** Metodo per ottenere le ultime partite completate di un utente.
    * @param user
    *   utente di cui si vogliono ottenere le partite completate
    * @param limit
    *   numero massimo di partite da estrarre
    * @return
    *   lista delle ultime partite completate dell'utente specificato
    */
  def getLastGameCompletedByUser(user: User, limit: Int = NUMBER_LAST_GAME): Option[List[Game]] =
    Await.result(gameRepository.getLastGameCompletedByUser(user, NUMBER_LAST_GAME), 5.seconds)

  /** Metodo per controllare se la partita in corso è terminata. In caso affermativo, ne aggiorna lo stato sul database.
    */
  def verifyGameEnd(): Unit =
    RoundController.round.foreach(round => {
      if round.numberRound == ROUND_FOR_GAME && round.scores.forall(_.score != -1) then
        gameOfLoggedUsers.foreach(game => {
          game.completed = true
          game.lastUpdate = LocalDateTime.now()
          gameRepository.update(game, game.id)
        })
    })

  /** Metodo per ottenere le partite vinte da un utente.
    * @param user
    *   utente di cui si vogliono ottenere le partite vinte
    * @return
    *   lista delle partite vinte dall'utente specificato
    */
  def getGameWonByUser(user: User): Int =
    val games: List[Game] = getLastGameCompletedByUser(user, -1).getOrElse(List.empty)
    games.count(game => {
      RoundController.computePartialPointsOfUser(user, Some(game)) > RoundController.computePartialPointsOfUser(
        game.players.filter(u => u.username != user.username).head,
        Some(game)
      )
    })

  /** Metodo per ottenere le partite perse da un utente.
    * @param user
    *   utente di cui si vogliono ottenere le partite perse
    * @return
    *   lista delle partite perse dall'utente specificato
    */
  def getGameLostByUser(user: User): Int =
    val games: List[Game] = getLastGameCompletedByUser(user, -1).getOrElse(List.empty)
    games.count(game => {
      RoundController.computePartialPointsOfUser(user, Some(game)) < RoundController.computePartialPointsOfUser(
        game.players.filter(u => u.username != user.username).head,
        Some(game)
      )
    })

  /** Metodo per ottenere la posizione in classifica di un utente per il numero di partite vinte.
    * @param user
    *   utente di cui si vuole ottenere la posizione in classifica
    * @return
    *   posizione in classifica dell'utente specificato
    */
  def getUserRanking(user: User): Int =
    val myPoint = getGameWonByUser(user)
    val otherUsers: List[User] = Await
      .result(userRepository.readMany(BSONDocument("_id" -> BSONDocument("$ne" -> user.id))), 5.seconds)
      .getOrElse(List.empty[User])
    otherUsers.count(u => getGameWonByUser(u) > myPoint) + 1

  /** Metodo per ottenere la classifica globale degli utenti per il numero di partite vinte.
    * @param rankingLength
    *   numero dei migliori giocatori da inserire nella classifica
    * @return
    *   classifica globale degli utenti, ovvero la lista dei migliori [[rankingLength]] giocatori in termini di partite
    *   vinte
    */
  def getGlobalRanking(rankingLength: Int = BEST_PLAYERS): Future[List[User]] =
    given ExecutionContext = ECHandler.createExecutor
    userRepository
      .readMany(BSONDocument())
      .flatMap(allUsers =>
        Future {
          allUsers.getOrElse(List.empty[User]).sortBy(getGameWonByUser).reverse.take(rankingLength)
        }
      )

  /** Metodo per creare una nuova partita e salvarla nel database.
    */
  def createNewGame(): Unit =
    val newGame = Game(
      UserController.loggedUsers.getOrElse(List.empty[User]),
      false,
      LocalDateTime.now(),
      CategoryController.getRandomCategories(ROUND_FOR_GAME)
    )
    Await.result(gameRepository.create(newGame), 5.seconds)

  /** Metodo invocato alla fine di ogni partita per resettare la variabile di controllo della partita in corso.
    */
  def resetVariable(): Unit =
    _gameOfLoggedUsers = None

end GameController
