package me.ling.kipfin.vkbot.messagecomponents.timetable;

import me.ling.kipfin.timetable.entities.Subject;
import me.ling.kipfin.vkbot.messagecomponents.MessageComponent;


/**
 * Компонент предмета
 */
public class SubjectComponent extends MessageComponent {

    private final Subject<?> subject;
    private final boolean displayNumber;

    /**
     * Конструктор
     *
     * @param subject       - предмет
     * @param displayNumber - отображение номера дисциплины
     */
    public SubjectComponent(Subject<?> subject, boolean displayNumber) {
        super();
        this.subject = subject;
        this.displayNumber = displayNumber;
    }

    /**
     * Конструктор
     *
     * @param subject - дисциплина
     */
    public SubjectComponent(Subject<?> subject) {
        this(subject, true);
    }

    /**
     * Возвращает предмет
     *
     * @return - предмет
     */
    public Subject<?> getSubject() {
        return subject;
    }


    @Override
    public String toString() {
        return this.displayNumber ?
                String.format("%d. %s", this.getSubject().getIndex() + 1, this.getSubject().getTitle()) :
                this.getSubject().getTitle();
    }
}

