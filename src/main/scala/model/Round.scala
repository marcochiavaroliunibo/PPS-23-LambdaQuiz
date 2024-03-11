package model

class Round (id: String, game: Game, pointUser1: Int, pointUser2: Int, numberRound: Int) {

    def getID () : String = id
    def getGame: Game = game
    def getPoint1: Int = pointUser1
    def getPoint2: Int = pointUser2
    def getNumberRound: Int = numberRound
  
}
