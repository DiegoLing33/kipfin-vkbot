package me.ling.kipfin.vkbot.actions.controllers.group;

import me.ling.kipfin.vkbot.app.BTController;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.builders.KeyboardBuilder;
import me.ling.kipfin.vkbot.actions.controllers.TimetableController;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import org.jetbrains.annotations.NotNull;

@BTController
public class SelectGroupController extends TimetableController {
    @Override
    public boolean test(String text, VKUser user, @NotNull ControllerArgs args) {
        return this.testAlias(text, "Выбрать группу", "Группа");
    }

    @NotNull
    @Override
    public TextMessage execute(String text, @NotNull VKUser user, ControllerArgs args) {
        user.setKeyboard(KeyboardBuilder.coursesKeyboard);
        return new TextMessage("Выберите курс");
    }
}
