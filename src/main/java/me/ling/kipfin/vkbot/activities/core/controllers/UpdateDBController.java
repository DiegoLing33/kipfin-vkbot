package me.ling.kipfin.vkbot.activities.core.controllers;

import me.ling.kipfin.database.university.GroupsDB;
import me.ling.kipfin.database.university.TeachersDB;
import me.ling.kipfin.vkbot.app.BTActivity;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.database.BTAnswersDB;
import me.ling.kipfin.vkbot.database.BTValuesDB;
import me.ling.kipfin.vkbot.entities.VKBTAnswer;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

/**
 * Обновление баз данных
 */
@BTActivity
public class UpdateDBController extends MessageController {

    @Override
    public boolean test(String text, VKUser user, ControllerArgs args) {
        return user.isAdmin() && args.testMainArg("/update-db");
    }

    @NotNull
    @Override
    public TextMessage execute(String text, VKUser user, ControllerArgs args) {
        if(!user.isAdmin()) return VKBTAnswer.DENY_PERMISSIONS.toTextMessage();
        try {
            GroupsDB.shared.update();
            TeachersDB.shared.update();
            BTValuesDB.shared.update();
            BTAnswersDB.shared.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new TextMessage( "Базы данных обновлены 🕺🏼");
    }
}
