import java.io.File

enum class SymbolValue {
    Rock, Paper, Scissors
}

class Symbol (val value : SymbolValue) {

    fun compare(other : Symbol) : Int {
        if (value == other.value) {
            return 3
        } else {
            if (value == SymbolValue.Rock && other.value == SymbolValue.Scissors) { return 6 }
            if (value == SymbolValue.Paper && other.value == SymbolValue.Rock) { return 6 }
            if (value == SymbolValue.Scissors && other.value == SymbolValue.Paper) { return 6 }
            return 0
        }
    }

    fun getScoreValue() : Int {
        when (value) {
            SymbolValue.Rock -> return 1
            SymbolValue.Paper -> return 2
            SymbolValue.Scissors -> return 3
        }
    }


    /**
     * Get symbol win order
     *
     * [Winning, Draw, Losing]
     *
     * Ex : if value is Scissors, this returns [Paper, Scissors, Rock]
     */
    fun getSymbolWinOrder() : List<SymbolValue> {
        when (value) {
            SymbolValue.Rock -> return listOf(SymbolValue.Scissors, SymbolValue.Rock, SymbolValue.Paper)
            SymbolValue.Paper -> return listOf(SymbolValue.Rock, SymbolValue.Paper, SymbolValue.Scissors)
            SymbolValue.Scissors -> return listOf(SymbolValue.Paper, SymbolValue.Scissors, SymbolValue.Rock)
        }
    }
}

fun convert(input : String) : Symbol {
    when(input) {
        "A" -> return Symbol(SymbolValue.Rock)
        "B" -> return Symbol(SymbolValue.Paper)
        "C" -> return Symbol(SymbolValue.Scissors)
        else -> throw RuntimeException("Wrong Input : $input")
    }
}

fun main() {

    var totalScore = 0

    File("input2.txt").forEachLine {
        val roundValues = it.split(" ")

        val opponentPlay = convert(roundValues[0])

        val myPlay : Symbol

        when(roundValues[1]) {
            "X" -> myPlay = Symbol(opponentPlay.getSymbolWinOrder()[0])
            "Y" -> myPlay = Symbol(opponentPlay.getSymbolWinOrder()[1])
            "Z" -> myPlay = Symbol(opponentPlay.getSymbolWinOrder()[2])
            else -> throw RuntimeException("Wrong Input : ${roundValues[1]}")
        }

        totalScore += myPlay.compare(opponentPlay) + myPlay.getScoreValue()

    }

    println("Total Score : $totalScore")

}