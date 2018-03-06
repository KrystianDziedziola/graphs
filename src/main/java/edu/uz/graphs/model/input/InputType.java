package edu.uz.graphs.model.input;

public enum InputType {
    ADJACENCY_LIST("Adjacency list"),
    ADJACENCY_MATRIC("Adjacency matrix"),
    INCIDENCE_MATRIX("Incidence matrix");

    private final String name;

    InputType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
