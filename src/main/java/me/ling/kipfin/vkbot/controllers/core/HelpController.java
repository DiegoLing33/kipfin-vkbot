package me.ling.kipfin.vkbot.controllers.core;

import me.ling.kipfin.vkbot.app.Application;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.controllers.TimetableController;
import me.ling.kipfin.vkbot.entities.BTAnswerType;
import me.ling.kipfin.vkbot.entities.BTUser;

public class HelpController extends TimetableController {
    @Override
    public boolean test(String text, BTUser user, ControllerArgs args) {
        return this.testAlias(text,"справка", "помощь");
    }

    @Override
    protected Object execute(String text, BTUser user, ControllerArgs args) {
        return BTAnswerType.HELP.random() + "\n\n" + Application.Version;
    }
}
