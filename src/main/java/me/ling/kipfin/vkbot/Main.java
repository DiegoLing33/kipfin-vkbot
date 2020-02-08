package me.ling.kipfin.vkbot;

import io.github.cdimascio.dotenv.Dotenv;
import me.ling.kipfin.core.Bootloader;
import me.ling.kipfin.timetable.managers.TimetableManager;
import me.ling.kipfin.vkbot.app.Application;

import java.sql.SQLException;
import java.util.Objects;

public class Main {

    /**
     * Точка входа
     *
     * @param args - аргументы
     */
    public static void main(String[] args) throws SQLException {

        // Загрузка стартовых данных. Библиотека kipfin.core
        Dotenv dotenv = Dotenv.load();
        Bootloader bootloader = new Bootloader(dotenv);
        bootloader.updateDatabase(false);
        TimetableManager.TIMETABLE_WEB_PATH = dotenv.get("timetable_web_path"); //fixme -- add bootloader modules

        // Получение актуальных данных из .env
        Integer groupId = Integer.parseInt(Objects.requireNonNull(dotenv.get("vk.group_id")));
        String token = dotenv.get("vk.token");

        // Созданеи и запуск приложения бота
        Application application = new Application(groupId, token);
        application.start();
    }

}