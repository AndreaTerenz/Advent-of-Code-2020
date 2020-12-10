import java.io.File

class Link(linkStr : String) {
    private val str = linkStr
    val color : String
        get() = this.str.split(Regex("\\d "))[1]
    val amount : String
        get() = this.str.split(Regex(" \\w"))[0]

    override fun equals(other: Any?): Boolean { return other is Link && this.color == other.color && this.amount == other.amount }
    override fun toString(): String { return "$amount $color" }
    override fun hashCode(): Int { return 31 * color.hashCode() + amount.hashCode() }
}

fun partA() : Int {

    val nodes = mutableMapOf<String, MutableList<Link>>()

    File("input.txt").forEachLine { l ->
        val line = l.replace(Regex(" (bags|bag)\\.$"), "").split(" bags contain ")
        val color = line[0]
        val content = line[1].split(Regex(" (bags|bag), ")).filter { x -> x != "no other" }

        if (content.isNotEmpty()) nodes[color] = content.map{ Link(it) }.toMutableList()
    }

    val useful = mutableSetOf("shiny gold")

    fun searchGold(node: String): Boolean {
        nodes[node]?.forEach { link ->
            if (useful.contains(link.color) || searchGold(link.color)) {
                useful.add(node)
                return true
            }
        }

        return false
    }

    return nodes.filter { n -> n.key != "shiny gold" && searchGold(n.key) }.size
}

fun main() {
    println(partA())
}