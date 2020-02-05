package me.ling.kipfin.vkbot.messagecomponents.timetable;

import me.ling.kipfin.timetable.entities.timeinfo.TimeInfoItem;
import me.ling.kipfin.vkbot.messagecomponents.MessageComponent;


public class WithTimeComponent<T extends MessageComponent> extends MessageComponent {

    private final T rootComponent;
    private final boolean displayEmoji;
    private final TimeInfoItem timeInfoItem;

    /**
     * Конструктор
     *
     * @param rootComponent - компонент
     * @param displayEmoji  - отображение эмодзи
     */
    public WithTimeComponent(T rootComponent, TimeInfoItem item, boolean displayEmoji) {
        this.rootComponent = rootComponent;
        this.timeInfoItem = item;
        this.displayEmoji = displayEmoji;
    }

    /**
     * Конструктор
     *
     * @param rootComponent - компонент
     * @param item          - элемент
     */
    public WithTimeComponent(T rootComponent, TimeInfoItem item) {
        this(rootComponent, item, true);
    }

    /**
     * Возвращает компонент
     *
     * @return - корневой компонент
     */
    public T getRootComponent() {
        return rootComponent;
    }

    /**
     * Возвращает объект информации о времени
     *
     * @return - информация о времени
     */
    public TimeInfoItem getTimeInfoItem() {
        return timeInfoItem;
    }

    /**
     * Преобразует компонент в строку
     * @return строк
     */
    @Override
    public String toString() {
        return this.displayEmoji ?
                String.format("⏰ %s ⏰\n%s", this.getTimeInfoItem().toString(), this.getRootComponent().toString()) :
                String.format("%s\n%s", this.getTimeInfoItem().toString(), this.getRootComponent().toString());
    }
}
