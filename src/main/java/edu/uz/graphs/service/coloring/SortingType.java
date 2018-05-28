package edu.uz.graphs.service.coloring;

public enum SortingType {

    ALPHABETICALLY("Alphabetically"),
    DEGREE_DESCENDING("Descending by vertex degree"),
    RANDOM("Random");

    private final String name;

    SortingType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
