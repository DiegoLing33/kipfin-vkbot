package me.ling.kipfin.vkbot.entities;

import me.ling.kipfin.core.utils.ListUtils;
import me.ling.kipfin.vkbot.database.BotAnswersDB;
import org.jetbrains.annotations.NotNull;

/**
 * Типы ответа бота
 */
public final class BTAnswerType {

    public final static BTAnswerType SECOND_SUBJECT = new BTAnswerType("2D_SUB");
    public final static BTAnswerType THIRD_SUBJECT = new BTAnswerType("3D_SUB");
    public final static BTAnswerType FOURTH_SUBJECT = new BTAnswerType("4D_SUB");
    public final static BTAnswerType FIFTH_SUBJECT = new BTAnswerType("5D_SUB");

    public final static BTAnswerType DENY_PERMISSIONS = new BTAnswerType("DENY_PERM");
    public final static BTAnswerType ERROR = new BTAnswerType("ERROR");

    public final static BTAnswerType HOME = new BTAnswerType("HOME");
    public final static BTAnswerType HOME_UNDEFINED = new BTAnswerType("UND_HOME");

    public final static BTAnswerType ITS_EARLY = new BTAnswerType("ITS_EARLY");
    public final static BTAnswerType ITS_LATE = new BTAnswerType("ITS_LATE");

    public final static BTAnswerType NO_TIMETABLE_AT_DATE = new BTAnswerType("NO_TIMETABLE_AT_DATE");
    public final static BTAnswerType NO_SUBJECTS = new BTAnswerType("NO_SUBJECTS");

    public final static BTAnswerType SOMETHING_WENT_WRONG = new BTAnswerType("SMT_WNT_WRONG");
    public final static BTAnswerType UNKNOWN_COMMAND = new BTAnswerType("UNK_CMD");

    /**
     * Возвращает случайнй ответ по типу
     *
     * @param type - тип
     * @return - случайный ответ
     */
    @NotNull
    public static String getRandomAnswerByType(String type) {
        return ListUtils.choice(BotAnswersDB.shared.getAnswersByType(type));
    }

    /**
     * Возвращает случайный ответ по типу и сразу шаблонизирует его
     *
     * @param type   - тип
     * @param btUser - пользователь (для шаблона)
     * @return - случайный ответ
     */
    @NotNull
    public static String getRandomAnswerByType(String type, BTUser btUser) {
        return BTUser.template(BTAnswerType.getRandomAnswerByType(type), btUser);
    }

    private final String rawType;

    /**
     * Конструктор
     *
     * @param rawType - тип ответа
     */
    public BTAnswerType(String rawType) {
        this.rawType = rawType;
    }

    /**
     * Возвращает слуайную фразу
     *
     * @param BTUser - пользователь
     * @return - случайная фраза по типу
     */
    @NotNull
    public String random(BTUser BTUser) {
        return BTAnswerType.getRandomAnswerByType(this.getRawType(), BTUser);
    }

    /**
     * Возвращает слуайную фразу
     *
     * @return - случайная фраза по типу
     */
    public String random() {
        return BTAnswerType.getRandomAnswerByType(this.getRawType());
    }

    /**
     * Возвращает слуайную фразу
     *
     * @return - случайная фраза по типу
     */
    public String random(Boolean isTeacher) {
        if (isTeacher)
            return BTAnswerType.getRandomAnswerByType(this.getRawType() + "__TEACHER");
        else
            return BTAnswerType.getRandomAnswerByType(this.getRawType());
    }

    /**
     * Возвращает константу типа
     *
     * @return - константа
     */
    public String getRawType() {
        return rawType;
    }

    @Override
    public String toString() {
        return this.rawType;
    }
}
