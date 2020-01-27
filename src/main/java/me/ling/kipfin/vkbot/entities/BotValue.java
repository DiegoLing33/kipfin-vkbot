package me.ling.kipfin.vkbot.entities;

import me.ling.kipfin.core.sql.Column;

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
     * @return - id значения
     */
    public Integer getBotValueId() {
        return botValueId;
    }

    /**
     * Возвращает тип значения
     * @return  - тип значения
     */
    public String getType() {
        return type;
    }

    /**
     * Возвращает ключ значения
     * @return  - ключ
     */
    public String getKey() {
        return key;
    }

    /**
     * Возвращает значение
     * @return - значение
     */
    public String getValue() {
        return value;
    }
}
