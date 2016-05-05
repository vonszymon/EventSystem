package tai.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tai.dao.CommentaryDao;
import tai.dao.EventDao;
import tai.dao.UserDao;
import tai.domain.Commentary;
import tai.domain.CommentaryComparator;
import tai.domain.Event;
import tai.domain.EventComparator;
import tai.domain.FeedContent;
import tai.domain.User;
import tai.feed.RssViewer;

@Controller
@EnableWebMvc
public class EventController {

	@Autowired
	EventDao eventDao;
	
	@Autowired
	CommentaryDao commentaryDao;
	
	@Autowired
	UserDao userDao;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    sdf.setLenient(true);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@RequestMapping("registerEvent")  
	public String registerEvent(@ModelAttribute Event event) {  
		return "eventRegister";
	}  
	
	@RequestMapping("registerUser")  
	public String registerUser(@ModelAttribute User user) {  
		return "userRegister";
	}
	
	@RequestMapping("deleteUser")  
	public ModelAndView deleteUser(@RequestParam("username") String username, RedirectAttributes redir) {  
		userDao.deleteData(username);
		ModelAndView model = new ModelAndView();
		redir.addFlashAttribute("msg","There is no trace left about you in the system...");
		model.setViewName("redirect:login");
		return model;
	}
	
	private boolean canUserPerformAction(UserAction action, String eventid, String commentid) {
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		User u = userDao.getUser(userDetails.getUsername());
		if(u.getRole().compareTo("ROLE_ADMIN")==0)
			return true;
		
		
		if(action==UserAction.DELETE_OR_EDIT_COMMENT) {
			Event e = eventDao.getEvent(eventid);
			Commentary c = commentaryDao.getCommentary(commentid);
			if(c.getEventID()==e.getEventID() && c.getAuthor().compareTo(u.getUsername())==0) {
				return true;
			}
		}		
		return false;
	}
	
	@RequestMapping("getList")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		
		List<Event> eventList = eventDao.getEventList();
		Date currentDate = new Date();
		for(Event e : eventList){
			if(currentDate.before(e.getStartDate())) e.setActive(true);
			else e.setActive(false);
		}
		Collections.sort(eventList, Collections.reverseOrder(new EventComparator()));
		PagedListHolder<Event> pagedListHolder = new PagedListHolder<Event>(eventList);
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		pagedListHolder.setPage(page);
		pagedListHolder.setPageSize(5);
		return new ModelAndView("eventList", "pagedListHolder", pagedListHolder);
	}
	
	private ModelAndView getEventPageContent(String id) {
		Map <String, Object> model = new HashMap<>();

		Event event = eventDao.getEvent(id);
		Date currentDate = new Date();
		if(currentDate.before(event.getStartDate())) event.setActive(true);
		else event.setActive(false);
		List <Commentary> comments = commentaryDao.getCommentList(id);
		Collections.sort(comments, Collections.reverseOrder(new CommentaryComparator()));
		model.put("event", event);
		model.put("comments", comments);
		model.put("commentary", new Commentary("", new Date(), ""));
		model.put("isEditing", false);
		model.put("isEditingEvent", false);
		return new ModelAndView("eventDetails", "eventDetails", model);
	}
	
	
	@RequestMapping(value="eventDetails/{eventId}", method=RequestMethod.GET)
	public ModelAndView getEventPage(@ModelAttribute("commentary") Commentary commentary, @PathVariable("eventId") String id) {
		return getEventPageContent(id);
	}
	
	@RequestMapping(value="eventDetails/{eventId}", method=RequestMethod.POST)
	public ModelAndView insertData(@ModelAttribute("commentary") Commentary commentary, @PathVariable("eventId") String id) {

		commentary.setEventID(Integer.parseInt(id));
		commentary.setPublishDate(new Date());
		commentaryDao.insertData(commentary);
		commentary = null;
		
		return getEventPageContent(id);
	}
	
	@RequestMapping(value="eventDetails/{eventId}/edit/{commentId}", method=RequestMethod.GET)
	public ModelAndView editComment(@ModelAttribute("commentary") Commentary commentary, @PathVariable("eventId") String id, @PathVariable("commentId") String commentid) {
		Map <String, Object> model = new HashMap<>();
		Event event = eventDao.getEvent(id);
		Date currentDate = new Date();
		if(currentDate.before(event.getStartDate())) event.setActive(true);
		else event.setActive(false);
		List <Commentary> comments = commentaryDao.getCommentList(id);
		Collections.sort(comments, Collections.reverseOrder(new CommentaryComparator()));
		model.put("event", event);
		model.put("comments", comments);
		model.put("editing_comment", commentid);
		model.put("commentary", new Commentary("", new Date(), ""));
		model.put("isEditing", true);
		model.put("isEditingEvent", false);
		return new ModelAndView("eventDetails", "eventDetails", model);
	}
	
	@RequestMapping(value="eventDetails/{eventId}/edit/{commentId}", method=RequestMethod.POST)
	public String editCommentDone(@ModelAttribute("commentary") Commentary commentary, @PathVariable("eventId") String id, @PathVariable("commentId") String commentid) {
		if(canUserPerformAction(UserAction.DELETE_OR_EDIT_COMMENT, id, commentid))
			commentaryDao.updateData(commentary, commentid);
		return "redirect:/eventDetails/"+id;
	}
	
	@RequestMapping(value="eventDetails/{eventId}/remove/{commentId}", method=RequestMethod.GET)
	public String removeCommentDone(@ModelAttribute("commentary") Commentary commentary, @PathVariable("eventId") String id, @PathVariable("commentId") String commentid) {
		if(canUserPerformAction(UserAction.DELETE_OR_EDIT_COMMENT, id, commentid))
			commentaryDao.deleteData(commentid);
		return "redirect:/eventDetails/"+id;
	}
	
	@RequestMapping("insertEvent")
	public String insertData(@ModelAttribute Event event) {

		if (event != null){
			eventDao.insertData(event);  
		}
		return "redirect:/getList";  
	}
	
	@RequestMapping(value="eventDetails/editEvent/{eventId}", method=RequestMethod.GET)
	public ModelAndView editEvent(@ModelAttribute("event") Event event, @PathVariable("eventId") String id) {
		Map <String, Object> model = new HashMap<>();
		Event e = eventDao.getEvent(id);
		Date currentDate = new Date();
		if(currentDate.before(e.getStartDate())) e.setActive(true);
		else e.setActive(false);
		List <Commentary> comments = commentaryDao.getCommentList(id);
		Collections.sort(comments, Collections.reverseOrder(new CommentaryComparator()));
		model.put("event", e);
		model.put("comments", comments);
		model.put("commentary", new Commentary("", new Date(), ""));
		model.put("isEditing", true);
		if(canUserPerformAction(UserAction.EDIT_EVENT, id, "")) {
			model.put("isEditingEvent", true);
		}
		return new ModelAndView("eventDetails", "eventDetails", model);
	}
	
	@RequestMapping(value="eventDetails/editEvent/{eventId}", method=RequestMethod.POST)
	public String editEventDone(@ModelAttribute("event") Event event, @PathVariable("eventId") String id) {
		if(canUserPerformAction(UserAction.EDIT_EVENT, id, "")) {
			eventDao.updateData(event, id);
		}
		return "redirect:/eventDetails/"+id;
	}
	
	@RequestMapping("insertUser")
	public ModelAndView insertData(@ModelAttribute User user, RedirectAttributes redir) {
		ModelAndView model = new ModelAndView();
		if (user != null && !userDao.exists(user.getUsername())){
			user.setEnabled((short)1);
			user.setRole("ROLE_USER");
			userDao.insertData(user);  
			redir.addFlashAttribute("msg","Registration succesfull. You can now Sign In");
		} else{
			redir.addFlashAttribute("msg","Registration failed. User already exists");
		}
		model.setViewName("redirect:login");
		return model;
	}

	@RequestMapping("login")
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}
	
	@RequestMapping("rssfeed")
	public ModelAndView getFeedInRss() {
		Date currentDate = new Date();
		List<FeedContent> items = new ArrayList<FeedContent>();
		List<Event> eventList = eventDao.getEventList();
		for(Event e : eventList){
			if(currentDate.before(e.getStartDate())){
				FeedContent content  = new FeedContent();
				content.setTitle(e.getTitle());
				content.setUrl("http://localhost:8080/TAI-Project/eventDetails/"+e.getEventID());
				content.setSummary(e.getContent());
				content.setCreatedDate(e.getStartDate());
				items.add(content);
			}
		}
		
		return new ModelAndView(new RssViewer(), "feedContent", items);

	}
	
	enum UserAction {
		DELETE_OR_EDIT_COMMENT,
		EDIT_EVENT
	}
}