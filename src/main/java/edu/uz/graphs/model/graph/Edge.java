package edu.uz.graphs.model.graph;

import java.io.Serializable;
import java.util.Objects;

public class Edge implements Serializable {

    private final String source;
    private final String target;
    private final Integer weight;

    public Edge(final String source, final String target, final Integer weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public Edge(final String source, final String target) {
        this(source, target, null);
    }

    public boolean contains(final String vertex) {
        return target.equals(vertex) || source.equals(vertex);
    }

    public Edge inverted() {
        return new Edge(target, source);
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

    public String getOther(final String vertex) {
        if (target.equals(vertex)) {
            return source;
        }
        if (source.equals(vertex)) {
            return target;
        }

        throw new IllegalArgumentException("No vertex " + vertex + " in edge " + this);
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
            Objects.equals(target, edge.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", source, target);
    }
}
