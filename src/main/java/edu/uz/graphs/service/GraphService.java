package edu.uz.graphs.service;

import edu.uz.graphs.model.graph.Graph;
import edu.uz.graphs.model.input.InputType;
import edu.uz.graphs.model.representation.NodeAdjacency;
import edu.uz.graphs.service.factory.GraphRepresentationFactory;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GraphService {

    private final GraphRepresentationFactory graphRepresentationFactory;

    public GraphService(final GraphRepresentationFactory graphRepresentationFactory) {
        this.graphRepresentationFactory = graphRepresentationFactory;
    }

    public Graph createGraphFromNodeAdjacency(final String text, final InputType type) {
        switch (type) {
            case ADJACENCY_LIST:
                return createFromAdjacencyList(text);
            case ADJACENCY_MATRIC:
                return createFromAdjacencyMatrix(text);
            case INCIDENCE_MATRIX:
                return createFromIncidenceMatrix(text);
            default:
                throw new RuntimeException("Undefined type");
        }
    }

    private Graph createFromAdjacencyList(final String text) {
        final List<NodeAdjacency> nodeAdjacencyList = graphRepresentationFactory
            .createFromAdjacencyList(text);
        return createGraphFromNodeAdjacency(nodeAdjacencyList);
    }

    private Graph createFromAdjacencyMatrix(final String text) {
        final List<NodeAdjacency> nodeAdjacencyList = graphRepresentationFactory
            .createFromAdjacencyMatrix(text);
        return createGraphFromNodeAdjacency(nodeAdjacencyList);
    }

    private Graph createFromIncidenceMatrix(final String text) {
        return graphRepresentationFactory.createGraphFromIncidenceMatrix(text);
    }

    private Graph createGraphFromNodeAdjacency(final List<NodeAdjacency> nodeAdjacencyList) {
        final Graph graph = new Graph();

        nodeAdjacencyList.forEach(nodeAdjacency -> graph.addVertex(nodeAdjacency.getNodeName()));
        nodeAdjacencyList.forEach(nodeAdjacency -> {
            final List<String> adjacentNodeNames = nodeAdjacency.getAdjacentNodeNames();
            adjacentNodeNames.forEach(
                adjacentNodeName -> graph.addEdge(nodeAdjacency.getNodeName(), adjacentNodeName));
        });

        return graph;
    }
}
