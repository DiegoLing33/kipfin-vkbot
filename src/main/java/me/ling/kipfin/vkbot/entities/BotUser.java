package me.ling.kipfin.vkbot.entities;

import me.ling.kipfin.core.log.Logger;
import me.ling.kipfin.core.log.WithLogger;
import me.ling.kipfin.vkbot.database.BotValuesDB;
import me.ling.kipfin.vkbot.entities.keboard.Button;
import me.ling.kipfin.vkbot.entities.keboard.Keyboard;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Бот
 */
public class BotUser extends WithLogger {

    /**
     * Пользователи
     */
    protected static Map<Integer, BotUser> users = new HashMap<>();

    /**
     * Возвращает ссылку на пользователя
     *
     * @param userId - ID пользователя
     * @return - ссылка на пользователя
     */
    public static BotUser getInstance(Integer userId) {
        Logger.log("Получение информации про ID", userId, "...");
        if (!users.containsKey(userId)) new BotUser(userId);
        return users.get(userId);
    }

    /**
     * Идентификатор пользователя
     */
    protected final Integer userId;

    @NotNull
    protected Keyboard keyboard = Keyboards.start;

    /**
     * Сохраненное в базе значение
     */
    protected String value = "";

    /**
     * Контруктор
     *
     * @param userId - идентификатор пользователя
     */
    public BotUser(Integer userId) {
        super("BotUser#" + userId);

        this.userId = userId;
        this.log("Создание нового пользователя");
        if (!BotUser.users.containsKey(userId)) BotUser.users.put(userId, this);

        this.reload();
    }

    /**
     * Перезагружает данные пользователя из БД
     */
    public void reload(){
        var value = this.getSavedSelection();
        if (value != null) this.log("Инициилизация:", value);
        this.value = value != null ? value : "";
    }

    /**
     * Возвращает true, если пользователь - студент
     *
     * @return - результат проверки
     */
    public boolean isStudent() {
        return this.value.contains("-");
    }

    /**
     * Возвращает true, если пользователь - преподаватель
     *
     * @return - результат проверки
     */
    public boolean isTeacher() {
        return !this.value.contains("-") && this.value.contains(" ");
    }

    /**
     * Возвращает сохраненный выбор по VK id
     *
     * @return сохраненный выбор
     */
    @Nullable
    public String getSavedSelection() {
        try {
            return BotValuesDB.shared.getValue("vkid", this.getUserId().toString()).getValue();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Возвращает клавиатуру пользователя
     *
     * @return - объект Keyboard
     */
    @NotNull
    public Keyboard getKeyboard() {
        return keyboard;
    }

    /**
     * Устанавливает клавиатуру
     * @param keyboard - клавиатура
     */
    public void setKeyboard(@NotNull Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    /**
     * Возвращает идентификатор пользователя
     *
     * @return - идентификатор пользователя
     */
    public Integer getUserId() {
        return userId;
    }
}
