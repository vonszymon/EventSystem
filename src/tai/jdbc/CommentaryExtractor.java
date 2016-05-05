package tai.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.dao.DataAccessException;

import tai.domain.Commentary;

public class CommentaryExtractor {
	
	public Commentary extractData(ResultSet resultSet) throws SQLException,
		DataAccessException {
		
		Commentary comment = new Commentary();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		comment.setCommentaryID(resultSet.getInt(1));
		comment.setEventID(resultSet.getInt(2));
		comment.setAuthor(resultSet.getString(3));
		try {
			if(resultSet.getString(4)!=null)
			comment.setPublishDate(df.parse(resultSet.getString(4)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		comment.setContent(resultSet.getString(5));
		
		return comment;
	}
}
