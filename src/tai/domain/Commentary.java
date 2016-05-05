package tai.domain;

import java.util.Date;

public class Commentary {
	
	private int commentaryID;
	private int eventID;
	private String author;
	private Date publishDate;
	private String content;
	
	
	
	public Commentary() {
		super();
	}
	public Commentary(String author, Date publishDate, String content) {
		super();
		this.author = author;
		this.publishDate = publishDate;
		this.content = content;
	}
	public int getCommentaryID() {
		return commentaryID;
	}
	public void setCommentaryID(int commentaryID) {
		this.commentaryID = commentaryID;
	}
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
