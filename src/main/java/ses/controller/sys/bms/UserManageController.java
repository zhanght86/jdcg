package ses.controller.sys.bms;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.bms.Userrole;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.util.Encrypt;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;


/**
 * Description: 用户管理控制类 
 *
 * @author Ye MaoLin
 * @version 2016-9-13
 * @since JDK1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserManageController {

	@Autowired
	private UserServiceI userService;

	@Autowired
	private RoleServiceI roleService;

	private Logger logger = Logger.getLogger(LoginController.class);

	/**
	 * Description: 查询列表
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param model
	 * @param page
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/list")
	public String list(Model model, Integer page) {
		List<User> users = userService.selectUser(null, page == null ? 1 : page);
		model.addAttribute("list", new PageInfo<User>(users));
		logger.info(JSON.toJSONStringWithDateFormat(users, "yyyy-MM-dd HH:mm:ss"));
		return "user/list";
	}

	/**
	 * Description: 跳转新增编辑页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param request
	 * @param model
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request, Model model) {
		List<Role> roles = roleService.getAll(null);
		model.addAttribute("roles", roles);
		return "user/add";
	}

	/**
	 * Description: 保存新增信息
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param request
	 * @param user
	 * @param roleId
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, User user, String roleId) {
		user.setIsDeleted(0);
		user.setCreatedAt(new Date());
		User currUser = (User) request.getSession().getAttribute("loginUser");
		if (currUser != null) {
			user.setUser(currUser);
		} else {

		}
		String psw = Encrypt
				.md5AndSha(user.getLoginName() + user.getPassword());
		user.setPassword(psw);
		userService.save(user);

		String[] roleIds = roleId.split(",");
		for (int i = 0; i < roleIds.length; i++) {
			Userrole userrole = new Userrole();
			Role role = roleService.get(roleIds[i]);
			userrole.setRoleId(role);
			userrole.setUserId(user);
			userService.saveRelativity(userrole);
		}
		return "redirect:list.html";
	}

	/**
	 * Description: 跳转修改编辑页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param u
	 * @param model
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/edit")
	public String edit(User u, Model model) {
		List<User> users = userService.find(u);
		if(users != null && users.size()>0){
			User user = users.get(0);
			logger.info(JSON
					.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
			logger.info(JSON.toJSONStringWithDateFormat(user.getUser(),
					"yyyy-MM-dd HH:mm:ss"));
			List<Role> roles = roleService.getAll(null);
			String roleId = "";
			String roleName = "";
			List<Role> list = user.getRoles();
			for (int i = 0; i < list.size(); i++) {
				if (i + 1 == list.size()) {
					roleId += list.get(i).getId();
					roleName += list.get(i).getName();
				} else {
					roleId += list.get(i).getId() + ",";
					roleName += list.get(i).getName() + ",";
				}
			}
			model.addAttribute("roleName", roleName);
			model.addAttribute("roleId", roleId);
			model.addAttribute("roles", roles);
			model.addAttribute("user", user);
		}else{
			
		}
		return "user/edit";
	}

	/**
	 * Description: 更新修改信息
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param request
	 * @param user
	 * @param roleId
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/update")
	public String update(HttpServletRequest request, User u, String roleId) {
		
		User temp = new User();
		temp.setId(u.getId());
		//查询旧数据的关联关系
		List<User> users = userService.find(temp);
		if(users != null && users.size() > 0){
			User olduser = users.get(0);
			
			// 先删除之前的与角色的关联关系
			List<Role> oldRole = olduser.getRoles();
			for (Role role : oldRole) {
				Userrole userrole = new Userrole();
				userrole.setUserId(olduser);
				userrole.setRoleId(role);
				roleService.deleteRoelUser(userrole);
			}
			
			//获取old创建人
			User oldCreate = new User();
			String oldCreaterId = request.getParameter("userId");
			User oldtemp = new User();
			oldtemp.setId(oldCreaterId);
			List<User> creaters=userService.find(oldtemp);
			if(creaters != null && creaters.size() > 0){
				oldCreate = creaters.get(0);
			}else{
				oldCreate = null;
			}
			
			u.setUser(olduser);
			u.setUpdatedAt(new Date());
			userService.update(u);
			
			String[] roleIds = roleId.split(",");
			for (int i = 0; i < roleIds.length; i++) {
				Userrole userrole = new Userrole();
				Role role = roleService.get(roleIds[i]);
				userrole.setRoleId(role);
				userrole.setUserId(u);
				userService.saveRelativity(userrole);
			}
		}else{
			
		}

		return "redirect:list.html";
	}

	/**
	 * Description: 批量删除用户信息，逻辑删除
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param ids
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/delete_soft")
	public String delete_soft(String ids) {
		String[] id = ids.split(",");
		for (String str : id) {
			Userrole userrole = new Userrole();
			User user=new User();
			user.setId(str);
			userrole.setUserId(user);
			roleService.deleteRoelUser(userrole);
			userService.deleteByLogic(str);
		}
		return "redirect:list.html";
	}

	/**
	 * Description: 显示用户详细信息页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param model
	 * @param user
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/view")
	public String view(Model model, User user) {
		List<User> ulist = userService.find(user);
		if(ulist != null && ulist.size() > 0){
			model.addAttribute("user", ulist.get(0));
		}else{
			
		}
		return "user/view";
	}

}
