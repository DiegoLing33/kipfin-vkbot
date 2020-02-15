package me.ling.kipfin.vkbot.utils.vk

import com.vk.api.sdk.exceptions.ApiException
import com.vk.api.sdk.exceptions.ClientException
import com.vk.api.sdk.queries.messages.MessagesSendQuery
import me.ling.kipfin.vkbot.entities.VKUser
import me.ling.kipfin.vkbot.entities.message.BroadcastMessage
import me.ling.kipfin.vkbot.entities.message.CoreMessage
import me.ling.kipfin.vkbot.entities.message.ImageMessage
import me.ling.kipfin.vkbot.entities.message.MessageButch
import me.ling.kipfin.vkbot.utils.BTUtils
import java.util.stream.Collectors
import kotlin.random.Random

/**
 * Отправка сообщений в VK
 */
class VKMessenger(val api: VKApiApplication) {

    /**
     * Создает запрос на отправку сообщения
     */
    private fun createMessageSendQuery(): MessagesSendQuery {
        return this.api.vk.messages().send(this.api.actor).randomId(Random.nextInt(10000, 99999))
    }

    /**
     * Создает запрос на отправку сообщения и подключает к нему информацию о пользователе
     */
    private fun createMessageSendQuery(user: VKUser): MessagesSendQuery {
        return this.createMessageSendQuery().userId(user.userId).keyboard(user.keyboard)
    }

    /**
     * Создает запрос на отправку сообщения и подключает к нему данные сообщения и пользователя
     */
    private fun createMessageSendQuery(message: CoreMessage, user: VKUser): MessagesSendQuery {
        message.applyUserFilter(user)
        val query = this.createMessageSendQuery(user)
        if (message.keyboard != null) query.keyboard(message.keyboard)
        query.message(message.text)
        return query
    }

    /**
     * Отправляет сообщение с изображением
     */
    @Throws(ApiException::class, ClientException::class)
    fun sendImage(message: ImageMessage, user: VKUser) {
        val query = createMessageSendQuery(message, user)
        if (message.file != null) {
            val uploader = api.vk.photos().getMessagesUploadServer(api.actor).peerId(user.userId).execute()
            val response = api.vk.upload().photoMessage(uploader.uploadUrl.toString(), message.file).execute()
            val photos = api.vk.photos().saveMessagesPhoto(api.actor, response.photo).server(response.server).hash(response.hash).execute()

            query.attachment(photos.stream().map { String.format("photo%s_%s", photos[0].ownerId.toString(), photos[0].id.toString()) }
                    .collect(Collectors.joining(",")))
        }
        query.execute()
    }

    /**
     * Выполняет broadcast рассылку
     */
    @Throws(ApiException::class, ClientException::class)
    fun sendBroadcast(message: BroadcastMessage){
        message.users.forEach {
            val user = VKUser.getInstance(api, it)
            sendImage(message, user)
        }
    }

    /**
     * Отправляет пакет сообщений
     */
    @Throws(ApiException::class, ClientException::class)
    fun sendButch(butch: MessageButch, user: VKUser){
        butch.butch.forEach { this.send(it, user) }
    }

    /**
     * Отправляет сообщение администратору
     */
    fun sendAdmin(message: CoreMessage){
        this.send(message, VKUser.getInstance(api, BTUtils.ADMIN_ID))
    }

    /**
     * Отправляет обычное сообщение
     */
    @Throws(ApiException::class, ClientException::class)
    fun sendText(message: CoreMessage, user: VKUser) {
        this.createMessageSendQuery(message, user).execute()
    }

    /**
     * Отправляет сообщение
     */
    @Throws(ApiException::class, ClientException::class)
    fun send(message: CoreMessage, user: VKUser){
        when(message){
            is MessageButch -> this.sendButch(message, user)
            is BroadcastMessage -> this.sendBroadcast(message)
            is ImageMessage -> this.sendImage(message, user)
            else -> this.sendText(message, user)
        }
    }
}