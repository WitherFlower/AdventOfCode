import java.math.BigInteger

class Monkey(
    intItemList: MutableList<Int>,
    val operation: (BigInteger) -> BigInteger,
    intDivTest: Int,
    val trueDest: Int,
    val falseDest: Int
) {

    val itemList = intItemList.map { value -> value.toBigInteger() } as MutableList<BigInteger>
    val divTest = intDivTest.toBigInteger()

    var inspectionCount = 0

    fun inspectNextItem(): Pair<BigInteger, Int> {

        inspectionCount++

        val item = itemList[0]

        itemList.removeAt(0)

        val result = operation(item) % 9699690.toBigInteger() // (13 * 2 * 7 * 17 * 5 * 11 * 3 * 19)
        if(result < BigInteger.ZERO) {
            println("Warning : $item gave negative result after operation ($result)")
        }

        if ((result % divTest) == BigInteger.ZERO) {
            return Pair(result, trueDest)
        } else {
            return Pair(result, falseDest)
        }

    }

}

fun main() {

    val monkeys = listOf(
//        Monkey(mutableListOf(79, 98), { value -> value * 19.toBigInteger() }, 23, 2, 3),
//        Monkey(mutableListOf(54, 65, 75, 74), { value -> value + 6.toBigInteger() }, 19, 2, 0),
//        Monkey(mutableListOf(79, 60, 97), { value -> value * value }, 13, 1, 3),
//        Monkey(mutableListOf(74), { value -> value + 3.toBigInteger() }, 17, 0, 1)

        Monkey(mutableListOf(84, 72, 58, 51), { value -> value * 3.toBigInteger() }, 13, 1, 7),
        Monkey(mutableListOf(88, 58, 58), { value -> value + 8.toBigInteger() }, 2, 7, 5),
        Monkey(mutableListOf(93, 82, 71, 77, 83, 53, 71, 89), { value -> value * value }, 7, 3, 4),
        Monkey(mutableListOf(81, 68, 65, 81, 73, 77, 96), { value -> value + 2.toBigInteger() }, 17, 4, 6),
        Monkey(mutableListOf(75, 80, 50, 73, 88), { value -> value + 3.toBigInteger() }, 5, 6, 0),
        Monkey(mutableListOf(59, 72, 99, 87, 91, 81), { value -> value * 17.toBigInteger() }, 11, 2, 3),
        Monkey(mutableListOf(86, 69), { value -> value + 6.toBigInteger() }, 3, 1, 0),
        Monkey(mutableListOf(91), { value -> value + 1.toBigInteger() }, 19, 2, 5)
    )

    for (i in 0 until 10000) {
        println("Turn $i")
        monkeys.forEach { monkey ->
            while (monkey.itemList.size > 0) {
                val (result, dest) = monkey.inspectNextItem()
                monkeys[dest].itemList.add(result)
            }
        }
    }

    monkeys.forEach { monkey ->
        println(monkey.inspectionCount)
    }

}