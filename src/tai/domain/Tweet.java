package tai.domain;

import java.util.Date;

public class Tweet {
	private String content;
	private String author;
	private Date date;
	
	public Tweet(String content, String author, Date date) {
		super();
		this.content = content;
		this.author = author;
		this.date = date;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
}
