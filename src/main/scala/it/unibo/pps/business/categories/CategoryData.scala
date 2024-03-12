package it.unibo.pps.business.categories

import org.mongodb.scala._

trait CategoryData {
  
    def getAllCategories() : Option[Document]
    def getCategoryByName (name: String) : Option[Document]

}
