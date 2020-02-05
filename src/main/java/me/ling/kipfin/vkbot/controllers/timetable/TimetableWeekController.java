package me.ling.kipfin.vkbot.controllers.timetable;

import me.ling.kipfin.core.utils.DateUtils;
import me.ling.kipfin.timetable.entities.TimetableMaster;
import me.ling.kipfin.timetable.managers.TimetableManager;
import me.ling.kipfin.vkbot.commands.ControllerArgs;
import me.ling.kipfin.vkbot.controllers.TimetableController;
import me.ling.kipfin.vkbot.entities.BTAnswerType;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.messagemodels.TimetableWeekModel;
import me.ling.kipfin.vkbot.utils.BTUtils;

import java.time.LocalDate;

public class TimetableWeekController extends TimetableController {
    @Override
    public boolean test(String text, BTUser user, ControllerArgs args) {
        return args.test("Неделя") ||
                (args.test("Неделя") && BTUtils.checkTextIsState(String.join(" ", args)));
    }

    @Override
    protected Object execute(String text, BTUser user, ControllerArgs args) {
        String state = BTUtils.getStateFromStringOrUser(args.getOrNull(0), user);
        if (BTUtils.isStateTeacher(state)) return new BTAnswerType("WEEK_TEACHER_NOT").random();

        var modal = new TimetableWeekModel(state,
                TimetableManager.downloadOrGetCache(DateUtils.toLocalDateString(LocalDate.now())));
        String[] result = new String[5];
        for (int i = 0; i < 5; i++) result[i] = modal.getMainComponent(i).toString();
        return result;
    }
}
