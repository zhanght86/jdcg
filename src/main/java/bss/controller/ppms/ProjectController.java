package bss.controller.ppms;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;

import bss.controller.base.BaseController;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectAttachments;
import bss.model.ppms.Task;
import bss.model.ppms.TaskAttachments;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.ProjectAttachmentsService;
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
	@Autowired
	private ProjectAttachmentsService attachmentsService;
	@Autowired
	private CollectPlanService collectPlanService; 
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	
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
		PageInfo<Project> info = new PageInfo<Project>(list);
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
		PageInfo<Task> info = new PageInfo<Task>(list);
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
	/**
	 * 
	* @Title: view
	* @author FengTian
	* @date 2016-9-29 下午5:11:38  
	* @Description: 查看项目 
	* @param @param id
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(String id,Model model,Integer page){
		List<Task> list = taskservice.selectByProject(id, page==null?1:page);
		Project ject = projectService.selectById(id);
		PageInfo<Task> info = new PageInfo<Task>(list);
		model.addAttribute("info", info);
		model.addAttribute("ject", ject);
		return "bss/ppms/project/view";
	}
	
	@RequestMapping("/edit")
	public String edit(String id,Model model,Integer page){
		List<Task> list = taskservice.selectByProject(id, page==null?1:page);
		Project ject = projectService.selectById(id);
		PageInfo<Task> info = new PageInfo<Task>(list);
		model.addAttribute("info", info);
		model.addAttribute("ject", ject);
		return "bss/ppms/project/edit";
	}
	/**
	 * 
	* @Title: startProject
	* @author FengTian
	* @date 2016-10-8 下午2:17:39  
	* @Description: 启动项目 
	* @param @param id
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/startProject")
	public String startProject(String id,Model model){
		Project project = projectService.selectById(id);
		model.addAttribute("project", project);
		return "bss/ppms/project/upload";
	}
	/**
	 * 
	* @Title: viewDetail
	* @author FengTian
	* @date 2016-10-8 下午2:16:44  
	* @Description: 查看明细 
	* @param @param id
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/viewDetail")
	public String viewDetail(String id,Model model){
		Task task = taskservice.selectById(id);
		CollectPlan queryById = collectPlanService.queryById(task.getCollectId());
		if(queryById != null){
		Map<String,Object> map = new HashMap<String,Object>();
		map.get(queryById);
		List<PurchaseRequired> list = purchaseRequiredService.getByMap(map);
		model.addAttribute("queryById", queryById);
		model.addAttribute("lists", list);
		
		}
		return "bss/ppms/project/viewDetail";
	}
	
	@RequestMapping("/viewDet")
	public String viewDet(String id,Model model){
		Task task = taskservice.selectById(id);
		CollectPlan queryById = collectPlanService.queryById(task.getCollectId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.get(queryById);
		List<PurchaseRequired> list = purchaseRequiredService.getByMap(map);
		model.addAttribute("queryById", queryById);
		model.addAttribute("lists", list);
		return "bss/ppms/project/saveDetail";
	}
	
	@RequestMapping("/saveDetail")
	public String saveDetail(String id,Model model){
		String[] ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(ids[i]);
			
			
		}
		 
		return "bss/ppms/project/saveDetail";
	}
	
	@RequestMapping("/editDet")
	public String editDet(String id,Model model){
		Task task = taskservice.selectById(id);
		CollectPlan queryById = collectPlanService.queryById(task.getCollectId());
			Map<String,Object> map = new HashMap<String,Object>();
			map.get(queryById);
			List<PurchaseRequired> list = purchaseRequiredService.getByMap(map);
			model.addAttribute("queryById", queryById);
			model.addAttribute("lists", list);
		return "bss/ppms/project/eidtDetail";
	}
	
	@RequestMapping("/editDetail")
	public void editDetail(String id,String purchaseCount,String price,String purchaseType,Model model){
		String[] idc = id.split(",");
		String[] ida = purchaseCount.split(",");
		String[] idb = price.split(",");
		String[] ide = purchaseType.split(",");
		for (int i = 0; i < idc.length; i++) {
			PurchaseRequired qq = purchaseRequiredService.queryById(idc[i]);
			qq.setPurchaseCount(Long.valueOf(ida[i]));
			qq.setPrice(new BigDecimal(idb[i]));
			qq.setPurchaseType(ide[i]);
			qq.setBudget(new BigDecimal(Long.valueOf(ida[i])).multiply(new BigDecimal(idb[i])));
			purchaseRequiredService.update(qq);
		}
		
	}
	
	/**
	 * 
	* @Title: subPackage
	* @author ZhaoBo
	* @date 2016-10-8 下午4:08:11  
	* @Description: 项目分包页面 
	* @param @param request
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/subPackage")
	public String subPackage(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		List<Task> task = taskservice.selectByProjectId(id);
		List<PurchaseRequired> list = new ArrayList<PurchaseRequired>();
		for(int i=0;i<task.size();i++){
			CollectPlan queryById = collectPlanService.queryById(task.get(i).getCollectId());
			if(queryById != null){
				Map<String,Object> map = new HashMap<String,Object>();
				map.get(queryById);
				List<PurchaseRequired> PurchaseRequired = purchaseRequiredService.getByMap(map);
				list.addAll(PurchaseRequired);
			}
		}
		System.out.println(list.size());
		model.addAttribute("list", list);
		Project project = projectService.selectById(id);
		model.addAttribute("project", project);
		return "bss/ppms/project/sub_package";
	}
	
	/**
	 * 
	* @Title: upfile
	* @author FengTian
	* @date 2016-10-8 下午2:18:09  
	* @Description: 上传 
	* @param @param attach
	* @param @param request
	* @param @param project      
	* @return void
	 */
	public void upfile( MultipartFile[] attach,
            HttpServletRequest request,Project project){
		if(attach!=null){
			for(int i=0;i<attach.length;i++){
				if(attach[i].getOriginalFilename()!=null && attach[i].getOriginalFilename()!=""){
			        String rootpath = (request.getSession().getServletContext().getRealPath("/")+"upload/").replace("\\", "/");
			        /** 创建文件夹 */
					File rootfile = new File(rootpath);
					if (!rootfile.exists()) {
						rootfile.mkdirs();
					}
			        String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_" + attach[i].getOriginalFilename();
			        String filePath = rootpath+fileName;
			        File file = new File(filePath);
			        try {
			        	attach[i].transferTo(file);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					ProjectAttachments attachment=new ProjectAttachments();
					attachment.setProject(new Project(project.getId()));
					attachment.setFileName(fileName);
					attachment.setCreatedAt(new Date());
					attachment.setUpdatedAt(new Date());
					attachment.setContentType(attach[i].getContentType());
					attachment.setFileSize((float)attach[i].getSize());
					attachment.setAttachmentPath(filePath);
					attachmentsService.save(attachment);
				}
			}
		}
	}
}