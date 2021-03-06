package ses.controller.sys.bms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.bms.UserPreMenu;
import ses.model.bms.Userrole;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseInfo;
import ses.model.oms.util.Ztree;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.oms.PurchaseServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;
import bss.controller.base.BaseController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.StaticVariables;

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
public class UserManageController extends BaseController{

	@Autowired
	private UserServiceI userService;

	@Autowired
	private RoleServiceI roleService;
	
	@Autowired
	private OrgnizationServiceI orgnizationService;
	
	@Autowired
	private PreMenuServiceI preMenuService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataService;
	
	@Autowired
  private PurchaseOrgnizationServiceI purchaseOrgnizationServiceI;
	
	@Autowired
  private PurchaseServiceI purchaseServiceI;

	private Logger logger = Logger.getLogger(UserManageController.class);

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
	public String list(Model model, Integer page, User user) {
	    if (user.getRoleId() != null && !"".equals(user.getRoleId())) {
	        user.setRoleIdList(Arrays.asList(user.getRoleId().split(",")));
	    }
		List<User> users = userService.findUserRole(user, page == null ? 1 : page);
//    List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
//    model.addAttribute("typeNames", typeNames);
	  Role role = new Role();
    List<Role> roles = roleService.find(role);
    for (User u : users) {
      List<Role> roles2 = roleService.selectByUserId(u.getId());
      u.setRoles(roles2);
    }
    model.addAttribute("roles", roles);
    model.addAttribute("list", new PageInfo<User>(users));
    model.addAttribute("user", user);
		return "ses/bms/user/list";
	}

	/**
	 * Description: 判断用户名是否存在
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-14
	 * @param loginName
	 * @param response
	 * @exception IOException
	 */
	@RequestMapping("/findByLoginName")
	public void findByLoginName(String loginName, HttpServletResponse response) throws IOException {
		try {
		  String msg = "";
		  if (loginName == null || "".equals(loginName)) {
		    msg = "用户名不能为空";
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(
            "{\"success\": " + false + ", \"msg\": \"" + msg
            + "\"}");
      } else {
        List<User> users = userService.findByLoginName(loginName);
        if (users != null && users.size() > 0) {
          msg = "该用户名已存在";
          response.setContentType("text/html;charset=utf-8");
          response.getWriter().print(
              "{\"success\": " + false + ", \"msg\": \"" + msg
              + "\"}");
        } else {
          msg = "该用户名可用";
          response.setContentType("text/html;charset=utf-8");
          response.getWriter()
          .print("{\"success\": " + true + ", \"msg\": \"" + msg
              + "\"}");
        }
      }
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			response.getWriter().close();
		}

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
	    List<DictionaryData> genders = DictionaryDataUtil.find(13);
	    //List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
	    //model.addAttribute("typeNames", typeNames);
	    model.addAttribute("genders", genders);
	    return "ses/bms/user/add";
	}

	/**
	 * Description: 保存新增信息
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param request
	 * @param model
	 * @param user
	 * @param roleId
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/save")
	public String save(@Valid User user, BindingResult result, String roleName, String orgName, HttpServletRequest request, Model model) throws NoSuchFieldException, SecurityException {
  		//校验字段
  		String origin = request.getParameter("origin");
  		String orgId = request.getParameter("org_orgId");
  		String deptTypeName = request.getParameter("deptTypeName");
		
	    if(result.hasErrors()){
	      List<DictionaryData> genders = DictionaryDataUtil.find(13);
//	    List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
//	    model.addAttribute("typeNames", typeNames);
        model.addAttribute("genders", genders);
  			model.addAttribute("user", user);
  			model.addAttribute("roleName", roleName);
  			model.addAttribute("orgName", orgName);
			
  			if (StringUtils.isNotBlank(origin)){
  			    addAtt(request, model);
  			}
  			return "ses/bms/user/add";
  		}
  		//校验用户名是否存在
  		List<User> users = userService.findByLoginName(user.getLoginName());
  		if(users.size() > 0){
  			model.addAttribute("user", user);
  			model.addAttribute("exist", "用户名已存在");
  			List<DictionaryData> genders = DictionaryDataUtil.find(13);
//        List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
//        model.addAttribute("typeNames", typeNames);
        model.addAttribute("genders", genders);
  			model.addAttribute("roleName", roleName);
  			model.addAttribute("orgName", orgName);
			
  			if (StringUtils.isNotBlank(origin)){
  			  addAtt(request, model);
        }
  			
  			return "ses/bms/user/add";
  		}
		//校验确认密码
		if (!user.getPassword().equals(user.getPassword2())){
			model.addAttribute("user", user);
			model.addAttribute("password2_msg", "两次输入密码不一致");
			List<DictionaryData> genders = DictionaryDataUtil.find(13);
//      List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
//      model.addAttribute("typeNames", typeNames);
      model.addAttribute("genders", genders);
			model.addAttribute("roleName", roleName);
			model.addAttribute("orgName", orgName);
			
			if (StringUtils.isNotBlank(origin)){
			  addAtt(request, model);
      }
			
			return "ses/bms/user/add";
		}
		User currUser = (User) request.getSession().getAttribute("loginUser");
		//机构
		if(user.getOrgId() != null && !"".equals(user.getOrgId())){
			if ("3".equals(user.getTypeName())) {
			  user.setOrgName(user.getOrgId());
      } else {
        Orgnization org = orgnizationService.getOrgByPrimaryKey(user.getOrgId());
        if (org != null){
          user.setOrg(org);
        }
      }
		}else{
			user.setOrg(null);
		}
		String PurTypeId = WfUtil.createUUID();
		user.setTypeId(PurTypeId);
		userService.save(user, currUser);
		if ("1".equals(user.getTypeName())) {
      //保存到采购人表
      purchaseServiceI.saveUser(user, PurTypeId);
    }

		if(user.getRoleId() != null && !"".equals(user.getRoleId())){
			String[] roleIds = user.getRoleId().split(",");
			for (int i = 0; i < roleIds.length; i++) {
				Userrole userrole = new Userrole();
				Role role = roleService.get(roleIds[i]);
				userrole.setRoleId(role);
				userrole.setUserId(user);
				//保存角色-用户关联信息
				userService.saveRelativity(userrole);
			}
			//保存用户与角色多对应权限的关联id
			/*List<String> mids = preMenuService.findByRids(roleIds);
			List<UserPreMenu> userPreMenus = new ArrayList<UserPreMenu>();
			for (String mid : mids) {
				UserPreMenu userPreMenu = new UserPreMenu();
				PreMenu menu = preMenuService.get(mid);
				userPreMenu.setPreMenu(menu);
				userPreMenu.setUser(user);
				userPreMenus.add(userPreMenu);
			}
			userService.saveUserMenuBatch(userPreMenus);*/
		}
		
		//不为空转到组织机构添加人员页面
		if (StringUtils.isNotBlank(origin)){
		    model.addAttribute("srcOrgId", orgId);
		    model.addAttribute("typeName", deptTypeName);
		    return "ses/oms/require_dep/list";
		} else {
	        return "redirect:list.html";
		}
	}
	
	/**
	 * 
	 *〈简述〉
	 * 添加属性
	 *〈详细描述〉
	 * @author myc
	 * @param request {@link HttpServletRequest}
	 * @param model {@link Model}
	 */
	private void addAtt(HttpServletRequest request ,Model model){
	    /*String personTypeId = request.getParameter("personTypeId");
        String personTypeName = request.getParameter("personTypeName");*/
        String origin = request.getParameter("origin");
        String orgId = request.getParameter("orgId");
        String deptTypeName = request.getParameter("deptTypeName");
        
/*        model.addAttribute("personTypeId", personTypeId);
        model.addAttribute("personTypeName", personTypeName);*/
        model.addAttribute("origin", origin);
        model.addAttribute("orgId", orgId);
        model.addAttribute("typeName", deptTypeName);
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
	public String edit(User u, Integer page, Model model, HttpServletRequest request) {
	    
	    String origin = request.getParameter("origin");
	    String userId = request.getParameter("userId");
	    
	    String typeName = request.getParameter("typeName");
	    List<User> users = null;
	    if (StringUtils.isNotBlank(origin) && StringUtils.isNotBlank(userId)){
	        User user = new User();
	        user.setId(userId);
	        users = userService.find(user);
	        model.addAttribute("origin", origin);
	    } else {
	        users = userService.find(u);
	    }
	    
		if (users != null && users.size() > 0) {
			User user = users.get(0);
			logger.info(JSON.toJSONStringWithDateFormat(user,
					"yyyy-MM-dd HH:mm:ss"));
			logger.info(JSON.toJSONStringWithDateFormat(user.getUser(),
					"yyyy-MM-dd HH:mm:ss"));
			List<Role> roles = roleService.find(null);
			String roleId = "";
			String roleName = "";
			List<Role> list = user.getRoles();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					if (i + 1 == list.size()) {
						roleId += list.get(i).getId();
						roleName += list.get(i).getName();
					} else {
						roleId += list.get(i).getId() + ",";
						roleName += list.get(i).getName() + ",";
					}
				}
			}
			List<DictionaryData> genders = DictionaryDataUtil.find(13);
			
			/*if (StringUtils.isNotBlank(origin)){
			   DictionaryData dd =  DictionaryDataUtil.findById(user.getTypeName());
			   if (dd != null){
			       model.addAttribute("personTypeId", dd.getId());
			       model.addAttribute("personTypeName", dd.getName());
			   }
			} else {
			    List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
	            model.addAttribute("typeNames", typeNames);
			}*/
	        model.addAttribute("genders", genders);
			model.addAttribute("roleName", roleName);
			model.addAttribute("roleId", roleId);
			model.addAttribute("roles", roles);
			if(user.getOrg() != null){
				model.addAttribute("orgId", user.getOrg().getId());
				model.addAttribute("orgName", user.getOrg().getName());
			}
			model.addAttribute("user", user);
			model.addAttribute("currPage", page);
		}
		
		  model.addAttribute("typeName", typeName);
		  return "ses/bms/user/edit";
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
	public String update(HttpServletRequest request, @Valid User u, BindingResult result, String roleId, String orgId, Model model) {
        
	    String origin = request.getParameter("origin");
	    String deptTypeName = request.getParameter("deptTypeName");
		//校验字段
		if(result.hasErrors()){
		    List<DictionaryData> genders = DictionaryDataUtil.find(13);
        List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
        model.addAttribute("typeNames", typeNames);
        model.addAttribute("genders", genders);
  			model.addAttribute("user", u);
  			model.addAttribute("orgId", u.getOrgId());
  			model.addAttribute("orgName", request.getParameter("orgName"));
  			model.addAttribute("roleId", u.getRoleId());
  			model.addAttribute("roleName", request.getParameter("roleName"));
  			model.addAttribute("currPage",request.getParameter("currpage"));
  			model.addAttribute("typeName", deptTypeName);
  			/*if (StringUtils.isNotBlank(origin)){
			      DictionaryData dd =  DictionaryDataUtil.findById(u.getTypeName());
                if (dd != null){
                   model.addAttribute("personTypeId", dd.getId());
                   model.addAttribute("personTypeName", dd.getName());
                }
            }*/
			
  			return "ses/bms/user/edit";
		}
	
		User temp = new User();
		temp.setId(u.getId());
		// 查询旧数据的关联关系
		List<User> users = userService.find(temp);
		if (users != null && users.size() > 0) {
			User olduser = users.get(0);
			List<Role> oldRole = olduser.getRoles();
			if(oldRole != null && oldRole.size() > 0){
				// 先删除之前的与角色的关联关系
				for (Role role : oldRole) {
					Userrole userrole = new Userrole();
					userrole.setUserId(olduser);
					userrole.setRoleId(role);
					roleService.deleteRoelUser(userrole);
				}
				
				/*//删除用户之前的与角色下权限菜单的关联关系
				String[] oldrIds = new String[oldRole.size()];
				for (int i = 0; i < oldRole.size(); i++) {
					oldrIds[i] = oldRole.get(i).getId();
				}
				List<String> oldmids = preMenuService.findByRids(oldrIds);
				List<UserPreMenu> ups = new ArrayList<UserPreMenu>();
				for (String mid : oldmids) {
					UserPreMenu userPreMenu = new UserPreMenu();
					PreMenu menu = preMenuService.get(mid);
					userPreMenu.setPreMenu(menu);
					userPreMenu.setUser(olduser);
					ups.add(userPreMenu);
				}
				userService.deleteUserMenuBatch(ups);*/
			}
			
			//机构
			if(orgId != null && !"".equals(orgId)){
				if ("3".equals(u.getTypeName())) {
				  u.setOrg(null);
	        u.setOrgName(orgId);
	      } else {
	        HashMap<String, Object> orgMap = new HashMap<String, Object>();
	        orgMap.put("id", orgId);
	        List<Orgnization> olist = orgnizationService.findOrgnizationList(orgMap);
	        if (olist != null && olist.size() > 0) {
	          u.setOrg(olist.get(0));
          }
	      }
			}else{
				u.setOrg(null);
			}
			
			//将采购机构用户修改为其他机构时，删除采购人表该用户对应数据
			if ("1".equals(olduser.getTypeName()) && !"1".equals(u.getTypeName())) {
			  //将采购人表该用户设为删除状态
        purchaseServiceI.busDelPurchase(olduser.getTypeId());
      }
			
			//将其他机构修改为采购机构用户时，新增采购人数据
      if (!"1".equals(olduser.getTypeName()) && "1".equals(u.getTypeName())) {
        String purTypeId = WfUtil.createUUID();
        u.setTypeId(purTypeId);
        purchaseServiceI.saveUser(u, purTypeId);
      }
      
      //修改采购人员
      if ("1".equals(olduser.getTypeName()) && "1".equals(u.getTypeName())) {
        //purchaseServiceI.updatePurchase(purchaseInfo);
      }
      
			u.setCreatedAt(olduser.getCreatedAt());
			u.setUser(olduser.getUser());
			u.setUpdatedAt(new Date());
			userService.update(u);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (users != null && users.size() > 0){
			  map.put("id", users.get(0).getTypeId());
			  List<PurchaseInfo> purchaseInfos = purchaseServiceI.findPurchaseList(map);
			  if (purchaseInfos != null && purchaseInfos.size() > 0) {
			    //修改采购人表的机构id
			    PurchaseInfo purchaseInfo = purchaseInfos.get(0);
			    purchaseInfo.setPurchaseDepId(orgId);
			    purchaseServiceI.update(purchaseInfo);
			  }
			}
			
			if(roleId != null && !"".equals(roleId)){
				String[] roleIds = roleId.split(",");
				for (int i = 0; i < roleIds.length; i++) {
					Userrole userrole = new Userrole();
					Role role = roleService.get(roleIds[i]);
					userrole.setRoleId(role);
					userrole.setUserId(u);
					userService.saveRelativity(userrole);
				}
				/*//保存用户与角色多对应权限的关联id
				List<String> mids = preMenuService.findByRids(roleIds);
				List<UserPreMenu> userPreMenus = new ArrayList<UserPreMenu>();
				for (String mid : mids) {
					UserPreMenu userPreMenu = new UserPreMenu();
					PreMenu menu = preMenuService.get(mid);
					userPreMenu.setPreMenu(menu);
					userPreMenu.setUser(u);
					userPreMenus.add(userPreMenu);
				}
				userService.saveUserMenuBatch(userPreMenus);*/
			}
		}
		
		if (StringUtils.isNotBlank(origin)){
		     model.addAttribute("srcOrgId", orgId);
		     model.addAttribute("typeName", deptTypeName);
            return "ses/oms/require_dep/list";
		} else {
		    String currpage = request.getParameter("currpage");
	        return "redirect:list.html?page="+currpage;
		}
	}

	/**
	 * Description: 删除用户信息，逻辑删除
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
			User user = new User();
			user.setId(str);
			//删除用户-角色关联
			Userrole userrole = new Userrole();
			userrole.setUserId(user);
			roleService.deleteRoelUser(userrole);
			//删除用户-权限关联
			UserPreMenu userPreMenu = new UserPreMenu();
			userPreMenu.setUser(user);
			userService.deleteUserMenu(userPreMenu);
			//修改用户为删除状态
			User u = userService.getUserById(str);
			userService.deleteByLogic(str);
			if ("1".equals(u.getTypeName())) {
        //同时将采购人表该用户设为删除状态
			  purchaseServiceI.busDelPurchase(u.getTypeId());
      }
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
	@RequestMapping("/show")
	public String show(Model model, User user) {
		List<User> ulist = userService.find(user);
		if (ulist != null && ulist.size() > 0) {
			User u = ulist.get(0);
			String roleName = "";
			List<Role> list = u.getRoles();
			for (int i = 0; i < list.size(); i++) {
				if (i + 1 == list.size()) {
					roleName += list.get(i).getName();
				} else {
					roleName += list.get(i).getName() + ",";
				}
			}
			List<DictionaryData> genders = DictionaryDataUtil.find(13);
      List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
			model.addAttribute("typeNames", typeNames);
	    model.addAttribute("genders", genders);
			model.addAttribute("roleName", roleName);
			model.addAttribute("user", u);
		} else {

		}
		model.addAttribute("flag", 1);
		return "ses/bms/user/view";
	}

	/**
	 * Description: 弹出权限分配页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param model
	 * @param id
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/openPreMenu")
	public String openPreMenu(Model model,String id){
		model.addAttribute("uid", id);
		return "ses/bms/user/add_menu";
	}
	
	/**
	 * Description: 保存用户-权限菜单关联
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-27
	 * @param request
	 * @param response
	 * @param userId
	 * @param ids
	 * @throws IOException
	 * @exception IOException
	 */
	@RequestMapping("/saveUserMenu")
	public void saveUserMenu(HttpServletRequest request, HttpServletResponse response, String userId, String ids) throws IOException {
		try {
		  User user = userService.getUserById(userId);
		  //先删除用户权限的增减量
		  UserPreMenu oldM = new UserPreMenu();
		  oldM.setUser(user);
		  userService.deleteUserMenu(oldM);
		  List<Role> roles = roleService.selectByUserId(userId);
		  List<UserPreMenu> userPreMenus = new ArrayList<UserPreMenu>();
		  if (roles != null && roles.size() > 0) {
		    String[] roleArry = new String[roles.size()];
		    for (int i = 0; i < roles.size(); i++) {
		      roleArry[i] = roles.get(i).getId();
		    }
		    //用户所属角色下的权限集合
		    List<String> rPreMenuIds = preMenuService.findByRids(roleArry);
		    if (ids != null && !"".equals(ids)) {
		      //用户勾选的权限数组
		      String[] mIds = ids.split(",");
		      //增加的权限
		      for (String str : mIds) {
		        if (!rPreMenuIds.contains(str)) {
		          //如果角色集合不包含勾选的权限,增加
		          UserPreMenu userPreMenu = new UserPreMenu();
		          PreMenu preMenu = preMenuService.get(str);
		          userPreMenu.setPreMenu(preMenu);
		          userPreMenu.setUser(user);
		          userPreMenu.setKind(0);
		          userPreMenus.add(userPreMenu);
		        }
		      }
		      //减少的权限
		      for (String rpId : rPreMenuIds) {
		        if (!ids.contains(rpId)) {
		          //如果角色权限有在勾选的权限中不存在的,减少
		          UserPreMenu userPreMenu = new UserPreMenu();
		          PreMenu preMenu = preMenuService.get(rpId);
		          userPreMenu.setPreMenu(preMenu);
		          userPreMenu.setUser(user);
		          userPreMenu.setKind(1);
		          userPreMenus.add(userPreMenu);
		        }
		      }
		    } else {
		      //如果全部不勾选,角色下权限全部是减量
		      for (String rpId : rPreMenuIds) {
		        UserPreMenu userPreMenu = new UserPreMenu();
		        PreMenu preMenu = preMenuService.get(rpId);
		        userPreMenu.setPreMenu(preMenu);
		        userPreMenu.setUser(user);
		        userPreMenu.setKind(1);
		        userPreMenus.add(userPreMenu);
		      }
		    }
      } else {
        String[] mIds = ids.split(",");
        for (String str : mIds) {
            //增加
            UserPreMenu userPreMenu = new UserPreMenu();
            PreMenu preMenu = preMenuService.get(str);
            userPreMenu.setPreMenu(preMenu);
            userPreMenu.setUser(user);
            userPreMenu.setKind(0);
            userPreMenus.add(userPreMenu);
        }
      }
		  //保存用户权限的个性化增减量
		  userService.saveUserMenuBatch(userPreMenus);
			/*UserPreMenu um = new UserPreMenu();
			um.setUser(user);
			userService.deleteUserMenu(um);
			if (ids != null && !"".equals(ids)) {
			    String[] mIds = ids.split(",");
			    List<UserPreMenu> userPreMenus = new ArrayList<UserPreMenu>();
			    for (String str : mIds) {
    			    UserPreMenu up = new UserPreMenu();
    			    PreMenu preMenu = preMenuService.get(str);
    			    up.setPreMenu(preMenu);
    			    up.setUser(user);
    			    userPreMenus.add(up);
    			}
			    userService.saveUserMenuBatch(userPreMenus);
			}*/
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print("权限配置完成");
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			response.getWriter().close();
		}

	}
	
	/**
	 * Description: 获取机构树
	 * 
	 * @author Ye MaoLin
	 * @version 2016-10-9
	 * @param request
	 * @param session
	 * @return
	 * @exception IOException
	 */
	@RequestMapping(value = "getOrgTree",produces={"application/json;charset=UTF-8"})
	@ResponseBody    
	public String getOrgTree(HttpServletRequest request, HttpSession session, String userId, String typeNameId ,String orgType){
		User user =null;
		if(userId != null && !"".equals(userId) ){
			user = userService.getUserById(userId);
		}
		/*String ddCode = dictionaryDataService.getDictionaryData(typeNameId).getCode();
		String typeName = "";
		if ("SUPERVISER_U".equals(ddCode) || "PUR_MG_U".equals(ddCode)) {
		    //采购管理部门
		    typeName = "2";
		}
		if ("NEED_U".equals(ddCode)) {
		    //所有机构
		    typeName = "";
		}
		if ("PURCHASER_U".equals(ddCode)) {
		    //采购机构
		    typeName = "1";
		}*/
		HashMap<String,Object> map = new HashMap<String,Object>();
    map.put("typeName", orgType);
    List<Orgnization> oList = orgnizationService.findOrgnizationList(map);
		List<Ztree> treeList = new ArrayList<Ztree>();  
		for(Orgnization o : oList){
			Ztree z = new Ztree();
			z.setId(o.getId());
			z.setName(o.getName());
			z.setpId(o.getParentId() == null ? "-1":o.getParentId());
			z.setLevel(o.getOrgLevel() + "");
			HashMap<String,Object> chimap = new HashMap<String,Object>();
			chimap.put("pid", o.getId());
			List<Orgnization> chiildList = orgnizationService.findOrgnizationList(chimap);
			if(chiildList != null && chiildList.size() > 0){
				z.setIsParent("true");
				//z.setNocheck(true);
			} else {
				z.setIsParent("false");
			}
			if(user != null ){
				Orgnization orgnization = user.getOrg();
				if(orgnization != null){
					if(o.getId().equals(orgnization.getId())){
						z.setChecked(true);
					}
				}
			} 
			//z.setIsParent(o.getParentId()==null?"true":"false");
			treeList.add(z);
		}
		JSONArray jObject = JSONArray.fromObject(treeList);
		return jObject.toString();
	}

	/**
   * Description: 校验原密码输入是否正确
   * 
   * @author Ye MaoLin
	 * @param response
	 * @param u
	 * @throws IOException
	 */
	@RequestMapping("/ajaxOldPassword")
  public void ajaxOldPassword(HttpServletResponse response, User u) throws IOException{
	    try {
	      String msg = "";
        if (u.getPassword() == null || "".equals(u.getPassword())) {
            msg = "请输入原密码";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
        } else {
            Boolean result = userService.ajaxOldPassword(u);
            if (result) {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print("{\"success\": " + true + ", \"msg\": \"" + msg + "\"}");
            } else {
                msg = "原密码输入有误";
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
            }
        }
	      response.getWriter().flush();
      } catch (Exception e) {
          e.printStackTrace();
      } finally{
          response.getWriter().close();
      }
	  
	}
	
	/**
	 *〈简述〉重置密码
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param response
	 * @param user
	 * @throws IOException 
	 */
	@RequestMapping("/resetPwd")
	public void resetPwd(HttpServletResponse response, User u) throws IOException{
	    try{
	        int count = 0;
	        String msg = "";
	        String pwd2 = u.getPassword2();
	        String pwd = u.getPassword();
	        if (pwd == null || "".equals(pwd)) {
	            msg = "请输入密码";
	            count ++;
            }
	        if (pwd2 == null || "".equals(pwd2)) {
                if (count > 0) {
                    msg = "请输入密码和确认密码";
                    count ++;
                } else {
                    msg = "请输入确认密码";
                    count ++;
                }
            }
	        if (count > 0) {
	            response.setContentType("text/html;charset=utf-8");
	            response.getWriter()
	                    .print("{\"success\": " + false + ", \"msg\": \"" + msg
	                            + "\"}");
            }
	        if (count == 0) {
                if (!pwd.equals(pwd2)) {
                    msg = "两次密码不一致";
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter()
                            .print("{\"success\": " + false + ", \"msg\": \"" + msg
                                    + "\"}");
                } else {
                    userService.resetPwd(u);
                    msg = "重置密码成功";
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter()
                            .print("{\"success\": " + true + ", \"msg\": \"" + msg
                                    + "\"}");
                }
            }
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            response.getWriter().close();
        }
	}
	
	/**
   *〈简述〉校验手机号重复
   *〈详细描述〉
   * @author Ye MaoLin
	 * @param mobile
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/ajaxMoblie")
  public void ajaxMoblie(String mobile, String id, HttpServletResponse response) throws IOException {
      try {
        String msg = "";
        if (mobile == null || "".equals(mobile)) {
          msg = "手机号不能为空";
          response.setContentType("text/html;charset=utf-8");
          response.getWriter().print(
              "{\"success\": " + false + ", \"msg\": \"" + msg
              + "\"}");
        } else {
          Boolean result = userService.ajaxMoblie(mobile, id);
          if (!result) {
            msg = "该手机号已注册";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(
                "{\"success\": " + false + ", \"msg\": \"" + msg
                + "\"}");
          } else {
            msg = "该手机号可用";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
            .print("{\"success\": " + true + ", \"msg\": \"" + msg
                + "\"}");
          }
        }
        response.getWriter().flush();
      } catch (Exception e) {
        e.printStackTrace();
      } finally{
        response.getWriter().close();
      }
  }
	
	/**
   *〈简述〉校验身份证号重复
   *〈详细描述〉
   * @author Ye MaoLin
	 * @param idNumber
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/ajaxIdNumber")
  public void ajaxIdNumber(String idNumber, String id, HttpServletResponse response) throws IOException {
      try {
        String msg = "";
        if (idNumber != null && !"".equals(idNumber)) {
          Boolean result = userService.ajaxIdNumber(idNumber, id);
          if (!result) {
            msg = "该身份证号已注册";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(
                "{\"success\": " + false + ", \"msg\": \"" + msg
                + "\"}");
          } else {
            msg = "该身份证号可用";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
            .print("{\"success\": " + true + ", \"msg\": \"" + msg
                + "\"}");
          }
        }
        response.getWriter().flush();
      } catch (Exception e) {
        e.printStackTrace();
      } finally{
        response.getWriter().close();
      }
  }
	
	@RequestMapping("/listByRole")
	public String listByRole(Model model, User user, String rId, Integer page){
  	  if (rId != null && !"".equals(rId)) {
  	    user.setRoleId(rId);
  	    List<String> rIds = new ArrayList<String>();
  	    rIds.add(rId);
        user.setRoleIdList(rIds);
      }
      List<User> users = userService.findUserRole(user, page == null ? 1 : page);
      Role role = new Role();
      List<Role> roles = roleService.find(role);
      for (User u : users) {
        List<Role> roles2 = roleService.selectByUserId(u.getId());
        u.setRoles(roles2);
      }
      model.addAttribute("roles", roles);
      model.addAttribute("list", new PageInfo<User>(users));
      model.addAttribute("user", user);
      model.addAttribute("rid", rId);
      return "ses/bms/role/user_list";
	}
	
	/**
   *〈简述〉重置密码页面
   *〈详细描述〉
   * @author Ye MaoLin
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/resetPassword")
   public String resetPassword(HttpServletRequest request, Model model) {
       User user = (User) request.getSession().getAttribute("loginUser");
       model.addAttribute("user", user);
       return "ses/bms/user/reset_password";
   }
	
	/**
   *〈简述〉查看个人信息
   *〈详细描述〉
	 * @param u
	 * @param model
	 * @return
	 */
	@RequestMapping("/personalInfo")
	public String personalInfo(@CurrentUser User u, Model model){
    if (u != null) {
      String roleName = "";
      List<Role> list = u.getRoles();
      for (int i = 0; i < list.size(); i++) {
        if (i + 1 == list.size()) {
          roleName += list.get(i).getName();
        } else {
          roleName += list.get(i).getName() + ",";
        }
      }
      List<DictionaryData> genders = DictionaryDataUtil.find(13);
      List<DictionaryData> typeNames = DictionaryDataUtil.find(7);
      model.addAttribute("typeNames", typeNames);
      model.addAttribute("genders", genders);
      model.addAttribute("roleName", roleName);
      model.addAttribute("user", u);
      model.addAttribute("flag", 0);
    } 
    return "ses/bms/user/view";
	}
}
