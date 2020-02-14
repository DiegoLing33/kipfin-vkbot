package me.ling.kipfin.vkbot.utils.vk;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import me.ling.kipfin.core.log.Logger;
import me.ling.kipfin.core.log.WithLogger;

/**
 * Приложение API
 */
public class VKApiApplication extends WithLogger {

    private final int groupId;
    private final String token;
    private final VkApiClient vk;
    private final GroupActor actor;

    private final VKReceiver receiver;
    private final VKMessenger messenger;

    /**
     * Конструктор
     * @param groupId - ID группы
     * @param token - токен группы
     */
    public VKApiApplication(int groupId, String token) {
        super("VK");
        this.groupId = groupId;
        this.token = token;

        HttpTransportClient transportClient = new HttpTransportClient();
        this.vk = new VkApiClient(transportClient);

        this.log(Logger.WAIT, "Авторизация");
        this.actor = new GroupActor(this.getGroupId(), this.getToken());
        this.log(Logger.YES, "Авторизация");

        this.receiver = new VKReceiver(this);
        this.messenger  = new VKMessenger(this);
    }

    /**
     * Запускает приложение
     * @throws ApiException         - ошибки в API
     * @throws InterruptedException - ошибки в системе
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() throws ApiException, InterruptedException {
        this.getReceiver().getRouter().findActivities(); // Выполняет поиск активити

        while (true) {
            try {
                this.getReceiver().run(); // Запускает приемник -- и так каждыйй раз
            }catch (ClientException e ){
                Thread.sleep(10000);
            }
        }
    }

    /**
     * Приемник сообщений
     * @return  - объект VKReceiver
     */
    public VKReceiver getReceiver() {
        return receiver;
    }

    /**
     * Отправитель сообщений
     * @return - объект VKMessenger
     */
    public VKMessenger getMessenger() {
        return messenger;
    }

    /**
     * Функцич для релога
     * @param l - лог сообщение
     */
    public void log(Object... l) {
        super.log(l);
    }

    /**
     * Возвращает ID руппы в ВК
     * @return  - id группы
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * Возвращает токен
     * @return  - токен группы в ВУ
     */
    public String getToken() {
        return token;
    }

    /**
     * Возвращает VK API объект
     * @see VkApiClient
     * @return - VK API интерфейс
     */
    public VkApiClient getVk() {
        return vk;
    }

    /**
     * Возвращает исполнителя (Клиента)
     * @see com.vk.api.sdk.client.actors.Actor
     * @return - клиент
     */
    public GroupActor getActor() {
        return actor;
    }
}
