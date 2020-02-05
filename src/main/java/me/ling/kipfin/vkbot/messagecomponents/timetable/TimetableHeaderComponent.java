package me.ling.kipfin.vkbot.messagecomponents.timetable;

import me.ling.kipfin.core.utils.DateUtils;
import me.ling.kipfin.vkbot.app.MessageComponent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimetableHeaderComponent extends MessageComponent {

    private final String state;
    private final LocalDateTime localDateTime;
    private final boolean displayTime;

    public TimetableHeaderComponent(String state, LocalDateTime localDateTime, boolean displayTime) {
        super();
        this.state = state;
        this.localDateTime = localDateTime;
        this.displayTime = displayTime;
    }

    /**
     * Возвращает строку даты в формате: 01.01.2020 (Понедельник)
     *
     * @return - дата
     */
    public String getDateLine() {
        LocalDate localDate = this.localDateTime.toLocalDate();
        String localDateString = DateUtils.toLocalDateString(localDate);
        String weekDayName = DateUtils.weekDaysNames[DateUtils.getLocalWeekDay(localDate)];
        return String.format("%s (%s)", localDateString, weekDayName);
    }

    /**
     * Возвращает строку с информацией
     *
     * @return - строка с информацией вида "Группа: ХХХ" или "Преподаватель: ХХХХ"
     */
    public String getInfoLine() {
        String name = state.contains("-") ? "Группа" : "Преподаватель";
        return String.format("%s: %s", name, state);
    }

    /**
     * Возвращает строку со временем
     *
     * @return - строка со временм
     */
    public String getTimeLine() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return String.format("Сейчас: %s", formatter.format(this.localDateTime));
    }

    /**
     * Преобразует в строку
     *
     * @return - строка
     */
    @Override
    public String toString() {
        return this.displayTime ?
                String.format("%s\n%s\n%s", this.getDateLine(), this.getInfoLine(), this.getTimeLine()) :
                String.format("%s\n%s", this.getDateLine(), this.getInfoLine());
    }
}