package edu.uz.graphs.model.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

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
        final Edge inverted = edge.inverted();
        if (edges.contains(inverted)) {
            edges.remove(inverted);
        }
    }

    public EulerianType eulerianType() {
        final List<String> oddDegreeVertexes = new ArrayList<>();
        vertices.forEach(vertex -> {
            final Integer degree = countDegree(vertex);
            System.out.format("Vertex: %s, degree: %s\n", vertex, degree);
            if (isOdd(degree)) {
                oddDegreeVertexes.add(vertex);
            }
        });

        return createType(oddDegreeVertexes);
    }

    private EulerianType createType(final List<String> oddDegreeVertexes) {
        final Integer numberOfOddDegreeVertexes = oddDegreeVertexes.size();

        switch (numberOfOddDegreeVertexes) {
            case 0:
                return EulerianType.EULERIAN;
            case 2:
                return EulerianType.SEMI_EULERIAN;
            default:
                return EulerianType.NON_EULERIAN;
        }
    }

    private boolean isOdd(final Integer degree) {
        return degree % 2 == 1;
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

    private Integer countDegree(final String vertex) {
        final AtomicInteger degree = new AtomicInteger();
        edges.forEach(edge -> {
            if (isConnected(vertex, edge)) {
                degree.incrementAndGet();
            }
        });
        return degree.intValue();
    }

    private boolean isConnected(final String vertex, final Edge edge) {
        return vertex.equals(edge.getSource()) || vertex.equals(edge.getTarget());
    }
}
