package me.ling.kipfin.vkbot.activities.core.controllers;

import me.ling.kipfin.vkbot.app.Application;
import me.ling.kipfin.vkbot.app.BTActivity;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.activities.TimetableController;
import me.ling.kipfin.vkbot.entities.VKBTAnswer;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import me.ling.kipfin.vkbot.entities.message.TextMessageButch;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@BTActivity
public class HelpController extends TimetableController {
    @Override
    public boolean test(String text, VKUser user, ControllerArgs args) {
        return this.testAlias(text, "справка", "помощь");
    }

    @NotNull
    @Override
    protected TextMessage execute(String text, VKUser user, ControllerArgs args) {
        return new TextMessageButch(List.of(
                new VKBTAnswer("HELP_p1").toTextMessage(),
                new VKBTAnswer("HELP_p2").toTextMessage(),
                new TextMessage(new VKBTAnswer("HELP_p3").random() + "\n\n" + Application.Version)));
    }

}
