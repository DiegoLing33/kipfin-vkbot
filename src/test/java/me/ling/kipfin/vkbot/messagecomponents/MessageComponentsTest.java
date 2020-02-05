package me.ling.kipfin.vkbot.messagecomponents;

import me.ling.kipfin.vkbot.messagecomponents.timetable.TimetableHeaderComponent;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;



class MessageComponentsTest {

    @Test
    void testTimetableHeaderComponent(){
        var date = LocalDateTime.of(2020, Month.FEBRUARY, 4, 12, 0);
        var com = new TimetableHeaderComponent("1ИСИП-319", date, true);

        assertEquals(com.getDateLine(), "04.02.2020 (Вторник)");
        assertEquals(com.getInfoLine(), "Группа: 1ИСИП-319");
        assertEquals(com.getTimeLine(), "Сейчас: 12:00");

        assertEquals(com.toString(), "04.02.2020 (Вторник)\nГруппа: 1ИСИП-319\nСейчас: 12:00");
    }

}