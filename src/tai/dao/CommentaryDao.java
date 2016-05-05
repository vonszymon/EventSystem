package tai.dao;

import java.util.List;

import tai.domain.Commentary;

public interface CommentaryDao {
	public void insertData(Commentary comment);

	public List<Commentary> getCommentList(String eventID);

	public void updateData(Commentary comment, String id);

	public void deleteData(String id);

	public Commentary getCommentary(String id);
}
