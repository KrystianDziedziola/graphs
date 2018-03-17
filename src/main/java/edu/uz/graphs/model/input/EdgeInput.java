package edu.uz.graphs.model.input;

public class EdgeInput {

    private String source;
    private String target;
    private Integer weight;

    public EdgeInput() {
    }

    EdgeInput(final String source, final String target, final Integer weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public String getSource() {
        return source;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(final String target) {
        this.target = target;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(final Integer weight) {
        this.weight = weight;
    }
}
