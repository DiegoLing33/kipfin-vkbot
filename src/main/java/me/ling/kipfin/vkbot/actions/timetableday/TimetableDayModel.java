package me.ling.kipfin.vkbot.actions.timetableday;

import me.ling.kipfin.core.utils.DateUtils;
import me.ling.kipfin.timetable.abstracts.AbstractAnalyzer;
import me.ling.kipfin.timetable.analyzers.GroupAnalyzer;
import me.ling.kipfin.timetable.analyzers.TeacherAnalyzer;
import me.ling.kipfin.timetable.entities.TimetableMaster;
import me.ling.kipfin.timetable.exceptions.NoTimetableOnDateException;
import me.ling.kipfin.timetable.exceptions.timetable.NoSubjectsException;
import me.ling.kipfin.vkbot.app.MessageModel;
import me.ling.kipfin.vkbot.entities.VKBotAnswer;
import me.ling.kipfin.vkbot.app.MessageComponent;
import me.ling.kipfin.vkbot.messagecomponents.TextComponent;
import me.ling.kipfin.vkbot.messagecomponents.timetable.ClassroomComponent;
import me.ling.kipfin.vkbot.messagecomponents.timetable.ExtendedSubjectComponent;
import me.ling.kipfin.vkbot.messagecomponents.timetable.TimetableHeaderComponent;
import me.ling.kipfin.vkbot.messagecomponents.timetable.WithTimeComponent;
import me.ling.kipfin.vkbot.utils.BTUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Модель расписания на день
 */
public class TimetableDayModel extends MessageModel {

    protected final String state;
    protected final TimetableMaster master;
    protected final AbstractAnalyzer<?> abstractAnalyzer;
    protected final boolean isTeacherState;

    public TimetableDayModel(String state, TimetableMaster master) {
        this.state = state;
        this.master = master;

        this.isTeacherState = BTUtils.isStateTeacher(state);
        this.abstractAnalyzer = isTeacherState ? new TeacherAnalyzer(state, master) : new GroupAnalyzer(state, master);
    }

    public String getState() {
        return state;
    }

    public TimetableMaster getMaster() {
        return master;
    }

    public AbstractAnalyzer<?> getAbstractAnalyzer() {
        return abstractAnalyzer;
    }

    public boolean isTeacher() {
        return isTeacherState;
    }

    /**
     * Возвращает текст или null
     *
     * @return - дополнительный текст или null
     */
    public String getTextOrNull() {
        try {
            int firstIndex = this.getAbstractAnalyzer().getFirstIndex();
            if (firstIndex > 0)
                return new VKBotAnswer(String.format("%dD_SUB", firstIndex + 1)).random(this.isTeacher());
            return null;
        } catch (NoSubjectsException ex) {
            return VKBotAnswer.NO_SUBJECTS.random(isTeacher());
        } catch (NoTimetableOnDateException ex) {
            return VKBotAnswer.NO_TIMETABLE_AT_DATE.random(isTeacher());
        }
    }

    /**
     * Создает массив компонентов предметов из списка
     *
     * @return - массив компонентов
     */
    public List<? extends MessageComponent> getMessageComponents() {
        if(this.isTeacher()) return ClassroomComponent.create(this.getMaster().getClassroomsForName(this.getState()));
        return ExtendedSubjectComponent.create(this.getMaster().getGroupSubjects(this.getState()));
    }

    /**
     * Создает массив компонентов предметов из списка
     *
     * @return - массив компонентов
     */
    public List<WithTimeComponent<? extends MessageComponent>> getWithTimeComponents() {
        return this.getMessageComponents().stream().map(messageComponent -> {
            var index = 0;
            if (messageComponent instanceof ExtendedSubjectComponent) {
                index = ((ExtendedSubjectComponent) messageComponent).getExtendedSubject().getIndex();
            } else if (messageComponent instanceof ClassroomComponent) {
                index = ((ClassroomComponent) messageComponent).getClassroom().getIndex();
            }
            return new WithTimeComponent<>(messageComponent, master.getTimeInfo().get(index));
        }).collect(Collectors.toList());
    }

    /**
     * Возвращает компонент текста или null
     *
     * @return - компонент текста или null
     */
    public TextComponent getTextComponentOrNull() {
        var text = this.getTextOrNull();
        return text == null ? null : new TextComponent(text);
    }

    /**
     * Возвращает компонент заголовка
     *
     * @return - заголовочный компонент
     */
    public TimetableHeaderComponent getHeaderComponent() {
        LocalDate date = DateUtils.fromLocalDateString(this.getMaster().getDate());
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.now());
        return new TimetableHeaderComponent(this.getState(), dateTime, false);
    }

    /**
     * Возвращает компонент
     */
    public TimetableDayComponent<?> getComponent() {
        return new TimetableDayComponent<>(
                this.getHeaderComponent(),
                this.getWithTimeComponents(),
                this.getTextComponentOrNull());
    }
}
