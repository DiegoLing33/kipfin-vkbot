package me.ling.kipfin.vkbot.analyzers;

import me.ling.kipfin.core.utils.ListUtils;
import me.ling.kipfin.vkbot.entities.BTStatItem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Анализатор статистики
 */
public class BTStatsAnalyzer {

    /**
     * Возвращает уникальные записи
     * @param list  - список
     * @return      - уникальный список
     */
    public static List<BTStatItem> unique(List<BTStatItem> list) {
        List<BTStatItem> items = new ArrayList<>();
        list.forEach(btStatItem -> {
            if (!ListUtils.contains(items, BTStatItem::getId, btStatItem.getId()))
                items.add(btStatItem);
        });
        return items;
    }

    private final List<BTStatItem> items;

    public BTStatsAnalyzer(List<BTStatItem> items) {
        this.items = items;
    }

    public List<BTStatItem> getItems() {
        return items;
    }

    /**
     * Возвращает статистику на день
     *
     * @param date - день
     * @return - статистика
     */
    public List<BTStatItem> getOnDate(LocalDate date) {
        LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);
        return this.getOnRange(start, end);
    }

    /**
     * Возвращает статистику на день
     *
     * @param date - день
     * @return - статистика
     */
    public List<BTStatItem> getOnMonth(LocalDate date) {
        LocalDateTime start = LocalDateTime.of(date.withDayOfMonth(1), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date.plusMonths(1).withDayOfMonth(1).minusDays(1), LocalTime.MAX);
        return this.getOnRange(start, end);
    }

    /**
     * Возвращает статистику на день
     *
     * @param start - начало промежутка
     * @param end   - конец промежутка
     * @return - статистика
     */
    public List<BTStatItem> getOnRange(LocalDateTime start, LocalDateTime end) {
        return this.getItems().stream().filter(btStatItem ->
                btStatItem.getLocalDateTime().isAfter(start) && btStatItem.getLocalDateTime()
                        .isBefore(end)).collect(Collectors.toList());
    }
}
