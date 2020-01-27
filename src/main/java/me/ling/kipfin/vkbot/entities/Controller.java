package me.ling.kipfin.vkbot.entities;

/**
 * Контроллер
 */
public abstract class Controller {

    /**
     * Тестирует контрллер
     * @param text  - полученный текст
     * @param user  - пользователь
     * @return      - результат проверки
     */
    public abstract boolean test(String text, BotUser user);

    /**
     * Выполняет метод и возвращает ответ бота
     * @param text  - текст
     * @param user  - пользователь
     * @return      - ответ бота
     */
    public abstract String execute(String text, BotUser user);
}
