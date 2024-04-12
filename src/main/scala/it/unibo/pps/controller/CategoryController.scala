package it.unibo.pps.controller

import it.unibo.pps.model.Category
import it.unibo.pps.model.Category.*
import scala.util.Random

object CategoryController:

  /** estrae casualmente un numero di categorie pari al numero di round giocati per ogni game */
  def getRandomCategories(roundForGame: Int): List[Category] =
    var listCategories: List[Category] = List()
    val sizeCategory: Int = Category.values.length 
    for (i <- 1 to roundForGame) {
      listCategories = Category.values(Random.nextInt(sizeCategory)) :: listCategories
    }
    listCategories

end CategoryController
