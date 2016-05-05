package tai.feed;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import tai.domain.FeedContent;

import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Content;
import com.sun.syndication.feed.rss.Item;

public class RssViewer extends AbstractRssFeedView {

	@Override
	protected void buildFeedMetadata(Map<String, Object> model, Channel feed,
			HttpServletRequest request) {
		
		feed.setTitle("Event System");
		feed.setDescription("Available events");
		feed.setLink("http://localhost:8080/TAI-Project/");
		
		super.buildFeedMetadata(model, feed, request);
	}
	
	
	@Override
	protected List<Item> buildFeedItems(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		@SuppressWarnings("unchecked")
		List<FeedContent> listContent = (List<FeedContent>) model.get("feedContent");
		List<Item> items = new ArrayList<Item>(listContent.size());
		
		for(FeedContent tempContent : listContent ){
		
			Item item = new Item();
			
			Content content = new Content();
			content.setValue(tempContent.getSummary());
			item.setContent(content);
			
			item.setTitle(tempContent.getTitle());
			item.setLink(tempContent.getUrl());
			item.setPubDate(tempContent.getCreatedDate());
			
			items.add(item);
		}
		
		return items;
	}

	
}
