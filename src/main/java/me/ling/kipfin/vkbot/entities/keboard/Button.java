package me.ling.kipfin.vkbot.entities.keboard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Button {

    @JsonProperty
    protected String color = "primary";

    @JsonProperty
    protected KeyboardAction action;

    public Button() {
    }

    public Button(KeyboardAction action, String color) {
        this.color = color;
        this.action = action;
    }

    public Button(KeyboardAction action) {
        this.action = action;
    }

    public Button(String text, String color){
        this.action = new KeyboardAction("text", text);
        this.color = color;
    }

    public Button(String text){
        this.action = new KeyboardAction("text", text);
    }
}
