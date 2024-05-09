package it.unibo.pps.controller

import it.unibo.pps.model.Category
import it.unibo.pps.model.Category.*

import scala.util.Random

/** Controller che si occupa di gestire le categorie */
object CategoryController:

  /** Metodo che restituisce una lista di categorie casuali senza ripetizioni.
    * @param nCategories
    *   numero di categorie da estrarre. Equivale al numero di round giocati per ogni partita
    * @return
    *   lista delle categorie estratte
    */
  def getRandomCategories(nCategories: Int): List[Category] =
    Iterator
      .continually(Random.nextInt(Category.values.length))
      .distinct
      .take(nCategories)
      .map(Category.values)
      .toList

end CategoryController
