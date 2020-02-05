package me.ling.kipfin.vkbot.controllers.group;

import me.ling.kipfin.vkbot.commands.ControllerArgs;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.commands.Controller;
import me.ling.kipfin.vkbot.entities.keboard.Keyboard;

public class SelectGroupController extends Controller {
    @Override
    public boolean test(String text, BTUser user, ControllerArgs args) {
        return args.test("Выбрать группу", "Группа");
    }

    @Override
    public Object execute(String text, BTUser user, ControllerArgs args) {
        user.setKeyboard(Keyboard.courses);
        return "Выберите курс";
    }
}
