import java.io.File

fun partA() : Int {
    var output = 0

    File("input.txt").forEachLine { it ->
        val tokens = it.splitToSequence(" ", ": ", "-").toList()
        val min_occ = tokens[0].toInt()
        val max_occ = tokens[1].toInt()
        val letter = tokens[2][0]
        val password = tokens[3]

        val count = password.filter { p -> p == letter }.count()

        output += if (count in min_occ..max_occ) 1 else 0
    }

    return output
}

fun partB() : Int {
    var output = 0

    File("input.txt").forEachLine { it ->
        val tokens = it.splitToSequence(" ", ": ", "-").toList()
        val pos1 = tokens[0].toInt()-1
        val pos2 = tokens[1].toInt()-1
        val letter = tokens[2][0]
        val password = tokens[3]

        output += if ((password[pos1] == letter) xor (password[pos2] == letter)) 1 else 0
    }

    return output
}

fun main(args: Array<String>) {
    println(partA())
    println(partB())
}