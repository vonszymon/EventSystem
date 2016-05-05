package tai.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tai.domain.Event;
import tai.domain.Tweet;
import tai.jdbc.EventRowMapper;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class EventDaoImpl implements EventDao {

	@Autowired
	DataSource dataSource;

	public void insertData(Event event) {

		String sql = "INSERT INTO Event "
				+ "(title, start_date, content, hashtag) VALUES (?,?,?,?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		jdbcTemplate.update(
				sql,
				new Object[] { event.getTitle(), dt.format(event.getStartDate()),
						event.getContent(), event.getHashtag()});

	}

	public List<Event> getEventList() {
		List<Event> eventList = new ArrayList<Event>();

		String sql = "select e.event_id, e.title, e.start_date, e.content, e.hashtag, (select count(*) from Commentary as c where e.event_id=c.event_id) "
				+ "from Event as e ;";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		eventList = jdbcTemplate.query(sql, new EventRowMapper());
		return eventList;
	}

	@Override
	public void deleteData(String id) {
		String sql = "delete from Event where event_id=" + id;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql);

	}

	@Override
	public void updateData(Event event, String id) {
		if(event.getTitle().compareTo("")!=0) {
			String sql = "UPDATE Event set title = ? WHERE event_id="+id;
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			jdbcTemplate.update(
					sql,
					new Object[] {event.getTitle()});
		}
		if(event.getContent().compareTo("")!=0) {
			String sql = "UPDATE Event set content = ? WHERE event_id="+id;
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			jdbcTemplate.update(
					sql,
					new Object[] {event.getContent()});
		}
		if(event.getHashtag().compareTo("")!=0) {
			String sql = "UPDATE Event set hashtag = ? WHERE event_id="+id;
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			jdbcTemplate.update(
					sql,
					new Object[] { event.getHashtag() });
		}

		if(event.getStartDate()!=null) {
			try {
				String sql = "UPDATE Event set start_date = ? WHERE event_id="+id;
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
				SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
				
				jdbcTemplate.update(
					sql,
					new Object[] { dt.format(event.getStartDate()) });
			} catch(Exception e) {
				return;
			}
		}

	}

	@Override
	public Event getEvent(String id) {
		List<Event> eventList = new ArrayList<Event>();
		//String sql = "select * from Event where event_id= " + id;
		String sql = "select e.event_id, e.title, e.start_date, e.content, e.hashtag, (select count(*) from Commentary as c where e.event_id=c.event_id) "
				+ "from Event as e "
				+ "where e.event_id="+id+";";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		eventList = jdbcTemplate.query(sql, new EventRowMapper());
		Event ev = eventList.get(0);
		try {
			ev.setTweets(getTweets(ev.getHashtag(), ev.getStartDate()));
		} catch (TwitterException e) {
			// do nothing - we can't get feed
		}
		return eventList.get(0);
	}
	
	private List<Tweet> getTweets(String hashtag, Date startDate) throws TwitterException {
		List<Tweet> tweets = new ArrayList<>();
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("VXsFVFIxnVQhoHrHrrRFmvqd0")
		  .setOAuthConsumerSecret("OPOb7CTfTGD4sfjg5CxCkQdDFYlKqQtzftDv4ExCWCMKPd56rw")
		  .setOAuthAccessToken("3311873921-wAyytyh5njBES95CPhZFCYQFOB5cLj4KzUUUOdU")
		  .setOAuthAccessTokenSecret("QXSq29MFAwM7UT4kjQKm863bbri0QnFEPutBJxoYYaEFC");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		Query query = new Query(hashtag);
		QueryResult result = twitter.search(query);
		
		if(result.getTweets().size()>10) {
			for(int i=0; i<10; i++) {
				tweets.add(new Tweet(result.getTweets().get(i).getText(), result.getTweets().get(i).getUser().getName(), result.getTweets().get(i).getCreatedAt()));
			}
		} else if(result.getTweets().size()>0) {
			for(int i=0; i<result.getTweets().size(); i++) {
				tweets.add(new Tweet(result.getTweets().get(i).getSource(), result.getTweets().get(i).getUser().getName(), result.getTweets().get(i).getCreatedAt()));
			}
		} else {
			return tweets;
		}
		return tweets;
	}

}
