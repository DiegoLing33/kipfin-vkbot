package me.ling.kipfin.vkbot.controllers.group;

import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.controllers.TimetableController;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.entities.keboard.Keyboard;
import org.jetbrains.annotations.NotNull;

public class SelectGroupController extends TimetableController {
    @Override
    public boolean test(String text, BTUser user, @NotNull ControllerArgs args) {
        return this.testAlias(text, "Выбрать группу", "Группа");
    }

    @Override
    public Object execute(String text, @NotNull BTUser user, ControllerArgs args) {
        user.setKeyboard(Keyboard.courses);
        return "Выберите курс";
    }
}
