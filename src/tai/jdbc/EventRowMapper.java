package tai.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tai.domain.Event;

public class EventRowMapper implements RowMapper<Event> {

	@Override
	public Event mapRow(ResultSet resultSet, int line) throws SQLException {
		EventExtractor eventExtractor = new EventExtractor();
		return eventExtractor.extractData(resultSet);
	}

}
