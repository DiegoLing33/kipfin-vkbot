package me.ling.kipfin.vkbot.controllers.timetable;

import me.ling.kipfin.vkbot.commands.ControllerArgs;
import me.ling.kipfin.vkbot.controllers.TimetableController;
import me.ling.kipfin.vkbot.entities.BTAnswerType;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.utils.BTUtils;

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

        return new String[]{"Пн", "Вт", "Чт"};
    }
}
