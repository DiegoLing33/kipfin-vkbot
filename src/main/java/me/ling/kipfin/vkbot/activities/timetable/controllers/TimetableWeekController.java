package me.ling.kipfin.vkbot.activities.timetable.controllers;

import me.ling.kipfin.core.utils.DateUtils;
import me.ling.kipfin.timetable.managers.TimetableManager;
import me.ling.kipfin.vkbot.activities.timetable.models.TimetableWeekModel;
import me.ling.kipfin.vkbot.app.BTActivity;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.activities.TimetableController;
import me.ling.kipfin.vkbot.entities.VKBTAnswer;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import me.ling.kipfin.vkbot.entities.message.TextMessageButch;
import me.ling.kipfin.vkbot.utils.BTUtils;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@BTActivity
public class TimetableWeekController extends TimetableController {
    @Override
    public boolean test(String text, VKUser user, ControllerArgs args) {
        return args.testMainArg("Неделя") ||
                (args.testMainArg("Неделя") && BTUtils.checkTextIsState(String.join(" ", args)));
    }

    @NotNull
    @Override
    protected TextMessage execute(String text, VKUser user, ControllerArgs args) {
        this.checkUserState(user);
        String state = BTUtils.getStateFromStringOrUser(args.getOrNull(0), user);
        if (BTUtils.isStateTeacher(state)) return new VKBTAnswer("WEEK_TEACHER_NOT").toTextMessage();

        LocalDate date = LocalDate.now();

        //todo - fix repeats
        int localWeekDay = DateUtils.getLocalWeekDay(date);
        if (localWeekDay == 5) date = date.plus(2, ChronoUnit.DAYS);
        else if (localWeekDay == 6) date = date.plus(1, ChronoUnit.DAYS);

        var modal = new TimetableWeekModel(state,
                TimetableManager.downloadOrGetCache(DateUtils.toLocalDateString(date)));
        List<TextMessage> result = new ArrayList<>();
        int subs = 0;
        for (int i = 0; i < 5; i++) {
            var component = modal.getMainComponent(i);
            subs += component.getSubjectComponents().size();
            result.add(component.toTextMessage());
        }
        if (subs == 0) return VKBTAnswer.WEEK_NO_SUBJECTS.toTextMessage()
                .applyTagValue("date", DateUtils.toLocalDateString(date));
        return new TextMessageButch(result);
    }
}
