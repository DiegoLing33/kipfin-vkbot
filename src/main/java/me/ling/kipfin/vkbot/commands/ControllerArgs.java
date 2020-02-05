package me.ling.kipfin.vkbot.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * Аргументы контроллера
 */
public class ControllerArgs extends ArrayList<String> {

    protected final String mainArg;

    public ControllerArgs(String mainArg, int initialCapacity) {
        super(initialCapacity);
        this.mainArg = mainArg;
    }

    public ControllerArgs() {
        this.mainArg = "";
    }

    public ControllerArgs(String mainArg, @NotNull Collection<? extends String> c) {
        super(c);
        this.mainArg = mainArg;
    }

    /**
     * Возвращает значение или null
     *
     * @param index - индекс аргумента
     * @return - аргумент
     */
    public String getOrNull(int index) {
        try {
            return this.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Тестирует аргументы на алиасы
     *
     * @param index - индекс аргумента. null - выполняет тестирование mainArg.
     * @param alias - алиасы
     * @return - аргументы
     */
    public boolean test(@Nullable Integer index, String... alias) {
        var text = index == null ? this.getMainArg() : this.getOrNull(index);
        return text != null && Arrays.stream(alias).anyMatch(s -> s.toLowerCase().equals(text.toLowerCase()));
    }

    /**
     * Тестирует mainArg на алиасы
     *
     * @param alias - алиасы
     * @return - результат тестирования
     */
    public boolean test(String... alias) {
        return this.test(null, alias);
    }

    /**
     * Тестирует аргументы на предикат
     *
     * @param index     - индекс аргумента. null - выполняет тестирование mainArg.
     * @param predicate - предикат
     * @return - результат тестирования
     */
    public boolean test(@Nullable Integer index, Predicate<String> predicate) {
        var text = index == null ? this.getMainArg() : this.getOrNull(index);
        return text != null && predicate.test(text);
    }

    /**
     * Тестирует mainArg на предикат
     *
     * @param predicate - предикат
     * @return - результат тестирования
     */
    public boolean test(Predicate<String> predicate) {
        return this.test(null, predicate);
    }

    /**
     * Возвращает главный аргумент
     *
     * @return - главный аргумент
     */
    public String getMainArg() {
        return mainArg;
    }
}
