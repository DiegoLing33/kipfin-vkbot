package me.ling.kipfin.vkbot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class BTStatItem {

    @JsonProperty("date")
    private String date;

    @JsonProperty("time")
    private String time;

    @JsonProperty("id")
    private int id;

    @JsonProperty("meta")
    private String meta;

    public BTStatItem() {
    }

    public BTStatItem(String date, String time, int id, String meta) {
        this.date = date;
        this.time = time;
        this.id = id;
        this.meta = meta;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public String getMeta() {
        return meta;
    }

    @JsonIgnore
    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.of(
                LocalDate.parse(this.getDate()),
                LocalTime.parse(this.getTime())
        );
    }
}
