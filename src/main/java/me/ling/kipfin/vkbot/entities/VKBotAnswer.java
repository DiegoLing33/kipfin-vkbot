package me.ling.kipfin.vkbot.entities;

import me.ling.kipfin.core.utils.ListUtils;
import me.ling.kipfin.vkbot.database.BotAnswersDB;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Типы ответа бота
 */
public final class VKBotAnswer {

    public final static VKBotAnswer SECOND_SUBJECT = new VKBotAnswer("2D_SUB");
    public final static VKBotAnswer THIRD_SUBJECT = new VKBotAnswer("3D_SUB");
    public final static VKBotAnswer FOURTH_SUBJECT = new VKBotAnswer("4D_SUB");
    public final static VKBotAnswer FIFTH_SUBJECT = new VKBotAnswer("5D_SUB");

    public final static VKBotAnswer DENY_PERMISSIONS = new VKBotAnswer("DENY_PERM");
    public final static VKBotAnswer ERROR = new VKBotAnswer("ERROR");

    public final static VKBotAnswer HELP = new VKBotAnswer("HELP");
    public final static VKBotAnswer HOME = new VKBotAnswer("HOME");
    public final static VKBotAnswer HOME_UNDEFINED = new VKBotAnswer("UND_HOME");

    public final static VKBotAnswer ITS_EARLY = new VKBotAnswer("ITS_EARLY");
    public final static VKBotAnswer ITS_LATE = new VKBotAnswer("ITS_LATE");

    public final static VKBotAnswer NO_TIMETABLE_AT_DATE = new VKBotAnswer("NO_TIMETABLE_AT_DATE");
    public final static VKBotAnswer NO_SUBJECTS = new VKBotAnswer("NO_SUBJECTS");
    public final static VKBotAnswer NO_ROUTING = new VKBotAnswer("NO_ROUTING");

    public final static VKBotAnswer SOMETHING_WENT_WRONG = new VKBotAnswer("SMT_WNT_WRONG");
    public final static VKBotAnswer UNKNOWN_COMMAND = new VKBotAnswer("UNK_CMD");

    public final static VKBotAnswer WEEK_NO_SUBJECTS = new VKBotAnswer("WEEK_NO_SUBJECTS");

    public final static VKBotAnswer WEEKENDS_BUT_MONDAY = new VKBotAnswer("WKNDS_BUT_MON");
    public final static VKBotAnswer WEEKENDS= new VKBotAnswer("WEKNDS");

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
    public static String getRandomAnswerByType(String type, VKUser btUser) {
        return VKUser.template(VKBotAnswer.getRandomAnswerByType(type), btUser);
    }

    private final String rawType;

    /**
     * Конструктор
     *
     * @param rawType - тип ответа
     */
    public VKBotAnswer(String rawType) {
        this.rawType = rawType;
    }

    /**
     * Возвращает слуайную фразу
     *
     * @return - случайная фраза по типу
     */
    public String random() {
        return VKBotAnswer.getRandomAnswerByType(this.getRawType());
    }

    /**
     * Возвращает слуайную фразу
     *
     * @return - случайная фраза по типу
     */
    @NotNull
    public String random(@NotNull Boolean isTeacher) {
        if (isTeacher)
            return VKBotAnswer.getRandomAnswerByType(this.getRawType() + "__TEACHER");
        else
            return VKBotAnswer.getRandomAnswerByType(this.getRawType());
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

    /**
     * Преобразует в сообщение
     * @return  - сообщение TextMessage
     */
    @NotNull
    @Contract(" -> new")
    public TextMessage toTextMessage(){
        return new TextMessage(this.random());
    }
    /**
     * Преобразует в сообщение
     * @param isTeacher - флаг проверки преподавателя
     * @return  - сообщение TextMessage
     */
    @NotNull
    public TextMessage toTextMessage(boolean isTeacher){
        return new TextMessage(this.random(isTeacher));
    }
}
