package it.unibo.pps.business

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.*

class ConnectionMongoDBTests extends AsyncFlatSpec with should.Matchers:
  "The application" should "connect to the database" in {
    ConnectionMongoDB.getDatabase.map(db => db.name shouldEqual "LambdaQuiz")
  }
end ConnectionMongoDBTests
