package me.ling.kipfin.vkbot.messagecomponents;

/**
 * Текстовой компонент
 */
public class TextComponent extends MessageComponent {

    /**
     * Типы сообщения
     */
    public enum TextType{
        DEFAULT,
        WARNING,
        ERROR
    }

    private final String text;
    private final TextType type;

    public TextComponent(String text, TextType type) {
        this.text = text;
        this.type = type;
    }

    public TextComponent(String text){
        this(text, TextType.DEFAULT);
    }

    /**
     * Возвращает текст
     * @return  - текст
     */
    public String getText() {
        return text;
    }

    /**
     * Возвращает тип сооьщения
     * @return  - тип сообщения
     */
    public TextType getType() {
        return type;
    }

    @Override
    public String toString() {
        switch (this.getType()){
            default:
            case DEFAULT:
                return this.getText();
            case WARNING:
                return String.format("❗%s❗️", this.getText());
            case ERROR:
                return String.format("❌ %s ❌", this.getText());
        }
    }


}
