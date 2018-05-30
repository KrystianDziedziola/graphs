package edu.uz.graphs.service.path;

import com.google.common.collect.Lists;
import edu.uz.graphs.model.graph.Graph;
import edu.uz.graphs.model.path.DijkstraEntry;
import edu.uz.graphs.model.path.PathResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ShortestPathService {

    private static final int INITIAL_DISTANCE = 0;

    public List<PathResult> findShortestPathsToEveryNode(final String rootNode, final Graph graph) {
        final List<String> vertices = graph.getVertices();
        final List<String> visitedVertices = startingVisitedNodes(rootNode);
        final Map<String, DijkstraEntry> table = createInitialTable(rootNode, vertices);

        for (int i = 0; i < visitedVertices.size(); i++) {
            final String currentVertex = visitedVertices.get(i);
            final List<String> unvisitedAdjacentVertices = getUnvisitedAdjacentVertices(
                currentVertex,
                graph,
                visitedVertices);

            unvisitedAdjacentVertices.forEach(vertex -> {
                final DijkstraEntry previousNodeEntry = table.get(currentVertex);

                final Integer cost =
                    graph.getWeight(currentVertex, vertex) + previousNodeEntry
                        .getShortestDistance();

                final DijkstraEntry entry = table.get(vertex);
                final Integer currentShortestDistance = entry.getShortestDistance();

                if (Objects.isNull(currentShortestDistance) || cost < currentShortestDistance) {
                    final DijkstraEntry updatedEntry = entry.withDistance(cost)
                        .withPreviousVertex(currentVertex);
                    table.put(vertex, updatedEntry);
                }
            });

            final String unvisitedWithSmallestDistance = unvisitedWithSmallestDistance(
                unvisitedAdjacentVertices, table);

            if (unvisitedAdjacentVertices.isEmpty()) {
                break;
            }
            visitedVertices.add(unvisitedWithSmallestDistance);
        }

        return createPathResultsFromTable(rootNode, table, vertices);
    }

    private List<PathResult> createPathResultsFromTable(final String rootNode,
        final Map<String, DijkstraEntry> table, final List<String> vertices) {

        final List<String> destinationVertices = vertices
            .stream()
            .filter(vertex -> !vertex.equals(rootNode))
            .collect(Collectors.toList());

        return destinationVertices
            .stream()
            .map(destination -> pathResult(destination, table))
            .collect(Collectors.toList());
    }

    private PathResult pathResult(final String destination,
        final Map<String, DijkstraEntry> table) {

        final List<String> path = new ArrayList<>();

        DijkstraEntry entry = table.get(destination);
        final int length = entry.getShortestDistance();
        path.add(destination);

        while (Objects.nonNull(entry.getPreviousVertex())) {
            final String previousVertex = entry.getPreviousVertex();
            entry = table.get(previousVertex);
            path.add(previousVertex);
        }

        return new PathResult(destination, length, Lists.reverse(path));
    }

    private String unvisitedWithSmallestDistance(final List<String> unvisitedAdjacentVertices,
        final Map<String, DijkstraEntry> table) {
        Integer minDistance = null;
        String minDistanceNode = null;

        for (final String vertex : unvisitedAdjacentVertices) {

            if (table.containsKey(vertex)) {
                final Integer distance = table.get(vertex).getShortestDistance();

                if (Objects.isNull(minDistance) || distance < minDistance) {
                    minDistance = distance;
                    minDistanceNode = vertex;
                }
            }
        }
        return minDistanceNode;
    }


    private Map<String, DijkstraEntry> createInitialTable(final String rootNode,
        final List<String> vertices) {

        final Map<String, DijkstraEntry> table = new HashMap<>();
        vertices.forEach(vertex -> {
            Integer distance = null;
            if (vertex.equals(rootNode)) {
                distance = INITIAL_DISTANCE;
            }
            table.put(vertex, new DijkstraEntry(distance, null));
        });

        return table;
    }

    private List<String> getUnvisitedAdjacentVertices(final String rootNode, final Graph graph,
        final List<String> visitedVertices) {

        final List<String> adjacentVertices = graph.getAdjacentVertices(rootNode);
        adjacentVertices.removeAll(visitedVertices);
        return adjacentVertices;
    }

    private List<String> startingUnvisitedNodes(final String rootNode, final Graph graph) {
        return graph
            .getVertices()
            .stream()
            .filter(vertex -> !vertex.equals(rootNode))
            .collect(Collectors.toList());
    }

    private List<String> startingVisitedNodes(final String rootNode) {
        final List<String> visitedVertices = new ArrayList<>();
        visitedVertices.add(rootNode);
        return visitedVertices;
    }
}
