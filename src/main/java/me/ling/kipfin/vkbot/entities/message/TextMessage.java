package me.ling.kipfin.vkbot.entities.message;

import me.ling.kipfin.vkbot.entities.keboard.Keyboard;
import org.jetbrains.annotations.Nullable;

public class TextMessage {

    private final String text;
    @Nullable
    private final Keyboard keyboard;

    public TextMessage(String text, @Nullable Keyboard keyboard) {
        this.text = text;
        this.keyboard = keyboard;
    }

    public TextMessage(String text) {
        this.text = text;
        this.keyboard = null;
    }

    @Nullable
    public Keyboard getKeyboard() {
        return keyboard;
    }

    public String getText() {
        return text;
    }
}
