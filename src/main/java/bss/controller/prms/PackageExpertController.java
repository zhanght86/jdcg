package bss.controller.prms;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.ExpExtPackage;
import ses.model.ems.Expert;
import ses.model.ems.ProjectExtract;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpExtPackageService;
import ses.service.ems.ExpExtractRecordService;
import ses.service.ems.ExpertService;
import ses.service.ems.ProjectExtractService;
import ses.service.sms.SupplierQuoteService;
import ses.service.sms.SupplierService;
import ses.util.CnUpperCaser;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.ppms.BidMethod;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Money;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ScoreModel;
import bss.model.ppms.SupplierCheckPass;
import bss.model.prms.ExpertScore;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageExpert;
import bss.model.prms.PackageFirstAudit;
import bss.model.prms.ReviewFirstAudit;
import bss.model.prms.ReviewProgress;
import bss.model.prms.SupplierRank;
import bss.model.prms.ext.AuditModelExt;
import bss.model.prms.ext.ExpertSuppScore;
import bss.model.prms.ext.Extension;
import bss.model.prms.ext.PackExpertExt;
import bss.model.prms.ext.SupplierExt;
import bss.service.ppms.AduitQuotaService;
import bss.service.ppms.BidMethodService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.MarkTermService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.service.ppms.ScoreModelService;
import bss.service.ppms.SupplierCheckPassService;
import bss.service.prms.ExpertScoreService;
import bss.service.prms.FirstAuditService;
import bss.service.prms.PackageExpertService;
import bss.service.prms.PackageFirstAuditService;
import bss.service.prms.ReviewFirstAuditService;
import bss.service.prms.ReviewProgressService;

import com.alibaba.fastjson.JSON;
import common.annotation.CurrentUser;

@Controller
@RequestMapping("packageExpert")
public class PackageExpertController {
    private final static int ONE = 1;
    private final static short SONE = 1;
    private final static Short NUMBER_TWO = 2;
    
    @Autowired
    private PackageExpertService service;
    @Autowired
    private ProjectDetailService detailService;
    @Autowired
    private PackageService packageService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectExtractService projectExtractService;
    @Autowired
    private SaleTenderService saleTenderService;// 供应商查询
    @Autowired
    private ReviewProgressService reviewProgressService;// 进度
    @Autowired
    private ExpertService expertService;// 专家
    @Autowired
    private ReviewFirstAuditService reviewFirstAuditService;// 初审表
    @Autowired
    private PackageExpertService packageExpertService;// 专家项目包 关联表
    @Autowired
    private SupplierQuoteService supplierQuoteService;// 供应商报价
    @Autowired
    private ExpertScoreService expertScoreService;// 专家评分
    @Autowired
    private AduitQuotaService aduitQuotaService;// 评分
    @Autowired
    private PackageFirstAuditService packageFirstAuditService;//包关联初审项
    @Autowired
    private FirstAuditService firstAuditService;//初审项
    @Autowired
    private SupplierService supplierService;//初审项
    @Autowired
    private FlowMangeService flowMangeService;//环节
    @Autowired
    private ExpExtPackageService expExtPackageService;//项目包关联
    @Autowired
    private ExpExtractRecordService expExtractRecordService; //专家抽取记录表
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI; //数据字典表
    @Autowired
    private MarkTermService markTermService; //数据字典表
    @Autowired
    private ScoreModelService scoreModelService; //数据字典表
    @Autowired 
    private UserServiceI userService;
    @Autowired 
    private BidMethodService bidMethodService;
    @Autowired
    private SupplierCheckPassService checkPassService;
    
    
    /**
     *〈简述〉跳转分配专家
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId 项目id
     * @param model
     * @param flowDefineId 流程环节id
     * @return
     */
    @RequestMapping("/assignedExpert")
    public String assignedExpert(String projectId, Model model, String flowDefineId) {
        //获取组长信息
        /*FlowExecute execute=new FlowExecute();
        execute.setProjectId(projectId);
        execute.setIsDeleted(0);
        execute.setFlowDefineId(flowDefineId);
        List<FlowExecute> findFlowExecute = flowMangeService.findFlowExecute(execute);
        if (findFlowExecute.size() != 0 && findFlowExecute.get(0).getStatus() != null &&   findFlowExecute.get(0).getStatus() == 1){
            model.addAttribute("execute","SCCUESS" );
        }*/
        /*Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        map.put("isGroupLeader", 1);
        List<PackageExpert> selectList = service.selectList(map);
        model.addAttribute("selectList", selectList);*/
        Project project = projectService.selectById(projectId);
        // 项目实体
        model.addAttribute("project", project);
        model.addAttribute("flowDefineId", flowDefineId);
        //专家类型
        model.addAttribute("ddList", expExtractRecordService.ddList());
        model.addAttribute("ddJson", JSON.toJSONString(expExtractRecordService.ddList()));
        //查询该项目下专家是否签到
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("projectId", projectId);
        List<PackageExpert> expertSigneds = packageExpertService.selectList(map2);
        if (expertSigneds != null && expertSigneds.size() > 0) {
          // 项目分包信息
          HashMap<String, Object> pack = new HashMap<String, Object>();
          pack.put("projectId", projectId);
          List<Packages> packages = packageService.findPackageById(pack);
          model.addAttribute("packages", packages);
          model.addAttribute("expertSigneds", expertSigneds);
          model.addAttribute("isEndSigin", "1");
          return "bss/prms/assign_expert/expert_list_view";
        } else {
          List<Packages> packages = packageService.listProjectExtract(projectId);
          model.addAttribute("isEndSigin", "0");
          // 包信息
          model.addAttribute("packageList", packages);
          return "bss/prms/assign_expert/expert_list";
        }
    }

    /**
     *〈简述〉展示专家列表
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId 项目id
     * @param model
     * @param flowDefineId 流程环节id
     * @return  
     */
    @RequestMapping("/showExpert")
    public String showExpert(Model model, String packageId,String flowDefineId,String execute) {
        // 项目抽取的专家信息
            ProjectExtract projectExtract = new ProjectExtract();
            projectExtract.setProjectId(packageId);
            projectExtract.setIsProvisional((short)1);
            projectExtract.setReason("1");
            List<ProjectExtract> expertList = projectExtractService.list(projectExtract);
            model.addAttribute("expertList", expertList);
            model.addAttribute("packageId", packageId);
            model.addAttribute("flowDefineId", flowDefineId);
            //专家类型
            model.addAttribute("ddList", expExtractRecordService.ddList());
        model.addAttribute("execute", execute);
        return "bss/prms/assign_expert/list";
    }

    /**
     *〈简述〉
     * 专家后台评分汇总按钮点击事件
     *〈详细描述〉
     * 展示专家信息
     * @author WangHuijie
     * @param model
     * @param projectId
     * @param packageId
     * @return
     */
    @RequestMapping("toTotal")
    public String toTotal(Model model, String projectId, String packageId){
      //获取关联信息
        ExpExtPackage extPackag = new ExpExtPackage();
        extPackag.setProjectId(projectId);
        extPackag.setPackageId(packageId);
        List<ExpExtPackage> list = expExtPackageService.list(extPackag, "0");
        // 项目抽取的专家信息
        if (list != null && list.size() !=0 ){
            ProjectExtract projectExtract = new ProjectExtract();
            projectExtract.setProjectId(list.get(0).getId());
            projectExtract.setIsProvisional((short)1);
            projectExtract.setReason("1");
            List<ProjectExtract> expertList = projectExtractService.list(projectExtract);
            model.addAttribute("expertList", expertList);
            model.addAttribute("packageId", packageId);
            model.addAttribute("projectId", projectId);
        }
        return "bss/prms/audit/expert_list";
    }

    /**
     * 
     *〈简述〉执行完成
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param projectId 项目id 
     * @param flowDefineId 流程id 
     * @return
     */
    @ResponseBody
    @RequestMapping("/executeFinish")
    public String executeFinish(String  projectId, String flowDefineId,HttpServletRequest sq){
        //获取组长信息315B381C86D74703AF44CA675E126A55
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        map.put("isGroupLeader", 1);
        //获取关联包的专家
        List<PackageExpert> selectList = service.selectList(map);
        //获取项目下的所有包
        List<Packages> packageList = packageService.findPackageById(map);
        Set<PackageExpert> set = new HashSet<PackageExpert>(selectList);
        selectList.clear();
        selectList.addAll(set);
        if (selectList.size() == packageList.size()){
            //             //修改流程状态
            flowMangeService.flowExe(sq, flowDefineId, projectId, 1);
        }else{
            return JSON.toJSONString("ERROR");
        }
        return JSON.toJSONString("SCCUESS");

    }
    
    @RequestMapping("/toSupplierQuote")
    public String supplierQuote(HttpServletRequest req, String projectId, Model model, String flowDefineId) throws ParseException {
        boolean status = false;
        if (!status) {
            //去saletender查出项目对应的所有的包
            List<String> packageIds = saleTenderService.getPackageIds(projectId);
            //这里用这个是因为hashMap是无序的
            TreeMap<String ,List<SaleTender>> treeMap = new TreeMap<String ,List<SaleTender>>();
            SaleTender condition = new SaleTender();
            HashMap<String, Object> map = new HashMap<String, Object>();
            HashMap<String, Object> map1 = new HashMap<String, Object>();
            if (packageIds != null) {
                labe:for (String packageId : packageIds) {
                    condition.setProjectId(projectId);
                    condition.setPackages(packageId);
                    condition.setStatusBid(NUMBER_TWO);
                    condition.setStatusBond(NUMBER_TWO);
                    List<SaleTender> stList = saleTenderService.find(condition);
                    map1.put("packageId", packageId);
                    map1.put("projectId", projectId);
                    List<ProjectDetail> detailList = detailService.selectByCondition(map1, null);
                    BigDecimal projectBudget = BigDecimal.ZERO;
                    for (ProjectDetail projectDetail : detailList) {
                        projectBudget = projectBudget.add(new BigDecimal(projectDetail.getBudget()));
                    }
                    //再次点击 查看
                    for (SaleTender saleTender : stList) {
                        Quote quote = new Quote();
                        quote.setProjectId(projectId);
                        quote.setPackageId(packageId);
                        quote.setSupplierId(saleTender.getSupplierId());
                        List<Quote> allQuote = supplierQuoteService.getAllQuote(quote, 1);
                        if (allQuote != null && allQuote.size() > 0) {
                            if (allQuote.get(0).getQuotePrice() != null ) {
                                status = true;
                                break labe;
                            }
                            for (Quote conditionQuote : allQuote) {
                                if (conditionQuote.getSupplier()!=null&&conditionQuote.getSupplier().getId().equals(saleTender.getSuppliers().getId()) &&
                                    conditionQuote.getProjectId().equals(saleTender.getProject().getId()) && saleTender.getPackages().equals(conditionQuote.getPackageId())) {
                                    saleTender.setTotal(conditionQuote.getTotal());
                                    saleTender.setDeliveryTime(conditionQuote.getDeliveryTime());
                                    saleTender.setIsTurnUp(conditionQuote.getIsTurnUp());
                                    saleTender.setQuoteId(conditionQuote.getId());
                                    if (conditionQuote.getPackages() != null) {
                                        saleTender.setPackageNames(conditionQuote.getPackages().getName());
                                    }
                                }
                            }
                        }
                    }
                    map.put("id", packageId);
                    List<Packages> pack = packageService.findPackageById(map);
                    if (pack != null && pack.size() > 0) {
                        treeMap.put(pack.get(0).getName()+"|"+projectBudget, stList);
                    } else {
                        treeMap.put("", stList);
                    };
                }
            }
            model.addAttribute("treeMap", treeMap);
            model.addAttribute("projectId", projectId);
            model.addAttribute("flowDefineId", flowDefineId);
        }
        
        boolean flagButton = false;
        if (status) {
            HashMap<String, Object> map1 = new HashMap<String, Object>();
            map1.put("projectId", projectId);
            SaleTender st = new SaleTender();
            st.setProjectId(projectId);
            StringBuilder sb = new StringBuilder("");
            List<SaleTender> saleTenderList = saleTenderService.find(st);
            for (SaleTender saleTender : saleTenderList) {
                sb.append(saleTender.getPackages());
            }
            List<Packages> listPack = supplierQuoteService.selectByPrimaryKey(map1, null);
            List<Packages> listPackage = new ArrayList<Packages>();
            for (Packages packages : listPack) {
                if (sb.toString().indexOf(packages.getId()) != -1) {
                    listPackage.add(packages);
                }
            }
            //开始循环包
            List<HashMap<List<Supplier>,List<ProjectDetail>>> listPd = new ArrayList<HashMap<List<Supplier>,List<ProjectDetail>>>();
            for (Packages pk:listPackage) {
                HashMap<List<Supplier>,List<ProjectDetail>> hashMap = new HashMap<List<Supplier>,List<ProjectDetail>>();
                List<Supplier> supplierList = new ArrayList<Supplier>();
                map1.put("packageId", pk.getId());
                //
                Quote quotes = new Quote();
                quotes.setProjectId(projectId);
                List<Date> listDate = supplierQuoteService.selectQuoteCount(quotes);
                List<ProjectDetail> detailList = null;
                if (listDate.size() != 0) {
                    Quote quote=new Quote();
                    quote.setProjectId(projectId);
                    quote.setCreatedAt(new Timestamp(listDate.get(listDate.size() - 1).getTime()));
                    List<Quote> listQuote=supplierQuoteService.selectQuoteHistoryList(quote);
                    detailList = detailService.selectByCondition(map1, null);
                    if (listQuote != null && listQuote.size() > 0 && detailList != null && detailList.size() > 0) {
                        flagButton = true;
                    }
                    List<ProjectDetail> detailList1 = new ArrayList<ProjectDetail>();
                    for (Quote q : listQuote) {
                        for (ProjectDetail projectDetail : detailList) {
                            if (q.getProjectDetail().getId().equals(projectDetail.getId())) {
                                ProjectDetail pd = new ProjectDetail();
                                pd.setId(projectDetail.getId());
                                pd.setGoodsName(projectDetail.getGoodsName());
                                pd.setSerialNumber(projectDetail.getSerialNumber());
                                pd.setStand(projectDetail.getStand());
                                pd.setQualitStand(projectDetail.getQualitStand());
                                pd.setItem(projectDetail.getItem());
                                pd.setPurchaseCount(projectDetail.getPurchaseCount());
                                pd.setTotal(q.getTotal());
                                pd.setDeliveryTime(q.getDeliveryTime());
                                pd.setRemark(q.getRemark());
                                pd.setQuotePrice(q.getQuotePrice());
                                pd.setSupplierId(q.getSupplierId());
                                pd.setIsTurnUp(q.getIsTurnUp());
                                detailList1.add(pd);
                            }
                        }
                    }
                    detailList = detailList1;
                } else {
                    detailList = detailService.selectByCondition(map1, null);
                }
                //
                for (SaleTender saleTender : saleTenderList) {
                    if (saleTender.getPackages().indexOf(pk.getId()) != -1) {
                        Supplier supplier = supplierService.get(saleTender.getSuppliers().getId());
                        Quote quote=new Quote();
                        quote.setProjectId(projectId);
                        quote.setPackageId(pk.getId());
                        quote.setSupplierId(supplier.getId());
                        List<Quote> listQuote=supplierQuoteService.selectQuoteHistoryList(quote);
                        if (listQuote != null && listQuote.size() >0) {
                            supplier.setIsturnUp(listQuote.get(0).getIsTurnUp().toString());
                        }
                        supplierList.add(supplier);
                    }
                }
                hashMap.put(supplierList, detailList);
                listPd.add(hashMap);
            }
            model.addAttribute("flagButton", flagButton);
            model.addAttribute("listPd", listPd);
            model.addAttribute("listPackage", listPackage);
            model.addAttribute("projectId", projectId);
        }
        model.addAttribute("status", status);
        return "bss/prms/supplier_quote/quote_list";
    }

    /**
     *〈简述〉跳转供应商报价
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId 项目id
     * @param model
     * @param flowDefineId 
     * @return
     * @throws ParseException 处理异常
     */
    //@RequestMapping("/toSupplierQuote")
    public String toSupplierQuote(HttpServletRequest req, String projectId, Model model, String flowDefineId) throws ParseException {
        List<SaleTender> supplierList = saleTenderService.list(new SaleTender(projectId), 0);
        List<Packages> packages = packageService.listResultExpert(projectId);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        List<Packages> listPackage = supplierQuoteService.selectByPrimaryKey(map, null);
        java.text.DecimalFormat df=new java.text.DecimalFormat("#.00");
        for (SaleTender stender : supplierList) {
            Quote qt = new Quote();
            qt.setProjectId(projectId);
            qt.setSupplierId(stender.getSuppliers().getId());
            List<Date> listDate = supplierQuoteService.selectQuoteCount(qt);
            List<Packages> listPackageEach = new ArrayList<Packages>();
            SaleTender st = new SaleTender();
            st.setProjectId(projectId);
            st.setSupplierId(stender.getSuppliers().getId());
            List<SaleTender> stList = saleTenderService.find(st);
            if (stList != null && stList.size() > 0) {
                String packageStr = stList.get(0).getPackages();
                for (Packages packa : listPackage) {
                    if (packageStr.indexOf(packa.getId()) != -1) {
                        listPackageEach.add(packa);
                    }
                }
            }
            List<Money> listMoney = new ArrayList<Money>();
            for (Packages pk:listPackageEach) {
                Money money = new Money();
                Quote quote = new Quote();
                if(listDate != null && listDate.size() > 0){
                    quote.setCreatedAt(new Timestamp(listDate.get(listDate.size()-1).getTime()));
                    quote.setPackageId(pk.getId());
                    List<Quote> quoteList = supplierQuoteService.selectQuoteHistoryList(quote);
                    BigDecimal totalMoney = BigDecimal.ZERO;
                    for (Quote q : quoteList) {
                        totalMoney = totalMoney.add(q.getTotal());
                    }
                    money.setPackageName(pk.getName());
                    money.setTotalMoney(new BigDecimal(df.format(totalMoney)));
                    money.setUpperName(CnUpperCaser.getCnString(totalMoney.doubleValue()));
                }else{
                    money.setPackageName(pk.getName());
                    money.setTotalMoney(BigDecimal.ZERO);
                    money.setUpperName(CnUpperCaser.getCnString(0));
                }
                listMoney.add(money);
            }
            stender.setMoney(listMoney);
        }
        //----
        // 包信息
        model.addAttribute("packageList", packages);
        model.addAttribute("supplierList", supplierList);
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/prms/supplier_quote/quote_list";
    }

    /**
     *〈简述〉跳转查看评审进度
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId 项目id
     * @param model 
     * @param flowDefineId 流程环节id
     * @return
     */
    @RequestMapping("/toAuditProgress")
    public String toAuditProgress(String projectId, Model model, String flowDefineId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        // 进度集合
        List<ReviewProgress> reviewProgressList = reviewProgressService.selectByMap(map);
        List<Packages> packages = packageService.listResultExpert(projectId);
        if (reviewProgressList.size() < packages.size()) {
            for (Packages pg : packages) {
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("projectId", projectId);
                map2.put("packageId", pg.getId());
                //查询该包有没有评审进度数据
                List<ReviewProgress> rplist = reviewProgressService.selectByMap(map2);
                if (rplist == null || rplist.size() <= 0) {
                    ReviewProgress reviewProgress = new ReviewProgress();
                    reviewProgress.setAuditStatus("0");
                    reviewProgress.setFirstAuditProgress(0.00);
                    reviewProgress.setPackageId(pg.getId());
                    reviewProgress.setPackageName(pg.getName());
                    reviewProgress.setProjectId(projectId);
                    reviewProgress.setScoreProgress(0.00);
                    reviewProgress.setTotalProgress(0.00);
                    reviewProgressList.add(reviewProgress);
                }
            }
        }
        // 包信息
        model.addAttribute("packageList", packages);
        // 进度
        model.addAttribute("reviewProgressList", reviewProgressList);
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/prms/bid_audit/audit_progress";
    }

    /**
     * 
     * @Title: toPackageExpert
     * @author ShaoYangYang
     * @date 2016年10月18日 下午3:05:52
     * @Description: TODO 跳转到组织专家评审页面
     * @param @param projectId
     * @param @return
     * @return String
     */
    @RequestMapping("toPackageExpert")
    public String toPackageExpert(String projectId, String flag, Model model) {
        // 项目分包信息
        //HashMap<String, Object> pack = new HashMap<String, Object>();
        //pack.put("projectId", projectId);
        //List<Packages> packages = packageService.findPackageById(pack);
        List<Packages> packages = packageService.listResultExpert(projectId);
        Map<String, Object> list = new HashMap<String, Object>();
        // 关联表集合
        List<PackageExpert> expertIdList = new ArrayList<>();
        // 进度集合
        List<ReviewProgress> reviewProgressList = new ArrayList<>();
        List<ExpertSuppScore> expertScoreAll = new ArrayList<>();
        List<AuditModelExt> auditModelListAll = new ArrayList<>();
        Map<String, Object> mapSearch = new HashMap<String, Object>();
        for (Packages ps : packages) {
            list.put("pack" + ps.getId(), ps);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("packageId", ps.getId());
            List<ProjectDetail> detailList = detailService.selectById(map);
            ps.setProjectDetails(detailList);
            // 设置查询条件
            mapSearch.put("projectId", projectId);
            mapSearch.put("packageId", ps.getId());
            // 查询出该项目下的包关联的信息集合
            List<PackageExpert> selectList = service.selectList(mapSearch);
            // 查询评审项
            List<AuditModelExt> auditModelExtList = aduitQuotaService
                .findAllByMap(mapSearch);
            auditModelListAll.addAll(auditModelExtList);
            // 查询评分信息
            //List<ExpertScore> expertList = expertScoreService.selectByMap(mapSearch);
            // 查询评分信息(由按项目查改为按供应商和包查)
            List<ExpertSuppScore> expertList = expertScoreService.getScoreByMap(mapSearch);
            for (ExpertSuppScore expertSuppScore : expertList) {
                System.out.println(expertSuppScore.getScore());
            }
            expertScoreAll.addAll(expertList);
            model.addAttribute("expertIdList", expertIdList);
            // 查询进度
            List<ReviewProgress> selectByMap = reviewProgressService
                .selectByMap(map); 
            expertIdList.addAll(selectList);
            reviewProgressList.addAll(selectByMap);
        }
        // 进度
        model.addAttribute("reviewProgressList", reviewProgressList);
        // 供应商信息
        List<SaleTender> supplierList = saleTenderService.list(new SaleTender(
            projectId), 0);
        model.addAttribute("supplierList", supplierList);
        // 查询条件
        ProjectExtract projectExtract = new ProjectExtract();
        projectExtract.setProjectId(projectId);
        projectExtract.setReason("1");
        // 项目抽取的专家信息
        List<ProjectExtract> expertList = projectExtractService
            .list(projectExtract);
        model.addAttribute("expertList", expertList);
        // 包信息
        model.addAttribute("packageList", packages);
        Project project = projectService.selectById(projectId);
        // 项目实体
        model.addAttribute("project", project);
        // 关联信息集合
        // 封装实体
        List<PackExpertExt> packExpertExtList = new ArrayList<>();
        // 供应商封装实体
        List<SupplierExt> supplierExtList = new ArrayList<>();
        PackExpertExt packExpertExt;
        for (PackageExpert packageExpert : expertIdList) {
            packExpertExt = new PackExpertExt();
            Expert expert = expertService.selectByPrimaryKey(packageExpert
                .getExpertId());
            packExpertExt.setExpert(expert);
            packExpertExt.setPackageId(packageExpert.getPackageId());
            packExpertExt.setProjectId(packageExpert.getProjectId());
            Map<String, Object> map = new HashMap<>();
            // 根据专家id和包id查询改包的这个专家是否评审完成
            map.put("expertId", packageExpert.getExpertId());
            map.put("packageId", packageExpert.getPackageId());
            // 根据专家id查询关联表 确定该专家是否都已评审
            List<PackageExpert> selectList = service.selectList(map);
            int count = 0;
            for (PackageExpert packageExpert2 : selectList) {
                if (packageExpert2.getIsAudit() == SONE) {
                    count++;
                }
            }
            if (count > 0) {
                packExpertExt.setIsPass("已评审");
            } else {
                packExpertExt.setIsPass("未评审");
            }
            // 根据供应商id 和包id查询审核表 确定该供应商是否通过评审
            for (SaleTender saleTender : supplierList) {
                SupplierExt supplierExt = new SupplierExt();
                Map<String, Object> map2 = new HashMap<>();
                map2.put("supplierId", saleTender.getSuppliers().getId());
                map2.put("packageId", packageExpert.getPackageId());
                map2.put("expertId", packageExpert.getExpertId());
                List<ReviewFirstAudit> selectList2 = reviewFirstAuditService
                    .selectList(map2);
                if (selectList2 != null && selectList2.size() > 0) {
                    int count2 = 0;
                    for (ReviewFirstAudit reviewFirstAudit : selectList2) {
                        if (reviewFirstAudit.getIsPass() == SONE) {
                            count2++;
                            break;
                        }
                    }
                    // 如果变量大于0 说明有不合格的数据
                    if (count2 > 0) {
                        supplierExt.setSupplierId(saleTender.getSuppliers()
                            .getId());
                        supplierExt.setExpertId(packageExpert.getExpertId());
                        supplierExt.setPackageId(packageExpert.getPackageId());
                        supplierExt.setSuppIsPass("不合格");
                    } else {
                        supplierExt.setSupplierId(saleTender.getSuppliers()
                            .getId());
                        supplierExt.setExpertId(packageExpert.getExpertId());
                        supplierExt.setPackageId(packageExpert.getPackageId());
                        supplierExt.setSuppIsPass("合格");
                    }
                } else {
                    supplierExt
                    .setSupplierId(saleTender.getSuppliers().getId());
                    supplierExt.setPackageId(packageExpert.getPackageId());
                    supplierExt.setExpertId(packageExpert.getExpertId());
                    supplierExt.setSuppIsPass("未评审");
                }

                supplierExtList.add(supplierExt);
            }
            packExpertExtList.add(packExpertExt);
        }
        // 评审项信息
        removeAuditModelExt(auditModelListAll);
        model.addAttribute("auditModelListAll", auditModelListAll);
        model.addAttribute("packExpertExtList", packExpertExtList);
        model.addAttribute("supplierExtList", supplierExtList);
        model.addAttribute("expertScoreList", expertScoreAll);
        // 成功标示
        model.addAttribute("flag", flag);
        return "bss/prms/package_expert";
    }

    /**
     * 
     * @Title: removeAuditModelExt
     * @author ShaoYangYang
     * @date 2016年11月18日 下午3:18:44
     * @Description: TODO 去重复
     * @param @param list
     * @return void
     */
    private static void removeAuditModelExt(List<AuditModelExt> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getScoreModelId()
                    .equals(list.get(i).getScoreModelId())) {
                    list.remove(j);
                }
            }
        }
    }


    /**
     * 
     *〈简述〉 添加专家。并设置组长
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param chkItem
     * @param packageExpert
     * @param groupId
     * @param attr
     * @return
     */
    @RequestMapping("relate")
    public String relate(String packageId, String groupId, RedirectAttributes attr,HttpServletRequest sq,String flowDefineId) {
        //获取关联信息
        Packages packages = new Packages();
        packages.setId(packageId);
        List<Packages> find = packageService.find(packages);
        String projectId = ""; 
        if (find != null && find.size() !=0 ){
            projectId=find.get(0).getProjectId();
        // 项目抽取的专家信息
            ProjectExtract projectExtract = new ProjectExtract();
            projectExtract.setProjectId(packageId);
            projectExtract.setIsProvisional((short)1);
            projectExtract.setReason("1");
            List<ProjectExtract> expertList = projectExtractService.list(projectExtract);
            for (ProjectExtract projectExtract2 : expertList) {

                PackageExpert packageExpert = new PackageExpert();
                // 设置专家id
                packageExpert.setExpertId(projectExtract2.getExpert().getId());
                packageExpert.setPackageId(packageId);
                packageExpert.setProjectId(projectId);
                // 评审状态 未评审
                packageExpert.setIsAudit((short) 0);
                // 初审是否汇总 未汇总
                packageExpert.setIsGather((short) 0);
                // 是否评分
                packageExpert.setIsGrade((short) 0);
                // 评分是否汇总
                packageExpert.setIsGatherGather((short) 0);
                // 判断组长id是否和选择的专家id一致，如果一致就设定为组长
                if (groupId.equals(projectExtract2.getExpert().getId())) {
                    packageExpert.setIsGroupLeader((short) 1);
                } else {
                    packageExpert.setIsGroupLeader((short) 0);
                }

                Map<String, Object> maps = new HashMap<String, Object>();
                maps.put("expertId", projectExtract2.getExpert().getId());
                maps.put("packageId", packageId);
                List<PackageExpert> selectList2 = service.selectList(maps);
                if (selectList2 != null && selectList2.size() != 0 ){
                    //如果和本次相同就不进行修改
                    if (selectList2.get(0).getIsGroupLeader() != packageExpert.getIsGroupLeader()){
                        service.updateByBean(packageExpert);
                    }  
                } else {
                    service.save(packageExpert);
                }

            }
            //修改流程状态
            flowMangeService.flowExe(sq, flowDefineId, projectId, 2);
        }
        return "redirect:/packageExpert/assignedExpert.html?projectId=" + projectId + "&&flowDefineId=" + flowDefineId;
    }

   
    /**
     * @Title: getPace
     * @author ShaoYangYang
     * @date 2016年10月27日 下午8:13:46
     * @Description: TODO 初审汇总
     * @param @param projectId
     * @param @param packageId
     * @param @param expertId
     * @return void
     * @throws IOException 
     */
    @RequestMapping("gather")
    @ResponseBody
    public void getPace(String projectId, String packageIds, HttpServletResponse response) throws IOException {
        try {
            PackageExpert record = new PackageExpert();
            String[] packageIdArr = packageIds.split(",");
            String msg = "";
            //遍历选中的包
            if (packageIdArr != null && packageIdArr.length > 0) {
                for (String packageId : packageIdArr) {
                    HashMap<String, Object> packmap = new HashMap<String, Object>();
                    packmap.put("id", packageId);
                    List<Packages> packages = packageService.findPackageById(packmap);
                    Packages pa = new Packages();
                    if (packages != null && packages.size() > 0) {
                        pa = packages.get(0);
                    }
                    Map<String, Object> map = new HashMap<>();
                    map.put("projectId", projectId);
                    map.put("packageId", packageId);
                    // 查询包关联专家实体
                    List<PackageExpert> selectList = packageExpertService.selectList(map);
                    if (selectList != null && selectList.size() > 0) {
                        int count2 = 0;
                        for (PackageExpert packageExpert : selectList) {
                            //判断为审核过的 和未汇总的 才执行汇总
                            if (packageExpert.getIsAudit() == SONE && packageExpert.getIsGather() != SONE) {
                                count2 ++;
                            } else {
                                break;
                            }
                        }
                        if (count2 == selectList.size()) {
                            record.setIsGather((short) 1);
                            record.setPackageId(packageId);
                            record.setProjectId(projectId);
                            service.updateByBean(record);
                            String str = "【"+pa.getName()+"】";
                            msg += str;
                        }
                    }
                }
            }
            msg += "汇总完成";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(msg);
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            response.getWriter().close();
        }
    }

    /**
     * 
     * @Title: isBack
     * @author ShaoYangYang
     * @date 2016年10月27日 下午8:13:46
     * @Description: TODO 初审退回
     * @param @param projectId
     * @param @param packageId
     * @param @param expertId
     * @return void
     */
    @RequestMapping("isBack")
    @ResponseBody
    public void isBack(PackageExpert record, HttpServletResponse response) {
        try {
            // 查询是否已评审
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("expertId", record.getExpertId());
            map.put("packageId", record.getPackageId());
            map.put("projectId", record.getProjectId());
            List<PackageExpert> selectList = service.selectList(map);
            if (selectList != null && selectList.size() > 0) {
                PackageExpert packageExpert = selectList.get(0);
                // 必须是已评审 但未评分的数据才能退回
                if (packageExpert.getIsAudit() != SONE
                    || packageExpert.getIsGrade() == SONE) {

                    response.getWriter().print("0");
                } else {
                    // 初审结果集合
                    List<ReviewFirstAudit> reviewFIrstAuditList = reviewFirstAuditService
                        .selectList(map);
                    // 判断是否全部通过，如果全部通过则不允许退回
                    int count = 0;
                    for (ReviewFirstAudit reviewFirstAudit : reviewFIrstAuditList) {
                        // 为1 证明有不合格数据
                        if (reviewFirstAudit.getIsPass() == SONE) {
                            count++;
                        }
                    }
                    if (count == 0) {
                        response.getWriter().print("0");
                        return;
                    }
                    // 查询是否已评审
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("packageId", record.getPackageId());
                    map2.put("projectId", record.getProjectId());
                    // 该专家下的审核项目关联集合
                    List<PackageExpert> packageExpertList = packageExpertService
                        .selectList(map2);
                    // 判断是否为全部已评审状态
                    for (PackageExpert packageExpert2 : packageExpertList) {
                        if (packageExpert2.getIsAudit() == SONE) {
                            // 查询改项目的进度信息
                            List<ReviewProgress> reviewProgressList = reviewProgressService
                                .selectByMap(map2);
                            // 更新项目进度
                            if (reviewProgressList != null
                                && reviewProgressList.size() > 0) {
                                ReviewProgress progress = reviewProgressList
                                    .get(0);
                                // 修改进度
                                if (packageExpertList != null
                                    && packageExpertList.size() > 0) {
                                    // 计算当前专家占用的进度比重
                                    double first = 1 / (double) packageExpertList
                                        .size();
                                    BigDecimal b = new BigDecimal(first);
                                    double firstProgress = b.setScale(2,
                                        BigDecimal.ROUND_HALF_UP)
                                        .doubleValue();
                                    // 计算退回后的初审进度
                                    Double firstAuditProgress = progress
                                        .getFirstAuditProgress();
                                    // 最终进度
                                    double endFirst = firstAuditProgress
                                        - firstProgress;
                                    // 退回后的初审进度
                                    progress.setFirstAuditProgress(endFirst);
                                    // 初审退回 评分进度清空 重新评分
                                    // progress.setScoreProgress((double) 0.0);
                                    // 总进度比例
                                    double totalProgress = (firstProgress + progress
                                        .getScoreProgress()) / 2;
                                    // 当前总进度
                                    Double totalProgress2 = progress
                                        .getTotalProgress();
                                    // 计算退回之后的总进度
                                    progress.setTotalProgress(totalProgress2
                                        - totalProgress);

                                    // 修改
                                    reviewProgressService
                                    .updateByPrimaryKeySelective(progress);
                                }
                            }
                        }
                    }
                    Short flag = 0;
                    record.setIsGather(flag);
                    record.setIsAudit(flag);
                    service.updateByBean(record);
                    response.getWriter().print("1");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 
     * @Title: isBackScore
     * @author ShaoYangYang
     * @date 2016年10月27日 下午8:13:46
     * @Description: TODO 评分确认或退回
     * @param @param projectId
     * @param @param packageId
     * @param @param expertId
     * @return void
     */
    @RequestMapping("isBackScore")
    @ResponseBody
    public void isBackScore(String projectId, String packageId,
                            String supplierId, String scoreModelId, Integer flag,
                            HttpServletResponse response) {
        try {
            // 查询是否已评审
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("projectId", projectId);
            map.put("packageId", packageId);
            // 1为退回
            if (flag == ONE) {
                // 判断能不能退回
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("projectId", projectId);
                map2.put("packageId", packageId);
                map2.put("supplierId", supplierId);
                map2.put("scoreModelId", scoreModelId);
                List<ExpertScore> expertScoreList = expertScoreService
                    .selectByMap(map2);
                if (expertScoreList == null || expertScoreList.size() == 0) {
                    // 为空证明没有评分数据 则不能退回
                    response.getWriter().print("tuihui");
                    return;
                } else {
                    for (ExpertScore expertScore : expertScoreList) {
                        BigDecimal score = expertScore.getScore();
                        // 如果有没有得分数据的也证明没有评分
                        if (score == null) {
                            response.getWriter().print("tuihui");
                            return;
                        }
                    }
                }
                // 修改进度
                reviewProgressService.updateProgress(map);
                // 修改评分状态为 未评分
                packageExpertService.updateScore(map);
                // 修改AduitQuota的round状态为退回
                aduitQuotaService.updateStatus(projectId, packageId,
                    supplierId, scoreModelId, flag);
                response.getWriter().print("tuihuisuccess");
            } else {
                // 确认 保存最终得分 分数不一致则不执行保存
                String message = aduitQuotaService.updateStatus(projectId,
                    packageId, supplierId, scoreModelId, flag);
                response.getWriter().print(message);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 
     * @Title: supplierQuote
     * @author ShaoYangYang
     * @date 2016年11月11日 下午2:46:47
     * @Description: TODO 查看供应商报价
     * @param @param packageId
     * @param @param projectId
     * @param @return
     * @return String
     */
    @RequestMapping("supplierQuote")
    public String supplierQuote(String projectId, String supplierId,
                                Model model, Quote quote, String timestamp) {
        try {
            Project project = projectService.selectById(projectId);
            DictionaryData dd = DictionaryDataUtil.findById(project.getPurchaseType());
            model.addAttribute("purchaserType", dd.getCode());
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("projectId", projectId);
            List<Packages> listPackage = supplierQuoteService
                .selectByPrimaryKey(map, null);
            List<Packages> listPackageEach = new ArrayList<Packages>();
            SaleTender saleTender = new SaleTender();
            saleTender.setProjectId(projectId);
            saleTender.setSupplierId(supplierId);
            List<SaleTender> sts = saleTenderService.find(saleTender);
            if (sts != null && sts.size() > 0) {
                String packageStr = sts.get(0).getPackages();
                for (Packages packages : listPackage) {
                    if (packageStr.indexOf(packages.getId()) != -1) {
                        listPackageEach.add(packages);
                    }
                }
            }
            List<List<Quote>> listQuote = new ArrayList<List<Quote>>();
            // 查询时间
            Quote quote2 = new Quote();
            quote2.setSupplierId(supplierId);
            quote.setProjectId(projectId);
            List<Date> listDate = supplierQuoteService.selectQuoteCount(quote2);
            for (Packages pk : listPackageEach) {
                if (StringUtils.isNotEmpty(timestamp)) {
                    // 如果传递时间 就按照时间查询
                    quote.setCreatedAt(new Timestamp(new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss").parse(timestamp).getTime()));
                } else {
                    if (listDate != null && listDate.size() > 0) {
                        // 否则就查询最后一次报价
                        Date date = listDate.get(listDate.size() - 1);
                        // SimpleDateFormat sdf = new
                        // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        // String formatDate = sdf.format(date);
                        Timestamp timestamp2 = new Timestamp(date.getTime());
                        quote.setCreatedAt(timestamp2);
                    }
                }
                quote.setPackageId(pk.getId());
                List<Quote> quoteList = supplierQuoteService
                    .selectQuoteHistoryList(quote);
                listQuote.add(quoteList);
            }
            model.addAttribute("listPackage", listPackageEach);
            model.addAttribute("listQuote", listQuote);
            model.addAttribute("listDate", listDate);
            model.addAttribute("length", listDate.size());
            model.addAttribute("projectId", projectId);
            model.addAttribute("supplierId", supplierId);
            if (timestamp != null) {
                model.addAttribute("timestamp", new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss").parse(timestamp));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "bss/prms/view_quote";
    }

    /**
     * 
     * 〈简述〉评分汇总 〈详细描述〉
     * 
     * @author WangHuijie
     * @param packageId
     * @param projectId
     * @return
     */
    @RequestMapping("/scoreTotal")
    @ResponseBody
    public void scoreTotal(String packageId, String projectId) {
        //评分办法计算
        //包内供应商总得分
        BigDecimal totalScore = new BigDecimal(0);
        //包内供应商平均得分
        BigDecimal totalScoreAver = new BigDecimal(0);
        //低于标准的分数
        BigDecimal totalScoreStandard = new BigDecimal(0);
        //包内供应商总报价
        BigDecimal totalPrice = new BigDecimal(0);
        //包内供应商平均报价
        BigDecimal totalPriceAver = new BigDecimal(0);
        //高于报价的金额数
        BigDecimal totalPriceStandard = new BigDecimal(0);
        //报价比例
        BigDecimal totalPricePercent = new BigDecimal(0);
        //评分比例
        BigDecimal totalScorePercent = new BigDecimal(0);
        
        // 供应商信息
        List<SaleTender> allSupplierList = saleTenderService.list(new SaleTender(projectId), 0);
        List<SaleTender> supplierList = new ArrayList<SaleTender>();
        for (int i = 0; i < allSupplierList.size(); i++) {
            SaleTender sale = allSupplierList.get(i);
            if (sale.getPackages().contains(packageId) && sale.getIsFirstPass() == 1 && !"1".equals(sale.getIsRemoved())) {
                supplierList.add(sale);
            }
        }
        expertScoreService.gather(packageId, projectId, supplierList);
        // 将供应商的经济技术总分存入SaleTender表中
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("packageId", packageId);
        int supplierNum0 = supplierList.size();
        BigDecimal supplierNum = new BigDecimal(supplierNum0);
        //计算所有供应商总分
        totalScore = getTotalScore(supplierList, map);
        totalScoreAver = totalScore.divide(supplierNum);
        totalScoreStandard = totalScoreAver.multiply(totalScorePercent);
        //计算总报价
        totalPrice = getTotalPrice(packageId, projectId, supplierList);
        totalPriceAver = totalPrice.divide(supplierNum);
        totalPriceStandard = totalPriceAver.multiply(totalPricePercent);
        for (SaleTender saleTender : supplierList) {
            String msg = "";
            map.put("supplierId", saleTender.getSuppliers().getId());
            List<ExpertScore> scoreList = expertScoreService.selectByMap(map);
            // 去重
            removeRankSame(scoreList);
            BigDecimal economicScore = new BigDecimal(0);
            BigDecimal technologyScore = new BigDecimal(0);
            for (ExpertScore score : scoreList) {
                ScoreModel scoModel = new ScoreModel();
                scoModel.setId(score.getScoreModelId());
                // 根据id查看scoreModel对象
                ScoreModel scoreModel = scoreModelService.findScoreModelByScoreModel(scoModel);
                if (scoreModel != null) {
                    MarkTerm mt = null;
                    if (scoreModel.getMarkTermId() != null && !"".equals(scoreModel.getMarkTermId())){
                        mt = markTermService.findMarkTermById(scoreModel.getMarkTermId());
                        if (mt.getTypeName() == null || "".equals(mt.getTypeName())) {
                            mt = markTermService.findMarkTermById(mt.getPid());
                        }
                    }
                    DictionaryData data = dictionaryDataServiceI.getDictionaryData(mt.getTypeName());
                    if ("ECONOMY".equals(data.getCode())) {
                        // 经济
                        economicScore = economicScore.add(score.getScore());
                    } else if ("TECHNOLOGY".equals(data.getCode())) {
                        // 技术
                        technologyScore = technologyScore.add(score.getScore());
                    }
                }
            }
            // 将算好的总分放入map
            map.put("economicScore", economicScore);
            map.put("technologyScore", technologyScore);
            //是否偏离
            int flag = 0;
            //该供应商总报价
            BigDecimal totalPriceSupplier = new BigDecimal(0);
            //四种评分办法计算
            BidMethod condition = new BidMethod();
            condition.setProjectId(projectId);
            condition.setPackageId(packageId);
            List<BidMethod> bmList = bidMethodService.findScoreMethod(condition);
            List<DictionaryData> ddList = DictionaryDataUtil.find(27);
            ddList.get(Integer.parseInt(bmList.get(0).getTypeName()));
            if (bmList != null && bmList.size() > 0 && ddList != null && ddList.size() > 0) {
              Integer position = Integer.parseInt(bmList.get(0).getTypeName());
              String aduitMethodCode = ddList.get(position).getCode();
              //综合评分法
              if ("OPEN_ZHPFF".equals(aduitMethodCode)) {
                totalScorePercent = bmList.get(0).getBusiness().divide(new BigDecimal(100));
                totalPricePercent = bmList.get(0).getValid().divide(new BigDecimal(100));
                //该供应商平均分偏离计算
                BigDecimal totalSupplier = new BigDecimal(0);
                totalSupplier= totalSupplier.add(economicScore);
                totalSupplier= totalSupplier.add(technologyScore);
                if (totalSupplier.compareTo(totalScoreAver) == -1) {
                    //小于平均分的分数
                    BigDecimal v = totalScoreAver.subtract(totalSupplier);
                    //如果偏离值大于标准偏离值
                    if (totalScoreStandard.compareTo(v) == -1) {
                      BigDecimal percent = totalScorePercent.multiply(new BigDecimal(100));
                      msg += "经济技术平均得分低于有效经济技术平均得分的"+percent+"%.";
                      flag = 1;
                    }
                } else {
                  msg += "";
                }
                //该供应商平均报价偏离计算
                Supplier supplier = saleTender.getSuppliers();
                Quote quote = new Quote();
                quote.setProjectId(projectId);
                quote.setPackageId(packageId);
                quote.setSupplierId(supplier.getId());
                List<Quote> allQuote = supplierQuoteService.get(quote);
                if (allQuote != null && allQuote.size()>0) {
                    if (allQuote.get(0).getQuotePrice() == null) {
                      totalPriceSupplier = totalPriceSupplier.add(allQuote.get(0).getTotal());
                    } else {
                        BigDecimal totalPrice2 = BigDecimal.ZERO;
                        for (Quote q : allQuote) {
                            totalPrice2 = q.getQuotePrice().add(totalPrice2);
                        }
                        totalPriceSupplier = totalPriceSupplier.add(totalPrice2);
                    }
                }
                if (totalPriceSupplier.compareTo(totalPriceAver) == -1) {
                    //小于平均分的分数
                    BigDecimal v = totalPriceAver.subtract(totalPriceSupplier);
                    //如果偏离值大于标准偏离值
                    if (totalPriceStandard.compareTo(v) == -1) {
                      BigDecimal percent = totalScorePercent.multiply(new BigDecimal(100));
                      msg += "报价高于有效平均报价的"+percent+"%.";
                      flag = 1;
                    }
                } else {
                  msg += "";
                }
              }
              //基准价法
              if ("PBFF_JZJF".equals(aduitMethodCode)) {
                
              }
              //性价比法
              if ("PBFF_XJBF".equals(aduitMethodCode)) {
                
              }
              //最低价法
              if ("PBFF_ZDJF".equals(aduitMethodCode)) {
                
              }
              
            }
            // 2.向SUPPLIER_CHECK_PASS表中插入不偏离数据
            if (flag == 0) {
              BigDecimal totalSupplier = new BigDecimal(0);
              totalSupplier= totalSupplier.add(economicScore);
              totalSupplier= totalSupplier.add(technologyScore);
              SupplierCheckPass record = new SupplierCheckPass();
              record.setId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
              record.setPackageId(packageId);
              record.setProjectId(projectId);
              record.setSupplierId(saleTender.getSuppliers().getId());
              record.setTotalScore(totalSupplier);
              record.setTotalPrice(totalPriceSupplier.longValue());
              
              SupplierCheckPass checkPass = new SupplierCheckPass();
              checkPass.setPackageId(packageId);
              checkPass.setSupplierId(saleTender.getSuppliers().getId());
              //判断是否有旧数据
              List<SupplierCheckPass> oldList= checkPassService.listCheckPass(checkPass);
              if (oldList != null && oldList.size() > 0) {
                for (SupplierCheckPass supplierCheckPass : oldList) {
                  //删除原数据
                  checkPassService.delete(supplierCheckPass.getId());
                }
              }
              checkPassService.insert(record);
            }
            map.put("reviewResult", msg);
            saleTenderService.editSumScore(map);
        }
    }
    
    //计算供应商总得分
    BigDecimal getTotalScore(List<SaleTender> supplierList, Map<String, Object> map){
      //包内供应商总得分
      BigDecimal totalScore = new BigDecimal(0);  
      for (SaleTender saleTender : supplierList) {
          map.put("supplierId", saleTender.getSuppliers().getId());
          List<ExpertScore> scoreList = expertScoreService.selectByMap(map);
          // 去重
          removeRankSame(scoreList);
          BigDecimal economicScore = new BigDecimal(0);
          BigDecimal technologyScore = new BigDecimal(0);
          for (ExpertScore score : scoreList) {
              ScoreModel scoModel = new ScoreModel();
              scoModel.setId(score.getScoreModelId());
              // 根据id查看scoreModel对象
              ScoreModel scoreModel = scoreModelService.findScoreModelByScoreModel(scoModel);
              if (scoreModel != null) {
                  MarkTerm mt = null;
                  if (scoreModel.getMarkTermId() != null && !"".equals(scoreModel.getMarkTermId())){
                      mt = markTermService.findMarkTermById(scoreModel.getMarkTermId());
                      if (mt.getTypeName() == null || "".equals(mt.getTypeName())) {
                          mt = markTermService.findMarkTermById(mt.getPid());
                      }
                  }
                  DictionaryData data = dictionaryDataServiceI.getDictionaryData(mt.getTypeName());
                  if ("ECONOMY".equals(data.getCode())) {
                      // 经济
                      economicScore = economicScore.add(score.getScore());
                  } else if ("TECHNOLOGY".equals(data.getCode())) {
                      // 技术
                      technologyScore = technologyScore.add(score.getScore());
                  }
              }
          }
          // 将算好的总分放入map
          map.put("economicScore", economicScore);
          map.put("technologyScore", technologyScore);
          totalScore = totalScore.add(economicScore);
          totalScore = totalScore.add(technologyScore);
        }
        return totalScore;
    }
    
    //计算供应商总报价
    BigDecimal getTotalPrice(String packageId, String projectId, List<SaleTender> supplierList){
      BigDecimal totalPrice = new BigDecimal(0);
      for (SaleTender sale : supplierList) {
        BigDecimal totalPriceSupplier = new BigDecimal(0);
        Supplier supplier = sale.getSuppliers();
        Quote quote = new Quote();
        quote.setProjectId(projectId);
        quote.setPackageId(packageId);
        quote.setSupplierId(supplier.getId());
        List<Quote> allQuote = supplierQuoteService.get(quote);
        if (allQuote != null && allQuote.size()>0) {
            if (allQuote.get(0).getQuotePrice() == null) {
              totalPriceSupplier = totalPriceSupplier.add(allQuote.get(0).getTotal());
            } else {
                BigDecimal totalPrice2 = BigDecimal.ZERO;
                for (Quote q : allQuote) {
                    totalPrice2 = q.getQuotePrice().add(totalPrice2);
                }
                totalPriceSupplier = totalPriceSupplier.add(totalPrice2);
            }
        }
        totalPrice = totalPrice.add(totalPriceSupplier);
      }
      return totalPrice;
    }
    

    /**
     *〈简述〉查看专家对各供应商的初审明细
     *〈详细描述〉
     * @author Ye MaoLin
     * @param id 专家id
     * @param model
     * @param packageId 包id
     * @param projectId 项目id
     * @return
     */
    @RequestMapping("/viewByExpert")
    public String viewByExpert(String id, Model model, String packageId, String projectId, String flowDefineId){
        Expert expert = expertService.selectByPrimaryKey(id);
        //创建封装的实体
        Extension extension = new Extension();
        HashMap<String ,Object> map = new HashMap<String ,Object>();
        map.put("projectId", projectId);
        map.put("id", packageId);
        //查询包信息
        List<Packages> list = packageService.findPackageById(map);
        if(list!=null && list.size()>0){
            Packages packages = list.get(0);
            //放入包信息
            extension.setPackageId(packages.getId());
            extension.setPackageName(packages.getName());
        }
        //查询项目信息
        Project project = projectService.selectById(projectId);
        if(project!=null){
            //放入项目信息
            extension.setProjectId(project.getId());
            extension.setProjectName(project.getName());
            extension.setProjectCode(project.getProjectNumber());
        }

        //查询改包下的初审项信息
        Map<String,Object> map2 = new HashMap<>();
        map2.put("projectId", projectId);
        map2.put("packageId", packageId);
        //查询出该包下的初审项id集合
        List<PackageFirstAudit> packageAuditList = packageFirstAuditService.selectList(map2);
        //创建初审项的集合
        List<FirstAudit> firstAuditList = new ArrayList<FirstAudit>();
        if(packageAuditList!=null && packageAuditList.size()>0){
            for (PackageFirstAudit packageFirst : packageAuditList) {
                //根据初审项的id 查询出初审项的信息放入集合
                FirstAudit firstAudits = firstAuditService.get(packageFirst.getFirstAuditId());
                firstAuditList.add(firstAudits);
            }
        }
        //放入初审项集合
        extension.setFirstAuditList(firstAuditList);
        //查询供应商信息
        List<SaleTender> supplierList = saleTenderService.list(new SaleTender(projectId), 0);
        extension.setSupplierList(supplierList);

        //查询审核过的信息用于回显
        Map<String, Object> reviewFirstAuditMap = new HashMap<String, Object>();
        reviewFirstAuditMap.put("projectId", projectId);
        reviewFirstAuditMap.put("packageId", packageId);
        reviewFirstAuditMap.put("expertId", expert.getId());
        List<ReviewFirstAudit> reviewFirstAuditList = reviewFirstAuditService.selectList(reviewFirstAuditMap);
        //回显信息放进去
        model.addAttribute("reviewFirstAuditList", reviewFirstAuditList);
        //把封装的实体放入域中
        model.addAttribute("extension", extension);
        model.addAttribute("expert", expert);
        model.addAttribute("packageId", packageId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/prms/first_audit/first_audit_expert_view";
    }
    
    /**
     *〈简述〉查看所有专家对供应商的初审明细
     *〈详细描述〉
     * @author Ye MaoLin
     * @param supplierId 供应商id
     * @param model
     * @param packageId 包id
     * @param projectId 项目id
     * @return
     */
    @RequestMapping("/viewBySupplier")
    public String viewBySupplier(String supplierId, Model model, String packageId, String projectId, String flowDefineId){
        Supplier supplier = supplierService.selectById(supplierId);
        //创建封装的实体
        Extension extension = new Extension();
        HashMap<String ,Object> map = new HashMap<String ,Object>();
        map.put("projectId", projectId);
        map.put("id", packageId);
        //查询包信息
        List<Packages> list = packageService.findPackageById(map);
        if(list!=null && list.size()>0){
            Packages packages = list.get(0);
            //放入包信息
            extension.setPackageId(packages.getId());
            extension.setPackageName(packages.getName());
        }
        //查询项目信息
        Project project = projectService.selectById(projectId);
        if(project!=null){
            //放入项目信息
            extension.setProjectId(project.getId());
            extension.setProjectName(project.getName());
            extension.setProjectCode(project.getProjectNumber());
        }

        //查询改包下的初审项信息
        Map<String,Object> map2 = new HashMap<>();
        map2.put("projectId", projectId);
        map2.put("packageId", packageId);
        //查询出该包下的初审项id集合
        List<PackageFirstAudit> packageAuditList = packageFirstAuditService.selectList(map2);
        //创建初审项的集合
        List<FirstAudit> firstAuditList = new ArrayList<FirstAudit>();
        if(packageAuditList!=null && packageAuditList.size()>0){
            for (PackageFirstAudit packageFirst : packageAuditList) {
                //根据初审项的id 查询出初审项的信息放入集合
                FirstAudit firstAudits = firstAuditService.get(packageFirst.getFirstAuditId());
                firstAuditList.add(firstAudits);
            }
        }
        //放入初审项集合
        extension.setFirstAuditList(firstAuditList);
        //查询专家初审记录
        Map<String, Object> rfamap = new HashMap<>();
        map.put("projectId", projectId);
        map.put("packageId", packageId);
        map.put("supplierId", supplierId);
        List<ReviewFirstAudit> reviewFirstAuditList = reviewFirstAuditService.selectList(rfamap);
        List<Expert> experts = new ArrayList<Expert>();
        HashMap<String, Object> packageExpertMap = new HashMap<String, Object>();
        packageExpertMap.put("projectId", projectId);
        packageExpertMap.put("packageId", packageId);
        //获取包下所有专家
        List<PackageExpert> packageExperts = packageExpertService.selectList(packageExpertMap);
        for (PackageExpert packageExpert : packageExperts) {
            experts.add(packageExpert.getExpert());
        }
        //回显信息放进去
        model.addAttribute("reviewFirstAuditList", reviewFirstAuditList);
        //把封装的实体放入域中
        model.addAttribute("experts", experts);
        model.addAttribute("extension", extension);
        model.addAttribute("supplier", supplier);
        model.addAttribute("packageId", packageId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/prms/first_audit/first_audit_supplier_view";
    }

    /**
    *〈简述〉跳转到初审页面
    *〈详细描述〉
    * @author Ye MaoLin
    * @param projectId 项目id
    * @param model
    * @param flowDefineId 流程环节id
    * @return
    */
   @RequestMapping("/toFirstAudit")
   public String toFirstAudit(String projectId, Model model, String flowDefineId){
//       Map<String, Object> map = new HashMap<String, Object>();
//       map.put("projectId", projectId);
       // 进度集合
      // List<ReviewProgress> reviewProgressList = reviewProgressService.selectByMap(map);
       List<ReviewProgress> reviewProgressList = new ArrayList<ReviewProgress>();
       List<Packages> packages = packageService.listResultExpert(projectId);
       for (Packages pg : packages) {
           Map<String, Object> map2 = new HashMap<String, Object>();
           map2.put("projectId", projectId);
           map2.put("packageId", pg.getId());
           //查询该包有没有评审进度数据
           List<ReviewProgress> rplist = reviewProgressService.selectByMap(map2);
           Map<String, Object> pemap = new HashMap<>();
           pemap.put("projectId", projectId);
           pemap.put("packageId", pg.getId());
           // 查询包关联专家实体
           List<PackageExpert> selectList = packageExpertService.selectList(pemap);
           if (rplist == null || rplist.size() <= 0) {
               //如果该包进度不存在
               ReviewProgress reviewProgress = new ReviewProgress();
               reviewProgress.setAuditStatus("0");
               reviewProgress.setFirstAuditProgress(0.00);
               reviewProgress.setPackageId(pg.getId());
               reviewProgress.setPackageName(pg.getName());
               reviewProgress.setProjectId(projectId);
               reviewProgress.setScoreProgress(0.00);
               reviewProgress.setTotalProgress(0.00);
               reviewProgress.setIsGather(0);
               reviewProgressList.add(reviewProgress);
           } else {
               //是否汇总  0:未汇总 1：已汇总
               Integer isGather = 1;
               if (selectList != null && selectList.size() > 0) {
                   for (PackageExpert packageExpert : selectList) {
                       if (packageExpert.getIsGather() != SONE) {
                           isGather = 0;
                           break;
                       }
                   }
               }
               ReviewProgress reviewProgress = rplist.get(0);
               reviewProgress.setIsGather(isGather);
               reviewProgressList.add(reviewProgress);
           }
       }
       // 包信息
       model.addAttribute("packageList", packages);
       // 进度
       model.addAttribute("reviewProgressList", reviewProgressList);
       model.addAttribute("projectId", projectId);
       model.addAttribute("flowDefineId", flowDefineId);
       return "bss/prms/first_audit/list";
   }

    /**
     *〈简述〉查看包的初审情况
     *〈详细描述〉
     * @author Ye MaoLin
     * @param packageId 包id 
     * @param projectId 项目id
     * @param model
     * @return
     */
    @RequestMapping("/firstAuditView")
    public String firstAuditView(String packageId, String projectId, Model model, String flowDefineId){
        //包与专家 关联表集合
        Map<String, Object> packageExpertmap = new HashMap<String, Object>();
        packageExpertmap.put("packageId", packageId);
        packageExpertmap.put("projectId", projectId);
        //查询专家
        List<PackageExpert> expertIdList = packageExpertService.selectList(packageExpertmap);
        if (expertIdList != null && expertIdList.size() > 0) {
          Short isEnd = expertIdList.get(0).getIsGatherGather();
          model.addAttribute("isEnd", isEnd);
        }
        // 供应商信息
        List<SaleTender> supplierList = new ArrayList<SaleTender>();
        List<SaleTender> sl = saleTenderService.list(new SaleTender(projectId), 0);
        for (SaleTender st : sl) {
            if (st.getPackages().indexOf(packageId) != -1) {
                supplierList.add(st);
            }
        }
        // 关联信息集合
        // 封装实体
        List<PackExpertExt> packExpertExtList = new ArrayList<>();
        // 供应商封装实体
        List<SupplierExt> supplierExtList = new ArrayList<>();
        PackExpertExt packExpertExt;
        for (PackageExpert packageExpert : expertIdList) {
            packExpertExt = new PackExpertExt();
            Expert expert = expertService.selectByPrimaryKey(packageExpert.getExpertId());
            packExpertExt.setExpert(expert);
            packExpertExt.setPackageId(packageExpert.getPackageId());
            packExpertExt.setProjectId(packageExpert.getProjectId());
            // 根据供应商id 和包id查询审核表 确定该供应商是否通过评审
            for (SaleTender saleTender : supplierList) {
                SupplierExt supplierExt = new SupplierExt();
                Map<String, Object> map2 = new HashMap<>();
                map2.put("supplierId", saleTender.getSuppliers().getId());
                map2.put("packageId", packageExpert.getPackageId());
                map2.put("expertId", packageExpert.getExpertId());
                map2.put("isBack", 0);
                List<ReviewFirstAudit> selectList2 = reviewFirstAuditService.selectList(map2);
                if (selectList2 != null && selectList2.size() > 0) {
                    int count2 = 0;
                    for (ReviewFirstAudit reviewFirstAudit : selectList2) {
                        if (reviewFirstAudit.getIsPass() == SONE) {
                            count2++;
                            break;
                        }
                    }
                    // 如果变量大于0 说明有不合格的数据
                    if (count2 > 0) {
                        supplierExt.setSupplierId(saleTender.getSuppliers().getId());
                        supplierExt.setExpertId(packageExpert.getExpertId());
                        supplierExt.setPackageId(packageExpert.getPackageId());
                        //判断专家是否提交
                        if (packageExpert.getIsAudit() == 1) {
                          //已提交的话显示评审结果
                          supplierExt.setSuppIsPass("0");
                        } else {
                          //未提交的话显示为未提交
                          supplierExt.setSuppIsPass("2");
                        }
                    } else {
                        supplierExt.setSupplierId(saleTender.getSuppliers().getId());
                        supplierExt.setExpertId(packageExpert.getExpertId());
                        supplierExt.setPackageId(packageExpert.getPackageId());
                        //判断专家是否提交
                        if (packageExpert.getIsAudit() == 1) {
                          //已提交的话显示评审结果
                          supplierExt.setSuppIsPass("1");
                        } else {
                          //未提交的话显示为未提交
                          supplierExt.setSuppIsPass("2");
                        }
                    }
                } else {
                    supplierExt
                    .setSupplierId(saleTender.getSuppliers().getId());
                    supplierExt.setPackageId(packageExpert.getPackageId());
                    supplierExt.setExpertId(packageExpert.getExpertId());
                    supplierExt.setSuppIsPass("2");
                }

                supplierExtList.add(supplierExt);
            }
            packExpertExtList.add(packExpertExt);
        }

        HashMap<String, Object> packmap = new HashMap<String, Object>();
        packmap.put("id", packageId);
        List<Packages> packages = packageService.findPackageById(packmap);
        if (packages != null && packages.size() > 0 ) {
            model.addAttribute("pack", packages.get(0));
        }
        model.addAttribute("packageId", packageId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("supplierList", supplierList);
        model.addAttribute("supplierExtList", supplierExtList);
        model.addAttribute("packExpertExtList", packExpertExtList);
        return "bss/prms/first_audit/view";
    }

    /**
     *〈简述〉跳转到详细打分页面
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId 项目id
     * @param model
     * @param flowDefineId 流程环节id
     * @return
     */
    @RequestMapping("/toScoreAudit")
    public String toScoreAudit(String projectId, Model model, String flowDefineId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        // 进度集合
        List<ReviewProgress> reviewProgressList = reviewProgressService.selectByMap(map);
        List<Packages> packages = packageService.listResultExpert(projectId);
        if (reviewProgressList.size() < packages.size()) {
            for (Packages pg : packages) {
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("projectId", projectId);
                map2.put("packageId", pg.getId());
                //查询该包有没有评审进度数据
                List<ReviewProgress> rplist = reviewProgressService.selectByMap(map2);
                if (rplist == null || rplist.size() <= 0) {
                    ReviewProgress reviewProgress = new ReviewProgress();
                    reviewProgress.setAuditStatus("0");
                    reviewProgress.setFirstAuditProgress(0.00);
                    reviewProgress.setPackageId(pg.getId());
                    reviewProgress.setPackageName(pg.getName());
                    reviewProgress.setProjectId(projectId);
                    reviewProgress.setScoreProgress(0.00);
                    reviewProgress.setTotalProgress(0.00);
                    reviewProgressList.add(reviewProgress);
                }
            }
        }
        for (ReviewProgress review : reviewProgressList) {
            map.put("packageId", review.getPackageId());
            List<PackageExpert> list = packageExpertService.selectList(map);
            if (list != null && list.size() > 0) {
                Integer isFinish = list.get(0).getIsGatherGather() == (short) 1 ? 1 : 0;
                review.setIsFinish(isFinish);
            }
        }
        // 包信息
        model.addAttribute("packageList", packages);
        model.addAttribute("projectId", projectId);
        // 进度
        model.addAttribute("reviewProgressList", reviewProgressList);
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/prms/score_audit/list";
    }
    
    /**
     *〈简述〉
     *〈详细描述〉
     * 供应商总排名(展示分包信息)
     * @author WangHuijie
     * @param projectId
     * @param model
     * @return
     */
    @RequestMapping("supplierRank")
    public String supplierRank(Packages packages, Model model, String flowDefineId){
        String projectId = packages.getProjectId();
        // 分包信息
        List<Packages> packagesList = packageService.find(packages);
        List<Packages> packList = new ArrayList<Packages>();
        // 去除经济技术评审没有结束的包
        Map<String, Object> map = new HashMap<String, Object>();
        for (Packages pack : packagesList) {
            map.put("packageId", pack.getId());
            List<PackageExpert> selectList = packageExpertService.selectList(map);
            if (selectList != null && selectList.size() > 0 && selectList.get(0).getIsGatherGather() == 1) {
                packList.add(pack);
            }
        }
        if (packList.size() != packagesList.size()) {
            // 判断有没有没显示出来的包
            model.addAttribute("flag", "1");
        }
        model.addAttribute("packagesList", packList);
        model.addAttribute("length", packList.size());
        // 供应商信息
        SaleTender saleTender = new SaleTender();
        List<SaleTender> supplierList = new ArrayList<SaleTender>();
        for (Packages pack : packList) {
            saleTender.setPackages(pack.getId());
            supplierList.addAll(saleTenderService.find(saleTender));
        }
        List<SaleTender> suppList = new ArrayList<SaleTender>();
        for (SaleTender supp : supplierList) {
            if (supp.getIsFirstPass() != null && supp.getIsFirstPass() == 1 && !"1".equals(supp.getIsRemoved())) {
                suppList.add(supp);
            }
        }
        model.addAttribute("supplierList", suppList);
        // 分数
        List<ExpertScore> scores = new ArrayList<ExpertScore>();
        Map<String, Object> searchMap = new HashMap<String, Object>();
        for (Packages pack : packList) {
            searchMap.put("packageId", pack.getId());
            scores.addAll(expertScoreService.selectByMap(searchMap));
        }
        removeRankSame(scores);
        // 供应商经济总分,技术总分,总分
        List<SupplierRank> rankList = new ArrayList<SupplierRank>();
        for (SaleTender supp : suppList) {
            SupplierRank rank = new SupplierRank();
            rank.setSupplierId(supp.getSuppliers().getId());
            rank.setPackageId(supp.getPackages());
            // 查询该供应商的经济总分
            //BigDecimal econScore = new BigDecimal(0);
            // 查询该供应商的技术总分
            //BigDecimal techScore = new BigDecimal(0);
            /*for (ExpertScore score : scores) {
                if (score.getSupplierId().equals(supp.getSuppliers().getId())) {
                    ScoreModel scoModel = new ScoreModel();
                    scoModel.setId(score.getScoreModelId());
                    // 根据id查看scoreModel对象
                    ScoreModel scoreModel1 = scoreModelService.findScoreModelByScoreModel(scoModel);
                    if (scoreModel1 != null) {
                        MarkTerm mt = null;
                        if (scoreModel1.getMarkTermId() != null && !"".equals(scoreModel1.getMarkTermId())){
                            mt = markTermService.findMarkTermById(scoreModel1.getMarkTermId());
                            if (mt.getTypeName() == null || "".equals(mt.getTypeName())) {
                                mt = markTermService.findMarkTermById(mt.getPid());
                            }
                        }
                        DictionaryData data = dictionaryDataServiceI.getDictionaryData(mt.getTypeName());
                        if ("ECONOMY".equals(data.getCode())) {
                            // 经济
                            econScore = econScore.add(score.getScore());
                        } else if ("TECHNOLOGY".equals(data.getCode())) {
                            // 技术
                            techScore = techScore.add(score.getScore());
                        }
                    }
                }
            }*/
            BigDecimal es = supp.getEconomicScore();
            if (es == null) {
              rank.setEconScore(null);
            } else {
              rank.setEconScore(es);
            }
            BigDecimal ts = supp.getTechnologyScore();
            if (ts == null) {
              rank.setTechScore(null);
            } else {
              rank.setTechScore(ts);
            }
            if (es == null || ts == null) {
              rank.setSumScore(null);
            } else {
              rank.setSumScore(supp.getEconomicScore().add(supp.getTechnologyScore()));
            }
            rankList.add(rank);
        }
        // 循环遍历判断名次
        for (SupplierRank rank : rankList) {
            int count = 0;
            int sum = 0;
            // 判断review_result是否不为空
            SaleTender saleTend = new SaleTender();
            saleTend.setPackages(rank.getPackageId());
            Supplier supplier = new Supplier();
            supplier.setId(rank.getSupplierId());
            saleTend.setSuppliers(supplier);
            String reviewResult = saleTenderService.findByCon(saleTend).get(0).getReviewResult();
            if (reviewResult != null && !"".equals(reviewResult)) {
                rank.setRank(0);
                rank.setReviewResult(reviewResult);
            } else {
                for (SupplierRank temp : rankList) {
                    if (rank.getPackageId().equals(temp.getPackageId())) {
                        // 判断review_result是否不为空
                        SaleTender sale = new SaleTender();
                        sale.setPackages(temp.getPackageId());
                        Supplier supp = new Supplier();
                        supp.setId(temp.getSupplierId());
                        sale.setSuppliers(supp);
                        String review = saleTenderService.findByCon(sale).get(0).getReviewResult();
                        if (review == null || "".equals(review)) {
                            sum++;
                            if (rank.getSumScore().compareTo(temp.getSumScore()) != -1 && rank != temp) {
                                count++;
                            }
                        }
                    }
                }
                rank.setRank(sum - count);
            }
        }
        model.addAttribute("rankList", rankList);
        // 项目中抽取的专家信息
        Map<String, Object> mapSearch1 = new HashMap<String, Object>(); 
        List<PackageExpert> expList = new ArrayList<PackageExpert>();
        for (Packages pack : packList) {
            mapSearch1.put("packageId", pack.getId());
            expList.addAll(packageExpertService.selectList(mapSearch1));
        }
        // 将专家进行排序,先经济,后技术
        List<PackageExpert> expertList = new ArrayList<PackageExpert>();
        for (PackageExpert exp : expList) {
            DictionaryData data = dictionaryDataServiceI.getDictionaryData(exp.getReviewTypeId());
            if (data != null && "ECONOMY".equals(data.getCode())) {
                expertList.add(exp);
            }
        }
        for (PackageExpert exp : expList) {
            DictionaryData data = dictionaryDataServiceI.getDictionaryData(exp.getReviewTypeId());
            if (data != null && "TECHNOLOGY".equals(data.getCode())) {
                
                expertList.add(exp);
            }
        }
        // 遍历排好序的expertList设置rowspan
        for (Packages pack : packList) {
            // 获取经济类型的个数
            int count = 0;
            // 该包内的专家总数
            int sumCount = 0;
            for (PackageExpert exp : expertList) {
                if (pack.getId().equals(exp.getPackageId())) {
                    sumCount++;
                    DictionaryData data = dictionaryDataServiceI.getDictionaryData(exp.getReviewTypeId());
                    if (data != null && "ECONOMY".equals(data.getCode())) {
                        count++;
                    }
                }
            }
            // 给指定位置设置rowspan
            int flag = 0;
            for (PackageExpert exp : expertList) {
                if (pack.getId().equals(exp.getPackageId())) {
                    if (count == 0 && flag == 0) {
                        // 如果没有经济类型,只有技术类型
                        exp.setCount(sumCount);
                    } else if (count == sumCount && flag == 0) {
                        // 如果全是经济类型
                        exp.setCount(sumCount);
                    } else if (count < sumCount && count > 0) {
                        // 都有
                        if (flag == 0) {
                            // 设置第一个rowspan为经济的个数
                            exp.setCount(count);
                        } else if (flag == count) {
                            // 设置第一个技术类型的rowspan为全部减去经济的个数
                            exp.setCount(sumCount - count);
                        } else {
                            exp.setCount(0);
                        };
                    }
                    flag++;
                }
            }
        }
        // 将reviewTypeId的值改为name
        for (PackageExpert expert : expertList) {
            DictionaryData data = dictionaryDataServiceI.getDictionaryData(expert.getReviewTypeId());
            if (data != null) {
                expert.setReviewTypeId(data.getName());
            }
        }
        model.addAttribute("expertList", expertList);
        // 专家给每个供应商打得分
        List<ExpertSuppScore> expertScoreList = new ArrayList<ExpertSuppScore>();
        searchMap.put("projectId", projectId);
        for (Packages pack : packList) {
            searchMap.put("packageId", pack.getId());
            expertScoreList.addAll(expertScoreService.getScoreByMap(searchMap));
        }
        model.addAttribute("expertScoreList", expertScoreList);
        // 跳转
        model.addAttribute("projectId", projectId); 
        model.addAttribute("flowDefineId", flowDefineId);        
        return "bss/prms/rank/supplier_rank";
    }
    
    /**
     *〈简述〉
     * 专家详细评审
     *〈详细描述〉
     * @author WangHuijie
     * @param packageId
     * @param expertId
     * @return
     */
    @RequestMapping("detailedReview")
    public String detailedReview(String projectId, String packageId, Model model) {
        HashMap<String, Object> searchMap = new HashMap<String, Object>();
        
        Packages packageModel = packageService.findPackageById(searchMap).get(0);
        model.addAttribute("pack", packageModel);
        // 项目分包信息
        HashMap<String, Object> pack = new HashMap<String, Object>();
        pack.put("projectId", projectId);
        List<Packages> packages = packageService.findPackageById(pack);
        //List<Packages> packages = packageService.listResultSupplier(projectId);
        Map<String, Object> list = new HashMap<String, Object>();
        // 进度集合
        List<ExpertSuppScore> expertScoreAll = new ArrayList<>();
        Map<String, Object> mapSearch = new HashMap<String, Object>();
        for (Packages ps : packages) {
            list.put("pack" + ps.getId(), ps);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("packageId", ps.getId());
            List<ProjectDetail> detailList = detailService.selectById(map);
            ps.setProjectDetails(detailList);
            // 设置查询条件
            mapSearch.put("projectId", projectId);
            mapSearch.put("packageId", ps.getId());
            // 查询评分信息
            //List<ExpertScore> expertList = expertScoreService.selectByMap(mapSearch);
            // 查询评分信息(由按项目查改为按供应商和包查)
            List<ExpertSuppScore> expertList = expertScoreService.getScoreByMap(mapSearch);
            expertScoreAll.addAll(expertList);
        }
        // 供应商信息
        SaleTender record = new SaleTender();
        record.setPackages(packageId);
        record.setIsFirstPass(1);
        record.setIsRemoved("0");
        List<SaleTender> supplierList = saleTenderService.getPackegeSuppliers(record);
        model.addAttribute("supplierList", supplierList);
        // 查询条件
        ProjectExtract projectExtract = new ProjectExtract();
        projectExtract.setProjectId(projectId);
        projectExtract.setReason("1");
        // 项目抽取的专家信息
        //List<ProjectExtract> expertList = projectExtractService.list(projectExtract);
        // 包中抽取的专家信息
        Map<String, Object> mapSearch1 = new HashMap<String, Object>(); 
        mapSearch1.put("projectId", projectId);
        mapSearch1.put("packageId", packageId);
        List<PackageExpert> expList = packageExpertService.selectList(mapSearch1);
        //判断是否结束评审
        if (expList != null && expList.size() > 0) {
          Short isEnd = expList.get(0).getIsGatherGather();
          model.addAttribute("isEnd", isEnd);
        }
        // 遍历进行排序   技术---经济---两者都有
        List<PackageExpert> expertList = new ArrayList<PackageExpert>();
        for (PackageExpert exp : expList) {
            DictionaryData data = dictionaryDataServiceI.getDictionaryData(exp.getReviewTypeId());
            if (data != null && "ECONOMY".equals(data.getCode())) {
                expertList.add(exp);
            }
        }
        for (PackageExpert exp : expList) {
            DictionaryData data = dictionaryDataServiceI.getDictionaryData(exp.getReviewTypeId());
            if (data != null && "TECHNOLOGY".equals(data.getCode())) {
                expertList.add(exp);
            }
        }
        model.addAttribute("expertList", expertList);
        // 包信息
        model.addAttribute("packageList", packages);
        Project project = projectService.selectById(projectId);
        // 项目实体
        model.addAttribute("project", project);
        model.addAttribute("projectId", projectId);
        List<ExpertSuppScore> expertScoreList = new ArrayList<>();
        for (ExpertSuppScore score : expertScoreAll) {
            String expertId = score.getExpertId();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("expertId", expertId);
            map.put("packageId", packageId);
            List<PackageExpert> temp = packageExpertService.selectList(map);
            if (temp != null && temp.size() > 0 && temp.get(0) != null && temp.get(0).getIsGrade() == 1) {
                expertScoreList.add(score);
            }
        }
        model.addAttribute("expertScoreList", expertScoreList);
        model.addAttribute("packageId", packageId);
        return "bss/prms/expert_detailed_review";
    }

    /**
     *〈简述〉
     * 根据专家编号查看明细
     *〈详细描述〉
     * @author WangHuijie
     * @param packageId
     * @param expertId
     * @return
     */
    @RequestMapping("showViewByExpertId")
    public String showViewByExpertId(String packageId, String expertId, Model model, String projectId) {
        Expert expert = expertService.selectByPrimaryKey(expertId);
        model.addAttribute("expert", expert);
        model.addAttribute("expertId", expertId);
        //查询项目信息
        Project project = projectService.selectById(projectId);
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("id", packageId);
        //查询包信息
        List<Packages> packages = packageService.findPackageById(map2);
        if(packages!=null && packages.size()>0){
            model.addAttribute("pack", packages.get(0));
        }
        //查询评分信息
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("packageId", packageId);
        // 专家可以打分的类型
        List<DictionaryData> markTermTypeList = new ArrayList<DictionaryData>();
        map.put("expertId", expertId);
        String typeId = packageExpertService.selectList(map).get(0).getReviewTypeId();
        markTermTypeList.add(dictionaryDataServiceI.getDictionaryData(typeId));
        
        model.addAttribute("markTermTypeList", markTermTypeList);
        // 查询所有的ScoreModel
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.setPackageId(packageId);
        scoreModel.setProjectId(projectId);
        List<ScoreModel> scoreModelList = scoreModelService.findListByScoreModel(scoreModel);
        for (ScoreModel score : scoreModelList) {
            if (score.getStandardScore() == null || "".equals(score.getStandardScore())) {
                score.setStandardScore(score.getMaxScore());
            }
        }
        model.addAttribute("scoreModelList", scoreModelList);
        // 查出该包内所有的markTerm
        MarkTerm markTerm = new MarkTerm();
        markTerm.setProjectId(projectId);
        markTerm.setPackageId(packageId);
        List<MarkTerm> allMarkTerm = markTermService.findListByMarkTerm(markTerm);
        // 遍历去除pid is not null 的
        List<MarkTerm> markTermList = new ArrayList<MarkTerm>();
        for (MarkTerm mark : allMarkTerm) {
            if ("0".equals(mark.getPid()) && mark.getTypeName().equals(typeId)) {
                markTermList.add(mark);
            }
        }
        // 查询父节点的子节点个数
        for (int i = 0; i < markTermList.size(); i++) {
            int count = 0;
            for (ScoreModel score : scoreModelList) {
                if (markTermList.get(i).getId().equals(score.getMarkTerm().getPid())) {
                    count++;
                }
            }
            // 设置指定父节点的rowspan
            markTermList.get(i).setCount(count);
        }
        // 将count == 0 的移除
        List<MarkTerm> markTerms = new ArrayList<MarkTerm>();
        for (MarkTerm mark : markTermList) {
            if (mark.getCount() != 0) {
                markTerms.add(mark);
            }
        }
        if (markTerms.size() > 0 && scoreModelList.size() > 0) {
            for (int i = 0; i < markTerms.size(); i++) {
                int count = 0;
                for (int j = 0; j < scoreModelList.size(); j++) {
                    if (markTerms.get(i).getId().equals(scoreModelList.get(j).getMarkTerm().getPid())) {
                        if (count == 0) {
                            scoreModelList.get(j).setCount(markTerms.get(i).getCount());
                        } else {
                            scoreModelList.get(j).setCount(0); 
                        }
                        count++;
                    }
                }
            }
        }
        model.addAttribute("markTermList", markTerms);
        //查询供应商信息
        SaleTender record = new SaleTender();
        record.setPackages(packageId);
        record.setProject(project);
        record.setIsFirstPass(1);
        record.setIsRemoved("0");
        List<SaleTender> supplierList = saleTenderService.getPackegeSuppliers(record);
        model.addAttribute("supplierList", supplierList);
        // 分数
        map.put("expertId", expertId);
        List<ExpertScore> scoresList = expertScoreService.selectInfoByMap(map);
        removeSame(scoresList);
        List<ExpertScore> scores = new ArrayList<ExpertScore>();
        // 判断如果该专家评分被退回就remove
        for (ExpertScore score : scoresList) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("packageId", score.getPackageId());
            map1.put("expertId", score.getExpertId());
            List<PackageExpert> temp = packageExpertService.selectList(map1);
            if (temp.get(0).getIsGrade() == 1) {
                scores.add(score);
            }
        }
        model.addAttribute("scores", scores);
        // 供应商经济总分,技术总分,总分
        List<SupplierRank> rankList = new ArrayList<SupplierRank>();
        for (SaleTender supp : supplierList) {
            SupplierRank rank = new SupplierRank();
            rank.setSupplierId(supp.getSuppliers().getId());
            rank.setPackageId(supp.getPackages());
            BigDecimal es = supp.getEconomicScore();
            if (es == null) {
              rank.setEconScore(null);
            } else {
              rank.setEconScore(es);
            }
            BigDecimal ts = supp.getTechnologyScore();
            if (ts == null) {
              rank.setTechScore(null);
            } else {
              rank.setTechScore(ts);
            }
            if (es == null || ts == null) {
              rank.setSumScore(null);
            } else {
              rank.setSumScore(supp.getEconomicScore().add(supp.getTechnologyScore()));
            }
            rankList.add(rank);
        }
        // 循环遍历判断名次
        for (SupplierRank rank : rankList) {
            int count = 0;
            int sum = 0;
            // 判断review_result是否不为空
            SaleTender saleTend = new SaleTender();
            saleTend.setPackages(rank.getPackageId());
            Supplier supplier = new Supplier();
            supplier.setId(rank.getSupplierId());
            saleTend.setSuppliers(supplier);
            String reviewResult = saleTenderService.findByCon(saleTend).get(0).getReviewResult();
            if (reviewResult != null && !"".equals(reviewResult)) {
                rank.setRank(0);
                rank.setReviewResult(reviewResult);
            } else {
                for (SupplierRank temp : rankList) {
                    if (rank.getPackageId().equals(temp.getPackageId())) {
                        // 判断review_result是否不为空
                        SaleTender sale = new SaleTender();
                        sale.setPackages(temp.getPackageId());
                        Supplier supp = new Supplier();
                        supp.setId(temp.getSupplierId());
                        sale.setSuppliers(supp);
                        String review = saleTenderService.findByCon(sale).get(0).getReviewResult();
                        if (review == null || "".equals(review)) {
                            sum++;
                            if (rank.getSumScore().compareTo(temp.getSumScore()) != -1 && rank != temp) {
                                count++;
                            }
                        }
                    }
                }
                rank.setRank(sum - count);
            }
        }
        model.addAttribute("rankList", rankList);
        // 新增参数
        model.addAttribute("project", project);
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);
        return "bss/prms/view_expert_score";
    }
    
    private static void removeDictionaryData(List<DictionaryData> list)   { 
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )   { 
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )   { 
                if  (list.get(j).getId(). equals(list.get(i).getId()))   { 
                    list.remove(j); 
                } 
            } 
        } 
    } 

    /**
     *〈简述〉
     * 根据供应商编号查看明细
     *〈详细描述〉
     * @author WangHuijie
     * @param packageId
     * @param supplierId
     * @return
     */
    @RequestMapping("showViewBySupplierId")
    public String showViewBySupplierId(String packageId, String supplierId, Model model, String projectId) {
        Supplier supplier = supplierService.get(supplierId);
        model.addAttribute("supplier", supplier);
        model.addAttribute("supplierId", supplierId);
        //查询项目信息
        Project project = projectService.selectById(projectId);
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("id", packageId);
        //查询包信息
        List<Packages> packages = packageService.findPackageById(map2);
        if(packages!=null && packages.size()>0){
            model.addAttribute("pack", packages.get(0));
        }
        //查询评分信息
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("packageId", packageId);
     // 查询所有的ScoreModel
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.setPackageId(packageId);
        scoreModel.setProjectId(projectId);
        List<ScoreModel> scoreModelList = scoreModelService.findListByScoreModel(scoreModel);
        for (ScoreModel score : scoreModelList) {
            if (score.getStandardScore() == null || "".equals(score.getStandardScore())) {
                score.setStandardScore(score.getMaxScore());
            }
        }
        model.addAttribute("scoreModelList", scoreModelList);
        // 查出该包内所有的markTerm
        MarkTerm markTerm = new MarkTerm();
        markTerm.setProjectId(projectId);
        markTerm.setPackageId(packageId);
        List<MarkTerm> allMarkTerm = markTermService.findListByMarkTerm(markTerm);
        // 遍历去除pid is not null 的
        List<MarkTerm> markTermList = new ArrayList<MarkTerm>();
        for (MarkTerm mark : allMarkTerm) {
            if ("0".equals(mark.getPid())) {
                markTermList.add(mark);
            }
        }
        // 查询父节点的子节点个数
        for (int i = 0; i < markTermList.size(); i++) {
            int count = 0;
            for (ScoreModel score : scoreModelList) {
                if (markTermList.get(i).getId().equals(score.getMarkTerm().getPid())) {
                    count++;
                }
            }
            // 设置指定父节点的rowspan
            markTermList.get(i).setCount(count);
        }
        // 将count == 0 的移除
        List<MarkTerm> markTerms = new ArrayList<MarkTerm>();
        for (MarkTerm mark : markTermList) {
            if (mark.getCount() != 0) {
                markTerms.add(mark);
            }
        }
        if (markTerms.size() > 0 && scoreModelList.size() > 0) {
            for (int i = 0; i < markTerms.size(); i++) {
                int count = 0;
                for (int j = 0; j < scoreModelList.size(); j++) {
                    if (markTerms.get(i).getId().equals(scoreModelList.get(j).getMarkTerm().getPid())) {
                        if (count == 0) {
                            scoreModelList.get(j).setCount(markTerms.get(i).getCount());
                        } else {
                            scoreModelList.get(j).setCount(0); 
                        }
                        count++;
                    }
                }
            }
        }
        model.addAttribute("markTermList", markTerms);
        //查询专家信息
        List<PackageExpert> packExpertList = packageExpertService.selectList(map);
        List<Expert> expertList = new ArrayList<Expert>();
        for (PackageExpert packExpert : packExpertList) {
            expertList.add(expertService.selectByPrimaryKey(packExpert.getExpertId()));
        }
        model.addAttribute("expertList", expertList);
        // 分数
        map.put("supplierId", supplierId);
        List<ExpertScore> scoresList = expertScoreService.selectInfoByMap(map);
        removeSame(scoresList);
        List<ExpertScore> scores = new ArrayList<ExpertScore>();
        // 判断如果该专家评分被退回就remove
        for (ExpertScore score : scoresList) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("packageId", score.getPackageId());
            map1.put("expertId", score.getExpertId());
            List<PackageExpert> temp = packageExpertService.selectList(map1);
            if (temp.get(0).getIsGrade() == 1) {
                scores.add(score);
            }
        }
        model.addAttribute("scores", scores);
        // 新增参数
        model.addAttribute("project", project);
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);
        return "bss/prms/view_supplier_score";
    }

    /**
     *〈简述〉
     * 退回分数
     *〈详细描述〉
     * @author WangHuijie
     * @param packageExpert
     * @param supplierId
     * @param model
     * @return 跳转到detailedReview.html
     */
    @RequestMapping("/backScore")
    @ResponseBody
    public String backScore(String projectId, String packageId, String expertId, Model model){
        // 将参数存储到model中以便redirect后取值
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);
        //model.addAttribute("expertIds", expertIds);
        //model.addAttribute("supplierId", supplierId);
        // 封装参数到map中
        Map<String, Object> mapSearch = new HashMap<String, Object>();
        mapSearch.put("projectId", projectId);
        //mapSearch.put("supplierId", supplierId);
        mapSearch.put("packageId", packageId);
        mapSearch.put("expertId", expertId);
        // 退回分数
        packageExpertService.backScore(mapSearch);
        // 跳转到showViewBySupplierId.html重新查询展示
        return "redirect:detailedReview.html";
    }


    /**
     *〈简述〉
     * 判断所选择的包是否满足汇总条件
     *〈详细描述〉
     * @author WangHuijie
     * @param packageIds
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "isGather", produces = "text/html;charset=utf-8")
    public String isGather(String packageIds, String projectId){
        return service.isGather(packageIds, projectId);
    }
    
    @RequestMapping("/auditManage")
    public String auditManage(Model model, String projectId, String flowDefineId){
      
      model.addAttribute("projectId", projectId);
      model.addAttribute("flowDefineId", flowDefineId);
      return "bss/prms/audit_manage/manage";
    }
    
    /**
     *〈简述〉符合性审查结束
     *〈详细描述〉
     * @author Ye MaoLin
     * @param packageId 包id
     * @param projectId 项目id
     * @return
     * @throws IOException 
     */
    @RequestMapping("/isFirstGather")
    public void isFirstGather(HttpServletRequest request, HttpServletResponse response, String projectId, String packageId, String flowDefineId) throws IOException{
      try {
        //结束包的符合性审查
        String msg = service.isFirstGather(projectId,packageId);
        if ("SUCCESS".equals(msg)) {
          //组织专家评审流程执行中
          flowMangeService.flowExe(request, flowDefineId, projectId, 2);
          response.setContentType("text/html;charset=utf-8");
          response.getWriter()
                  .print("{\"success\": " + true + ", \"msg\": \"" + msg+ "\"}");
        } else {
          response.setContentType("text/html;charset=utf-8");
          response.getWriter()
                  .print("{\"success\": " + false + ", \"msg\": \"" + msg+ "\"}");
        }
        response.getWriter().flush();
      } catch (Exception e) {
          e.printStackTrace();
      } finally{
          response.getWriter().close();
      }
    }
    
    /**
     *〈简述〉符合性审查结束提示
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId
     * @param packageId
     * @param expertIds
     * @param response
     * @throws IOException
     */
    @RequestMapping("/isSendBack")
    public void isSendBack(String projectId, String packageId, String expertIds, HttpServletResponse response) throws IOException{
      try {
        String msg = "";
        int flag = 0;
        String[] expertIdArr = expertIds.split(",");
        if (expertIdArr != null && expertIdArr.length > 0) {
          for (int i = 0; i < expertIdArr.length; i++) {
             // 查询是否已评审
             Map<String, Object> map = new HashMap<String, Object>();
             map.put("expertId", expertIdArr[i]);
             map.put("packageId", packageId);
             map.put("projectId", projectId);
             List<PackageExpert> selectList = service.selectList(map);
             Expert expert = expertService.selectByPrimaryKey(expertIdArr[i]);
             if (selectList != null && selectList.size() > 0) {
               PackageExpert packageExpert = selectList.get(0);
               // 必须是已评审
               if (packageExpert.getIsAudit() != SONE) {
                 msg += "【"+expert.getRelName()+"】未提交评审内容.";
                 //操作失败提示
                 flag += 1;
               }
             }
          }
        }
        if (flag > 0) {
          response.setContentType("text/html;charset=utf-8");
          response.getWriter()
                  .print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
        }  
        if (flag == 0 && expertIdArr != null && expertIdArr.length > 0){
            for (int i = 0; i < expertIdArr.length; i++) {
              // 查询是否已评审
              Map<String, Object> map = new HashMap<String, Object>();
              map.put("expertId", expertIdArr[i]);
              map.put("packageId", packageId);
              map.put("projectId", projectId);
              List<PackageExpert> selectList = service.selectList(map);
              Expert expert = expertService.selectByPrimaryKey(expertIdArr[i]);
              if (selectList != null && selectList.size() > 0) {
                PackageExpert packageExpert = selectList.get(0);
                if (packageExpert.getIsGrade() == SONE || packageExpert.getIsGrade() == NUMBER_TWO) {
                  msg += "【"+expert.getRelName()+"】经济技术评分内容将会清空.";
                }
              }
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
                    .print("{\"success\": " + true + ", \"msg\": \"" + msg + "\"}");
        }
        response.getWriter().flush();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        response.getWriter().close();
      }
    }
     
    /**
     *〈简述〉暂存符合性审查内容
     *〈详细描述〉
     * @author Ye MaoLin
     * @param user
     * @param response
     * @param projectId
     * @param packageId
     * @throws IOException
     */
    @RequestMapping("/tempSave")
    public void tempSave(@CurrentUser User user, HttpServletResponse response, String projectId, String packageId) throws IOException{
      try {
        String msg = "";
        String expertId = user.getTypeId();
        String result = reviewProgressService.tempSaveFirstAudit(projectId, packageId, expertId);
        if ("SUCCESS".equals(result)) {
          msg = "暂存成功";
          response.setContentType("text/html;charset=utf-8");
          response.getWriter()
                  .print("{\"success\": " + true + ", \"msg\": \"" + msg + "\"}");
        }
        if ("ERROR".equals(result)) {
          msg = "暂存失败";
          response.setContentType("text/html;charset=utf-8");
          response.getWriter()
                  .print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
        }
        response.getWriter().flush();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        response.getWriter().close();
      }
      
    }
    
    /**
     *〈简述〉符合性审查退回复核
     *〈详细描述〉
     * @author Ye MaoLin
     * @param record 
     * @throws IOException 
     */
    @RequestMapping("/sendBack")
    public void sendBack(String projectId, String packageId, String expertIds, HttpServletResponse response) throws IOException{
      try {
        String msg = "";
        int flag = 0;
        String[] expertIdArr = expertIds.split(",");
        //将包下所有供应商符合性审查结果置为空
        SaleTender saleTender = new SaleTender();
        saleTender.setProjectId(projectId);
        saleTender.setPackages(packageId);
        List<SaleTender> saleTenders = saleTenderService.findByCon(saleTender);
        if (saleTenders != null && saleTenders.size() > 0) {
          for (SaleTender saleTender2 : saleTenders) {
            HashMap<String, Object> stMap = new HashMap<String, Object>();
            stMap.put("id", saleTender2.getId());
            stMap.put("isFirstPass", null);
            stMap.put("economicScore", null);
            stMap.put("technologyScore", null);
            stMap.put("reviewResult", null);
            stMap.put("isRemoved", "0");
            saleTenderService.updateResult(stMap);
          }
        }
        if (expertIdArr != null && expertIdArr.length > 0) {
           for (int i = 0; i < expertIdArr.length; i++) {
              // 查询是否已评审
              Map<String, Object> map = new HashMap<String, Object>();
              map.put("expertId", expertIdArr[i]);
              map.put("packageId", packageId);
              map.put("projectId", projectId);
              List<PackageExpert> selectList = service.selectList(map);
              Expert expert = expertService.selectByPrimaryKey(expertIdArr[i]);
              if (selectList != null && selectList.size() > 0) {
                  PackageExpert packageExpert = selectList.get(0);
                  //如果该专家已提交或暂存评分内容或，则退回并清空评分内容
                  if (packageExpert.getIsGrade() == SONE || packageExpert.getIsGrade() == NUMBER_TWO) {
                    //改为经济技术评审未提交和未结束状态
                    packageExpert.setIsGrade((short)0);
                    packageExpertService.updateByBean(packageExpert);
                    //评分内容清空
                    
                  }
                  //改为符合性评审未提交和未结束状态
                  packageExpert.setIsAudit((short)0);
                  packageExpert.setIsGather((short)0);
                  packageExpertService.updateByBean(packageExpert);
                  //将符合性审查改为退回状态
                  Map<String, Object> map2 = new HashMap<String, Object>();
                  map2.put("expertId", expertIdArr[i]);
                  map2.put("packageId", packageId);
                  map2.put("projectId", projectId);
                  List<ReviewFirstAudit> rfas =  reviewFirstAuditService.selectList(map2);
                  for (ReviewFirstAudit reviewFirstAudit : rfas) {
                    reviewFirstAudit.setIsBack(1);
                    reviewFirstAuditService.update(reviewFirstAudit);
                  }
              }
            }
         }
         //初审进度
         double firstProgress = 0;
         //总进度
         double totalProgress = 0;
         //评分进度
         double scoreProgress = 0;
         Map<String, Object> map2 = new HashMap<String, Object>();
         map2.put("packageId", packageId);
         map2.put("projectId", projectId);
         List<ReviewProgress> reviewProgressList = reviewProgressService.selectByMap(map2);
         if (reviewProgressList != null && reviewProgressList.size() > 0) {
           ReviewProgress reviewProgress = reviewProgressList.get(0);
           //设置状态为初审中
           reviewProgress.setAuditStatus("1");
           // 查询提交符合性评审的专家数
           Map<String, Object> map1 = new HashMap<String, Object>();
           map1.put("packageId", packageId);
           map1.put("projectId", projectId);
           map1.put("isAudit", 1);
           List<PackageExpert> expertAuditeds = service.selectList(map1);
           // 查询提交经济技术评审的专家数
           Map<String, Object> map3 = new HashMap<String, Object>();
           map3.put("packageId", packageId);
           map3.put("projectId", projectId);
           map3.put("isGrade", 1);
           List<PackageExpert> secondExperts = service.selectList(map3);
           Map<String, Object> map = new HashMap<String, Object>();
           map.put("packageId", packageId);
           map.put("projectId", projectId);
           //查询包下全部评审专家
           List<PackageExpert> packageExpertList = service.selectList(map);
           double first = 0;
           double second = 0;
           //初审进度更新
           first = ((double)expertAuditeds.size())/(double)packageExpertList.size();
           BigDecimal b = new BigDecimal(first); 
           firstProgress  = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
           reviewProgress.setFirstAuditProgress(firstProgress);
           //经济技术进度更新
           second = ((double)secondExperts.size())/(double)packageExpertList.size();
           BigDecimal s = new BigDecimal(second); 
           scoreProgress  = s.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
           reviewProgress.setScoreProgress(scoreProgress);
           //总进度更新
           double total2 =  (firstProgress+scoreProgress)/2;
           BigDecimal t = new BigDecimal(total2); 
           totalProgress  = t.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
           reviewProgress.setTotalProgress(totalProgress);
           //更新数据库
           reviewProgressService.updateByMap(reviewProgress);
           msg = "ok";
           response.setContentType("text/html;charset=utf-8");
           response.getWriter()
                   .print("{\"success\": " + true + ", \"msg\": \"" + msg + "\"}");
       }
      } catch (Exception e) {
        e.printStackTrace();
      } finally{
          response.getWriter().close();
      }
      
    }
    /**
     *〈简述〉
     * 对List<ExpertScore>去重
     *〈详细描述〉
     * @author WangHuijie
     * @param list
     * @return
     */
    private List<ExpertScore> removeSame(List<ExpertScore> list){
        for (int i = 0; i < list.size(); i++) {
            for (int j = list.size() - 1 ; j > i; j--) {
                if (list.get(i).getScoreModelId().equals(list.get(j).getScoreModelId()) && list.get(i).getExpertId().equals(list.get(j).getExpertId()) && list.get(i).getSupplierId().equals(list.get(j).getSupplierId())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }
    /**
     *〈简述〉
     * 对List<ExpertScore>去重(供应商排名)
     *〈详细描述〉
     * @author WangHuijie
     * @param list
     * @return
     */
    private List<ExpertScore> removeRankSame(List<ExpertScore> list){
        for (int i = 0; i < list.size(); i++) {
            for (int j = list.size() - 1 ; j > i; j--) {
                if (list.get(i).getScoreModelId().equals(list.get(j).getScoreModelId()) && list.get(i).getSupplierId().equals(list.get(j).getSupplierId()) && list.get(i).getPackageId().equals(list.get(j).getPackageId())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }
    /**
     *〈简述〉
     *判断专家有没有进行评分
     *〈详细描述〉
     * @author WangHuijie
     * @param expertId
     * @param packageId
     * @return
     */
    @ResponseBody
    @RequestMapping("/isGrade")
    public String isGrade (String expertId, String packageId) {
        String result = "0";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("expertId", expertId);
        map.put("packageId", packageId);
        List<PackageExpert> list = packageExpertService.selectList(map);
        if (list.get(0).getIsGrade() == 1) {
            result = "1";
        }
        return result;
    }
    
    /**
     *〈简述〉查看专家符合性审查
     *〈详细描述〉
     * @param projectId
     * @param packageId
     * @param model
     * @param expertId
     * @param session
     * @return
     */
    @RequestMapping("/printView")
    public String printView(String projectId, String packageId, Model model, String expertId, HttpSession session){
      //创建封装的实体
      Extension extension = new Extension();
      HashMap<String ,Object> map = new HashMap<>();
      map.put("projectId", projectId);
      map.put("id", packageId);
      //查询包信息
      List<Packages> list = packageService.findPackageById(map);
      if(list!=null && list.size()>0){
        Packages packages = list.get(0);
        //放入包信息
        extension.setPackageId(packages.getId());
        extension.setPackageName(packages.getName());
      }
      //查询项目信息
      Project project = projectService.selectById(projectId);
      if(project!=null){
        //放入项目信息
        extension.setProjectId(project.getId());
        extension.setProjectName(project.getName());
        extension.setProjectCode(project.getProjectNumber());
      }
      
      //查询改包下的初审项信息
      Map<String,Object> map2 = new HashMap<>();
      map2.put("projectId", projectId);
      map2.put("packageId", packageId);
      //查询出该包下的初审项id集合
      List<PackageFirstAudit> packageAuditList = packageFirstAuditService.selectList(map2);
      //创建初审项的集合
      List<FirstAudit> firstAuditList = new ArrayList<>();
      if(packageAuditList!=null && packageAuditList.size()>0){
        for (PackageFirstAudit packageFirst : packageAuditList) {
          //根据初审项的id 查询出初审项的信息放入集合
          FirstAudit firstAudits = firstAuditService.get(packageFirst.getFirstAuditId());
          firstAuditList.add(firstAudits);
        }
      }
        //放入初审项集合
      extension.setFirstAuditList(firstAuditList);
      //查询供应商信息
      List<SaleTender> supplierList = saleTenderService.list(new SaleTender(projectId), 0);
      extension.setSupplierList(supplierList);
      
      //查询审核过的信息用于回显
      Map<String, Object> reviewFirstAuditMap = new HashMap<>();
      reviewFirstAuditMap.put("projectId", projectId);
      reviewFirstAuditMap.put("packageId", packageId);
      reviewFirstAuditMap.put("expertId", expertId);
      List<ReviewFirstAudit> reviewFirstAuditList = reviewFirstAuditService.selectList(reviewFirstAuditMap);
      //回显信息放进去
      model.addAttribute("reviewFirstAuditList", reviewFirstAuditList);
      //把封装的实体放入域中
      model.addAttribute("extension", extension);
      List<DictionaryData> dds = DictionaryDataUtil.find(22);
      model.addAttribute("dds", dds);
      model.addAttribute("expertId", expertId);
      return "bss/prms/first_audit/print_view";
    }
    
    /**
     *〈简述〉
     * 确定供应商
     *〈详细描述〉
     * @author WangHuijie
     * @param projectId
     * @param model
     * @return
     */
    @RequestMapping("/confirmSupplier")
    public String confirmSupplier (String projectId, Model model) {
        List<SaleTender> supplierList = saleTenderService.selectListByProjectId(projectId);
        Packages pack = new Packages();
        Map<String, Object> map = new HashMap<String, Object>();
        for (SaleTender sale : supplierList) {
            pack.setId(sale.getPackages());
            sale.setPackageNames(packageService.find(pack).get(0).getName());
            map.put("packageId", sale.getPackages());
            List<PackageExpert> list = packageExpertService.selectList(map);
            if (list != null && list.size() > 0) {
                Integer isFinish = list.get(0).getIsGatherGather() == (short) 1 ? 1 : 0;
                sale.setIsFinish(isFinish);
            }
        }
        model.addAttribute("supplierList", supplierList);
        model.addAttribute("projectId", projectId);
        return "bss/prms/rank/confirm_supplier";
    }
    
    /**
     *〈简述〉
     * 移除供应商
     *〈详细描述〉
     * @author WangHuijie
     * @param packageId
     * @param supplierId
     * @return
     */
    @ResponseBody
    @RequestMapping("/removeSaleTender")
    public void removeSaleTender (String packageId, String supplierId, String projectId, String removedReason) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("supplierId", supplierId);
        map.put("packageId", packageId);
        map.put("removedReason", removedReason);
        saleTenderService.removeSaleTender(map);
    }
    
    /**
     *〈简述〉专家签到
     *〈详细描述〉
     * @author Ye Maolin
     * @param response
     * @param list
     * @param projectId
     * @throws IOException
     */
    @RequestMapping("/endSignIn")
    public void endSignIn(HttpServletResponse response, PurchaseRequiredFormBean list, String projectId) throws IOException{
      try {
        String msg = "";
        int flag = 0;
        List<PackageExpert> packageExperts = list.getPackageExperts();
        // 项目分包信息
        HashMap<String, Object> pack = new HashMap<String, Object>();
        pack.put("projectId", projectId);
        List<Packages> packages = packageService.findPackageById(pack);
        for (Packages packages2 : packages) {
          int count = 0;
          for (int i = 1; i < packageExperts.size(); i++) {
            PackageExpert packageExpert = packageExperts.get(i);
            //校验每包组长数量
            if (packages2.getId().equals(packageExpert.getPackageId()) && packageExpert.getIsGroupLeader() == 1) {
                count ++;
            }
            //校验组长必须签到
            if (packages2.getId().equals(packageExpert.getPackageId()) && packageExpert.getIsGroupLeader() == 1 && packageExpert.getIsSigin() == 0) {
              msg += "【"+packages2.getName()+"】请选择已到场的专家作为组长.";
              flag = 1;
            }
            //临时专家字段校验
            if (packages2.getId().equals(packageExpert.getPackageId()) && packageExpert.getIsTempExpert() == 0) {
              Expert expert = packageExpert.getExpert();
              if (expert != null ) {
                if ("".equals(expert.getRelName()) || expert.getRelName() == null 
                    || "".equals(expert.getIdNumber()) || expert.getIdNumber() == null 
                    || "".equals(expert.getMobile()) || expert.getMobile() == null
                    || "".equals(expert.getAtDuty()) || expert.getAtDuty() == null) {
                  msg += "【"+packages2.getName()+"】临时专家填写项不能为空.";
                  flag = 1;
                }
                List<User> users = userService.findByLoginName(expert.getMobile());
                if (users.size() > 0) {
                  msg += "已存在【"+expert.getRelName()+"】手机号的用户名";
                  flag = 1;
                }
              }
            }
          }
          if (count == 0) {
            msg += "【"+packages2.getName()+"】请设置组长";
            flag = 1;
          }
          if (count > 1) {
            msg += "【"+packages2.getName()+"】请设置一个组长";
            flag = 1;
          }
        }
        if (flag == 1) {
          response.setContentType("text/html;charset=utf-8");
          response.getWriter()
          .print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
        }
        if (flag == 0) {
          PackageExpert pe = packageExperts.get(0);
          for (int i = 1; i < packageExperts.size(); i++) {
            PackageExpert packageExpert = packageExperts.get(i);
            //保存到场签到的专家
            if (packageExpert.getIsSigin() == 1 && packageExpert.getIsTempExpert() == 1) {
              packageExpert.setIsAudit((short)0);
              packageExpert.setIsGather((short)0);
              packageExpert.setIsGrade((short)0);
              packageExpert.setIsGatherGather((short)0);
              String tempTypeId = packageExpert.getReviewTypeId();
              DictionaryData tempdd = DictionaryDataUtil.findById(tempTypeId);
              //技术类型
              if ("GOODS".equals(tempdd.getCode()) || "PROJECT".equals(tempdd.getCode()) || "SERVICE".equals(tempdd.getCode())) {
                packageExpert.setReviewTypeId(DictionaryDataUtil.getId("TECHNOLOGY"));
              }
              //经济类型
              if ("GOODS_SERVER".equals(tempdd.getCode()) || "GOODS_PROJECT".equals(tempdd.getCode())) {
                packageExpert.setReviewTypeId(DictionaryDataUtil.getId("ECONOMY"));
              }
              packageExpertService.save(packageExpert);
            }
            //保存到场签到的临时专家
            if (packageExpert.getIsSigin() == 1 && packageExpert.getIsTempExpert() == 0) {
              packageExpertService.saveTempExpert(packageExpert,packageExpert.getPackageId());
              Expert expert = packageExpert.getExpert();
              if (expert != null ) {
                packageExpert.setExpertId(expert.getId());
              }
              packageExpert.setIsAudit((short)0);
              packageExpert.setIsGather((short)0);
              packageExpert.setIsGrade((short)0);
              packageExpert.setIsGatherGather((short)0);
              String tempTypeId = packageExpert.getReviewTypeId();
              DictionaryData tempdd = DictionaryDataUtil.findById(tempTypeId);
              //技术类型
              if ("GOODS".equals(tempdd.getCode()) || "PROJECT".equals(tempdd.getCode()) || "SERVICE".equals(tempdd.getCode())) {
                packageExpert.setReviewTypeId(DictionaryDataUtil.getId("TECHNOLOGY"));
              }
              //经济类型
              if ("GOODS_SERVER".equals(tempdd.getCode()) || "GOODS_PROJECT".equals(tempdd.getCode())) {
                packageExpert.setReviewTypeId(DictionaryDataUtil.getId("ECONOMY"));
              }
              packageExpertService.save(packageExpert);
            }
          }
          //修改项目状态
          //Project project = projectService.selectById(projectId);
          //project.setStatus(DictionaryDataUtil.getId("ZJQDWC"));
          //projectService.update(project);
          msg = "签到完成，临时专家的用户名为【手机号】，密码为【123456】";
          response.setContentType("text/html;charset=utf-8");
          response.getWriter()
          .print("{\"success\": " + true + ", \"msg\": \"" + msg + "\"}");
        }
        response.getWriter().flush();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        response.getWriter().close();
      }
      
    }
    
    /**
     *〈简述〉返回临时专家id
     *〈详细描述〉
     * @author Ye Maolin
     * @param response
     * @throws IOException
     */
    @RequestMapping("/returnNew")
    public void returnNew(HttpServletResponse response) throws IOException{
      try {
        String expertId = WfUtil.createUUID();
        response.setContentType("text/html;charset=utf-8");
        response.getWriter()
        .print("{\"success\": " + true + ", \"expertId\": \"" + expertId + "\"}");
        response.getWriter().flush();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        response.getWriter().close();
      }
      
    }
}
