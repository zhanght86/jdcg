package ses.controller.sys.ems;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.bms.Userrole;
import ses.model.ems.*;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierRegPerson;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.EngCategoryService;
import ses.service.bms.NoticeDocumentService;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertAttachmentService;
import ses.service.ems.ExpertAuditService;
import ses.service.ems.ExpertCategoryService;
import ses.service.ems.ExpertService;
import ses.service.ems.ProjectExtractService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierQuoteService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;
import ses.util.SupplierLevelUtil;
import ses.util.WfUtil;
import ses.util.WordUtil;
import bss.controller.base.BaseController;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ext.ProjectExt;
import bss.model.prms.PackageExpert;
import bss.service.ppms.BidMethodService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.service.prms.PackageExpertService;
import bss.service.prms.ReviewProgressService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.constant.Constant;
import common.constant.StaticVariables;
import common.service.UploadService;
import common.model.UploadFile;

@Controller
@RequestMapping("/expert")
public class ExpertController extends BaseController {
    @Autowired
    private UserServiceI userService; // 用户管理
    @Autowired
    private ExpertService service; // 专家管理
    @Autowired
    private PurchaseOrgnizationServiceI purchaseOrgnizationService; // 采购机构管理
    @Autowired
    private ExpertAuditService expertAuditService; // 审核信息管理
    @Autowired
    private ExpertCategoryService expertCategoryService; // 专家类别中间表
    @Autowired
    private ExpertAttachmentService attachmentService; // 附件管理
    @Autowired
    private PackageExpertService packageExpertService; // 专家项目包 关联表
    @Autowired
    private PackageService packageService; // 包 service
    @Autowired
    private ProjectService projectService; // 项目service
    @Autowired
    private SaleTenderService saleTenderService; // 供应商查询
    @Autowired
    private ReviewProgressService reviewProgressService; // 进度
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI; // TypeId
    @Autowired
    private SupplierQuoteService supplierQuoteService; // 供应商报价
    @Autowired
    private NoticeDocumentService noticeDocumentService;
    @Autowired
    private AreaServiceI areaServiceI; // 地区查询
    @Autowired
    private PreMenuServiceI menuService; // 地区查询
    @Autowired
    private RoleServiceI roleService; // 地区查询
    @Autowired
    private ProjectExtractService projectExtractService; //是否被抽取查询
    @Autowired
    private CategoryService categoryService; //品目
    @Autowired
    private EngCategoryService engCategoryService; //工程专业信息
    @Autowired
    private SupplierItemService supplierItemService; //品目
    @Autowired
    private SupplierService supplierService; //供应商
    @Autowired
    private BidMethodService bidMethodService;
    //校验文件是否上传
    @Autowired
    private UploadService uploadService;

    /**
     * @param @return
     * @return String
     * @Title: toExpert
     * @author lkzx
     * @date 2016年8月31日 下午7:04:16
     * @Description: TODO 跳转到评审专家注册页面
     */
    @RequestMapping(value = "/toExpert")
    public String toExpert(Model model) {
        // 查询数据字典中的专家来源配置数据
        List<DictionaryData> lyTypeList = DictionaryDataUtil.find(12);
        model.addAttribute("lyTypeList", lyTypeList);
        return "ses/ems/expert/expert_register";
    }

    /**
     * @param id
     * @param model
     * @param @return
     * @return String
     * @Title: view
     * @author ShaoYangYang
     * @date 2016年9月29日 上午11:03:50
     * @Description: TODO 查看专家信息
     */
    @RequestMapping("/view")
    public String view(@RequestParam("id") String id, Model model) {
        // 查询出专家
        Expert expert = service.selectByPrimaryKey(id);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", expert.getPurchaseDepId());
        map.put("typeName", "1");
        // 查询出采购机构
        List<PurchaseDep> depList = purchaseOrgnizationService
                .findPurchaseDepList(map);
        if (depList != null && depList.size() > 0) {
            PurchaseDep purchaseDep = depList.get(0);
            model.addAttribute("purchase", purchaseDep);
        }
        // 查询数据字典中的证件类型配置数据
        List<DictionaryData> idTypeList = DictionaryDataUtil.find(9);
        model.addAttribute("idTypeList", idTypeList);
        // 查询数据字典中的政治面貌配置数据
        List<DictionaryData> zzList = DictionaryDataUtil.find(10);
        model.addAttribute("zzList", zzList);
        // 查询数据字典中的最高学历配置数据
        List<DictionaryData> xlList = DictionaryDataUtil.find(11);
        model.addAttribute("xlList", xlList);
        // 查询数据字典中的专家来源配置数据
        List<DictionaryData> lyTypeList = DictionaryDataUtil.find(12);
        model.addAttribute("lyTypeList", lyTypeList);
        // 查询数据字典中的性别配置数据
        List<DictionaryData> sexList = DictionaryDataUtil.find(13);
        model.addAttribute("sexList", sexList);
        // 产品类型数据字典
        List<DictionaryData> spList = DictionaryDataUtil.find(6);
        model.addAttribute("spList", spList);
        // 货物类型数据字典
        List<DictionaryData> hwList = DictionaryDataUtil.find(8);
        model.addAttribute("hwList", hwList);
        // 经济类型数据字典
        List<DictionaryData> jjTypeList = DictionaryDataUtil.find(19);
        model.addAttribute("jjList", jjTypeList);
        // 专家系统key
        Integer expertKey = Constant.EXPERT_SYS_KEY;
        Map<String, Object> typeMap = getTypeId();
        // typrId集合
        model.addAttribute("typeMap", typeMap);
        // 业务id就是专家id
        model.addAttribute("sysId", id);
        // Constant.EXPERT_SYS_VALUE;
        model.addAttribute("expertKey", expertKey);
        model.addAttribute("expert", expert);

        return "ses/ems/expert/view";
    }

    /**
     * @param @return
     * @return String
     * @Title: toRegisterNotice
     * @author lkzx
     * @date 2016年8月31日 下午7:04:16
     * @Description: TODO 跳转到评审专家注册须知页面
     */
    @RequestMapping(value = "/toRegisterNotice")
    public String toRegisterNotice(Model model) {
        DictionaryData dd = DictionaryDataUtil.get("EXPERT_REGISTER_NOTICE");
        if (dd != null) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("docType", dd.getId());
            String doc = noticeDocumentService.findDocByMap(param);
            model.addAttribute("doc", doc);
        }
        return "ses/ems/expert/register_notice";
    }

    /**
     * @param expert
     * @param model
     * @return String
     * @Title: register
     * @author lkzx
     * @date 2016年8月31日 下午6:36:19
     * @Description: TODO 注册评审专家用户
     */
    @RequestMapping("/register")
    public String register(User user, HttpSession session, Model model,
                           HttpServletRequest request, @RequestParam String token2,
                           RedirectAttributes attr, String expertsFrom) {
        Object tokenValue = session.getAttribute("tokenSession");
        if (tokenValue != null && tokenValue.equals(token2)) {
            // 正常提交
            session.removeAttribute("tokenSession");
            // 判断用户名密码是否合法
            String loginName = user.getLoginName();
            String password = user.getPassword();
            String regex = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            Pattern p = Pattern.compile(regex);
            Pattern p2 = Pattern.compile("[\u4e00-\u9fa5]");
            Matcher m = p.matcher(loginName);
            Matcher m2 = p2.matcher(loginName);
            Matcher matcher = p.matcher(password);
            Matcher matcher2 = p2.matcher(password);
            if (loginName.trim().length() < 3 || m.find() || m2.find()) {
                model.addAttribute("message", "用户名不符合规则");
                return "ems/expert/expert_register";
            } else if (password.trim().length() < 6 || matcher.find() ||
                    matcher2.find()) {
                model.addAttribute("message", "密码不符合规则");
                return "ems/expert/expert_register";
            }
            user.setId(WfUtil.createUUID());
            request.setAttribute("user", user);
            // 查找用户类型(字段被移除)
            // String userType = DictionaryDataUtil.getId("EXPERT_U");
            // user.setTypeName(userType);
            String expertId = WfUtil.createUUID();
            user.setTypeId(expertId);
            userService.save(user, null);
            Expert expert = new Expert();
            expert.setId(expertId);
            expert.setIsProvisional((short) 0);
            expert.setMobile(user.getMobile());
            expert.setExpertsFrom(expertsFrom);
            service.insertSelective(expert);
            Role role = new Role();
            role.setCode("EXPERT_R");
            List<Role> listRole = roleService.find(role);
            if (listRole != null && listRole.size() > 0) {
                Userrole userrole = new Userrole();
                userrole.setRoleId(listRole.get(0));
                userrole.setUserId(user);
                /** 给该用户初始化专家角色 */
                userService.saveRelativity(userrole);
                /** 删除用户之前的菜单权限*/
                /*UserPreMenu userPreMenu = new UserPreMenu();
                userPreMenu.setUser(user);
				userService.deleteUserMenu(userPreMenu);*/
                /** 给用户初始化专家菜单权限 */
				/*String[] roleIds = listRole.get(0).getId().split(",");
				List<String> listMenu = menuService.findByRids(roleIds);

				for (String menuId : listMenu) {
				    UserPreMenu upm = new UserPreMenu();
				    PreMenu preMenu = menuService.get(menuId);
				    upm.setPreMenu(preMenu);
				    upm.setUser(user);
				    userService.saveUserMenu(upm);
				}*/
            }
            attr.addAttribute("userId", user.getId());
            return "redirect:toAddBasicInfo.html";
        }
        // 重复提交
        else {
            attr.addAttribute("userId", user.getId());
            return "redirect:toAddBasicInfo.html";
        }
    }

    /**
     * @param userId
     * @param request
     * @param response
     * @param model
     * @return String
     * @Title: toAddBasicInfo
     * @author ShaoYangYang
     * @date 2016年11月23日 下午7:27:29
     * @Description: TODO
     */

    @RequestMapping("/toAddBasicInfo")
    public String toAddBasicInfo(@RequestParam("userId") String userId,
                                 HttpServletRequest request, HttpServletResponse response,
                                 Model model) {
        model.addAttribute("userId", userId);
        User user = userService.getUserById(userId);
        String typeId = user.getTypeId();
        // 生成专家id
        String expertId = "";
        int flag = 0;
        // 暂存 或退回后重新填写
        Expert expert = service.selectByPrimaryKey(typeId);
        model.addAttribute("expert", expert);
        String stepNumber;
        if ("".equals(expert.getStepNumber()) || expert.getStepNumber() == null) {
            stepNumber = "one";
        } else {
            stepNumber = expert.getStepNumber();
        }
        if (expert != null)
            expertId = expert.getId();
        // 判断已提交 未审核的数据 跳转到查看页面
        if (expert != null && expert.getIsSubmit().equals("1") &&
                expert.getStatus().equals("0")) {
            // 已提交未审核数据
            flag = 1;
        }
        Map<String, Object> errorMap = service.Validate(expert, 3, null);
        expert.setExpertsFrom(dictionaryDataServiceI.getDictionaryData(expert.getExpertsFrom()).getCode());
        model.addAttribute("expert", expert);
        model.addAttribute("errorMap", errorMap);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("typeName", "1");
        List<PurchaseDep> purchaseDepList = purchaseOrgnizationService
                .findPurchaseDepList(map);
        // 专家系统key
        Integer expertKey = Constant.EXPERT_SYS_KEY;
        // 获取各个附件类型id集合
        Map<String, Object> typeMap = getTypeId();
        // 判断是否有合同书和申请表的附件
        String att = isAttachment(expertId, typeMap);
        // 查询数据字典中的证件类型配置数据
        List<DictionaryData> idTypeList = DictionaryDataUtil.find(9);
        model.addAttribute("idTypeList", idTypeList);
        // 查询数据字典中的政治面貌配置数据
        List<DictionaryData> zzList = DictionaryDataUtil.find(10);
        model.addAttribute("zzList", zzList);
        // 查询数据字典中的最高学历配置数据
        List<DictionaryData> xlList = DictionaryDataUtil.find(11);
        model.addAttribute("xlList", xlList);
        // 查询数据字典中的专家来源配置数据
        List<DictionaryData> lyTypeList = DictionaryDataUtil.find(12);
        model.addAttribute("lyTypeList", lyTypeList);
        // 如果是外网用户则不可以选择专家来源为军队
        //String ipAddress = request.getRemoteAddr();
        //int type = IpAddressUtil.validateIpAddress(ipAddress);
        PropertiesUtil config = new PropertiesUtil("config.properties");
        String type = config.getString("ipAddressType");
        // 如果是外网用户,则删除军队这个选项
        if ("1".equals(type)) {
            for (int i = 0; i < lyTypeList.size(); i++) {
                // 循环判断如果是军队则remove
                if ("军队".equals(lyTypeList.get(i).getName())) {
                    lyTypeList.remove(i);
                }
            }
        }
        // 查询数据字典中的性别配置数据
        List<DictionaryData> sexList = DictionaryDataUtil.find(13);
        model.addAttribute("sexList", sexList);
        // 产品类型数据字典
        List<DictionaryData> spList = DictionaryDataUtil.find(6);
        model.addAttribute("spList", spList);
        // 货物类型数据字典
        List<DictionaryData> hwList = DictionaryDataUtil.find(8);
        model.addAttribute("hwList", hwList);
        // 经济类型数据字典
        List<DictionaryData> jjTypeList = DictionaryDataUtil.find(19);
        model.addAttribute("jjList", jjTypeList);
        // 学位类型数据字典
        List<DictionaryData> xwTypeList = DictionaryDataUtil.find(21);
        model.addAttribute("xwList", xwTypeList);
        model.addAttribute("att", att);
        // typrId集合
        model.addAttribute("typeMap", typeMap);

        model.addAttribute("sysId", expertId);
        // Constant.EXPERT_SYS_VALUE;
        model.addAttribute("expertKey", expertKey);
        model.addAttribute("purchase", purchaseDepList);
        model.addAttribute("user", user);
        if ("six".equals(stepNumber)) {
            showCategory(expert, model);
        }
        if ("3".equals(expert.getStatus())) {
            // 如果状态为退回修改则查询没通过的字段
            ExpertAudit expertAudit = new ExpertAudit();
            expertAudit.setExpertId(expertId);
            expertAudit.setSuggestType(stepNumber);
            List<ExpertAudit> auditList = expertAuditService.selectFailByExpertId(expertAudit);
            // 所有的不通过字段的名字
            StringBuffer errorField = new StringBuffer();
            for (ExpertAudit audit : auditList) {
                errorField.append(audit.getAuditField() + ",");
            }
            model.addAttribute("errorField", errorField);
        }
        if ("three".equals(stepNumber)) {
            HashMap<String, Object> map1 = new HashMap<String, Object>();
            map1.put("typeName", "1");
            List<PurchaseDep> list = purchaseOrgnizationService
                    .findPurchaseDepList(map1);
            model.addAttribute("allPurList", list);
        }
        model.addAttribute("engId", DictionaryDataUtil.getId("ENG_INFO_ID"));
        return "ses/ems/expert/basic_info_" + stepNumber;
    }

    /**
     * 〈简述〉
     * 专家注册新加步骤:产品目录
     * 〈详细描述〉
     *
     * @return
     * @author WangHuijie
     */
    public void showCategory(Expert expert, Model model) {
        List<DictionaryData> allCategoryList = new ArrayList<DictionaryData>();
        // 获取专家类别
        List<String> allTypeId = new ArrayList<String>();
        for (String id : expert.getExpertsTypeId().split(",")) {
            allTypeId.add(id);
        }
        a:
        for (int i = 0; i < allTypeId.size(); i++) {
            DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(allTypeId.get(i));
            if (dictionaryData != null && dictionaryData.getKind() == 19) {
                allTypeId.remove(i);
                continue a;
            }
            ;
            allCategoryList.add(dictionaryData);
        }
        model.addAttribute("allCategoryList", allCategoryList);
    }

    /**
     * 〈简述〉
     * 查询不通过理由
     * 〈详细描述〉
     *
     * @param expertId
     * @param auditField
     * @return
     * @author WangHuijie
     */
    @RequestMapping(value = "/findAuditReason", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findErrorReason(ExpertAudit expertAudit) {
        List<ExpertAudit> audit = expertAuditService.selectFailByExpertId(expertAudit);
        return JSON.toJSONString(audit.get(0));
    }

    /**
     * 〈简述〉递归获取所有的子节点
     * 〈详细描述〉
     *
     * @param categoryId
     * @return
     * @author WangHuijie
     */
    public List<Category> getChildrenNodes(String categoryId, String flag) {
        if (flag == null) {
            List<Category> allChildrenNodes = new ArrayList<Category>();
            List<Category> childrenList = categoryService.findPublishTree(categoryId, null);
            allChildrenNodes.addAll(childrenList);
            if (childrenList != null && childrenList.size() > 0) {
                for (Category cate : childrenList) {
                    allChildrenNodes.addAll(getChildrenNodes(cate.getId(), null));
                }
            }
            return allChildrenNodes;
        } else {
            List<Category> allChildrenNodes = new ArrayList<Category>();
            List<Category> childrenList = categoryService.findPublishTree(categoryId, null);
            allChildrenNodes.addAll(childrenList);
            if (childrenList != null && childrenList.size() > 0) {
                for (Category cate : childrenList) {
                    allChildrenNodes.addAll(getChildrenNodes(cate.getId(), "ENG_INFO"));
                }
            }
            return allChildrenNodes;
        }
    }

    /**
     * 〈简述〉
     * 保存品目信息
     * 〈详细描述〉
     *
     * @param expertId
     * @param categoryId
     * @param type
     * @return
     * @author WangHuijie
     */
    @ResponseBody
    @RequestMapping("/saveCategory")
    public void saveCategory(String expertId, String categoryId, String type, String typeId, boolean isParent) {
        String code = DictionaryDataUtil.findById(typeId).getCode();
        String flag = null;
        if (code.equals("ENG_INFO_ID")) {
            flag = "ENG_INFO";
        }
        if ("1".equals(type)) {
            Expert expert = new Expert();
            expert.setId(expertId);
            // 递归获取当前节点的所有子节点
            List<Category> list = getChildrenNodes(categoryId, flag);
            if (flag == null) {
                list.add(categoryService.selectByPrimaryKey(categoryId));
            } else {
                list.add(engCategoryService.selectByPrimaryKey(categoryId));
            }
            // 去重
            removeSame(list);
            // 去除父节点,只保存子节点
            removeParentNodes(list, flag);
            for (Category cate : list) {
                ExpertCategory expertCategory = expertCategoryService.getExpertCategory(expertId, cate.getId());
                if (expertCategory == null) {
                    expertCategoryService.save(expert, cate.getId(), typeId);
                }
            }
        } else if ("0".equals(type)) {
            // 0代表删除
            // 判断是否是子节点,如果是父节点被取消则删除该节点的所有子节点
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("expertId", expertId);
            if (!isParent) {
                // 代表是子节点,只需要在中间表中删除自身即可
                map.put("categoryId", categoryId);
                expertCategoryService.deleteByMap(map);
            } else {
                // 需要删除所有的子节点
                List<ExpertCategory> allList = expertCategoryService.getListByExpertId(expertId, null);
                Expert expert = new Expert();
                expert.setId(expertId);
                map.put("categoryId", categoryId);
                expertCategoryService.deleteByMap(map);
                for (ExpertCategory category : allList) {
                    String id = category.getCategoryId();
                    boolean isDel = false;
                    a:
                    while (true) {
                        Category cate1 = null;
                        if (flag == null) {
                            cate1 = categoryService.selectByPrimaryKey(id);
                        } else {
                            cate1 = engCategoryService.selectByPrimaryKey(id);
                        }
                        if (cate1 != null) {
                            if (cate1.getParentId().equals(categoryId)) {
                                isDel = true;
                                break a;
                            } else {
                                id = cate1.getParentId();
                            }
                        } else {
                            if (id.equals(categoryId)) {
                                isDel = true;
                            }
                            break a;
                        }
                    }
                    if (isDel) {
                        map.put("categoryId", id);
                        expertCategoryService.deleteByMap(map);
                    }
                }
            }
        }
    }

    /**
     * 〈简述〉
     * 异步加载zTree
     * 〈详细描述〉
     *
     * @param expertId
     * @param id
     * @param categoryId
     * @return
     * @author WangHuijie
     */
    @ResponseBody
    @RequestMapping(value = "/getCategory", produces = "application/json;charset=UTF-8")
    public String getCategory(String expertId, String id, String categoryId) {
        String code = DictionaryDataUtil.findById(categoryId).getCode();
        if (code != null && code.equals("ENG_INFO_ID")) {
            List<CategoryTree> allCategories = new ArrayList<CategoryTree>();
            Expert expert = service.selectByPrimaryKey(expertId);
            if (id == null) {
                DictionaryData parent = dictionaryDataServiceI.getDictionaryData(categoryId);
                CategoryTree ct = new CategoryTree();
                ct.setName(parent.getName());
                ct.setId(parent.getId());
                ct.setIsParent("true");
                // 设置是否被选中
                ct.setChecked(isExpertChecked(ct.getId(), expertId, categoryId, "ENG_INFO"));
                allCategories.add(ct);
            } else {
                List<Category> childNodes = engCategoryService.findPublishTree(id, null);
                if (childNodes != null && childNodes.size() > 0) {
                    for (Category category : childNodes) {
                        CategoryTree ct = new CategoryTree();
                        ct.setName(category.getName());
                        ct.setId(category.getId());
                        ct.setParentId(category.getParentId());
                        // 判断是否为父级节点
                        List<Category> nodesList = engCategoryService.findPublishTree(category.getId(), null);
                        if (nodesList != null && nodesList.size() > 0) {
                            ct.setIsParent("true");
                        }
                        // 判断是否被选中
                        ct.setChecked(isExpertChecked(ct.getId(), expertId, categoryId, null));
                        allCategories.add(ct);
                    }
                    // 判断专家是否为被退回状态
                    if (expert.getStatus().equals("3")) {
                        // 查询所有的不通过的品目
                        ExpertAudit expertAudit = new ExpertAudit();
                        expertAudit.setExpertId(expertId);
                        expertAudit.setSuggestType("six");
                        List<ExpertAudit> auditList = expertAuditService.selectFailByExpertId(expertAudit);
                        for (CategoryTree treeNode : allCategories) {
                            for (ExpertAudit audit : auditList) {
                                if (audit.getAuditField().equals(treeNode.getId())) {
                                    // 如果该品目没有通过则设置树的title为不通过理由
                                    expertAudit.setAuditField(audit.getAuditField());
                                    treeNode.setAuditAdvise(expertAuditService.selectFailByExpertId(expertAudit).get(0).getAuditReason());
                                }
                            }
                        }
                    }
                }
            }
            return JSON.toJSONString(allCategories);
        } else {
            List<CategoryTree> allCategories = new ArrayList<CategoryTree>();
            Expert expert = service.selectByPrimaryKey(expertId);
            if (id == null) {
                DictionaryData parent = dictionaryDataServiceI.getDictionaryData(categoryId);
                CategoryTree ct = new CategoryTree();
                ct.setName(parent.getName());
                ct.setId(parent.getId());
                ct.setIsParent("true");
                // 设置是否被选中
                ct.setChecked(isExpertChecked(ct.getId(), expertId, categoryId, null));
                allCategories.add(ct);
            } else {
                List<Category> childNodes = categoryService.findPublishTree(id, null);
                if (childNodes != null && childNodes.size() > 0) {
                    for (Category category : childNodes) {
                        CategoryTree ct = new CategoryTree();
                        ct.setName(category.getName());
                        ct.setId(category.getId());
                        ct.setParentId(category.getParentId());
                        // 判断是否为父级节点
                        List<Category> nodesList = categoryService.findPublishTree(category.getId(), null);
                        if (nodesList != null && nodesList.size() > 0) {
                            ct.setIsParent("true");
                        }
                        // 判断是否被选中
                        ct.setChecked(isExpertChecked(ct.getId(), expertId, categoryId, null));
                        allCategories.add(ct);
                    }
                    // 判断专家是否为被退回状态
                    if (expert.getStatus().equals("3")) {
                        // 查询所有的不通过的品目
                        ExpertAudit expertAudit = new ExpertAudit();
                        expertAudit.setExpertId(expertId);
                        expertAudit.setSuggestType("six");
                        List<ExpertAudit> auditList = expertAuditService.selectFailByExpertId(expertAudit);
                        for (CategoryTree treeNode : allCategories) {
                            for (ExpertAudit audit : auditList) {
                                if (audit.getAuditField().equals(treeNode.getId())) {
                                    // 如果该品目没有通过则设置树的title为不通过理由
                                    expertAudit.setAuditField(audit.getAuditField());
                                    treeNode.setAuditAdvise(expertAuditService.selectFailByExpertId(expertAudit).get(0).getAuditReason());
                                }
                            }
                        }
                    }
                }
            }
            return JSON.toJSONString(allCategories);
        }
    }

    /**
     * 〈简述〉
     * 判断该节点是否需要被选中
     * 〈详细描述〉
     *
     * @param categoryId
     * @param expertId
     * @return
     * @author WangHuijie
     */
    public boolean isExpertChecked(String categoryId, String expertId, String typeId, String flag) {
        List<ExpertCategory> allCategoryList = expertCategoryService.getListByExpertId(expertId, typeId);
        boolean isChecked = false;
        for (ExpertCategory expertCategory : allCategoryList) {
            String id = expertCategory.getCategoryId();
            if (categoryId.equals(id)) {
                isChecked = true;
                break;
            } else {
                while (true) {
                    Category cate = null;
                    if (flag == null) {
                        cate = categoryService.selectByPrimaryKey(id);
                    } else {
                        cate = engCategoryService.selectByPrimaryKey(id);
                    }
                    if (cate != null) {
                        if (cate.getParentId().equals(categoryId)) {
                            isChecked = true;
                            break;
                        } else {
                            id = cate.getParentId();
                        }
                    } else {
                        if (id.equals(categoryId)) {
                            isChecked = true;
                        }
                        break;
                    }
                }
            }
        }
        return isChecked;
    }

    /**
     * 〈简述〉
     * 判断该节点是否需要被选中
     * 〈详细描述〉
     *
     * @param categoryId
     * @param expertId
     * @return
     * @author WangHuijie
     */
    public boolean isSupplierChecked(String categoryId, String supplierId, String type) {
        List<SupplierItem> category = supplierItemService.getSupplierIdCategoryId(supplierId, categoryId, type);
        if (category != null && category.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
		/*Expert expert = service.selectByPrimaryKey(expertId);
		    List<CategoryTree> allCategories = new ArrayList<CategoryTree>();
		    DictionaryData parent = dictionaryDataServiceI.getDictionaryData(id);
		    CategoryTree ct = new CategoryTree();
		    ct.setName(parent.getName());
		    ct.setId(parent.getId());
		    ct.setIsParent("true");
		    // 判断是否被选中
		    List<ExpertCategory> allCategory = expertCategoryService.getListByExpertId(expertId);
		    for (ExpertCategory expertCategory : allCategory) {
		        String parentId = categoryService.selectByPrimaryKey(expertCategory.getCategoryId()).getParentId();
		        if (parentId != null && parentId.equals(ct.getId())) {
		            ct.setChecked(true);
		        }
		    }
		    allCategories.add(ct);
		    ct.setChecked(isCheckedById(ct.getId(), expertId));
		    allCategories.add(ct);
		    // 递归查询出所有节点
		    List<Category> categoryTree = getCategoryTree(ct.getId());
		    // 遍历所有节点添加到list中
		    for (Category c : categoryTree) {
		        List<Category> list1 = categoryService.findPublishTree(c.getId());
		        CategoryTree ct1 = new CategoryTree();
		        ct1.setName(c.getName());
		        ct1.setParentId(c.getParentId());
		        ct1.setId(c.getId());
		        // 设置是否为父级
		        if (!list1.isEmpty()) {
		            ct1.setIsParent("true");
		        } else {
		            ct1.setIsParent("false");
		        }
		        ct1.setChecked(isCheckedById(ct1.getId(), expertId));
		        // 判断是否被退回(不通过)
		        if ("3".equals(expert.getStatus())) {
		            // 判断该节点有没有被退回
		            ExpertAudit expertAudit = new ExpertAudit();
		            expertAudit.setSuggestType("six");
		            expertAudit.setAuditField(c.getId());
		            List<ExpertAudit> audit = expertAuditService.selectFailByExpertId(expertAudit);
		            if (audit != null && !audit.isEmpty() && audit.get(0) != null) {
		                ct1.setAuditAdvise(audit.get(0).getAuditReason());
		            }
		        }
		        allCategories.add(ct1);
		    }
		    return JSON.toJSONString(allCategories);*/

    /**
     * 〈简述〉
     * 根据产品id递归判断是否需要被选中
     * 〈详细描述〉
     *
     * @param id
     * @return
     * @author WangHuijie
     */
    public boolean isCheckedById(String id, String expertId) {
        boolean isChecked = false;
        // 先判断该节点有没有被选中,如果有则直接返回true
        ExpertCategory expCategory = expertCategoryService.getExpertCategory(expertId, id);
        if (expCategory != null) {
            isChecked = true;
        } else {
            List<Category> childList = categoryService.findPublishTree(id, null);
            for (Category category : childList) {
                // 判断该节点有无子节点
                List<Category> list = categoryService.findPublishTree(category.getId(), null);
                if (list.isEmpty()) {
                    // list为空代表没有子节点
                    ExpertCategory ec = expertCategoryService.getExpertCategory(expertId, category.getId());
                    // 判断该节点是否被选中
                    if (ec != null) {
                        isChecked = true;
                        break;
                    }
                } else {
                    // 代表有子节点,递归查询是否有子节点被选中
                    if (isCheckedById(category.getId(), expertId)) {
                        // 如果递归结果为true,则代表该节点要被选中
                        isChecked = true;
                    }
                }
            }
        }
        return isChecked;
    }

    /**
     * 〈简述〉
     * 递归查询所有Tree节点
     * 〈详细描述〉
     *
     * @param id
     * @return
     * @author WangHuijie
     */
    public List<Category> getCategoryTree(String id) {
        List<Category> childList = new ArrayList<Category>();
        List<Category> list = categoryService.findTreeByStatus(id, StaticVariables.CATEGORY_PUBLISH_STATUS);
        childList.addAll(list);
        for (Category cate : list) {
            childList.addAll(getCategoryTree(cate.getId()));
        }
        return childList;
    }

    @RequestMapping(value = "getAllCategory", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getAllCategory(String expertId) {
        Expert expert = service.selectByPrimaryKey(expertId);
        List<DictionaryData> allCategoryList = new ArrayList<DictionaryData>();
        List<String> allTypeId = new ArrayList<String>();
        for (String id : expert.getExpertsTypeId().split(",")) {
            allTypeId.add(id);
        }
        a:
        for (int i = 0; i < allTypeId.size(); i++) {
            DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(allTypeId.get(i));
            if (dictionaryData.getName().contains("经济")) {
                allTypeId.remove(i);
                continue a;
            }
            ;
            allCategoryList.add(dictionaryData);
        }
        return JSON.toJSONString(allCategoryList);
    }

    /**
     * 〈简述〉
     * 判断提交审核后有没有超过45天以及查询初审机构信息
     * 〈详细描述〉
     *
     * @param expertId
     * @return
     * @throws Exception
     * @author WangHuijie
     */
    @RequestMapping(value = "validateAuditTime", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String validateAuditTime(String userId) throws Exception {
        HashMap<String, Object> allInfo = new HashMap<String, Object>();
        // 根据userId查询出Expert
        Expert expert = service.selectByPrimaryKey(userService.getUserById(userId).getTypeId());
        Date submitDate = expert.getUpdatedAt();
        allInfo.put("submitDate", new SimpleDateFormat("yyyy年MM月dd日").format(submitDate));
        // 判断有没有超过45天
        String isok;
        int betweenDays = service.daysBetween(submitDate);
        if (betweenDays > 45) {
            isok = "0";
        } else {
            isok = "1";
        }
        allInfo.put("isok", isok);
        // 查询初审机构信息
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", expert.getPurchaseDepId());
        map.put("typeName", "1");
        List<PurchaseDep> depList = purchaseOrgnizationService.findPurchaseDepList(map);
        if (depList != null && depList.size() > 0) {
            PurchaseDep purchaseDep = depList.get(0);
            allInfo.put("contact", purchaseDep.getContact() == null ? "暂无" : purchaseDep.getContact());
            allInfo.put("contactTelephone", purchaseDep.getContactTelephone() == null ? "暂无" : purchaseDep.getContactTelephone());
            allInfo.put("contactAddress", purchaseDep.getContactAddress() == null ? "暂无" : purchaseDep.getContactAddress());
            allInfo.put("contactAddress", purchaseDep.getContactAddress() == null ? "暂无" : purchaseDep.getContactAddress());
            allInfo.put("businessRange", purchaseDep.getBusinessRange() == null ? "暂无" : purchaseDep.getBusinessRange());

        }
        return JSON.toJSONString(allInfo);
    }

    @RequestMapping(value = "showJiGou", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String showJiGou(String pId, String zId) {
        // 全部的采购机构
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("typeName", "1");
        List<PurchaseDep> allPurList = purchaseOrgnizationService.findPurchaseDepList(map);
        for (PurchaseDep purchaseDep : allPurList) {
            if ((purchaseDep.getProvinceId() != null && purchaseDep.getCityId() == null && purchaseDep.getProvinceId().equals(pId)) || (purchaseDep.getCityId() != null && purchaseDep.getCityId().equals(zId))) {
                purchaseDep.setFlag("1");
            } else {
                purchaseDep.setFlag("0");
            }
        }
        return JSON.toJSONString(allPurList);
    }

    /**
     * @param expertId
     * @param typeMap
     * @param @return
     * @return String
     * @Title: isAttachment
     * @author ShaoYangYang
     * @date 2016年11月9日 下午3:02:58
     * @Description: TODO 判断是否有合同书和申请表的附件
     */
    private String isAttachment(String expertId, Map<String, Object> typeMap) {
        Map<String, Object> mapAttachment = new HashMap<>();
        mapAttachment.put("isDeleted", 0);
        mapAttachment.put("businessId", expertId);
        mapAttachment.put("typeId", typeMap.get("EXPERT_CONTRACT_TYPEID"));
        List<ExpertAttachment> attList = attachmentService
                .selectListByMap(mapAttachment);
        Map<String, Object> mapAttachment2 = new HashMap<>();
        mapAttachment2.put("isDeleted", 0);
        if (StringUtils.isEmpty(expertId)) {
            return "2";
        }
        mapAttachment2.put("businessId", expertId);
        mapAttachment2.put("typeId", typeMap.get("EXPERT_APPLICATION_TYPEID"));
        List<ExpertAttachment> attList2 = attachmentService
                .selectListByMap(mapAttachment2);
        if ((attList != null && attList.size() > 0) ||
                (attList2 != null && attList2.size() > 0)) {
            // 有附件为1
            return "1";
        } else {
            // 没有附件为2
            return "2";
        }

    }

    /**
     * @param @return
     * @return Map<String,Object>
     * @Title: getTypeId
     * @author ShaoYangYang
     * @date 2016年11月9日 下午2:32:38
     * @Description: TODO 封装附件类型
     */
    private Map<String, Object> getTypeId() {
        DictionaryData dd = new DictionaryData();
        Map<String, Object> typeMap = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                // 军官证件
                dd.setCode("EXPERT_IDNUMBER");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_IDNUMBER_TYPEID", find.get(0).getId());
            }
            if (i == 1) {
                // 职称证书
                dd.setCode("EXPERT_TITLE");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_TITLE_TYPEID", find.get(0).getId());
            }
            if (i == 2) {
                // 申请表
                dd.setCode("EXPERT_APPLICATION");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_APPLICATION_TYPEID", find.get(0).getId());
            }
            if (i == 3) {
                // 学历证书
                dd.setCode("EXPERT_ACADEMIC");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_ACADEMIC_TYPEID", find.get(0).getId());
            }
            if (i == 4) {
                // 学位证书
                dd.setCode("EXPERT_DEGREE");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_DEGREE_TYPEID", find.get(0).getId());
            }
            if (i == 5) {
                // 个人照片
                dd.setCode("EXPERT_PHOTO");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_PHOTO_TYPEID", find.get(0).getId());
            }
            if (i == 6) {
                // 合同书
                dd.setCode("EXPERT_CONTRACT");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_CONTRACT_TYPEID", find.get(0).getId());
            }
            if (i == 7) {
                // 军官证件
                dd.setCode("EXPERT_IDCARDNUMBER");
                List<DictionaryData> find = dictionaryDataServiceI.find(dd);
                typeMap.put("EXPERT_IDCARDNUMBER_TYPEID", find.get(0).getId());
            }
        }
        return typeMap;
    }

    /**
     * @param model
     * @param @return
     * @return String
     * @Title: toEditBasicInfo
     * @author lkzx
     * @date 2016年9月1日 上午11:12:55
     * @Description: TODO 跳转到修改个人信息
     */
    @RequestMapping("/toEditBasicInfo")
    public String toEditBasicInfo(@RequestParam("id") String id,
                                  HttpServletRequest request, HttpServletResponse response,
                                  Model model) {
        Expert expert = service.selectByPrimaryKey(id);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("typeName", "1");
        List<PurchaseDep> depList = purchaseOrgnizationService
                .findPurchaseDepList(null);
        if (depList != null && depList.size() > 0) {
            PurchaseDep purchaseDep = depList.get(0);
            model.addAttribute("purchase", purchaseDep);
        }
        // 查询数据字典中的证件类型配置数据
        List<DictionaryData> idTypeList = DictionaryDataUtil.find(9);
        model.addAttribute("idTypeList", idTypeList);
        // 查询数据字典中的政治面貌配置数据
        List<DictionaryData> zzList = DictionaryDataUtil.find(10);
        model.addAttribute("zzList", zzList);
        // 查询数据字典中的最高学历配置数据
        List<DictionaryData> xlList = DictionaryDataUtil.find(11);
        model.addAttribute("xlList", xlList);
        // 查询数据字典中的最高学位配置数据
        List<DictionaryData> xwList = DictionaryDataUtil.find(21);
        model.addAttribute("xwList", xwList);
        // 查询数据字典中的专家来源配置数据
        List<DictionaryData> lyTypeList = DictionaryDataUtil.find(12);
        model.addAttribute("lyTypeList", lyTypeList);
        // 查询数据字典中的性别配置数据
        List<DictionaryData> sexList = DictionaryDataUtil.find(13);
        model.addAttribute("sexList", sexList);
        // 产品类型数据字典
        List<DictionaryData> spList = DictionaryDataUtil.find(6);
        model.addAttribute("spList", spList);
        // 货物类型数据字典
        List<DictionaryData> hwList = DictionaryDataUtil.find(8);
        model.addAttribute("hwList", hwList);
        // 经济类型数据字典
        List<DictionaryData> jjTypeList = DictionaryDataUtil.find(19);
        model.addAttribute("jjList", jjTypeList);

        // 专家系统key
        Integer expertKey = Constant.EXPERT_SYS_KEY;
        Map<String, Object> typeMap = getTypeId();
        // typrId集合
        model.addAttribute("typeMap", typeMap);
        // 业务id就是专家id
        model.addAttribute("sysId", id);
        // Constant.EXPERT_SYS_VALUE;
        model.addAttribute("expertKey", expertKey);
        expert.setExpertsFrom(dictionaryDataServiceI.getDictionaryData(expert.getExpertsFrom()).getCode());
        model.addAttribute("expert", expert);
        return "ses/ems/expert/edit_basic_info";
    }

    /**
     * @param model
     * @return String
     * @Title: toBasicInfo
     * @author lkzx
     * @date 2016年9月1日 上午11:12:55
     * @Description: TODO 跳转到审核页面
     */
    @RequestMapping("/toShenHe")
    public String toShenHe(@RequestParam("id") String id,
                           HttpServletRequest request, HttpServletResponse response,
                           Model model) {
        Expert expert = service.selectByPrimaryKey(id);
        // 查询出采购机构
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", expert.getPurchaseDepId());
        map.put("typeName", "1");
        List<PurchaseDep> depList = purchaseOrgnizationService.findPurchaseDepList(map);
        if (depList != null && depList.size() > 0) {
            PurchaseDep purchaseDep = depList.get(0);
            model.addAttribute("purchase", purchaseDep);
        }
        // 查询数据字典中的证件类型配置数据
        List<DictionaryData> idTypeList = DictionaryDataUtil.find(9);
        model.addAttribute("idTypeList", idTypeList);
        // 查询数据字典中的政治面貌配置数据
        List<DictionaryData> zzList = DictionaryDataUtil.find(10);
        model.addAttribute("zzList", zzList);
        // 查询数据字典中的最高学历配置数据
        List<DictionaryData> xlList = DictionaryDataUtil.find(11);
        model.addAttribute("xlList", xlList);
        // 查询数据字典中的专家来源配置数据
        List<DictionaryData> lyTypeList = DictionaryDataUtil.find(12);
        model.addAttribute("lyTypeList", lyTypeList);
        // 查询数据字典中的性别配置数据
        List<DictionaryData> sexList = DictionaryDataUtil.find(13);
        model.addAttribute("sexList", sexList);
        // 产品类型数据字典
        List<DictionaryData> spList = DictionaryDataUtil.find(6);
        model.addAttribute("spList", spList);
        // 货物类型数据字典
        List<DictionaryData> hwList = DictionaryDataUtil.find(8);
        model.addAttribute("hwList", hwList);
        // 经济类型数据字典
        List<DictionaryData> jjTypeList = DictionaryDataUtil.find(19);
        model.addAttribute("jjList", jjTypeList);
        // 专家系统key
        Integer expertKey = Constant.EXPERT_SYS_KEY;
        Map<String, Object> typeMap = getTypeId();
        // typrId集合
        model.addAttribute("typeMap", typeMap);
        // 业务id就是专家id
        model.addAttribute("sysId", id);
        // Constant.EXPERT_SYS_VALUE;
        model.addAttribute("expertKey", expertKey);
        request.setAttribute("expert", expert);
        return "ses/ems/expert/audit";
    }

    @RequestMapping("/toSecondAudit")
    public String toSecondAudit(@RequestParam("id") String id,
                                HttpServletRequest request, HttpServletResponse response,
                                Model model) {
        Expert expert = service.selectByPrimaryKey(id);
        // 查询出采购机构
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", expert.getPurchaseDepId());
        map.put("typeName", "1");
        List<PurchaseDep> depList = purchaseOrgnizationService
                .findPurchaseDepList(map);
        if (depList != null && depList.size() > 0) {
            PurchaseDep purchaseDep = depList.get(0);
            model.addAttribute("purchase", purchaseDep);
        }
        // 查询数据字典中的证件类型配置数据
        List<DictionaryData> idTypeList = DictionaryDataUtil.find(9);
        model.addAttribute("idTypeList", idTypeList);
        // 查询数据字典中的政治面貌配置数据
        List<DictionaryData> zzList = DictionaryDataUtil.find(10);
        model.addAttribute("zzList", zzList);
        // 查询数据字典中的最高学历配置数据
        List<DictionaryData> xlList = DictionaryDataUtil.find(11);
        model.addAttribute("xlList", xlList);
        // 查询数据字典中的专家来源配置数据
        List<DictionaryData> lyTypeList = DictionaryDataUtil.find(12);
        model.addAttribute("lyTypeList", lyTypeList);
        // 查询数据字典中的性别配置数据
        List<DictionaryData> sexList = DictionaryDataUtil.find(13);
        model.addAttribute("sexList", sexList);
        // 产品类型数据字典
        List<DictionaryData> spList = DictionaryDataUtil.find(6);
        model.addAttribute("spList", spList);
        // 货物类型数据字典
        List<DictionaryData> hwList = DictionaryDataUtil.find(8);
        model.addAttribute("hwList", hwList);
        // 经济类型数据字典
        List<DictionaryData> jjTypeList = DictionaryDataUtil.find(19);
        model.addAttribute("jjList", jjTypeList);
        // 专家系统key
        Integer expertKey = Constant.EXPERT_SYS_KEY;
        Map<String, Object> typeMap = getTypeId();
        // typrId集合
        model.addAttribute("typeMap", typeMap);
        // 业务id就是专家id
        model.addAttribute("sysId", id);
        // Constant.EXPERT_SYS_VALUE;
        model.addAttribute("expertKey", expertKey);
        request.setAttribute("expert", expert);
        return "ses/ems/expert/second_audit";
    }

    /**
     * @param @return
     * @return String
     * @Title: shenhe
     * @author lkzx
     * @date 2016年9月5日 下午2:12:19
     * @Description: TODO 执行审核专家信息
     */
    @RequestMapping("/shenhe")
    public String shenhe(@RequestParam("isPass") String isPass, Expert expert,
                         @RequestParam("remark") String remark, HttpSession session) {
        // 当前登录用户
        User user = (User) session.getAttribute("loginUser");
        // 去除临时专家角色,根据状态去判断登录的跳转路径
		/*User expertUser = userService.findByTypeId(expert.getId());
		if ("1".equals(isPass)) {
		    Role role = new Role();
		    role.setCode("EXPERT_R");
		    List<Role> listRole = roleService.find(role);
		    if (listRole != null && listRole.size() > 0) {
		        Userrole userrole = new Userrole();
		        userrole.setRoleId(listRole.get(0));
		        userrole.setUserId(expertUser);
		        */
        /** 给该用户初始化进口代理商角色 */
		/*
		                userService.saveRelativity(userrole);
		                String[] roleIds = listRole.get(0).getId().split(",");
		                List<String> listMenu = menuService.findByRids(roleIds);
		                */
        /** 给用户初始化进口代理商菜单权限 */
		/*
		                for (String menuId : listMenu) {
		                    UserPreMenu upm = new UserPreMenu();
		                    PreMenu preMenu = menuService.get(menuId);
		                    upm.setPreMenu(preMenu);
		                    upm.setUser(expertUser);
		                    userService.saveUserMenu(upm);
		                }
		            }
		        }*/
        // 专家状态修改
        expert.setStatus(isPass);
        // 审核时初始化专家诚信积分
        expert.setHonestyScore(0);
        // 审核信息增加
        expertAuditService.auditExpert(expert, remark, user);
        // 执行修改
        service.updateByPrimaryKeySelective(expert);
        return "redirect:findAllExpert.html";
    }

    @RequestMapping("/secondAudit")
    public String secondAudit(@RequestParam("isPass") String isPass, Expert expert,
                              HttpSession session) {
        // 专家状态修改
        expert.setStatus(isPass);
        // 执行修改
        service.updateByPrimaryKeySelective(expert);
        return "redirect:secondAuditExpert.html";
    }

    /**
     * @param @return
     * @return String
     * @throws IOException
     * @Title: toEditBasicInfo
     * @author lkzx
     * @date 2016年9月1日 上午11:14:38
     * @Description: TODO 后台 跳到修改个人信息页面
     */
    @RequestMapping("/toPersonInfo")
    public String toPersonInfo(Model model, HttpSession session,
                               HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        User user = (User) session.getAttribute("loginUser");
        model.addAttribute("user", user);
        // 判断用户的类型为专家类型
        if (user != null) {
            String typeId = user.getTypeId();
            if (typeId != null && StringUtils.isNotEmpty(typeId)) {
                Expert expert = service.selectByPrimaryKey(typeId);
                HashMap<String, Object> map = new HashMap<String, Object>();
                if (expert != null) {
                    String purchaseDepId = expert.getPurchaseDepId();
                    if (purchaseDepId != null &&
                            StringUtils.isNotEmpty(purchaseDepId)) {
                        map.put("id", purchaseDepId);
                        map.put("typeName", "1");
                        // 采购机构
                        List<PurchaseDep> depList = purchaseOrgnizationService
                                .findPurchaseDepList(map);
                        if (depList != null && depList.size() > 0) {
                            PurchaseDep purchaseDep = depList.get(0);
                            model.addAttribute("purchase", purchaseDep);
                        }
                    }
                    // 查询数据字典中的证件类型配置数据
                    List<DictionaryData> idTypeList = DictionaryDataUtil
                            .find(9);
                    model.addAttribute("idTypeList", idTypeList);
                    // 查询数据字典中的政治面貌配置数据
                    List<DictionaryData> zzList = DictionaryDataUtil.find(10);
                    model.addAttribute("zzList", zzList);
                    // 查询数据字典中的最高学历配置数据
                    List<DictionaryData> xlList = DictionaryDataUtil.find(11);
                    model.addAttribute("xlList", xlList);
                    // 查询数据字典中的专家来源配置数据
                    List<DictionaryData> lyTypeList = DictionaryDataUtil
                            .find(12);
                    model.addAttribute("lyTypeList", lyTypeList);
                    // 查询数据字典中的性别配置数据
                    List<DictionaryData> sexList = DictionaryDataUtil.find(13);
                    model.addAttribute("sexList", sexList);
                    // 产品类型数据字典
                    List<DictionaryData> spList = DictionaryDataUtil.find(6);
                    model.addAttribute("spList", spList);
                    // 货物类型数据字典
                    List<DictionaryData> hwList = DictionaryDataUtil.find(8);
                    model.addAttribute("hwList", hwList);
                    // 经济类型数据字典
                    List<DictionaryData> jjTypeList = DictionaryDataUtil.find(19);
                    model.addAttribute("jjList", jjTypeList);
                    // 专家系统key
                    Integer expertKey = Constant.EXPERT_SYS_KEY;
                    Map<String, Object> typeMap = getTypeId();
                    // typrId集合
                    model.addAttribute("typeMap", typeMap);
                    // 业务id就是专家id
                    model.addAttribute("sysId", expert.getId());
                    // Constant.EXPERT_SYS_VALUE;
                    model.addAttribute("expertKey", expertKey);
                    model.addAttribute("expert", expert);
                }
            }
        }
        return "ses/ems/expert/person_info";
    }

    /**
     * @param @return
     * @return String
     * @throws IOException
     * @Title: editBasicInfo
     * @author lkzx
     * @date 2016年9月1日 上午11:14:38
     * @Description: TODO 修改个人信息
     */
    @RequestMapping("/editBasicInfo")
    public String editBasicInfo(Expert expert, Model model,
                                HttpSession session, @RequestParam("token2") String token2,
                                HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        User user = (User) session.getAttribute("loginUser");
        // 修改个人信息
        service.editBasicInfo(expert, user);
        return "redirect:toPersonInfo.html";
    }

    /**
     * @param @return
     * @return String
     * @throws IOException
     * @Title: edit
     * @author lkzx
     * @date 2016年9月1日 上午11:14:38
     * @Description: TODO 修改个人全部信息
     */
    @RequestMapping("/edit")
    public String edit(Expert expert, Model model, HttpSession session,
                       @RequestParam("token2") String token2, HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        Object tokenValue = session.getAttribute("tokenSession");
        if (tokenValue != null && tokenValue.equals(token2)) {
            // 正常提交
            session.removeAttribute("tokenSession");
            // 修改状态为已提交
            expert.setIsSubmit("1");
            // 修改时间
            expert.setUpdatedAt(new Date());
            service.updateByPrimaryKeySelective(expert);
            return "redirect:findAllExpert.html";
        } else {
            // 重复提交 这里未做重复提醒，只是不重复修改
            return "redirect:findAllExpert.html";
        }
    }

    /**
     * @param @return
     * @return String
     * @throws IOException
     * @Title: add
     * @author lkzx
     * @date 2016年9月1日 上午11:14:38
     * @Description: TODO 新增个人信息
     */
    @RequestMapping("/add")
    public String add(String categoryId, String sysId, Expert expert,
                      String userId, Model model, RedirectAttributes attr,
                      HttpSession session, String token2, HttpServletRequest request,
                      HttpServletResponse response) {
        try {
            Object tokenValue = session.getAttribute("tokenSession");
            String expertId = sysId;
            if (tokenValue != null && tokenValue.equals(token2)) {
                // 正常提交
                session.removeAttribute("tokenSession");
                User user = (User) session.getAttribute("loginUser");
                // 用户信息处理
                service.userManager(user, userId, expert, expertId);
                // 调用service逻辑代码 实现提交
                Map<String, Object> map = service.saveOrUpdate(expert,
                        expertId, categoryId, null, userId);
                if (map != null && !map.isEmpty()) {
                    attr.addAttribute("userId", userId);
                    return "redirect:toAddBasicInfo.html";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 未做异常处理
        }
        attr.addAttribute("userId", userId);
        return "redirect:toAddBasicInfo.html";
    }

    /**
     * @param @return
     * @return String
     * @throws IOException
     * @Title: add
     * @author lkzx
     * @date 2016年9月1日 上午11:14:38
     * @Description: TODO 新增个人信息
     */
    @ResponseBody
    @RequestMapping("/add1")
    public String add1(String categoryId, String sysId, Expert expert,
                       String userId, Model model, RedirectAttributes attr,
                       HttpSession session, String token2, HttpServletRequest request,
                       HttpServletResponse response, String gitFlag) {
        Expert exp = service.selectByPrimaryKey(expert.getId());
        if ("1".equals(exp.getIsSubmit())) {
            return "1";
        } else {
            try {
                String expertId = sysId;
                // 正常提交
                User user = (User) session.getAttribute("loginUser");
                // 用户信息处理
                service.userManager(user, userId, expert, expertId);
                // 调用service逻辑代码 实现提交
                service.saveOrUpdate(expert, expertId, categoryId, gitFlag, userId);
                expert.setIsDo("0");
                //已提交
                expert.setIsSubmit("1");
                Expert temp = service.selectByPrimaryKey(expertId);
                if ("3".equals(temp.getStatus())) {
                    //删除之前的审核信息
					/*expertAuditService.updateIsDeleteByExpertId(expertId);*/
                    expertAuditService.deleteByExpertId(expertId);
                    //未审核
                    expert.setStatus("0");
					/*expert.setIsDelete((short) 1);*/
                }
                //修改时间
                expert.setSubmitAt(new Date());
                expert.setAuditAt(new Date());
                service.updateByPrimaryKeySelective(expert);
            } catch (Exception e) {
                e.printStackTrace();
                // 未做异常处理
            }
            attr.addAttribute("userId", userId);
            return "0";
            //return "redirect:toAddBasicInfo.html";
        }
    }

    /**
     * @param @param sysId
     * @param @param expert
     * @param @param categoryId
     * @param @param userId
     * @param @param model
     * @param @param session
     * @return void
     * @Title: zanCun
     * @author ShaoYangYang
     * @date 2016年11月8日 上午10:40:42
     * @Description: TODO ajax暂存逻辑
     */
    @RequestMapping("zanCun")
    @ResponseBody
    public Expert zanCun(String sysId, Expert expert, String categoryId,
                         String userId, Model model, HttpSession session) {
        try {
            // 预定义id
            String expertId = sysId;
            User user = (User) session.getAttribute("loginUser");
            // 用户信息处理
            service.userManager(user, userId, expert, expertId);
            // 调用service逻辑 实现暂存
            StringBuffer categories = new StringBuffer();
            List<ExpertCategory> allList = expertCategoryService.getListByExpertId(expert.getId(), null);
            for (ExpertCategory expertCategory : allList) {
                Category category = categoryService.selectByPrimaryKey(expertCategory.getCategoryId());
                categories.append(category == null ? "" : category.getName());
                categories.append("、");
            }
            if (!"".equals(categories.toString())) {
                String productCategories = categories.substring(0, categories.length() - 1);
                expert.setProductCategories(productCategories);
            }
            service.zanCunInsert(expert, expertId, categoryId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return expert;
    }

    /**
     * @param @param  id
     * @param @return
     * @return List<ExpertCategory>
     * @Title: getCategoryByExpertId
     * @author ShaoYangYang
     * @date 2016年9月28日 下午5:14:00
     * @Description: TODO 根据专家id查询该专家关联的品目code
     */
    @RequestMapping("/getCategoryByExpertId")
    @ResponseBody
    public String getCategoryByExpertId(String expertId) {
        List<ExpertCategory> list = expertCategoryService.getListByExpertId(expertId, null);
        List<String> categoryList = new ArrayList<String>();
        for (ExpertCategory expertCategory : list) {
            categoryList.add(expertCategory.getCategoryId());
        }
        return JSON.toJSONString(categoryList);
    }

    /**
     * @param @param  id
     * @param @return
     * @return List<ExpertCategory>
     * @Title: getCategoryByExpertId
     * @author ShaoYangYang
     * @date 2016年9月28日 下午5:14:00
     * @Description: TODO 根据专家id查询该专家的采购机构id
     */
    @RequestMapping("/getPurDepIdByExpertId")
    @ResponseBody
    public String getPurDepIdByExpertId(@RequestParam("expertId") String id) {
        Expert expert = service.selectByPrimaryKey(id);
        if (expert != null) {
            String purDepId = expert.getPurchaseDepId();
            return purDepId;
        }
        return null;
    }

    /**
     * @param
     * @return void
     * @Title: deleteAll
     * @author ShaoYangYang
     * @date 2016年9月8日 下午3:53:36
     * @Description: TODO 软删除
     */
    @RequestMapping("/deleteAll")
    public String deleteAll(@RequestParam("ids") String ids) {
        String[] id = ids.split(",");
        // 循环删除选中的数据
        for (String string : id) {
            Expert expert = service.selectByPrimaryKey(string);
            if (expert != null) {
                expert.setIsDelete((short) 1);
                service.updateByPrimaryKeySelective(expert);
            }
        }
        return "redirect:findAllExpert.html";
    }

    /**
     * @param @return
     * @return String
     * @Title: findAllExpert
     * @author lkzx
     * @date 2016年9月2日 下午5:44:37
     * @Description: TODO 查询所有专家 可以条件查询
     */
    @RequestMapping("/findAllExpert")
    public String findAllExpert(Expert expert, Integer page,
                                HttpServletRequest request, HttpServletResponse response) {
        List<Expert> allExpert = service.selectAllExpert(page == null ? 0 :
                page, expert);
        for (Expert exp : allExpert) {
            DictionaryData dictionaryData = dictionaryDataServiceI
                    .getDictionaryData(exp.getGender());
            exp.setGender(dictionaryData == null ? "" : dictionaryData.getName());
            StringBuffer expertType = new StringBuffer();
            if (exp.getExpertsTypeId() != null) {
                for (String typeId : exp.getExpertsTypeId().split(",")) {
                    DictionaryData data = dictionaryDataServiceI.getDictionaryData(typeId);
                    if (6 == data.getKind()) {
                        expertType.append(data.getName() + "技术、");
                    } else {
                        expertType.append(data.getName() + "、");
                    }
                }
                String expertsType = expertType.toString().substring(0, expertType.length() - 1);
                exp.setExpertsTypeId(expertsType);
            } else {
                exp.setExpertsTypeId("");
            }
        }
        // 查询数据字典中的专家来源配置数据
        List<DictionaryData> lyTypeList = DictionaryDataUtil.find(12);
        request.setAttribute("lyTypeList", lyTypeList);
        // 查询数据字典中的专家类别数据
        List<DictionaryData> jsTypeList = DictionaryDataUtil.find(6);
        for (DictionaryData data : jsTypeList) {
            data.setName(data.getName() + "技术");
        }
        List<DictionaryData> jjTypeList = DictionaryDataUtil.find(19);
        jsTypeList.addAll(jjTypeList);
        request.setAttribute("expTypeList", jsTypeList);
        request.setAttribute("result", new PageInfo<Expert>(allExpert));
        request.setAttribute("expert", expert);
        return "ses/ems/expert/list";
    }

    /**
     * 〈简述〉
     * 专家复审列表展示
     * 〈详细描述〉
     *
     * @param expert
     * @param page
     * @param request
     * @param response
     * @return
     * @author WangHuijie
     */
    @RequestMapping("/secondAuditExpert")
    public String secondAuditExpert(Expert expert, Integer page,
                                    HttpServletRequest request, HttpServletResponse response) {
        List<Expert> allExpert = service.selectAllExpert(page == null ? 0 :
                page, expert);
        ProjectExtract projectExtract = new ProjectExtract();
        a:
        for (Expert exp : allExpert) {
            // 判断是否被抽取
            projectExtract.setExpertId(exp.getId());
            projectExtract.setReason("1");
            List<ProjectExtract> list = projectExtractService.list(projectExtract);
            if (list.isEmpty()) {
                allExpert.remove(exp);
                continue a;
            }
            DictionaryData dictionaryData = dictionaryDataServiceI
                    .getDictionaryData(exp.getGender());
            exp.setGender(dictionaryData == null ? "" : dictionaryData.getName());
        }
        // 查询数据字典中的专家来源配置数据
        List<DictionaryData> lyTypeList = DictionaryDataUtil.find(12);
        request.setAttribute("lyTypeList", lyTypeList);
        request.setAttribute("result", new PageInfo<Expert>(allExpert));
        request.setAttribute("expert", expert);
        return "ses/ems/expert/second_audit_list";
    }

    /**
     * @param @return
     * @return String
     * @Title: findAllExpertShenHe
     * @author lkzx
     * @date 2016年9月2日 下午5:44:37
     * @Description: TODO 查询所有审核状态的专家 可以条件查询
     */
    @RequestMapping("/findAllExpertShenHe")
    public String findAllExpertShenHe(Expert expert, Integer page,
                                      HttpServletRequest request, HttpServletResponse response) {
        List<Expert> allExpert = service.selectAllExpert(page == null ? 1 :
                page, expert);
        request.setAttribute("result", new PageInfo<Expert>(allExpert));
        request.setAttribute("expert", expert);
        return "ses/ems/expert/audit_list";
    }

    /**
     * @param @return
     * @return String
     * @Title: toShenHeExpert
     * @author lkzx
     * @date 2016年9月2日 下午5:44:37
     * @Description: TODO 跳转到未审核专家
     */
    @RequestMapping("/toShenHeExpert")
    public String toShenHeExpert(Expert expert, Integer page,
                                 HttpServletRequest request, HttpServletResponse response) {
        expert.setStatus("0");
        List<Expert> allExpert = service.selectAllExpert(page == null ? 1 :
                page, expert);
        request.setAttribute("result", new PageInfo<Expert>(allExpert));
        request.setAttribute("expert", expert);
        return "ses/ems/expert/audit_list";
    }

    /**
     * @param @param  loginName
     * @param @param  model
     * @param @return
     * @return List<User>
     * @Title: findAllLoginName
     * @author ShaoYangYang
     * @date 2016年9月14日 下午6:13:09
     * @Description: TODO 用户名唯一判断
     */
    @RequestMapping("/findAllLoginName")
    @ResponseBody
    public String findAllLoginName(@RequestParam("loginName") String loginName,
                                   Model model) {
        List<User> userList = userService.findByLoginName(loginName);
        if (userList != null && userList.size() > 0) {
            return "1";
        }
        return "2";
    }

    /**
     * @param @param  sysId
     * @param @param  typeId
     * @param @return
     * @return List<ExpertAttachment>
     * @Title: findAttachment
     * @author ShaoYangYang
     * @date 2016年11月8日 下午5:35:46
     * @Description: TODO 根据附件类型id 和业务id 查询附件
     */
    @RequestMapping(value = "findAttachment", produces = "application/json;charset=UTF-8")
    @ResponseBody
   /* public String findAttachment(String sysId) {

        Map<String, Object> map = new HashMap<>();
        map.put("businessId", sysId);
        map.put("isDeleted", "0");
        List<ExpertAttachment> list = attachmentService.selectListByMap(map);
        return JSON.toJSONString(list);
    }*/

    public String findAttachment(@RequestParam("sysId") String sysId,@RequestParam("from") String from, @RequestParam("isReferenceLftter") int isReferenceLftter ) {

        List<UploadFile> SOCIAL_SECURITY_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.SOCIAL_SECURITY_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        List<UploadFile> IDENTITY_CARD_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.IDENTITY_CARD_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        List<UploadFile> TECHNOLOGY_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.TECHNOLOGY_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        List<UploadFile> GRADUATE_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.GRADUATE_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        List<UploadFile> QUALIFICATIONS_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.QUALIFICATIONS_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        List<UploadFile> RECOMMENDATION_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.RECOMMENDATION_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        List<UploadFile> PRACTICING_REQUIREMENTS_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.PRACTICING_REQUIREMENTS_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        List<UploadFile> APPLICATION_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.APPLICATION_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());
        List<UploadFile> COMMITMENT_PROOF = uploadService.getFilesOther(sysId, ExpertPictureType.COMMITMENT_PROOF.getSign() + "", Constant.EXPERT_SYS_KEY.toString());



        String imgInfo="cg";
        if(from.equals("LOCAL")){
            if(SOCIAL_SECURITY_PROOF.size()<1 && SOCIAL_SECURITY_PROOF !=null ){
                imgInfo="缴纳社保或退休证明未上传";
                return JSON.toJSONString(imgInfo);
            }
            if(GRADUATE_PROOF.size()<1 && GRADUATE_PROOF !=null ){
                imgInfo="毕业证书未上传";
                return JSON.toJSONString(imgInfo);
            }
            if(QUALIFICATIONS_PROOF.size()<1 && QUALIFICATIONS_PROOF !=null ){
                imgInfo="学位证书未上传";
                return JSON.toJSONString(imgInfo);
            }

        }else if(from.equals("ARMY")){
            if(SOCIAL_SECURITY_PROOF.size()<1 && SOCIAL_SECURITY_PROOF !=null ){
                imgInfo="军队人员的身份证件未上传";
                return JSON.toJSONString(imgInfo);
            }
        }
        if(IDENTITY_CARD_PROOF.size()<1 && IDENTITY_CARD_PROOF!=null){
            imgInfo="身份证复印件未上传";
            return JSON.toJSONString(imgInfo);
        }
        if(TECHNOLOGY_PROOF.size()<1 && TECHNOLOGY_PROOF!=null){
            imgInfo="技术职称未上传";
            return JSON.toJSONString(imgInfo);
        }
        if(isReferenceLftter==1 && RECOMMENDATION_PROOF.size()<1 && RECOMMENDATION_PROOF!=null){
            imgInfo="推荐信未上传";
            return JSON.toJSONString(imgInfo);
        }
        if(isReferenceLftter==3 && PRACTICING_REQUIREMENTS_PROOF.size()<1 && PRACTICING_REQUIREMENTS_PROOF!=null){
            imgInfo="执业资格证书未上传";
            return JSON.toJSONString(imgInfo);
        }
        if( APPLICATION_PROOF.size()<1 && isReferenceLftter==5 && APPLICATION_PROOF!=null){
            imgInfo="专家申请未上传";
            return JSON.toJSONString(imgInfo);
        }
        if( COMMITMENT_PROOF.size()<1 && isReferenceLftter==5    &&  COMMITMENT_PROOF!=null){
            imgInfo="专家承诺未上传";
            return JSON.toJSONString(imgInfo);
        }
    return imgInfo;
    }
    /**
     * 〈简述〉项目评审 只显示项目
     * 〈详细描述〉
     *
     * @param model   模型
     * @param session session-【哦你突然
     * @return String
     *
     *
     *
     *
     *
     *
     *
     * @author Song Biaowei
     */
    @RequestMapping(value = "toProjectList")
    public String projectList(Model model, HttpSession session) {
        try {
            User user = (User) session.getAttribute("loginUser");
            // 判断用户的类型为专家类型
            if (user != null) {
                // 获取专家id
                String typeId = user.getTypeId();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("expertId", typeId);
                map.put("isGather", "0");
                // 查询出关联表中的项目id和包id
                List<PackageExpert> packageExpertList = packageExpertService.selectList(map);
                HashMap<String, Object> hashMap;
                // 该专家的所有包集合
                List<Packages> packageList = new ArrayList<Packages>();
                for (PackageExpert packageExpert : packageExpertList) {
                    // 包id
                    String string = packageExpert.getPackageId();
                    hashMap = new HashMap<String, Object>();
                    hashMap.put("id", string);
                    List<Packages> packages = packageService.findPackageById(hashMap);
                    if (packages != null && packages.size() > 0) {
                        packageList.add(packages.get(0));
                    }
                }
                Set<String> strList = new HashSet<String>();
                for (PackageExpert packageExpert : packageExpertList) {
                    strList.add(packageExpert.getProjectId());
                }
                if (packageList != null && packageList.size() > 0) {
                    List<ProjectExt> projectExtList = new ArrayList<ProjectExt>();
                    ProjectExt projectExt;
                    for (String projectId : strList) {
                        projectExt = new ProjectExt();
                        Project project = projectService.selectById(projectId);
                        PropertyUtils.copyProperties(projectExt, project);
                        projectExtList.add(projectExt);
                    }
                    model.addAttribute("projectExtList", projectExtList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "bss/prms/audit/list_project";
    }

    /**
     * @param @return
     * @return String
     * @Title: projectList
     * @author ShaoYangYang
     * @date 2016年10月22日 上午10:28:43
     * @Description: TODO 去项目评审列表页面
     */
    @RequestMapping("projectList")
    public String toProjectList(Model model, HttpSession session, String projectCode, String projectName, String status, Integer pageNum) {
        try {
            User user = (User) session.getAttribute("loginUser");
            // 判断用户的类型为专家类型
            if (user != null) {
                // 获取专家id
                String typeId = user.getTypeId();
                model.addAttribute("expertId", typeId);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("expertId", typeId);
                // 查询出关联表中的项目id和包id
                List<PackageExpert> packageExpertList = packageExpertService.selectList(map);
                // 该专家的所有包集合
                HashMap<String, Object> hashMap;
                List<Packages> packageList = new ArrayList<Packages>();
                for (PackageExpert packageExpert : packageExpertList) {
                    // 包id
                    String string = packageExpert.getPackageId();
                    hashMap = new HashMap<String, Object>();
                    hashMap.put("id", string);
                    hashMap.put("projectId", projectCode);
                    hashMap.put("projectName", projectName);
                    List<Packages> packages = packageService.selectPackageById(hashMap);
                    if (packages != null && packages.size() > 0) {
                        packageList.add(packages.get(0));
                    }
                }
                List<ProjectExt> projectExtList = new ArrayList<ProjectExt>();
                // 循环包集合 根据包中的项目id 查询出项目集合
                if (packageList != null && packageList.size() > 0) {
                    projectExtList = service.getProjectExtList(packageList, typeId, status, pageNum == null ? 1 : pageNum);
                }
                // 排序
                Collections.sort(projectExtList, new Comparator<ProjectExt>() {
                    public int compare(ProjectExt pro1, ProjectExt pro2) {
                        // 按照SupplierFinance的年份进行升序排列
                        if (pro1.getBidDate().getTime() > pro2.getBidDate().getTime()) {
                            return -1;
                        }
                        if (pro1.getBidDate().getTime() == pro2.getBidDate().getTime()) {
                            return 0;
                        } else {
                            return 1;
                        }
                    }
                });
                PageInfo<ProjectExt> pageInfo = new PageInfo<ProjectExt>(projectExtList);
                model.addAttribute("projectExtList", pageInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 查询条件回显
        model.addAttribute("projectId", projectCode);
        model.addAttribute("projectName", projectName);
        model.addAttribute("status", status);
        return "bss/prms/audit/list";
    }

    /**
     * 〈简述〉异步获取评分类型
     * 〈详细描述〉符合性资格性审查,经济技术评审,以及其他不满足条件的情况
     *
     * @param packageId 包编号
     * @param expertId  专家编号
     * @return 1--符合性资格性审查    2--经济技术评审
     * @author WangHuijie
     */
    @ResponseBody
    @RequestMapping(value = "/getReviewType", produces = "text/html;charset=utf-8")
    public String getReviewType(String packageId, String expertId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("packageId", packageId);
        map.put("expertId", expertId);
        List<PackageExpert> packageExpertList = packageExpertService.selectList(map);
        if (packageExpertList != null && packageExpertList.size() > 0) {
            PackageExpert packageExpert = packageExpertList.get(0);
            if (packageExpert.getIsAudit() == 0 || packageExpert.getIsAudit() == 2) {
                // 符合性资格性审查
                return "1";
            } else if (packageExpert.getIsAudit() == 1 && packageExpert.getIsGather() == 0) {
                return "该包符合性审查未结束";
            } else if (packageExpert.getIsAudit() == 1 && packageExpert.getIsGather() == 1 && (packageExpert.getIsGrade() == 0 || packageExpert.getIsGrade() == 2)) {
                String methodCode = null;
                HashMap<String, Object> map2 = new HashMap<String, Object>();
                map2.put("id", packageId);
                List<Packages> packs = packageService.findPackageById(map2);
                if (packs != null && packs.size() > 0) {
                    //获取评分办法数据字典编码
                    methodCode = bidMethodService.getMethod(packs.get(0).getProjectId(), packageId);
                }
                if ("PBFF_JZJF".equals(methodCode) || "PBFF_ZDJF".equals(methodCode)) {
                    // 经济技术评审
                    return "3";
                } else if ("OPEN_ZHPFF".equals(methodCode)) {
                    // 经济技术模型打分评审
                    return "2";
                } else {
                    return null;
                }

            } else if (packageExpert.getIsGrade() == 1 && packageExpert.getIsGather() == 1 && packageExpert.getIsGatherGather() == 0) {
                return "该包经济技术评审未结束";
            } else if (packageExpert.getIsGather() == 1 && packageExpert.getIsGatherGather() == 1) {
                return "该包已结束评审!";
            } else {
                return "该包数据异常,暂时无法进行评审!";
            }
        } else {
            return "该包数据异常,暂时无法进行评审!";
        }
    }

    /**
     * 〈简述〉
     * 判断是否通过了符合性审查
     * 〈详细描述〉
     * 1代表通过,0没通过
     *
     * @param projectId
     * @param packageId
     * @return
     * @author WangHuijie
     */
    @ResponseBody
    @RequestMapping("/validateIsGrade")
    public String validateIsGrade(String projectId, String packageId) {
        // 0代表为通过符合性审查
        String isok = "0";
        Map<String, Object> mapSearch = new HashMap<String, Object>();
        mapSearch.put("projectId", projectId);
        mapSearch.put("packageId", packageId);
        List<PackageExpert> list = packageExpertService.selectList(mapSearch);
        if (list.isEmpty()) {
            PackageExpert packageExpert = list.get(0);
            if ("1".equals(packageExpert.getIsAudit()) && !"1".equals(packageExpert.getIsGrade())) {
                // 如果通过则改为1
                isok = "1";
            }
        }
        return isok;
    }

    /**
     * @param @return
     * @return String
     * @Title: toFirstAudit
     * @author ShaoYangYang
     * @date 2016年10月22日 下午3:50:09
     * @Description: TODO 去往项目初审 供应商详情页
     */
    @RequestMapping("toFirstAudit")
    public String toFirstAudit(String projectId, String packageId, Model model,
                               HttpSession session) {
        // 是否已评审
        User user = (User) session.getAttribute("loginUser");
        String expertId = user.getTypeId();
        Map<String, Object> map = new HashMap<>();
        map.put("expertId", expertId);
        map.put("packageId", packageId);
        map.put("projectId", projectId);
        List<PackageExpert> packageExpertList = packageExpertService
                .selectList(map);
        if (packageExpertList != null && packageExpertList.size() > 0) {
            model.addAttribute("packageExpert", packageExpertList.get(0));
        }
        // 供应商信息
        List<SaleTender> supplierList = saleTenderService.list(new SaleTender(
                projectId), 0);
        model.addAttribute("supplierList", supplierList);
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);

        return "bss/prms/audit/suppplier_list";
    }

    /**
     * @param @return
     * @return String
     * @Title: saveProgress
     * @author ShaoYangYang
     * @date 2016年10月27日 下午2:17:47
     * @Description: TODO 保存评分信息 更新评分进度
     */
    @RequestMapping("saveGrade")
    public String saveGrade(String projectId, String packageId,
                            HttpSession session, RedirectAttributes attr) {
        User user = (User) session.getAttribute("loginUser");
        String expertId = user.getTypeId();
        reviewProgressService.saveGrade(projectId, packageId, expertId);
        attr.addAttribute("projectId", projectId);
        attr.addAttribute("packageId", packageId);
        return "redirect:projectList.html";
    }

    /**
     * @param packageId
     * @param supplierId
     * @param @return
     * @return String
     * @Title: supplierQuote
     * @author ShaoYangYang
     * @date 2016年11月11日 下午2:46:47
     * @Description: TODO 查看供应商报价
     */
    @RequestMapping("supplierQuote")
    public String supplierQuote(String packageId, String supplierId, Model model) {
        Quote quote = new Quote();
        quote.setPackageId(packageId);
        quote.setSupplierId(supplierId);
        List<Quote> historyList = supplierQuoteService
                .selectQuoteHistoryList(quote);
        if (historyList != null && historyList.size() > 0) {
            long create = historyList.get(0).getCreatedAt().getTime();
            for (Quote quote2 : historyList) {
                if (quote2.getCreatedAt().getTime() > create) {
                    create = quote2.getCreatedAt().getTime();
                }
            }
            Date date = new Date(create);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String formatDate = sdf.format(date);
            Timestamp timestamp = Timestamp.valueOf(formatDate);
            quote.setCreatedAt(timestamp);
            List<Quote> historyList2 = supplierQuoteService
                    .selectQuoteHistoryList(quote);
            model.addAttribute("historyList", historyList2);
        }
        return "bss/prms/audit/list";
    }

    /**
     * @param @return
     * @return String
     * @Title: saveProgress
     * @author ShaoYangYang
     * @date 2016年10月27日 下午2:17:47
     * @Description: TODO 保存审核信息
     */
    @RequestMapping("saveProgress")
    public String saveProgress(String projectId, String packageId,
                               HttpSession session, RedirectAttributes attr) {
        User user = (User) session.getAttribute("loginUser");
        String expertId = user.getTypeId();
        // 更新进度 保存审核信息
        reviewProgressService.saveProgress(projectId, packageId, expertId);
        attr.addAttribute("projectId", projectId);
        attr.addAttribute("packageId", packageId);
        return "redirect:projectList.html";
    }

    /**
     * @param @param  expert
     * @param @param  request
     * @param @throws Exception
     * @return ResponseEntity<byte[]>
     * @Title: download
     * @author ShaoYangYang
     * @date 2016年9月7日 下午6:53:12
     * @Description: TODO 下载申请表
     */
    @RequestMapping("download")
    public ResponseEntity<byte[]> download(String id,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 根据编号查询专家信息
        Expert expert = service.selectByPrimaryKey(id);
        // 文件存储地址
        String filePath = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String fileName = createWordMethod(expert, request);
        // 下载后的文件名
        String downFileName = new String("军队评标专家申请表.doc".getBytes("UTF-8"),
                "iso-8859-1"); // 为了解决中文名称乱码问题
        return service.downloadFile(fileName, filePath, downFileName);
    }

    /**
     * 〈简述〉提交专家经济技术评审结果
     * 〈详细描述〉
     *
     * @param projectId
     * @param packageId
     * @param session
     * @param attr
     * @return
     * @author Ye Maolin
     */
    @RequestMapping("/saveCheck")
    public String saveCheck(String projectId, String packageId, HttpSession session, RedirectAttributes attr) {
        User user = (User) session.getAttribute("loginUser");
        String expertId = user.getTypeId();
        // 更新进度 保存经济技术评审信息
        reviewProgressService.saveCheck(projectId, packageId, expertId);
        attr.addAttribute("projectId", projectId);
        attr.addAttribute("packageId", packageId);
        return "redirect:projectList.html";
    }

    /**
     * 〈简述〉
     * 下载专家承诺书
     * 〈详细描述〉
     *
     * @param id
     * @param request
     * @return
     * @throws Exception
     * @author WangHuijie
     */
    @RequestMapping("downloadBook")
    public ResponseEntity<byte[]> downloadBook(String id,
                                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 文件存储地址
        String filePath = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String name = new String(("军队评标专家承诺书.doc").getBytes("UTF-8"),
                "UTF-8");
        /** 生成word 返回文件名 */
        String fileName = WordUtil.createWord(null, "expertBook.ftl",
                name, request);
        // 下载后的文件名
        String downFileName = new String("军队评标专家承诺书.doc".getBytes("UTF-8"),
                "iso-8859-1"); // 为了解决中文名称乱码问题
        return service.downloadFile(fileName, filePath, downFileName);
    }

    /**
     * 〈简述〉
     * 下载供应商申请表
     * 〈详细描述〉
     *
     * @param supplierId 供应商编号
     * @param request
     * @return
     * @throws Exception
     * @author WangHuijie
     */
    @RequestMapping("downloadSupplier")
    public ResponseEntity<byte[]> downloadSupplier(String supplierJson, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 文件存储地址
        String filePath = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String name = new String(("供应商入库申请表.doc").getBytes("UTF-8"),
                "UTF-8");
        Supplier supplier = JSON.parseObject(supplierJson, Supplier.class);
        /** 创建word文件 **/
        String fileName = WordUtil.createWord(supplier, "supplier.ftl",
                name, request);
        // 下载后的文件名
        String downFileName = new String("供应商入库申请表.doc".getBytes("UTF-8"),
                "iso-8859-1"); // 为了解决中文名称乱码问题
        return service.downloadFile(fileName, filePath, downFileName);
    }

    @ResponseBody
    @RequestMapping(value = "/getSupplierInfo", produces = "application/json;charset=utf-8")
    public String getSupplierInfo(String supplierId, HttpServletResponse response) throws IOException {
        Supplier supplier = supplierService.get(supplierId);
        /** 数据处理 **/
        handingData(supplier);
        return JSON.toJSONString(supplier);
    }

    /**
     * 〈简述〉为供应商申请表下载处理数据
     * 〈详细描述〉
     *
     * @param supplier 供应商
     * @author WangHuijie
     */
    public void handingData(Supplier supplier) {

        // 地址
        Area area = areaServiceI.listById(supplier.getAddress());
        if (area != null) {
            String province = areaServiceI.listById(area.getParentId()).getName();
            String city = area.getName();
            supplier.setAddress(province + city + supplier.getDetailAddress());
        }

        // 类型
        StringBuffer supplierTypeId = new StringBuffer();
        String[] typeIds = supplier.getSupplierTypeIds().split(",");
        for (String typeId : typeIds) {
            String typeName = DictionaryDataUtil.get(typeId).getName();
            if (typeName != null) {
                supplierTypeId.append(typeName + "、");
            }
        }
        if (!"".equals(supplierTypeId) && supplierTypeId.length() > 0) {
            supplier.setSupplierType(supplierTypeId.toString().substring(0, supplierTypeId.toString().length() - 1));
        }

        // 营业执照登记类型
        DictionaryData businessType = DictionaryDataUtil.findById(supplier.getBusinessType());
        if (businessType != null) {
            supplier.setBusinessType(businessType.getName());
        }

        // 生产经营地址
        List<SupplierAddress> addressList = supplier.getAddressList();
        for (SupplierAddress address : addressList) {
            Area addr = areaServiceI.listById(address.getAddress());
            if (addr != null) {
                String province = areaServiceI.listById(addr.getParentId()).getName();
                String city = addr.getName();
                address.setAddress(province + city + address.getDetailAddress());
            }
        }

        // 境外分支地址
        List<SupplierBranch> branchList = supplier.getBranchList();
        for (SupplierBranch branch : branchList) {
            // 国家(地区)
            if (branch.getCountry() != null) {
                branch.setCountry(DictionaryDataUtil.findById(branch.getCountry()).getName());
            }
        }

        // 物资类,服务类资质证书
        List<SupplierCertPro> listSupplierCertPros = new ArrayList<SupplierCertPro>();
        if (supplier.getSupplierMatPro() != null && supplier.getSupplierMatPro().getListSupplierCertPros() != null) {
            listSupplierCertPros = supplier.getSupplierMatPro().getListSupplierCertPros();
            List<SupplierCertServe> listSupplierCertSes = new ArrayList<SupplierCertServe>();
            if (supplier.getSupplierMatSe() != null && supplier.getSupplierMatSe().getListSupplierCertSes() != null) {
                listSupplierCertSes = supplier.getSupplierMatSe().getListSupplierCertSes();
                for (SupplierCertServe server : listSupplierCertSes) {
                    SupplierCertPro pro = new SupplierCertPro();
                    pro.setName(server.getName());
                    pro.setLevelCert(server.getLevelCert());
                    pro.setLicenceAuthorith(server.getLicenceAuthorith());
                    pro.setExpStartDate(server.getExpStartDate());
                    pro.setExpEndDate(server.getExpEndDate());
                    pro.setMot(server.getMot());
                    listSupplierCertPros.add(pro);
                }
                supplier.getSupplierMatPro().setListSupplierCertPros(listSupplierCertPros);
            }
        }

        List<SupplierRegPerson> listSupplierRegPersons = new ArrayList<SupplierRegPerson>();
        if (supplier.getSupplierMatEng() != null && supplier.getSupplierMatEng().getListSupplierRegPersons() != null) {
            listSupplierRegPersons = supplier.getSupplierMatEng().getListSupplierRegPersons();
            List<SupplierRegPerson> persons = new ArrayList<SupplierRegPerson>();
            List<List<SupplierRegPerson>> personList = new ArrayList<List<SupplierRegPerson>>();
            // 注册人员信息,换行后的内容
            if (listSupplierRegPersons != null && listSupplierRegPersons.size() > 2) {
                for (int i = 0; i < listSupplierRegPersons.size(); i++) {
                    if (i > 1) {
                        if (i % 2 == 0 && i + 1 != listSupplierRegPersons.size()) {
                            persons.add(listSupplierRegPersons.get(i));
                            persons.add(listSupplierRegPersons.get(i + 1));
                            personList.add(persons);
                        } else if (i + 1 == listSupplierRegPersons.size()) {
                            persons.add(listSupplierRegPersons.get(i));
                            personList.add(persons);
                        }
                    }
                }
            }
            supplier.getSupplierMatEng().setPersons(personList);

            // 工程类注册人员信息
            Integer personSize = listSupplierRegPersons != null && listSupplierRegPersons.size() % 2 == 0 ? listSupplierRegPersons.size() / 2 : listSupplierRegPersons.size() / 2 + 1;
            supplier.setPersonSize(personSize);
        }

        // 品目信息
        List<SupplierCateTree> allTreeList = new ArrayList<SupplierCateTree>();
        List<SupplierItem> itemsList = supplierItemService.findCategoryList(supplier.getId(), null, null);
        for (SupplierItem supplierItem : itemsList) {
            SupplierCateTree cateTree = getTreeListByCategoryId(supplierItem);
            if (cateTree != null && cateTree.getRootNode() != null) {
                allTreeList.add(cateTree);
            }
        }
        supplier.setAllTreeList(allTreeList);
    }

    /**
     * 〈简述〉去除不是根节点的产品
     * 〈详细描述〉
     *
     * @param listSupplierItems
     * @author WangHuijie
     */
    public List<SupplierItem> removeNotChild(List<SupplierItem> listSupplierItems) {
        List<SupplierItem> newSupplierItems = new ArrayList<SupplierItem>();
        for (SupplierItem cate : listSupplierItems) {
            String cateId = cate.getCategoryId();
            List<Category> childList = categoryService.findPublishTree(cateId, null);
            if (childList != null && childList.size() > 0) {
                newSupplierItems.add(cate);
            }
        }
        listSupplierItems.removeAll(newSupplierItems);
        return listSupplierItems;
    }

    /**
     * 〈简述〉获取当前节点的所有父级节点(包括根节点)
     * 〈详细描述〉
     *
     * @param categoryId
     * @return
     * @author WangHuijie
     */
    public List<Category> getAllParentNode(String categoryId, String flag) {
        List<Category> categoryList = new ArrayList<Category>();
        while (true) {
            Category cate = null;
            if (flag == null) {
                cate = categoryService.findById(categoryId);
            } else {
                cate = engCategoryService.findById(categoryId);
            }
            if (cate == null) {
                DictionaryData root = DictionaryDataUtil.findById(categoryId);
                Category rootNode = new Category();
                rootNode.setId(root.getId());
                rootNode.setName(root.getName());
                categoryList.add(rootNode);
                break;
            } else {
                categoryList.add(cate);
                categoryId = cate.getParentId();
            }
        }
        return categoryList;
    }

    /**
     * 〈简述〉查询品目信息
     * 〈详细描述〉
     *
     * @param categoryId 产品Id
     * @return List<CategoryTree> tree对象List
     * @author WangHuijie
     */
    public SupplierCateTree getTreeListByCategoryId(SupplierItem supplierItem) {
        String categoryId = supplierItem.getCategoryId();
        SupplierCateTree cateTree = new SupplierCateTree();
        // 递归获取所有父节点
        List<Category> parentNodeList = getAllParentNode(categoryId, null);
        // 加入根节点
        for (int i = 0; i < parentNodeList.size(); i++) {
            DictionaryData rootNode = DictionaryDataUtil.findById(parentNodeList.get(i).getId());
            if (rootNode != null) {
                cateTree.setRootNode(rootNode.getName());
            }
        }
        // 加入一级节点
        if (cateTree.getRootNode() != null) {
            for (int i = 0; i < parentNodeList.size(); i++) {
                Category cate = categoryService.findById(parentNodeList.get(i).getId());
                if (cate != null && cate.getParentId() != null) {
                    DictionaryData rootNode = DictionaryDataUtil.findById(cate.getParentId());
                    if (rootNode != null && cateTree.getRootNode().equals(rootNode.getName())) {
                        cateTree.setFirstNode(cate.getName());
                    }
                }
            }
        }
        // 加入二级节点
        if (cateTree.getRootNode() != null && cateTree.getFirstNode() != null) {
            for (int i = 0; i < parentNodeList.size(); i++) {
                Category cate = categoryService.findById(parentNodeList.get(i).getId());
                if (cate != null && cate.getParentId() != null) {
                    Category parentNode = categoryService.findById(cate.getParentId());
                    if (parentNode != null && cateTree.getFirstNode().equals(parentNode.getName())) {
                        cateTree.setSecondNode(cate.getName());
                    }
                }
            }
        }
        // 加入三级节点
        if (cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null) {
            for (int i = 0; i < parentNodeList.size(); i++) {
                Category cate = categoryService.findById(parentNodeList.get(i).getId());
                if (cate != null && cate.getParentId() != null) {
                    Category parentNode = categoryService.findById(cate.getParentId());
                    if (parentNode != null && cateTree.getSecondNode().equals(parentNode.getName())) {
                        cateTree.setThirdNode(cate.getName());
                    }
                }
            }
        }
        // 加入末级节点
        if (cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null && cateTree.getThirdNode() != null) {
            for (int i = 0; i < parentNodeList.size(); i++) {
                Category cate = categoryService.findById(parentNodeList.get(i).getId());
                if (cate != null && cate.getParentId() != null) {
                    Category parentNode = categoryService.findById(cate.getParentId());
                    if (parentNode != null && cateTree.getThirdNode().equals(parentNode.getName())) {
                        cateTree.setFourthNode(cate.getName());
                    }
                }
            }
        }
        // 判断是否是物资生产和物资销售类
        if ("PRODUCT".equals(supplierItem.getSupplierTypeRelateId())) {
            cateTree.setRootNode(cateTree.getRootNode() + "生产");
        } else if ("SALES".equals(supplierItem.getSupplierTypeRelateId())) {
            cateTree.setRootNode(cateTree.getRootNode() + "销售");
        }
        return cateTree;
    }

    /**
     * 〈简述〉
     * 下载专家承诺书
     * 〈详细描述〉
     *
     * @param id
     * @param request
     * @return
     * @throws Exception
     * @author WangHuijie
     */
    @RequestMapping("downloadSupplierNotice")
    public ResponseEntity<byte[]> downloadSupplierNotice(String id,
                                                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 文件存储地址
        String filePath = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String name = new String(("供应商承诺书.doc").getBytes("UTF-8"),
                "UTF-8");
        /** 生成word 返回文件名 */
        String fileName = WordUtil.createWord(null, "supplierNotice.ftl",
                name, request);
        // 下载后的文件名
        String downFileName = new String("供应商承诺书.doc".getBytes("UTF-8"),
                "iso-8859-1"); // 为了解决中文名称乱码问题
        return service.downloadFile(fileName, filePath, downFileName);
    }

    /**
     * 〈简述〉下载产品目录
     * 〈详细描述〉
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author WangHuijie
     */
    @RequestMapping("/downCategory")
    public ResponseEntity<byte[]> downloadSupplierNotice(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 文件存储地址
        String filePath = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String name = new String(("供应商承诺书.doc").getBytes("UTF-8"),
                "UTF-8");
        /** 生成word 返回文件名 */
        String fileName = WordUtil.createWord(null, "supplierNotice.ftl",
                name, request);
        // 下载后的文件名
        String downFileName = new String("供应商承诺书.doc".getBytes("UTF-8"),
                "iso-8859-1"); // 为了解决中文名称乱码问题

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("品目类别表");
        HSSFRow row = sheet.createRow((int) 0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("采购产品目录编码");
        cell = row.createCell(1);
        cell.setCellValue("类别名称");

        cell = row.createCell(2);
        cell.setCellValue("专业资质要求");
        cell = row.createCell(3);
        cell.setCellValue("说明");
        cell = row.createCell(4);
        DictionaryData goods = DictionaryDataUtil.get("GOODS");

        return service.downloadFile(fileName, filePath, downFileName);
    }

    /**
     * 〈简述〉
     * 下载专家注册须知
     * 〈详细描述〉
     *
     * @param id
     * @param request
     * @return
     * @throws Exception
     * @author WangHuijie
     */
    @RequestMapping("/downNotice")
    public ResponseEntity<byte[]> downNotice(String id,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 文件存储地址
        String filePath = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String name = new String(("评审专家申请人注册须知.doc").getBytes("UTF-8"),
                "UTF-8");
        /** 生成word 返回文件名 */
        String fileName = WordUtil.createWord(null, "expertNotice.ftl",
                name, request);
        // 下载后的文件名
        String downFileName = new String("评审专家申请人注册须知.doc".getBytes("UTF-8"),
                "iso-8859-1"); // 为了解决中文名称乱码问题
        return service.downloadFile(fileName, filePath, downFileName);
    }

    /**
     * 〈简述〉
     * 下载供应商注册须知
     * 〈详细描述〉
     *
     * @param id
     * @param request
     * @return
     * @throws Exception
     * @author WangHuijie
     */
    @RequestMapping("/downSupplierNotice")
    public ResponseEntity<byte[]> downSupplierNotice(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 文件存储地址
        String filePath = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/upload_file/");
        // 文件名称
        String name = new String(("供应商注册须知.doc").getBytes("UTF-8"),
                "UTF-8");
        /** 生成word 返回文件名 */
        String fileName = WordUtil.createWord(null, "supplierNotices.ftl",
                name, request);
        // 下载后的文件名
        String downFileName = new String("供应商注册须知.doc".getBytes("UTF-8"),
                "iso-8859-1"); // 为了解决中文名称乱码问题
        return service.downloadFile(fileName, filePath, downFileName);
    }

    /**
     * @throws Exception
     * @Title: createWordMethod
     * @author: lkzx
     * @date: 2016-9-7 下午3:25:38
     * @Description: TODO 生成word文件提供下载
     * @param: @param expert
     * @return: String
     */
    private String createWordMethod(Expert expert, HttpServletRequest request) throws Exception {
        /** 用于组装word页面需要的数据 */
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("relName", expert.getRelName() == null ? "" : expert.getRelName());
        String sex = expert.getGender();
        DictionaryData gender = dictionaryDataServiceI.getDictionaryData(sex);
        dataMap.put("gender", gender == null ? "" : gender.getName());
        dataMap.put("birthday",
                expert.getBirthday() == null ? "" : new SimpleDateFormat(
                        "yyyy-MM-dd").format(expert.getBirthday()));
        String faceId = expert.getPoliticsStatus();
        DictionaryData politicsStatus = dictionaryDataServiceI.getDictionaryData(faceId);
        dataMap.put("politicsStatus", politicsStatus == null ? "" : politicsStatus.getName());
        dataMap.put("nation", expert.getNation() == null ? "" : expert.getNation());
        dataMap.put("healthState", expert.getHealthState() == null ? "" : expert.getHealthState());
        dataMap.put("workUnit", expert.getWorkUnit() == null ? "" : expert.getWorkUnit());
        dataMap.put("coverNote", expert.getCoverNote() == null ? "(不必填)" : expert.getCoverNote().equals("1") ? "是" : "否");
        //address

        dataMap.put("unitAddress", expert.getUnitAddress() == null ? "" : expert.getRange() + "," + expert.getUnitAddress());
        dataMap.put("postCode", expert.getPostCode() == null ? "" : expert.getPostCode());
        dataMap.put("atDuty", expert.getAtDuty() == null ? "" : expert.getAtDuty());
        dataMap.put("idCardNumber", expert.getIdCardNumber() == null ? "" : expert.getIdCardNumber());
        DictionaryData idType = dictionaryDataServiceI.getDictionaryData(expert.getIdType());
        if (idType != null) {
            dataMap.put("idType", idType.getName() == null ? "" : idType.getName());
        } else {
            dataMap.put("idType", "");
        }
        dataMap.put("idNumber", expert.getIdNumber() == null ? "" : expert.getIdNumber());
        dataMap.put("major", expert.getMajor() == null ? "" : expert.getMajor());
        dataMap.put("timeStartWork", expert.getTimeStartWork() == null ? "" : new SimpleDateFormat("yyyy-MM").format(expert.getTimeStartWork()));
        DictionaryData expertsForm = dictionaryDataServiceI.getDictionaryData(expert.getExpertsFrom());
        if (expertsForm != null) {
            dataMap.put("expertsFrom", expertsForm.getName() == null ? "" : expertsForm.getName());
        } else {
            dataMap.put("expertsFrom", "");
        }
        dataMap.put(
                "professTechTitles",
                expert.getProfessTechTitles() == null ? "" : expert
                        .getProfessTechTitles());
        dataMap.put("makeTechDate", expert.getMakeTechDate() == null ? "" : new SimpleDateFormat("yyyy-MM").format(expert.getMakeTechDate()));
        StringBuffer expertType = new StringBuffer();
        for (String typeId : expert.getExpertsTypeId().split(",")) {
            expertType.append(dictionaryDataServiceI.getDictionaryData(typeId).getName() + "、");
        }
        String expertsType = expertType.toString().substring(0, expertType.length() - 1);
        dataMap.put("expertsTypeId", expertsType);
        dataMap.put("graduateSchool", expert.getGraduateSchool() == null ? "" : expert.getGraduateSchool());
        String hightEducationId = expert.getHightEducation();
        DictionaryData hightEducation = dictionaryDataServiceI.getDictionaryData(hightEducationId);
        dataMap.put("hightEducation", hightEducation == null ? "" : hightEducation.getName());
        DictionaryData degree = dictionaryDataServiceI.getDictionaryData(expert.getDegree());
        if (degree != null) {
            dataMap.put("degree", degree.getName() == null ? "" : degree.getName());
        } else {
            dataMap.put("degree", "");
        }
        dataMap.put("mobile", expert.getMobile() == null ? "" : expert.getMobile());
        dataMap.put("telephone", expert.getTelephone() == null ? "" : expert.getTelephone());
        dataMap.put("fax", expert.getFax() == null ? "" : expert.getFax());
        dataMap.put("email", expert.getEmail() == null ? "" : expert.getEmail());
        StringBuffer categories = new StringBuffer();
        List<ExpertCategory> allList = expertCategoryService.getListByExpertId(expert.getId(), null);
        for (ExpertCategory expertCategory : allList) {
            categories.append(categoryService.selectByPrimaryKey(expertCategory.getCategoryId()).getName());
            categories.append("、");
        }
        String productCategories = categories.substring(0, categories.length() - 1);
        dataMap.put("productCategories", productCategories);
        dataMap.put("jobExperiences", expert.getJobExperiences() == null ? "" : expert.getJobExperiences());
        dataMap.put("academicAchievement", expert.getAcademicAchievement() == null ? "" : expert.getAcademicAchievement());
        dataMap.put("reviewSituation", expert.getReviewSituation() == null ? "" : expert.getReviewSituation());
        dataMap.put("avoidanceSituation", expert.getAvoidanceSituation() == null ? "" : expert.getAvoidanceSituation());
        // 文件名称
        String fileName = new String(("军队评标专家申请表.doc").getBytes("UTF-8"),
                "UTF-8");
        /** 生成word 返回文件名 */
        String newFileName = WordUtil.createWord(dataMap, "expert.ftl",
                fileName, request);
        return newFileName;
    }

    /**
     * 〈简述〉
     * 根据机构编号查询所在的省市
     * 〈详细描述〉
     *
     * @param purDepId
     * @return
     * @author Wanghuijie
     */
    @ResponseBody
    @RequestMapping(value = "getPIdandCIdByPurDepId")
    public String getPIdandCIdByPurDepId(String purDepId) {
        if (purDepId != null && !"".equals(purDepId)) {
            Map<String, String> purchaseDep = purchaseOrgnizationService.findPIDandCIDByOrgId(purDepId);
            return JSON.toJSONString(purchaseDep);
        }
        return null;
    }

    /**
     * 〈简述〉
     * 专家注册页面的手机号唯一性验证
     * 〈详细描述〉
     *
     * @param phone
     * @return
     * @author WangHuijie
     */
    @ResponseBody
    @RequestMapping("/validatePhone")
    public String findAllPhone(String phone) {
        Boolean ajaxMoblie = userService.ajaxMoblie(phone, null);
        if (ajaxMoblie) {
            return "0";
        } else {
            return "1";
        }
    }

    @ResponseBody
    @RequestMapping("/validateAge")
    public String validateAge(String birthday) {
        String isok = "0";
        String year = birthday.substring(0, 4);
        String now = new SimpleDateFormat("yyyy").format(new Date());
        if (Integer.parseInt(now) - Integer.parseInt(year) >= 70) {
            isok = "1";
        }
        return isok;
    }

    /**
     * 〈简述〉
     * 专家注册页面的身份证号唯一性验证
     * 〈详细描述〉
     *
     * @param idCardNumber
     * @return
     * @author WangHuijie
     */
    @ResponseBody
    @RequestMapping("/validateIdCardNumber")
    public String validateIdCardNumber(String idCardNumber, String expertId) {
        Boolean ajaxIdNumber = userService.ajaxIdNumber(idCardNumber, userService.findByTypeId(expertId).getId());
        if (ajaxIdNumber) {
            return "0";
        } else {
            return "1";
        }
    }

    /**
     * 〈简述〉
     * 专家注册页面的证件号码唯一性验证
     * 〈详细描述〉
     *
     * @param idNumber
     * @return
     * @author WangHuijie
     */
    @ResponseBody
    @RequestMapping("/validateIdNumber")
    public String validateIdNumber(String idNumber, String expertId) {
        List<Expert> list = service.validateIdNumber(idNumber, expertId);
        if (list.isEmpty()) {
            return "0";
        } else {
            if (list.size() == 1 && expertId.equals(list.get(0).getId())) {
                return "0";
            } else {
                return "1";
            }
        }
    }

    /**
     * 〈简述〉
     * 注册时点击下一步,将表中的STRP_NUMBER进行同步
     * 〈详细描述〉
     *
     * @param expertId
     * @param stepNumber
     * @author WangHuijie
     */
    @ResponseBody
    @RequestMapping("/updateStepNumber")
    public void updateStepNumber(String expertId, String stepNumber) {
        service.updateStepNumber(expertId, stepNumber);
    }

    /**
     * 〈简述〉
     * 为专家注册第四部准备数据
     * 〈详细描述〉
     * 将表中的数据查出,在数据字典中查询
     *
     * @param expertId
     * @return
     * @author WangHuijie
     */
    @ResponseBody
    @RequestMapping(value = "/initData", produces = "application/json;charset=UTF-8")
    public String initData(String expertId) {
        Expert expert = service.selectByPrimaryKey(expertId);
        DictionaryData gender = dictionaryDataServiceI.getDictionaryData(expert.getGender());
        if (gender != null) {
            expert.setGender(gender.getName());
        }
        // 政治面貌
        DictionaryData politics = dictionaryDataServiceI.getDictionaryData(expert.getPoliticsStatus());
        if (politics != null) {
            expert.setPoliticsStatus(politics.getName());
        }
        String address = expert.getAddress();
        Area area = areaServiceI.listById(address);
        // 市
        String cityName = area.getName();
        // 省
        String provinceName = areaServiceI.listById(area.getParentId()).getName();
        expert.setAddress(provinceName.concat(cityName));
        // 最高学历
        expert.setHightEducation(dictionaryDataServiceI.getDictionaryData(expert.getHightEducation()).getName());
        // 最高学位
        expert.setDegree(dictionaryDataServiceI.getDictionaryData(expert.getDegree()).getName());
        // 军队人员身份证件类型
        String idType = expert.getIdType();
        if (idType != null) {
            expert.setIdType(dictionaryDataServiceI.getDictionaryData(idType).getName());
        }
        // 专家来源
        expert.setExpertsFrom(dictionaryDataServiceI.getDictionaryData(expert.getExpertsFrom()).getName());
        // 专家类别
        StringBuffer expertType = new StringBuffer();
        for (String typeId : expert.getExpertsTypeId().split(",")) {
            DictionaryData type = dictionaryDataServiceI.getDictionaryData(typeId);
            if (type.getKind().intValue() == 6) {
                type.setName(type.getName() + "技术");
            }
            expertType.append(type.getName() + "、");
        }
        String expertsType = expertType.toString().substring(0, expertType.length() - 1);
        expert.setExpertsTypeId(expertsType);
        return JSON.toJSONString(expert);
    }

    @ResponseBody
    @RequestMapping("/isHaveCategory")
    public String isHaveCategory(String expertId) {
        List<ExpertCategory> list = expertCategoryService.getListByExpertId(expertId, null);
        return list != null && list.size() > 0 ? "1" : "0";
    }

    public String getParentId(String cateId, String flag) {
        if (flag == null) {
            Category cate = categoryService.selectByPrimaryKey(cateId);
            if (cate != null) {
                cateId = getParentId(cate.getParentId(), null);
            }
            return cateId;
        } else {
            Category cate = engCategoryService.selectByPrimaryKey(cateId);
            if (cate != null) {
                cateId = getParentId(cate.getParentId(), "ENG_INFO");
            }
            return cateId;
        }
    }

    /**
     * 〈简述〉品目去重
     * 〈详细描述〉
     *
     * @param allCategories
     * @return
     * @author WangHuijie
     */
    public void removeSame(List<Category> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getId().equals(list.get(i).getId())) {
                    list.remove(j);
                }
            }
        }
    }

    /**
     * 〈简述〉去重父级节点,只保留子节点
     * 〈详细描述〉
     *
     * @param list Category类型的List
     * @return
     * @author WangHuijie
     */
    public void removeParentNodes(List<Category> list, String flag) {
        Category cate = null;
        List<Category> childrenList = new ArrayList<Category>();
        for (int i = 0; i < list.size(); i++) {
            cate = list.get(i);
            if (flag == null) {
                childrenList = categoryService.findPublishTree(cate.getId(), null);
            } else {
                childrenList = engCategoryService.findPublishTree(cate.getId(), null);
            }
            if (childrenList.size() > 0) {
                list.remove(i);
            }
        }
    }

    /**
     * 〈简述〉获取当前节点的所有父级节点
     * 〈详细描述〉
     *
     * @param nodeId 节点Id
     * @return 返回CategoryList
     * @author WangHuijie
     */
    public List<Category> getParentNodeList(String nodeId, String flag) {
        if (flag == null) {
            List<Category> parentNodeList = new ArrayList<Category>();
            Category category = categoryService.findById(nodeId);
            if (category != null) {
                String parentId = category.getParentId();
                if (parentId != null && !"".equals(parentId)) {
                    Category cate = categoryService.findById(parentId);
                    if (cate != null) {
                        parentNodeList.add(cate);
                        List<Category> parentList = getParentNodeList(cate.getId(), null);
                        parentNodeList.addAll(parentList);
                    }
                }
            }
            return parentNodeList;
        } else {
            List<Category> parentNodeList = new ArrayList<Category>();
            Category category = engCategoryService.findById(nodeId);
            if (category != null) {
                String parentId = category.getParentId();
                if (parentId != null && !"".equals(parentId)) {
                    Category cate = engCategoryService.findById(parentId);
                    if (cate != null) {
                        parentNodeList.add(cate);
                        List<Category> parentList = getParentNodeList(cate.getId(), "ENG_INFO");
                        parentNodeList.addAll(parentList);
                    }
                }
            }
            return parentNodeList;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/searchCate", produces = "application/json;charset=utf-8")
    public String searchCate(String typeId, String cateName, String expertId, String supplierId, String codeName) {
        DictionaryData typeData = DictionaryDataUtil.findById(typeId);
        if (typeData != null && typeData.getCode().equals("ENG_INFO_ID")) {
            // 查询出所有满足条件的品目
            List<Category> categoryList = service.searchByName(cateName, "ENG_INFO", codeName);
            // 循环判断是不是当前树的节点
            List<Category> cateList = new ArrayList<Category>();
            for (Category category : categoryList) {
                String parentId = getParentId(category.getId(), "ENG_INFO");
                if (parentId.equals(typeId)) {
                    cateList.add(category);
                }
            }
            // 去重
            removeSame(cateList);
            // 获取被选中的节点的父节点
            List<Category> allCateList = new ArrayList<Category>();
            allCateList.addAll(cateList);
            for (Category category : cateList) {
                List<Category> list = getParentNodeList(category.getId(), "ENG_INFO");
                allCateList.addAll(list);
            }
            // 去重
            removeSame(allCateList);
            // 最后加入根节点
            DictionaryData data = DictionaryDataUtil.findById(typeId);
            Category root = new Category();
            root.setId(data.getId());
            root.setName(data.getName());
            allCateList.add(root);
            // 将筛选完的List转换为CategoryTreeList
            List<CategoryTree> treeList = new ArrayList<CategoryTree>();
            for (Category category : allCateList) {
                CategoryTree treeNode = new CategoryTree();
                treeNode.setId(category.getId());
                treeNode.setName(category.getName());
                treeNode.setParentId(category.getParentId());
                // 判断是否为父级节点
                List<Category> nodesList = engCategoryService.findPublishTree(category.getId(), null);
                if (nodesList != null && nodesList.size() > 0) {
                    treeNode.setIsParent("true");
                }
                treeList.add(treeNode);
            }
            for (CategoryTree treeNode : treeList) {
                // 判断是否被选中
                if (expertId != null) {
                    treeNode.setChecked(isExpertChecked(treeNode.getId(), expertId, typeId, "ENG_INFO"));
                }
            }
            return JSON.toJSONString(treeList);
        } else {
            String type = typeId;
            if (supplierId != null) {
                if (typeId.equals("SALES") || typeId.equals("PRODUCT")) {
                    typeId = DictionaryDataUtil.getId("GOODS");
                } else {
                    typeId = DictionaryDataUtil.getId(typeId);
                }
            }
            // 查询出所有满足条件的品目
            List<Category> categoryList = service.searchByName(cateName, null, codeName);
            Integer level = SupplierLevelUtil.getLevel(supplierId, type);
            if (level != null) {
                for (int i = 0; i < categoryList.size(); i++) {
                    Category cate = categoryList.get(i);
                    if (cate != null) {
                        if (cate.getLevel() != null && Integer.parseInt(cate.getLevel()) < level) {
                            categoryList.remove(i);
                        } else {
                            if (cate.getParentId() != null) {
                                Category parentCate = categoryService.findById(cate.getParentId());
                                if (parentCate != null) {
                                    if (parentCate.getLevel() != null && Integer.parseInt(parentCate.getLevel()) < level) {
                                        categoryList.remove(i);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // 循环判断是不是当前树的节点
            List<Category> cateList = new ArrayList<Category>();
            for (Category category : categoryList) {
                String parentId = getParentId(category.getId(), null);
                if (parentId.equals(typeId)) {
                    cateList.add(category);
                }
            }
            // 去重
            removeSame(cateList);
            // 获取被选中的节点的父节点
            List<Category> allCateList = new ArrayList<Category>();
            allCateList.addAll(cateList);
            for (Category category : cateList) {
                List<Category> list = getParentNodeList(category.getId(), null);
                allCateList.addAll(list);
            }
            // 去重
            removeSame(allCateList);
            // 最后加入根节点
            DictionaryData data = DictionaryDataUtil.findById(typeId);
            Category root = new Category();
            root.setId(data.getId());
            if ("PRODUCT".equals(type)) {
                data.setName(data.getName() + "生产");
            } else if ("SALES".equals(type)) {
                data.setName(data.getName() + "销售");
            }
            root.setName(data.getName());
            allCateList.add(root);
            // 将筛选完的List转换为CategoryTreeList
            List<CategoryTree> treeList = new ArrayList<CategoryTree>();
            for (Category category : allCateList) {
                CategoryTree treeNode = new CategoryTree();
                treeNode.setId(category.getId());
                treeNode.setName(category.getName());
                treeNode.setParentId(category.getParentId());
                // 判断是否为父级节点
                List<Category> nodesList = categoryService.findPublishTree(category.getId(), null);
                if (nodesList != null && nodesList.size() > 0) {
                    treeNode.setIsParent("true");
                }
                treeList.add(treeNode);
            }
            for (CategoryTree treeNode : treeList) {
                // 判断是否被选中
                if (expertId != null) {
                    treeNode.setChecked(isExpertChecked(treeNode.getId(), expertId, typeId, null));
                } else if (supplierId != null) {
                    treeNode.setChecked(isSupplierChecked(treeNode.getId(), supplierId, type));
                }
            }
            return JSON.toJSONString(treeList);
        }
    }

    /**
     * 〈简述〉
     * 获取所有已选中的节点
     * 〈详细描述〉
     *
     * @param expertId
     * @param typeId
     * @param model
     * @return
     * @author WangHuijie
     */
    @RequestMapping("/getCategories")
    public String getCategories(String expertId, String typeId, Model model, Integer pageNum) {
        String code = DictionaryDataUtil.findById(typeId).getCode();
        String flag = null;
        if (code.equals("ENG_INFO_ID")) {
            flag = "ENG_INFO";
        }
        // 查询已选中的节点信息
        List<ExpertCategory> expertItems = expertCategoryService.getListByExpertId(expertId, typeId, pageNum == null ? 1 : pageNum);
        List<SupplierCateTree> allTreeList = new ArrayList<SupplierCateTree>();
        for (ExpertCategory item : expertItems) {
            String categoryId = item.getCategoryId();
            SupplierCateTree cateTree = getTreeListByCategoryId(categoryId, flag);
            if (cateTree != null && cateTree.getRootNode() != null) {
                cateTree.setItemsId(categoryId);
                allTreeList.add(cateTree);
            }
        }
        for (SupplierCateTree cate : allTreeList) {
            cate.setRootNode(cate.getRootNode() == null ? "" : cate.getRootNode());
            cate.setFirstNode(cate.getFirstNode() == null ? "" : cate.getFirstNode());
            cate.setSecondNode(cate.getSecondNode() == null ? "" : cate.getSecondNode());
            cate.setThirdNode(cate.getThirdNode() == null ? "" : cate.getThirdNode());
            cate.setFourthNode(cate.getFourthNode() == null ? "" : cate.getFourthNode());
            cate.setRootNode(cate.getRootNode());
        }
        model.addAttribute("expertId", expertId);
        model.addAttribute("typeId", typeId);
        model.addAttribute("result", new PageInfo<>(expertItems));
        model.addAttribute("itemsList", allTreeList);

        // 如果状态为退回修改则查询没通过的字段
        ExpertAudit expertAudit = new ExpertAudit();
        expertAudit.setExpertId(expertId);
        expertAudit.setSuggestType("six");
        List<ExpertAudit> auditList = expertAuditService.selectFailByExpertId(expertAudit);
        // 所有的不通过字段的名字
        StringBuffer errorField = new StringBuffer();
        for (ExpertAudit audit : auditList) {
            errorField.append(audit.getAuditField() + ",");
        }
        model.addAttribute("errorField", errorField);

        return "ses/ems/expert/ajax_items";
    }

    /**
     * 〈简述〉查询品目信息
     * 〈详细描述〉
     *
     * @param categoryId 产品Id
     * @return List<CategoryTree> tree对象List
     * @author WangHuijie
     */
    public SupplierCateTree getTreeListByCategoryId(String categoryId, String flag) {
        SupplierCateTree cateTree = new SupplierCateTree();
        // 递归获取所有父节点
        List<Category> parentNodeList = getAllParentNode(categoryId, flag);
        // 加入根节点
        for (int i = 0; i < parentNodeList.size(); i++) {
            DictionaryData rootNode = DictionaryDataUtil.findById(parentNodeList.get(i).getId());
            if (rootNode != null) {
                cateTree.setRootNode(rootNode.getName());
            }
        }
        // 加入一级节点
        if (cateTree.getRootNode() != null) {
            for (int i = 0; i < parentNodeList.size(); i++) {
                Category cate = null;
                if (flag == null) {
                    cate = categoryService.findById(parentNodeList.get(i).getId());
                } else {
                    cate = engCategoryService.findById(parentNodeList.get(i).getId());
                }
                if (cate != null && cate.getParentId() != null) {
                    DictionaryData rootNode = DictionaryDataUtil.findById(cate.getParentId());
                    if (rootNode != null && cateTree.getRootNode().equals(rootNode.getName())) {
                        cateTree.setFirstNode(cate.getName());
                    }
                }
            }
        }
        // 加入二级节点
        if (cateTree.getRootNode() != null && cateTree.getFirstNode() != null) {
            for (int i = 0; i < parentNodeList.size(); i++) {
                Category cate = null;
                if (flag == null) {
                    cate = categoryService.findById(parentNodeList.get(i).getId());
                } else {
                    cate = engCategoryService.findById(parentNodeList.get(i).getId());
                }
                if (cate != null && cate.getParentId() != null) {
                    Category parentNode = null;
                    if (flag == null) {
                        parentNode = categoryService.findById(cate.getParentId());
                    } else {
                        parentNode = engCategoryService.findById(cate.getParentId());
                    }
                    if (parentNode != null && cateTree.getFirstNode().equals(parentNode.getName())) {
                        cateTree.setSecondNode(cate.getName());
                    }
                }
            }
        }
        // 加入三级节点
        if (cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null) {
            for (int i = 0; i < parentNodeList.size(); i++) {
                Category cate = null;
                if (flag == null) {
                    cate = categoryService.findById(parentNodeList.get(i).getId());
                } else {
                    cate = engCategoryService.findById(parentNodeList.get(i).getId());
                }
                if (cate != null && cate.getParentId() != null) {
                    Category parentNode = null;
                    if (flag == null) {
                        parentNode = categoryService.findById(cate.getParentId());
                    } else {
                        parentNode = engCategoryService.findById(cate.getParentId());
                    }
                    if (parentNode != null && cateTree.getSecondNode().equals(parentNode.getName())) {
                        cateTree.setThirdNode(cate.getName());
                    }
                }
            }
        }
        // 加入末级节点
        if (cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null && cateTree.getThirdNode() != null) {
            for (int i = 0; i < parentNodeList.size(); i++) {
                Category cate = null;
                if (flag == null) {
                    cate = categoryService.findById(parentNodeList.get(i).getId());
                } else {
                    cate = engCategoryService.findById(parentNodeList.get(i).getId());
                }
                if (cate != null && cate.getParentId() != null) {
                    Category parentNode = null;
                    if (flag == null) {
                        parentNode = categoryService.findById(cate.getParentId());
                    } else {
                        parentNode = engCategoryService.findById(cate.getParentId());
                    }
                    if (parentNode != null && cateTree.getThirdNode().equals(parentNode.getName())) {
                        cateTree.setFourthNode(cate.getName());
                    }
                }
            }
        }
        return cateTree;
    }
}