# Divisione del lavoro

...

## Marco

Entrando nel dettaglio del lavoro svolto dal singolo membro del team, si riporta di seguito un resoconto delle parti svolte prevalentemente da Marco.

Nella prima fase del progetto è stato fondamentale definire una logica dati su cui gettare le fondamenta della struttura del sistema. Questa parte, è stata svolta con sinergia da entrambi i membri del gruppo con Marco che ha avuto un maggior numero di task focalizzati sulla logica di business e repository dell'applicativo.
Di conseguenza, si è occupato di definire le funzioni che implementano le query per la scrittura, modifica ed estrazione dei dati, gestendo così la comunicazione con la base di dati MongoDB.
Questa parte ha avuto chiaramente risonanza su tutte le funzionalità dell'applicazione, sviluppate in seguito seguendo i pattern funzionali del linguaggio Scala.
Si sottolinea di nuovo come, essendo una parte iniziale del progetto, questa sia stata comunque seguita con l'affiancamento di Alberto, altro membro del team.

Svolta la prima parte (più strutturale) del progetto, il duo ha continuato gli sviluppi dividendo il progetto in due parti: la prima più logica (backend) e la seconda più di vista (frontend), sviluppando comunque tutte le aree del sistema in Scala.
Seguendo questa divisione, Marco si è occupato prevalentemente della logica (gestita nel package dei controller).
In questa area del progetto, Marco ha lavorato sullo sviluppo degli algoritmi che si attivano in base alle interazioni dell'utente con il sistema (ad esempio la creazione di un nuovo match o la risposta a una domanda del quiz).
Ogni controller si occupa di un modello identificato nella base dati, gestendo così le interazioni con esso, come descritto precedentemente nel [paragrafo dedicato](../4-design_di_dettaglio.md#Controller).