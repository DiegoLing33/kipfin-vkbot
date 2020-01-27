package me.ling.kipfin.vkbot.app;

import com.petersamokhin.bots.sdk.clients.Group;
import me.ling.kipfin.core.log.WithLogger;
import me.ling.kipfin.vkbot.controllers.GroupSetController;
import me.ling.kipfin.vkbot.database.BotValuesDB;
import me.ling.kipfin.vkbot.entities.BotUser;
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
     * @param groupId   - группа
     * @param token     - токен
     */
    public Application(Integer groupId, String token) {
        super("Application");
        this.groupId = groupId;
        this.token = token;
    }

    /**
     * Запускает приложение
     * @throws SQLException - SQL ошибки
     */
    public void start() throws SQLException {
        this.log("Запуск приложения...");
        this.log("Загрузка дополнительных БД...");
        BotValuesDB.shared.update();

        org.apache.log4j.BasicConfigurator.configure();
        org.apache.log4j.Logger.getRootLogger().setLevel(Level.OFF);

        CommandsRouter router = new CommandsRouter();

        router.addController(new GroupSetController());

        Group group = new Group(this.groupId, this.token);

        this.log(true, "Приложение запущено!");
        group.onMessage(message -> {
            BotUser botUser = BotUser.getInstance(message.authorId());
            String result = router.execute(message.getText(), botUser);
            String responseText = result == null ? "Неизвестная команда." : result;

            new Message()
                    .from(group)
                    .to(botUser.getUserId())
                    .text(responseText)
                    .setKeyboard(botUser.getKeyboard())
                    .send();
        });
    }

    /**
     * Возвращает идентификатор группы
     * @return  - ID группы
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * Возвращает токен
     * @return  - токен
     */
    public String getToken() {
        return token;
    }
}
