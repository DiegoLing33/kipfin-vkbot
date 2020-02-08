package me.ling.kipfin.vkbot.entities.message;

import com.vk.api.sdk.objects.messages.Keyboard;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class ImagedTextMessage extends TextMessage {

    @Nullable
    private final File image;

    public ImagedTextMessage(String text, @Nullable Keyboard keyboard, @Nullable File image) {
        super(text, keyboard);
        this.image = image;
    }

    public ImagedTextMessage(String text, @Nullable File image) {
        super(text, null);
        this.image = image;
    }

    public ImagedTextMessage(String text) {
        super(text, null);
        this.image = null;
    }

    @Nullable
    public File getImage() {
        return image;
    }
}
