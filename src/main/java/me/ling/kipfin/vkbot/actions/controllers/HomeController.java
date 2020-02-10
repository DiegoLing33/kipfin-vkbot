package me.ling.kipfin.vkbot.actions.controllers;

import me.ling.kipfin.vkbot.app.BTController;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.builders.KeyboardBuilder;
import me.ling.kipfin.vkbot.entities.VKBotAnswer;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import org.jetbrains.annotations.NotNull;

@BTController
public class HomeController extends TimetableController {
    @Override
    public boolean test(String text, VKUser user, ControllerArgs args) {
        return args.testMainArg("Домой", "Начать", "/start", "Начало");
    }

    @NotNull
    @Override
    public TextMessage execute(String text, VKUser user, ControllerArgs args) {
        user.reload();
        if (user.isStudent() || user.isTeacher()) return VKBotAnswer.HOME.toTextMessage(user.isTeacher());
        this.checkUserState(user);
        return VKBotAnswer.HOME_UNDEFINED.toTextMessage().setKeyboard(KeyboardBuilder.startInlineKeyboard);
    }
}
