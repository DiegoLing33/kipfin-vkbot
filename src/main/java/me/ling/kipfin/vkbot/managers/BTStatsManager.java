package me.ling.kipfin.vkbot.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.ling.kipfin.vkbot.entities.BTStatItem;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Менеджер статистики
 */
public class BTStatsManager {

    protected static ArrayList<BTStatItem> statItems = new ArrayList<>();
    public static String PATH = "./stats/";
    public static int MAX_LINES_PER_FILE = 50;

    /**
     * Добавляет запись в статистику
     *
     * @param id   - id
     * @param meta - meta
     */
    public static void add(int id, String meta) {
        statItems.add(new BTStatItem(LocalDate.now().toString(), LocalTime.now().toString(), id, meta));
        if (statItems.size() > MAX_LINES_PER_FILE) save();
    }

    /**
     * Волучает полную статистику
     *
     * @return - полная статистика
     */
    @NotNull
    public static ArrayList<BTStatItem> loadAll() {
        ArrayList<BTStatItem> state = new ArrayList<>();
        try {
            Files.walk(Path.of(PATH)).forEach(path -> {
                if (path.toFile().isDirectory()) return;
                try {
                    state.addAll(Arrays.asList(new ObjectMapper().readValue(path.toFile(), BTStatItem[].class)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return state;
    }

    /**
     * Слхраняет статистику
     */
    public static void save() {
        File source = new File(PATH);
        if (!source.exists() || !source.isDirectory()) source.mkdir();

        String date = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH:mm").format(LocalDateTime.now());
        try {
            new ObjectMapper().writeValue(new File(PATH + "/" + date + ".stats.json"), statItems);
            statItems.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
