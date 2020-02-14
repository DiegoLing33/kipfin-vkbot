package me.ling.kipfin.vkbot.entities;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.users.Fields;
import me.ling.kipfin.core.log.Logger;
import me.ling.kipfin.core.log.WithLogger;
import me.ling.kipfin.vkbot.utils.builders.KeyboardBuilder;
import me.ling.kipfin.vkbot.database.BTValuesDB;
import me.ling.kipfin.vkbot.entities.enums.BotUserSex;
import me.ling.kipfin.vkbot.utils.vk.VKApiApplication;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Бот
 */
public class VKUser extends WithLogger {

    /**
     * Преобразует строку шаблона в строку
     *
     * @param template - шаблон
     * @param BTUser   - пользователь
     * @return - реализованный шаблон
     */
    @NotNull
    public static String template(String template, VKUser BTUser) {
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
    protected static final Map<Integer, VKUser> users = new HashMap<>();

    /**
     * Возвращает ссылку на пользователя
     *
     * @param vkApiApplication - API
     * @param userId           - ID пользователя
     * @return - ссылка на пользователя
     */
    public static VKUser getInstance(VKApiApplication vkApiApplication, Integer userId) {
        Logger.log("Получение информации про ID", userId, "...");
        if (!users.containsKey(userId)) new VKUser(vkApiApplication, userId);
        return users.get(userId);
    }

    protected final Integer userId;
    protected final String name;

    protected String state;
    protected BotUserSex sex = BotUserSex.MALE;

    @NotNull
    protected Keyboard keyboard = KeyboardBuilder.startKeyboard;

    /**
     * Контруктор
     *
     * @param userId - идентификатор пользователя
     */
    public VKUser(VKApiApplication vkApiApplication, Integer userId) {
        super("BotUser#" + userId);
        String name = "Тайна";
        try {
            var values = vkApiApplication.getVk().users().get(vkApiApplication.getActor())
                    .userIds(String.valueOf(userId)).fields(Fields.SEX).execute();
            name = values.get(0).getFirstName();
            this.setSex(values.get(0).getSex().ordinal());
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        this.name = name;
        this.userId = userId;
        this.log("Создание нового пользователя:", this.name);
        if (!VKUser.users.containsKey(userId)) VKUser.users.put(userId, this);
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

        if (this.isStudent()) this.keyboard = KeyboardBuilder.studentHomeKeyboard;
        if (this.isTeacher()) this.keyboard = KeyboardBuilder.teacherHomeKeyboard;
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
            return BTValuesDB.shared.getValue("admin", this.getUserId().toString()).value.equals("1");
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
            return BTValuesDB.shared.getValue("vkid", this.getUserId().toString()).getValue();
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
            BTValuesDB.shared.setValue("vkid", this.getUserId().toString(), state);
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
