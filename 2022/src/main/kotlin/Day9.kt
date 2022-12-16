import java.io.File
import kotlin.math.abs

class Vector2(var x: Int, var y: Int) {
    fun add(other: Vector2) {
        x += other.x
        y += other.y
    }

    fun minus(other: Vector2): Vector2 {
        return Vector2(x - other.x, y - other.y)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Vector2) {
            return x == other.x && y == other.y
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return x.hashCode() / 2 + y.hashCode() / 2
    }

    override fun toString(): String {
        return "($x, $y)"
    }
}

// Part 1
//val head = Vector2(0, 0)
//val tail = Vector2(0, 0)

// Part 2

val knots = mutableListOf<Vector2>()

val tailPositions = mutableSetOf<Vector2>()
val knotCount = 10

fun main() {

    tailPositions.add(Vector2(0, 0))

    for (i in 0 until knotCount) {
        knots.add(Vector2(0, 0))
    }

    File("input9.txt").forEachLine {

        val line = it.split(" ")

        var headMovement: Vector2 = Vector2(0, 0)

        when (line[0]) {
            "U" -> headMovement = Vector2(0, 1)
            "D" -> headMovement = Vector2(0, -1)
            "R" -> headMovement = Vector2(1, 0)
            "L" -> headMovement = Vector2(-1, 0)
        }

        for (i in 0 until line[1].toInt()) {
            knots[0].add(headMovement)
//            updateTail()
//            println("Head: $head, Tail: $tail")

            for (j in 0 until knotCount - 1) {
                updateKnots(knots[j], knots[j + 1])
            }
            println(knots)

            tailPositions.add(Vector2(knots.last().x, knots.last().y))
        }


    }

    println(tailPositions)
    println(tailPositions.size)

}

fun updateKnots(head: Vector2, tail: Vector2) {
    val difference = head.minus(tail)
    if (abs(difference.x) == 2 && abs(difference.y) == 2) {
        tail.add(Vector2(difference.x / 2, difference.y / 2))
    } else if (difference.x == 2) {
        tail.add(Vector2(1, difference.y))
    } else if (difference.x == -2) {
        tail.add(Vector2(-1, difference.y))
    } else if (difference.y == 2) {
        tail.add(Vector2(difference.x, 1))
    } else if (difference.y == -2) {
        tail.add(Vector2(difference.x, -1))
    }

}
