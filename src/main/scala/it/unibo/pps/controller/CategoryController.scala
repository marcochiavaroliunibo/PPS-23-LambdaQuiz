package it.unibo.pps.controller

import it.unibo.pps.model.Category
import it.unibo.pps.model.Category.*
import scala.util.Random

object CategoryController:

  def getRandomCategories(roundForGame: Int): List[Category] =
    var listCategories: List[Category] = List()
    for (i <- 1 to roundForGame) {
      listCategories = Random.nextInt(7) match
        case 0 => Scienze :: listCategories
        case 1 => Geografia :: listCategories
        case 2 => Storia :: listCategories
        case 3 => CulturaGenerale :: listCategories
        case 4 => Politica :: listCategories
        case 5 => Sport :: listCategories
        case 6 => Psicologia :: listCategories
    }
    listCategories

end CategoryController
