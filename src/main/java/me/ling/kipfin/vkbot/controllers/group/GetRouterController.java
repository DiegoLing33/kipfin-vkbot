package me.ling.kipfin.vkbot.controllers.group;

import me.ling.kipfin.core.entities.university.Teacher;
import me.ling.kipfin.database.university.GroupsDB;
import me.ling.kipfin.database.university.TeachersDB;
import me.ling.kipfin.vkbot.commands.ControllerArgs;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.commands.Controller;

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
        if (user.isStudent()) {
            for (Teacher teacher : TeachersDB.shared.getCache().values()) {
                Integer groupId = teacher.getGroupId();
                if (groupId != null) {
                    String group = GroupsDB.shared.getById(groupId).getTitle();
                    if (group.equals(user.getState())) {
                        return "Группа " + user.getState() + "\nКлассный руководитель: " + teacher.getName();
                    }
                }
            }
            return "Хм, я не могу найти классного руководителя для группы " + user.getState();
        } else if (user.isTeacher()) {
            for (Teacher teacher : TeachersDB.shared.getCache().values()) {
                if (teacher.getName().equals(user.getState())) {
                    Integer groupId = teacher.getGroupId();
                    if (groupId != null) {
                        String group = GroupsDB.shared.getById(groupId).getTitle();
                        return "Вы руководите группой: " + group;
                    }
                }
            }
            return "Ничего не найдено по запросу " + user.getState() + " и [классный руководитель]... Смею предположить, что у Вас нет группы 😶";
        }
        return "Ничего не найдено";
    }
}
