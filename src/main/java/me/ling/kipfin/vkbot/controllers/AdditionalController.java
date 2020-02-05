package me.ling.kipfin.vkbot.controllers;

import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.entities.keboard.Keyboard;

public class AdditionalController extends MessageController {
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
