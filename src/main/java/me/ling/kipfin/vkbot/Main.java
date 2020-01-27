package me.ling.kipfin.vkbot;

import com.petersamokhin.bots.sdk.clients.Group;
import io.github.cdimascio.dotenv.Dotenv;
import me.ling.kipfin.core.Bootloader;
import me.ling.kipfin.vkbot.app.Application;
import me.ling.kipfin.vkbot.database.BotValuesDB;
import me.ling.kipfin.vkbot.entities.BotUser;
import me.ling.kipfin.vkbot.tweak.Message;
import org.apache.log4j.Level;

import java.sql.SQLException;
import java.util.Objects;

public class Main {

    /**
     * Точка входа
     *
     * @param args - аргументы
     */
    public static void main(String[] args) throws SQLException {
        Dotenv dotenv = Dotenv.load();
        Bootloader bootloader = new Bootloader(dotenv);
        bootloader.updateDatabase(false);
        BotValuesDB.shared.update();

        new Application(Integer.parseInt(Objects.requireNonNull(dotenv.get("vk.group_id"))),
                dotenv.get("vk.token")).start();
    }

}