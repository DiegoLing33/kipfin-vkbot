package me.ling.kipfin.vkbot;

import io.github.cdimascio.dotenv.Dotenv;
import me.ling.kipfin.core.Bootloader;
import me.ling.kipfin.timetable.TimetableBootloader;
import me.ling.kipfin.timetable.entities.TimetableMaster;
import me.ling.kipfin.timetable.managers.TimetableManager;
import me.ling.kipfin.vkbot.app.Application;
import me.ling.kipfin.vkbot.utils.ResourceManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class Main {

    /**
     * Точка входа
     *
     * @param args - аргументы
     */
    public static void main(String[] args) throws SQLException, IOException {

        // Загрузка стартовых данных. Библиотека kipfin.core
        Bootloader bootloader = new TimetableBootloader();
        bootloader.updateDatabase(false);

        // Получение актуальных данных из .env
        Integer groupId = bootloader.getEnvInteger("vk.group_id");
        String token = bootloader.getEnvString("vk.token");

        // Распаковка ресурсов
        ResourceManager.unpack("moft.jpg");
        ResourceManager.unpack("next_day.jpg");
        ResourceManager.unpack("welcome.jpg");

//        var master = TimetableMaster.create("/Users/Diego/kipfin_bot/c.xlsx", "/Users/Diego/kipfin_bot/w.xls");
//        TimetableManager.upload(master);

        // Созданеи и запуск приложения бота
//        Application application = new Application(groupId, token);
//        application.start();
    }

}