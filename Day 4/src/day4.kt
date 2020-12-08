import java.io.File

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
        val fields = requiredFields.toMutableList()
        passport.split(Regex(":(#*[a-zA-Z0-9]*)(\\s|\$)"))
                .forEach { tok -> fields.remove(tok) }

        (fields.size == 0)
    }

    val checkB = { passport: String ->
        var passportGUD = true
        val fields = requiredFields.toMutableList()
        passport.split(" ")
                .filter { tok -> tok.isNotBlank() && !Regex("cid:.*").matches(tok)}
                .forEach { tok ->
                    if (passportGUD) {
                        val id = tok.split(":")[0]
                        val value = tok.split(":")[1]

                        var valueGUD = true

                        when (id) {
                            "byr" -> valueGUD = (value.toInt() in 1920..2002)
                            "iyr" -> valueGUD = (value.toInt() in 2010..2020)
                            "eyr" -> valueGUD = (value.toInt() in 2020..2030)
                            "hcl" -> valueGUD = (Regex("#[a-f0-9]{6}").matches(value))
                            "ecl" -> valueGUD = (value in arrayOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth"))
                            "pid" -> valueGUD = (Regex("[0-9]{9}").matches(value))
                            "hgt" -> if (Regex("[0-9]+(cm|in)").matches(value)) {
                                val v = value.substring(0, value.length-2).toInt()
                                when (value.substring(value.length-2)) {
                                    "cm" -> valueGUD = (v in 150..193)
                                    "in" -> valueGUD = (v in 59..76)
                                }
                            } else valueGUD = false
                        }

                        passportGUD = passportGUD && fields.contains(id) && valueGUD
                        fields.remove(id)
                    }
                }

        passportGUD && (fields.size == 0)
    }

    println(test(checkA))
    println(test(checkB))
}