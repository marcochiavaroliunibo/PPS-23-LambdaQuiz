package it.unibo.pps.controller

import it.unibo.pps.model.Category
import org.scalatest.DoNotDiscover
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

@DoNotDiscover
class CategoryControllerTests extends AnyFlatSpec with should.Matchers:

  private val randomCategories = for {
    i <- 2 to Category.values.length
    categories = CategoryController.getRandomCategories(i)
  } yield (i, categories)

  "CategoryController" should "be able to generate a specific number of random categories" in {
    randomCategories.map { (n, categories) =>
      categories.size should be (n)
    }
  }

  it should "generate categories without repetitions" in {
    randomCategories.map { (_, categories) =>
      categories.distinct.size should be (categories.size)
    }
  }

end CategoryControllerTests
