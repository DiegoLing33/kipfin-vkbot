package me.ling.kipfin.vkbot.utils.vk;

import com.vk.api.sdk.callback.longpoll.CallbackApiLongPoll;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import me.ling.kipfin.core.utils.StringUtils;
import me.ling.kipfin.vkbot.app.CommandsRouter;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import me.ling.kipfin.vkbot.managers.BTStatsManager;

/**
 * Обработчик Long-poll VK
 * <p>
 * Данный класс не просто принемает сообщения, но и отправляет их в роутинг
 *
 * @see CommandsRouter
 */
public class VKReceiver extends CallbackApiLongPoll {

    private final VKApiApplication vkApiApplication;
    private final CommandsRouter router = new CommandsRouter();

    /**
     * Конструктор
     *
     * @param vkApiApplication - объект VK приложения
     */
    public VKReceiver(VKApiApplication vkApiApplication) {
        super(vkApiApplication.getVk(), vkApiApplication.getActor(), 30);
        this.vkApiApplication = vkApiApplication;
    }

    /**
     * Пришло сообщение
     *
     * @param groupId - id группы
     * @param secret  - секретный ключ
     * @param message - сообщение
     * @see Message
     */
    @Override
    public void messageNew(Integer groupId, String secret, Message message) {
        try {
            String inputText = StringUtils.removeAllSpaces(message.getText());
            VKUser btUser = VKUser.getInstance(this.getVkApiApplication(), message.getFromId());
            TextMessage result = router.execute(inputText, btUser);
            BTStatsManager.add(btUser.getUserId(), message.getText());
            this.getVkApiApplication().getMessenger().sendMessage(result, btUser);
        } catch (ClientException | ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает VK приложение
     *
     * @return - VkApiApplication
     */
    public VKApiApplication getVkApiApplication() {
        return vkApiApplication;
    }

    /**
     * Возвращает роутер команд
     *
     * @return - роутер
     */
    public CommandsRouter getRouter() {
        return router;
    }

}
