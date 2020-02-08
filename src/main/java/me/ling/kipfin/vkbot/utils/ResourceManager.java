package me.ling.kipfin.vkbot.utils;

import me.ling.kipfin.vkbot.Main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Менеджер ресурсов
 */
public class ResourceManager {

    public static File PATH = new File("./common");


    public static File get(String name) {
        return new File(PATH + "/" + name);
    }

    /**
     * Распаковывает реусурс
     *
     * @param name - имя ресурса
     */
    public static void unpack(String name) throws IOException {
        if (!PATH.exists()) PATH.mkdir();
        var is = Main.class.getResourceAsStream("/" + name);
        copyInputStreamToFile(is, new File(PATH.toString() + "/" + name));
    }

    /**
     * Копирует поток в файл
     *
     * @param inputStream - поток
     * @param file        - файл
     * @throws IOException - исключение
     */
    public static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
    }

}
