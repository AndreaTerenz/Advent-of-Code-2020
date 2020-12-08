import java.io.File

//the only thing that changes between part A and B is how a passport is checked
//so the overall logic for reading and checking the whole file is the same
//the check argument is the lambda function to use to validate a passport
fun test(check : (String) -> Boolean) : Int {
    var output = 0
    var lastPassport = ""

    File("input.txt").forEachLine { line ->
        if (line.isNotBlank()) {
            //accumulate into last passport
            if (lastPassport != "")
                lastPassport += " "
            lastPassport += line
        }
        else {
            //check last passport
            if (check(lastPassport)) output++
            lastPassport = ""
        }
    }
    //there may not have been an empty line at the end of the file to trigger the final check
    if (check(lastPassport)) output++

    return output
}

fun main() {
    val requiredFields = arrayOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    val checkA = { passport: String ->
        val fields = requiredFields.toMutableList() //mutable copy of the required fields list
        passport.split(Regex(":(#*[a-zA-Z0-9]*)(\\s|\$)"))  //this regex filters out the ids of the fields contained
                                                                   //in the passport
                .forEach { tok -> fields.remove(tok) } //if tok is present in fields it will be removed

        //if all values in fields were removed, then the passport had all required fields
        (fields.size == 0)
    }

    val checkB = { passport: String ->
        var passportOk = true
        val fields = requiredFields.toMutableList()
        passport.split(" ")
                //ignore all cid tokens
                .filter { tok -> tok.isNotBlank() && !Regex("cid:.*").matches(tok)}
                .forEach { tok ->
                    //check the token only if the passport may still be OK
                    if (passportOk) {
                        val id = tok.split(":")[0]
                        val value = tok.split(":")[1]

                        var valueOk = true

                        //checks defined in the puzzle prompt
                        when (id) {
                            "byr" -> valueOk = (value.toInt() in 1920..2002)
                            "iyr" -> valueOk = (value.toInt() in 2010..2020)
                            "eyr" -> valueOk = (value.toInt() in 2020..2030)
                            "hcl" -> valueOk = (Regex("#[a-f0-9]{6}").matches(value))
                            "ecl" -> valueOk = (value in arrayOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth"))
                            "pid" -> valueOk = (Regex("[0-9]{9}").matches(value))
                            "hgt" -> if (Regex("[0-9]+(cm|in)").matches(value)) {
                                        val v = value.substring(0, value.length-2).toInt()
                                        when (value.substring(value.length-2)) {
                                            "cm" -> valueOk = (v in 150..193)
                                            "in" -> valueOk = (v in 59..76)
                                            else -> valueOk = false
                                        }
                                    } else valueOk = false
                        }

                        //the passport is still OK only if the id is a valid field and the value is correct
                        passportOk = passportOk && fields.contains(id) && valueOk
                        fields.remove(id)
                    }
                }

        //if all values in fields were removed, then the passport had all required fields
        passportOk && (fields.size == 0)
    }

    println(test(checkA))
    println(test(checkB))
}