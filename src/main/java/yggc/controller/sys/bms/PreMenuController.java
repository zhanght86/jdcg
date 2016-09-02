package yggc.controller.sys.bms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import yggc.model.bms.PreMenu;
import yggc.model.bms.Role;
import yggc.service.bms.PreMenuServiceI;
import yggc.service.bms.RoleServiceI;

@Controller
@Scope("prototype")
@RequestMapping("/preMenu")
public class PreMenuController {
	
	@Autowired
	private PreMenuServiceI preMenuService;
	
	@Autowired
	private RoleServiceI roleService;
	
	private static Logger logger = Logger.getLogger(RoleManageController.class);
	
	
	@RequestMapping(value = "/treedata")  
    public void menutree(HttpServletRequest request,HttpServletResponse response, Model model,Role r) throws IOException { 
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<PreMenu> list=preMenuService.getAll(null);
		List<Role> roles=roleService.selectRoleUser(r);
		List<PreMenu> roleMenus=roles.get(0).getPreMenus();
        for (int i = 0; i < list.size(); i++) {  
        	PreMenu e = list.get(i);  
            Map<String, Object> map = new HashMap<String, Object>();  
            map.put("id", e.getId());  
            map.put("pId", e.getParentId() != null ? e.getParentId().getId() : 0);  
            map.put("name", e.getName());  
            for (PreMenu roleMenu : roleMenus) {  
                if (roleMenu.getId().equals(e.getId()) ) {  
                    map.put("checked", true);  
                }  
            }  
            mapList.add(map);  
        }  
       String jsonstr= JSON.toJSONString(mapList);
       response.setContentType("text/html;charset=utf-8");
	   response.getWriter().print(jsonstr);  
    } 
	
	@RequestMapping("/save")
	public String save(){
		PreMenu menu=new PreMenu();
		menu.setName("菜单");
		menu.setIsDeleted(0);
		menu.setMenulevel(1);
		menu.setOrderby(0);
		menu.setParentId(null);
		menu.setState(0);
		menu.setType("");
		menu.setCreatedAt(new Date());
		menu.setUrl(null);
		preMenuService.save(menu);
		for (int i = 1; i < 10; i++) {
			PreMenu preMenu=new PreMenu();
			preMenu.setName("菜单"+i);
			preMenu.setIsDeleted(0);
			preMenu.setMenulevel(2);
			preMenu.setOrderby(i);
			preMenu.setParentId(menu);
			preMenu.setState(0);
			preMenu.setType("navigation");
			preMenu.setCreatedAt(new Date());
			preMenu.setUrl(null);
			preMenuService.save(preMenu);
			for (int j = 1; j < 6; j++) {
				PreMenu preMenu1=new PreMenu();
				preMenu1.setName("菜单"+i+"-"+j);
				preMenu1.setIsDeleted(0);
				preMenu1.setMenulevel(3);
				preMenu1.setOrderby(j);
				preMenu1.setParentId(preMenu);
				preMenu1.setState(0);
				preMenu1.setType("accordion");
				preMenu1.setCreatedAt(new Date());
				preMenu1.setUrl(null);
				preMenuService.save(preMenu1);
				for (int k = 1; k < 6; k++) {
					PreMenu preMenu2=new PreMenu();
					preMenu2.setName("菜单"+i+"-"+j+"-"+k);
					preMenu2.setIsDeleted(0);
					preMenu2.setMenulevel(4);
					preMenu2.setOrderby(k);
					preMenu2.setParentId(preMenu1);
					preMenu2.setState(0);
					preMenu2.setType("menu");
					preMenu2.setCreatedAt(new Date());
					preMenu2.setUrl(null);
					preMenuService.save(preMenu2);
				}
			}
		}
		return null;
	}
}
