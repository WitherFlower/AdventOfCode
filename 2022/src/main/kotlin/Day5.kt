import java.io.File
import java.util.Stack

enum class FilePart { StackDeclarations, Instructions }

fun main() {

    var filePart = FilePart.StackDeclarations

    val initialStackStrings: MutableList<String> = mutableListOf()
    val stackList: MutableList<Stack<String>> = mutableListOf()

    for (line in File("input5.txt").readLines()) {

        if (line.equals("")) {
            filePart = FilePart.Instructions

            val stackCount = initialStackStrings.last().split("   ").size

            for (i in 0 until stackCount) {
                stackList.add(i, Stack<String>())
            }

            for (i in initialStackStrings.size - 2 downTo 0) {
                for (j in 0 until stackCount) {

                    if (initialStackStrings[i].length > 4 * j + 1) {
                        val crateChar = initialStackStrings[i].substring(4 * j + 1, 4 * j + 2)

                        if (crateChar != " ") {
                            stackList[j].push(crateChar)
                        }

                    }

                }
            }

            println(stackList)

        } else if (filePart == FilePart.StackDeclarations) {
            initialStackStrings.add(line)

        } else { // filePart = Instructions

            val instruction = line.split(" ")

            val instructionCount = instruction[1].toInt()
            val sourceStackIndex = instruction[3].toInt() - 1
            val destStackIndex = instruction[5].toInt() - 1

//            println("Source : $sourceStackIndex")
//            println("Dest : $destStackIndex")

            // Part 1

//            for (i in 0 until instructionCount) {
//                stackList[destStackIndex].push(stackList[sourceStackIndex].pop())
//            }

            // Part 2

            val tempStack : Stack<String> = Stack()

            for (i in 0 until instructionCount) {
                tempStack.push(stackList[sourceStackIndex].pop())
            }

            for (i in 0 until instructionCount) {
                stackList[destStackIndex].push(tempStack.pop())
            }

        }
    }

    stackList.forEach { stack: Stack<String> -> print(stack.peek()) }

}