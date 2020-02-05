package me.ling.kipfin.vkbot.actions.timetableday;

import me.ling.kipfin.core.utils.DateUtils;
import me.ling.kipfin.timetable.entities.TimetableMaster;
import me.ling.kipfin.timetable.managers.TimetableManager;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.controllers.TimetableController;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.utils.BTUtils;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Контроллер расписания на сегодня
 */
public class TimetableDayController extends TimetableController {

    /**
     * Тест команды "Сегдня"
     *
     * @param args - аргументы
     * @return - результат тестирования
     */
    protected boolean testToday(@NotNull ControllerArgs args) {
        return args.test("Сегодня") ||
                (args.test("Сегодня") && BTUtils.checkTextIsState(String.join(" ", args)));
    }

    /**
     * Тест команды "Завтра"
     *
     * @param args - аргументы
     * @return - результат тестирования
     */
    protected boolean testTomorrow(@NotNull ControllerArgs args) {
        return args.test("Завтра") ||
                (args.test("Завтра") && BTUtils.checkTextIsState(String.join(" ", args)));
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
    public boolean test(String text, BTUser user, ControllerArgs args) {
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
    @Override
    public Object execute(String text, BTUser user, ControllerArgs args) {
        String state = BTUtils.getStateFromStringOrUser(String.join(" ", args), user);

        int add = DateUtils.getLocalWeekDay(LocalDate.now()) == 4 ? 3 : 1;
        LocalDate date = this.testTomorrow(args) ? LocalDate.now().plus(add, ChronoUnit.DAYS) : LocalDate.now();

        String dateString = DateUtils.toLocalDateString(date);
        TimetableMaster master = TimetableManager.downloadOrGetCache(dateString);
        TimetableDayModel model = new TimetableDayModel(state, master);
        return model.getComponent();
    }
}
