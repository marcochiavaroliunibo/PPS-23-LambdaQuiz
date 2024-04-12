package it.unibo.pps.controller

import it.unibo.pps.model.Category
import it.unibo.pps.model.Category.*
import scala.util.Random

object CategoryController:

  /** estrae casualmente un numero di categorie pari al numero di round giocati per ogni game */
  def getRandomCategories(roundForGame: Int): List[Category] =
    var listCategories: List[Category] = List()
    val sizeCategory: Int = Category.values.length
    val listNumber = List()
    for (i <- 1 to roundForGame) {
      var random: Int  = Random.nextInt(sizeCategory)
      while (listNumber.contains(random))
        random = Random.nextInt(sizeCategory)
      listCategories = Category.values(random) :: listCategories
    }
    listCategories

end CategoryController
