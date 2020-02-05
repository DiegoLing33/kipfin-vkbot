package me.ling.kipfin.vkbot.messagemodels;

import me.ling.kipfin.abstracts.Indexable;
import me.ling.kipfin.exceptions.NotFoundEntityException;
import me.ling.kipfin.timetable.abstracts.AbstractAnalyzer;
import me.ling.kipfin.timetable.analyzers.GroupAnalyzer;
import me.ling.kipfin.timetable.analyzers.TeacherAnalyzer;
import me.ling.kipfin.timetable.entities.Classroom;
import me.ling.kipfin.timetable.entities.ExtendedSubject;
import me.ling.kipfin.timetable.entities.TimetableMaster;
import me.ling.kipfin.timetable.exceptions.timetable.NoSubjectsException;
import me.ling.kipfin.vkbot.entities.BTAnswerType;
import me.ling.kipfin.vkbot.messagecomponents.MessageComponent;
import me.ling.kipfin.vkbot.messagecomponents.TextComponent;
import me.ling.kipfin.vkbot.messagecomponents.timetable.*;
import me.ling.kipfin.vkbot.utils.BTUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Модель дисциплин Сейчас
 */
public class TimetableNowModel {

    @Nullable
    public static String getTextOrNull(@NotNull String state, LocalTime time, TimetableMaster master) throws NoSubjectsException {
        boolean isTeacher = BTUtils.isStateTeacher(state);
        AbstractAnalyzer<?> analyzer = isTeacher ? new TeacherAnalyzer(state, master) : new GroupAnalyzer(state, master);

        if (!analyzer.isStarted(time)) return BTAnswerType.ITS_EARLY.random(isTeacher);
        if (analyzer.isEnded(time)) return BTAnswerType.ITS_LATE.random(isTeacher);
        return null;
    }

    /**
     * Компонент текста или null
     *
     * @param state  - состояние
     * @param time   - время
     * @param master - мастер
     * @return -  компонент дополнительного текста или null
     * @throws NoSubjectsException - нет дисциплин
     */
    @Nullable
    public static TextComponent getTextComponentOrNull(String state, LocalTime time, TimetableMaster master) throws NoSubjectsException {
        String text = TimetableNowModel.getTextOrNull(state, time, master);
        return text != null ? new TextComponent(text) : null;
    }


    /**
     * Возвращает элемент по времени
     *
     * @param state  - состояние
     * @param time   - время
     * @param master - мастер-расписание
     * @return - объект
     * @throws NotFoundEntityException - исключение не найденного предмета
     * @throws NoSubjectsException     - исключенеи когда нет дисциплин
     */
    public static WithTimeComponent<MessageComponent> getSubjectComponentOnTime(String state, LocalTime time, TimetableMaster master) throws NotFoundEntityException, NoSubjectsException {
        boolean isTeacher = BTUtils.isStateTeacher(state);
        var analyzer = isTeacher ? new TeacherAnalyzer(state, master) : new GroupAnalyzer(state, master);
        var closet = analyzer.getClosetInfo(time);
        Indexable<? extends Indexable<?>> obj = analyzer.getObjectByIndex(closet.getClosetIndex());

        MessageComponent component = null;
        if(obj instanceof ExtendedSubject) {
            component = new ExtendedSubjectComponent((ExtendedSubject) obj);
        }else if(obj instanceof Classroom){
            component = new ClassroomComponent((Classroom) obj);
        }
        return new WithTimeComponent<>(component, master.getTimeInfo().get(closet.getClosetIndex()));
    }

    public static TimetableDayComponent<?> getTimetableNowDayComponent(String state, LocalTime time, TimetableMaster master) {
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), time);
        return new TimetableDayComponent<>(
                new TimetableHeaderComponent(state, dateTime, true),
                List.of(TimetableNowModel.getSubjectComponentOnTime(state, time, master)),
                TimetableNowModel.getTextComponentOrNull(state, time, master));
    }

}
