package edu.uz.graphs.model.path;

import java.util.List;

public class PathResult {

    private final int length;
    private final List<String> path;
    private final String destinationNode;

    public PathResult(final String destinationNode, final int length, final List<String> path) {
        this.destinationNode = destinationNode;
        this.length = length;
        this.path = path;
    }

    public String getDestinationNode() {
        return destinationNode;
    }

    public int getLength() {
        return length;
    }

    public List<String> getPath() {
        return path;
    }

    @Override
    public String toString() {
        return String.format("%s: %s; length: %d", destinationNode, path, length);
    }
}
