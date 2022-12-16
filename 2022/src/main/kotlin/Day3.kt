import java.io.File

const val alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

fun splitLine(line: String): Pair<String, String> {
    return Pair(line.substring(0..line.length / 2), line.substring(line.length / 2..line.length))
}

fun getPriority(c: Char): Int {
    return alphabet.indexOf(c) + 1
}

fun main() {

    var totalPriority = 0

    val elfGroup: MutableList<String> = mutableListOf()

    File("input3.txt").forEachLine {

        // Problem 1

//        val (compartment1, compartment2) = splitLine(it)
//        val commonCharacter = compartment1.toSet().intersect(compartment2.toSet()).first()
//        totalPriority += getPriority(commonCharacter)

        // Problem 2

        elfGroup.add(it)

        if (elfGroup.size == 3) {
            val commonCharacter = elfGroup.fold(alphabet.toSet()) { acc, elem -> acc.intersect(elem.toSet()) }.first()
            totalPriority += getPriority(commonCharacter)
            elfGroup.removeAll(elfGroup)
        }

    }

    println(totalPriority)

}
