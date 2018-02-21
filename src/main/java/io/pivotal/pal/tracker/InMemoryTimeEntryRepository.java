package io.pivotal.pal.tracker;

import java.sql.Time;
import java.util.*;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private Map<Long,TimeEntry> entries = new HashMap<>();

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(entries.size() + 1);
        entries.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        return entries.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        List<TimeEntry> values = new ArrayList<>(entries.values());
        return values;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        timeEntry.setId(id);
        entries.put(id, timeEntry);
        return timeEntry;
    }

    @Override
    public void delete(long id) {
        entries.remove(id);
    }
}
