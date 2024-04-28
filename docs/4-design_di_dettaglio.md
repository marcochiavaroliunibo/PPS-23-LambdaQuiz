# Design di dettaglio
Il sistema si divide in 4 package che presentiamo nel dettaglio nei paragrafi che seguono:

## Business
Contiene i componenti che si occupano di gestire connessione e chiusura delle connessioni verso il database Mongo utilizzato come appoggio dati per l'applicativo.
Ogni collezione dati del database è gestita da una classe (ad esempio GameRepository) che si occupa di implementare tutte le chiamate necessarie per estrarre dati, scrivere o modificare.
Inoltre, è implementato il trait Repository, esteso da tutte le classi prima descritte, allo scopo di generalizzare su tutte l'uso delle funzioni chiave (come query per l'inserimento di un nuovo documento al DB, e la ricerca per ID).

## Model
Contiene le classi che rappresentano la logica di business, rispettando quindi la rappresentazione effettuata nel package descritto precedentemente.
Il modello è composto da 6 componenti:
- User: rappresenta il singolo utente registrato al gioco.
- Round: rappresenta un singolo round di gioco, composto da _n_ domande e un punteggio per ognuno dei due giocatori.
- Game: rappresenta la partita creata da due utenti, che decidono di sfidarsi. Il Game è composto da più round di gioco.
- Score: rappresenta il punteggio di un utente, effettuato durante il gioco di un round.
- Question: rappresenta una domanda, contenente testo, risposte (di cui solo una esatta) e categoria di appartenenza.
- Category: lista delle categorie create (ogni round e domanda fanno parte di una singola categoria).

## Controller
Seguendo la logica descritta nei paragrafi precedenti, anche i controller sono stati creati in base ai componenti del sistema, nel rispetto delle regole della tecnica MVC:
- GameController: si occupa di gestire tutte le azioni effettuate dall'utente (tramite l'interfaccia di gioco) che impattano sui dati del Game. Quindi si occupa anche di far uso delle funzioni messe a disposizione dalla logica di business, oltre che modificare glo oggetti del modello corriposndente.
- QuestionController: si occupa di gestire le interazioni con le domande (come estrazione casuale in base alla categoria o gestione della risposta dell'utente).
- RoundController: si occupa di gestire il corretto funzionamento del round, rispettando quindi le logiche di gioco e permettendo così l'alternanza dei round e, nel singolo turno, della mano di gioco tra i due utenti.
- UserController: si occupa di registrazione e login degli utenti tramite l'interfaccia di gioco.
- ReportController: si occupa del calcolo delle statisticge da mostrare all'utente quando questo le richiede tramite l'interfaccia, sia i report globali che i report variabili in base all'utente che ha effettuato il login.

## View
Si occupa di gestire l'interfaccia utente. In particolare, mette a disposizione una serie di utility presenti nel file "UIUtilis", utilizzate per caricare le relative pagine grafiche costuite tramite i due sottopackages descritti di seguito.

### Scenes
Contiene le pagine visualizzate dall'utente, caricando i componenti grafici da visualizzare:
- MenuScene: pagina iniziale, visualizzata all'avvio del sistema. 
- Dashboard: visualizzazione dello stato di una partita, punteggi dei vari round e risultato (parziale o finale).
- QuizScene: pagina di gioco, visualizzazione delle domande con relative risposte.
- ReportScene: pagina delle statistiche utente.
- GlobalRankingScene: pagine delle statistiche generali su tutti gli utenti iscritti al sistema.

### Components
Costituiscono i sotto elementi delle scene e sono usati per comporre in maniera ordinata le stesse.
A titolo di esempio, la pagina di gioco (QuizScene) è stata divisa da una parte alta della pagina in cui viene visualizzata la domanda a cui rispondere ed una parte centrale con le risposte possibile. Quest'ultime sono state separate e implementate in AnswerSpace per gestire in modo più efficace il suo corretto funzionamento.
La stessa cosa è stata svolta anche per la gestione delle popup di registrazione e login degli utenti.

[Indietro](3-design_architetturale.md) | [Torna alla Home](index.md) | [Avanti](5-tecnologie.md)