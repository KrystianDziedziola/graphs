package edu.uz.graphs.model.graph;

import java.util.List;

public class GraphCycleInfo {

    private final String type;
    private final String cycle;

    public GraphCycleInfo(final EulerianType type, final List<Edge> cycle) {
        this.type = type.name();
        this.cycle = cycle.toString();
    }

    public GraphCycleInfo(final HamiltonianType type, final List<String> cycle) {
        this.type = type.name();
        this.cycle = cycle.toString();
    }

    public String getType() {
        return type;
    }

    public String getCycle() {
        return cycle;
    }
}
