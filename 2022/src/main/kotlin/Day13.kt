import java.io.File

enum class ListEquality {
    Inferior,
    Equal,
    Superior
}

class Packet(var value: Int? = null, val packetList: MutableList<Packet> = mutableListOf()) {

    override fun toString(): String {
        return if (value != null) {
            value.toString()
        } else {
            packetList.toString()
        }
    }

    fun lessThan(other: Packet): ListEquality {

        // Value - Value
        if (value != null && other.value != null) {
            return if (value!! < other.value!!) {
                ListEquality.Inferior
            } else if (value!! == other.value!!) {
                ListEquality.Equal
            } else {
                ListEquality.Superior
            }
        }

        if (value != null) {
            val newPacket = Packet(packetList = mutableListOf(this))
            return newPacket.lessThan(other)
        }

        if (other.value != null) {
            val newOtherPacket = Packet(packetList = mutableListOf(other))
            return lessThan(newOtherPacket)
        }

        // List - List

        fun comparePacketLists(list1: MutableList<Packet>, list2: MutableList<Packet>): ListEquality {

            if (list1.isEmpty()) {
                if (list2.isEmpty()) {
                    // [] vs []
                    return ListEquality.Equal
                } else {
                    // [] vs [...]
                    return ListEquality.Inferior
                }
            } else {
                if (list2.isEmpty()) {
                    // [...] vs []
                    return ListEquality.Superior
                } else {
                    // [...] vs [...]
                    when (list1[0].lessThan(list2[0])) {
                        ListEquality.Inferior -> return ListEquality.Inferior
                        ListEquality.Superior -> return ListEquality.Superior
                        ListEquality.Equal -> return comparePacketLists(
                            list1.toMutableList().apply { removeAt(0) },
                            list2.toMutableList().apply { removeAt(0) },
                        )
                    }
                }
            }
        }

        return comparePacketLists(packetList, other.packetList)

//        if (value != null) {
//            return if (other.value == null) {
//                false
//            } else {
//                value!! <= other.value!!
//            }
//        }
//
//        if (other.value != null) {
//
//            if(packetList.isEmpty()) {
//                return true
//            }
//
//            var packet = packetList.first()
//            while (packet.value == null) {
//                if (packet.packetList.size > 0) {
//                    packet = packet.packetList.first()
//                } else {
//                    return false
//                }
//            }
//            return packet.value!! <= other.value!!
//        }
//
//        fun comparePacketLists(list1: List<Packet>, list2: List<Packet>): Boolean {
//            if (list1.size == 0) {
//                return true
//            } else if (list1.size > list2.size) {
//                return false
//            } else {
//                return list1.first().lessThan(list2.first()) && comparePacketLists(
//                    list1.subList(1, list1.size),
//                    list2.subList(1, list2.size)
//                )
//            }
//        }


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

    println(packetList)

    var totalIndices = 0

    for (i in packetList.indices) {
        if (i % 2 == 1) {
            println(packetList[i - 1])
            println(packetList[i])

            val comparison = packetList[i - 1].lessThan(packetList[i])
            println(comparison)

            if (comparison == ListEquality.Inferior) {
                totalIndices += i / 2 + 1
            }
        }
    }

    println(totalIndices)


    // Part 2

    val packet2 = Packet(
        packetList = mutableListOf(
            Packet(
                packetList = mutableListOf(
                    Packet(value = 2)
                )
            )
        )
    )

    packetList.add(
        packet2
    )

    val packet6 = Packet(
        packetList = mutableListOf(
            Packet(
                packetList = mutableListOf(
                    Packet(value = 6)
                )
            )
        )
    )

    packetList.add(
        packet6
    )

    packetList.sortWith { packet, packet2 ->
        if (packet.lessThan(packet2) == ListEquality.Inferior) {
            -1
        } else {
            1
        }
    }

    println(packetList)

    println(packetList.indexOf(packet2) + 1)
    println(packetList.indexOf(packet6) + 1)

}