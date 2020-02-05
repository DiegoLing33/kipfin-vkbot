package me.ling.kipfin.vkbot.controllers;

import me.ling.kipfin.vkbot.commands.ControllerArgs;
import me.ling.kipfin.vkbot.entities.BTAnswerType;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.commands.Controller;

public class HomeController extends Controller {
    @Override
    public boolean test(String text, BTUser user, ControllerArgs args) {
        return args.test("Домой", "Начать", "/start", "Начало");
    }

    @Override
    public Object execute(String text, BTUser user, ControllerArgs args) {
        user.reload();
        if (user.isStudent() || user.isTeacher()) return BTAnswerType.HOME.random(user.isTeacher());
        return BTAnswerType.HOME_UNDEFINED.random();
    }
}
