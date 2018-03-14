package edu.uz.graphs.model.input;

public class Input {

    private String text;
    private InputType type;

    public InputType getType() {
        return type;
    }

    public void setType(InputType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
