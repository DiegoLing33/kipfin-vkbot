package me.ling.kipfin.vkbot.controllers.core;

import me.ling.kipfin.vkbot.app.Application;
import me.ling.kipfin.vkbot.commands.Controller;
import me.ling.kipfin.vkbot.commands.ControllerArgs;
import me.ling.kipfin.vkbot.entities.BTUser;

public class VersionController extends Controller {
    @Override
    public boolean test(String text, BTUser user, ControllerArgs args) {
        return args.test("/version", "версия", "/v");
    }

    @Override
    protected Object execute(String text, BTUser user, ControllerArgs args) {
        return Application.Version;
    }
}
