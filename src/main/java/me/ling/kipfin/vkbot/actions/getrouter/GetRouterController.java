package me.ling.kipfin.vkbot.actions.getrouter;

import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.controllers.TimetableController;
import me.ling.kipfin.vkbot.entities.BTUser;

/**
 * Контрллер руководителей
 */
public class GetRouterController extends TimetableController {

    @Override
    public boolean test(String text, BTUser user, ControllerArgs args) {
        return (user.isStudent() && this.testAlias(text, "Классный руководитель", "Руководитель")) ||
                (user.isTeacher() && this.testAlias(text, "Группа в руководстве"));
    }

    @Override
    public Object execute(String text, BTUser user, ControllerArgs args) {
        GetRouterModel model = new GetRouterModel(user.getState());
        return model.getComponent().toString();
    }
}
