package me.ling.kipfin.vkbot;

import me.ling.kipfin.timetable.TimetableBootloader;
import me.ling.kipfin.vkbot.database.BTAnswersDB;
import me.ling.kipfin.vkbot.database.BTValuesDB;
import me.ling.kipfin.vkbot.utils.BTUtils;

import java.sql.SQLException;

/**
 * Загрузчик бота
 */
public class BTBootloader extends TimetableBootloader {

    private final String token;
    private final Integer groupId;

    public BTBootloader() {
        super();
        this.token = this.getEnvString("vk.token");
        this.groupId = this.getEnvInteger("vk.group_id");
        BTUtils.ADMIN_ID = this.getEnvInteger("vk.admin_id");
    }

    /**
     * Возвращает токен
     * @return  - строка токена сообщества/группы в VK
     */
    public String getToken() {
        return token;
    }

    /**
     * Возвращает id группы
     * @return  - идентификатор группы в VK
     */
    public Integer getGroupId() {
        return groupId;
    }

    @Override
    public void updateDatabase(Boolean extended) throws SQLException {
        super.updateDatabase(extended);
        this.log("Загрузка дополнительных БД...");

        // Загрузка данных из БД
        BTValuesDB.shared.update();
        BTAnswersDB.shared.update();
    }
}
