package it.unibo.pps.controller

import it.unibo.pps.model.Category
import it.unibo.pps.model.Category.{CulturaGenerale, Geografia, Scienze, Storia}

import scala.collection.immutable.::
import scala.util.Random

object CategoryController:

  def getRandomCategories(roundForGame: Int): List[Category] =
    var listCategories: List[Category] = List()
    for (i <- 1 to roundForGame) {
      listCategories = Random.nextInt(3) match
        case 0 => Scienze :: listCategories
        case 1 => Geografia :: listCategories
        case 2 => Storia :: listCategories
        case 3 => CulturaGenerale :: listCategories
    }
    listCategories

end CategoryController