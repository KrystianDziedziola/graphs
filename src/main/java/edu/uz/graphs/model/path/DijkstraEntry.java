package edu.uz.graphs.model.path;

public class DijkstraEntry {

    private final Integer shortestDistance;
    private final String previousVertex;

    public DijkstraEntry(final Integer shortestDistance, final String previousVertex) {
        this.shortestDistance = shortestDistance;
        this.previousVertex = previousVertex;
    }

    public Integer getShortestDistance() {
        return shortestDistance;
    }

    public String getPreviousVertex() {
        return previousVertex;
    }

    public DijkstraEntry withDistance(final Integer distance) {
        return new DijkstraEntry(distance, previousVertex);
    }

    public DijkstraEntry withPreviousVertex(final String vertex) {
        return new DijkstraEntry(shortestDistance, vertex);
    }
}
