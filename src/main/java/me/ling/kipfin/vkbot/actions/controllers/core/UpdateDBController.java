package me.ling.kipfin.vkbot.actions.controllers.core;

import me.ling.kipfin.database.university.GroupsDB;
import me.ling.kipfin.database.university.TeachersDB;
import me.ling.kipfin.vkbot.app.BTController;
import me.ling.kipfin.vkbot.app.ControllerArgs;
import me.ling.kipfin.vkbot.database.BotAnswersDB;
import me.ling.kipfin.vkbot.database.BotValuesDB;
import me.ling.kipfin.vkbot.entities.VKBotAnswer;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.app.MessageController;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

/**
 * Обновление баз данных
 */
@BTController
public class UpdateDBController extends MessageController {

    @Override
    public boolean test(String text, VKUser user, ControllerArgs args) {
        return user.isAdmin() && args.testMainArg("/update-db");
    }

    @NotNull
    @Override
    public TextMessage execute(String text, VKUser user, ControllerArgs args) {
        if(!user.isAdmin()) return VKBotAnswer.DENY_PERMISSIONS.toTextMessage();
        try {
            GroupsDB.shared.update();
            TeachersDB.shared.update();
            BotValuesDB.shared.update();
            BotAnswersDB.shared.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new TextMessage( "Базы данных обновлены 🕺🏼");
    }
}
