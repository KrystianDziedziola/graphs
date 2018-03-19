package edu.uz.graphs.model.graph;

import java.util.Objects;

public class Edge {

    private final String source;
    private final String target;
    private final Integer weight;

    Edge(final String source, final String target, final Integer weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    Edge(final String source, final String target) {
        this(source, target, null);
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getName() {
        return source + target;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Edge edge = (Edge) o;
        return Objects.equals(source, edge.source) &&
            Objects.equals(target, edge.target) &&
            Objects.equals(weight, edge.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target);
    }
}
