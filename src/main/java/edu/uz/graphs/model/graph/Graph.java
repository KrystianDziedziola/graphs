package edu.uz.graphs.model.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.commons.lang3.SerializationUtils;

public class Graph implements Serializable {

    private final List<String> vertices;
    private final Set<Edge> edges;

    public Graph() {
        vertices = new ArrayList<>();
        edges = new HashSet<>();
    }

    public Graph(final List<String> vertices, final Set<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public static Graph copyOf(final Graph graph) {
        return SerializationUtils.clone(graph);
    }

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

//        todo: remove edges containing vertex
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

    public void removeEdge(final Edge edge) {
        removeEdge(edge.getSource(), edge.getTarget());
    }

    /**
     * Na podstawie twierdzenia Eulera
     */
    public EulerianType eulerianType() {
        final Integer numberOfOddDegreeVertexes = getOddDegreeVertices().size();

        switch (numberOfOddDegreeVertexes) {
            case 0:
                return EulerianType.EULERIAN;
            case 2:
                return EulerianType.SEMI_EULERIAN;
            default:
                return EulerianType.NON_EULERIAN;
        }
    }

    public List<String> getOddDegreeVertices() {
        final List<String> oddDegreeVertices = new ArrayList<>();
        vertices.forEach(vertex -> {
            final Integer degree = countVertexDegree(vertex);
            if (isOdd(degree)) {
                oddDegreeVertices.add(vertex);
            }
        });
        return oddDegreeVertices;
    }

    public List<Edge> getAdjacentEdges(final String vertex, final boolean randomOrder) {
        final List<Edge> edges = this.edges.stream()
            .filter(edge ->
                edge.getSource().equals(vertex) || edge.getTarget().equals(vertex))
            .collect(Collectors.toList());

        if (randomOrder) {
            Collections.shuffle(edges);
        }
        return edges;
    }

    public List<Edge> getAdjacentEdges(final String vertex) {
        return getAdjacentEdges(vertex, false);
    }

    public List<String> getAdjacentVertices(final String vertex) {
        final List<String> adjacentVertices = new ArrayList<>();
        edges.forEach(edge -> {
            final String source = edge.getSource();
            final String target = edge.getTarget();

            if (source.equals(vertex)) {
                adjacentVertices.add(target);
            }
            if (target.equals(vertex)) {
                adjacentVertices.add(source);
            }
        });

        return adjacentVertices;
    }

    public Integer countReachableVertices(final String vertex) {
        final List<String> visitedVertices = new ArrayList<>();
        return reachableVertices(vertex, visitedVertices);
    }

    public Integer countVertexDegree(final String vertex) {
        final AtomicInteger degree = new AtomicInteger();
        edges.forEach(edge -> {
            if (isConnected(vertex, edge)) {
                degree.incrementAndGet();
            }
        });
        return degree.intValue();
    }

    public boolean edgeExists(final String source, final String target) {
        final Edge edge = new Edge(source, target);
        return edges.contains(edge) || edges.contains(edge.inverted());
    }

    public Set<String> getNonIsolatedVertices() {
        return vertices.stream()
            .filter(vertex -> !countVertexDegree(vertex).equals(0))
            .collect(Collectors.toSet());
    }

    public boolean isBridge(final Edge edge) {
        return isBridge(edge.getSource(), edge);
    }

    public boolean isBridge(final String vertex, final Edge edge) {
        final Graph graphCopy = Graph.copyOf(this);

        final Integer reachableVerticesCount = graphCopy.countReachableVertices(vertex);
        graphCopy.removeEdge(edge);
        final Integer reachableVerticesCountWithoutEdge = graphCopy.countReachableVertices(vertex);

        return reachableVerticesCount > reachableVerticesCountWithoutEdge;
    }

    public List<String> deepFirstTraverse(final String vertex) {
        final List<String> visitedVertices = new ArrayList<>();
        dfs(vertex, visitedVertices);
        return visitedVertices;
    }

    private void dfs(final String vertex, final List<String> visitedVertices) {
        visitedVertices.add(vertex);

        final List<String> adjacentVertices = getAdjacentVertices(vertex);
        for (final String adjacentVertex : adjacentVertices) {
            if (!visitedVertices.contains(adjacentVertex)) {
                dfs(adjacentVertex, visitedVertices);
            }
        }
    }

    public List<String> getVertices() {
        return vertices;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public Integer getWeight(final String from, final String to) {
        final Edge edge = getEdge(from, to);
        return edge.getWeight();
    }

    private Edge getEdge(final String from, final String to) {
        return edges
            .stream()
            .filter(edge -> edge.equals(new Edge(from, to)) || edge.equals(new Edge(to, from)))
            .findFirst()
            .orElseThrow(edgeNotFound(from, to));
    }

    private Supplier<IllegalArgumentException> edgeNotFound(final String from, final String to) {
        return () -> new IllegalArgumentException(String.format("Edge %s-%s not found", from, to));
    }

    private int reachableVertices(final String vertex, final List<String> visitedVertices) {
        int numberOfReachableVertices = 1;

        visitedVertices.add(vertex);

        for (final String adjacentVertex : getAdjacentVertices(vertex)) {
            if (!visitedVertices.contains(adjacentVertex)) {
                numberOfReachableVertices += reachableVertices(adjacentVertex, visitedVertices);
            }
        }
        return numberOfReachableVertices;
    }

    private boolean isOdd(final Integer degree) {
        return degree % 2 == 1;
    }

    private void removeDuplicatedEdge(final String source, final String target,
        final Set<Edge> edges) {
        final Edge invertedEdge = new Edge(target, source, null);
        if (edges.contains(invertedEdge)) {
            edges.remove(invertedEdge);
        }
    }

    private boolean isConnected(final String vertex, final Edge edge) {
        return vertex.equals(edge.getSource()) || vertex.equals(edge.getTarget());
    }
}
