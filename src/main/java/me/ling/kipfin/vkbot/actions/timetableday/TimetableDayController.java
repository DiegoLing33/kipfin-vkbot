package me.ling.kipfin.vkbot.actions.timetableday;

import me.ling.kipfin.core.utils.DateUtils;
import me.ling.kipfin.timetable.entities.TimetableMaster;
import me.ling.kipfin.timetable.exceptions.timetable.NoSubjectsException;
import me.ling.kipfin.timetable.managers.TimetableManager;
import me.ling.kipfin.vkbot.app.BTController;
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
import java.time.temporal.ChronoUnit;

/**
 * Контроллер расписания на сегодня
 */
@BTController
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
     * @see MessageController ::test
     */
    @NotNull
    @Override
    public TextMessage execute(String text, VKUser user, ControllerArgs args) {
        this.checkUserState(user);
        String state = BTUtils.getStateFromStringOrUser(String.join(" ", args), user);
        LocalDate date = this.testTomorrow(args) ? LocalDate.now().plus(1, ChronoUnit.DAYS) : LocalDate.now();

        int localWeekDay = DateUtils.getLocalWeekDay(date);
        if (localWeekDay == 5 || localWeekDay == 6)
            return new TextMessage(new TimetableHeaderComponent(state, LocalDateTime.of(date, LocalTime.now()), false)
                    .toString() + "\n\n" + VKBotAnswer.WEEKENDS.random(BTUtils.isStateTeacher(state))).applyTagValue("state", state);

        try {
            String dateString = DateUtils.toLocalDateString(date);
            TimetableMaster master = TimetableManager.downloadOrGetCache(dateString);
            TimetableDayModel model = new TimetableDayModel(state, master);
            return model.getComponent().toTextMessage();
        } catch (NoSubjectsException ex) {
            //todo - fix repeats
            String message = new TimetableHeaderComponent(state, LocalDateTime.of(date, LocalTime.now()), false)
                    .toString() + "\n\n" + VKBotAnswer.NO_SUBJECTS.random(user.isTeacher());
            return new TextMessage(message).applyTagValue("state", state);
        }
    }
}
