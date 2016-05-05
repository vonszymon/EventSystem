package tai.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import tai.domain.Event;

public class EventExtractor implements ResultSetExtractor<Event> {

	public Event extractData(ResultSet resultSet) throws SQLException,
			DataAccessException {
		
		Event event = new Event();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		event.setEventID(resultSet.getInt(1));
		event.setTitle(resultSet.getString(2));
		try {
			event.setStartDate(df.parse(resultSet.getString(3)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		event.setContent(resultSet.getString(4));
		event.setHashtag(resultSet.getString(5));
		event.setNumOfComments(Integer.parseInt(resultSet.getString(6)));
		
		return event;
	}

}
