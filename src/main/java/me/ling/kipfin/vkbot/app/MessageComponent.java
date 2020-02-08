package me.ling.kipfin.vkbot.app;

import me.ling.kipfin.vkbot.entities.message.TextMessage;

/**
 * Компонент сообщения
 */
public abstract class MessageComponent {

    @Override
    public abstract String toString();

    /**
     * Преобразует компонент в текстовое сообщение
     * @return  - текстовое сообщение TextMessage
     */
    public TextMessage toTextMessage(){
        return new TextMessage(this.toString());
    }
}
