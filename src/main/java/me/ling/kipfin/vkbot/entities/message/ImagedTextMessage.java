package me.ling.kipfin.vkbot.entities.message;

import com.vk.api.sdk.objects.messages.Keyboard;
import org.jetbrains.annotations.Nullable;

public class ImagedTextMessage extends TextMessage {

    @Nullable
    private final String image;

    public ImagedTextMessage(String text, @Nullable Keyboard keyboard, @Nullable String image) {
        super(text, keyboard);
        this.image = image;
    }

    public ImagedTextMessage(String text, @Nullable String image) {
        super(text, null);
        this.image = image;
    }

    public ImagedTextMessage(String text) {
        super(text, null);
        this.image = null;
    }

    @Nullable
    public String getImage() {
        return image;
    }
}
