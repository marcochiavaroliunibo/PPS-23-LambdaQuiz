# Implementazione

Nel presente capitolo vengono descritte le scelte implementative adottate per la realizzazione del progetto. In
particolare, si descrivono le tecnologie utilizzate, la suddivisione del lavoro e le scelte progettuali adottate, con un
particolare focus sulle tecniche di programmazione funzionale utilizzate.

## Tecnologie

Il progetto è stato sviluppato facendo largo uso di tecnologie moderne e potenti, selezionate per garantire
un'esperienza utente fluida e una gestione efficiente dei dati. Le principali tecnologie impiegate sono descritte nei
paragrafi che seguono.

### Scala

Scala è stato scelto come linguaggio principale di sviluppo per il progetto, e ciò si è dimostrata fondamentale per il
successo e l'efficienza del sistema. Esso, coniugando il paradigma di programmazione orientata agli oggetti con quello
funzionale, offre una sintassi flessibile ed espressiva che rende il codice conciso e altamente leggibile. Questa
combinazione di caratteristiche rende Scala particolarmente adatto per lo sviluppo di applicazioni complesse e
scalabili.

### MongoDB

Nel progetto, è stato scelto MongoDB come sistema di gestione dei dati per la sua flessibilità e scalabilità. Grazie
alla sua struttura orientata ai documenti, consente di memorizzare e interrogare dati in modo personalizzato senza la
necessità di uno schema rigido. Questa caratteristica rende possibile memorizzare dati complessi in modo flessibile e
scalabile.

Per semplificare l'interazione con il database, è stata utilizzata la libreria esterna `reactivemongo` che fornisce
un'interfaccia reattiva e asincrona per comunicare con MongoDB. Essa, sfruttando varie funzionalità di Scala, fornisce
agli sviluppatori la possibilità di implementare le operazioni CRUD in maniera concisa, elegante e in puro stile
funzionale.

### ScalaFX

ScalaFX è una libreria per la creazione di interfacce grafiche in Scala, basata su JavaFX. Essa offre un'interfaccia
semplice e intuitiva per la creazione di GUI, consentendo di sviluppare applicazioni desktop moderne e accattivanti.
ScalaFX è stata principalmente scelta per la sua perfetta integrazione con Scala, che ha permesso di creare
un'interfaccia utente intuitiva e ben strutturata.

Tra i suoi punti di forza, ScalaFX vanta l'utilizzo di un paradigma dichiarativo per la definizione delle interfacce
utente, rendendole facili da modificare e aggiornare. Inoltre, supporta la programmazione reattiva, permettendo di
creare GUI dinamiche che si adattano automaticamente ai cambiamenti dei dati sottostanti. Un altro aspetto interessante
delle applicazioni realizzate con ScalaFX è che possono essere eseguite su diverse piattaforme, tra cui Windows, macOS e
Linux, partendo da un unico codice sorgente.

## Tecniche di programmazione funzionale

L'utilizzo di Scala come unico linguaggio di programmazione per il progetto ha permesso di sfruttare a pieno le
potenzialità della programmazione funzionale, sperimentando anche alcune tecniche avanzate messe a disposizione dal
linguaggio. In particolare, sono state sfruttate le seguenti tecniche:

- **Operatori sulle collezioni**: Scala offre una vasta gamma di operatori standard (come `map`, `filter`, `reduce`) che
  possono essere utilizzati sulle collezioni. Essi rendono la manipolazione dei dati più semplice e intuitiva,
  caratterizzata da un approccio dichiarativo alla gestione delle collezioni che consente di evitare la scrittura di
  cicli espliciti. Inoltre, tali operatori possono essere combinati in modo flessibile per ottenere risultati complessi.
- **Immutabilità**: In Scala, l'immutabilità è il default. Ciò significa che un oggetto, una volta creato, non può
  essere modificato. Questo porta a codice più sicuro dal punto di vista del thread e può aiutare a prevenire molti
  errori comuni. Inoltre,
- **Pattern matching**: Il pattern matching di Scala è uno strumento versatile che supera le capacità degli `if`
  e `switch` statement tradizionali. Infatti, permette di confrontare non solo valori, ma anche tipi, e di destrutturare
  oggetti complessi, semplificando la gestione di diversi casi e strutture dati. Questo approccio consente di produrre
  un codice molto espressivo e comprensibile, in perfetto accordo con il paradigma funzionale.
- **Impliciti**: Gli impliciti in Scala sono un meccanismo che permette di estendere le funzionalità delle classi e di
  fornire valori predefiniti per i parametri dei metodi. Possono rendere il codice più pulito e leggibile, migliorare
  l’estensibilità e facilitare l’interoperabilità con altre librerie e linguaggi. Tuttavia, un uso eccessivo può rendere
  il codice difficile da comprendere e da mantenere.

## Suddivisione del lavoro

Durante tutto il progetto si è sempre cercato di dividere il lavoro nella maniera più equa possibile, cercando di
sfruttare al meglio le competenze di entrambi i membri del team ed evitando che uno dei due si trovasse a fare più
lavoro dell'altro. Essendo il gruppo composto da soli due membri, è stato difficile avere delle aree dell'applicazione
interamente attribuibili a un singolo membro. Questo percché c'è stata molta collaborazione tra i membri, pertanto quasi
tutti i componenti sono stati sviluppati da entrambi i membri del team. Tuttavia, è possibile identificare alcune aree
in cui uno dei due membri ha lavorato di più rispetto all'altro. In particolare, Marco Chiavaroli si è occupato
prevalentemente della logica di gioco e dell'interazione tra il modello e il database, mentre Alberto Spadoni ha
lavorato maggiormente sull'interfaccia grafica, sul modello e sui test.

La suddivisione del lavoro, dal punto di vista dei package, è stata la seguente:

- Marco Chiavaroli:
    - `it.unibo.pps`
        - `controller`,
        - `business`
- Alberto Spadoni:
    - `it.unibo.pps`
        - `model`,
        - `view`,
        - `test`

Di seguito, sono riportati i link ai documenti che descrivono il lavoro svolto da ciascun membro del team:

- [contributo di Marco Chiavaroli](contributions/marco.md),
- [contributo di Alberto Spadoni](contributions/alberto.md)

[Indietro](4-design_di_dettaglio.md) | [Torna alla Home](index.md) | [Avanti](6-conclusioni)