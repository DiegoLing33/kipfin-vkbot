package me.ling.kipfin.vkbot.app;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import me.ling.kipfin.core.log.WithLogger;
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

    public static final String Version = "1.1 Maven Of Time (Build: 31t53)";
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
            var router = this.vkApiApplication.getReceiver().getRouter();
            router.findActivities();

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
