package me.ling.kipfin.vkbot.activities.core.controllers;

import me.ling.kipfin.vkbot.activities.TimetableController;
import me.ling.kipfin.vkbot.app.BTActivity;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.entities.message.CoreMessage;
import me.ling.kipfin.vkbot.utils.builders.KeyboardBuilder;
import me.ling.kipfin.vkbot.entities.VKUser;
import org.jetbrains.annotations.NotNull;

@BTActivity
public class AdditionalController extends TimetableController {
    @Override
    public boolean test(String text, VKUser user, ControllerArgs args) {
        return args.testMainArg("Дополнительно");
    }

    @NotNull
    @Override
    public CoreMessage execute(String text, VKUser user, ControllerArgs args) {
        this.checkUserState(user);
        user.setKeyboard(user.isStudent() ? KeyboardBuilder.addictionStudentKeyboard : KeyboardBuilder.addictionTeacherKeyboard);
        return new CoreMessage("Дополнительная информация 👾", null);
    }
}
