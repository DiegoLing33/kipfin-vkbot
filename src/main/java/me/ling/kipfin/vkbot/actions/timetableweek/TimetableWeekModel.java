package me.ling.kipfin.vkbot.actions.timetableweek;

import me.ling.kipfin.timetable.entities.TimetableMaster;
import me.ling.kipfin.vkbot.messagecomponents.timetable.SubjectComponent;
import me.ling.kipfin.vkbot.messagecomponents.timetable.TimetableHeaderComponent;
import me.ling.kipfin.vkbot.actions.timetableweek.TimetableWeekComponent;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;

public class TimetableWeekModel {

    protected final String group;
    protected final TimetableMaster master;

    public TimetableWeekModel(String group, TimetableMaster master) {
        this.group = group;
        this.master = master;
    }

    public TimetableWeekComponent getMainComponent(int dayIndex) {
        LocalDate date = LocalDate.now().with(DayOfWeek.of(dayIndex + 1));
        LocalDateTime localDateTime = LocalDateTime.of(date, LocalTime.now());
        return new TimetableWeekComponent(
                new TimetableHeaderComponent(this.group, localDateTime, false),
                this.master.getWeek().getGroupWeek(this.group).get(dayIndex).stream()
                        .map(SubjectComponent::new).collect(Collectors.toList())
        );
    }
}
