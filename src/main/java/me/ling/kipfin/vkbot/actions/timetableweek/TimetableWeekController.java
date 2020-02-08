package me.ling.kipfin.vkbot.actions.timetableweek;

import me.ling.kipfin.core.utils.DateUtils;
import me.ling.kipfin.timetable.managers.TimetableManager;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.controllers.TimetableController;
import me.ling.kipfin.vkbot.entities.VKBotAnswer;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import me.ling.kipfin.vkbot.entities.message.TextMessageButch;
import me.ling.kipfin.vkbot.utils.BTUtils;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TimetableWeekController extends TimetableController {
    @Override
    public boolean test(String text, VKUser user, ControllerArgs args) {
        return args.test("Неделя") ||
                (args.test("Неделя") && BTUtils.checkTextIsState(String.join(" ", args)));
    }

    @NotNull
    @Override
    protected TextMessage execute(String text, VKUser user, ControllerArgs args) {
        this.checkUserState(user);
        String state = BTUtils.getStateFromStringOrUser(args.getOrNull(0), user);
        if (BTUtils.isStateTeacher(state)) return new VKBotAnswer("WEEK_TEACHER_NOT").toTextMessage();

        var modal = new TimetableWeekModel(state,
                TimetableManager.downloadOrGetCache(DateUtils.toLocalDateString(LocalDate.now())));
        List<TextMessage> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) result.add(modal.getMainComponent(i).toTextMessage());
        return new TextMessageButch(result);
    }
}
