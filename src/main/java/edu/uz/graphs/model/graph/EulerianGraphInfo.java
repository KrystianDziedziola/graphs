package edu.uz.graphs.model.graph;

import java.util.List;

public class EulerianGraphInfo {

    private final EulerianType type;
    private final List<Edge> cycle;

    public EulerianGraphInfo(final EulerianType type, final List<Edge> cycle) {
        this.type = type;
        this.cycle = cycle;
    }

    public EulerianType getType() {
        return type;
    }

    public List<Edge> getCycle() {
        return cycle;
    }
}
