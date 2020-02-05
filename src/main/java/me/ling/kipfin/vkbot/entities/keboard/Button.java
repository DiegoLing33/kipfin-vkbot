package me.ling.kipfin.vkbot.entities.keboard;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Кнопка на клавиатуре
 */
public class Button {

    public static final String POSITIVE = "positive";
    public static final String DEFAULT = "default";
    public static final String NEGATIVE = "negative";
    public static final String SECONDARY = "secondary";

    /**
     * Цвет кнопки
     */
    @JsonProperty
    protected String color = POSITIVE;

    /**
     * Действие
     */
    @JsonProperty
    protected KeyboardAction action;

    /**
     * Конструктор кнопки
     */
    public Button() { }

    /**
     * Конструктор кнопки
     * @param action    - дейтсвие
     * @param color     - цвет
     */
    public Button(KeyboardAction action, String color) {
        this.color = color;
        this.action = action;
    }

    /**
     * Конструктор кнопки. Цвет при этом будет positive
     * @param action    - действие
     */
    public Button(KeyboardAction action) {
        this.action = action;
    }

    /**
     * Конструктор кнопки
     * @param text  - текст кнопки
     * @param color - цвет кнопки
     */
    public Button(String text, String color){
        this.action = new KeyboardAction("text", text);
        this.color = color;
    }

    /**
     * Конструктор кнопки
     * @param text  - текст кнопки
     */
    public Button(String text){
        this.action = new KeyboardAction("text", text);
    }
}
