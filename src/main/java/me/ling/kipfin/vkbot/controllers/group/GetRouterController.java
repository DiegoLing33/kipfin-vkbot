package me.ling.kipfin.vkbot.controllers.group;

import me.ling.kipfin.core.entities.university.Teacher;
import me.ling.kipfin.database.university.GroupsDB;
import me.ling.kipfin.database.university.TeachersDB;
import me.ling.kipfin.vkbot.commands.ControllerArgs;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.commands.Controller;
import me.ling.kipfin.vkbot.messagemodels.group.GetRouterModel;

/**
 * Контрллер руководителей
 */
public class GetRouterController extends Controller {

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
