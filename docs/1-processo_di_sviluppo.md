# Processo di sviluppo

## Metodologia di sviluppo

Lo sviluppo del progetto è avvenuto seguendo i principi della metodologia *Agile*, adottando alcuni aspetti del
framework **SCRUM**. In particolare è stato utilizzato un processo incrementale e iterativo: lo sviluppo è stato diviso
in diverse iterazioni (sprint), ognuna delle quali ha aggiunto nuove funzionalità a quanto già implementato o ha
raffinato quanto già presente.

## Pianificazione

Nel meeting iniziale, oltre a discutere l'idea di progetto, il team ha pianificato il lavoro da svolgere. Tenendo in
considerazione la scadenza del progetto, è stato definito un piano di lavoro composto da 10 sprint, ciascuno della
durata di 6 giorni lavorativi. Ogni sprint, quindi, ha avuto una durata di 48 ore di lavoro per ciascun membro del team.
Sebbene il monte ore totale sia superiore a quello consigliato dalle regole di progetto, il team ha deciso di mantenere
questa pianificazione per garantire una maggiore flessibilità e per poter gestire eventuali imprevisti.

Successivamente, è stata definita la cadenza dei meeting di progetto: almeno due per ogni sprint, uno all'inizio e uno
alla fine. Questo per garantire un controllo costante sull'avanzamento del lavoro e per poter apportare eventuali
correzioni di rotta in caso di necessità. Il meeting di inizio sprint aveva la funzione di definire i task da svolgere e
assegnarli ai membri del team, mentre quello di fine sprint serviva per verificare lo stato di avanzamento del lavoro,
per condividere con il team alcuni dettagli implementativi relativi ai compiti svolti e per discutere eventuali problemi
riscontrati. Oltre a questi due meeting, ritenuti obbligatori, il team si è riservato la possibilità di effettuare un
ulteriore incontro a metà sprint, qualora fosse stato necessario.

Per una migliore gestione e tracciamento del lavoro, è stato utilizzato il software **Jira**. In particolare, sono stati
creati tanti macro-task quanti erano gli sprint previsti, ciascuno dei quali conteneva i task relativi a
quell'iterazione. Essi venivano assegnati ai membri del team durante il meeting di inizio sprint, con l'obiettivo di
completarli entro la fine dello stesso.

## Sprint Backlog

Per tenere traccia in maniera più dettagliata possibile di tutte le attività svolte e della loro durata, il team si è
impegnato a mantenere uno sprint backlog aggiornato costantemente. Più in dettaglio, i membri inserivano le attività da
svolgere subito prima di iniziarci a lavorare, con un livello di astrazione più basso rispetto ai task definiti in Jira.
Successivamente ne stimavano la durata e, una volta completate, segnavano il tempo effettivamente impiegato. Al termine
di ogni iterazione, il backlog veniva arricchito con una breve retrospettiva che aveva l'obiettivo di riportare l'
andamento dello sprint a posteriori, le difficoltà incontrate e le eventuali correzioni da apportare per il futuro.

Lo sprint backlog di questo progetto è visionabile [qui](process/sprint-backlog.md).

## Version Control System

Il team ha adottato un approccio di sviluppo collaborativo, basato sull'utilizzo di un **Version Control System** (VCS)
per tracciare le modifiche al codice sorgente e coordinare il lavoro tra i membri. In particolare, è stato scelto di
utilizzare **Git** come sistema di controllo delle versioni, ospitando il repository su **GitHub**.

Per quanto concerne il branching model, è stato scelto un approccio semplice, che vedeva l'utilizzo del branch `main`
per il codice sorgente e del branch `report` per i file della documentazione di progetto. La scelta di non utilizzare un
branching model più complesso, come ad esempio GitFlow, è stata dettata dalla volontà di mantenere il processo di
sviluppo il più semplice possibile. Inoltre, il team ha ritenuto che un modello di branching più complesso non fosse
necessario, considerando il numero limitato di membri del team. I meeting frequenti e un'attenta pianificazione delle
aree di lavoro hanno permesso di mantenere il repository pulito e di evitare conflitti sui file co-prodotti.

## Testing

Il testing del progetto è stato effettuato mediante l'utilizzo di **ScalaTest**. In particolare, sono stati scritti test
di unità e di integrazione per verificare il corretto funzionamento delle funzionalità implementate. Si è cercato di
scrivere i test parallelamente allo sviluppo delle funzionalità previste, seguendo quanto più possibile il principio del
*Test-Driven Development* (TDD).

La scelta di utilizzare ScalaTest è stata dettata dalla volontà di mantenere il progetto il più possibile all'interno
dell'ecosistema Scala. Inoltre, ScalaTest è uno dei framework di testing più diffusi e supportati dalla community, il
che ha reso più agevole la scrittura dei test e la risoluzione di eventuali problemi riscontrati. Inoltre, la
possibilità di scegliere tra diversi stili di scrittura dei test ha permesso al team di apprezzare ancora di più le
potenzialità e la flessibilità di Scala.

## Build Automation

Per la gestione automatizzata delle dipendenze, della compilazione e dell'esecuzione del progetto, si è scelto di
utilizzare **SBT**. Tale strumento risulta essere perfettamente integrato con l'ecosistema Scala e offre una vasta gamma
di funzionalità che hanno reso più agevole la gestione della build automation.

In particolare, è stato possibile definire in maniera idiomatica e concisa tutte librerie di terze parti utilizzate nel
progetto, gestendo anche le diverse versioni per i vari sistemi operativi.

## Quality Assurance

Per garantire la qualità del codice prodotto, il team ha deciso di utilizzare alcuni strumenti di analisi statica del
codice. In particolare, sono stati impiegati i seguenti tool:

- **ScalaFmt**: che consente di formattare automaticamente il codice sorgente in modo uniforme e coerente. Questo
  strumento è stato utilizzato per applicare uno stile di formattazione comune a tutto il codice prodotto, garantendo
  una maggiore leggibilità e manutenibilità del codice.
- **WartRemover**: per individuare possibili difetti o problemi nel codice Scala. Esso analizza il codice alla ricerca
  di costrutti o pattern noti che potrebbero causare errori o produrre comportamenti indesiderati. Questo strumento è
  stato utilizzato per garantire che il codice prodotto rispettasse le best practices e le convenzioni di Scala.

## Continuous Integration

Per garantire la continua integrità e correttezza del codice durante lo sviluppo, si è scelto di adottare un workflow
basato su **GitHub Actions**. Esso è un servizio di *Continuous Integration* (CI) offerto da GitHub che consente di
automatizzare le attività di build, test e rilascio del software direttamente nel repository GitHub. Utilizzando
Actions, è possibile definire e configurare flussi di lavoro personalizzati per eseguire una serie di azioni o comandi
in risposta a determinati eventi, come il push di nuovi commit o la creazione di pull request.

Nel caso specifico del progetto, è stato definito un flusso di lavoro per eseguire automaticamente le seguenti
operazioni ad ogni push sul branch `main`:

- **Verifica della qualità del codice con ScalaFmt**: in questa prima fase, si sfrutta il plugin `sbt-scalafmt` per
  controllare che tutti i file del progetto siano correttamente formattati. Nel caso in cui ci fosse anche solo un file
  non propriamente formattato, la build fallirebbe;
- **Esecuzione dei test**: questo passaggio viene eseguito solo se l'attività di verifica della qualità va a buon fine e
  consiste nell'esecuzione di tutti i test presenti nel progetto su diverse configurazioni di sistemi operativi (
  Windows, Ubuntu, macOS). In caso di fallimento di uno o più test, la build viene interrotta e il processo restituisce
  un errore;

In entrambe le operazioni appena citate, Github Action esegue prima di tutto la compilazione del codice sorgente e la
build dell'intero progetto. In questo frangente, il tool WartRemover verifica la presenza di eventuali warning o errori
nel codice, facendo fallire il processo in caso di problemi;

[Indietro](0-introduzione.md) | [Torna alla Home](index.md) | [Avanti](2-requisiti.md)
