package me.ling.kipfin.vkbot.messagemodels;

import me.ling.kipfin.core.utils.DateUtils;
import me.ling.kipfin.timetable.abstracts.AbstractAnalyzer;
import me.ling.kipfin.timetable.analyzers.GroupAnalyzer;
import me.ling.kipfin.timetable.analyzers.TeacherAnalyzer;
import me.ling.kipfin.timetable.entities.TimetableMaster;
import me.ling.kipfin.timetable.exceptions.NoTimetableOnDateException;
import me.ling.kipfin.timetable.exceptions.timetable.NoSubjectsException;
import me.ling.kipfin.vkbot.entities.BTAnswerType;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.messagecomponents.MessageComponent;
import me.ling.kipfin.vkbot.messagecomponents.TextComponent;
import me.ling.kipfin.vkbot.messagecomponents.timetable.*;
import me.ling.kipfin.vkbot.utils.BTUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Модель расписания
 */
public class SubjectsModel {

    /**
     * Создает массив компонентов предметов из списка
     *
     * @param group  - группа
     * @param index  - индекс дня
     * @param master - мастер расписание
     * @return - массив компонентов
     */
    public static List<SubjectComponent> getSubjectComponents(String group, Integer index, @NotNull TimetableMaster master) {
        return master.getWeek().getGroupWeek(group).get(index).stream().map(SubjectComponent::new).collect(Collectors.toList());
    }

    /**
     * Создает массив компонентов предметов из списка
     *
     * @param state  - состояние
     * @param master - мастер расписание
     * @return - массив компонентов
     */
    public static List<MessageComponent> getMessageComponents(String state, @NotNull TimetableMaster master) {
        boolean isTeacher = BTUtils.isStateTeacher(state);
        return isTeacher ? master.getClassrooms().get(state).stream().map(ClassroomComponent::new).collect(Collectors.toList()) :
                master.getGroupSubjects(state).stream().map(ExtendedSubjectComponent::new).collect(Collectors.toList());
    }

    /**
     * Создает массив компонентов предметов из списка
     *
     * @param group  - группа
     * @param master - мастер расписание
     * @return - массив компонентов
     */
    public static List<WithTimeComponent<MessageComponent>> getWithTimeComponents(String group, @NotNull TimetableMaster master) {
        return SubjectsModel.getMessageComponents(group, master).stream().map(messageComponent -> {
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
     * Возвращает текст или null
     *
     * @param state  - состояние
     * @param master - мастер расписание
     * @return - дополнительный текст или null
     */
    @Nullable
    public static String getTextOrNull(String state, TimetableMaster master) {
        boolean isTeacher = BTUtils.isStateTeacher(state);
        try {
            AbstractAnalyzer<?> analyzer = isTeacher ? new TeacherAnalyzer(state, master) : new GroupAnalyzer(state, master);
            int firstIndex = analyzer.getFirstIndex();
            if (firstIndex > 0) return new BTAnswerType(String.format("%dD_SUB", firstIndex + 1)).random(isTeacher);
            return null;
        } catch (NoSubjectsException ex) {
            return BTAnswerType.NO_SUBJECTS.random(isTeacher);
        } catch (NoTimetableOnDateException ex) {
            return BTAnswerType.NO_TIMETABLE_AT_DATE.random(isTeacher);
        }
    }

    /**
     * Извлекает компонент текста из дня расписания
     *
     * @param state  - состояние
     * @param master - мастер расписание
     * @return - компонент тексте
     */
    @Nullable
    public static TextComponent getTextComponentOrNull(String state, TimetableMaster master) {
        var text = SubjectsModel.getTextOrNull(state, master);
        return text == null ? null : new TextComponent(text);
    }

    /**
     * Создает копонент с расписанием на день из мастер-расписания
     *
     * @param group  - группа
     * @param btUser - пользователь
     * @param master - мастер расписание
     * @return - компонент расписания на день
     */
    @NotNull
    public static TimetableDayComponent<WithTimeComponent<MessageComponent>> getExtendedDay(String group, BTUser btUser, @NotNull TimetableMaster master) {
        LocalDateTime localDateTime = LocalDateTime.of(DateUtils.fromLocalDateString(master.getDate()), LocalTime.of(0, 0));
        return new TimetableDayComponent<>(
                new TimetableHeaderComponent(group, localDateTime, false),
                SubjectsModel.getWithTimeComponents(group, master),
                SubjectsModel.getTextComponentOrNull(group, master)
        );
    }

}
