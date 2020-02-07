package me.ling.kipfin.vkbot.controllers;

import me.ling.kipfin.timetable.exceptions.NoTimetableOnDateException;
import me.ling.kipfin.timetable.exceptions.timetable.NoSubjectsException;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.entities.BTAnswerType;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.exceptions.StateNotSetException;
import org.jetbrains.annotations.Nullable;

/**
 * Контроллер для расписания (В нем содержится обработка исключений)
 */
public abstract class TimetableController extends MessageController {

    /**
     * Провреяет наличие установленного состояния
     * @param user                  - пользователь
     * @throws StateNotSetException - исключение отсутсвия состояния
     */
    public void checkUserState(BTUser user) throws StateNotSetException {
        if(user == null || user.getState().isEmpty() || user.getState().isBlank())
            throw new StateNotSetException();
    }

    @Nullable
    @Override
    public Object requestExecute(String text, BTUser user, ControllerArgs args) {
        try {
            return super.requestExecute(text, user, args);
        } catch (NoTimetableOnDateException ex) {
            return BTAnswerType.NO_TIMETABLE_AT_DATE.random();
        } catch (NoSubjectsException ex) {
            return BTAnswerType.NO_SUBJECTS.random(user.isTeacher());
        } catch (StateNotSetException ex) {
            return BTAnswerType.HOME_UNDEFINED.random();
        } catch (Exception ex) {
            return BTAnswerType.SOMETHING_WENT_WRONG.random();
        }
    }
}
