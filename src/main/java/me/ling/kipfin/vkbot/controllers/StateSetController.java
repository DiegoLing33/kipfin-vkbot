package me.ling.kipfin.vkbot.controllers;

import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.entities.BTAnswerType;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.utils.BTUtils;

/**
 * Контроллер установки группы
 */
public class StateSetController extends MessageController {

    @Override
    public boolean test(String text, BTUser user, ControllerArgs args) {
        return BTUtils.getStateFromTextOrNull(text) != null;
    }

    @Override
    public Object execute(String text, BTUser user, ControllerArgs args) {
        var state = BTUtils.getStateFromTextOrNull(text);
        if (state != null) {
            user.setState(state);
            user.reload(false);
            if (user.isTeacher() || user.isStudent()) return BTAnswerType.HOME.random(user.isTeacher());
            return BTAnswerType.HOME_UNDEFINED.random();
        } else {
            return BTAnswerType.ERROR.random();
        }
    }
}
