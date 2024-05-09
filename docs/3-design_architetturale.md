# Design architetturale

Terminata la fase di analisi dei requisiti, si è proceduto con la progettazione dell'architettura del sistema.

Sulla base della natura di applicazione da realizzare, si è scelto di adottare un'architettura che implementasse il
pattern **Model-View-Controller (MVC)**. Questo pattern è stato scelto per garantire una separazione netta tra la logica
di business
e la presentazione dei dati, in modo da rendere il sistema più modulare e manutenibile.

## Architettura del sistema

L'architettura del sistema è composta da tre componenti principali:

- **Model**: rappresenta la struttura dei dati e le operazioni che possono essere eseguite su di essi. In particolare,
  il modello è composto da tutte le entità del dominio di gioco e dalle procedure per implementare la persistenza dei
  dati per mezzo di un database documentale. A questo proposito, il componente in oggetto è provvisto delle classi
  necessarie per la connessione al database e per l'interazione con esso, facendosi carico anche delle operazioni di
  serializzazione e deserializzazione dei dati;

- **View**: si occupa di visualizzare all'utente i dati provenienti dal modello e consente ai giocatori di interagire
  con il sistema. In particolare, la view è formata da tutti gli elementi che, insieme, formano l'interfaccia grafica
  dell'applicazione, i quali includono la logica di gestione delle interazioni con l'utente;

- **Controller**: gestisce le interazioni tra il model e la view. In questo contesto, sono stati previsti tanti
  controller quante sono le entità del dominio di gioco, in modo da organizzare meglio il codice per le varie operazioni
  che possono essere eseguite sui dati. Tali controllers implementano tutta la logica di gioco e quella per gestire i
  processi di autenticazione e registrazione degli utenti.

[Indietro](2-requisiti.md) | [Torna alla Home](index.md) | [Avanti](4-design_di_dettaglio.md)
