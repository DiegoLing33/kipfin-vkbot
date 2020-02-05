package me.ling.kipfin.vkbot.controllers.core;

import me.ling.kipfin.vkbot.app.Application;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.entities.BTUser;

public class VersionController extends MessageController {
    @Override
    public boolean test(String text, BTUser user, ControllerArgs args) {
        return args.test("/version", "версия", "/v");
    }

    @Override
    protected Object execute(String text, BTUser user, ControllerArgs args) {
        return Application.Version;
    }
}
