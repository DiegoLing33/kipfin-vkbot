package me.ling.kipfin.vkbot.app;

import com.vk.api.sdk.exceptions.ApiException;
import me.ling.kipfin.core.log.WithLogger;
import me.ling.kipfin.vkbot.utils.vk.VKApiApplication;
import me.ling.kipfin.vkbot.utils.vk.VKMessenger;

/**
 * Приложение
 */
public class Application extends WithLogger {

    public static final String Version = "1.1 Maven Of Time (Build: 32t71)";
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
        VKMessenger messenger = vkApiApplication.getMessenger();
        try {
            this.log(true, "Приложение запущено!");
            messenger.sendMessageToAdmin("Бот запущен");
            this.vkApiApplication.run();
        } catch (InterruptedException | ApiException e) {
            e.printStackTrace();
            messenger.sendMessageToAdmin(e.getMessage());

            start();
        }
    }
}
