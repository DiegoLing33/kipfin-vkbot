package me.ling.kipfin.vkbot.entities.message;

import com.vk.api.sdk.objects.messages.Keyboard;
import me.ling.kipfin.vkbot.entities.VKUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TextMessageButch extends TextMessage {

    private final List<TextMessage> butch;

    public TextMessageButch(List<TextMessage> textMessages, @Nullable Keyboard keyboard) {
        super("", keyboard);
        this.butch = textMessages;
    }

    public TextMessageButch(List<TextMessage> textMessages) {
        super("", null);
        this.butch = textMessages;
    }

    public List<TextMessage> getButch() {
        return butch;
    }

    @Override
    public TextMessage applyTagValue(String tag, @NotNull Object value) {
        this.butch.forEach(v -> v.applyTagValue(tag, value));
        return this;
    }

    @Override
    public TextMessage applyUserFilter(VKUser user) {
        this.butch.forEach(v -> v.applyUserFilter(user));
        return this;
    }
}
