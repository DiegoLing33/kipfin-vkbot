package me.ling.kipfin.vkbot;

import me.ling.kipfin.vkbot.app.Application;
import me.ling.kipfin.vkbot.utils.ResourceManager;

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
        ResourceManager.unpack("moft.jpg");
        ResourceManager.unpack("next_day.jpg");
        ResourceManager.unpack("welcome.jpg");

        // Созданеи и запуск приложения бота
        Application application = new Application(bootloader.getGroupId(), bootloader.getToken());
        application.start();
    }

}