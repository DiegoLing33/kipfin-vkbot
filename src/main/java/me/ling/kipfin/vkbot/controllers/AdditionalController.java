package me.ling.kipfin.vkbot.controllers;

import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.entities.keboard.Keyboard;

public class AdditionalController extends TimetableController {
    @Override
    public boolean test(String text, BTUser user, ControllerArgs args) {
        return args.test("Дополнительно");
    }

    @Override
    public Object execute(String text, BTUser user, ControllerArgs args) {
        this.checkUserState(user);
        user.setKeyboard(user.isStudent() ? Keyboard.additionalStudent : Keyboard.additionalTeacher);
        return "Дополнительная информация 👾";
    }
}
