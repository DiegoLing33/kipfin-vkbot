package me.ling.kipfin.vkbot.actions.controllers.core;

import me.ling.kipfin.vkbot.app.Application;
import me.ling.kipfin.vkbot.app.BTController;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.actions.controllers.TimetableController;
import me.ling.kipfin.vkbot.entities.VKBotAnswer;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import me.ling.kipfin.vkbot.entities.message.TextMessageButch;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@BTController
public class HelpController extends TimetableController {
    @Override
    public boolean test(String text, VKUser user, ControllerArgs args) {
        return this.testAlias(text, "справка", "помощь");
    }

    @NotNull
    @Override
    protected TextMessage execute(String text, VKUser user, ControllerArgs args) {
        return new TextMessageButch(List.of(
                new VKBotAnswer("HELP_p1").toTextMessage(),
                new VKBotAnswer("HELP_p2").toTextMessage(),
                new TextMessage(new VKBotAnswer("HELP_p3").random() + "\n\n" + Application.Version)));
    }

}
