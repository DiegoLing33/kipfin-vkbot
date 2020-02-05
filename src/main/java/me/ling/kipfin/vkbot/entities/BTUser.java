package me.ling.kipfin.vkbot.entities;

import com.petersamokhin.bots.sdk.clients.Client;
import me.ling.kipfin.core.log.Logger;
import me.ling.kipfin.core.log.WithLogger;
import me.ling.kipfin.vkbot.utils.BotUserUtils;
import me.ling.kipfin.vkbot.database.BotValuesDB;
import me.ling.kipfin.vkbot.entities.keboard.Keyboard;
import me.ling.kipfin.vkbot.enums.BotUserSex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Бот
 */
public class BTUser extends WithLogger {

    /**
     * Преобразует строку шаблона в строку
     *
     * @param template - шаблон
     * @param BTUser  - пользователь
     * @return - реализованный шаблон
     */
    @NotNull
    public static String template(String template, BTUser BTUser) {
        return template
                .replaceAll("\\{name}", BTUser.getName())
                .replaceAll("\\{id}", BTUser.getUserId().toString())
                .replaceAll("\\{state}", BTUser.getState())
                .replaceAll("\\[n]", "\n")
                .replaceAll("\\[([^|]+)\\|([^)]+)]", BTUser.getSex().equals(BotUserSex.MALE) ? "$1" : "$2");
    }

    /**
     * Сохраненные в памяти польователи
     */
    protected static final Map<Integer, BTUser> users = new HashMap<>();

    /**
     * Возвращает ссылку на пользователя
     *
     * @param client - API
     * @param userId - ID пользователя
     * @return - ссылка на пользователя
     */
    public static BTUser getInstance(Client client, Integer userId) {
        Logger.log("Получение информации про ID", userId, "...");
        if (!users.containsKey(userId)) new BTUser(client, userId);
        return users.get(userId);
    }

    protected final Integer userId;
    protected final String name;

    protected String state;
    protected BotUserSex sex = BotUserSex.MALE;

    @NotNull
    protected Keyboard keyboard = Keyboard.start;

    /**
     * Контруктор
     *
     * @param userId - идентификатор пользователя
     */
    public BTUser(Client client, Integer userId) {
        super("BotUser#" + userId);
        var info = BotUserUtils.getUserInfo(userId, client.getAccessToken());

        this.userId = userId;
        this.name = info.optString("first_name", "Петя");
        this.setSex(info.optInt("sex", 1));

        this.log("Создание нового пользователя:", this.name);
        if (!BTUser.users.containsKey(userId)) BTUser.users.put(userId, this);
        this.reload();
    }


    /**
     * Возвращает true, если пользователь - студент
     *
     * @return - результат проверки
     */
    public boolean isStudent() {
        return this.getState().contains("-");
    }

    /**
     * Возвращает true, если пользователь - преподаватель
     *
     * @return - результат проверки
     */
    public boolean isTeacher() {
        return !this.getState().contains("-") && this.state.contains(" ");
    }

    /**
     * Возвращает состояние
     *
     * @return - состояние
     */
    public String getState() {
        return state;
    }

    /**
     * Перезагружает данные пользователя из БД
     */
    public void reload(boolean updateValue) {
        if (updateValue) {
            var value = this.getStateFromServer();
            if (value != null) this.log("Инициилизация:", value);
            this.state = value != null ? value : "";
        }

        if (this.isStudent()) this.keyboard = Keyboard.student;
        if (this.isTeacher()) this.keyboard = Keyboard.teacher;
    }

    /**
     * Перезагружает данные пользователя из бд
     */
    public void reload() {
        this.reload(true);
    }

    /**
     * Возвращает true, если пользователь - админ
     *
     * @return - результат проверки
     */
    public boolean isAdmin() {
        try {
            return BotValuesDB.shared.getValue("admin", this.getUserId().toString()).value.equals("1");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Возвращает сохраненный выбор по VK id
     *
     * @return сохраненный выбор
     */
    @Nullable
    public String getStateFromServer() {
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
     *
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

    /**
     * Возвращет имя пользователя
     *
     * @return - имя пользователя
     */
    public String getName() {
        return name;
    }



    /**
     * Устанавливает состояние
     *
     * @param state - состояние
     */
    public void setState(String state) {
        this.state = state;
        try {
            BotValuesDB.shared.setValue("vkid", this.getUserId().toString(), state);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает пол
     *
     * @return - пол пользователя
     */
    public BotUserSex getSex() {
        return sex;
    }

    /**
     * Устанавливает пол
     *
     * @param v - пол
     */
    public void setSex(int v) {
        if (v == 1) this.sex = BotUserSex.FEMALE;
        else this.sex = BotUserSex.MALE;
    }

    /**
     * Устанавливает пол
     *
     * @param v - пол
     */
    public void setSex(BotUserSex v) {
        this.sex = v;
    }
}
