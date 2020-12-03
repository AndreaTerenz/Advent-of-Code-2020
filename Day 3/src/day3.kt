import java.io.File

fun treesForSlope(dx : Int, dy : Int) : Long {
    var output : Long = 0
    val len = 31
    val delta = intArrayOf(dx, dy)
    val position = intArrayOf(0, 0)

    var i = 0
    File("input.txt").forEachLine { line ->
        if (i % delta[1] == 0) {
            val c = line[position[0] % len]

            output += if (c == '#') 1 else 0
            position[0] += delta[0]
            position[1] += delta[1]
        }

        i += 1
    }

    return output
}

fun partA() : Long {
    return treesForSlope(3, 1)
}

fun partB() : Long {
    return treesForSlope(1, 1)*partA()*treesForSlope(5, 1)*treesForSlope(7, 1)*treesForSlope(1, 2)
}

fun main() {
    println(partA())
    println(partB())
}