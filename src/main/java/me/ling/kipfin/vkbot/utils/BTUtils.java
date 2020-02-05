package me.ling.kipfin.vkbot.utils;

import me.ling.kipfin.database.university.GroupsDB;
import me.ling.kipfin.database.university.TeachersDB;
import me.ling.kipfin.vkbot.entities.BTUser;
import org.jetbrains.annotations.Nullable;

public final class BTUtils {

    /**
     * Совершает попытку получить состояние
     *
     * @param text - текст
     * @return - состояние или null
     */
    @Nullable
    public static String getStateFromTextOrNull(@Nullable String text) {
        if (text == null || text.isEmpty()) return null;
        try {
            return GroupsDB.shared.easy(text).getTitle();
        } catch (Exception e) {
            try {
                return TeachersDB.shared.easy(text).getName();
            } catch (Exception ex) {
                return null;
            }
        }
    }

    /**
     * Проверяет текст на наличие state
     *
     * @param text - текст
     * @return - результат проверки
     */
    public static boolean checkTextIsState(String text) {
        return BTUtils.getStateFromTextOrNull(text) != null;
    }

    /**
     * Возвращает состояние из строки или пользователя
     *
     * @param source -    строка
     * @param user   - пользователь
     * @return - состояние
     */
    public static String getStateFromStringOrUser(@Nullable String source, BTUser user) {
        String state = BTUtils.getStateFromTextOrNull(source);
        return state == null ? user.getState() : state;
    }

    /**
     * Возвращает true, если состояние студента
     *
     * @param state - состояние
     * @return - результат проверки
     */
    public static boolean isStateStudent(@Nullable String state) {
        return state != null && state.contains("-");
    }

    /**
     * Возвращает true, если состояние преподавателя
     *
     * @param state - состояние
     * @return - результат проверки
     */
    public static boolean isStateTeacher(@Nullable String state) {
        return state != null && !BTUtils.isStateStudent(state) && state.contains(" ");
    }
}
