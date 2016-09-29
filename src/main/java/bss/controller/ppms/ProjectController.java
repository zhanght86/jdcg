package bss.controller.ppms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import bss.controller.base.BaseController;
import bss.model.ppms.Project;
import bss.model.ppms.Task;
import bss.service.ppms.ProjectService;
import bss.service.ppms.TaskService;

@Controller
@Scope("prototype")
@RequestMapping("/project")
public class ProjectController extends BaseController{
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private TaskService taskservice;
	
	/**
	 * 
	* @Title: list
	* @author FengTian
	* @date 2016-9-27 上午11:03:34  
	* @Description: 查询全部 
	* @param @param page
	* @param @param model
	* @param @param project
	* @param @return      
	* @return String
	 */
	@RequestMapping("/list")
	public String list(Integer page,Model model,Project project){
		List<Project> list = projectService.list(page==null?1:page, project);
		PageInfo<Project> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("projects", project);
		return "bss/ppms/project/list";
	}
	/**
	 * 
	* @Title: add
	* @author FengTian
	* @date 2016-9-28 上午10:23:30  
	* @Description: 跳转添加页面
	* @param @return      
	* @return String
	 */
	@RequestMapping("/add")
	public String add(Integer page,Model model,Task task){
		List<Task> list = taskservice.listAll(page==null?1:page, task);
		PageInfo<Task> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("task", task);
		return "bss/ppms/project/add";
	}
	/**
	 * 
	* @Title: create
	* @author FengTian
	* @date 2016-9-28 下午1:58:47  
	* @Description: 根据id查询出任务跳转新增项目页面 
	* @param @param id
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/create")
	public String create(String id,HttpServletRequest request){
		request.getSession().setAttribute("ids", id);
		return "bss/ppms/project/addProject";
	}
	/**
	 * 
	* @Title: createProject
	* @author FengTian
	* @date 2016-9-28 下午1:59:36  
	* @Description: 添加新项目 
	* @param @param name
	* @param @param projectNumber
	* @param @param task
	* @param @return      
	* @return String
	 */
	@RequestMapping("/createProject")
	public String createProject(String name,String projectNumber,HttpServletRequest request){
		if(name != null && projectNumber != null){
			Project project = new Project();
			project.setName(name);
			project.setProjectNumber(projectNumber);
			project.setStatus(3);
			projectService.add(project);
			String id = (String) request.getSession().getAttribute("ids");
			request.getSession().removeAttribute("ids");
			String[] ids = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				Task task = taskservice.selectById(ids[i]);
				task.setProject(new Project(project.getId()));
				taskservice.update(task);
			}
		}
		return "redirect:list.html";
	}
	
	@RequestMapping("/view")
	public String view(String id,Model model){
		List<Project> list = projectService.selectByTask(id);
		model.addAttribute("proList", list);
		return "bss/ppms/project/view";
	}
	
}
