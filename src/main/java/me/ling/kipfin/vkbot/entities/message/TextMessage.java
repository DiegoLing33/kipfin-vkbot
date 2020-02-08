package me.ling.kipfin.vkbot.entities.message;

import com.vk.api.sdk.objects.messages.Keyboard;
import me.ling.kipfin.vkbot.entities.VKUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Текстовое сообщение
 */
public class TextMessage {

    private String text;
    @Nullable
    private Keyboard keyboard;

    /**
     * Конструктор
     *
     * @param text     - текст сообщения
     * @param keyboard - клавиатура
     */
    public TextMessage(String text, @Nullable Keyboard keyboard) {
        this.text = text;
        this.keyboard = keyboard;
    }

    /**
     * Конструктор
     *
     * @param text - текст сообщения
     */
    public TextMessage(String text) {
        this.text = text;
        this.keyboard = null;
    }

    /**
     * Возвращает клавиатуру или Null
     *
     * @return - клавиатура Keyboard
     */
    @Nullable
    public Keyboard getKeyboard() {
        return keyboard;
    }

    /**
     * Устанавливает клавиатуру
     * @param keyboard - клавиатура
     */
    public TextMessage setKeyboard(@Nullable Keyboard keyboard) {
        this.keyboard = keyboard;
        return this;
    }

    /**
     * Возвращает текст сообщения
     *
     * @return - текст сообщения
     */
    public String getText() {
        return text;
    }

    /**
     * Применяет значение тега.
     *
     * Заменяет вхождения "{tag}" на value.
     *
     * @param tag   - тэг
     * @param value - значение
     */
    public TextMessage applyTagValue(String tag, @NotNull Object value) {
        this.text = text.replaceAll("\\{" + tag + "}", value.toString());
        return this;
    }

    /**
     * Применяет обработку пользователя
     *
     * @param user - пользователь
     */
    public TextMessage applyUserFilter(VKUser user) {
        this.text = VKUser.template(this.text, user);
        return this;
    }
}
