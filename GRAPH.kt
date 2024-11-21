package MST

import java.io.File

data class Edge(val source: String, val destination: String, val distance: Int)

class Graph {
    private val edges: MutableList<Edge> = mutableListOf()
    private val vertices: MutableSet<String> = mutableSetOf()

    fun addEdge(source: String, destination: String, distance: Int) {
        edges.add(Edge(source, destination, distance))
        vertices.add(source)
        vertices.add(destination)
    }

    fun kruskalMST(): List<Edge> {
        // Sort edges based on their weight (distance)
        edges.sortBy { it.distance }

        val parent = mutableMapOf<String, String>()
        val rank = mutableMapOf<String, Int>()

        // Initialize the union-find structure
        for (vertex in vertices) {
            parent[vertex] = vertex
            rank[vertex] = 0
        }

        val mstEdges = mutableListOf<Edge>()

        for (edge in edges) {
            val rootSource = find(parent, edge.source)
            val rootDestination = find(parent, edge.destination)

            // If they are in different sets, include this edge in the MST
            if (rootSource != rootDestination) {
                mstEdges.add(edge)
                union(parent, rank, rootSource, rootDestination)
            }
        }

        return mstEdges
    }

    private fun find(parent: MutableMap<String, String>, vertex: String): String {
        if (parent[vertex] != vertex) {
            parent[vertex] = find(parent, parent[vertex]!!)
        }
        return parent[vertex]!!
    }

    private fun union(parent: MutableMap<String, String>, rank: MutableMap<String, Int>, source: String, destination: String) {
        val rootSource = find(parent, source)
        val rootDestination = find(parent, destination)

        if (rank[rootSource]!! < rank[rootDestination]!!) {
            parent[rootSource] = rootDestination
        } else if (rank[rootSource]!! > rank[rootDestination]!!) {
            parent[rootDestination] = rootSource
        } else {
            parent[rootDestination] = rootSource
            rank[rootSource] = rank[rootSource]!! + 1
        }
    }





    companion object {
        fun fromFile(filename: String): Graph {
            val graph = Graph()
            File(filename).forEachLine { line ->
                val parts = line.trim().split("\\s+".toRegex())
                if (parts.size >= 3) {
                    val source = parts[0]
                    val destination = parts[1]
                    val distance = parts[2].toIntOrNull()
                    if (distance != null) {
                        graph.addEdge(source, destination, distance)
                    }
                }
            }
            return graph
        }
    }
}





fun main() {
    try {
        // Specify the path to your dataset file
        val filePath = "src/datasetCapitals.txt"  // Adjust this path as needed

        // Create a graph from the file
        val graph = Graph.fromFile(filePath)

        // Calculate Minimum Spanning Tree (MST)
        val mstEdges = graph.kruskalMST()

        // Print the edges in the MST
        println("Minimum Spanning Tree (MST):")
        var totalDistance = 0
        for (edge in mstEdges) {
            println("${edge.source} -- ${edge.destination}: ${edge.distance} km")
            totalDistance += edge.distance
        }
        println("Total Distance of MST: $totalDistance km")
    } catch (e: Exception) {
        println("An error occurred: ${e.message}")
    }
}