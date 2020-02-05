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
     * Данный метод является главным в контроллере - он отвечает за роутинг. Если данный метод возвращает
     * значение true, будет запущен метод Controller::execute данного класса.
     *
     * @warning метод `Controller::execute` данного класса НИКОГДА не будет запущен, кроме случаев, когда
     *   `Controller::test` возвращает true. <b>Нет необходимости делнть двойные проверки</b>!
     *
     * @param text - полученный текст
     * @param user - пользователь
     * @param args - аргументы
     * @return - результат проверки
     */
    public abstract boolean test(String text, BTUser user, ControllerArgs args);

    /**
     * Выполняет метод и возвращает ответ
     *
     * Данный метод выполняется только после выполнения метода `Controller::test`!
     * @see Controller
     * @see Controller::test
     *
     * @param text - текст
     * @param user - пользователь
     * @param args - аргументы контроллера
     * @return - ответ бота
     */
    protected abstract Object execute(String text, BTUser user, ControllerArgs args);

    /**
     * Запрашивает исполнение
     *
     * @param text  - текст
     * @param user  - пользователь
     * @param args  - агументы
     * @return      - возвращает ответ контроллера или null
     */
    @Nullable
    public Object requestExecute(String text, BTUser user, ControllerArgs args) {
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
