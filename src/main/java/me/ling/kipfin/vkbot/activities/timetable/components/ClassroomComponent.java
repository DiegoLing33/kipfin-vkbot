package me.ling.kipfin.vkbot.activities.timetable.components;

import me.ling.kipfin.timetable.entities.Classroom;
import me.ling.kipfin.vkbot.app.MessageComponent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Компонент аудитории для преподавателя
 */
public class ClassroomComponent extends MessageComponent {

    private final Classroom classroom;

    /**
     * Создает компонент аудитории
     * @param classroom - аудитория
     * @return  - компонент
     */
    @NotNull
    @Contract("_ -> new")
    public static ClassroomComponent create(Classroom classroom){
        return new ClassroomComponent(classroom);
    }

    /**
     * Создает список компонентов аудиторий
     * @param classrooms    - список аудиторий
     * @return  - список компонентов
     */
    public static List<ClassroomComponent> create(@NotNull List<Classroom> classrooms){
        return classrooms.stream().map(ClassroomComponent::new).collect(Collectors.toList());
    }

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
