package tai.domain;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {
    @Override
    public int compare(Event e1, Event e2) {
        return e1.getStartDate().compareTo(e2.getStartDate());
    }
}
