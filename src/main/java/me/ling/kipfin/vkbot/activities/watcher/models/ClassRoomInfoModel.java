package me.ling.kipfin.vkbot.activities.watcher.models;

import me.ling.kipfin.timetable.entities.Classroom;
import me.ling.kipfin.timetable.entities.TimetableMaster;
import me.ling.kipfin.vkbot.activities.watcher.components.ClassRoomInfoComponent;
import me.ling.kipfin.vkbot.app.MessageComponent;
import me.ling.kipfin.vkbot.app.MessageModel;

import java.util.ArrayList;
import java.util.List;

public class ClassRoomInfoModel extends MessageModel {

    private final String classroom;
    private final TimetableMaster master;

    public ClassRoomInfoModel(String classroom, TimetableMaster master) {
        this.classroom = classroom;
        this.master = master;
    }

    public String getClassroom() {
        return classroom;
    }


    public TimetableMaster getMaster() {
        return master;
    }

    @Override
    public MessageComponent getComponent() {
        List<Classroom> classrooms = new ArrayList<>();
        for (List<Classroom> cs : this.master.getClassrooms().values()) {
            for (Classroom c : cs)
                if (c.getWhere().equals(this.getClassroom()))
                    classrooms.add(c);
        }
        return new ClassRoomInfoComponent(this.getClassroom(), this.getMaster().getDate(), classrooms);
    }
}
