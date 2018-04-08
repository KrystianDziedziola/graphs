package edu.uz.graphs.service;

import edu.uz.graphs.model.graph.Edge;
import edu.uz.graphs.model.graph.EulerianGraphInfo;
import edu.uz.graphs.model.graph.EulerianType;
import edu.uz.graphs.model.graph.Graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class EulerianCycleService {

    public EulerianGraphInfo eulerianCycle(final Graph graph) {
        final Graph graphCopy = Graph.copyOf(graph);

        final EulerianType graphType = graphCopy.eulerianType();
        final Optional<String> startVertexOptional = getStartVertex(graphCopy, graphType);

        final List<Edge> cycle = new ArrayList<>();
        if (!startVertexOptional.isPresent()) {
            return new EulerianGraphInfo(graphType, cycle);
        }

        final String startVertex = startVertexOptional.get();
        cycle(startVertex, graphCopy, cycle);

        return new EulerianGraphInfo(graphType, cycle);
    }

    private void cycle(final String startVertex, final Graph graph, final List<Edge> cycle) {
        final List<Edge> adjacentEdges = graph.getAdjacentEdges(startVertex, true);

        adjacentEdges.forEach(edge -> {
            if (isValidNextEdge(edge, startVertex, cycle, adjacentEdges, graph)) {
                cycle.add(edge);
                graph.removeEdge(edge);
                cycle(edge.getOther(startVertex), graph, cycle);
            }
        });
    }

    private Optional<String> getStartVertex(final Graph graph, final EulerianType graphType) {
        if (EulerianType.EULERIAN.equals(graphType)) {
            return Optional.of(anyVertex(graph));
        } else if (EulerianType.SEMI_EULERIAN.equals(graphType)) {
            return Optional.of(someOddVertex(graph));
        } else {
            return Optional.empty();
        }
    }

    private String someOddVertex(final Graph graph) {
        final List<String> vertices = graph.getOddDegreeVertices();
        return vertices.get(new Random().nextInt(vertices.size()));
    }

    private String anyVertex(final Graph graph) {
        final List<String> vertices = new ArrayList<>(graph.getVertices());
        Collections.shuffle(vertices);
        return vertices.iterator().next();
    }

    private boolean isValidNextEdge(final Edge edge, final String startVertex,
        final List<Edge> cycle, final List<Edge> adjacentEdges, final Graph graph) {

        if (cycle.contains(edge)) {
            return false;
        }
        if (isOnlyOne(adjacentEdges)) {
            return true;
        }

        if (isBridge(startVertex, edge, graph)) {
            return false;
        }

        return true;
    }

    private boolean isBridge(final String vertex, final Edge edge, final Graph graph) {
        final Graph graphCopy = Graph.copyOf(graph);

        final Integer reachableVerticesCount = graphCopy.countReachableVertices(vertex);
        graphCopy.removeEdge(edge);
        final Integer reachableVerticesCountWithoutEdge = graphCopy.countReachableVertices(vertex);

        return reachableVerticesCount > reachableVerticesCountWithoutEdge;
    }

    private boolean isOnlyOne(final List<Edge> adjacentEdges) {
        return adjacentEdges.size() == 1;
    }
}
