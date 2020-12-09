import java.io.File
import kotlin.math.max

fun passToInt(p : String) : Int {
    //convert every F and L to a 0 and every B and R to a 1, then convert the string to an int using base 2
    return p.replace(Regex("([FL])"), "0").replace(Regex("([BR])"), "1").toInt(2)
}

fun partA() : Int {
    var maxID = -1

    //the row is contained in the 7 left-most bits of the pass and the column in the 3 right-most bits, therefore:
    //      the row is obtained by logically shifting right by 3 bits - so pass >> 3 = pass / 8 (because 8=2^3)
    //      the column is given by (pass - row*8)=(pass - pass/8)=pass % 8
    //      the id is defined as row*8 + column, but that means (pass/8)*8 + (pass%8)=pass (because of how int divisions work)
    //in conclusion, computing row, column and id is useless because you simply end up with the initial pass value
    File("input.txt").forEachLine { pass -> maxID = max(maxID, passToInt(pass)) }

    return maxID
}

fun partB() : Int {
    var id = -1
    var prev = -1

    File("input.txt")
        .readLines()
        .map{ pass -> passToInt(pass) } //convert each pass to the corresponding binary string and therefore number
        .sorted() //sort the pass list
        .forEach { passInt ->
            //if the difference between the current pass and the last one seen is more than one, then you've
            //found the missing pass (current one - 1)
            if (prev != -1 && id == -1 && passInt-prev > 1) id = passInt-1
            prev = passInt
        }

    return id
}

fun main() {
    println(partA())
    println(partB())
}