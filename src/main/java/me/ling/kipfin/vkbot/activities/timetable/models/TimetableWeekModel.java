package me.ling.kipfin.vkbot.activities.timetable.models;

import me.ling.kipfin.core.utils.DateUtils;
import me.ling.kipfin.timetable.TimetableRequest;
import me.ling.kipfin.timetable.entities.TimetableMaster;
import me.ling.kipfin.vkbot.activities.timetable.components.TimetableWeekComponent;
import me.ling.kipfin.vkbot.activities.timetable.components.SubjectComponent;
import me.ling.kipfin.vkbot.activities.timetable.components.TimetableHeaderComponent;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

public class TimetableWeekModel {

    protected final String group;
    protected final TimetableMaster master;

    public TimetableWeekModel(String group, TimetableMaster master) {
        this.group = group;
        this.master = master;
    }

    public TimetableWeekComponent getMainComponent(int dayIndex) {
        var date = TimetableRequest.getClosetWorkingDay(LocalDate.now()).with(DayOfWeek.of(dayIndex + 1));
        var localDateTime = LocalDateTime.of(date, LocalTime.now());

        return new TimetableWeekComponent(
                new TimetableHeaderComponent(this.group, localDateTime, false),
                this.master.getWeek().getGroupWeek(this.group).get(dayIndex).stream()
                        .map(SubjectComponent::new).collect(Collectors.toList())
        );
    }
}
