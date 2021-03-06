package me.ling.kipfin.vkbot.activities.group.controllers;

import me.ling.kipfin.vkbot.app.BTActivity;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.entities.message.CoreMessage;
import me.ling.kipfin.vkbot.utils.builders.KeyboardBuilder;
import me.ling.kipfin.vkbot.activities.TimetableController;
import me.ling.kipfin.vkbot.entities.VKUser;
import org.jetbrains.annotations.NotNull;

@BTActivity
public class SelectGroupController extends TimetableController {
    @Override
    public boolean test(String text, VKUser user, @NotNull ControllerArgs args) {
        return this.testAlias(text, "Выбрать группу", "Группа");
    }

    @NotNull
    @Override
    public CoreMessage execute(String text, @NotNull VKUser user, ControllerArgs args) {
        user.setKeyboard(KeyboardBuilder.coursesKeyboard);
        return new CoreMessage("Выберите курс", null);
    }
}
