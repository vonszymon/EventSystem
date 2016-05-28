package tai.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import tai.domain.Event;

public class EventServiceClient {

	@Value("${tai.services.event.uri}")
	private String REST_SERVICE_URI;
	
	public Event getEvent(String eventId){
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(REST_SERVICE_URI+"/"+eventId, Event.class);
	}
	
	public List<Event> getEvents(){
		RestTemplate restTemplate = new RestTemplate();
		Event[] events = restTemplate.getForObject(REST_SERVICE_URI+"/eventlist", Event[].class);
		return new ArrayList<Event>(Arrays.asList(events));
	}
	
	public void saveEvent(Event event){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForLocation(REST_SERVICE_URI+"/save", event, Event.class);
	}
	
	public void updateEvent(String eventId, Event event){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(REST_SERVICE_URI+"/update/"+eventId, event);
	}
	
}
