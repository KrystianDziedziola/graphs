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
        addEdge(nodeName, adjacentNodeName, null);
    }

    public void addEdge(final String source, final String target, final Integer weight) {
        final Edge edge = new Edge(source, target, weight);
        if (edges.contains(edge)) {
            edges.remove(edge);
        }
        removeDuplicatedEdge(source, target, edges);
        edges.add(edge);
    }

    public void removeVertex(final String name) {
        if (vertices.contains(name)) {
            vertices.remove(name);
        }
    }

    public void removeEdge(final String source, final String target) {
        final Edge edge = new Edge(source, target);
        if (edges.contains(edge)) {
            edges.remove(edge);
        }
    }

    public Set<String> getVertices() {
        return vertices;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    private void removeDuplicatedEdge(final String source, final String target,
        final Set<Edge> edges) {
        final Edge invertedEdge = new Edge(target, source, null);
        if (edges.contains(invertedEdge)) {
            edges.remove(invertedEdge);
        }
    }
}
