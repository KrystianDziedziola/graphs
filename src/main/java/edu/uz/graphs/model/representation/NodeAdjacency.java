package edu.uz.graphs.model.representation;

import java.util.List;

public class NodeAdjacency {

    private final String nodeName;
    private final List<String> adjacentNodeNames;

    public NodeAdjacency(final String nodeName, final List<String> adjacentNodeNames) {
        this.nodeName = nodeName;
        this.adjacentNodeNames = adjacentNodeNames;
    }

    public String getNodeName() {
        return nodeName;
    }

    public List<String> getAdjacentNodeNames() {
        return adjacentNodeNames;
    }


}
