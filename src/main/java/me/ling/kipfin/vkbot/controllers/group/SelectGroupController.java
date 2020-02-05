package me.ling.kipfin.vkbot.controllers.group;

import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.entities.keboard.Keyboard;

public class SelectGroupController extends MessageController {
    @Override
    public boolean test(String text, BTUser user, ControllerArgs args) {
        return this.testAlias(text, "Выбрать группу", "Группа");
    }

    @Override
    public Object execute(String text, BTUser user, ControllerArgs args) {
        user.setKeyboard(Keyboard.courses);
        return "Выберите курс";
    }
}
