package me.ling.kipfin.vkbot.app;

import com.petersamokhin.bots.sdk.clients.Group;
import me.ling.kipfin.core.log.WithLogger;
import me.ling.kipfin.core.utils.StringUtils;
import me.ling.kipfin.vkbot.commands.CommandsRouter;
import me.ling.kipfin.vkbot.controllers.AdditionalController;
import me.ling.kipfin.vkbot.controllers.HomeController;
import me.ling.kipfin.vkbot.controllers.StateSetController;
import me.ling.kipfin.vkbot.controllers.core.UpdateDBController;
import me.ling.kipfin.vkbot.controllers.group.GetRouterController;
import me.ling.kipfin.vkbot.controllers.group.SelectGroupController;
import me.ling.kipfin.vkbot.controllers.group.SelectGroupNextController;
import me.ling.kipfin.vkbot.controllers.timetable.TimetableNowController;
import me.ling.kipfin.vkbot.controllers.timetable.TimetableDayController;
import me.ling.kipfin.vkbot.database.BotAnswersDB;
import me.ling.kipfin.vkbot.database.BotValuesDB;
import me.ling.kipfin.vkbot.entities.BTAnswerType;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.tweak.Message;
import org.apache.log4j.Level;

import java.sql.SQLException;

/**
 * Приложение
 */
public class Application extends WithLogger {


    protected Integer groupId;
    protected String token;

    /**
     * Конструткор
     *
     * @param groupId - группа
     * @param token   - токен
     */
    public Application(Integer groupId, String token) {
        super("Application");
        this.groupId = groupId;
        this.token = token;
    }

    /**
     * Запускает приложение
     *
     * @throws SQLException - SQL ошибки
     */
    public void start() throws SQLException {
        this.log("Запуск приложения...");
        this.log("Загрузка дополнительных БД...");
        BotValuesDB.shared.update();
        BotAnswersDB.shared.update();

        org.apache.log4j.BasicConfigurator.configure();
        org.apache.log4j.Logger.getRootLogger().setLevel(Level.OFF);

        CommandsRouter router = new CommandsRouter();

        router.addController(new HomeController());
        router.addController(new StateSetController());
        router.addController(new AdditionalController());

        router.addController(new SelectGroupController());
        router.addController(new SelectGroupNextController());
        router.addController(new GetRouterController());

        router.addController(new TimetableDayController());
        router.addController(new TimetableNowController());

        router.addController(new UpdateDBController());

        Group group = new Group(this.groupId, this.token);
        this.log(true, "Приложение запущено!");

        // Long poll starts right here
        group.onMessage(message -> {
            String inputText = StringUtils.removeAllSpaces(message.getText());
            BTUser btUser = BTUser.getInstance(group, message.authorId());
            String result = router.execute(inputText, btUser);

            String responseText = result == null ? BTAnswerType.UNKNOWN_COMMAND.random(btUser) : result;

            new Message().from(group).to(btUser.getUserId()).text(responseText)
                    .setKeyboard(btUser.getKeyboard()).send();
        });
    }

    /**
     * Возвращает идентификатор группы
     *
     * @return - ID группы
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * Возвращает токен
     *
     * @return - токен
     */
    public String getToken() {
        return token;
    }
}
