package me.ling.kipfin.vkbot.controllers;

import me.ling.kipfin.timetable.exceptions.NoTimetableOnDateException;
import me.ling.kipfin.timetable.exceptions.timetable.NoSubjectsException;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.entities.BTAnswerType;
import me.ling.kipfin.vkbot.entities.BTUser;
import org.jetbrains.annotations.Nullable;

/**
 * Контроллер для расписания (В нем содержится обработка исключений)
 */
public abstract class TimetableController extends MessageController {

    @Nullable
    @Override
    public Object requestExecute(String text, BTUser user, ControllerArgs args) {
        try {
            return super.requestExecute(text, user, args);
        } catch (NoTimetableOnDateException ex) {
            return BTAnswerType.NO_TIMETABLE_AT_DATE.random();
        } catch (NoSubjectsException ex) {
            return BTAnswerType.NO_SUBJECTS.random(user.isTeacher());
        } catch (Exception ex) {
            return BTAnswerType.SOMETHING_WENT_WRONG.random();
        }
    }
}
