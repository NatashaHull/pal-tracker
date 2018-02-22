package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcTimeEntryRepository implements TimeEntryRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry find(long id) {
        try {
            Map<String, Object> timeEntryData = this.jdbcTemplate.queryForMap(
                    "Select * from time_entries where id = ?",
                    id
            );
            return new TimeEntry(timeEntryData);
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        String sql = "INSERT INTO time_entries (project_id, user_id, date, hours)" +
                "VALUES (?, ?, ?, ?)";
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
                    PreparedStatement statement = connection.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    );
                    statement.setLong(1, timeEntry.getProjectId());
                    statement.setLong(2, timeEntry.getUserId());
                    statement.setDate(3, Date.valueOf(timeEntry.getDate()));
                    statement.setInt(4, timeEntry.getHours());

                    return statement;
                },
                generatedKeyHolder

        );
        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public List<TimeEntry> list() {
        List<Map<String, Object>> timeEntryData = this.jdbcTemplate.queryForList(
                "Select * from time_entries"
        );

        ArrayList<TimeEntry> fetchedEntries = new ArrayList<>();
        timeEntryData.forEach((value) -> {
            fetchedEntries.add(new TimeEntry(value));
        });

        return fetchedEntries;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        jdbcTemplate.update(
                "UPDATE time_entries " +
                        "SET project_id = ?," +
                        "user_id = ?," +
                        "date = ?," +
                        "hours = ?" +
                        " WHERE id = ?",
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours(),
                id
        );
        return find(id);
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM time_entries WHERE id = ?", id);
    }
}
