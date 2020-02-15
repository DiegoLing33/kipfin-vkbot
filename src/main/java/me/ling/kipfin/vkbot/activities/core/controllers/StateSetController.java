package me.ling.kipfin.vkbot.activities.core.controllers;

import me.ling.kipfin.vkbot.app.BTActivity;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.entities.message.CoreMessage;
import me.ling.kipfin.vkbot.utils.builders.KeyboardBuilder;
import me.ling.kipfin.vkbot.entities.VKBTAnswer;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.app.MessageController;
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
    public CoreMessage execute(String text, VKUser user, ControllerArgs args) {
        var state = BTUtils.getStateFromTextOrNull(text);
        if (state != null) {
            user.setState(state);
            user.reload(false);
            if (user.isTeacher() || user.isStudent()) return VKBTAnswer.HOME.toTextMessage(user.isTeacher());
            var message =  VKBTAnswer.HOME_UNDEFINED.toTextMessage();
            message.setKeyboard(KeyboardBuilder.startInlineKeyboard);
            return message;
        } else {
            return VKBTAnswer.ERROR.toTextMessage();
        }
    }
}
