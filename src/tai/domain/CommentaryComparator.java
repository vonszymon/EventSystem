package tai.domain;

import java.util.Comparator;

public class CommentaryComparator implements Comparator<Commentary> {
    @Override
    public int compare(Commentary c1, Commentary c2) {
    	int v1 = c1.getCommentaryID();
    	int v2 = c2.getCommentaryID();
    	return v1 < v2 ? -1 : v1 > v2 ? +1 : 0;
    }
}
