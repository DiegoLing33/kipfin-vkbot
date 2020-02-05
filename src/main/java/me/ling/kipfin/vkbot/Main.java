package me.ling.kipfin.vkbot;

import io.github.cdimascio.dotenv.Dotenv;
import me.ling.kipfin.core.Bootloader;
import me.ling.kipfin.timetable.managers.TimetableManager;
import me.ling.kipfin.vkbot.app.Application;

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
        Dotenv dotenv = Dotenv.load();
        Bootloader bootloader = new Bootloader(dotenv);
        bootloader.updateDatabase(false);
        TimetableManager.TIMETABLE_WEB_PATH = dotenv.get("timetable_web_path");

//       TimetableManager.upload(TimetableMaster.create("/Users/Diego/kipfin_bot/c.xlsx", "/Users/Diego/kipfin_bot/w.xls"));

        new Application(Integer.parseInt(Objects.requireNonNull(dotenv.get("vk.group_id"))),
                dotenv.get("vk.token")).start();
    }

}