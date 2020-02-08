package me.ling.kipfin.vkbot.app;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import me.ling.kipfin.core.log.WithLogger;
import me.ling.kipfin.vkbot.actions.admin.broadcastupdate.BroadcastUpdateController;
import me.ling.kipfin.vkbot.actions.admin.stat.StatController;
import me.ling.kipfin.vkbot.actions.getrouter.GetRouterController;
import me.ling.kipfin.vkbot.actions.timetableday.TimetableDayController;
import me.ling.kipfin.vkbot.actions.timetablenow.TimetableNowController;
import me.ling.kipfin.vkbot.actions.timetableweek.TimetableWeekController;
import me.ling.kipfin.vkbot.controllers.AdditionalController;
import me.ling.kipfin.vkbot.controllers.HomeController;
import me.ling.kipfin.vkbot.controllers.StateSetController;
import me.ling.kipfin.vkbot.controllers.core.HelpController;
import me.ling.kipfin.vkbot.controllers.core.UpdateDBController;
import me.ling.kipfin.vkbot.controllers.core.VersionController;
import me.ling.kipfin.vkbot.controllers.group.SelectGroupController;
import me.ling.kipfin.vkbot.controllers.group.SelectGroupNextController;
import me.ling.kipfin.vkbot.database.BotAnswersDB;
import me.ling.kipfin.vkbot.database.BotValuesDB;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import me.ling.kipfin.vkbot.vk.VKApiApplication;

import java.sql.SQLException;

/**
 * Приложение
 */
public class Application extends WithLogger {

    public static final String Version = "1.1 Maven Of Time (Build: 31t50)";
    protected final VKApiApplication vkApiApplication;

    /**
     * Конструткор
     *
     * @param groupId - группа
     * @param token   - токен
     */
    public Application(Integer groupId, String token) {
        super("Application");
        vkApiApplication = new VKApiApplication(groupId, token);
    }

    /**
     * Запускает приложение
     */
    public void start() {
        try {
            this.log("Запуск приложения...");
            this.log("Загрузка дополнительных БД...");

            // Загрузка данных из БД
            BotValuesDB.shared.update();
            BotAnswersDB.shared.update();

            // Добавленеи контроллеров команд
            // todo упаковать в файлы конфигурации
            var router = this.vkApiApplication.getReceiver().getRouter();
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

            // Дебаг о запуске todo - упаковать в утилиты
            this.vkApiApplication.getMessenger().sendTextMessage(new TextMessage("Бот запущен"),
                    VKUser.getInstance(this.vkApiApplication, 49062753));
            this.vkApiApplication.run();
        } catch (InterruptedException | SQLException | ApiException | ClientException e) {
            e.printStackTrace();
            try {
                // Сообщение об ошибке в личку todo - упаковать в утилиты
                this.vkApiApplication.getMessenger().sendTextMessage(new TextMessage(e.getMessage()),
                        VKUser.getInstance(this.vkApiApplication, 49062753));
            } catch (ClientException | ApiException ex) {
                ex.printStackTrace();
            }
            start();
        }
    }
}
