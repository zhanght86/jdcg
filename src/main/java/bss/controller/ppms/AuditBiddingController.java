package bss.controller.ppms;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;
import ses.service.bms.TodosService;
import ses.service.bms.UserServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;
import bss.model.ppms.Project;
import bss.model.ppms.Reason;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.ProjectService;
import bss.util.PropUtil;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>招标文件审核
 * <详细描述>
 * @author   Wang Wenshuai
 * @version  
 * @since
 * @see
 */
@Controller 
@RequestMapping("/Auditbidding")
public class AuditBiddingController extends BaseController {

  /** SCCUESS */
  private static final String SUCCESS = "SUCCESS";
  /** ERROR */
  private static final String ERROR = "ERROR";

  /**
   * 项目信息
   */
  @Autowired
  ProjectService projectService;

  /**
   * 流程
   */
  @Autowired
  FlowMangeService flowMangeService;
  /**
   * 用户
   */
  @Autowired
  private UserServiceI userService;

  /**
   * 待办
   */
  @Autowired
  private TodosService todosService;

  @Autowired
  private PurchaseOrgnizationServiceI purchaseOrgnizationService;
  /**
   * 
   *〈简述〉返回待审核的项目信息
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  @RequestMapping("/list")
  public String list(@CurrentUser User user,Integer page,Model model,Project project){
    //采购机构信息
    PurchaseDep purchaseDep = purchaseOrgnizationService.selectByOrgId(user.getOrg().getId());
    project.setStatusArray(new String[]{DictionaryDataUtil.getId("ZBWJYTJ"),DictionaryDataUtil.getId("NZPFBZ"),DictionaryDataUtil.getId("ZBWJYTG")});
    //拿到当前的采购机构获取到组织机构
    List<PurchaseOrg> listOrg = purchaseOrgnizationService.getOrg(purchaseDep.getOrgId());
    String org = "";
    for (PurchaseOrg purchaseOrg : listOrg) {
      org += "'"+purchaseOrg.getPurchaseDepId() + "',";
    }
    if(!"".equals(org)){
      project.setPurchaseDepId(org.substring(0, org.length()-1));
    }else{
      project.setPurchaseDepId("'123456'");
    }
    project.setPrincipal(user.getId());
    model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
    model.addAttribute("status", DictionaryDataUtil.find(2));//获取数据字典数据
    List<Project> list = projectService.list(page == null || "".equals(page) ? 1 : page, project);
    for (int i=0, k = list.size(); i < k; i++) {
      try {
        User contractor = userService.getUserById(list.get(i).getPrincipal());
        list.get(i).setProjectContractor(contractor.getRelName());
      } catch (Exception e) {
        list.get(i).setProjectContractor("");
      }
    }
    model.addAttribute("confirmFile", project.getConfirmFile());
    model.addAttribute("list", new PageInfo<Project>(list));
    return "bss/ppms/audit_bidding/list";
  } 

  /**
   * 
   *〈简述〉审核状态修改
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   * @throws UnsupportedEncodingException 
   */
  @ResponseBody
  @RequestMapping(value = "/updateAuditStatus",produces = "text/html;charset=UTF-8")
  public String updateAuditStatus(@CurrentUser User user, String projectId, String status, Reason reasons,HttpServletRequest request,String flowDefineId,String process) throws UnsupportedEncodingException{
    String  reasonStr = "";
    if (reasons != null) {
      reasonStr = URLDecoder.decode(JSON.toJSONString(reasons),"UTF-8");
    }
    Project project = new Project();
    project.setId(projectId);
    project.setAuditReason(reasonStr);
    //该环节设置为执行中状态
    flowMangeService.flowExe(request, flowDefineId, projectId, 2);
    //获取项目信息
    Project selectById = projectService.selectById(projectId);
    //修改代办为已办
    todosService.updateIsFinish("open_bidding/bidFile.html?id=" + projectId + "&process=1");
    //通过 修改状态为一下状态
    if ("3".equals(status)) {
      project.setStatus(DictionaryDataUtil.getId("ZBWJYTG"));
      project.setConfirmFile(3);
      //推送待办
      Todos todos = new Todos();
      todos.setName(selectById.getName() + "招标文件审核通过");
      todos.setSenderId(user.getId());
      todos.setReceiverId(selectById.getPrincipal());
      todos.setUndoType((short)3);
      todos.setPowerId(PropUtil.getProperty("zbwjsh"));
      todos.setUrl("open_bidding/bidFile.html?id=" + projectId + "&process=1");
      todosService.insert(todos);

    }
    //退回 修改状态为上一状态
    if ("2".equals(status)) {
      project.setStatus(DictionaryDataUtil.getId("NZPFBZ"));
      project.setConfirmFile(2);
      //推送待办
      Todos todos = new Todos();
      todos.setName(selectById.getName() + "招标文件审核退回");
      todos.setSenderId(user.getId());
      todos.setReceiverId(selectById.getPrincipal());
      todos.setUndoType((short)3);
      todos.setPowerId(PropUtil.getProperty("zbwjsh"));
      todos.setUrl("open_bidding/bidFile.html?id=" + projectId + "&process=1");
      todosService.insert(todos);
    }
    projectService.update(project);
    return JSON.toJSONString(SUCCESS);

  }
  

  /**
   * 
   *〈简述〉生成正式的采购文件
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   * @throws UnsupportedEncodingException 
   */
  @ResponseBody
  @RequestMapping(value = "/purchaseFile",produces = "text/html;charset=UTF-8")
  public String purchaseFile(String projectId){
    //修改代办为已办
    todosService.updateIsFinish("open_bidding/bidFile.html?id=" + projectId + "&process=1");
    return SUCCESS;
    
  }




}
