package it.unibo.pps

import java.util.concurrent.Executors.newFixedThreadPool
import java.util.concurrent.{ExecutorService, Executors}
import scala.concurrent.ExecutionContext

object ECHandler:
  @SuppressWarnings(Array("org.wartremover.warts.Var"))
  private var executors: List[ExecutorService] = List.empty[ExecutorService]

  def createExecutor: ExecutionContext =
    val ftp = newFixedThreadPool(8)
    executors = ftp :: executors
    ExecutionContext.fromExecutor(ftp)

  def closeAll(): Unit =
    executors.foreach(_.shutdown())
end ECHandler
