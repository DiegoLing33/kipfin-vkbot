package me.ling.kipfin.vkbot.actions.timetablenow;

import me.ling.kipfin.core.utils.DateUtils;
import me.ling.kipfin.timetable.entities.TimetableMaster;
import me.ling.kipfin.timetable.exceptions.timetable.NoSubjectsException;
import me.ling.kipfin.timetable.managers.TimetableManager;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.controllers.TimetableController;
import me.ling.kipfin.vkbot.entities.BTAnswerType;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.messagecomponents.timetable.TimetableHeaderComponent;
import me.ling.kipfin.vkbot.utils.BTUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * Отображает информацию о расписании в данный момент
 */
public class TimetableNowController extends TimetableController {

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
        return args.test("Сейчас") ||
                (args.test("Сейчас") && BTUtils.checkTextIsState(String.join(" ", args)));
    }

    /**
     * Выполняет метод и возвращает ответ
     *
     * Данный метод выполняется только после выполнения метода `Controller::test`!
     * @see MessageController
     * @see MessageController ::test
     *
     * @param text - текст
     * @param user - пользователь
     * @param args - аргументы контроллера
     * @return - ответ бота
     */
    @Override
    public Object execute(String text, BTUser user, ControllerArgs args) {
        this.checkUserState(user);
        String state = BTUtils.getStateFromStringOrUser(String.join(" ", args), user);
        LocalTime time = LocalTime.now();

        try {
            String dateString = DateUtils.toLocalDateString(LocalDate.now());
            TimetableMaster master = TimetableManager.downloadOrGetCache(dateString);

            return TimetableNowModel.getTimetableNowDayComponent(state, time, master).toString();
        }catch (NoSubjectsException ex){
            return new TimetableHeaderComponent(state, LocalDateTime.of(LocalDate.now(), time), true)
                    .toString() + "\n\n" + BTAnswerType.NO_SUBJECTS.random(user.isTeacher())
                    .replaceAll("\\{state}", state);
        }
    }
}
