package me.ling.kipfin.vkbot.activities.core.controllers;

import me.ling.kipfin.vkbot.app.Application;
import me.ling.kipfin.vkbot.app.BTActivity;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import org.jetbrains.annotations.NotNull;

@BTActivity
public class VersionController extends MessageController {
    @Override
    public boolean test(String text, VKUser user, ControllerArgs args) {
        return args.testMainArg("/version", "версия", "/v");
    }

    @NotNull
    @Override
    protected TextMessage execute(String text, VKUser user, ControllerArgs args) {
        return new TextMessage(Application.Version);
    }
}
