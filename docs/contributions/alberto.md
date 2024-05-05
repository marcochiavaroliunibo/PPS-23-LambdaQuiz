# Implementazione

## Model
Il modello dei dati dell'applicazione è stato progettato e implementato in Pair programming con il mio compagno di progetto. Abbiamo deciso di implementare ogni entità mediante una `case class`, ad eccezione delle categorie, per le quali si prestava meglio un `enum` di Scala 3.

La scelta di usare `case class` è stata dettata dalla necessità di avere delle classi immutabili, in modo da garantire la coerenza dei dati e la facilità di gestione. Inoltre, ciò ha permesso di sfruttare al meglio le funzionalità di Scala, come il pattern matching e la destrutturazione, nonché la generazione automatica dei metodi `equals` e `toString`, rendendo il codice più leggibile ed elegante.

Alcune entità del modello avevano bisogno di essere salvate e recuperate da un database documentale, facendo nascere l'esigenza di introdurre un modo per serializzare e deserializzare tali oggetti. A tal scopo, grazie anche alla libreria `reactivemongo` scelta per l'interfacciamento con il database, ho implementato un meccanismo di conversione da e verso BSON totalmente trasparente allo sviluppatore. Più nel dettaglio, nel contesto del companion object delle entità `User`, `Score`, `Round`, `Question` e `Game` (ovvero quelle che necessitavano di essere salvate nel database), sono stati definiti due `implicit object`s: uno per la lettura e uno per la scrittura dei dati nella base di dati. Tali oggetti implementano due specifici metodi della libreria suddetta che si occupano di gestire il processo di serializzazione e deserializzazione specifico sul tipo di dato in questione. Il fatto di definire questi oggetti come impliciti consente di avere un processo di conversione automatico e trasparente, sfruttando il meccanismo degli _implicits_ di Scala.
```scala
object Score {
  implicit object ScoreReader extends BSONDocumentReader[Score] {
    // serializzatore
    def readDocument(doc: BSONDocument): Try[Score] =
      for
        user <- doc.getAsTry[User]("user")
        score <- doc.getAsTry[Int]("score")
      yield Score(user, score)
  }

  implicit object ScoreWriter extends BSONDocumentWriter[Score] {
    // deserializzatore
    override def writeTry(score: Score): Try[BSONDocument] =
      for
        user <- Try(score.user)
        score <- Try(score.score)
      yield BSONDocument(
        "user" -> user,
        "score" -> score
      )
  }
}
```
Come esempio di quanto appena descritto, riporto il companion object della classe `Score`, in quanto si presta molto bene a mostrare le caratteristiche rilevanti di questa scelta implementativa. In particolare, è possibile notare come i due oggetti impliciti `ScoreReader` e `ScoreWriter`  definiscano al loro interno le procedure per effettuare la conversione, con tutte le specificità del tipo di dato in questione. Inoltre, è interessante notare come sia stato possibile sfruttare il potente _for comprehension_ di Scala per gestire il processo in maniera molto elegante e concisa. 

Le caratteristiche di trasparenza ed eleganza di questo approccio possono essere chiaramente apprezzate guardando la prima linea della for comprehension, all'interno del metodo `readDocument`. Qui, è possibile vedere la facilità con la quale si riesce a estrarre l'entità `User` da un documento BSON. Infatti, è necessario semplicemente chiamare il metodo `getAsTry`, specificando il tipo del dato che ci si aspetta. Dal momento che anche la classe `User` contiene lo stesso meccanismo di conversione, il processo di estrazione è completamente trasparente e automatico. Inoltre, è interessante notare come sia stato possibile gestire il caso in cui l'estrazione non vada a buon fine, restituendo un `Try` che rappresenta il fallimento dell'operazione. Questo approccio ha permesso di scrivere codice molto più leggibile e robusto, rispetto a quello che sarebbe stato necessario in Java.


## View
In generale, quasi tutta la parte relativa all'interfaccia grafica è stata realizzata da me. In particolare, ho interamente provveduto a scegliere il framework da utilizzare (ScalaFX), a definire l'architettura della view e a implementare la maggior parte delle classi all'interno dei package `it.unibo.pps.view.scenes` e `it.unibo.pps.view.components`.

Alcuni aspetti implementativi interessanti, la cui realizzazione è stata grazie alle potenzialità di Scala, sono:
- **Ereditarietà**: tutte le classi all'interno dei package `scenes` e `controllers` sono state definite in funzione di un particolare componente grafico di ScalaFX. La caratteristica di Scala di essere coinciso e poco verboso, ha permesso di costruire classi che rappresentano una personalizzazione del componente grafico base, in maniera molto trasparente e leggibile. È stato possibile accedere e modificare il valore dei campi ereditati come se fossero stati definiti all'interno della classe stessa, rendendo il processo di sviluppo molto più agevole e il codice più comprensibile. 
  ```scala
  /** Componente grafico per la visualizzazione del titolo di gioco con un aspetto estetico accattivante. */
  class GameTitle extends Text("LambdaQuiz") {
    font = new Font("Verdana Bold", 75)
    fill = new LinearGradient(endX = 0, stops = Stops(Orange, DarkOrange))
    alignmentInParent = Pos.Center
    margin = Insets(20, 0, 0, 0) 
  }
  ```
  Nell'esempio di codice sopra, la classe `GameTitle` estende la classe `Text` di ScalaFX, definendo un titolo di gioco personalizzato. In questo modo, è stato possibile definire un titolo con un aspetto estetico accattivante, andando ad agire solo sugli attributi di interesse.

- **Classi anonime**: in alcuni casi, per definire il comportamento di un componente grafico, è stato necessario creare delle classi anonime. Questa funzionalità di Scala ha permesso di definire il comportamento del componente direttamente all'interno del codice, senza dover creare una classe apposita. Questo ha reso il codice più breve e leggibile, evitando di dover creare classi che sarebbero state utilizzate solo in un contesto specifico.
  ```scala
  /** Lambda per la creazione di un componente grafico di tipo Text con un testo ed un font specifici. */
  val getInfoText: String => Text = new Text(_) {
    font = new Font("Roboto", 14)
    textAlignment = TextAlignment.Center
  }
  ```
  In questo esempio di codice è possibile vedere due aspetti interessanti del linguaggio. In primo luogo, la definizione di `getInfoText` che mostra molto bene l'uniformità dei tipi di Scala. Infatti, è stato possibile attribuire ad una `val` una funzione lambda in modo molto naturale. In secondo luogo, la definizione di una classe anonima che estende `Text` di ScalaFX. Qui è interessante come sia possibile passare un parametro al costruttore della classe anonima, in modo molto conciso e leggibile.

- **Operatori sulle collezioni**: in svariati punti del codice è stato necessario lavorare con delle collezioni di dati. Scala mette a disposizione una serie di operatori che permettono di manipolare le collezioni in maniera molto più concisa rispetto a Java. Questo ha permesso di scrivere codice più leggibile e di ridurre la quantità di codice necessaria per ottenere lo stesso risultato. Inoltre, in questo modo ci si è potuti attenere al paradigma funzionale, che è uno dei punti di forza di Scala.
  ```scala
  /** Lista dei pulsanti utilizzati per mostrare e selezionare le risposte alle domande */
  val answersButtons = QuestionController.getQuestion.map(_.answers.zipWithIndex.map { case (answer, index) =>
    val btnColors = getAnswerBtnColor(index)
    val button = craftButton(answer, btnColors)
    button.onAction = e => answerQuestion(index)
    button
  })
  ```
  Nell'esempio di codice sopra, è possibile vedere come sia stato possibile creare una lista di pulsanti, ognuno associato a una risposta, in modo molto conciso e leggibile. In sostanza, si è fatto uso dell'operatore `map` per trasformare una collezione di stringhe in una lista di Button. Scendendo più nel dettaglio, la lista di stringhe, prima di essere trasformata, è stata combinata con un numero intero, in modo da poter associare a ogni pulsante un indice univoco relativo al numero della risposta. Successivamente, l'operatore `map` insieme al pattern matching, ha permesso di creare un pulsante per ogni risposta, associando a ognuno di essi un comportamento specifico. Riassumendo, l'uso degli operatori sulle collezioni, combinati con il pattern matching, ha permesso di scrivere codice molto più conciso, leggibile ed elegante, rispetto a quello che sarebbe stato necessario in Java.

Sempre in relazione all'interfaccia grafica, ho interamente sviluppato l'object `UIUtils` che contiene metodi di utilità per la gestione dell'interfaccia grafica. Nell'ottica del principio DRY, tale oggetto contiene una serie di procedure che vengono utilizzate in più parti del codice, rendendole accessibili staticamente a qualunque classe ne abbia bisogno. In questo file sono state applicate molteplici tecniche di programmazione specifiche di Scala, come il currying, il pattern matching e l'uso di funzioni lambda. In questo frangente, come aspetto implementativo interessante, viene riportato l'utilizzo del currying:
```scala
/** Verifica se i campi di input per il login sono validi. */
def areLoginInputsValid(usernameFields: TextField*)(passwordFields: TextField*): Boolean =
  fieldsAreCompliant(usernameFields.appendedAll(passwordFields)) && fieldsAreDistinct(usernameFields)
```
Il codice qui sopra mostra come è stato possibile separare concettualmente i parametri del metodo `areLoginInputsValid` in maniera molto elegante e concisa, grazie all'utilizzo del currying. In particolare, è possibile notare come i parametri relativi agli username e alle password siano stati separati in due gruppi distinti, in modo da poterli passare al metodo in maniera indipendente.


## Testing
Al testing dell'applicazione hanno contribuito entrambi i membri del team, man mano che portavano a termine i compiti assegnati. Per quanto riguarda la struttura dei test, essa è stata definita da me, a partire dallo stile di scrittura degli stessi. 

In particolare, ho optato per lo stile di testing _FlatSpec_ di ScalaTest, in quanto ritenuto il più adatto per il tipo di applicazione che stavamo sviluppando. Esso permette di scrivere test in modo intuitivo e idiomatico, sfruttando le funzionalità di Scala per rendere il codice più leggibile e conciso. 

Per quanto riguarda l'architettura dei test, ho messo in piedi una struttura in grado di orchestrare il processo di esecuzione degli stessi. Più nel dettaglio, il punto di ingresso è la classe `TestOrchestrator` che estende il trait `BeforeAndAfterAll`. Ciò, ha permesso di sfruttare il metodo `beforeAll`  per inizializzare il database prima dell'esecuzione dei test, e il metodo `afterAll` per chiudere la connessione alla base dati al termine di tutto. In più, è stato sfruttato il metodo `nestedSuites` per specificare la lista di classi di test da eseguire. Questo approccio ha permesso di avere un controllo molto fine sul processo di esecuzione dei test, garantendo che il contesto di esecuzione fosse sempre lo stesso.

Per consentire alla suddetta architettura di funzionare correttamente, è stato necessario marcare tutte le classi di test con l'annotazione `@DoNotDiscover`. Questo perché, altrimenti, ScalaTest avrebbe cercato di eseguire tutti i test presenti nel progetto, anche se essi venivano già mandati in esecuzione dalla classe orchestratrice.


## Build Automation
La scelta di utilizzare SBT come strumento di build automation è stata presa e condivisa da entrambi i membri del team. Io mi sono occupato di scrivere il file di configurazione `build.sbt`, in cui sono state definite le dipendenze del progetto e le regole per determinare la corretta versione delle librerie usate in base al sistema operativo e all'architettura sottostante.

Per quanto riguarda la creazione del _fat JAR_, ho scelto di utilizzare il plugin `sbt-assembly` per creare un eseguibile contenente tutte le dipendenze necessarie per l'esecuzione dell'applicazione.


## Continuous Integration
La configurazione del processo di Continuous Integration è stata anch'essa realizzata da me. In particolare, ho scelto di utilizzare GitHub Actions come servizio di CI/CD, in quanto integrato direttamente con il repository su GitHub e molto semplice da configurare. Il file di definizione del _workflow_ è stato scritto in YAML e si trova nella directory `.github/workflows/lambdaquiz-ci.yml`.

Il processo di CI è stato configurato in modo da eseguire una serie di passaggi automatici a ogni _push_ sul branch `main`. Sono stati definiti due _jobs_ principali: uno per la compilazione e il testing del codice, e uno per la verifica della qualità di esso. L'ordine di esecuzione imposto prevede che prima venga eseguito il _job_ `quality` e successivamente `tests`. Così facendo, la compilazione e il testing del codice avverranno solo ed esclusivamente se lo stesso rispetta i criteri di qualità definiti.

Affinché i _jobs_ riuscissero a portare a termine il loro compito, è stato necessario prevedere una fase di setup di Java 17 e di SBT. Questo è stato possibile grazie all'uso di _actions_ predefinite di GitHub, che permettono di configurare l'ambiente di lavoro in modo molto semplice e veloce. Inoltre, è stato necessario installare il plugin SBT per `scalafmt`, in modo da poter eseguire i test di qualità del codice. Questo plugin è stato installato tramite il file `project/plugins.sbt`, che in generale contiene tutte le definizioni dei plugin necessari per il corretto funzionamento del progetto.


## Documentazione
Abbiamo entrambi contribuito a documentare il progetto, in particolare mediante l'uso di Scaladoc. Durante il processo, si è cercato di documentare il codice in modo esaustivo, con lo scopo di rendere più agevole la comprensione del funzionamento delle classi e dei metodi.

Per quanto riguarda la suddivisione del lavoro, il membro del team Marco ha scritto la documentazione relativa ai package `controllers` e `model`, mentre il membro Alberto si è occupato di documentare tutto il testo, ovvero `view`, `business`,  e tutto ciò che riguarda i test.
