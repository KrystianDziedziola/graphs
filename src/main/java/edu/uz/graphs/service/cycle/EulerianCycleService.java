package edu.uz.graphs.service.cycle;

import edu.uz.graphs.model.graph.Edge;
import edu.uz.graphs.model.graph.EulerianType;
import edu.uz.graphs.model.graph.Graph;
import edu.uz.graphs.model.graph.GraphCycleInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;

/**
 * Uses Fleury's algorithm: https://www.geeksforgeeks.org/fleurys-algorithm-for-printing-eulerian-path/
 */
@Service
public class EulerianCycleService {

    GraphCycleInfo cycle(final Graph graph) {
        final Graph graphCopy = Graph.copyOf(graph);

        final EulerianType graphType = graphCopy.eulerianType();
        final Optional<String> startVertexOptional = getStartVertex(graphCopy, graphType);

        final List<Edge> cycle = new ArrayList<>();
        if (!startVertexOptional.isPresent()) {
            return new GraphCycleInfo(graphType, cycle);
        }

        final String startVertex = startVertexOptional.get();
        cycle(startVertex, graphCopy, cycle);

        return new GraphCycleInfo(graphType, cycle);
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

        if (graph.isBridge(startVertex, edge)) {
            return false;
        }

        return true;
    }

    private boolean isOnlyOne(final List<Edge> adjacentEdges) {
        return adjacentEdges.size() == 1;
    }
}
