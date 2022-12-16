import java.io.File

fun main() {

    var cycle = 1
    var reg = 1

    var sumValues = 0

    fun incrementCycle(n : Int){
        for (i in 0 until n){
            cycle++
            if (((cycle - 20) % 40) == 0) {
                sumValues += reg*cycle
//                println("$cycle : ${reg * cycle}")
            }

            // Part 2
            if ( (cycle % 40) - 1 >= reg - 1 && (cycle % 40) - 1 <= reg + 1) {
                print("#")
            } else {
                print(".")
            }

            if ((cycle - 1) % 40 == 0) {
                println()
            }


        }
    }

    File("input10.txt").forEachLine {
        val line = it.split(" ")

        when (line[0]) {

            "noop" -> incrementCycle(1)
            "addx" -> {
                incrementCycle(1)
                reg += line[1].toInt()
                incrementCycle(1)
            }
        }

    }

    println(sumValues)


}