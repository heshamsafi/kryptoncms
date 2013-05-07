package edu.asu.krypton.controllers.membership.social;

import static org.springframework.util.StringUtils.hasText;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.drive.DriveFileQueryBuilder;
import org.springframework.social.google.api.drive.DriveFilesPage;
import org.springframework.social.google.api.drive.DriveOperations;
import org.springframework.social.google.api.plus.activity.ActivitiesPage;
import org.springframework.social.google.api.plus.activity.Activity;
import org.springframework.social.google.api.plus.comment.Comment;
import org.springframework.social.google.api.plus.comment.CommentsPage;
import org.springframework.social.google.api.plus.person.PeoplePage;
import org.springframework.social.google.api.plus.person.Person;
import org.springframework.social.google.api.tasks.Task;
import org.springframework.social.google.api.tasks.TaskList;
import org.springframework.social.google.api.tasks.TaskListsPage;
import org.springframework.social.google.api.tasks.TasksPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.krypton.service.membership.social.google.SearchForm;
import edu.asu.krypton.service.membership.social.google.drive.DateOperators;
import edu.asu.krypton.service.membership.social.google.drive.DriveSearchForm;
import edu.asu.krypton.service.membership.social.google.drive.OptionalBoolean;
import edu.asu.krypton.service.membership.social.google.tasks.TaskForm;
import edu.asu.krypton.service.membership.social.google.tasks.TaskListForm;
import edu.asu.krypton.service.membership.social.google.tasks.TaskSearchForm;



@Controller
@RequestMapping(value="/membership/social/google")
public class GoogleController extends edu.asu.krypton.controllers.Controller {

	private final String DEFAULT_DIR        = "connect/google/";
	private final String SIGNIN_VIEW        = DEFAULT_DIR+"signin";
	private final String PROFILE_VIEW       = DEFAULT_DIR+"profile";
	private final String PEOPLE_VIEW        = DEFAULT_DIR+"people";
	private final String ACTIVITY_VIEW      = DEFAULT_DIR+"activity";
	private final String COMMENTS_VIEW      = DEFAULT_DIR+"people";
	private final String TASKLIST_VIEW      = DEFAULT_DIR+"tasklist";
	private final String TASKS_VIEW	        = DEFAULT_DIR+"tasks";
	private final String DRIVE_FILES_VIEW   = DEFAULT_DIR+"drivefiles";
	
	private final String BODIES_PREFIX = "bodies/";
	
	@Inject
	private ConnectionRepository connectionRepository;

	
	@ExceptionHandler(ExpiredAuthorizationException.class)
	public String handleExpiredToken() {
		return "redirect:/google/signout";
	}
	
	@ExceptionHandler(Exception.class)
	public void handleException(Exception e) {
		e.printStackTrace();
	}
	
	@RequestMapping(value="signin")
	public String signin(ModelMap model,HttpServletRequest request){ 
		return appropriateView(request, BODIES_PREFIX+SIGNIN_VIEW, defaultView(model, SIGNIN_VIEW)); 
	}
	
	@RequestMapping(value="profile",method=GET)
	public String profile(ModelMap model,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		model.addAttribute("profile", google.userOperations().getUserProfile());
		return appropriateView(request, BODIES_PREFIX+PROFILE_VIEW, defaultView(model,PROFILE_VIEW));
	}
	
	@RequestMapping(value="person", method=GET)
	public String person(ModelMap model, String id, String contact,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		if(hasText(id)) {
			Person person = google.personOperations().getPerson(id);
			model.addAttribute("command", new SearchForm())
				 .addAttribute("person", person);
			return appropriateView(request,BODIES_PREFIX+PEOPLE_VIEW, defaultView(model, PEOPLE_VIEW));
		}
		return "redirect:/google/people";
	}
	
	@RequestMapping(value="people", method=GET, params={"!plusoners","!resharers"})
	public String people(String text, String pageToken,ModelMap model,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		PeoplePage people;
		people = hasText(text) ? google.personOperations().personQuery()
								.searchFor(text)
								.fromPage(pageToken)
								.getPage() : new PeoplePage();
		model.addAttribute("people", people);
		return appropriateView(request, BODIES_PREFIX+PEOPLE_VIEW, defaultView(model, PEOPLE_VIEW));
	}
	
	@RequestMapping(value="people", method=GET, params="plusoners")
	public String plusOners(ModelMap model, String plusoners, String pageToken,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		PeoplePage people = google.personOperations().getActivityPlusOners(plusoners, pageToken);
		model.addAttribute("people", people);
		return appropriateView(request, BODIES_PREFIX+PEOPLE_VIEW, defaultView(model, PEOPLE_VIEW));
	}
	
	@RequestMapping(value="people", method=GET, params="resharers")
	public String resharers(ModelMap model, String resharers, String pageToken,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		PeoplePage people = google.personOperations().getActivityPlusOners(resharers, pageToken);
		model.addAttribute("people", people);
		return appropriateView(request, BODIES_PREFIX+PEOPLE_VIEW, defaultView(model, PEOPLE_VIEW));
	}
	
	@RequestMapping(value="activity", method=GET)
	public String activity(ModelMap model,String id,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		Activity activity = google.activityOperations().getActivity(id);
		model.addAttribute("activity", activity);
		return appropriateView(request, BODIES_PREFIX+ACTIVITY_VIEW, defaultView(model, ACTIVITY_VIEW));
	}
	
	@RequestMapping(value="activities", method=GET, params="!text")
	public String listActivities(ModelMap model,@RequestParam(defaultValue="me") String person, String pageToken,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		ActivitiesPage activities = google.activityOperations().getActivitiesPage(person, pageToken);
		model.addAttribute("activities", activities);
		return appropriateView(request, BODIES_PREFIX+ACTIVITY_VIEW, defaultView(model, ACTIVITY_VIEW));
	}
	
	@RequestMapping(value="activities", method=GET, params="text")
	public String searchActivities(ModelMap model,String text, String pageToken,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		ActivitiesPage activities = google.activityOperations().activityQuery()
										  .searchFor(text)
										  .fromPage(pageToken)
										  .getPage();
		model.addAttribute("activities", activities);
		return appropriateView(request, BODIES_PREFIX+ACTIVITY_VIEW, defaultView(model, ACTIVITY_VIEW));
	}
	
	@RequestMapping(value="comments", method=GET)
	public String comments(ModelMap model,String activity, String pageToken,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		CommentsPage comments = google.commentOperations().getComments(activity, pageToken);
		model.addAttribute("comments", comments);
		return appropriateView(request, BODIES_PREFIX+COMMENTS_VIEW, defaultView(model, COMMENTS_VIEW));
	}
	
	@RequestMapping(value="comment", method=GET)
	public String comment(ModelMap model,String id,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		Comment comment = google.commentOperations().getComment(id);
		model.addAttribute("comment", comment);
		return appropriateView(request, BODIES_PREFIX+COMMENTS_VIEW, defaultView(model, COMMENTS_VIEW));
	}
	
	@RequestMapping(value="tasklists", method=GET)
	public String taskLists(ModelMap model,String pageToken,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		TaskListsPage taskLists = google.taskOperations().taskListQuery().fromPage(pageToken).getPage();
		model.addAttribute("taskLists", taskLists);
		return appropriateView(request, BODIES_PREFIX+TASKLIST_VIEW, defaultView(model, TASKLIST_VIEW));
	}
	
	@RequestMapping(value="tasklist", method=GET)
	public String tasklist(ModelMap model,HttpServletRequest request) {
		model.addAttribute("command", new TaskListForm());
		return appropriateView(request, BODIES_PREFIX+TASKLIST_VIEW, defaultView(model, TASKLIST_VIEW));
	}
	
	@RequestMapping(value="tasklist", method=GET, params="id")
	public String taskList(ModelMap model,String id,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		TaskList taskList = google.taskOperations().getTaskList(id);
		TaskListForm command = new TaskListForm(taskList.getId(), taskList.getTitle());
		model.addAttribute("command", command);
		return appropriateView(request, BODIES_PREFIX+TASKLIST_VIEW, defaultView(model, TASKLIST_VIEW));
	}
	
	@RequestMapping(value="tasklist", method=POST)
	public String saveTaskList(ModelMap model,@Valid TaskListForm command, BindingResult result,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		if(result.hasErrors()){
			model.addAttribute("command", command);
			return appropriateView(request, BODIES_PREFIX+TASKLIST_VIEW, defaultView(model, TASKLIST_VIEW));
		}
		
		TaskList taskList = new TaskList(command.getId(), command.getTitle());
		google.taskOperations().saveTaskList(taskList);
		return "redirect:/google/tasklists";
	}
	
	@RequestMapping(value="tasklist", method=POST, params="delete")
	public String deleteTaskList(TaskListForm command,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		TaskList taskList = new TaskList(command.getId(), command.getTitle());
		google.taskOperations().deleteTaskList(taskList);
		return appropriateView(request, BODIES_PREFIX+TASKLIST_VIEW, TASKLIST_VIEW);
	}
	
	@RequestMapping(value="tasks", method=GET)
	public String  tasks(ModelMap model,TaskSearchForm command,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		TasksPage tasks = google.taskOperations().taskQuery()
								.fromTaskList(command.getList())
								.fromPage(command.getPageToken())
								.completedFrom(command.getCompletedMin())
								.completedUntil(command.getCompletedMax())
								.dueFrom(command.getDueMin())
								.dueUntil(command.getDueMax())
								.updatedFrom(command.getUpdatedMin())
								.includeCompleted(command.isIncludeCompleted())
								.includeDeleted(command.isIncludeDeleted())
								.includeHidden(command.isIncludeHidden())
								.getPage();
		model.addAttribute("command", command)
			 .addAttribute("tasks", tasks);
		return appropriateView(request, BODIES_PREFIX+TASKS_VIEW, defaultView(model, TASKS_VIEW));
	}
	
	@RequestMapping(value="task", method=GET)
	public String task(ModelMap model,HttpServletRequest request) {
		model.addAttribute("command", new TaskForm());
		return appropriateView(request, BODIES_PREFIX+TASKS_VIEW, defaultView(model, TASKS_VIEW));
	}
	
	@RequestMapping(value="task", method=GET, params="id")
	public String task(ModelMap model,String list, String id,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		if(!hasText(list)) list = "@default";
		
		Task task = google.taskOperations().getTask(list, id);
		TaskForm command = new TaskForm(task.getId(), task.getTitle(), task.getDue(), task.getNotes(), task.getCompleted());
		model.addAttribute("command", command);
		return appropriateView(request, BODIES_PREFIX+TASKS_VIEW, defaultView(model, TASKS_VIEW));
	}
	
	@RequestMapping(value="task", method=POST)
	public String saveTask(ModelMap model,TaskForm command, BindingResult result,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		if(result.hasErrors()) {
			model.addAttribute("command", command);
			return appropriateView(request,BODIES_PREFIX+TASKS_VIEW, defaultView(model, TASKS_VIEW));
		}

		Task task = new Task(command.getId(), command.getTitle(), command.getNotes(), command.getDue(), command.getCompleted());
		google.taskOperations().saveTask(command.getList(), task);
		model.addAttribute("list",command.getList());
		return appropriateView(request, BODIES_PREFIX+TASKLIST_VIEW, defaultView(model,TASKLIST_VIEW));
	}
	
	@RequestMapping(value="task", method=POST, params="parent")
	public String createTask(ModelMap model,String parent, String previous, TaskForm command, BindingResult result,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		if(result.hasErrors()) {
			model.addAttribute("command", command);
			return appropriateView(request, BODIES_PREFIX+TASKS_VIEW, defaultView(model, TASKS_VIEW));
		}

		Task task = new Task(command.getId(), command.getTitle(), command.getNotes(), command.getDue(), command.getCompleted());
		google.taskOperations().createTaskAt(command.getList(), parent, previous, task);
		model.addAttribute("list", command.getList());
		return appropriateView(request, BODIES_PREFIX+TASKS_VIEW, defaultView(model, TASKS_VIEW));
	}
	
	@RequestMapping(value="movetask", method=POST)
	public String moveTask(ModelMap model,String list, String move, String parent, String previous,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		google.taskOperations().moveTask(list, new Task(move), parent, previous);
		model.addAttribute("list", list);
		return appropriateView(request, BODIES_PREFIX+TASKS_VIEW, defaultView(model,TASKS_VIEW));
	}
	
	@RequestMapping(value="task", method=POST, params="delete")
	public String deleteTask(ModelMap model,@Valid TaskForm command,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		google.taskOperations().deleteTask(command.getList(), new Task(command.getId()));
		model.addAttribute("list", command.getList());
		return appropriateView(request, BODIES_PREFIX+TASKS_VIEW, defaultView(model,TASKS_VIEW));
	}
	
	@RequestMapping(value="cleartasks", method=POST)
	public String clearTasks(ModelMap model,String list,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		if(!hasText(list)) {
			list = "@default";
		}
		google.taskOperations().clearCompletedTasks(new TaskList(list, null));
		model.addAttribute("list", list);
		return appropriateView(request, BODIES_PREFIX+TASKS_VIEW, defaultView(model,TASKS_VIEW));
	}
	
	@RequestMapping(value="drivefiles", method=GET)
	public String getDriveFiles(ModelMap model,DriveSearchForm command,HttpServletRequest request) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		DriveFileQueryBuilder queryBuilder = google.driveOperations().driveFileQuery()
				.fromPage(command.getPageToken());

		if(command.isNegate()) {
			queryBuilder.not();
		}
		
		if(hasText(command.getTitleIs())) {
			queryBuilder.titleIs(command.getTitleIs());
		}
		
		if(hasText(command.getTitleContains())) {
			queryBuilder.titleContains(command.getTitleContains());
		}
		
		if(hasText(command.getFullTextContains())) {
			queryBuilder.fullTextContains(command.getFullTextContains());
		}
		
		if(hasText(command.getMimeTypeIs())) {
			queryBuilder.mimeTypeIs(command.getMimeTypeIs());
		}
		
		if(command.getModifiedValue() != null) {
			Date date = command.getModifiedValue();
			switch(command.getModifiedOperator()) {
			case BEFORE:
				queryBuilder.modifiedDateBefore(date);
				break;
			case IS_OR_BEFORE:
				queryBuilder.modifiedDateIsOrBefore(date);
				break;
			case IS:
				queryBuilder.modifiedDateIs(date);
				break;
			case IS_OR_AFTER:
				queryBuilder.modifiedDateIsOrAfter(date);
				break;
			case AFTER:
				queryBuilder.modifiedDateAfter(date);
				break;
			}
		}
		
		if(command.getLastViewedValue() != null) {
			Date date = command.getLastViewedValue();
			switch(command.getLastViewedOperator()) {
			case BEFORE:
				queryBuilder.lastViewedByMeBefore(date);
				break;
			case IS_OR_BEFORE:
				queryBuilder.lastViewedByMeIsOrBefore(date);
				break;
			case IS:
				queryBuilder.lastViewedByMeIs(date);
				break;
			case IS_OR_AFTER:
				queryBuilder.lastViewedByMeIsOrAfter(date);
				break;
			case AFTER:
				queryBuilder.lastViewedByMeAfter(date);
				break;
			}
		}
		
		if(command.getTrashed() != null && command.getTrashed().getValue() != null) {
			queryBuilder.trashed(command.getTrashed().getValue());
		}
		
		if(command.getStarred() != null && command.getStarred().getValue() != null) {
			queryBuilder.starred(command.getStarred().getValue());
		}
		
		if(command.getHidden() != null && command.getHidden().getValue() != null) {
			queryBuilder.hidden(command.getHidden().getValue());
		}
		
		if(hasText(command.getParentId())) {
			queryBuilder.parentIs(command.getParentId());
		} else {
			queryBuilder.parentIs("root");
		}
		
		if(hasText(command.getOwner())) {
			queryBuilder.ownerIs(command.getOwner());
		}
		
		if(hasText(command.getWriter())) {
			queryBuilder.writerIs(command.getWriter());
		}
		
		if(hasText(command.getReader())) {
			queryBuilder.readerIs(command.getReader());
		}
		
		if(command.isSharedWithMe()) {
			queryBuilder.sharedWithMe();
		}
		
		DriveFilesPage files = queryBuilder.getPage();

		Map<DateOperators, String> dateOperators = new LinkedHashMap<DateOperators, String>();
		for(DateOperators operator : DateOperators.values()) {
			dateOperators.put(operator, operator.toString());
		}
		
		Map<OptionalBoolean, String> booleanOperators = new LinkedHashMap<OptionalBoolean, String>();
		for(OptionalBoolean operator : OptionalBoolean.values()) {
			booleanOperators.put(operator, operator.toString());
		}
		model.addAttribute("dateOperators", dateOperators)
			 .addAttribute("booleanOperators", booleanOperators)
			 .addAttribute("command", command)
			 .addAttribute("files", files);
		return appropriateView(request, BODIES_PREFIX+DRIVE_FILES_VIEW, defaultView(model, DRIVE_FILES_VIEW));
	}
	
	
	//TODO: begin ajax methods !
	@RequestMapping(value="starfile", method=POST)
	@ResponseBody
	public void starFile(String fileId, boolean star) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		DriveOperations drive = google.driveOperations();
		if(star) {
			drive.star(fileId);
		} else {
			drive.unstar(fileId);
		}
	}
	
	@RequestMapping(value="trashfile", method=POST)
	@ResponseBody
	public void trashFile(String fileId, boolean trash) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		DriveOperations drive = google.driveOperations();
		if(trash) {
			drive.trash(fileId);
		} else {
			drive.untrash(fileId);
		}
	}
	
	@RequestMapping(value="deletefile", method=POST)
	@ResponseBody
	public void deleteFile(String fileId) {
		Google google = connectionRepository.getPrimaryConnection(Google.class).getApi();
		google.driveOperations().delete(fileId);
	}
}
