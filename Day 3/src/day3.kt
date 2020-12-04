import java.io.File

fun treesForSlope(dx : Int, dy : Int) : Long {
    var output : Long = 0
    val len = 31 //length of each string (constant)
    val delta = intArrayOf(dx, dy) //position's delta (basically the slope itself)
    val position = intArrayOf(0, 0)

    var i = 0
    //for each line in the input file
    File("input.txt").forEachLine { line ->
        //if the line is intersected by the slope
        if (i % delta[1] == 0) {
            //the intersected character is at position pos[0]%len
            //this is due to the horizontal repetition of the map
            output += if (line[position[0] % len] == '#') 1 else 0

            //update position
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