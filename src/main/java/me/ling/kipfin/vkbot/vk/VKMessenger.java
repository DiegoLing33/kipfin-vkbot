package me.ling.kipfin.vkbot.vk;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import me.ling.kipfin.core.log.WithLogger;
import me.ling.kipfin.core.utils.ExceptionUtils;
import me.ling.kipfin.core.utils.IntUtils;
import me.ling.kipfin.vkbot.entities.VKUser;
import me.ling.kipfin.vkbot.entities.message.BroadcastMessage;
import me.ling.kipfin.vkbot.entities.message.ImagedTextMessage;
import me.ling.kipfin.vkbot.entities.message.TextMessage;
import me.ling.kipfin.vkbot.entities.message.TextMessageButch;
import me.ling.kipfin.vkbot.utils.BTUtils;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

/**
 * Мессенджер
 * <p>
 * Класс для отправки сообщений
 *
 * @see TextMessage
 * @see ImagedTextMessage
 * @see BroadcastMessage
 * @see TextMessageButch
 */
public class VKMessenger extends WithLogger {

    private final VKApiApplication vkApiApplication;

    /**
     * Конструктор
     *
     * @param vkApiApplication - объект VK приложения
     */
    public VKMessenger(VKApiApplication vkApiApplication) {
        super("VK Messenger");
        this.vkApiApplication = vkApiApplication;
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
     * Создает запрос на отправку сообщения
     *
     * @return - запрос на отправку
     */
    public MessagesSendQuery createMessageSendQuery() {
        int random = IntUtils.getRandomInteger(99999, 10000);
        return this.getVkApiApplication().getVk().messages().send(this.getVkApiApplication().getActor()).randomId(random);
    }

    /**
     * Создает запрос на отправку сообщения и применяет пользователя
     *
     * @param user - пользователь
     * @return - пользователь
     */
    public MessagesSendQuery createMessageSendQuery(@NotNull VKUser user) {
        return createMessageSendQuery().userId(user.getUserId()).keyboard(user.getKeyboard());
    }

    /**
     * Создает запрос на отправку сообщения и применяет пользователя и сообщение
     *
     * @param user        - пользователь
     * @param textMessage - сообщение
     * @return - пользователь
     */
    public MessagesSendQuery createMessageSendQuery(@NotNull VKUser user, @NotNull TextMessage textMessage) {
        textMessage.applyUserFilter(user);
        var query = createMessageSendQuery().userId(user.getUserId()).keyboard(user.getKeyboard());
        if (textMessage.getKeyboard() != null) query.keyboard(textMessage.getKeyboard());
        query.message(textMessage.getText());
        return query;
    }

    public void sendTextMessage(@NotNull TextMessage message, @NotNull VKUser user) throws ClientException, ApiException {
        createMessageSendQuery(user, message).execute();
    }


    /**
     * Выполняет отправку сообщения
     *
     * @param message - сообщение
     * @param user    - пользователь
     * @throws ClientException - исключение клиента
     * @throws ApiException    - исключение API
     */
    public void sendMessage(TextMessage message, @NotNull VKUser user) throws ClientException, ApiException {
        if (message instanceof BroadcastMessage) {
            getVkApiApplication().getMessenger().sendBroadcastMessage((BroadcastMessage) message);
        } else if (message instanceof TextMessageButch) {
            getVkApiApplication().getMessenger().sendTextMessagesButch((TextMessageButch) message, user);
        } else if (message instanceof ImagedTextMessage) {
            getVkApiApplication().getMessenger().sendImagedTextMessage((ImagedTextMessage) message, user);
        } else {
            getVkApiApplication().getMessenger().sendTextMessage(message, user);
        }
    }

    /**
     * Отправляет сообщение с изображением
     *
     * @param message - сообщение
     * @param user    - пользователь
     * @throws ClientException - исключения в отправке изображения
     * @throws ApiException    - исключения API
     */
    public void sendImagedTextMessage(@NotNull ImagedTextMessage message, @NotNull VKUser user) throws ClientException, ApiException {
        var query = createMessageSendQuery(user, message);
        var actor = getVkApiApplication().getActor();
        var vk = getVkApiApplication().getVk();
        if (message.getImage() != null) {
            var uploader = vk.photos().getMessagesUploadServer(actor).peerId(user.getUserId()).execute();
            var response = vk.upload().photoMessage(uploader.getUploadUrl().toString(), message.getImage()).execute();
            var photos = vk.photos().saveMessagesPhoto(actor, response.getPhoto())
                    .server(response.getServer()).hash(response.getHash()).execute();
            query.attachment(photos.stream().map(photo -> String.format("photo%s_%s", photos.get(0).getOwnerId().toString(),
                    photos.get(0).getId().toString())).collect(Collectors.joining(",")));
        }
        query.execute();
    }

    /**
     * Отправляет общее сообщение
     *
     * @param broadcastMessage - общее сообщение
     * @throws ClientException - исключеняи клиента
     * @throws ApiException    - исключения API
     */
    public void sendBroadcastMessage(BroadcastMessage broadcastMessage) throws ClientException, ApiException {
        for (var id : broadcastMessage.getUsers()) {
            var user = VKUser.getInstance(this.getVkApiApplication(), id);
            this.sendImagedTextMessage(broadcastMessage, user);
        }
    }


    /**
     * Отправляет корзину сообщений
     *
     * @param butch - пакет сообщений
     * @throws ClientException - исключеняи клиента
     * @throws ApiException    - исключения API
     */
    public void sendTextMessagesButch(TextMessageButch butch, VKUser user) throws ClientException, ApiException {
        for (var message : butch.getButch()) {
            this.sendTextMessage(message, user);
        }
    }

    /**
     * Отправляет текстовое сообщение администратору
     *
     * @param message - сообщение
     */
    public void sendMessageToAdmin(TextMessage message) {
        ExceptionUtils.silent(() -> this.sendMessage(message, VKUser.getInstance(this.getVkApiApplication(), BTUtils.ADMIN_ID)));
    }

    /**
     * Отправляет текстовое сообщение администратору
     *
     * @param message - сообщение
     */
    public void sendMessageToAdmin(String message) {
        this.sendMessageToAdmin(new TextMessage(message));
    }
}
