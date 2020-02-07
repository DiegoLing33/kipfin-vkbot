package me.ling.kipfin.vkbot.app;

import com.petersamokhin.bots.sdk.clients.Group;
import me.ling.kipfin.core.log.WithLogger;
import me.ling.kipfin.core.utils.StringUtils;
import me.ling.kipfin.vkbot.actions.admin.broadcastupdate.BroadcastUpdateController;
import me.ling.kipfin.vkbot.actions.admin.stat.StatController;
import me.ling.kipfin.vkbot.controllers.AdditionalController;
import me.ling.kipfin.vkbot.controllers.HomeController;
import me.ling.kipfin.vkbot.controllers.StateSetController;
import me.ling.kipfin.vkbot.controllers.core.HelpController;
import me.ling.kipfin.vkbot.controllers.core.UpdateDBController;
import me.ling.kipfin.vkbot.controllers.core.VersionController;
import me.ling.kipfin.vkbot.actions.getrouter.GetRouterController;
import me.ling.kipfin.vkbot.controllers.group.SelectGroupController;
import me.ling.kipfin.vkbot.controllers.group.SelectGroupNextController;
import me.ling.kipfin.vkbot.actions.timetablenow.TimetableNowController;
import me.ling.kipfin.vkbot.actions.timetableday.TimetableDayController;
import me.ling.kipfin.vkbot.actions.timetableweek.TimetableWeekController;
import me.ling.kipfin.vkbot.database.BotAnswersDB;
import me.ling.kipfin.vkbot.database.BotValuesDB;
import me.ling.kipfin.vkbot.entities.BTAnswerType;
import me.ling.kipfin.vkbot.entities.BTUser;
import me.ling.kipfin.vkbot.managers.BTStatsManager;
import me.ling.kipfin.vkbot.utils.BroadcastMessage;
import me.ling.kipfin.vkbot.utils.tweak.Message;
import org.apache.commons.collections4.Closure;
import org.apache.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * Приложение
 */
public class Application extends WithLogger {

    public static final String Version = "1.1 Maven Of Time (Build: 30t0)";

    protected final Integer groupId;
    protected final String token;
    protected final Group group;

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
        group = new Group(this.groupId, this.token);
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

        router.addController(new BroadcastUpdateController());

        router.addController(new HelpController());
        router.addController(new HomeController());
        router.addController(new StateSetController());
        router.addController(new AdditionalController());

        router.addController(new SelectGroupController());
        router.addController(new SelectGroupNextController());
        router.addController(new GetRouterController());

        router.addController(new TimetableDayController());
        router.addController(new TimetableNowController());
        router.addController(new TimetableWeekController());

        router.addController(new VersionController());
        router.addController(new StatController());
        router.addController(new UpdateDBController());

        this.log(true, "Приложение запущено!");

        // Long poll starts right here
        group.onMessage(message -> {
            try {
                String inputText = StringUtils.removeAllSpaces(message.getText());
                BTUser btUser = BTUser.getInstance(group, message.authorId());
                Object result = router.execute(inputText, btUser);
                BTStatsManager.add(btUser.getUserId(), message.getText());

                if (result != null) {
                    if (result instanceof String)
                        this.sendMessage(btUser, (String) result);
                    else if (result instanceof String[]) {
                        this.sendAsyncButch(btUser, (String[]) result);
                    } else if (result instanceof BroadcastMessage) {
                        this.sendBroadcast((BroadcastMessage) result);
                    }
                } else {
                    this.sendMessage(btUser, BTAnswerType.UNKNOWN_COMMAND.random());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public void sendBroadcast(BroadcastMessage message) {
        message.getUsers().forEach(id -> {
            var user = BTUser.getInstance(this.group, id);
            var text = BTUser.template(message.getMessage(), user);
            new Message()
                    .from(this.group)
                    .to(id)
                    .text(text)
                    .photo(message.getImage())
                    .send();
        });
    }

    /**
     * Отправляет несколько сообщений пользователю
     *
     * @param user     - пользователь
     * @param messages - сообщения
     */
    public void sendAsyncButch(BTUser user, @NotNull String[] messages) {
        this.sendMessage(user, messages[0], r -> {
            if (messages.length > 1) this.sendAsyncButch(user, Arrays.copyOfRange(messages, 1, messages.length));
        });
    }

    /**
     * Отправляет сообщение
     *
     * @param btUser   - получатель
     * @param text     - текст
     * @param function - функция ответа
     */
    public void sendMessage(@NotNull BTUser btUser, String text, @Nullable Closure<Object> function) {
        new Message().from(this.group).to(btUser.getUserId()).text(BTUser.template(text, btUser))
                .setKeyboard(btUser.getKeyboard()).send(res -> {
            if (function != null) function.execute(res);
        });
    }

    /**
     * Отправляет сообщение
     *
     * @param btUser - получатель
     * @param text   - текст
     */
    public void sendMessage(@NotNull BTUser btUser, String text) {
        this.sendMessage(btUser, text, null);
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
