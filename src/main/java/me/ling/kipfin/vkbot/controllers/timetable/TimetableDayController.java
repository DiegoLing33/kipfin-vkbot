package me.ling.kipfin.vkbot.controllers.timetable;

import me.ling.kipfin.core.utils.DateUtils;
import me.ling.kipfin.timetable.entities.TimetableMaster;
import me.ling.kipfin.timetable.managers.TimetableManager;
import me.ling.kipfin.vkbot.commands.Controller;
import me.ling.kipfin.vkbot.commands.ControllerArgs;
import me.ling.kipfin.vkbot.controllers.TimetableController;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.messagemodels.SubjectsModel;
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
     *
     * Данный метод является главным в контроллере - он отвечает за роутинг. Если данный метод возвращает
     * значение true, будет запущен метод Controller::execute данного класса.
     *
     * @warning метод `Controller::execute` данного класса НИКОГДА не будет запущен, кроме случаев, когда
     *   `Controller::test` возвращает true. <b>Нет необходимости делнть двойные проверки</b>!
     *
     * @param text - полученный текст
     * @param user - пользователь
     * @param args - аргументы
     * @return - результат проверки
     */
    @Override
    public boolean test(String text, BTUser user, ControllerArgs args) {
        return this.testToday(args) || this.testTomorrow(args);
    }

    /**
     * Выполняет метод и возвращает ответ
     *
     * Данный метод выполняется только после выполнения метода `Controller::test`!
     * @see Controller
     * @see Controller::test
     *
     * @param text - текст
     * @param user - пользователь
     * @param args - аргументы контроллера
     * @return - ответ бота
     */
    @Override
    public Object execute(String text, BTUser user, ControllerArgs args) {
        String state = BTUtils.getStateFromStringOrUser(String.join(" ", args), user);
        LocalDate date = this.testTomorrow(args) ? LocalDate.now().plus(1, ChronoUnit.DAYS) : LocalDate.now();

        String dateString = DateUtils.toLocalDateString(date);
        TimetableMaster master = TimetableManager.downloadOrGetCache(dateString);
        return SubjectsModel.getExtendedDay(state, user, master).toString();
    }
}
