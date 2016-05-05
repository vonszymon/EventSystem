package tai.dao;

import java.util.List;

import tai.domain.Event;

public interface EventDao {
	public void insertData(Event event);

	public List<Event> getEventList();

	public void updateData(Event event, String id);

	public void deleteData(String id);

	public Event getEvent(String id);

}
