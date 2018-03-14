package edu.uz.graphs.model.graph;

public class Edge {

    private final String source;
    private final String target;

    public Edge(String source, String target) {
        this.source = source;
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public String getName() {
        return source + target;
    }
}
