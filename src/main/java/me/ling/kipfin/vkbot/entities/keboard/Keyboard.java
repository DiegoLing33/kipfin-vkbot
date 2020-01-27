package me.ling.kipfin.vkbot.entities.keboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {

    @JsonProperty("one_time")
    protected boolean oneTime = false;

    @JsonProperty
    List<List<Button>> buttons = new ArrayList<>();

    public Keyboard() {
    }

    public Keyboard(boolean oneTime, List<List<Button>> buttons) {
        this.oneTime = oneTime;
        this.buttons = buttons;
    }

    /**
     * Добавляет кнопку
     *
     * @param button - кнопка
     * @param row    - индекс строки
     * @return - Keyboard объект
     */
    public Keyboard add(Button button, @Nullable Integer row) {
        if (row == null) {
            this.buttons.add(new ArrayList<>(List.of(button)));
        } else {
            if (row + 1 > this.buttons.size()) this.buttons.add(new ArrayList<>());
            this.buttons.get(row).add(button);
        }
        return this;
    }

    /**
     * Добавляет кнопку
     *
     * @param button - кнопка
     * @return - Keyboard объект
     */
    public Keyboard add(Button button) {
        return this.add(button, null);
    }

    /**
     * Преобразует клавиатуру в JSON
     *
     * @return - JSON строка клавиатуры
     */
    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
