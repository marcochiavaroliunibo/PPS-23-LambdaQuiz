package it.unibo.pps.controller

import it.unibo.pps.model.Category
import it.unibo.pps.model.Category.*

import scala.util.Random

/** Controller che si occupa di gestire le categorie */
object CategoryController:

  /** Metodo che restituisce una lista di categorie casuali senza ripetizioni.
    * @param roundForGame
    *   numero di categorie da estrarre. Equivale al numero di round giocati per ogni partita
    * @return
    *   lista delle categorie estreatte
    */
  def getRandomCategories(roundForGame: Int): List[Category] =
    var listCategories: List[Category] = List()
    val sizeCategory: Int = Category.values.length
    val listNumber = List()
    for (i <- 1 to roundForGame) {
      var random: Int = Random.nextInt(sizeCategory)
      while (listNumber.contains(random))
        random = Random.nextInt(sizeCategory)
      listCategories = Category.values(random) :: listCategories
    }
    listCategories

end CategoryController
