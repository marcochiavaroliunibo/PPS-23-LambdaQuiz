package it.unibo.pps.business

import it.unibo.pps.model.Category
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

class CategoryRepositoryTests extends AsyncFlatSpec with should.Matchers:

  val category = new Category("Categoria di test")
  "A category" should "eventually be inserted in the database" in {
    val categoryRepository = new CategoryRepository
    categoryRepository
      .create(category)
      .map(_ shouldBe a[Unit])
  }

  it should "be read from the database" in {
    val categoryRepository = new CategoryRepository
    val futureCategory = categoryRepository.read(category.getID)
    futureCategory
      .map(_.exists(_.getName == category.getName) should be(true))
  }
end CategoryRepositoryTests
