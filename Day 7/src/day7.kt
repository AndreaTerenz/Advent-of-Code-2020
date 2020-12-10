import java.io.File

fun linkColorFromStr(str : String) : String {
    return str.split(Regex("\\d ")).filter{ it.isNotBlank() }[0]
}

fun linkWeightFromStr(str : String) : Int {
    return str.split(Regex(" \\w"))[0].toInt()
}

fun readGraph() : Map<String, List<String>> {
    val output = mutableMapOf<String, List<String>>()

    File("input.txt").forEachLine { l ->
        val line = l.replace(Regex(" (bags|bag)\\.$"), "").split(" bags contain ")
        val color = line[0]
        val content = line[1].split(Regex(" (bags|bag), ")).filter { x -> x != "no other" }

        if (content.isNotEmpty()) output[color] = content
    }

    return output.toMap()
}

fun partA(graph : Map<String, List<String>>) : Int {
    val useful = mutableSetOf("shiny gold")

    fun search(node: String): Boolean {
        graph[node]?.map{ link -> linkColorFromStr(link) }?.forEach { color ->
            if (useful.contains(color) || search(color)) {
                useful.add(node)
                return true
            }
        }

        return false
    }

    return graph.filter { n -> n.key != "shiny gold" && search(n.key) }.size
}

fun partB(graph : Map<String, List<String>>) : Int {
    fun foo(node : String) : Int {
        var tot = 1

        if (graph[node] != null) graph[node]?.forEach { link -> tot += foo(linkColorFromStr(link)) * linkWeightFromStr(link) }

        return tot
    }

    return foo("shiny gold")-1 //minus one because the shiny gold bag itself doesn't count
}

fun main() {
    val graph = readGraph()
    println(partA(graph))
    println(partB(graph))
}