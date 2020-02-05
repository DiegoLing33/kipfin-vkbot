package me.ling.kipfin.vkbot.actions.timetableweek;

import me.ling.kipfin.vkbot.app.MessageComponent;
import me.ling.kipfin.vkbot.messagecomponents.timetable.SubjectComponent;
import me.ling.kipfin.vkbot.messagecomponents.timetable.TimetableHeaderComponent;

import java.util.List;
import java.util.stream.Collectors;

public class TimetableWeekComponent extends MessageComponent {

    protected final TimetableHeaderComponent headerComponent;
    protected final List<SubjectComponent> subjectComponents;

    public TimetableWeekComponent(TimetableHeaderComponent headerComponent, List<SubjectComponent> subjectComponents) {
        this.headerComponent = headerComponent;
        this.subjectComponents = subjectComponents;
    }

    public List<SubjectComponent> getSubjectComponents() {
        return subjectComponents;
    }

    public TimetableHeaderComponent getHeaderComponent() {
        return headerComponent;
    }

    @Override
    public String toString() {
        String subjects = this.subjectComponents.stream()
                .map(SubjectComponent::toString).collect(Collectors.joining("\n"));
        return String.format("%s\n\n%s", this.headerComponent.toString(), subjects);
    }
}
