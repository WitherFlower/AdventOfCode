import java.io.File
import kotlin.math.max

data class Tree(val height: Int, var checked: Boolean = false, var visible: Boolean = false)

fun main() {

    val treeMap: MutableList<MutableList<Tree>> = mutableListOf()

    File("input8.txt").forEachLine {
        val row = it.map { Tree(it.toString().toInt()) }
        treeMap.add(row.toMutableList())
    }

    val height = treeMap.size
    val width = treeMap[0].size

    // Part 1

    var visibleTreesCount = 0

    fun getMaxSizeAbove(i: Int, j: Int): Int {
        val trees: MutableList<Tree> = mutableListOf()
        for (index in 0 until i) {
            trees.add(treeMap[index][j])
        }
        return trees.map { tree -> tree.height }.max()
    }

    fun getMaxSizeBelow(i: Int, j: Int): Int {
        val trees: MutableList<Tree> = mutableListOf()
        for (index in i + 1 until height) {
            trees.add(treeMap[index][j])
        }
        return trees.map { tree -> tree.height }.max()
    }

    fun getMaxSizeLeft(i: Int, j: Int): Int {
        val trees: MutableList<Tree> = mutableListOf()
        for (index in 0 until j) {
            trees.add(treeMap[i][index])
        }
        return trees.map { tree -> tree.height }.max()
    }

    fun getMaxSizeRight(i: Int, j: Int): Int {
        val trees: MutableList<Tree> = mutableListOf()
        for (index in j + 1 until height) {
            trees.add(treeMap[i][index])
        }
        return trees.map { tree -> tree.height }.max()
    }

    for (i in 0 until height) {
        for (j in 0 until width) {
            if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                // Mark all edge trees as visible
                treeMap[i][j].visible = true
            } else {
                if (listOf(
                        getMaxSizeAbove(i, j),
                        getMaxSizeBelow(i, j),
                        getMaxSizeLeft(i, j),
                        getMaxSizeRight(i, j)
                    ).fold(false) { acc: Boolean, maxSize: Int -> acc || maxSize < treeMap[i][j].height }
                ) {
                    treeMap[i][j].visible = true
                }
            }
        }
    }

    treeMap.forEach { trees ->
        trees.forEach { tree ->
            if (tree.visible) {
                visibleTreesCount++
            }
        }
    }

    println(treeMap.map { trees -> trees.map { tree -> tree.height } })
    println(visibleTreesCount)

    // Part 2

    fun getDistanceAbove(i: Int, j: Int): Int {
        for (distance in 1..i) {
            if (treeMap[i - distance][j].height >= treeMap[i][j].height) {
                return distance;
            }
        }
        return i
    }

    fun getDistanceBelow(i: Int, j: Int): Int {
        for (distance in 1..height - 1 - i) {
            if (treeMap[i + distance][j].height >= treeMap[i][j].height) {
                return distance;
            }
        }
        return height - 1 - i
    }

    fun getDistanceLeft(i: Int, j: Int): Int {
        for (distance in 1..j) {
            if (treeMap[i][j - distance].height >= treeMap[i][j].height) {
                return distance;
            }
        }
        return j
    }

    fun getDistanceRight(i: Int, j: Int): Int {
        for (distance in 1..height - 1 - j) {
            if (treeMap[i][j + distance].height >= treeMap[i][j].height) {
                return distance;
            }
        }
        return height - 1 - j
    }

    var maxScenicScore = 0

    for (i in 0 until height) {
        for (j in 0 until width) {
            val scenicScore =
                getDistanceAbove(i, j) * getDistanceBelow(i, j) * getDistanceLeft(i, j) * getDistanceRight(i, j)
            maxScenicScore = max(scenicScore, maxScenicScore)
        }
    }

    println(maxScenicScore)

}