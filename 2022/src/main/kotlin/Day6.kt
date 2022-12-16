import java.io.File

fun main() {

    var signal : String

    signal = File("input6.txt").readLines()[0]

    val readingQueue: ArrayDeque<Char> = ArrayDeque()

    for (i in 0 until signal.length) {

        println("Queue : $readingQueue, Set : ${readingQueue.toSet()}")

        if (readingQueue.size < 13) {
            readingQueue.add(signal[i])
        } else {
            readingQueue.add(signal[i])

            if (readingQueue.toSet().size == 14) {
                println(i)
                break
            } else {
                readingQueue.removeFirst()
            }
        }

    }

}