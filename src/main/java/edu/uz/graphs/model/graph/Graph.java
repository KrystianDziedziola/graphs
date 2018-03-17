package edu.uz.graphs.model.graph;

import java.util.HashSet;
import java.util.Set;

public class Graph {

    private final Set<String> vertices = new HashSet<>();
    private final Set<Edge> edges = new HashSet<>();

    public void addVertex(final String nodeName) {
        vertices.add(nodeName);
    }

    public void addEdge(final String nodeName, final String adjacentNodeName) {
        edges.add(new Edge(nodeName, adjacentNodeName));
    }

    public Set<String> getVertices() {
        return vertices;
    }

    public Set<Edge> getEdges() {
        return edges;
    }
}
