package it.unibo.pps.business

import it.unibo.pps.business.{CategoryRepository, ConnectionMongoDB}
import it.unibo.pps.model.Category
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

class ConnectionMongoDBTests extends AsyncFlatSpec with should.Matchers:
  "The application" should "connect to the database" in {
    ConnectionMongoDB.getDatabase.map(db => db.name shouldEqual "LambdaQuiz")
  }
end ConnectionMongoDBTests
