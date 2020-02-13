package me.ling.kipfin.vkbot.actions.controllers;

import me.ling.kipfin.timetable.exceptions.NoTimetableOnDateException;
import me.ling.kipfin.timetable.exceptions.timetable.NoSubjectsException;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.builders.KeyboardBuilder;
import me.ling.kipfin.vkbot.entities.VKBotAnswer;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.ImagedTextMessage;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import me.ling.kipfin.vkbot.exceptions.StateNotSetException;
import me.ling.kipfin.vkbot.messagecomponents.timetable.TimetableHeaderComponent;
import me.ling.kipfin.vkbot.utils.BTUtils;
import me.ling.kipfin.vkbot.utils.ResourceManager;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

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
                    ResourceManager.get("welcome.jpg"));
        } catch (Exception ex) {
            ex.printStackTrace();
            return VKBotAnswer.SOMETHING_WENT_WRONG.toTextMessage();
        }
    }

    /**
     * Возвращает текстовое сообщение с отладочным заголовокм
     * @param state         - состояние
     * @param date          - дата
     * @param element       - элемент
     * @param displayTime   - отображение времени
     * @return - текстовое сообщение
     */
    public TextMessage getTextMessageWithHeader(String state, LocalDate date, @NotNull TextMessage element, boolean displayTime){
        var header = new TimetableHeaderComponent(state, date, displayTime);
        return new TextMessage(header.toString() + "\n\n" + element.toString()).applyTagValue("state", state);
    }

    /**
     * Возвращает текстовое сообщение с отладочным заголовокм
     * @param state         - состояние
     * @param date          - дата
     * @param element       - элемент
     * @param displayTime   - отображение времени
     * @return - текстовое сообщение
     */
    public TextMessage getTextMessageWithHeader(String state, LocalDate date, @NotNull VKBotAnswer element, boolean displayTime){
        return this.getTextMessageWithHeader(state, date, element.toTextMessage(BTUtils.isStateTeacher(state)), displayTime);
    }

    /**
     * Возвращает текстовое сообщение с отладочным заголовокм
     * @param state         - состояние
     * @param date          - дата
     * @param element       - элемент
     * @return - текстовое сообщение
     */
    public TextMessage getTextMessageWithHeader(String state, LocalDate date, @NotNull VKBotAnswer element){
        return this.getTextMessageWithHeader(state, date, element.toTextMessage(BTUtils.isStateTeacher(state)), false);
    }

    /**
     * Возвращает текстовое сообщение с отладочным заголовокм
     * @param state         - состояние
     * @param date          - дата
     * @param element       - элемент
     * @return - текстовое сообщение
     */
    public TextMessage getTextMessageWithHeader(String state, LocalDate date, @NotNull TextMessage element){
        return this.getTextMessageWithHeader(state, date, element, false);
    }
}
