package tai.domain;

import java.util.Date;
import java.util.List;

public class Event {

	private int eventID;
	private String title;
	private Date startDate;
	private String content;
	private String hashtag;
	private int numOfComments;
	private List<Tweet> tweets;
	private boolean active;
	
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getHashtag() {
		return hashtag;
	}
	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
	public List<Tweet> getTweets() {
		return tweets;
	}
	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getNumOfComments() {
		return numOfComments;
	}
	public void setNumOfComments(int numOfComments) {
		this.numOfComments = numOfComments;
	}
	
	
}
