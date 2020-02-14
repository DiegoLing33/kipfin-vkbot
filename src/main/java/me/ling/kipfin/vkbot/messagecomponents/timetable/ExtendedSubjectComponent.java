package me.ling.kipfin.vkbot.messagecomponents.timetable;

import me.ling.kipfin.timetable.entities.ExtendedSubject;
import me.ling.kipfin.vkbot.app.MessageComponent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Компонент расширенного предмета
 */
public class ExtendedSubjectComponent extends MessageComponent {

    private final ExtendedSubject extendedSubject;

    /**
     * Создает компонент из предмета
     * @param extendedSubject   - предмет
     * @return  - компонент
     */
    @NotNull
    @Contract("_ -> new")
    public static ExtendedSubjectComponent create(ExtendedSubject extendedSubject){
        return new ExtendedSubjectComponent(extendedSubject);
    }

    /**
     * Создает список компонентов из списка предметов
     * @param extendedSubjects  - список предметов
     * @return  - список компонентов
     */
    public static List<ExtendedSubjectComponent> create(@NotNull List<ExtendedSubject> extendedSubjects){
        return extendedSubjects.stream().map(ExtendedSubjectComponent::new).collect(Collectors.toList());
    }

    public ExtendedSubjectComponent(ExtendedSubject extendedSubject) {
        this.extendedSubject = extendedSubject;
    }

    /**
     * Возвращает расширенный предмет
     * @return  - расширенный пермдет
     */
    public ExtendedSubject getExtendedSubject() {
        return extendedSubject;
    }

    /**
     * Преобразует компонент в строку
     * @return  - компонент в виде текста
     */
    @Override
    public String toString() {
        return String.format("%s\nГде: %s\nКто: %s", this.getExtendedSubject().getTitle(),
                this.getExtendedSubject().getWho().getClassroomsJoin(),
                this.getExtendedSubject().getWho().getTeachersJoin());
    }
}
