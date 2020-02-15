package me.ling.kipfin.vkbot.entities.message

import com.vk.api.sdk.objects.messages.Keyboard
import java.io.File

/**
 * Сообзение с картинкой
 */
open class ImageMessage(text: String, keyboard: Keyboard? = null, var file: File? = null) : CoreMessage(text, keyboard)