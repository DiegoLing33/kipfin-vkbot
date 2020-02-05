package me.ling.kipfin.vkbot.messagecomponents.timetable;

import me.ling.kipfin.vkbot.messagecomponents.MessageComponent;
import me.ling.kipfin.vkbot.messagecomponents.TextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * День расписания
 */
public class TimetableDayComponent<T extends MessageComponent> extends MessageComponent {

    @NotNull
    private final TimetableHeaderComponent timetableHeaderComponent;

    @NotNull
    private final List<T> messageComponents;

    @Nullable
    private final TextComponent textComponent;


    /**
     * Конструктор
     *
     * @param timetableHeaderComponent - копонент заголовка
     * @param messageComponents        - компонент дисциплины
     * @param textComponent            - компонент текста (необязательно)
     */
    public TimetableDayComponent(@NotNull TimetableHeaderComponent timetableHeaderComponent,
                                 @NotNull List<T> messageComponents,
                                 @Nullable TextComponent textComponent) {
        this.timetableHeaderComponent = timetableHeaderComponent;
        this.messageComponents = messageComponents;
        this.textComponent = textComponent;
    }

    /**
     * Возвращает компонент заголовка
     *
     * @return -    заголовок
     */
    @NotNull
    public TimetableHeaderComponent getTimetableHeaderComponent() {
        return timetableHeaderComponent;
    }

    /**
     * Возвращает компонент дисциплин
     *
     * @return - дисциплины
     */
    @NotNull
    public List<T> getMessageComponents() {
        return messageComponents;
    }

    /**
     * Возвращает екомпонент текста
     *
     * @return - компонент с текстом
     */
    @Nullable
    public TextComponent getTextComponent() {
        return textComponent;
    }

    /**
     * Преобразует компонент в строку
     *
     * @return - строка
     */
    @Override
    public String toString() {
        String messageComponents = this.getMessageComponents().stream().map(MessageComponent::toString).collect(Collectors.joining("\n\n"));
        if (this.getTextComponent() == null)
            return String.format("%s\n\n%s", this.getTimetableHeaderComponent(), messageComponents);
        else
            return String.format("%s\n\n%s\n\n%s", this.getTimetableHeaderComponent(), this.getTextComponent(), messageComponents);
    }
}
