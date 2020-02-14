package me.ling.kipfin.vkbot.actions.timetablenow;

import me.ling.kipfin.core.utils.DateUtils;
import me.ling.kipfin.timetable.TimetableRequest;
import me.ling.kipfin.timetable.entities.TimetableMaster;
import me.ling.kipfin.timetable.enums.StudentDayState;
import me.ling.kipfin.timetable.exceptions.timetable.NoSubjectsException;
import me.ling.kipfin.timetable.managers.TimetableManager;
import me.ling.kipfin.vkbot.app.BTActivity;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.actions.controllers.TimetableController;
import me.ling.kipfin.vkbot.entities.VKBotAnswer;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import me.ling.kipfin.vkbot.messagecomponents.timetable.TimetableHeaderComponent;
import me.ling.kipfin.vkbot.utils.BTUtils;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * Отображает информацию о расписании в данный момент
 */
@BTActivity
public class TimetableNowController extends TimetableController {

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
        return args.testMainArg("Сейчас") ||
                (args.testMainArg("Сейчас") && BTUtils.checkTextIsState(String.join(" ", args)));
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
     * @see MessageController ::test
     */
    @NotNull
    @Override
    public TextMessage execute(String text, VKUser user, ControllerArgs args) {
        this.checkUserState(user);
        var state = BTUtils.getStateFromStringOrUser(String.join(" ", args), user);
        var time = LocalTime.now();
        var request = new TimetableRequest(time);

        if (request.getDayState().equals(StudentDayState.WEEKENDS))
            return this.getTextMessageWithHeader(state, request.getDate(), VKBotAnswer.WEEKENDS, true);
        return TimetableNowModel.getTimetableNowDayComponent(state, time, request.getMaster()).toTextMessage();
    }
}
