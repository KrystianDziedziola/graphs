package edu.uz.graphs.model;

import java.util.List;

public class PathResult {

    private String destinationNode;
    private final int length;
    private final List<String> path;

    public PathResult(String destinationNode, int length, List<String> path) {
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
