package me.ling.kipfin.vkbot.app

import me.ling.kipfin.vkbot.entities.message.CoreMessage

/**
 * Компонент сообщения
 */
abstract class MessageComponent {

    /**
     * Компонент
     */
    abstract override fun toString(): String

    /**
     * Преобразует компонент в сообщение
     */
    fun toMessage(): CoreMessage = CoreMessage(this.toString())
}