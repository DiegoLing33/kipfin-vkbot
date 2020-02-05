package me.ling.kipfin.vkbot.commands;

import me.ling.kipfin.vkbot.entities.BTUser;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Контроллер
 */
public abstract class Controller {

    /**
     * Тестирует контрллер
     *
     * @param text - полученный текст
     * @param user - пользователь
     * @param args - дополнительные аргументы
     * @return - результат проверки
     */
    public abstract boolean test(String text, BTUser user, ControllerArgs args);

    /**
     * Выполняет метод и возвращает ответ бота
     *
     * @param text - текст
     * @param user - пользователь
     * @return - ответ бота
     */
    protected abstract String execute(String text, BTUser user, ControllerArgs args);

    /**
     * Запрашивает исполнение
     *
     * @param text  - текст
     * @param user  - пользователь
     * @param args  - агументы
     * @return      - возвращает ответ контроллера или null
     */
    @Nullable
    public String requestExecute(String text, BTUser user, ControllerArgs args) {
        if (this.test(text, user, args))
            return execute(text, user, args);
        return null;
    }

    /**
     * Возвращает true, если есть совпадения
     *
     * @param text  - текст
     * @param alias - алиасы
     * @return - результат
     */
    public boolean testAlias(String text, String... alias) {
        return Arrays.stream(alias).anyMatch(s -> s.toLowerCase().equals(text.toLowerCase()));
    }
}
