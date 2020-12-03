import java.io.File

fun partA() : Int {
    var output = 0

    //for each line of the input file
    File("input.txt").forEachLine {
        val tokens = it.splitToSequence(" ", ": ", "-").toList() //split the line into its four components (N-M C: PASSWD)
        val minOcc = tokens[0].toInt() //N
        val maxOcc = tokens[1].toInt() //M
        val letter = tokens[2][0] //C
        val password = tokens[3] //PASSWD

        //password.filter {...} filters out the characters in PASSWD that do not match C and returns a list
        //.count()              returns the length of the filtered list
        //in minOcc..maxOcc     checks that the count is in the range [N;M]
        //if the condition is matched, 1 is added to output
        output += if (password.filter { p -> p == letter }.count() in minOcc..maxOcc) 1 else 0
    }

    return output
}

fun partB() : Int {
    var output = 0

    File("input.txt").forEachLine {
        val tokens = it.splitToSequence(" ", ": ", "-").toList()
        val pos1 = tokens[0].toInt()-1 //as per prompt instructions, the positions in each record are NOT 0 indexed, so you have to subtract one
        val pos2 = tokens[1].toInt()-1
        val letter = tokens[2][0]
        val password = tokens[3]

        //the only particular thing is the use of kotlin's infix xor function
        //and it is used because it matches exactly the meaning of the second puzzle
        //(one but neither both nor none)
        output += if ((password[pos1] == letter) xor (password[pos2] == letter)) 1 else 0
    }

    return output
}

fun main() {
    println(partA())
    println(partB())
}