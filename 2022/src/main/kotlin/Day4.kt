import java.io.File

fun contains(range: IntRange, other: IntRange): Boolean {
    return range.first <= other.first && range.last >= other.last
}

fun overlaps(range: IntRange, other: IntRange): Boolean {
    return range.fold(false, {acc: Boolean, i: Int ->  other.contains(i) || acc})
}

fun main() {

    var fullyContained = 0
    var overlapCount = 0

    File("input4.txt").forEachLine {
        val (leftString, rightString) = it.split(",")

        val leftInts = leftString.split("-").map { it.toInt() }
        val rightInts = rightString.split("-").map { it.toInt() }

        val range1 = IntRange(leftInts[0], leftInts[1])
        val range2 = IntRange(rightInts[0], rightInts[1])

        if (contains(range1, range2) || contains(range2, range1)) {
            fullyContained++
        }

        if (overlaps(range1, range2)) {
            overlapCount++
        }

    }

    println("Fully Contained pairs : $fullyContained")
    println("Overlaps : $overlapCount")
}