package tai.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tai.domain.Commentary;

public class CommentaryRowMapper implements RowMapper<Commentary>{
	@Override
	public Commentary mapRow(ResultSet resultSet, int line) throws SQLException {
		CommentaryExtractor userExtractor = new CommentaryExtractor();
		return userExtractor.extractData(resultSet);
	}
}
