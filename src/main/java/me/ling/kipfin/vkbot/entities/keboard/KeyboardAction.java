package me.ling.kipfin.vkbot.entities.keboard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KeyboardAction {

    @JsonProperty
    protected String type;

    @JsonProperty
    protected String label;

    public KeyboardAction() {
    }

    public KeyboardAction(String type, String label) {
        this.type = type;
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }
}
