import java.awt.event.ItemListener
import java.io.File
import kotlin.math.abs
import kotlin.math.min

class Tile(val x: Int, val y: Int, val elevation: Int) {

    var bestDistance = 100000
    var explored = false

    val neighbors = mutableListOf<Tile>()

}

fun main() {

    val lines = File("input12.txt").readLines()

    val width = lines[0].length
    val height = lines.size

    val tiles = mutableListOf<MutableList<Tile>>()

    for (i in 0 until width) {
        tiles.add(mutableListOf())
    }

    // Create Tile Map

    for (i in 0 until height) {
        val chars = lines[i].toList()
        val elevations = chars.map { c ->
            when (c) {
                'S' -> {
                    'a'.code
                }

                'E' -> {
                    'z'.code
                }

                else -> {
                    c.code
                }
            }
        }

        for (j in 0 until width) {
            tiles[j].add(
                Tile(
                    i,
                    j,
                    elevations[j]
                )
            )
        }

    }

    fun isLegalPosition(x: Int, y: Int): Boolean {
        return (x in 0 until width && y in 0 until height)
    }

    fun getNeighborPositions(x: Int, y: Int): List<Pair<Int, Int>> {

        val neighborsPositions = mutableListOf<Pair<Int, Int>>()

        for (i in x - 1..x + 1) {
            for (j in y - 1..y + 1) {
                if (isLegalPosition(i, j) && abs(i - x + j - y) == 1) {
                    neighborsPositions.add(Pair(i, j))
                }
            }
        }

        return neighborsPositions
    }

    // Neighbors

    for (x in 0 until width) {
        for (y in 0 until height) {
            val neighbors = getNeighborPositions(x, y).map { neighbor -> tiles[neighbor.first][neighbor.second] }

            neighbors.forEach { neighbor ->
                if (tiles[x][y].elevation + 1 >= neighbor.elevation) {
                    tiles[x][y].neighbors.add(neighbor)
                }
            }
        }
    }

    // Dijkstra

    // Part 1

//    tiles[0][20].explored = true
//    tiles[0][20].bestDistance = 0
//
//    tiles[0][20].neighbors.forEach { tile ->
//        tile.bestDistance = 1
//    }

    // Part 2

    val lowestElevationTiles = mutableListOf<Tile>()

    for (x in 0 until width) {
        for (y in 0 until height) {
            if (tiles[x][y].elevation == 'a'.code) {
                lowestElevationTiles.add(tiles[x][y])
            }
        }
    }

    fun runDijkstraAt(tile: Tile): Int {

        tile.explored = true
        tile.bestDistance = 0

        val unexploredTiles = mutableListOf<Tile>()

        for (x in 0 until width) {
            for (y in 0 until height) {
                if (x != tile.x || y != tile.y) {
                    tiles[x][y].bestDistance = 10000
                    unexploredTiles.add(tiles[x][y])
                }
            }
        }

        tile.neighbors.forEach { tile ->
            tile.bestDistance = 1
        }

        while (unexploredTiles.size > 0) {
            unexploredTiles.sortBy { tile -> tile.bestDistance }

//            println("(${unexploredTiles[0].x}, ${unexploredTiles[0].y}) : ${unexploredTiles[0].bestDistance}")

            val currentTile = unexploredTiles.removeAt(0)
            currentTile.explored = true

            currentTile.neighbors.forEach { neighborTile ->

                if (currentTile.bestDistance + 1 < neighborTile.bestDistance) {
                    neighborTile.bestDistance = currentTile.bestDistance + 1
                }
            }
        }

        return tiles[52][20].bestDistance

    }

    var lowestDistance = 10000

    lowestElevationTiles.forEach {
        lowestDistance = min(lowestDistance, runDijkstraAt(it))
    }

    println(lowestDistance)

//    println(runDijkstraAt(tiles[0][20]))

    // Print explored tiles

//    for (y in 0 until height) {
//        for (x in 0 until width) {
//            if (tiles[x][y].bestDistance < 100000) {
//                if(y == 20 && x == 52) {
//                    print('E')
//                } else {
//                    print('#')
//                }
//            } else {
//                print('.')
//            }
//        }
//        println()
//    }

//    println(tiles[52][20].bestDistance)

}