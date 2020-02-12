package me.ling.kipfin.vkbot.actions.controllers;

import me.ling.kipfin.vkbot.app.BTActivity;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.builders.KeyboardBuilder;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import org.jetbrains.annotations.NotNull;

@BTActivity
public class AdditionalController extends TimetableController {
    @Override
    public boolean test(String text, VKUser user, ControllerArgs args) {
        return args.testMainArg("Дополнительно");
    }

    @NotNull
    @Override
    public TextMessage execute(String text, VKUser user, ControllerArgs args) {
        this.checkUserState(user);
        user.setKeyboard(user.isStudent() ? KeyboardBuilder.addictionStudentKeyboard : KeyboardBuilder.addictionTeacherKeyboard);
        return new TextMessage("Дополнительная информация 👾");
    }
}
