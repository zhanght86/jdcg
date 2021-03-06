package bss.controller.ppms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageInfo;

import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.prms.FirstAudit;
import bss.model.prms.FirstAuditTemitem;
import bss.model.prms.FirstAuditTemplat;
import bss.model.prms.PackageFirstAudit;
import bss.service.ppms.AdvancedDetailService;
import bss.service.ppms.AdvancedPackageService;
import bss.service.ppms.AdvancedProjectService;
import bss.service.prms.FirstAuditService;
import bss.service.prms.FirstAuditTemitemService;
import bss.service.prms.FirstAuditTemplatService;
import bss.service.prms.PackageFirstAuditService;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.util.DictionaryDataUtil;

@Controller
@RequestMapping("/adFirstAudit")
public class AdFirstAuditController {
	@Autowired
	private FirstAuditService service;
	@Autowired
	private AdvancedDetailService detailService;
	@Autowired
	private AdvancedPackageService packageService;
	@Autowired
	private AdvancedProjectService projectService;
	@Autowired
	private FirstAuditTemplatService templatService;
	@Autowired
	private PackageFirstAuditService packageFirstAuditService;
	@Autowired
    private FirstAuditTemplatService firstAuditTemplatService;//符合性审查模板
    @Autowired
    private FirstAuditTemitemService firstAuditTemitemService;//符合性审查模板评审项
	/**
	 * 
	  * @Title: toAdd
	  * @author ShaoYangYang
	  * @date 2016年10月9日 上午11:10:20  
	  * @Description: TODO 跳转到初审项定义页面
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toAdd")
	public String toAdd(String projectId, Model model, String msg){
		try {
		  AdvancedProject project = projectService.selectById(projectId);
		  HashMap<String, Object> map = new HashMap<String, Object>();
		  map.put("projectId", projectId);
      List<AdvancedPackages> packages = packageService.selectByAll(map);
      //查询项目下所有的符合性审查项
      List<FirstAudit> firstAudits = service.getListByProjectId(projectId);
      model.addAttribute("packages", packages);
      List<DictionaryData> dds = DictionaryDataUtil.find(22);
      //符合性资格性审查项类型
      model.addAttribute("dds", dds);
      List<DictionaryData> purchaseTypes = DictionaryDataUtil.find(5);
      model.addAttribute("purchaseTypes", purchaseTypes);
      model.addAttribute("firstAudits", firstAudits);
			model.addAttribute("projectId", projectId);
			model.addAttribute("project", project);
			model.addAttribute("msg", msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bss/ppms/advanced_project/advanced_bid_file/bid_file";
	}
	
	/**
	 * 
	  * @Title: toPackageFirstAudit
	  * @author ShaoYangYang
	  * @date 2016年10月14日 下午6:13:08  
	  * @Description: TODO 去往分包选择页面
	  * @param @param projectId
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toPackageFirstAudit")
	public String toPackageFirstAudit(String projectId, String flag, Model model){
		try {
			//项目分包信息
			HashMap<String,Object> pack = new HashMap<String,Object>();
			pack.put("projectId", projectId);
			List<AdvancedPackages> packages = packageService.selectByAll(pack);
			Map<String,Object> list = new HashMap<String,Object>();
			//关联表集合
			List<PackageFirstAudit> idList = new ArrayList<>();
			Map<String,Object> mapSearch = new HashMap<String,Object>(); 
			for(AdvancedPackages ps:packages){
				list.put("pack"+ps.getId(),ps);
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("packageId", ps.getId());
				List<AdvancedDetail> detailList = detailService.selectByAll(map);
				ps.setAdvancedDetails(detailList);
				//设置查询条件
				mapSearch.put("projectId", projectId);
				mapSearch.put("packageId", ps.getId());
				List<PackageFirstAudit> selectList = packageFirstAuditService.selectList(mapSearch);
				idList.addAll(selectList);
			}
			model.addAttribute("idList",idList);
			model.addAttribute("packageList", packages);
			AdvancedProject project = projectService.selectById(projectId);
			model.addAttribute("project", project);
			//初审项信息
			List<FirstAudit> list2 = service.getListByProjectId(projectId);
			model.addAttribute("list", list2);
			model.addAttribute("projectId", projectId);
			model.addAttribute("flag", flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bss/ppms/advanced_project/advanced_bid_file/package_first_audit";
	}
	
	/**
	 * 
	  * @Title: add
	  * @author ShaoYangYang
	  * @date 2016年10月9日 下午1:49:47  
	  * @Description: TODO 新增初审项定义
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("add")
	public String add(FirstAudit firstAudit,Model model,RedirectAttributes attr){
		service.add(firstAudit);
		attr.addAttribute("projectId", firstAudit.getProjectId());
		return "redirect:toAdd.html";
	}
	/**
	 * 
	  * @Title: remove
	  * @author ShaoYangYang
	  * @date 2016年10月9日 下午4:38:47  
	  * @Description: TODO 删除
	  * @param @param id
	  * @param @param attr
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("remove")
	@ResponseBody
	public void remove(String id,RedirectAttributes attr){
		String[] ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			service.delete(ids[i]);
		}
	}
	/**
	 * 
	  * @Title: toEdit
	  * @author ShaoYangYang
	  * @date 2016年10月9日 下午4:56:24  
	  * @Description: TODO 跳转到修改页面
	  * @param @param id
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("toEdit")
	public String toEdit(String id,Model model){
		FirstAudit firstAudit = service.get(id);
		model.addAttribute("firstAudit", firstAudit);
		return "bss/advanced_project/advanced_bid_file/edit";
	}
	/**
	 * 
	  * @Title: edit
	  * @author ShaoYangYang
	  * @date 2016年10月9日 下午4:59:39  
	  * @Description: TODO 修改
	  * @param @param firstAudit
	  * @param @param attr
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("edit")
	public String edit(FirstAudit firstAudit,RedirectAttributes attr){
		service.update(firstAudit);
		attr.addAttribute("projectId", firstAudit.getProjectId());
		return "redirect:toAdd.html";
	}
	/**
	 * 
	  * @Title: list
	  * @author ShaoYangYang
	  * @date 2016年10月12日 上午10:17:21  
	  * @Description: TODO 打开模板列表
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("toTemplatList")
	public String toTemplatList(String name,Integer page,Model model,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loginUser");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("userId", user.getId());
		List<FirstAuditTemplat> list = templatService.selectAllTemplat(map,page==null?1:page);
		model.addAttribute("list", new PageInfo<>(list));
		model.addAttribute("name", name);
		return "bss/ppms/templat/window_list";
	}
	/**
	 * 
	  * @Title: relate
	  * @author ShaoYangYang
	  * @date 2016年10月12日 下午5:32:26  
	  * @Description: TODO 关联模板
	  * @param       
	  * @return void
	 */
	@RequestMapping("relate")
	@ResponseBody
	public void relate(String id,String projectId){
		templatService.relate(id, projectId);
	}
	
	
	/**
	 *〈简述〉编辑包的符合性审查项
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param packageId 包id
	 * @param model
	 * @return
	 */
	@RequestMapping("/editPackageFirstAudit")
	public String editPackageFirstAudit(String packageId, Model model, String projectId, String flag){	  
	  List<DictionaryData> dds = DictionaryDataUtil.find(22);
    //符合性审查项
	  FirstAudit firstAudit1 = new FirstAudit();
	  firstAudit1.setKind(DictionaryDataUtil.getId("COMPLIANCE"));
	  firstAudit1.setPackageId(packageId);
	  List<FirstAudit> items1 = service.findBykind(firstAudit1);
    //资格性审查项
	  FirstAudit firstAudit2 = new FirstAudit();
    firstAudit2.setKind(DictionaryDataUtil.getId("QUALIFICATION"));
    firstAudit2.setPackageId(packageId);
    List<FirstAudit> items2 = service.findBykind(firstAudit2);
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("id", packageId);
    List<AdvancedPackages> packages = packageService.selectByAll(map);
    if (packages != null) {
      model.addAttribute("packages", packages.get(0));
    }
    HashMap<String, Object> map2 = new HashMap<String, Object>();
    map2.put("kind", DictionaryDataUtil.getId("REVIEW_QC"));
    //获取资格性和符合性审查模版
    List<FirstAuditTemplat> firstAuditTemplats = firstAuditTemplatService.find(map2);
    model.addAttribute("dds", dds);
    model.addAttribute("items1", items1);
    model.addAttribute("items2", items2);
    model.addAttribute("packageId", packageId);
    model.addAttribute("projectId", projectId);
    model.addAttribute("firstAuditTemplats", firstAuditTemplats);
    model.addAttribute("flag", flag);
	  return "bss/ppms/advanced_project/advanced_bid_file/edit_package_qc";
	}
	
	/**
	 *〈简述〉保存符合性审查项
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param response
	 * @param firstAudit 符合性审查项封装实体
	 * @throws IOException
	 */
	@RequestMapping("/savePackageFirstAudit")
	public void savePackageFirstAudit(HttpServletResponse response, FirstAudit firstAudit) throws IOException{
	  try {
      int count = 0;
      String msg = "";
      if (firstAudit.getName() == null || "".equals(firstAudit.getName())) {
        msg += "请输入评审项名称";
        count ++;
      }
      if (firstAudit.getPosition()== null) {
        if (count > 0) {
          msg += "、序号";
        } else {
          msg += "请输入排序号";
        }
        count ++;
      }
      if (firstAudit.getContent()== null || "".equals(firstAudit.getContent())) {
        if (count > 0) {
          msg += "和评审内容";
        } else {
          msg += "请输入评审内容";
        }
        count ++;
      }
      if (count > 0) {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter()
                .print("{\"success\": " + false + ", \"msg\": \"" + msg+ "\"}");
      }
      if (count == 0) {
        msg += "添加成功";
        service.add(firstAudit);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter()
                .print("{\"success\": " + true + ", \"msg\": \"" + msg+ "\"}");
      }
      response.getWriter().flush();
    } catch (Exception e) {
        e.printStackTrace();
    } finally{
        response.getWriter().close();
    }
	  
	}
	
	/**
   *〈简述〉弹出编辑符合性评审项页面
   *〈详细描述〉
   * @author Ye MaoLin
   * @return
   */
  @RequestMapping("/editItem")
  public String editItem(String id, Model model){
    FirstAudit firstAudit = service.get(id);
    model.addAttribute("item", firstAudit);
    return "bss/ppms/advanced_project/advanced_bid_file/qc_edit_item";
  }
  
  /**
   *〈简述〉更新符合性评审项
   *〈详细描述〉
   * @author Ye MaoLin
   * @param response
   * @param firstAuditTemitem
   * @throws IOException
   */
  @RequestMapping("/updateItem")
  public void updateItem(HttpServletResponse response, FirstAudit firstAudit) throws IOException{
    try {
      int count = 0;
      String msg = "";
      if (firstAudit.getName() == null || "".equals(firstAudit.getName())) {
        msg += "请输入评审项名称";
        count ++;
      }
      if (firstAudit.getPosition()== null) {
        if (count > 0) {
          msg += "、序号";
        } else {
          msg += "请输入排序号";
        }
        count ++;
      }
      if (firstAudit.getContent()== null || "".equals(firstAudit.getContent())) {
        if (count > 0) {
          msg += "和评审内容";
        } else {
          msg += "请输入评审内容";
        }
        count ++;
      }
      if (count > 0) {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter()
                .print("{\"success\": " + false + ", \"msg\": \"" + msg+ "\"}");
      }
      if (count == 0) {
        msg += "更新成功";
        service.update(firstAudit);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter()
                .print("{\"success\": " + true + ", \"msg\": \"" + msg+ "\"}");
        
      }
      response.getWriter().flush();
    } catch (Exception e) {
        e.printStackTrace();
    } finally{
        response.getWriter().close();
    }
  }
  
  /**
   *〈简述〉删除符合性评审项
   *〈详细描述〉
   * @author Ye MaoLin
   * @param response
   * @param id 
   * @throws IOException
   */
  @RequestMapping("/delItem")
  public void delItem(HttpServletResponse response, String id) throws IOException{
    try {
      service.delete(id);
      String msg = "删除成功";
      response.setContentType("text/html;charset=utf-8");
      response.getWriter()
              .print("{\"success\": " + true + ", \"msg\": \"" + msg+ "\"}");
      response.getWriter().flush();
    } catch (Exception e) {
        e.printStackTrace();
    } finally{
        response.getWriter().close();
    }
  }
	
  /**
   *〈简述〉引入模板数据
   *〈详细描述〉
   * @author Ye MaoLin
   * @param response
   * @param id 模板id
   * @param projectId 项目id
   * @param packageId 包id
   * @throws IOException 
   */
  @RequestMapping("/loadTemplat")
  public void loadTemplat(HttpServletResponse response, String id, String projectId, String packageId) throws IOException{
    try{
      FirstAudit record = new FirstAudit();
      record.setPackageId(packageId);
      record.setProjectId(projectId);
      List<FirstAudit> firstAudits = service.findBykind(record);
      //先删除数据
      for (FirstAudit firstAudit : firstAudits) {
        service.delete(firstAudit.getId());
      }
      List<FirstAuditTemitem> items = firstAuditTemitemService.selectByTemplatId(id);
      for (FirstAuditTemitem firstAuditTemitem : items) {
        FirstAudit firstAudit = new FirstAudit();
        firstAudit.setContent(firstAuditTemitem.getContent());
        firstAudit.setKind(firstAuditTemitem.getKind());
        firstAudit.setName(firstAuditTemitem.getName());
        firstAudit.setPackageId(packageId);
        firstAudit.setPosition(firstAuditTemitem.getPosition());
        firstAudit.setProjectId(projectId);
        //保存导入模板数据
        service.add(firstAudit);
      }
      String msg = "引入成功";
      response.setContentType("text/html;charset=utf-8");
      response.getWriter()
              .print("{\"success\": " + true + ", \"msg\": \"" + msg+ "\"}");
      response.getWriter().flush();
    } catch (Exception e) {
        e.printStackTrace();
    } finally{
        response.getWriter().close();
    }
  }
  
  
  /**
   *〈简述〉加载其他项目包的评审项
   *〈详细描述〉
   * @author Ye MaoLin
   * @param model
   * @param page页码
   * @param packages
   * @return
   */
  @RequestMapping("/loadOtherPackage")
  public String loadOtherPackage(Model model, Integer page, AdvancedPackages packages, String oldPackageId, String oldProjectId){
      List<AdvancedPackages> list = packageService.find(packages, page == null ? 1 : page);
      model.addAttribute("list", new PageInfo<AdvancedPackages>(list));
      model.addAttribute("packages", packages);
      model.addAttribute("oldPackageId", oldPackageId);
      model.addAttribute("oldProjectId", oldProjectId);
      return "bss/ppms/advanced_project/advanced_bid_file/load_other";
  }
  
  @RequestMapping("/saveLoadPackage")
  public void saveLoadPackage(HttpServletResponse response, String id, String packageId, String projectId) throws IOException{
    try{
      FirstAudit record = new FirstAudit();
      record.setPackageId(packageId);
      record.setProjectId(projectId);
      List<FirstAudit> firstAudits = service.findBykind(record);
      //先删除数据
      for (FirstAudit firstAudit : firstAudits) {
        service.delete(firstAudit.getId());
      }
      FirstAudit record2 = new FirstAudit();
      record2.setPackageId(id);
      //获取引入数据评审项
      List<FirstAudit> firstAudits2 = service.findBykind(record2);
      for (FirstAudit fa : firstAudits2) {
        FirstAudit firstAudit = new FirstAudit();
        firstAudit.setContent(fa.getContent());
        firstAudit.setKind(fa.getKind());
        firstAudit.setName(fa.getName());
        firstAudit.setPackageId(packageId);
        firstAudit.setPosition(fa.getPosition());
        firstAudit.setProjectId(projectId);
        //保存导入模板数据
        service.add(firstAudit);
      }
      String msg = "引入成功";
      response.setContentType("text/html;charset=utf-8");
      response.getWriter()
              .print("{\"success\": " + true + ", \"msg\": \"" + msg+ "\"}");
      response.getWriter().flush();
    } catch (Exception e) {
        e.printStackTrace();
    } finally{
        response.getWriter().close();
    }
    
  }
}
