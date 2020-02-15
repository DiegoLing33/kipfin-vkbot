package me.ling.kipfin.vkbot.entities.message

import com.vk.api.sdk.objects.messages.Keyboard
import java.io.File

class BroadcastMessage(text: String, keyboard: Keyboard? = null, file: File? = null, val users: List<Int>) : ImageMessage(text, keyboard, file)