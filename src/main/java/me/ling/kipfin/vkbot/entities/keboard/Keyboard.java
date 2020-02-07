package me.ling.kipfin.vkbot.entities.keboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Клавиатура
 */
public class Keyboard {

    /**
     * Стартовая клавиатура
     */
    public static final Keyboard start =
            new Keyboard()
                    .add(new Button("Выбрать группу"), 0)
                    .add(new Button("Справка", Button.SECONDARY), 1);

    /**
     * Стартовая клавиатура (inline)
     */
    public static final Keyboard startInline =
            new Keyboard()
                    .add(new Button("Выбрать группу"), 0)
                    .add(new Button("Справка", Button.SECONDARY), 1)
                    .setInline(true);

    /**
     * Стартовая клавиатура студента
     */
    public static final Keyboard student =
            new Keyboard()
                    .add(new Button("Сегодня"), 0)
                    .add(new Button("Завтра"), 0)
                    .add(new Button("Сейчас"), 1)
                    .add(new Button("Дополнительно", Button.SECONDARY), 2);

    /**
     * Стартовая клавиатура учителя
     */
    public static final Keyboard teacher =
            new Keyboard()
                    .add(new Button("Сегодня"), 0)
                    .add(new Button("Завтра"), 0)
                    .add(new Button("Сейчас"), 1)
                    .add(new Button("Дополнительно", Button.SECONDARY), 2);

    /**
     * Клавиатура выбора курса
     */
    public static final Keyboard courses =
            new Keyboard()
                    .add(new Button("1 курс"), 0)
                    .add(new Button("2 курс"), 0)
                    .add(new Button("3 курс"), 1)
                    .add(new Button("4 курс"), 1)
                    .add(new Button("Домой", Button.SECONDARY), 2);


    /**
     * Клавиатура "Дополнительно" для студента
     */
    public static final Keyboard additionalStudent =
            new Keyboard()
                    .add(new Button("Классный руководитель"), 0)
                    .add(new Button("Неделя"), 1)
                    .add(new Button("Выбрать группу", Button.SECONDARY), 2)
                    .add(new Button("Справка", Button.SECONDARY), 2)
                    .add(new Button("Домой", Button.SECONDARY), 3);

    /**
     * Клавиатура "Дополнительно" для преподавателя
     */
    public static final Keyboard additionalTeacher =
            new Keyboard()
                    .add(new Button("Группа в руководстве"), 0)
                    .add(new Button("Справка", Button.SECONDARY), 1)
                    .add(new Button("Выбрать группу", Button.SECONDARY), 1)
                    .add(new Button("Домой", Button.SECONDARY), 2);

    public static final Keyboard whenUpdate
            = new Keyboard()
            .add(new Button("Завтра"))
            .setInline(true);

    @JsonProperty("one_time")
    protected boolean oneTime = false;

    @JsonProperty("inline")
    protected boolean inline = false;

    @JsonProperty
    List<List<Button>> buttons = new ArrayList<>();

    public Keyboard() {
    }

    public Keyboard(boolean oneTime, List<List<Button>> buttons) {
        this.oneTime = oneTime;
        this.buttons = buttons;
    }

    public Keyboard setInline(boolean inline) {
        this.inline = inline;
        return this;
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
