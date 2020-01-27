package me.ling.kipfin.vkbot.database;

import me.ling.kipfin.core.EntityDB;
import me.ling.kipfin.core.sql.SQLObjectMapper;
import me.ling.kipfin.exceptions.DatabaseEntityNotFoundException;
import me.ling.kipfin.vkbot.entities.BotValue;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Map;

public class BotValuesDB extends EntityDB<BotValue, DatabaseEntityNotFoundException> {

    /**
     * Общая база данных
     */
    public static BotValuesDB shared = new BotValuesDB();

    @Override
    public Map<Integer, BotValue> getAll() throws SQLException {
        return SQLObjectMapper.selectAllAndMap(BotValue.class, "bot_values", "id");
    }

    /**
     * Выполняет поиск значения бота
     * @param type  - тип значения (категория)
     * @param key   - ключ значения
     * @return      - объект BotValue
     * @throws DatabaseEntityNotFoundException  - исключение, если значение не найдено
     */
    @NotNull
    public BotValue getValue(String type, String key) throws DatabaseEntityNotFoundException {
        for (BotValue botValue : this.getCache().values())
            if (botValue.getType().equals(type) && botValue.getKey().equals(key))
                return botValue;
        throw new DatabaseEntityNotFoundException("Значение для бота " + type + "@" + key + " не найдено!");
    }
}
