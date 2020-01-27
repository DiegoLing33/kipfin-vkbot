package me.ling.kipfin.vkbot.entities;

import me.ling.kipfin.vkbot.entities.keboard.Button;
import me.ling.kipfin.vkbot.entities.keboard.Keyboard;

/**
 * Клавиатуры
 */
public class Keyboards {

    public static final Keyboard start =
            new Keyboard()
                    .add(new Button("Выбрать группу"));

    public static final Keyboard student =
            new Keyboard()
                    .add(new Button("Сегодня"), 0)
                    .add(new Button("Завтра"), 0)
                    .add(new Button("Сейчас"), 1)
                    .add(new Button("Неделя"), 1)
                    .add(new Button("Дополнительно", "secondary"), 2);

}
