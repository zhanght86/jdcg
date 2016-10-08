package iss.controller.fs;

import iss.model.fs.Park;
import iss.model.fs.Post;
import iss.model.fs.Reply;
import iss.model.fs.Topic;
import iss.service.fs.ParkService;
import iss.service.fs.PostService;
import iss.service.fs.ReplyService;
import iss.service.fs.TopicService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.bms.UserPreMenu;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;

import com.github.pagehelper.PageInfo;


/**
* @Title:ParkManageController 
* @Description: 版块管理控制类
* @author Peng Zhongjun
* @date 2016-9-7下午6:21:30
 */
@Controller
@Scope("prototype")
@RequestMapping("/park")
public class ParkManageController {

	@Autowired
	private ParkService parkService;
	@Autowired
	private PostService postService;
	@Autowired
	private TopicService topicService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private UserServiceI userService;
	@Autowired
	private RoleServiceI roleService;
	@Autowired
	private PreMenuServiceI preMenuService;
	

	/**
	 * @Title: getParkList
	 * @author Peng Zhongjun
	 * @date 2016-8-10 下午19:47:32
	 * @Description: 获取版块列表跳转到后台管理
	 * @param @param model
	 * @param @param park
	 * @return String
	 */
	@RequestMapping("/getlist")
	public String getParkList(Model model, Park park,Integer page) {
		List<Park> parklist = parkService.queryByList(park,page==null?1:page);
		for (Park park2 : parklist) {
			Topic topic = new Topic();
			topic.setPark(park2);
			BigDecimal topiccount = topicService.queryByCount(topic);
			Post post = new Post();
			post.setPark(park2);
			BigDecimal postcount = postService.queryByCount(post);
			BigDecimal replycount = replyService.queryCountByParkId(park2.getId());
			park2.setTopiccount(topiccount);
			park2.setPostcount(postcount);
			park2.setReplycount(replycount);
		}	
		model.addAttribute("list", new PageInfo<Park>(parklist));
		return "iss/forum/park/list";
	}

	/**
	 * @Title: view
	 * @author Peng Zhongjun
	 * @date 2016-8-10 下午19:55:32
	 * @Description: 显示版块详细信息页面
	 * @param @param model
	 * @param @param park
	 * @return String
	 */
	@RequestMapping("/view")
	public String view(Model model, String id) {
		Park p = parkService.selectByPrimaryKey(id);
		Topic topic = new Topic();
		topic.setPark(p);
		BigDecimal topiccount = topicService.queryByCount(topic);
		Post post = new Post();
		post.setPark(p);
		BigDecimal postcount = postService.queryByCount(post);
		BigDecimal replycount = replyService.queryCountByParkId(p.getId());
		p.setTopiccount(topiccount);
		p.setPostcount(postcount);
		p.setReplycount(replycount);
		model.addAttribute("park", p);
		return "iss/forum/park/view";
	}

	/**
	 * @Title: add
	 * @author Peng Zhongjun
	 * @date 2016-8-10 下午19:58:43
	 * @Description: 跳转新增编辑页面
	 * @param @param request
	 * @return String
	 */
	@RequestMapping("/add")
	public String add(Model model, HttpServletRequest request) {
		List<User> users = userService.find(null);
		model.addAttribute("users", users);
		return "iss/forum/park/add";
	}

	/**
	 * @Title: save
	 * @author Peng Zhongjun
	 * @date 2016-8-10 下午20:03:41
	 * @Description: 保存新增信息
	 * @param @param request
	 * @param @param park
	 * @return String
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, Park park) {
		User user = new User();
		user = userService.getUserById(request.getParameter("userId"));
		//设置权限
		Role role = roleService.get("018375864F3C403CAC7698C2549763F0");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		user.setRoles(roles);
		UserPreMenu um = new UserPreMenu();
		um.setUser(user);
		userService.deleteUserMenu(um);
		String ids ="C58C30A33C4A4AB49B125589267BE64B,0298F628AB6C4018A0B43561993A43DE,DDA573A2CCA54DF29E4B8BCCDFAF80DA,8715A14AB3F74D77AF85C443386023F3,3DFF3C15462047A185B6173348BE7839,4AE68DC483454C298D9330A9976159F3";
		String[] mIds = ids.split(",");
		for (String str : mIds) {
			UserPreMenu up = new UserPreMenu();
			PreMenu preMenu = preMenuService.get(str);
			up.setPreMenu(preMenu);
			up.setUser(user);
			userService.saveUserMenu(up);
		}
		park.setUser(user);
		
		User creater = (User) request.getSession().getAttribute("loginUser");
		park.setCreater(creater);
		Timestamp ts = new Timestamp(new Date().getTime());
		park.setCreatedAt(ts);
		Timestamp ts1 = new Timestamp(new Date().getTime());
		park.setUpdatedAt(ts1);
		parkService.insertSelective(park);
		return "redirect:getlist.html";
	}

	/**
	 * @Title: edit
	 * @author Peng Zhongjun
	 * @date 2016-8-10 下午20:03:41
	 * @Description: 跳转修改编辑页面
	 * @param @param id
	 * @param @param model
	 * @return String
	 */
	@RequestMapping("/edit")
	public String edit(String id, Model model) {
		Park p = parkService.selectByPrimaryKey(id);
		System.out.println(p.getUser());
		model.addAttribute("park", p);
		List<User> users = userService.find(null);
		model.addAttribute("users", users);
		return "iss/forum/park/edit";
	}

	/**
	 * @Title: update
	 * @author Peng Zhongjun
	 * @date 2016-8-10 下午20:03:41
	 * @Description: 更新修改信息
	 * @param @param request
	 * @param @param park
	 * @return String
	 */
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Park park) {	
		String oldUserId = request.getParameter("oldUserId");
		if( oldUserId != null && oldUserId !=""){
			User oldUser = userService.getUserById(oldUserId);
			UserPreMenu um = new UserPreMenu();
			um.setUser(oldUser);
			userService.deleteUserMenu(um);
		}
		User user = userService.getUserById(request.getParameter("userId"));
		//菜单
		String ids ="C58C30A33C4A4AB49B125589267BE64B,0298F628AB6C4018A0B43561993A43DE,DDA573A2CCA54DF29E4B8BCCDFAF80DA,8715A14AB3F74D77AF85C443386023F3,3DFF3C15462047A185B6173348BE7839,4AE68DC483454C298D9330A9976159F3";
		String[] mIds = ids.split(",");
		for (String str : mIds) {
			UserPreMenu up = new UserPreMenu();
			PreMenu preMenu = preMenuService.get(str);
			up.setPreMenu(preMenu);
			up.setUser(user);
			userService.saveUserMenu(up);
		}			
		//角色
		Role role = roleService.get("018375864F3C403CAC7698C2549763F0");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		user.setRoles(roles);	
		park.setUser(user);
		
		Timestamp ts = new Timestamp(new Date().getTime());
		park.setUpdatedAt(ts);		
		String parkId = request.getParameter("parkId");
		park.setId(parkId);
		parkService.updateByPrimaryKeySelective(park);
		return "redirect:getlist.html";
	}

	/**
	 * @Title: delete
	 * @author Peng Zhongjun
	 * @date 2016-8-10 下午20:03:41
	 * @Description: 删除版块信息
	 * @param @param id
	 * @return String
	 */
	@RequestMapping("/delete")
	public String delete(String ids) {	
		String[] id=ids.split(",");
		for (String str : id) {
			parkService.deleteByPrimaryKey(str);
			List<Topic> topics = topicService.selectByParkID(str);
			for (Topic topic : topics) {
				topicService.deleteByPrimaryKey(topic.getId());
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("parkId", str);
			List<Post> posts = postService.queryByList(map); 
			for (Post post : posts) {
				postService.deleteByPrimaryKey(post.getId());
				Map<String,Object> map1 = new HashMap<String, Object>();
				map1.put("postId", post.getId());
				List<Reply> replies = replyService.selectByPostID(map1);
				for (Reply reply : replies) {
					replyService.deleteByPrimaryKey(reply.getId());
				}
			}
		}
		return "redirect:getlist.html";
	}

	/**
	 * @Title: getPark
	 * @author Peng Zhongjun
	 * @date 2016-8-24 下午13:41:32
	 * @Description: 获取版块列表跳转到前台
	 * @param @param model
	 * @param @param park
	 * @return String
	 */
	@RequestMapping("/getIndex")
	public String getPostIndex(Model model) {
		Map<String, Object> forumIndexMapper = new HashMap<String, Object>();
		List<Park> parklist = parkService.getAll(null);
		for (Park park : parklist) {			
			forumIndexMapper.put("select"+park.getId()+"Park", park);	
			List<Post> posts = postService.selectByParkID(park.getId());
			park.setPosts(posts);
		}
		List<Post> hotPostList = postService.queryHotPost();
		//List<Topic> topicList = topicService.selectByParkID();
		model.addAttribute("forumIndexMapper", forumIndexMapper);
		model.addAttribute("hotPostList", hotPostList);
		model.addAttribute("list", parklist);
		return "iss/forum/forum_Index";
	}
	
}
