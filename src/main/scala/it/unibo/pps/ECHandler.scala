package it.unibo.pps

import java.util.concurrent.Executors.newFixedThreadPool
import java.util.concurrent.{ExecutorService, Executors}
import scala.concurrent.ExecutionContext

/** Oggetto che si occupa di gestire gli ExecutionContext
  */
object ECHandler:
  @SuppressWarnings(Array("org.wartremover.warts.Var"))
  /** Lista degli [[ExecutorService]] usati nel programma */
  private var executors: List[ExecutorService] = List.empty[ExecutorService]

  /** Crea un nuovo ExecutionContext a partire da un FixedThreadPool di 8 thread, aggiunge il relativo [[Executors]]
    * alla lista interna e lo restituisce
    *
    * @return
    *   ExecutionContext creato
    */
  def createExecutor: ExecutionContext =
    val ftp = newFixedThreadPool(8)
    executors = ftp :: executors
    ExecutionContext.fromExecutor(ftp)

  /** Chiude tutti gli [[ExecutorService]] aperti */
  def closeAll(): Unit =
    executors.foreach(_.shutdown())

end ECHandler
