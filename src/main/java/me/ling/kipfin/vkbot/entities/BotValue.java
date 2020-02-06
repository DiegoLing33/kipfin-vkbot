package me.ling.kipfin.vkbot.entities;

import me.ling.kipfin.core.sql.Column;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Переменные бота
 */
public class BotValue {

    @Column(name = "id", type = Integer.class)
    protected Integer botValueId;

    @Column(name = "type")
    protected String type;

    @Column(name = "kw")
    protected String key;

    @Column(name = "value")
    protected String value;

    @Column(name = "updated")
    protected String updated;

    public BotValue() {
    }

    public BotValue(Integer botValueId, String type, String key, String value) {
        this.botValueId = botValueId;
        this.type = type;
        this.key = key;
        this.value = value;
    }

    /**
     * Возвращает id значения
     *
     * @return - id значения
     */
    public Integer getBotValueId() {
        return botValueId;
    }

    /**
     * Возвращает тип значения
     *
     * @return - тип значения
     */
    public String getType() {
        return type;
    }

    /**
     * Возвращает ключ значения
     *
     * @return - ключ
     */
    public String getKey() {
        return key;
    }

    /**
     * Возвращает значение
     *
     * @return - значение
     */
    public String getValue() {
        return value;
    }

    /**
     * Возвращает строку
     *
     * @return -  строка последнего обновления
     */
    public String getUpdatedString() {
        return this.updated;
    }

    /**
     * Возвращает дату обновления
     *
     * @return - дата обновления
     */
    public LocalDateTime getUpdatedDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String[] updated = this.getUpdatedString().split(" ");

        return LocalDateTime.of(
                LocalDate.parse(updated[0], dateFormatter),
                LocalTime.parse(updated[1].substring(0, 8), timeFormatter));
    }
}
