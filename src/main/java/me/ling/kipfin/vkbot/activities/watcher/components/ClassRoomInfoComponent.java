package me.ling.kipfin.vkbot.activities.watcher.components;

import me.ling.kipfin.timetable.entities.Classroom;
import me.ling.kipfin.vkbot.app.MessageComponent;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ClassRoomInfoComponent extends MessageComponent {

    private final String classroomName;
    private final String date;
    private final List<Classroom> classrooms;

    public ClassRoomInfoComponent(String classroomName, String date, List<Classroom> classrooms) {
        this.classroomName = classroomName;
        this.classrooms = classrooms;
        this.date = date;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public String getDate() {
        return date;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    @Override
    public String toString() {
        String info = this.getClassrooms().stream()
                .map(classroom -> String.format("Номер пары: %s\nКто: %s\nГруппа: %s",
                        classroom.getIndex() + 1, classroom.getWho(), classroom.getGroup()))
                .collect(Collectors.joining("\n\n"));
        if (info.isEmpty() || info.isBlank()) info = String.format("В этот день в аудитории %s ничего не проходит.",
                this.getClassroomName());
        return String.format("Аудитория: %s\nДата: %s\n\nИнформация:\n\n%s",
                this.getClassroomName(), this.getDate(), info);
    }
}
