package me.ling.kipfin.vkbot.activities.timetable.controllers;

import me.ling.kipfin.timetable.TimetableRequest;
import me.ling.kipfin.timetable.entities.TimetableMaster;
import me.ling.kipfin.timetable.enums.StudentDayState;
import me.ling.kipfin.vkbot.activities.timetable.models.TimetableDayModel;
import me.ling.kipfin.vkbot.app.BTActivity;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.activities.TimetableController;
import me.ling.kipfin.vkbot.entities.VKBTAnswer;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import me.ling.kipfin.vkbot.utils.BTUtils;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Контроллер расписания на сегодня
 */
@BTActivity
public class TimetableDayController extends TimetableController {

    /**
     * Тест команды "Сегдня"
     *
     * @param args - аргументы
     * @return - результат тестирования
     */
    protected boolean testToday(@NotNull ControllerArgs args) {
        return args.testMainArg("Сегодня") ||
                (args.testMainArg("Сегодня") && BTUtils.checkTextIsState(String.join(" ", args)));
    }

    /**
     * Тест команды "Завтра"
     *
     * @param args - аргументы
     * @return - результат тестирования
     */
    protected boolean testTomorrow(@NotNull ControllerArgs args) {
        return args.testMainArg("Завтра") ||
                (args.testMainArg("Завтра") && BTUtils.checkTextIsState(String.join(" ", args)));
    }

    /**
     * Тестирует контрллер
     * <p>
     * Данный метод является главным в контроллере - он отвечает за роутинг. Если данный метод возвращает
     * значение true, будет запущен метод Controller::execute данного класса.
     *
     * @param text - полученный текст
     * @param user - пользователь
     * @param args - аргументы
     * @return - результат проверки
     * @warning метод `Controller::execute` данного класса НИКОГДА не будет запущен, кроме случаев, когда
     * `Controller::test` возвращает true. <b>Нет необходимости делнть двойные проверки</b>!
     */
    @Override
    public boolean test(String text, VKUser user, ControllerArgs args) {
        return this.testToday(args) || this.testTomorrow(args);
    }

    /**
     * Выполняет метод и возвращает ответ
     * <p>
     * Данный метод выполняется только после выполнения метода `Controller::test`!
     *
     * @param text - текст
     * @param user - пользователь
     * @param args - аргументы контроллера
     * @return - ответ бота
     * @see MessageController
     */
    @NotNull
    @Override
    public TextMessage execute(String text, VKUser user, ControllerArgs args) {
        this.checkUserState(user);

        var state = BTUtils.getStateFromStringOrUser(String.join(" ", args), user);
        var date = this.testTomorrow(args) ? LocalDate.now().plus(1, ChronoUnit.DAYS) : LocalDate.now();
        var request = new TimetableRequest(date);

        if (request.getDayState().equals(StudentDayState.WEEKENDS))
            return this.getTextMessageWithHeader(state, date, VKBTAnswer.WEEKENDS);

        TimetableMaster master = request.getMaster();
        TimetableDayModel model = new TimetableDayModel(state, master);
        return model.getComponent().toTextMessage();
    }
}
