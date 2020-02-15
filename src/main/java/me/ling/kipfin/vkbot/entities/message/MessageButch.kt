package me.ling.kipfin.vkbot.entities.message

import me.ling.kipfin.vkbot.entities.VKUser

class MessageButch(val butch: List<CoreMessage>) : CoreMessage("", null) {

    /**
     * Применяет фильтр по тегу
     */
    override fun applyTagValue(tag: String, value: Any): CoreMessage {
        this.butch.forEach { it.applyTagValue(tag, value) }
        return this
    }

    /**
     * Применяет фильтр пользователя
     */
    override fun applyUserFilter(user: VKUser): CoreMessage {
        this.butch.forEach { it.applyUserFilter(user) }
        return this
    }
}