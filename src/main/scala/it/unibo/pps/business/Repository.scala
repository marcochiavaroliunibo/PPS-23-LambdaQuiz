package it.unibo.pps.business

import it.unibo.pps.ECHandler
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

import java.util.concurrent.Executors.newFixedThreadPool
import scala.concurrent.{ExecutionContext, Future}

/** Trait che rappresenta un repository generico che definisce le operazioni CRUD di base su un database MongoDB.
  * @tparam T
  *   tipo dell'oggetto da salvare nel database
  */
trait Repository[T]:
  given ExecutionContext = ECHandler.createExecutor

  /** Metodo che restituisce la collezione su cui effettuare le operazioni CRUD.
    * @return
    *   collezione su cui effettuare le operazioni CRUD come [[Future]] di [[BSONCollection]]
    */
  protected def collection: Future[BSONCollection]

  /** Metodo che permette di salvare un oggetto di tipo [[T]] nel database.
    * @param t
    *   oggetto da salvare
    * @param writer
    *   implicito che si occupa di serializzare un oggetto di tipo [[T]] in un [[BSONDocument]]
    * @return
    *   l'esito dell'operazione di salvataggio come [[Future]] di Unit
    */
  def create(t: T)(implicit writer: BSONDocumentWriter[T]): Future[Unit] =
    this.collection
      .flatMap(_.insert.one(t))
      .recover { case f: Throwable => f.printStackTrace() }
      .map(_ => ())

  /** Metodo che permette di aggiornare un oggetto di tipo [[T]] nel database.
    * @param t
    *   oggetto aggiornato
    * @param id
    *   identificativo dell'oggetto da aggiornare
    * @param writer
    *   implicito che si occupa di serializzare un oggetto di tipo [[T]] in un [[BSONDocument]]
    * @return
    *   l'esito dell'operazione di aggiornamento come [[Future]] di Unit
    */
  def update(t: T, id: String)(implicit writer: BSONDocumentWriter[T]): Future[Unit] =
    this.collection
      .flatMap(
        _.findAndUpdate(
          BSONDocument(
            "_id" -> id
          ),
          t
        )
      )
      .recover { case f: Throwable => f.printStackTrace() }
      .map(_ => ())

  /** Metodo che permette di leggere un singolo oggetto di tipo [[T]] dal database.
    * @param query
    *   [[BSONDocument]] che rappresenta il filtro da applicare alla ricerca
    * @param reader
    *   implicito che si occupa di deserializzare un [[BSONDocument]] in un oggetto di tipo [[T]]
    * @return
    *   l'oggetto letto dal database come [[Future]] di [[Option]] di [[T]]
    */
  def readOne(query: BSONDocument)(implicit reader: BSONDocumentReader[T]): Future[Option[T]] =
    this.collection
      .flatMap(_.find(query).one[T])
      .map {
        case Some(user) => Some(user)
        case None => None
      }
      .recover { case f: Throwable =>
        f.printStackTrace()
        None
      }

  /** Simile a [[readOne]] ma permette di leggere un oggetto di tipo [[T]] dal database tramite il suo id.
    * @param id
    *   identificativo dell'oggetto da leggere
    * @param reader
    *   implicito che si occupa di deserializzare un [[BSONDocument]] in un oggetto di tipo [[T]]
    * @return
    *   l'oggetto letto dal database come [[Future]] di [[Option]] di [[T]]
    */
  def readById(id: String)(implicit reader: BSONDocumentReader[T]): Future[Option[T]] =
    this.readOne(BSONDocument("_id" -> id))

  /** Metodo che permette di leggere più oggetti di tipo [[T]] dal database, eventualmente ordinati secondo un criterio
    * @param query
    *   [[BSONDocument]] che rappresenta il filtro da applicare alla ricerca
    * @param sort
    *   [[BSONDocument]] che rappresenta il criterio di ordinamento. Default: nessun ordinamento
    * @param nDocsToRead
    *   numero massimo di documenti da leggere. Default: `-1` (nessun limite)
    * @param reader
    *   implicito che si occupa di deserializzare un [[BSONDocument]] in un oggetto di tipo [[T]]
    * @return
    *   la lista formata dai [[nDocsToRead]] oggetti letti dal database come [[Future]] di [[Option]] di [[List]] di
    *   [[T]]
    */
  @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
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

  /** Metodo che permette di eliminare un oggetto di tipo [[T]] dal database.
    * @param selector
    *   filtro per la ricerca dell'oggetto da eliminare
    * @return
    *   l'esito dell'operazione di eliminazione come [[Future]] di Unit
    */
  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  def delete(selector: BSONDocument): Future[Unit] =
    this.collection
      .flatMap(_.delete.one(selector))
      .recover { case f: Throwable => f.printStackTrace() }
      .map(_ => ())

end Repository
