package edu.uz.graphs.model.graph;

public class Vertex {

    private final String name;
    private final Color color;

    public Vertex(final String name, final Color color) {
        this.name = name;
        this.color = color;
    }


    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", name, color);
    }
}
