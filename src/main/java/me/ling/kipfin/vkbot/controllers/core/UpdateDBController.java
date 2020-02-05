package me.ling.kipfin.vkbot.controllers.core;

import me.ling.kipfin.database.university.GroupsDB;
import me.ling.kipfin.database.university.TeachersDB;
import me.ling.kipfin.vkbot.commands.ControllerArgs;
import me.ling.kipfin.vkbot.database.BotAnswersDB;
import me.ling.kipfin.vkbot.database.BotValuesDB;
import me.ling.kipfin.vkbot.entities.BTAnswerType;
import me.ling.kipfin.vkbot.entities.BotAnswer;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.commands.Controller;

import java.sql.SQLException;

/**
 * Обновление баз данных
 */
public class UpdateDBController extends Controller {

    @Override
    public boolean test(String text, BTUser user, ControllerArgs args) {
        return user.isAdmin() && args.test("/update-db");
    }

    @Override
    public String execute(String text, BTUser user, ControllerArgs args) {
        if(!user.isAdmin()) return BTAnswerType.DENY_PERMISSIONS.random();
        try {
            GroupsDB.shared.update();
            TeachersDB.shared.update();
            BotValuesDB.shared.update();
            BotAnswersDB.shared.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Базы данных обновлены 🕺🏼";
    }
}