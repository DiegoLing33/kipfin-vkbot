package me.ling.kipfin.vkbot.messagecomponents.timetable;

import me.ling.kipfin.timetable.entities.ExtendedSubject;
import me.ling.kipfin.vkbot.app.MessageComponent;

/**
 * Компонент расширенного предмета
 */
public class ExtendedSubjectComponent extends MessageComponent {

    private final ExtendedSubject extendedSubject;

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
