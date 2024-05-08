# Marco Chiavaroli

Entrando nel dettaglio del lavoro svolto dal singolo membro del team, si riporta di seguito un resoconto delle parti
svolte prevalentemente da Marco.

## Logica di business

Nella prima fase del progetto è stato fondamentale definire una logica dati su cui gettare le fondamenta della struttura
del sistema. Questa parte, è stata svolta con sinergia da entrambi i membri del gruppo con Marco che ha avuto un maggior
numero di task focalizzati sulla logica di business e repository dell'applicativo.
Di conseguenza, si è occupato di definire le funzioni che implementano le query per la scrittura, modifica ed estrazione
dei dati, gestendo così la comunicazione con la base di dati MongoDB.
Questa parte ha avuto chiaramente risonanza su tutte le funzionalità dell'applicazione, sviluppate in seguito seguendo i
pattern funzionali del linguaggio Scala.

L'implementazione è partita sviluppando un trait contenente le funzioni generali e che saranno estese successivamente da
ogni classe di gestione del business.
Di seguito si riporta la funzione più semplice, usata per salvare un nuovo documento nel database

```scala
def create(t: T)(implicit writer: BSONDocumentWriter[T]): Future[Unit] =
  this.collection
    .map(_.insert.one(t))
```

Come si vede, il tipo in input è parametrico, per permettere così la creazione di qualsiasi tipo di documento gestito da
ognuna delle classi in quanto, si ricorda, ogni classe della categoria Repository gestirà una diversa collezione dati.

Nel codice che segue si riporta invece la funzione usata per l'estrazione dati da una collezione (utilizzando sempre la
logica parametrica appena spiegata) con la possibilità di filtrare, ordinare o limitare i documenti.

```scala
def readMany(query: BSONDocument, sort: BSONDocument = BSONDocument(), nDocsToRead: Int = -1)(implicit
                                                                                              reader: BSONDocumentReader[T]
): Future[Option[List[T]]] =
  this.collection
    .flatMap(_.find(query).sort(sort).cursor[T]().collect[List](nDocsToRead))
    .map {
      case l: List[T] if l.nonEmpty => Some(l)
      case _ => None
    }
    .recover { case f: Throwable =>
      f.printStackTrace()
      None
    }
```

Svolta questa prima fase di query generali, si sono realizzate le specifiche classi per gestire ogni tipo di collezione.
Per riportare un esempio a titolo dimostrativo, nel codice che segue la funzione appartenente alla classe
GameRepository (che gestisce la collezione dei Game) si occupa di estrarre tutti match in corso tra due utenti.

```scala
def getCurrentGameFromPlayers(players: List[User]): Future[Option[List[Game]]] =
val query = BSONDocument(
  "completed" -> false,
  "players" -> BSONDocument("$all" -> players)
)
val sort = BSONDocument("lastUpdate" -> -1)
readMany(query, sort)
```

La query filtra i risultati ottenuti dal database, selezionando solo i Game "non completati" che hanno come giocatori
quelli passati in input.
Inoltre, la query presenta una logica di ordinamento decrescente, rispetto alla data del match.

Analogamente alla logica appena vista, si sviluppano tutte le altre query necessarie per lo sviluppo del progetto e
specifiche per ogni collezione.

## Controller

Svolta la prima parte (più strutturale) del progetto, il duo ha continuato gli sviluppi dividendo il progetto in due
parti: la prima più logica (backend) e la seconda più di vista (frontend), sviluppando comunque tutte le aree del
sistema in Scala.
Seguendo questa divisione, Marco si è occupato prevalentemente della logica di gioco (gestita nel package dei
controller).
In questa area del progetto, Marco ha lavorato sullo sviluppo degli algoritmi che si attivano in base alle interazioni
dell'utente con il sistema (ad esempio la creazione di un nuovo match o la risposta a una domanda del quiz).
Ogni controller si occupa di un modello identificato nella base dati, gestendo così le interazioni con esso.

Nel codice che segue si riporta uno degli algoritmi più interessanti, sviluppato per la gestione dei round. Come si
legge dalla _scaladoc_ appena sopra la funzione, si parte dall'ottenere l'ultimo round giocato del match (se non esiste
viene creato) ed in seguito si verifica se:

- il primo utente deve ancora giocare;
- il primo utente ha già giocato e si può passare il turno al secondo;
- entrambi gli utenti hanno giocato e di conseguenza va creato il successivo round di gioco.

Ogni volta che l'utente vuole proseguire il match, questa funzione permette di caricare correttamente il turno
successivo, garantendo così la corretta consistenza della sfida.

```scala
/** Metodo per gestire la progressione dei round.
 *
 * Innanzitutto ottiene l'ultimo round relativo al gioco in corso. Se esso non è presente, significa che la partita è
 * appena iniziata e occorre creare il primo round. Al contrario, se l'ultimo round risulta presente, si verifica se
 * è in corso o completato. Nel primo caso si passa al secondo giocatore, nel secondo si crea il round successivo.
 * @return
 * round appena iniziato o in corso
 */
private def manageNewRound(): Option[Round] = {
  val game = GameController.gameOfLoggedUsers

  GameController.getLastRoundByGame
    .map { round =>
      if round.scores.count(_.score == -1) > 0 then
        // Siamo nel mezzo di un round - deve giocare user2
        RoundController.round = round
      round.scores
        .find(_.score == -1)
        .map(_.user)
        .map(nextPlayer => {
          RoundController.player = nextPlayer
          round
        })
      else
      // Devo creare il round successivo - inizia a giocare firstPlayer
      game.map(g => initializeNewRound(g.id, g.players.head, round.numberRound + 1))
    }
    .getOrElse {
      // Devo creare il primo round - inizia a giocare firstPlayer
      game.map(g => initializeNewRound(g.id, g.players.head))
    }
}
```

Di seguito si riporta un altro estratto di codice, appartenente alla classe RoundController, usato per calcolare il
punteggio parziale di un utente sulla base dei round giocati fino a quel momento.

```scala
/** Metodo per calcolare il punteggio parziale di un utente sulla base dei round giocati fino a quel momento.
 * @param user
 * lo [[User]] di cui si vuole conoscere il punteggio
 * @param game
 * il [[Game]] a cui si riferiscono i round per il calcolo del punteggio. Se non viene passato, si considera la
 * partita corrente.
 * @return
 * il punteggio parziale dell'utente. Se non ci sono round giocati, ritorna [[0]]
 */
def computePartialPointsOfUser(user: User, game: Game = null): Int =
val allRounds = game match
case null
=> Await.result(roundRepository.getAllRoundsByGame(GameController.gameOfLoggedUsers.orNull), 5.seconds)
case _ => Await.result(roundRepository.getAllRoundsByGame(game), 5.seconds)

allRounds
  .getOrElse(List.empty)
  .flatMap(_.scores) // trasforma la lista di Round in lista di Score
  .filter(_.user.username == user.username) // filtra solo le Score dell'utente in input
  .filter(_.score != -1) // esclude i valori -1 (round non ancora giocato dall'utente)
  .foldRight(0)(_.score + _) // calcola il punteggio per accumulazione
```

Si vuole riportare tale implementazione poiché ritenuta interessante, specie per mostrare l'uso di alcune funzionalità
avanzate di Scala che hanno permesso di effettuare pattern matching sull'estrazione dei round tramite la logica di
business e in seguito il filtraggio dei dati secondo le regole che seguono:

- estrazione di una lista di punteggi, partendo dalla lista dei loro relativi round;
- estrazione dei soli punteggi appartenenti all'utente di interesse;
- esclusione dei punteggi che hanno un valore pari a -1, poiché hanno significato di _"round non ancora giocato
  dall'utente"_;
- calcolo del punteggio per accumulazione dei dati rimasti.

Infine, si vuole mostrare un esempio di come è stato possibile sfruttare il pattern matching per gestire una porzione
della logica di business in modo chiaro e conciso.
Nel codice che segue si riporta un caso d'uso del pattern matching, sfruttato per sviluppare la gestione del login
dell'utente.
In base al risultato ottenuto, in seguito all'interazione tra utente e finestra di login, si procede a eseguire elementi
il ramo corrispondente:

- se il valore estratto è una lista contenente i due utenti che stanno provando a fare login, allora quest'ultimo è
  andato a buon fine si viene reiderizzati alla Dashboard.
- se si ottiene qualsiasi altro valore, allora le credenziali di login sono errate e viene segnalato all'utente tramite
  un messaggio.
- se non si ottiene un risultato, allora l'utente ha chiuso la finestra di login, senza quindi provare ad accedere.

```scala
// Gestione pulsante per andare alla dashboard
private val playBtn: Button = craftButton("DASHBOARD")
playBtn.onAction = _ => {
  LoginComponent().showAndWait() match
  case Some(List(u1: User, u2: User))
  if UserController.authenticateUsers(List(u1, u2))
  =>
  changeScene(scene.get(), DashboardScene())
  case Some(_)
  => // l'utente ha inserito dei dati che non hanno soddisfatto i criteri di verifica
  showSimpleAlert(AlertType.Error, errorMsg)
  case None => // l'utente ha chiuso il pannello di login
}
```

In conclusione, sfruttando le sintassi del linguaggio funzionale di Scala, è stato possibile creare un progetto
scalabile che risponde alle esigenze dei requisiti iniziali in modo efficiente e affidabile.
L'uso di una corretta logica funzionale ha contribuito alla realizzazione di un codice conciso, modulare e facilmente
testabile.

[Torna all'implementazione](../5-implementazione.md)
