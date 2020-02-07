package me.ling.kipfin.vkbot.controllers.core;

import me.ling.kipfin.vkbot.app.Application;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.controllers.TimetableController;
import me.ling.kipfin.vkbot.entities.BTAnswerType;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.entities.BotAnswer;

public class HelpController extends TimetableController {
    @Override
    public boolean test(String text, BTUser user, ControllerArgs args) {
        return this.testAlias(text,"справка", "помощь");
    }

    @Override
    protected Object execute(String text, BTUser user, ControllerArgs args) {
        return new String[]{
                new BTAnswerType("HELP_p1").random(),
                new BTAnswerType("HELP_p2").random(),
                new BTAnswerType("HELP_p3").random() + "\n\n" + Application.Version
        };
    }
}
