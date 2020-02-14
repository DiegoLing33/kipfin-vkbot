package me.ling.kipfin.vkbot.activities.routing;

import me.ling.kipfin.vkbot.app.BTActivity;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.activities.TimetableController;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import org.jetbrains.annotations.NotNull;

/**
 * Контрллер руководителей
 */
@BTActivity
public class GetRouterController extends TimetableController {

    @Override
    public boolean test(String text, VKUser user, ControllerArgs args) {
        return (user.isStudent() && this.testAlias(text, "Классный руководитель", "Руководитель")) ||
                (user.isTeacher() && this.testAlias(text, "Группа в руководстве"));
    }

    @NotNull
    @Override
    public TextMessage execute(String text, VKUser user, ControllerArgs args) {
        this.checkUserState(user);
        GetRouterModel model = new GetRouterModel(user.getState());
        return model.getComponent().toTextMessage();
    }
}