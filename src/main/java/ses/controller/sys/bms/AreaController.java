package ses.controller.sys.bms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Area;
import ses.model.bms.AreaZtree;
import ses.service.bms.AreaServiceI;

import com.alibaba.fastjson.JSONObject;


/**
 * 
 * @Title:AreaController
 * @Description:地区管理控制类
 * @author FengTian
 * @date 2016-9-7下午6:32:07
 */
@Controller
@Scope("prototype")
@RequestMapping("/area")
public class AreaController {
	@Autowired
	private AreaServiceI areaService;

	/**
	 * 
	 * @Title: createList
	 * @author FengTian
	 * @date 2016-9-1 下午3:27:16
	 * @Description: 创建页面
	 * @param @param model
	 * @param @return
	 * @return String
	 */
	@RequestMapping("/list")
	public String list() {
		return "ses/bms/area/list";
	}

	/**
	 * 
	 * @Title: listByOne
	 * @author FengTian
	 * @date 2016-9-7 下午6:30:11
	 * @Description: 生成tree树
	 * @param @param area
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 */
	@ResponseBody
	@RequestMapping("/listByOne")
	public String listByOne(Area area,Model model)throws Exception {
		if (area.getId() == null) {
			area.setId("1");
		}
		List<Area> list = areaService.findTreeByPid(area.getId(),area.getName());
		List<AreaZtree> listTree = new ArrayList<AreaZtree>();
		String ee = "";
		for (Area a : list) {
			List<Area> cList = areaService.findTreeByPid(a.getId(),area.getName());
			AreaZtree az = new AreaZtree();
			if (!cList.isEmpty()) {
				az.setIsParent("true");
			} else {
				az.setIsParent("false");
			}
			az.setId(a.getId());
			az.setName(a.getName());
			az.setpId(a.getAreaType());
			listTree.add(az);
		}
		ee = JSONObject.toJSONString(listTree);
		//model.addAttribute("aa", list1);
		return ee;
	}
	/**
	 * 
	* @Title: add
	* @author FengTian
	* @date 2016-9-29 下午3:47:27  
	* @Description: 跳转添加页面 
	* @param @param request
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request, Model model){
		String pid = request.getParameter("pid");
		Area area = areaService.listById(pid);
		model.addAttribute("area", area);
		return "ses/bms/area/add";
	}
	/**
	 * 
	* @Title: edit
	* @author FengTian
	* @date 2016-10-12 下午4:23:43  
	* @Description: 跳转修改页面 
	* @param @param request
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/edit")
	public String edit(String pid, Model model){
		Area area = areaService.listById(pid);
		String ids = area.getAreaType();
		Area area1 = areaService.listById(ids);
		model.addAttribute("area", area);
		model.addAttribute("area1", area1);
		return "ses/bms/area/edit";
	}
	/**
	 * 
	* @Title: save
	* @author FengTian
	* @date 2016-9-29 下午3:47:40  
	* @Description: 添加地区 
	* @param @param area
	* @param @return      
	* @return String
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(Area area){
		Area aa = null;
		if(area.getId() != null && !"".equals(area.getId())){
			aa = areaService.listById(area.getId());
		}
		area.setAreaType(aa.getId());
		area.setIsDeleted(0);
		area.setCreatedAt(new Date());
		areaService.save(area);
		String msg = "{\"msg\":\"success\"}";
		return msg;
	}
	/**
	 * 
	* @Title: update
	* @author FengTian
	* @date 2016-10-12 下午7:05:25  
	* @Description: 修改 
	* @param @param area
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String update(Area area){
		Area aa = null;
		if(area.getId() != null && !"".equals(area.getId())){
			aa = areaService.listById(area.getId());
		}
		area.setIsDeleted(0);
		area.setCreatedAt(new Date());
		areaService.update(area);
		String msg = "{\"msg\":\"success\"}";
		return msg;
	}
	
	@RequestMapping("/delete")
	public String delete(String id){
		String[] ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			Area area = areaService.listById(ids[i]);
			area.setIsDeleted(1);
			areaService.update(area);
		}
		return "redirect:list.html";
	}
}
