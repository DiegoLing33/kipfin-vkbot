package me.ling.kipfin.vkbot.controllers;

import me.ling.kipfin.vkbot.commands.ControllerArgs;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.commands.Controller;
import me.ling.kipfin.vkbot.entities.keboard.Keyboard;

public class AdditionalController extends Controller {
    @Override
    public boolean test(String text, BTUser user, ControllerArgs args) {
        return (user.isStudent() || user.isTeacher()) && args.test("Дополнительно");
    }

    @Override
    public Object execute(String text, BTUser user, ControllerArgs args) {
        user.setKeyboard(user.isStudent() ? Keyboard.additionalStudent : Keyboard.additionalTeacher);
        return "Дополнительная информация 👾";
    }
}
