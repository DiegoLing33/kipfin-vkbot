package me.ling.kipfin.vkbot.messagecomponents.timetable;

import com.google.common.base.Joiner;
import me.ling.kipfin.vkbot.messagecomponents.MessageComponent;

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
