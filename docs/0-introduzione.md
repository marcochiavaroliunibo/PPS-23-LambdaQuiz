# Introduzione

## Obiettivi

Il principale obiettivo del progetto in questione è la realizzazione di un'applicazione desktop in Scala che implementi
un gioco di quiz. Scendendo più nel dettaglio, è possibile individuare ulteriori obiettivi, i quali interessano il
processo di sviluppo e la qualità del prodotto. In particolare, si vogliono perseguire i seguenti obiettivi:

- sviluppare l'applicativo sfruttando il più possibile le potenzialità di Scala, adottando il giusto equilibrio tra
  programmazione funzionale e orientata agli oggetti;
- sperimentare una metodologia di sviluppo agile, basata su principi di incremento e iterazione, per garantire una
  maggiore flessibilità e adattabilità ai requisiti del progetto;
- garantire la qualità del codice prodotto, non solo attraverso l'utilizzo di strumenti di analisi statica e di testing,
  ma anche attraverso l'adozione di buone pratiche di programmazione e di design;

## Descrizione del sistema

Il sistema in oggetto si concretizza in un'applicazione Scala che simula un gioco di quiz multi giocatore in modalità
*hot seat* (i giocatori si alternano sullo stesso dispositivo). L'obiettivo principale dello stesso è accumulare il
maggior numero possibile di punti, rispondendo correttamente alle domande proposte.

Più in dettaglio, l'applicativo consente a due concorrenti di sfidarsi in una serie di round, ciascuno composto da tre
domande, selezionate casualmente a partire da una gamma di categorie. Ogni round offre una varietà di categorie di
domande, garantendo una sfida sempre stimolante e coinvolgente per i partecipanti.

L'interfaccia utente intuitiva consente ai giocatori di interagire facilmente con il sistema, sia per rispondere alle
domande che per navigare tra le varie funzionalità offerte.

Oltre alla competizione diretta tra i giocatori, il sistema offre anche la possibilità di
visualizzare statistiche personali e comparative. Gli utenti possono monitorare il numero di partite vinte, perse e lo
stato delle sfide ancora in corso. Inoltre, possono verificare la propria posizione all'interno della classifica
globale, la quale comprende tutti gli utenti del sistema. Questa funzionalità aggiunge un elemento competitivo e
motivante al gioco, incoraggiando i giocatori a migliorare le proprie abilità e a confrontarsi con gli altri
partecipanti.

L’applicativo cerca di rispettare requisiti di gioco ben definiti, di tipo funzionale (quindi che identificano
chiaramente quali funzionalità deve essere a disposizione degli utenti) e di tipo non funzionale. Ogni requisito sarà
descritto con chiarezza nei seguenti capitoli di questa relazione.

[Torna alla Home](index.md) | [Avanti](1-processo_di_sviluppo.md)
