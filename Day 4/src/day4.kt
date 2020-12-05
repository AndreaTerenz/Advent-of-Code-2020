import java.io.File

fun partA() : Int {
    var output = 0
    var lastPassport = ""
    val requiredFields = arrayOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    File("input.txt").forEachLine { line ->
        if (line.isNotBlank()) {
            //accumulate into last passport
            if (lastPassport != "")
                lastPassport += " "
            lastPassport += line
        }
        else {
            //check last passport
            if (checkPassport(requiredFields.toMutableList(), lastPassport)) output++

            lastPassport = ""
        }
    }

    //there may not have been an empty line at the end of the file to trigger the final check
    if (checkPassport(requiredFields.toMutableList(), lastPassport)) output++

    return output
}

fun checkPassport(fields: MutableList<String>, passport: String): Boolean {
    //check last passport
    println(passport)

    val tokens = passport.split(Regex(":(#*[a-zA-Z0-9]*)(\\s|\$)"))
            .filter { tok -> tok.isNotBlank() && tok != "cid" }

    println(tokens)

    tokens.forEach { tok -> fields.remove(tok) }
    return fields.size == 0
}

fun main() {
    println(partA())
}