package edu.uz.graphs.service;

import edu.uz.graphs.model.NodeAdjacency;
import edu.uz.graphs.model.graph.Graph;
import edu.uz.graphs.model.input.InputType;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GraphService {

    private final NodeAdjacencyFactory nodeAdjacencyFactory;

    public GraphService(NodeAdjacencyFactory nodeAdjacencyFactory) {
        this.nodeAdjacencyFactory = nodeAdjacencyFactory;
    }

    public Graph createGraph(String text, InputType type) {
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
        final List<NodeAdjacency> nodeAdjacencyList = nodeAdjacencyFactory.create(text);
        final Graph graph = new Graph();

        nodeAdjacencyList.forEach(nodeAdjacency -> graph.addVertex(nodeAdjacency.getNodeName()));
        nodeAdjacencyList.forEach(nodeAdjacency -> {
            final List<String> adjacentNodeNames = nodeAdjacency.getAdjacentNodeNames();
            adjacentNodeNames.forEach(
                adjacentNodeName -> graph.addEdge(nodeAdjacency.getNodeName(), adjacentNodeName));
        });

        return graph;
    }

    private Graph createFromAdjacencyMatrix(String text) {
        return new Graph();
    }

    private Graph createFromIncidenceMatrix(String text) {
        return new Graph();
    }
}
