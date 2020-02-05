package me.ling.kipfin.vkbot.messagecomponents.timetable;

import me.ling.kipfin.timetable.entities.Classroom;
import me.ling.kipfin.vkbot.messagecomponents.MessageComponent;

/**
 * Компонент аудитории для преподавателя
 */
public class ClassroomComponent extends MessageComponent {

    private final Classroom classroom;

    /**
     * Конструктор
     * @param classroom - аудитория
     */
    public ClassroomComponent(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * Возвращает аудиторию
     * @return  - аудитория
     */
    public Classroom getClassroom() {
        return classroom;
    }

    @Override
    public String toString() {
        return String.format("Кто: %s\nГде: %s", this.getClassroom().getGroup(), this.getClassroom().getWhere());
    }
}
