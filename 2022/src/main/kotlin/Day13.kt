import java.io.File

class Packet(var value: Int? = null) {

    val packetList = mutableListOf<Packet>()

    override fun toString(): String {
        return if (value != null) {
            value.toString()
        } else {
            packetList.toString()
        }
    }

    fun lessThan(other: Packet): Boolean {
        if (value != null) {
            return if (other.value == null) {
                false
            } else {
                value!! <= other.value!!
            }
        }

        if (other.value != null) {

            if(packetList.isEmpty()) {
                return true
            }

            var packet = packetList.first()
            while (packet.value == null) {
                if (packet.packetList.size > 0) {
                    packet = packet.packetList.first()
                } else {
                    return false
                }
            }
            return packet.value!! <= other.value!!
        }

        fun comparePacketLists(list1: List<Packet>, list2: List<Packet>): Boolean {
            if (list1.size == 0) {
                return true
            } else if (list1.size > list2.size) {
                return false
            } else {
                return list1.first().lessThan(list2.first()) && comparePacketLists(
                    list1.subList(1, list1.size),
                    list2.subList(1, list2.size)
                )
            }
        }

        return comparePacketLists(packetList, other.packetList)

    }

}

fun getPacketFromString(string: String): Packet {

    val packetString = string.substring(1 until string.length - 1)

    val packet = Packet()

    var currentSubstringStart = 0

    var i = 0
    while (i < packetString.length) {

        if (packetString[i] == '[') {
            var depth = 1
            for (j in i + 1 until packetString.length) {

                if (packetString[j] == '[') {
                    depth++
                }

                if (packetString[j] == ']') {
                    depth--
                }

                if (depth == 0) {
                    packet.packetList.add(getPacketFromString(packetString.substring(i..j)))
                    i = j + 1
                    currentSubstringStart = j + 2
                    break
                }

            }
        } else

            if (packetString[i] == ',') {
                packet.packetList.add(Packet(packetString.substring(currentSubstringStart, i).toInt()))
                currentSubstringStart = i + 1
            } else

                if (i == packetString.length - 1 && i >= currentSubstringStart) {
                    packet.packetList.add(Packet(packetString.substring(currentSubstringStart..i).toInt()))
                    currentSubstringStart = i + 1
                }
        i++
    }

    return packet

}

fun main() {

    val packetList = mutableListOf<Packet>()

    File("input13.txt").forEachLine {
        if (it != "") {
            packetList.add(getPacketFromString(it))
        }
    }

    var totalIndices = 0

    for (i in packetList.indices) {
        if (i % 2 == 1) {
            if (packetList[i - 1].lessThan(packetList[i])) {
                totalIndices += i / 2 + 1
            }
        }
    }

    println(totalIndices)

}