package me.ling.kipfin.vkbot.activities.core.controllers;

import me.ling.kipfin.vkbot.activities.TimetableController;
import me.ling.kipfin.vkbot.app.BTActivity;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.entities.message.CoreMessage;
import me.ling.kipfin.vkbot.utils.builders.KeyboardBuilder;
import me.ling.kipfin.vkbot.entities.VKBTAnswer;
import me.ling.kipfin.vkbot.entities.VKUser;
import org.jetbrains.annotations.NotNull;

@BTActivity
public class HomeController extends TimetableController {
    @Override
    public boolean test(String text, VKUser user, ControllerArgs args) {
        return args.testMainArg("Домой", "Начать", "/start", "Начало");
    }

    @NotNull
    @Override
    public CoreMessage execute(String text, VKUser user, ControllerArgs args) {
        user.reload();
        if (user.isStudent() || user.isTeacher()) return VKBTAnswer.HOME.toTextMessage(user.isTeacher());
        this.checkUserState(user);
        var m = VKBTAnswer.HOME_UNDEFINED.toTextMessage();
        m.setKeyboard(KeyboardBuilder.startInlineKeyboard);
        return m;
    }
}
