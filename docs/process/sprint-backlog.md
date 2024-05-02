# Sprint Backlog

Questo documento contiene la lista delle attività che il team si impegna a completare durante lo sprint. Questa lista è creata durante la Sprint Planning, con possibilità di aggiornamenti in itinere.

Per ogni attività, il team deve stimare il tempo necessario per completarla. Questa stima deve essere fatta in ore e deve essere il più precisa possibile. Si considera come unità di misura l'ora di lavoro effettivo, quindi non si considerano pause, interruzioni o attività non correlate allo sprint. Inoltre, per indicare un'intera giornata di lavoro, si considerano 8 ore. Ciò significa che un'attività che richiede 2 giorni di lavoro, deve essere stimata in 16 ore.

Di seguito verranno elencate le diverse iterazioni effettuate, comprese di date di inizio e fine e delle attività svolte in ciascuna di esse, compresa la stima della loro durata in ore o giorni lavorativi. Inoltre, sarà presente una breve retrospettiva, con l'obiettivo di:
- effettuare una valutazione sul lavoro svolto,
- evidenziare le eventuali difficoltà incontrate,
- discutere sulle possibili variazioni rispetto al piano iniziale.

## Sprint 1 
#### 06-03-2024 - 11-03-2024

| Attività sul branch `report`                                                    | Durata |
|------------------------------------------------------------|--------|
| Creazione repo e inizializzazione progetto                 | 2h     |
| Studio approfondito del linguaggio Scala 3                 | 6d     |
| Creazione modello dei dati                                 | 2d     |
| Creazione versione embrionale della GUI                    | 3d     |
| Configurazione di scalafmt                                 | 1h     |
| Scrittura dei primi test e studio della libreria scalatest | 6h     |

### Retrospettiva
Durante questo sprint ci sono state alcune difficoltà relative alle tecnologie utilizzate, che hanno portato le attività a durare più tempo del previsto. Ad esempio, ci siamo accorti che alcune librerie inizialmente scelte per il progetto, non funzionavano su Scala 3. Abbiamo quindi dovuto trovare delle alternative e riscrivere il codice fino a quel momento sviluppato. Inoltre, ci siamo trovati ancora poco confidenti con Scala, con SBT, e con le librerie utilizzate. Ciò ha impattato sulla produttività in quanto eravamo continuamente costretti a consultare le documentazioni. Auspichiamo che da qui in poi riusciremo a diventare sempre più sciolti e riuscire a massimizzare il lavoro svolto.

## Sprint 2
#### 12-03-2024 - 17-03-2024

| Attività sul branch `report`                                            | Durata |
|----------------------------------------------------|--------|
| Implementazione della schermata di login nella GUI | 4h     |
| Implementazione della dashboard                    | 2d     |
| Miglioramento dell'interazione con il database     | 4h     |
| Piccola revisione del modello                      | 2h     |
| Piccolo refactoring                                | 1h     |

### Retrospettiva
Anche questo secondo sprint è stato caratterizzato da una minore produttività per via della difficoltà nell’uso delle nuove tecnologie. Rispetto alla settimana precedente ci sono stati dei miglioramenti e ora ci sentiamo più confidenti sia con Scala, sia con le librerie di terze parti. 

Man mano che la modellazione continuava, ci siamo accorti che l’iniziale struttura del modello non era completa o non rappresentava in maniera appropriata alcune entità. Per questo motivo, l’ultima parte dello sprint ha visto una piccola revisione del modello e la riscrittura del codice che lo sfruttava.

## Sprint 3
#### 18-03-2024 - 23-03-2024

| Attività sul branch `report`                                                      | Durata |
|--------------------------------------------------------------|--------|
| Refactoring della view                                       | 4h     |
| Refactoring dei test esistenti                               | 4h     |
| Ampliamento dei test esistenti                               | 1d     |
| Creazione della schermata di gioco che visualizza le domande | 2d     |

### Retrospettiva
Questo terzo sprint è stato caratterizzato dall’aver raggiunto una versione del gioco parzialmente funzionante, la quale permetteva di avviare l’applicazione, registrare un utente, effettuare il login di due giocatori e visualizzare l’eventuale partita in corso. Ciò ha consentito di interagire con il sistema in maniera meno banale rispetto al passato e in questo contesto siamo stati in grado di rilevare e correggere alcuni bug in diverse sezioni del sistema.

## Sprint 4
#### 24-03-2024 - 29-03-2024

| Attività sul branch `report`                                             | Durata |
|-----------------------------------------------------|--------|
| Aggiustamenti grafici nella dashboard               | 1d     |
| Report e Ranking page (prima versione)              | 2d     |
| Inserimento domanda a DB                            | 1h     |
| Possibilità di creare query usando funzione di Sort | 2h     |

### Retrospettiva
In questo sprint il membro del team Alberto Spadoni non ha potuto partecipare al completamento delle attività previste in quanto assente per malattia. Per questo motivo, il membro Marco Chiavaroli ha proceduto in autonomia per tutta la durata dello sprint.

Questo sprint è caratterizzato dall’aver raggiunto una prima versione (usabile) dell’area delle statistiche, in cui ogni utente può visualizzare i dati sulle proprie partite giocate e il suo posizionamento globale (quest’ultimo punto è stato inizialmente considerato come requisito facoltativo). Inoltre gli ulteriori aggiustamenti sulla grafica della dashboard consentono ora di visualizzare i box colorati per ogni domanda indovinata o errata durante una partita.

Le pagine di Report e Ranking mancano di molta ottimizzazione, sia grafica che di tempi di caricamento, che sono state rimandate al prossimo sprint.


## Sprint 5
# DATE DA VERIFICARE (05/04 - 10/04)
#### 30-03-2024 - 04-04-2024 

| Attività sul branch `report`                                                                                       | Durata |
|-----------------------------------------------------------------------------------------------|--------|
| Miglioramenti grafici in varie parti dell’app                                                 | 4h     |
| Gestione del lungo tempo di caricamento delle statistiche e classifica globale                | 6h     |
| Aggiunta schermata di caricamento quando si accede alle statistiche e alla classifica globale | 2h     |
| Sistemazione della visualizzazione delle domande                                              | 4h     |
| Refactoring generale di tutti i componenti nel package view                                   | 1d     |
| Aggiunta scaladoc in tutto il package view                                                    | 1d     |

### Retrospettiva
Questo sprint si è chiuso con una versione dell’applicazione completa e funzionante in tutte le sue componenti. Dal momento che tutti i requisiti obbligatori sono stati implementati, si è deciso di fermare il processo di sviluppo con lo scopo di aggiungere nuove funzionalità, per concentrarsi sul refactoring e miglioramento del codice già presente, compresa la suite di test.

## Sprint 6
# DATE DA VERIFICARE (11/04 - 16/04)
#### 05-04-2024 - 10-04-2024

| Attività sul branch `report`                                                                                       | Durata |
|-----------------------------------------------------------------------------------------------|--------|
| Refactoring generale di tutti i componenti del package controller                             | 1d     |
| Refactoring generale di tutti i componenti dei package model e business                       | 1d     |
| Aggiunta scaladoc in tutto il package controller                                              | 6h     |
| Aggiunta scaladoc in tutto il package model e business                                        | 1d     |
| Estrazione domande casuali senza ripetizioni per la stessa partita                            | 2h     |

### Retrospettiva
Questo sprint, come preventivato alla fine del precedente, è stato interamente dedicato al miglioramento del codice già presente. In particolare, si è passati in rassegna ogni singolo file nei package `controller`, `model` e `business`, andando non solo a effettuare un refactoring di tutte le parti che potevano essere migliorate ma andando anche a documentare ogni classe, metodo, object e variabili significative. Il processo di refactoring ci ha aiutato a mettere ancora di più in pratica le potenzialità di Scala. Infatti questa fase ha visto la riscrittura di porzioni di codice in modo che sfruttasse il più possibile la programmazione funzionale e le buone tecniche di programmazione apprese durante il corso.

Non è stato però possibile mettere mano alla suite di test, come inizialmente previsto. Tale attività è stata rimandata al prossimo sprint.

## Sprint 7
# DATE DA VERIFICARE (17/04 - 22/04)
#### 11-04-2024 - 16-04-2024

| Attività sul branch `report`                                                                    | Durata |
|----------------------------------------------------------------------------|--------|
| Refactoring e ampliamento della suite di test                              | 2d     |
| Risoluzione di alcuni problemi relativi ai test asincroni                  | 2d     |
| Creazione entità per rappresentare le statistiche, con relativo controller | 6h     |

### Retrospettiva
In questo sprint è stato principalmente trattato l’aspetto relativo ai test. In particolare, è stata modificata l’architettura esistente e si è predisposto un sistema per la creazione dei dati su cui effettuare i test prima che essi vengano effettivamente eseguiti. A fronte di quest’ultima modifica, i test esistenti sono stati adattati in modo da sfruttare correttamente la nuova architettura e si è approfittato per ampliare la suite stessa.
Infine, si è dovuta impiegare una certa quantità di tempo per risolvere un problema che causava il fallimento di alcuni test in maniera non deterministica.

## Sprint 8
# DATE DA VERIFICARE (23/04 - 28/04)
#### 17-04-2024 - 22-04-2024

| Attività sul branch `report`                                                 | Durata |
|------------------------------------------------------------------------------|--------|
| Creazione dello scheletro della relazione                                    | 2h     |
| Inizializzazione della pagina su Github Pages per ospitare la documentazione | 1h     |
| Scrittura capitolo introduttivo                                              | 6h     |
| Scrittura capitolo relativo al processo di sviluppo                          | 1d     |
| Scrittura capitolo relativo ai requisiti del progetto                        | 2d     |
| Scrittura capitolo relativo al design architetturale                         | 4h     |
| Bozza del capitolo relativo al design di dettaglio                           | 6h     |

| Attività sul branch `main`                                                      | Durata |
|---------------------------------------------------------------------------------|--------|
| Aggiunta di alcuni test per le categorie di domande                             | 4h     |
| Aggiunta dei plugins `scalafmt` e `wartremover` in sbt per la quality assurance | 6h     |

### Retrospettiva
Questo sprint è stato dedicato alla preparazione della relazione finale e alla creazione di una pagina su Github Pages per ospitare la documentazione del progetto. In particolare...

## Sprint 9
# DATE DA VERIFICARE (29/04 - 04/05)
#### 23-04-2024 - 28-04-2024

| Attività sul branch `report`                            | Durata |
|---------------------------------------------------------|--------|
| Revisione capitolo introduttivo                         | 4h     |
| Revisione capitolo sul processo di sviluppo             | 4h     |
| Scrittura capitolo sulle tecnologie                     | 1d     |
| Revisione completa del capitolo sul design di dettaglio | 2d     |
| Disegno di alcuni diagrammi per la relazione            | 6h     |

| Attività sul branch `main`                                                                      | Durata |
|-------------------------------------------------------------------------------------------------|--------|
| Refactoring dell'intera codebase per risolvere gli errori ed i warning sollevati da wartremover | 4h     |
| Migliorata la gestione degli ExecutorContext impiegati per l'esecuzione delle Futures           | 6h     |
| CI aggiornata per verificare la qualità dle codice con scalafmt e wartremover                   | 4h     |

### Retrospettiva

## Sprint 10
# DATE DA VERIFICARE (05/05 - 10/05)
#### 29-04-2024 - 04-05-2024

### Retrospettiva
