package edu.uz.graphs.service.coloring;

import edu.uz.graphs.model.graph.Color;
import edu.uz.graphs.model.graph.Graph;
import edu.uz.graphs.model.graph.Vertex;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GraphColoringService {

    public List<Vertex> color(final Graph graph, final SortingType sortingType) {
        final List<String> sortedVertices = sortVertices(graph, sortingType);

        final List<Vertex> coloredVertices = new ArrayList<>();
        sortedVertices.forEach(vertex -> {
            final Color color = chooseColor(vertex, coloredVertices, graph);
            final Vertex coloredVertex = new Vertex(vertex, color);
            coloredVertices.add(coloredVertex);
        });
        return coloredVertices;
    }

    private Color chooseColor(final String vertex, final List<Vertex> coloredVertices,
        final Graph graph) {
        final List<String> adjacentVertices = graph.getAdjacentVertices(vertex);
        final List<Color> unavailableColors = getUnavailableColors(coloredVertices,
            adjacentVertices);

        return assignColor(unavailableColors);
    }

    private Color assignColor(final List<Color> unavailableColors) {
        return Arrays.stream(Color.values())
            .filter(color -> !unavailableColors.contains(color))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Out of available colors"));
    }

    private List<Color> getUnavailableColors(final List<Vertex> coloredVertices,
        final List<String> adjacentVertices) {
        final List<Color> unavailableColors = new ArrayList<>();
        adjacentVertices.forEach(adjacentVertex -> {
            final Optional<Color> adjacentVertexColor = determineColor(adjacentVertex,
                coloredVertices);
            adjacentVertexColor.ifPresent(unavailableColors::add);
        });

        return unavailableColors;
    }

    private Optional<Color> determineColor(final String vertex,
        final List<Vertex> coloredVertices) {
        return coloredVertices
            .stream()
            .filter(coloredVertex -> coloredVertex.getName().equals(vertex))
            .findFirst()
            .map(Vertex::getColor);
    }

    private List<String> sortVertices(final Graph graph, final SortingType sortingType) {
        final List<String> vertices = graph.getVertices();
        switch (sortingType) {
            case RANDOM:
                Collections.shuffle(vertices);
                return vertices;
            case ALPHABETICALLY:
                return vertices
                    .stream()
                    .sorted(Comparator.naturalOrder())
                    .collect(Collectors.toList());
            case DEGREE_DESCENDING:
                return vertices
                    .stream()
                    .sorted(byDegreeDescending(graph))
                    .collect(Collectors.toList());
            default:
                throw new IllegalArgumentException("Wrong sorting type");
        }
    }

    private Comparator<? super String> byDegreeDescending(final Graph graph) {
        return (v1, v2) -> {
            final Integer firstVertexDegree = graph.countVertexDegree(v1);
            final Integer secondVertexDegree = graph.countVertexDegree(v2);
            return secondVertexDegree.compareTo(firstVertexDegree);
        };
    }
}
