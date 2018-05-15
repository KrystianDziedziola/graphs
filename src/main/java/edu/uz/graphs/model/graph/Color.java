package edu.uz.graphs.model.graph;

public enum Color {

    RED("red"),
    GREEN("green"),
    BLUE("blue"),
    YELLOW("yellow"),
    BLACK("black"),
    ORANGE("orange"),
    BROWN("brown"),
    PINK("pink"),
    VIOLET("violet"),
    WHITE("white"),
    GRAY("gray");

    private final String value;

    Color(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
