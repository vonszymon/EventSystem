package tai.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tai.domain.Commentary;
import tai.jdbc.CommentaryRowMapper;

public class CommentaryDaoImpl implements CommentaryDao{
	@Autowired
	DataSource dataSource;

	public void insertData(Commentary comment) {

		String sql = "INSERT INTO Commentary "
				+ "(event_id, author, publish_date, content) VALUES (?,?,?,?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		jdbcTemplate.update(
				sql,
				new Object[] { comment.getEventID(), comment.getAuthor(), 
						dt.format(comment.getPublishDate()), comment.getContent()});

	}

	public List<Commentary> getCommentList(String eventID) {
		List<Commentary> commentList = new ArrayList<Commentary>();

		String sql = "select commentary_id, event_id, author, publish_date, content from Commentary where event_id=" +eventID+"; ";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		commentList = jdbcTemplate.query(sql, new CommentaryRowMapper());
		
		
		
		return commentList;
	}

	@Override
	public void deleteData(String id) {
		String sql = "delete from Commentary where commentary_id=" + id;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql);

	}

	@Override
	public void updateData(Commentary comment, String id) {
		String sql = "UPDATE Commentary set content = ? where commentary_id="+id;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql,
				new Object[] { comment.getContent() });

	}

	@Override
	public Commentary getCommentary(String id) {
		List<Commentary> commentList = new ArrayList<Commentary>();
		String sql = "select * from Commentary where commentary_id= " + id;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		commentList = jdbcTemplate.query(sql, new CommentaryRowMapper());
		return commentList.get(0);
	}

}
