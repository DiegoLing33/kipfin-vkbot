package me.ling.kipfin.vkbot.entities.message

import com.vk.api.sdk.objects.messages.Keyboard
import me.ling.kipfin.vkbot.entities.VKUser

open class CoreMessage(var text: String, var keyboard: Keyboard? = null) {
    /**
     * Применяет фильтр пользователя
     */
    open fun applyUserFilter(user: VKUser): CoreMessage {
        this.text = VKUser.template(this.text, user)
        return this
    }

    /**
     * Применяет фильтр по тегу
     */
    open fun applyTagValue(tag: String, value: Any): CoreMessage {
        this.text = this.text.replace("{$tag}", value.toString())
        return this
    }
}

fun String.toMessage(): CoreMessage{
    return CoreMessage(this)
}