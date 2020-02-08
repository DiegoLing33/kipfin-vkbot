package me.ling.kipfin.vkbot.controllers;

import me.ling.kipfin.timetable.exceptions.NoTimetableOnDateException;
import me.ling.kipfin.timetable.exceptions.timetable.NoSubjectsException;
import me.ling.kipfin.vkbot.Main;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.builders.KeyboardBuilder;
import me.ling.kipfin.vkbot.entities.VKBotAnswer;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.ImagedTextMessage;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import me.ling.kipfin.vkbot.exceptions.StateNotSetException;

/**
 * Контроллер для расписания (В нем содержится обработка исключений)
 */
public abstract class TimetableController extends MessageController {

    /**
     * Провреяет наличие установленного состояния
     *
     * @param user - пользователь
     * @throws StateNotSetException - исключение отсутсвия состояния
     */
    public void checkUserState(VKUser user) throws StateNotSetException {
        if (user == null || user.getState().isEmpty() || user.getState().isBlank())
            throw new StateNotSetException();
    }

    @Override
    public TextMessage requestExecute(String text, VKUser user, ControllerArgs args) {
        try {
            return super.requestExecute(text, user, args);
        } catch (NoTimetableOnDateException ex) {
            return VKBotAnswer.NO_TIMETABLE_AT_DATE.toTextMessage();
        } catch (NoSubjectsException ex) {
            return VKBotAnswer.NO_SUBJECTS.toTextMessage(user.isTeacher());
        } catch (StateNotSetException ex) {
            return new ImagedTextMessage(VKBotAnswer.HOME_UNDEFINED.random(),
                    KeyboardBuilder.startInlineKeyboard,
                    Main.class.getResource("/hi.jpg").getPath());
        } catch (Exception ex) {
            ex.printStackTrace();
            return VKBotAnswer.SOMETHING_WENT_WRONG.toTextMessage();
        }
    }
}
