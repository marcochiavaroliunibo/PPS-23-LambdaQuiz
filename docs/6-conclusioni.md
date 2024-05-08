# Conclusioni

Al progetto terminato, i membri del team possono ritenersi ampiamente soddisfatti del lavoro svolto e dei risultati
ottenuti. Sono stati raggiunti tutti gli obiettivi prefissati durante la fase di analisi e progettazione, realizzando
un'applicazione funzionante e rispettando i requisiti individuati. Si è riusciti a produrre una documentazione di
progetto dettagliata e completa, la quale non solo ha permesso di mantenere una visione chiara sul lavoro svolto, ma
consentirà anche a futuri sviluppatori di comprendere il funzionamento del sistema e di apportare eventuali modifiche o
miglioramenti. Inoltre, il team ha avuto modo di acquisire nuove competenze e conoscenze, sia dal punto di vista tecnico
che da quello organizzativo, le quali si sono rivelate fondamentali per il successo del progetto.

## Retrospettiva

Sebbene il progetto si sia concluso nel migliore dei modi, ci sono stati dei momenti di difficoltà e di sfida che il
team ha dovuto affrontare, soprattutto nelle fasi iniziali di sviluppo. In particolare, l'inesperienza dei membri nel
lavorare con tecnologie nuove e complesse ha richiesto un notevole impegno e una curva di apprendimento ripida.
Tuttavia, grazie alla determinazione e alla collaborazione di entrambi, si è riusciti a superare gli ostacoli e a
portare sempre a termine le attività entro le scadenze prefissate. Inoltre, la gestione del tempo e delle risorse è
stata un aspetto critico, che ha richiesto una costante attenzione e un'organizzazione accurata delle attività. In
questo senso, l'adozione di metodologie agili si è rivelata estremamente utile, consentendo al team di adattarsi
rapidamente ai cambiamenti e di mantenere un ritmo di lavoro costante e produttivo.

### Cambiamenti in corso d'opera

Rispetto alla proposta iniziale di progetto, il sistema ha subito alcune importanti modifiche ai requisiti.
Originariamente, infatti, il team si era prefissato di realizzare un gioco di quiz **online** e **distribuito**, il
quale avrebbe consentito ai diversi giocatori di partecipare alle partite da dispositivi diversi e in contemporanea.
Questo tipo di applicazione avrebbe portato con se una serie di funzionalità diverse rispetto a quelle presenti nella
versione attuale del gioco, come ad esempio una chat tra i giocatori o il fatto di avere un server centrale che gestisce
le partite.

Tuttavia, a causa delle limitazioni di tempo e delle difficoltà tecniche riscontrate, si è deciso di ridimensionare il
progetto e di concentrarsi su una versione più semplice e locale del gioco. Questo cambiamento ha permesso di facilitare
l'implementazione del sistema e di concentrarsi sugli aspetti di programmazione funzionale e avanzata in Scala,
piùttosto che su aspetti di programmazione distribuita, architetture a micro-servizi, e in generale tutto cio che
riguarda la comunicazione tra diversi nodi.

L'unica caratteristica in comune con la versione distribuita è l'utilizzo di un database MongoDB per la memorizzazione
dei dati degli utenti e delle partite giocate. Questo ha permesso di rispettare i requisiti di persistenza dei dati,
dando la possibilità ai giocatori di salvare i propri progressi e di riprendere le partite in un secondo momento.

### Teamwork

I membri del team non si conoscevano prima dell'inizio del progetto ma sono riusciti a instaurare un clima di
collaborazione e fiducia reciproca, lavorando insieme in maniera sinergica e coesa. Questo ha permesso di affrontare le
difficoltà e le sfide che si sono presentate, risolvendo i problemi in modo rapido ed efficace e mantenendo sempre un
atteggiamento positivo e proattivo.

Tutto il lavoro necessario alla realizzazione del progetto è stato svolto da remoto, utilizzando strumenti di
comunicazione e collaborazione online. Questo ha permesso al team di operare in modo flessibile e autonomo, organizzando
le attività in base ai propri impegni e alle proprie esigenze. Questa modalità di lavoro è stata assolutamente azzeccata
in quanto gli orari lavorativi dei membri si sono rivelati essere differenti e spesso non coincidenti. Ciò ha portato ad
alcune difficoltà di comunicazione e coordinamento, soprattutto nei giorni di inizio e fine sprint, ma grazie
all'impegno e alla disponibilità di entrambi si è riusciti a superare tali ostacoli e a portare a termine il progetto
con successo.

### Considerazioni personali

Di seguito, alcune considerazioni personali in merito allo strumento di gestione del progetto utilizzato, JIRA:

> #### Alberto Spadoni
>
> Premetto che non conoscevo e quindi non avevo mai usato JIRA prima di questo progetto.
> Proprio per questo, non appena Marco mi ha proposto di sfruttare quello come tool di gestione delle attività, ho
> accettato con entusiasmo.
> Sono stato spinto dal fatto di imparare un nuovo strumento, che mi sarebbe potuto rivelare utile in un futuro
> lavorativo.
> Complessivamente, posso ritenermi soddisfatto di questa scelta, però, durante l'utilizzo, ho riscontrato alcune
> criticità che, a mio avviso, non fanno di JIRA lo strumento perfetto per questo tipo di utilizzo.
>
> Innanzitutto ho trovato l'interfaccia grafica poco intuitiva e non sempre reattiva.
> Ma più di questo, ho trovato poco pertinenti le varie funzionalità per creare, modificare e gestire le attività.
> Secondo me JIRA è uno strumento che si adatta meglio a contesti più strutturati e complessi e soprattutto che trattano
> di argomenti quali richieste di assistenza da parte degli utenti, tracking di bug e problemi, e così via, piuttosto che
> per uno sviluppo software con metodologie SCRUM.
>
> In conclusione, nonostante queste criticità, sono comunque soddisfatto di aver avuto l'opportunità di utilizzare JIRA.
> Tuttavia, se dovessi tornare indietro, probabilmente sceglierei un altro strumento per la gestione del progetto.

## Sviluppi futuri

Sebbene il progetto sia giunto al termine, ci sono ancora molte possibilità di sviluppo e di miglioramento per il gioco
LambdaQuiz. Alcune delle funzionalità che potrebbero essere implementate in futuro includono:

- Aggiunta di una schermata che a fine partita indica il vincitore e il punteggio ottenuto da entrambi i giocatori;
- Inserire la possibilità per gli utenti di modificare il proprio profilo, cambiando il nome utente o la password;
- Implementare una modalità di pgioco in cui i giocatori devono rispondere alle domande entro un limite di tempo
  prefissato;
- Aggiungere delle statistiche globali, che comprendono, per ogni giocatore:
    - il numero di partite giocate e il punteggio medio ottenuto,
    - il numero di risposte corrette e sbagliate,
    - una Top 3 delle categorie in cui il giocatore ha ottenuto il punteggio più alto.

[Indietro](5-implementazione) | [Torna alla Home](index.md)