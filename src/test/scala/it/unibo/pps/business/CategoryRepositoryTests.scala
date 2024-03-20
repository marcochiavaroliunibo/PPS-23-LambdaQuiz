package it.unibo.pps.business

import it.unibo.pps.model.{Category, EnumCategory}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*
import reactivemongo.api.bson.BSONDocument

class CategoryRepositoryTests extends AsyncFlatSpec with should.Matchers:

  private val category = new Category("Categoria di test")
  private val categoryRepository = new CategoryRepository

  "A category" should "eventually be inserted in the database" in {
    categoryRepository
      .create(category)
      .map(_ shouldBe a[Unit])
  }

  it should "be read from the database" in {
    val idQuery = BSONDocument("_id" -> category.getID)
    categoryRepository.readOne(idQuery).map(_.exists(_.getName == category.getName) should be(true))
  }

  // Test delle categorie con Enum
  val r = new EnumCategoryRepository
  "Enum category" should "be inserted in the database" in {
    r.insertCategory(EnumCategory.Storia).map(_ shouldBe a[Unit])
  }

  it should " also be read by its name as string" in {
    r.getCategoryFromName("Storia")
      .map(_.exists(_ == EnumCategory.Storia) should be(true))
  }

end CategoryRepositoryTests
