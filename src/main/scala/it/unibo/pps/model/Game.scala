package it.unibo.pps.model

import java.time.LocalDateTime

class Game (id: String, user1: User, user2: User, completed: Boolean, lastUpdate: LocalDateTime) {

    def getID () : String = id
    def getUser1: User = user1
    def getUser2: User = user2
    def getCompleted: Boolean = completed
    def getLastUpdate: LocalDateTime = lastUpdate
  
}
