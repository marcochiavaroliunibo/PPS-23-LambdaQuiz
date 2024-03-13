package it.unibo.pps.business.categories

import it.unibo.pps.business.{ConnectionMongoDB, Repository}
import it.unibo.pps.model.Category

import scala.concurrent.ExecutionContext.Implicits.global

trait CategoryData extends Repository[Category] {
  def getAllCategories: Option[?]
  def getCategoryByName(name: String): Option[?]
}
