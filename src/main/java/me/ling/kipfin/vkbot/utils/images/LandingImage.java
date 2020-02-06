package me.ling.kipfin.vkbot.utils.images;

import me.ling.kipfin.vkbot.utils.BTImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LandingImage extends BTImage {
    private final String date;

    public LandingImage(String date) {
        super("created-" + date);
        this.date = date;
    }

    /**
     * Отрисовывает изображение
     *
     * @return - изображение
     */
    @Override
    protected BufferedImage draw() {
        try {
            BufferedImage image = ImageIO.read(LandingImage.class.getResourceAsStream("/next_day.jpg"));
            Graphics2D graphics2D = image.createGraphics();
            Font font = new Font("PT Sans", Font.BOLD, 52);
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(font);
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
            drawCenteredString(graphics2D, date, new Rectangle(0, 300, image.getWidth(), 53), font);
            graphics2D.dispose();
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
