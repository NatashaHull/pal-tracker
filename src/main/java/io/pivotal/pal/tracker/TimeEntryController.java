package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private final TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        return ResponseEntity.status(HttpStatus.CREATED).body(timeEntryRepository.create(timeEntry));
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {
        TimeEntry entry = timeEntryRepository.find(id);
        if (entry == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(entry);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        return ResponseEntity.ok().body(timeEntryRepository.list());
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry entry = timeEntryRepository.update(id, timeEntry);
        if (entry == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(entry);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        timeEntryRepository.delete(id);
        return ResponseEntity.noContent().build();
    }

}
