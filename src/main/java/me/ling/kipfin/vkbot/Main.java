package me.ling.kipfin.vkbot;

import me.ling.kipfin.core.io.ResourcesManager;
import me.ling.kipfin.vkbot.app.Application;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    /**
     * Точка входа
     *
     * @param args - аргументы
     */
    public static void main(String[] args) throws SQLException, IOException {

        // Загрузка стартовых данных. Библиотека kipfin.core
        BTBootloader bootloader = new BTBootloader();
        bootloader.updateDatabase(false);

        // Распаковка ресурсов
        ResourcesManager.unpack("moft.jpg");
        ResourcesManager.unpack("next_day.jpg");
        ResourcesManager.unpack("welcome.jpg");

        // Созданеи и запуск приложения бота
        Application application = new Application(bootloader.getGroupId(), bootloader.getToken());
        application.start();
    }

}