package me.ling.kipfin.vkbot.actions.controllers;

import me.ling.kipfin.vkbot.app.BTActivity;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.builders.KeyboardBuilder;
import me.ling.kipfin.vkbot.entities.VKBotAnswer;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import me.ling.kipfin.vkbot.utils.BTUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Контроллер установки группы
 */
@BTActivity
public class StateSetController extends MessageController {

    @Override
    public boolean test(String text, VKUser user, ControllerArgs args) {
        return BTUtils.getStateFromTextOrNull(text) != null;
    }

    @NotNull
    @Override
    public TextMessage execute(String text, VKUser user, ControllerArgs args) {
        var state = BTUtils.getStateFromTextOrNull(text);
        if (state != null) {
            user.setState(state);
            user.reload(false);
            if (user.isTeacher() || user.isStudent()) return VKBotAnswer.HOME.toTextMessage(user.isTeacher());
            return VKBotAnswer.HOME_UNDEFINED.toTextMessage().setKeyboard(KeyboardBuilder.startInlineKeyboard);
        } else {
            return VKBotAnswer.ERROR.toTextMessage();
        }
    }
}
