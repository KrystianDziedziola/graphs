package edu.uz.graphs.model.graph;

import java.util.List;

public class EulerianGraphInfo {

    private final EulerianType type;
    private final List<String> cycle;

    public EulerianGraphInfo(final EulerianType type, final List<String> cycle) {
        this.type = type;
        this.cycle = cycle;
    }

    public EulerianType getType() {
        return type;
    }

    public List<String> getCycle() {
        return cycle;
    }
}
