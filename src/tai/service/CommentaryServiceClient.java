package tai.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import tai.domain.Commentary;
import tai.domain.Event;

public class CommentaryServiceClient {
	
	@Value("${tai.services.comment.uri}")
	private String REST_SERVICE_URI;
	
	public Commentary getCommentary(String commentId){
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(REST_SERVICE_URI+"/"+commentId, Commentary.class);
	}
	
	public List<Commentary> getCommentaries(String eventId){
		RestTemplate restTemplate = new RestTemplate();
		Commentary[] commentaries = restTemplate.getForObject(REST_SERVICE_URI+"/commentlist/"+eventId, Commentary[].class);
		return new ArrayList<Commentary>(Arrays.asList(commentaries));
	}
	
	public void saveComment(Commentary comment){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForLocation(REST_SERVICE_URI+"/save", comment, Commentary.class);
	}
	
	public void updateComment(String commentId, Commentary comment){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(REST_SERVICE_URI+"/update/"+commentId, comment);
	}
	
	public void deleteComment(String commentId){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI+"/delete/"+commentId);
	}

}
