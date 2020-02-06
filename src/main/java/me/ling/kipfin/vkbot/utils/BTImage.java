package me.ling.kipfin.vkbot.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Изобржение
 */
public abstract class BTImage {

    /**
     * Путь до изображений
     */
    public static String PATH = "./images";

    /**
     * Возвращает путь до изображения
     *
     * @param image  - имя файла
     * @param format - формат
     * @return - полный путь с форматом
     */
    @NotNull
    @Contract("_, _ -> new")
    public static File getImagePath(String image, String format) {
        return new File(PATH + "/" + image + "." + format);
    }

    /**
     * Возвращает путь до изображения
     *
     * @param image - имя файла
     * @return - полный путь с форматом
     */
    @NotNull
    public static File getImagePath(String image) {
        return getImagePath(image, "jpg");
    }

    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g    The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     */
    public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

    private final String name;
    private final String format;
    public boolean NO_CACHE = false;

    /**
     * Конструктор
     *
     * @param name   - имя
     * @param format - формат
     */
    public BTImage(String name, String format) {
        this.name = name;
        this.format = format;
    }

    /**
     * Конструктор
     *
     * @param name - имя
     */
    public BTImage(String name) {
        this(name, "jpg");
    }

    /**
     * Возвращает имя
     *
     * @return - имя
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает формат
     *
     * @return - формат
     */
    public String getFormat() {
        return format;
    }

    /**
     * Отрисовывает изображение
     *
     * @return - выполняет рисование
     */
    protected abstract BufferedImage draw();

    /**
     * Возвращает путь до изображения
     *
     * @return - путь до изображения
     */
    public File getPath() {
        return BTImage.getImagePath(this.getName(), this.getFormat());
    }

    /**
     * Выполняет сохранение картинки
     */
    public void save() {
        try {
            File root = new File(PATH);
            if(!root.exists() || !root.isDirectory()) root.mkdir();
            ImageIO.write(this.draw(), this.format, this.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает изображение
     *
     * @return - изображение
     */
    public File getImage() {
        File source = getImagePath(this.getName(), this.getFormat());
        if (!source.exists() || NO_CACHE) this.save();
        return source;
    }
}
