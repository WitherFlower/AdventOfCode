import java.io.File
import java.lang.Exception

class Directory(val parent: Directory?, val name: String) {

    val subDirectories: MutableList<Directory> = mutableListOf()
    val files: MutableList<SimpleFile> = mutableListOf()

    fun getSize(): Int {
        return files.fold(0, { acc: Int, file: SimpleFile -> acc + file.size }) + subDirectories.fold(
            0,
            { acc: Int, directory: Directory -> acc + directory.getSize() })
    }

}

data class SimpleFile(val size: Int)

fun main() {

    val rootDirectory = Directory(null, "/")
    var currentDirectory: Directory = rootDirectory

    for (line in File("input7.txt").readLines()) {

        val words = line.split(" ")

        // CD command
        if (words[0] == "$" && words[1] == "cd") {
            if (words[2] == "/") {
                currentDirectory = rootDirectory
                continue
            }

            if (words[2] == ".."){
                currentDirectory = currentDirectory.parent!!
                continue
            }

            currentDirectory = currentDirectory.subDirectories.find { directory: Directory -> directory.name == words[2] }!!
        }

        if (words[0] == "dir") {
            currentDirectory.subDirectories.add(Directory(currentDirectory, words[1]))
            continue
        }

        try {
            val size = words[0].toInt()
            currentDirectory.files.add(SimpleFile(size))
        } catch (e: Exception) {
            continue
        }

    }

    // Part 1
//    println(totalSizeBelow(rootDirectory, 100000))

    // Part 2
    val totalSize = rootDirectory.getSize()
    val remainingSize = 70000000 - totalSize
    val minimumSizeToDelete = 30000000 - remainingSize

    val directoryList = listAllDirectories(rootDirectory)
    directoryList.sortBy { directory -> directory.getSize() }

    val smallestDirToDelete = directoryList.find { directory -> directory.getSize() > minimumSizeToDelete }
    println(smallestDirToDelete?.getSize())

}

// Part 1
fun totalSizeBelow(directory: Directory, threshold: Int): Int {
    val size = directory.getSize()
        return (if (size > threshold) 0 else size) + directory.subDirectories.fold(0, {acc: Int, subDirectory: Directory -> acc + totalSizeBelow(subDirectory, threshold) })
}

// Part 2
fun listAllDirectories(directory : Directory): MutableList<Directory> {
    val dirList = mutableListOf(directory)
    directory.subDirectories.forEach { subDirectory: Directory -> dirList.addAll(listAllDirectories(subDirectory)) }
    return dirList
}