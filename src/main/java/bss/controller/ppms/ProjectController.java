package bss.controller.ppms;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import ses.model.oms.PurchaseInfo;
import ses.service.oms.PurchaseServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;
import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.Task;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ProjectTaskService;
import bss.service.ppms.TaskService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;


/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>
 * @author   Administrator
 * @version  
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping("/project")
public class ProjectController extends BaseController {
  
    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskservice;

    @Autowired
    private PurchaseRequiredService purchaseRequiredService;
 
    @Autowired
    private ProjectDetailService detailService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private PurchaseServiceI purchaseService;
  
    @Autowired
    private CollectPurchaseService conllectPurchaseService;
  
    @Autowired
    private ProjectTaskService projectTaskService;
  
    @Autowired
    private FlowMangeService flowMangeService;

    /**
     * 〈简述〉 
     * 〈详细描述〉.
     * @author FengTian
     * @param page 分页
     * @param model 内置对象
     * @param project 项目实体
     * @return 跳转list页面
     */
    @RequestMapping("/list")
    public String list(Integer page, Model model, Project project, HttpServletRequest request) {
        request.getSession().removeAttribute("listFengtian");//返回展示页面删掉session
        List<Project> list = projectService.list(page == null ? 1 : page, project);
        PageInfo<Project> info = new PageInfo<Project>(list);
        model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
        model.addAttribute("info", info);
        model.addAttribute("projects", project);
        return "bss/ppms/project/list";
    }
    
    /**
     * 〈简述〉 〈详细描述〉
     * 
     * @author FengTian
     * @param page 分页
     * @param model 内置对象
     * @param purchaseRequired 需求计划实体
     * @param request 内置对象
     * @return 添加页面
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/add")
    public String add(Integer page, Model model, String id, String checkedIds,
                      HttpServletRequest request,PurchaseRequiredFormBean listBean) {
        List<Task> list = taskservice.listByTask(null, page==null?1:page);
        PageInfo<Task> info = new PageInfo<Task>(list);
        HashMap<String, Object> map = new HashMap<String, Object>();
        model.addAttribute("info", info);
        //显示项目明细
        if(id != null){
           /* String idr = (String) request.getSession().getAttribute("idr");
            if (idr != null) {
                idr = idr + "," + id;
                request.getSession().setAttribute("idr", idr);
            } else {
                request.getSession().setAttribute("idr", id);
            }
            String ide = (String) request.getSession().getAttribute("idr");*/
            List<PurchaseRequired> lists  = new ArrayList<>();
            String[] ids = id.split(",");
            //BigDecimal zero = BigDecimal.ZERO;
            int bud = 0;
            for (int i = 0; i < ids.length; i++ ) {
                PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(ids[i]);
                map.put("id", purchaseRequired.getId());
                List<PurchaseRequired> lis = purchaseRequiredService.selectByParentId(map);
                if(lis.size() == 1){
                    for (PurchaseRequired purchaseRequired2 : lis) {
                        bud+=purchaseRequired2.getBudget().intValue();
                    }
                }
                    lists.add(purchaseRequired);
            }
            Map<String,Object> mapTwo = new HashMap<>();
            List<PurchaseRequired> list1 = new ArrayList<>();
            for (PurchaseRequired pur : lists) {
                mapTwo.put("id", pur.getId());
                List<PurchaseRequired> lis = purchaseRequiredService.selectByParentId(mapTwo);
                if(lis.size()>1){
                    pur.setBudget(new BigDecimal(bud));
                }
                list1.add(pur);
            }
            model.addAttribute("kind", DictionaryDataUtil.find(5));
            
            List<PurchaseRequired> sessionList=  (List<PurchaseRequired>)request.getSession().getAttribute("listFengtian");
            if(sessionList!=null&&sessionList.size()>0){
                list1.addAll(sessionList);
            }
            model.addAttribute("lists", list1);
            
            request.getSession().setAttribute("listFengtian", list1);
            
            model.addAttribute("ids", id);
            model.addAttribute("checkedIds", checkedIds);
        }
        
        return "bss/ppms/project/add";
    }
    
    
    
    /**
     * 
     *〈预研项目选择采购明细〉
     *〈详细描述〉
     * @author Administrator
     * @param purchaseRequired
     * @param model
     * @return
     */
    @RequestMapping("/addDetail")
    public String addDetail(PurchaseRequired purchaseRequired,Model model) {
        List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired,0);
        model.addAttribute("info", list);
        return "bss/ppms/project/addDetail";
    }
    
    /**
     * 
     *〈添加明细〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @param chkItem
     * @param token2
     * @param list
     * @param name
     * @param projectNumber
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/create")
    public String create(String id, String chkItem, String token2, PurchaseRequiredFormBean list, String name, String projectNumber, Model model, HttpServletRequest request) {
        request.getSession().removeAttribute("listFengtian");
        try {
            // 判断表单是否重复提交
            HttpSession session = request.getSession();
            Object tokenValue = session.getAttribute("tokenSession");
            if (tokenValue != null && tokenValue.equals(token2)) {
                // 正常提交
                session.removeAttribute("tokenSession");
            } else {
                // 重复提交
                return "redirect:list.html";
            }
            //新增项目信息
            Project project = new Project();
            if(name != null && projectNumber != null){
                project.setName(name);
                project.setProjectNumber(projectNumber);
                project.setCreateAt(new Date());
                project.setStatus(3);
                if(chkItem != null){
                    project.setIsRehearse(0);
                }else{
                    project.setIsRehearse(1);
                }
                
                if(list.getList().get(0).getGoodsUse() != null || list.getList().get(0).getUseUnit() != null){
                    project.setIsImport(1);
                }else{
                    project.setIsImport(0);
                }
                if(list.getList().get(0).getPlanType() != null){
                    project.setPlanType(list.getList().get(0).getPlanType());
                }
                project.setPurchaseType(list.getList().get(0).getPurchaseType());
                projectService.add(project);    
            }
            //中间表
            String[] idss = chkItem.split(",");
            for (int i = 0; i < idss.length; i++ ) {
                ProjectTask projectTask = new ProjectTask();
                projectTask.setTaskId(idss[i]);
                projectTask.setProjectId(project.getId());
                projectTaskService.insertSelective(projectTask);
            }
            //新增项目明细
            int i=1;
            if(list != null){
                if(list.getList()!=null&&list.getList().size()>0){
                    for (PurchaseRequired purchaseRequired:list.getList()) {
                        ProjectDetail projectDetail = new ProjectDetail();
                        projectDetail.setRequiredId(purchaseRequired.getId());
                        projectDetail.setSerialNumber(purchaseRequired.getSeq());
                        projectDetail.setDepartment(purchaseRequired.getDepartment());
                        projectDetail.setGoodsName(purchaseRequired.getGoodsName());
                        projectDetail.setStand(purchaseRequired.getStand());
                        projectDetail.setQualitStand(purchaseRequired.getQualitStand());
                        projectDetail.setItem(purchaseRequired.getItem());
                        projectDetail.setCreatedAt(new Date());
                        projectDetail.setProject(new Project(project.getId()));
                        if (purchaseRequired.getPurchaseCount() != null) {
                            projectDetail.setPurchaseCount(purchaseRequired.getPurchaseCount().doubleValue());
                        }
                        if (purchaseRequired.getPrice() != null) {
                            projectDetail.setPrice(purchaseRequired.getPrice().doubleValue());
                        }
                        if (purchaseRequired.getBudget() != null) {
                            projectDetail.setBudget(purchaseRequired.getBudget().doubleValue());
                        }
                        if (purchaseRequired.getDeliverDate() != null) {
                            projectDetail.setDeliverDate(purchaseRequired.getDeliverDate());
                        }
                        if (purchaseRequired.getPurchaseType() != null) {
                            projectDetail.setPurchaseType(purchaseRequired.getPurchaseType());
                        }
                        if (purchaseRequired.getSupplier() != null) {
                            projectDetail.setSupplier(purchaseRequired.getSupplier());
                        }
                        if (purchaseRequired.getIsFreeTax() != null) {
                            projectDetail.setIsFreeTax(purchaseRequired.getIsFreeTax());
                        }
                        if (purchaseRequired.getGoodsUse() != null) {
                            projectDetail.setGoodsUse(purchaseRequired.getGoodsUse());
                        }
                        if (purchaseRequired.getUseUnit() != null) {
                            projectDetail.setUseUnit(purchaseRequired.getUseUnit());
                        }
                        if (purchaseRequired.getParentId() != null) {
                            projectDetail.setParentId(purchaseRequired.getParentId());
                        }
                        if (purchaseRequired.getDetailStatus() != null) {
                            projectDetail.setStatus(String.valueOf(purchaseRequired.getDetailStatus()));
                        }
                        projectDetail.setPosition(i);
                        i++;
                        detailService.insert(projectDetail);
                    }
                }
                
            }
            
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:list.html";
    }
    
    /**
     * 
     *〈跳转添加明细页面〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @param id
     * @param checkedIds
     * @return
     */
    @RequestMapping("/addDeatil")
    public String addDeatil(Model model, String id, String checkedIds) {
        Task task = taskservice.selectById(id);
        List<PurchaseRequired> lists=new LinkedList<PurchaseRequired>();
        List<String> list = conllectPurchaseService.getNo(task.getCollectId());
        for(String s:list){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("planNo", s);
            List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(map);
            lists.addAll(list2);
        }
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        model.addAttribute("lists", lists);
        model.addAttribute("checkedIds", checkedIds);
        return "bss/ppms/project/saveDetail";
    }
    
    /**
     * 
     *〈递归选中〉
     *〈详细描述〉
     * @author Administrator
     * @param response
     * @param id
     * @throws IOException
     */
    @RequestMapping("/viewIds")
    public void viewIds(HttpServletResponse response,String id) throws IOException {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            List<ProjectDetail> list = detailService.selectByParent(map);
            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
    }

    /**
     * 〈递归选中〉 
     * 〈详细描述〉
     * @author FengTian
     * @param response 内置对象
     * @param id 需求明细id
     * @param model 内置对象
     * @throws IOException 抛出异常
     */
    @RequestMapping("/checkDeail")
    public void checkDeail(HttpServletResponse response, String id, Model model)
        throws IOException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(id);
        if ("1".equals(purchaseRequired.getParentId())) {
            map.put("id", purchaseRequired.getId());
            List<PurchaseRequired> list = purchaseRequiredService.selectByParentId(map);
            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        }
        map.put("id", purchaseRequired.getId());
        List<PurchaseRequired> list = purchaseRequiredService.selectByParent(map);
        String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }


    /**
     * 
     *〈查看明细〉
     *〈详细描述〉
     * @author Administrator
     * @param id 項目id
     * @param ids 项目id
     * @param model 内置对象
     * @param page 分页
     * @param request 内置对象
     * @return 查看明细页面
     */
    @RequestMapping("/view")
    public String view(String id, String ids, Model model, Integer page, HttpServletRequest request) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("projectId", id);
            List<Packages> list = packageService.findPackageById(map);
            if(list != null && list.size()>0){
                for(Packages ps:list){
                    HashMap<String,Object> packageId = new HashMap<String,Object>();
                    packageId.put("packageId", ps.getId());
                    List<ProjectDetail> detailList = detailService.selectById(packageId);
                    ps.setProjectDetails(detailList);
                }
            }else{
                map.put("id", id);
                List<ProjectDetail> detail = detailService.selectById(map);
                model.addAttribute("lists", detail);
            }
            model.addAttribute("kind", DictionaryDataUtil.find(5));
            model.addAttribute("packageList", list);
            return "bss/ppms/project/viewDetail";

    }

    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author Administrator
     * @param id 项目id
     * @param model 内置对象
     * @param page 分页
     * @param request 内置对象
     * @return 跳转修改项目页面
     */
    @RequestMapping("/edit")
    public String edit(String id, Model model, Integer page, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Project project = projectService.selectById(id);
        map.put("id", id);
        List<ProjectDetail> detail = detailService.selectById(map);
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        model.addAttribute("lists", detail);
        model.addAttribute("project", project);
        return "bss/ppms/project/editDetail";
    }
    
    @RequestMapping("/addProject")
    public String addProject(String id, String bidAddress, String flowDefineId, String bidDate, String linkman, String linkmanIpone, Integer supplierNumber, HttpServletRequest request) {
        Project project = projectService.selectById(id);
        project.setLinkman(linkman);
        project.setLinkmanIpone(linkmanIpone);
        project.setSupplierNumber(supplierNumber);
        project.setBidAddress(bidAddress);
        Date date = new Date();   
        //注意format的格式要与日期String的格式相匹配   
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        try {   
            date = sdf.parse(bidDate);  
            project.setBidDate(date);
        } catch (Exception e) {   
            e.printStackTrace();   
        }  
        projectService.update(project);
        flowExe(request, flowDefineId, project.getId(), 2);
        return "redirect:excute.html?id="+id;
    }

    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author Administrator
     * @param id 项目id
     * @param model 内置对象
     * @return 跳转上传页面
     */
    @RequestMapping("/startProject")
    public String startProject(String id, Model model) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Project project = projectService.selectById(id);
        map.put("purchaseDepName", project.getPurchaseDepName());
        List<PurchaseInfo> purchaseInfo = purchaseService.findPurchaseList(map);
        model.addAttribute("purchaseInfo", purchaseInfo);
        model.addAttribute("project", project);
        model.addAttribute("dataId", DictionaryDataUtil.getId("PROJECT_IMPLEMENT"));
        return "bss/ppms/project/upload";
    }

    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author Administrator
     * @param attach 上传属性
     * @param project 项目实体
     * @param principal 项目负责人
     * @param request 内置对象
     * @return 跳转流程页面
     */
    @RequestMapping("/start")
    public String start(String id, String principal, HttpServletRequest request) {
        Project project = projectService.selectById(id);
        project.setPrincipal(principal);
        project.setStatus(1);
        project.setStartTime(new Date());
        projectService.update(project);
        return "redirect:excute.html?id=" + project.getId();
    }
    
    @RequestMapping("/mplement")
    public String starts(String projectId, String flowDefineId, Model model, Integer page) {
        String number = "";
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        Project project = projectService.selectById(projectId);
        List<ProjectTask> tasks = projectTaskService.queryByNo(map);
        Set<String> set =new HashSet<String>();
        for (ProjectTask projectTask : tasks) {
            if(StringUtils.isNotEmpty(projectTask.getTaskId())){
                set.add( projectTask.getTaskId());
                number = projectTask.getTaskId();
            }
        }   
        if(set.size() == 1){
            Task task = taskservice.selectById(number);
            model.addAttribute("task", task);
        }
        map.put("projectId", projectId);
        List<Packages> list = packageService.findPackageById(map);
        if(list != null && list.size()>0){
            for(Packages ps:list){
                HashMap<String,Object> packageId = new HashMap<String,Object>();
                packageId.put("packageId", ps.getId());
                List<ProjectDetail> detailList = detailService.selectById(packageId);
                ps.setProjectDetails(detailList);
            }
        }
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        model.addAttribute("packageList", list);
        model.addAttribute("project", project);
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("dataId", DictionaryDataUtil.getId("PROJECT_IMPLEMENT"));
        model.addAttribute("dataIds", DictionaryDataUtil.getId("PROJECT_APPROVAL_DOCUMENTS"));
        return "bss/ppms/project/essential_information";
    }
    
    @RequestMapping("/SameNameCheck")
    public void SameNameCheck(Project project, HttpServletResponse response) throws IOException {
        response.reset();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(projectService.SameNameCheck(project));
    }
    

    /**
     * 
     *〈修改项目信息〉
     *〈详细描述〉
     * @author Administrator
     * @param ide
     * @param name
     * @param projectNumber
     * @param lists
     * @return
     */
    @RequestMapping("/update")
    public String update( String ide, String name, String projectNumber, PurchaseRequiredFormBean lists){
        //修改项目名称和项目编号
        Project project = projectService.selectById(ide);
        project.setName(name);
        project.setProjectNumber(projectNumber);
        project.setPurchaseType(lists.getLists().get(0).getPurchaseType());
        projectService.update(project);
        //修改项目明细
        if(lists!=null){
            if(lists.getLists()!=null&&lists.getLists().size()>0){
                for( ProjectDetail detail:lists.getLists()){
                    if( detail.getId()!=null){
                        detailService.update(detail);
                    }
                }
            }
        }
        return "redirect:list.html";
    }     
    
    /**
     * 
     *〈查看明细是否分包〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping("/viewPackage")
    public void viewPackage(String id, HttpServletResponse response) throws IOException{
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("id", id);
        List<ProjectDetail> detail = detailService.selectById(map);
        String json = JSON.toJSONStringWithDateFormat(detail, "yyyy-MM-dd HH:mm:ss");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
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
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("id", id);
        List<ProjectDetail> detail = detailService.selectById(map);
        model.addAttribute("lists", detail);
        HashMap<String,Object> pack = new HashMap<String,Object>();
        pack.put("projectId", id);
        List<Packages> packages = packageService.findPackageById(pack);
        if(packages.size()!=0){
            for(Packages ps:packages){
                HashMap<String,Object> packageId = new HashMap<String,Object>();
                packageId.put("packageId", ps.getId());
                List<ProjectDetail> detailList = detailService.selectById(packageId);
                ps.setProjectDetails(detailList);
            }
        }
        model.addAttribute("packageList", packages);
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        Project project = projectService.selectById(id);
        model.addAttribute("project", project);
        return "bss/ppms/project/sub_package";
    }
    /**
     * 
    * @Title: checkProjectDeail
    * @author ZhaoBo
    * @date 2016-10-18 上午10:01:35  
    * @Description: 递归选中 
    * @param @param response
    * @param @param id
    * @param @param model
    * @param @throws IOException      
    * @return void
     */
    @RequestMapping("/checkProjectDeail")
    public void checkProjectDeail(HttpServletResponse response,HttpServletRequest request) throws IOException{
        String projectId = request.getParameter("projectId");
        HashMap<String,Object> map = new HashMap<String,Object>();
        ProjectDetail projectDetail = detailService.selectByPrimaryKey(request.getParameter("id"));
        if("1".equals(projectDetail.getParentId())){
            map.put("projectId", projectId);
            map.put("id", projectDetail.getRequiredId());
            List<ProjectDetail> list = detailService.selectByParentId(map);
            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        }
        map.put("projectId", projectId);
        map.put("id", projectDetail.getRequiredId());
        List<ProjectDetail> list = detailService.selectByParent(map);
        String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
    /**
     * 
    * @Title: addPack
    * @author ZhaoBo
    * @date 2016-10-18 下午2:42:15  
    * @Description: 添加分包 
    * @param @param request
    * @param @return      
    * @return String
     */
    @RequestMapping("/addPack")
    @ResponseBody
    public String addPack(HttpServletRequest request){
        String[] id = request.getParameter("id").split(",");
        String projectId = request.getParameter("projectId");
        Project project = projectService.selectById(projectId);
        HashMap<String,Object> pack = new HashMap<String,Object>();
        pack.put("projectId",projectId);
        List<Packages> packList = packageService.findPackageById(pack);
        Packages pg = new Packages();
        pg.setName("第"+(packList.size()+1)+"包");
        pg.setProjectId(projectId);
        pg.setIsDeleted(0);
        if(project.getIsImport()==1){
            pg.setIsImport(1);
        }else{
            pg.setIsImport(0);
        }
        pg.setPurchaseType(project.getPurchaseType());
        pg.setCreatedAt(new Date());
        packageService.insertSelective(pg);
        List<Packages> wantPackId = packageService.findPackageById(pack);
        for(int i=0;i<id.length;i++){
            ProjectDetail projectDetail = new ProjectDetail();
            projectDetail.setId(id[i]);
            projectDetail.setPackageId(wantPackId.get(0).getId());
            detailService.update(projectDetail);
        }
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("packageId", wantPackId.get(0).getId());
        List<ProjectDetail> details = detailService.selectById(map);
        Packages p = new Packages();
        p.setId(wantPackId.get(0).getId());
        if(details.get(0).getStatus().equals("1")){
            p.setStatus(1);
            packageService.updateByPrimaryKeySelective(p);
        }else{
            p.setStatus(0);
            packageService.updateByPrimaryKeySelective(p);
        }
        return "1";
    }

    
    /**
     * 
    * @Title: editPackName
    * @author ZhaoBo
    * @date 2016-10-18 下午2:41:47  
    * @Description: 修改包名 
    * @param @return      
    * @return String
     */
    @RequestMapping("/editPackName")
    @ResponseBody
    public String editPackName(HttpServletRequest request){
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        Packages pk = new Packages();
        pk.setId(id);
        pk.setName(name);
        packageService.updateByPrimaryKeySelective(pk);
        return "1";
    }
    /**
     * 
    * @Title: deletePackageById
    * @author ZhaoBo
    * @date 2016-10-18 下午3:15:18  
    * @Description: 删除分包 
    * @param @param request
    * @param @return      
    * @return String
     */
    @RequestMapping("/deletePackageById")
    @ResponseBody
    public String deletePackageById(HttpServletRequest request){
        String id = request.getParameter("id");
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("packageId", id);
        List<ProjectDetail> detail = detailService.selectById(map);
        for(int i=0;i<detail.size();i++){
            ProjectDetail projectDetail = new ProjectDetail();
            projectDetail.setId(detail.get(i).getId());
            projectDetail.setPackageId("");
            detailService.update(projectDetail);
        }
        Packages pk = new Packages();
        pk.setId(id);
        pk.setIsDeleted(1);
        packageService.updateByPrimaryKeySelective(pk);
        return "1";
    }
    
    
    /**
     * Description: 根据项目的采购方式进入不同的实施页面
     * 
     * @author Ye MaoLin
     * @version 2016-10-13
     * @param projectId
     * @return String
     * @exception IOException
     */
    @RequestMapping("/excute")
    public String execute(String id, Model model, Integer page) {
        Project project = projectService.selectById(id);
        model.addAttribute("project", project);
        model.addAttribute("page", page);
        HashMap<String, Object> map = (HashMap<String, Object>)getFlowDefine(project.getPurchaseType(), id);
        model.addAttribute("fds", map.get("fds"));
        //默认url
        model.addAttribute("url", map.get("url"));
        System.out.println(map.get("url"));
        return "bss/ppms/open_bidding/main";
    }

    /**
     *〈简述〉根据采购方式获取流程环节list
     *〈详细描述〉
     * @author Ye MaoLin
     * @param code 采购方式编码
     * @return 流程环节
     */
    public Map<String, Object> getFlowDefine(String purchaseTypeId, String projectId){
        HashMap<String, Object> map = new HashMap<String, Object>();
        FlowDefine fd = new FlowDefine();
        fd.setPurchaseTypeId(purchaseTypeId);
        //该采购方式定义的流程环节
        List<FlowDefine> fds = flowMangeService.find(fd);
        //该项目已执行的流程环节
        FlowExecute flowExecute = new FlowExecute();
        flowExecute.setProjectId(projectId);
        List<FlowExecute> flowExecutes = flowMangeService.findFlowExecute(flowExecute);
        //如果项目已开始实施执行
        if (flowExecutes != null && flowExecutes.size() > 0) {
            for (FlowDefine flowDefine : fds) {
                //将要执行的步骤
                Integer willStep = flowExecutes.get(0).getStep()+1;
                flowExecute.setFlowDefineId(flowDefine.getId());
                //获取该项目该环节的执行情况
                List<FlowExecute> flowExecutes2 = flowMangeService.findFlowExecute(flowExecute);
                if (flowExecutes2 != null && flowExecutes2.size() > 0) {
                    Integer s = flowExecutes2.get(0).getStatus();
                    if (s == 1) {
                        //已执行状态
                        flowDefine.setStatus(1);
                    } else if (s == 2) {
                        //执行中状态
                        flowDefine.setStatus(2);
                    }
                } else {
                    if (flowDefine.getStep() == willStep) {
                        //将要执行状态
                        flowDefine.setStatus(4);
                        map.put("url", flowDefine.getUrl()+"?projectId="+projectId+"&flowDefineId="+flowDefine.getId());
                    } else {
                        //未执行状态
                        flowDefine.setStatus(3);
                    }
                }
            }
        } else {
            //默认第一个为将要执行状态
            fds.get(0).setStatus(4);
            map.put("url", fds.get(0).getUrl()+"?projectId="+projectId+"&flowDefineId="+fds.get(0).getId());
        }
        map.put("fds", fds);
        return map;
    }
    
    
    
    /**
     *〈简述〉添加一条流程执行记录
     *〈详细描述〉
     * @author Ye MaoLin
     * @param request
     * @param flowDefineId 流程环节定义
     * @param projectId 项目id
     * @param status 执行状态
     */
    public void flowExe(HttpServletRequest request, String flowDefineId, String projectId, Integer status){
        FlowExecute temp = new FlowExecute();
        temp.setFlowDefineId(flowDefineId);
        temp.setProjectId(projectId);
        List<FlowExecute> flowExecutes = flowMangeService.findFlowExecute(temp);
        //如果该项目该环节流程已经执行过
        if (flowExecutes != null && flowExecutes.size() > 0) {
            //执行记录设置为假删除状态
            FlowExecute oldFlowExecute = flowExecutes.get(0); 
            oldFlowExecute.setIsDeleted(1);
            oldFlowExecute.setUpdatedAt(new Date());
            flowMangeService.updateExecute(oldFlowExecute);
            //新增一条相同环节记录
            oldFlowExecute.setCreatedAt(new Date());
            oldFlowExecute.setStatus(status);
            oldFlowExecute.setId(WfUtil.createUUID());
            oldFlowExecute.setIsDeleted(0);
            User currUser = (User) request.getSession().getAttribute("loginUser");
            oldFlowExecute.setOperatorId(currUser.getId());
            oldFlowExecute.setOperatorName(currUser.getRelName());
            oldFlowExecute.setStatus(status);
            flowMangeService.saveExecute(oldFlowExecute);
        } else {
            //如果该项目该环节流程没有执行过
            FlowDefine flowDefine = flowMangeService.getFlowDefine(flowDefineId);
            FlowExecute flowExecute = new FlowExecute();
            flowExecute.setCreatedAt(new Date());
            flowExecute.setFlowDefineId(flowDefineId);
            flowExecute.setIsDeleted(0);
            User currUser = (User) request.getSession().getAttribute("loginUser");
            flowExecute.setOperatorId(currUser.getId());
            flowExecute.setOperatorName(currUser.getRelName());
            flowExecute.setProjectId(projectId);
            flowExecute.setStatus(status);
            flowExecute.setId(WfUtil.createUUID());
            flowExecute.setStep(flowDefine.getStep());
            flowMangeService.saveExecute(flowExecute);
        }
    }
}
