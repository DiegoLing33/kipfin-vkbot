package me.ling.kipfin.vkbot.builders;

import com.vk.api.sdk.objects.messages.*;

import java.util.List;

public class KeyboardBuilder {

    /**
     * Заготовленные кнопки
     */
    public static final class Buttons {
        public static final KeyboardButton homeButton = createTextButton("Домой", KeyboardButtonColor.DEFAULT);
        public static final KeyboardButton helpButton = createTextButton("Справка", KeyboardButtonColor.DEFAULT);

        public static final KeyboardButton todayButton = createTextButton("Сегодня");
        public static final KeyboardButton tomorrowButton = createTextButton("Завтра");
        public static final KeyboardButton nowButton = createTextButton("Сейчас");
        public static final KeyboardButton weekButton = createTextButton("Неделя");

        public static final KeyboardButton selectGroupPositiveButton = createTextButton("Выбрать группу");
        public static final KeyboardButton selectGroupSecondaryButton = createTextButton("Выбрать группу", KeyboardButtonColor.DEFAULT);

        public static final KeyboardButton getRouterButton = createTextButton("Классный руководитель");
        public static final KeyboardButton getRoutingButton = createTextButton("Группа в руководстве");

        public static final KeyboardButton addictionButton = createTextButton("Дополнительно", KeyboardButtonColor.DEFAULT);

        public static KeyboardButton createTextButton(String text, KeyboardButtonColor color) {
            return new KeyboardButton()
                    .setColor(color)
                    .setAction(new KeyboardButtonAction().setLabel(text).setType(KeyboardButtonActionType.TEXT));
        }
        
        public static KeyboardButton createTextButton(String text) {
            return createTextButton(text, KeyboardButtonColor.POSITIVE);
        }
    }

    /**
     * Стартовая клавиатура
     */
    public static final Keyboard startKeyboard = new Keyboard()
            .setOneTime(false)
            .setButtons(List.of(
                    List.of(Buttons.selectGroupPositiveButton),
                    List.of(Buttons.helpButton)
            ));

    /**
     * Стартовая клавиатура
     */
    public static final Keyboard startInlineKeyboard = new Keyboard()
            .setOneTime(false)
            .setInline(true)
            .setButtons(List.of(
                    List.of(Buttons.selectGroupPositiveButton),
                    List.of(Buttons.helpButton)
            ));
    /**
     * Стартовая клавиатура студента
     */
    public static final Keyboard inStateHomeKeyboard = new Keyboard()
            .setOneTime(false)
            .setButtons(List.of(
                    List.of(Buttons.todayButton, Buttons.tomorrowButton),
                    List.of(Buttons.nowButton),
                    List.of(Buttons.addictionButton)
            ));


    /**
     * Клавиатура выбора курса
     */
    public static final Keyboard coursesKeyboard = new Keyboard()
            .setOneTime(false)
            .setButtons(List.of(
               List.of(Buttons.createTextButton("1 курс"), Buttons.createTextButton("2 курс")),
               List.of(Buttons.createTextButton("3 курс"), Buttons.createTextButton("4 курс")),
               List.of(Buttons.homeButton),
               List.of(Buttons.helpButton)
            ));

    /**
     * "Дополнительно" для студентов
     */
    public static final Keyboard addictionStudentKeyboard = new Keyboard()
            .setOneTime(false)
            .setButtons(List.of(
                    List.of(Buttons.weekButton),
                    List.of(Buttons.getRouterButton),
                    List.of(Buttons.selectGroupSecondaryButton, Buttons.helpButton),
                    List.of(Buttons.homeButton)
            ));

    /**
     * "Дополнительно" для преподавателей
     */
    public static final Keyboard addictionTeacherKeyboard = new Keyboard()
            .setOneTime(false)
            .setButtons(List.of(
                    List.of(Buttons.getRoutingButton),
                    List.of(Buttons.selectGroupSecondaryButton, Buttons.helpButton),
                    List.of(Buttons.homeButton)
            ));

    /**
     * Клавиатура на обновлении
     */
    public static final Keyboard whenTimetableUpdateInlineKeyboard = new Keyboard()
            .setOneTime(false)
            .setInline(true)
            .setButtons(List.of(
                    List.of(Buttons.tomorrowButton)
            ));
}
