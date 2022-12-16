import java.io.File

fun main(args: Array<String>) {

    val maxCal = mutableListOf(0, 0, 0)
    var elfCal = 0

    File("input.txt").forEachLine {
        try {
            val lineCal = it.toInt()
            elfCal += lineCal

        } catch (e : NumberFormatException) {

            if (elfCal > maxCal[maxCal.size - 1])
                maxCal[maxCal.size - 1] = elfCal
                maxCal.sortDescending()

            println(elfCal)
            elfCal = 0
        }
    }

    println("The total of the three maximums is ${maxCal.sum()}")
}