import java.io.File

fun test(count : (String) -> Int) : Int {
    var output = 0
    var lastGroup = ""

    File("input.txt").forEachLine { line ->
        if (line.isNotBlank()) {
            //accumulate into last group
            lastGroup += "$line "
        }
        else {
            //add the count to the output
            output += count(lastGroup)
            lastGroup = ""
        }
    }

    //there may not be a newline at the end of the file to trigger the count for the very last group
    output += count(lastGroup)

    return output
}

fun main() {
    //convert the accumulated group into a set (to remove duplicated chars)
    val countA = { group : String -> group.replace(" ", "").toSet().size }

    val countB = { group : String ->
        var accumulator = setOf<Char>() //will keep track of

        group.split(" ") //split each group by individual responses
            .filter { tok -> tok.isNotBlank() } //remove empty tokens
            .forEachIndexed loop@{ idx, tok ->
                accumulator = when {
                    //if this is the very first token, the accumulator is initialized with its set of chars
                    idx == 0 -> tok.toSet()
                    //otherwise, if the accumulator isn't empty yet, intersect it with the char set of the current token
                    accumulator.isNotEmpty() -> accumulator intersect tok.toSet()
                    //if the accumulator is already empty, there's no point in checking the other tokens because some of
                    //the first ones already don't share all of their chars
                    else -> return@loop
                }
            }

        accumulator.size
    }

    println(test(countA))
    println(test(countB))
}