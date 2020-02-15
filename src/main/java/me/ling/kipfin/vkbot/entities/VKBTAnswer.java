package me.ling.kipfin.vkbot.entities;

import me.ling.kipfin.core.utils.ListUtils;
import me.ling.kipfin.vkbot.database.BTAnswersDB;
import me.ling.kipfin.vkbot.entities.message.CoreMessage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Типы ответа бота
 */
public final class VKBTAnswer {

    public final static VKBTAnswer SECOND_SUBJECT = new VKBTAnswer("2D_SUB");
    public final static VKBTAnswer THIRD_SUBJECT = new VKBTAnswer("3D_SUB");
    public final static VKBTAnswer FOURTH_SUBJECT = new VKBTAnswer("4D_SUB");
    public final static VKBTAnswer FIFTH_SUBJECT = new VKBTAnswer("5D_SUB");

    public final static VKBTAnswer DENY_PERMISSIONS = new VKBTAnswer("DENY_PERM");
    public final static VKBTAnswer ERROR = new VKBTAnswer("ERROR");

    public final static VKBTAnswer HELP = new VKBTAnswer("HELP");
    public final static VKBTAnswer HOME = new VKBTAnswer("HOME");
    public final static VKBTAnswer HOME_UNDEFINED = new VKBTAnswer("UND_HOME");

    public final static VKBTAnswer ITS_EARLY = new VKBTAnswer("ITS_EARLY");
    public final static VKBTAnswer ITS_LATE = new VKBTAnswer("ITS_LATE");

    public final static VKBTAnswer NO_TIMETABLE_AT_DATE = new VKBTAnswer("NO_TIMETABLE_AT_DATE");
    public final static VKBTAnswer NO_SUBJECTS = new VKBTAnswer("NO_SUBJECTS");
    public final static VKBTAnswer NO_ROUTING = new VKBTAnswer("NO_ROUTING");

    public final static VKBTAnswer SOMETHING_WENT_WRONG = new VKBTAnswer("SMT_WNT_WRONG");
    public final static VKBTAnswer UNKNOWN_COMMAND = new VKBTAnswer("UNK_CMD");

    public final static VKBTAnswer WEEK_NO_SUBJECTS = new VKBTAnswer("WEEK_NO_SUBJECTS");

    public final static VKBTAnswer WEEKENDS_BUT_MONDAY = new VKBTAnswer("WKNDS_BUT_MON");
    public final static VKBTAnswer WEEKENDS= new VKBTAnswer("WEKNDS");

    /**
     * Возвращает случайнй ответ по типу
     *
     * @param type - тип
     * @return - случайный ответ
     */
    @NotNull
    public static String getRandomAnswerByType(String type) {
        return ListUtils.choice(BTAnswersDB.shared.getAnswersByType(type));
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
        return VKUser.template(VKBTAnswer.getRandomAnswerByType(type), btUser);
    }

    private final String rawType;

    /**
     * Конструктор
     *
     * @param rawType - тип ответа
     */
    public VKBTAnswer(String rawType) {
        this.rawType = rawType;
    }

    /**
     * Возвращает слуайную фразу
     *
     * @return - случайная фраза по типу
     */
    public String random() {
        return VKBTAnswer.getRandomAnswerByType(this.getRawType());
    }

    /**
     * Возвращает слуайную фразу
     *
     * @return - случайная фраза по типу
     */
    @NotNull
    public String random(@NotNull Boolean isTeacher) {
        if (isTeacher)
            return VKBTAnswer.getRandomAnswerByType(this.getRawType() + "__TEACHER");
        else
            return VKBTAnswer.getRandomAnswerByType(this.getRawType());
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
    public CoreMessage toTextMessage(){
        return new CoreMessage(this.random(), null);
    }
    /**
     * Преобразует в сообщение
     * @param isTeacher - флаг проверки преподавателя
     * @return  - сообщение TextMessage
     */
    @NotNull
    public CoreMessage toTextMessage(boolean isTeacher){
        return new CoreMessage(this.random(isTeacher), null);
    }
}
