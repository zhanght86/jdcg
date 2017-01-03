package ses.controller.sys.sms;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import common.constant.Constant;
import common.constant.StaticVariables;
import common.model.UploadFile;
import common.service.UploadService;
import ses.dao.sms.SupplierFinanceMapper;
import ses.dao.sms.SupplierMapper;
import ses.dao.sms.SupplierStockholderMapper;
import ses.formbean.ContractBean;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.ems.Expert;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierHistory;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.NoticeDocumentService;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierBranchService;
import ses.service.sms.SupplierFinanceService;
import ses.service.sms.SupplierHistoryService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierMatEngService;
import ses.service.sms.SupplierMatProService;
import ses.service.sms.SupplierMatSeService;
import ses.service.sms.SupplierMatSellService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;
import ses.util.DictionaryDataUtil;
import ses.util.FtpUtil;
import ses.util.IdentityCode;
import ses.util.PropUtil;
import ses.util.ValidateUtils;
import ses.util.WfUtil;

/**
 * @Title: supplierController
 * @Description: 供应商信息 Controller
 * @author: Wang Zhaohua
 * @date: 2016-9-7下午1:39:22
 */
 @Controller
 @Scope("prototype")
@RequestMapping("/supplier")
 public class SupplierController extends BaseSupplierController {

   @Autowired
   private SupplierService supplierService;// 供应商基本信息

   @Autowired
   private SupplierMatProService supplierMatProService;// 供应商物资生产专业信息

   @Autowired
   private SupplierMatSellService supplierMatSellService;// 供应商物资销售专业信息

   @Autowired
   private SupplierMatSeService supplierMatSeService;// 供应商服务专业信息

   @Autowired
   private SupplierMatEngService supplierMatEngService;// 供应商工程专业信息

   @Autowired
   private OrgnizationServiceI orgnizationServiceI;// 机构

   @Autowired
   private DictionaryDataServiceI dictionaryDataServiceI;

   @Autowired
   private NoticeDocumentService noticeDocumentService;

   @Autowired
   private CategoryService categoryService;


   /** 供应商关联类型 */
   @Autowired
   private SupplierTypeRelateService supplierTypeRelateService;

   @Autowired
   private UploadService uploadService;

   @Autowired
   private SupplierItemService supplierItemService;


   @Autowired
   private SupplierFinanceMapper supplierFinanceMapper;// 供应商财务信息


   @Autowired
   private SupplierStockholderMapper supplierStockholderMapper;//股东信息

   @Autowired
   private SupplierFinanceService supplierFinanceService;

   @Autowired
   private SupplierMapper supplierMapper;

   @Autowired
   private AreaServiceI areaService;

   @Autowired
   private ExpertService expertService;

   @Autowired
   private SupplierAddressService supplierAddressService;

   @Autowired
   private SupplierBranchService supplierBranchService;


   @Autowired
   private SupplierAuditService supplierAuditService;


   @Autowired
   private  SupplierHistoryService supplierHistoryService;

   @Autowired
   private PurchaseOrgnizationServiceI purchaseOrgnizationService;

   @Autowired
   private UserServiceI userService;

   /**
    * @Title: getIdentity
    * @author: Wang Zhaohua
    * @date: 2016-10-11 下午4:59:01
    * @Description: 获取验证码
    * @param: @param request
    * @param: @param response
    * @param: @throws IOException
    * @return: void
    */
   @RequestMapping(value = "get_identity")
   public void getIdentity(HttpServletRequest request, HttpServletResponse response) throws IOException {
     IdentityCode identityCode = new IdentityCode(96, 28, 4, 5);
     identityCode.write(request, response);
   }

   /**
    * @Title: registrationPage
    * @author: Wang Zhaohua
    * @date: 2016-9-2 下午4:49:18
    * @Description: 跳转到注册须知页面
    * @param: @return
    * @return: String
    */
   @RequestMapping("registration_page")
   public String registrationPage(Model model) {
     DictionaryData dd = DictionaryDataUtil.get("SUPPLIER_REGISTER_NOTICE");
     if (dd != null){
       Map<String, Object> param = new HashMap<String, Object>();
       param.put("docType", dd.getId());
       model.addAttribute("doc", noticeDocumentService.findDocByMap(param));
     }
     return "ses/sms/supplier_register/registration";
   }

   /**
    * @Title: register
    * @author: Wang Zhaohua
    * @date: 2016-9-2 下午4:49:34
    * @Description: 跳转到注册页面
    * @param: @param supplier
    * @param: @param model
    * @param: @return
    * @return: String
    */
   @RequestMapping("register_page")
   public String registerPage(HttpServletRequest request) {

     String id = WfUtil.createUUID();
     request.setAttribute("id",id);

     return "ses/sms/supplier_register/register";

   }

   /**
    * @Title: register
    * @author: Wang Zhaohua
    * @date: 2016-9-5 下午4:37:39
    * @Description: 供应商注册
    * @param: @param supplier
    * @param: @param model
    * @param: @return
    * @return: String
    */
   @RequestMapping(value = "register")
   public String register(HttpServletRequest request, Model model, Supplier supplier) {

     //页面过期处理
     if (StringUtils.isEmpty(supplier.getId())){
       String id = WfUtil.createUUID();
       request.setAttribute("id",id);
       return "ses/sms/supplier_register/register";
     }
     Supplier sup = supplierService.selectById(supplier.getId());

     //未注册供应商
     if (sup == null){
       boolean flag = validateRegister(request, model, supplier);
       if (flag){
         supplier = supplierService.register(supplier);
         List<SupplierFinance> list = supplierFinanceService.getYear();
         supplier.setListSupplierFinances(list);
         initCompanyType(model, supplier);
         return "ses/sms/supplier_register/basic_info";
       }
     }

     //已注册供应商
     if(sup != null){
       //初始化近三年的财务信息
       initFinance(sup);

       //股东信息
       List<SupplierStockholder> stock = supplierStockholderMapper.findStockholderBySupplierId(sup.getId());
       if(stock != null && stock.size()>0){
         sup.setListSupplierStockholders(stock);
       } 
       /* List<SupplierTypeRelate> relate = supplierTypeRelateService.queryBySupplier(sup.getId());
  	        model.addAttribute("relate", relate);*/

       //供应商地址信息
       if(sup.getAddress()!=null){
         Area area = areaService.listById(sup.getAddress());
         List<Area> city = areaService.findAreaByParentId(area.getParentId());
         model.addAttribute("city", city);
         model.addAttribute("area", area);
       }

       //生产经营地址
       if(sup.getAddressList()!=null && sup.getAddressList().size()>0){
         for(SupplierAddress b:supplier.getAddressList()){
           if (StringUtils.isNotBlank(b.getProvinceId())){
             List<Area> city = areaService.findAreaByParentId(b.getProvinceId());
             b.setAreaList(city);
           }
         }

       } else {
         List<SupplierAddress> addressList = new ArrayList<SupplierAddress>();
         SupplierAddress address = new SupplierAddress();
         addressList.add(address);
         sup.setAddressList(addressList);
       }

       //省份
       if(sup.getConcatProvince()!=null){
         List<Area> concity = areaService.findAreaByParentId(sup.getConcatProvince());
         sup.setConcatCityList(concity);
       }
       if(sup.getArmyBuinessProvince()!=null){
         List<Area> armcity = areaService.findAreaByParentId(sup.getArmyBuinessProvince());
         sup.setArmyCity(armcity);
       }

       //境外分支
       List<SupplierBranch> branchList =  supplierBranchService.findSupplierBranch(supplier.getId());
       if (branchList != null && branchList.size() > 0){
         sup.setBranchList(branchList);
       } else {
         branchList = new ArrayList<SupplierBranch>();
         SupplierBranch branch = new SupplierBranch();
         branchList.add(branch);
         sup.setBranchList(branchList);
       }

       initCompanyType(model, sup);
       return "ses/sms/supplier_register/basic_info";
     }
     return "ses/sms/supplier_register/register";
   }


   /**
    * 
    *〈简述〉初始化近三年的财务信息
    *〈详细描述〉
    * @author myc
    * @param sup {@link Supplier}
    */
   private void initFinance(Supplier sup){
     List<SupplierFinance> finace = supplierFinanceMapper.findFinanceBySupplierId(sup.getId());
     if(finace!=null&&finace.size()>0){
       List<SupplierFinance> finaceList = supplierFinanceService.getList(finace);
       sup.setListSupplierFinances(finaceList);
     } else {
       List<SupplierFinance> list = supplierFinanceService.getYear();
       sup.setListSupplierFinances(list);
     }
   }

   /**
    * 
    *〈简述〉初始化常量
    *〈详细描述〉
    * @author myc
    * @param model
    * @param supplier 供应商
    */
   private void initCompanyType(Model model, Supplier supplier){
     //初始化省份
     List<Area> privnce = areaService.findRootArea();
     model.addAttribute("privnce", privnce);
     //初始化当前供应商
     model.addAttribute("currSupplier", supplier);
     //初始化供应商注册附件类型
     model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
     model.addAttribute("sysKey",  Constant.SUPPLIER_SYS_KEY);
     //初始化公司性质
     model.addAttribute("company", DictionaryDataUtil.find(17));
     //初始化所在国家
     model.addAttribute("foregin", DictionaryDataUtil.find(24));
   }

   /**
    * @Title: searchOrg
    * @author: Wang Zhaohua
    * @date: 2016-10-19 下午2:28:42
    * @Description: 查询采购机构
    * @param: @param request
    * @param: @param isName
    * @param: @return
    * @return: String
    */
   @RequestMapping(value = "search_org")
   public String searchOrg(HttpServletRequest request, String pid,String cid,Model model) {
     //		Supplier supplier = (Supplier) request.getSession().getAttribute("currSupplier");
     HashMap<String, Object> map = new HashMap<String, Object>();
     //		map.put("name", "%" + supplier.getAddress().split(",")[0] + "%");
     map.put("provinceId",pid);
     map.put("cityId",cid);
     List<Orgnization> listOrgnizations1 = orgnizationServiceI.findOrgnizationList(map);
     //		map.clear();
     //		map.put("notName", "%" + supplier.getAddress().split(",")[0] + "%");
     //		map.put("isName", "%" + isName + "%");
     //		List<Orgnization> listOrgnizations2 = orgnizationServiceI.findOrgnizationList(map);
     model.addAttribute("listOrgnizations1", listOrgnizations1);
     //		request.getSession().setAttribute("listOrgnizations2", listOrgnizations2);
     //		request.getSession().setAttribute("jump.page", "procurement_dep");
     HashMap<String, Object> map1 = new HashMap<String, Object>();
     map1.put("typeName", "1");
     List<PurchaseDep> list = purchaseOrgnizationService
       .findPurchaseDepList(map1);  
     for (PurchaseDep purchaseDep : list) {
       for (Orgnization org : listOrgnizations1) {
         if(purchaseDep.getOrgnization().getId().equals(org.getId())){
           list.remove(org);
         }
       }
     }
     model.addAttribute("allPurList", list);
     return "ses/sms/supplier_register/procurement_dep";
   }

   /**
    * @Title: download
    * @author: Wang Zhaohua
    * @date: 2016-10-11 下午5:01:10
    * @Description: 文件下载
    * @param: @param request
    * @param: @param response
    * @param: @param fileName
    * @return: void
    */
   @RequestMapping(value = "download")
   public void download(HttpServletRequest request, HttpServletResponse response, String fileName) {
     String stashPath = super.getStashPath(request);
     FtpUtil.startDownFile(stashPath, PropUtil.getProperty("file.upload.path.supplier"), fileName);
     FtpUtil.closeFtp();
     if (fileName != null && !"".equals(fileName)) {
       super.download(request, response, fileName);
     } else {
       super.alert(request, response, "无附件下载 !", true);
     }
     super.removeStash(request, fileName);
   }

   /**
    * 
    *〈简述〉临时保存
    *〈详细描述〉
    * @author myc
    * @param request {@link HttpServletRequest}
    * @param supplier {@link Supplier}
    * @return
    */
   @ResponseBody
   @RequestMapping(value="/temporarySave",produces="html/text;charset=UTF-8")
   public String temporarySave(HttpServletRequest request, Supplier supplier,String flag){
     String res = StaticVariables.SUCCESS;

     //如果是附件上传页面
     if(flag != null && flag.equals("file")){
       res = StaticVariables.SUCCESS;
     }
     //保存审核采购机构
     else if(flag != null && flag.equals("1")){
       try {
         supplierService.updateSupplierProcurementDep(supplier);
       } catch (Exception e) {
         res = StaticVariables.FAILED;
         e.printStackTrace();
       }
     }else{
       //保存基本信息
       try {
         supplierService.perfectBasic(supplier);
       } catch (Exception e) {
         res = StaticVariables.FAILED;
         e.printStackTrace();
       }

     }

     return res;
   }

   /**
    * @Title: basic
    * @author: Wang Zhaohua
    * @date: 2016-9-7 下午4:47:37
    * @Description: 完善基本信息
    * @param: @param request
    * @param: @param supplier
    * @param: @param model
    * @param: @return
    * @return: String
    * @throws Exception 
    */
   @RequestMapping(value = "perfect_basic")
   public String perfectBasic(HttpServletRequest request,Model model, Supplier supplier) throws Exception {

     boolean info = validateBasicInfo(request,model,supplier);

     List<SupplierTypeRelate> relate = supplierTypeRelateService.queryBySupplier(supplier.getId());
     model.addAttribute("relate", relate);

     if(supplier.getAddress()!=null){
       Area area = areaService.listById(supplier.getAddress());
       List<Area> city = areaService.findAreaByParentId(area.getParentId());
       model.addAttribute("city", city);
       model.addAttribute("area", area);
     }

     if (info){
       Supplier before = supplierService.get(supplier.getId());
       if(before.getStatus().equals(7)){
         record("", before, supplier, supplier.getId());//记录供应商退回修改的内容
       }
       supplierService.perfectBasic(supplier);
       supplier = supplierService.get(supplier.getId());

       List<DictionaryData> list = DictionaryDataUtil.find(6);
       for(int i=0;i<list.size();i++){
         String code = list.get(i).getCode();
         if(code.equals("GOODS")){
           list.remove(list.get(i));
         }
       }
       model.addAttribute("supplieType", list);
       List<DictionaryData> wlist = DictionaryDataUtil.find(8);
       model.addAttribute("wlist", wlist);
       //物资生产类型的必须有的证书
       if(supplier.getSupplierMatPro()==null){
         //                if(supplier.getSupplierMatPro().getOrgName()==null){
         SupplierMatPro pro = supplierMatProService.init();
         supplier.setSupplierMatPro(pro);
         //                }


       }else if(supplier.getSupplierMatPro().getOrgName()==null){
         supplier.setSupplierMatPro(null);
         SupplierMatPro pro = supplierMatProService.init();
         supplier.setSupplierMatPro(pro);
       }
       String attid = DictionaryDataUtil.getId("SUPPLIER_PRODUCT");


       model.addAttribute("currSupplier", supplier);
       Map<String, Object> map = supplierService.getCategory(supplier.getId());
       model.addAttribute("server", map.get("server"));
       model.addAttribute("product", map.get("product"));
       model.addAttribute("sale", map.get("sale"));
       model.addAttribute("project", map.get("project"));
       model.addAttribute("attid", attid);
       List<DictionaryData> company = DictionaryDataUtil.find(17);
       model.addAttribute("company", company);
       List<Area> privnce = areaService.findRootArea();
       model.addAttribute("privnce", privnce);

       return "ses/sms/supplier_register/supplier_type";

     } else{
       Supplier supplier2 = supplierService.get(supplier.getId());
       if(supplier2.getListSupplierFinances()!=null&&supplier2.getListSupplierFinances().size()>0){
         supplier.setListSupplierFinances(supplier2.getListSupplierFinances());  
       }
       if(supplier2.getListSupplierStockholders()!=null&&supplier2.getListSupplierStockholders().size()>0){
         supplier.setListSupplierStockholders(supplier2.getListSupplierStockholders()); 
       }
       if(supplier.getAddressList()!=null&&supplier.getAddressList().size()>0){
         for(SupplierAddress b:supplier.getAddressList()){
           if (StringUtils.isNotBlank(b.getProvinceId())){
             List<Area> city = areaService.findAreaByParentId(b.getProvinceId());
             b.setAreaList(city);
           }
         }

       }
       if(supplier.getConcatProvince()!=null){
         List<Area> concity = areaService.findAreaByParentId(supplier.getConcatProvince());
         supplier.setConcatCityList(concity);
       }
       if(supplier.getArmyBuinessProvince()!=null){
         List<Area> armcity = areaService.findAreaByParentId(supplier.getArmyBuinessProvince());
         supplier.setArmyCity(armcity);
       }

       initCompanyType(model, supplier);
       return "ses/sms/supplier_register/basic_info";
     }


   }

   /**
    * 
    *〈简述〉ajax保存供应商类型
    *〈详细描述〉
    * @author myc
    * @param supplier {@link Supplier}
    * @return
    */
   //	@ResponseBody
   @RequestMapping(value="/saveSupplierType",produces="html/text;charset=UTF-8")
   public String saveSupplierType(Supplier supplier,Model model){

     if (supplier != null){
       if (StringUtils.isNotBlank(supplier.getSupplierTypeIds())){
         String[] supplierTypeArray = supplier.getSupplierTypeIds().trim().split(",");
         for(String supplierType : supplierTypeArray){
           if (supplierType.equals("PRODUCT")) {
             supplierMatProService.saveOrUpdateSupplierMatPro(supplier);
           } 
           if (supplierType.equals("SALES")) {
             supplierMatSellService.saveOrUpdateSupplierMatSell(supplier);
           }
           if (supplierType.equals("PROJECT")) {
             supplierMatEngService.saveOrUpdateSupplierMatPro(supplier);
           }
           if (supplierType.equals("SERVICE")) {
             supplierMatSeService.saveOrUpdateSupplierMatSe(supplier);
           }
         }
       }
       supplierTypeRelateService.saveSupplierTypeRelate(supplier);
     }
     model.addAttribute("currSupplier", supplier);
     return "ses/sms/supplier_register/supplier_type";
   }

   /**
    * @Title: perfectProfessional
    * @author: Wang Zhaohua
    * @date: 2016-10-31 下午3:11:06
    * @Description: 完善专业信息
    * @param: @param request
    * @param: @param supplier
    * @param: @param jsp
    * @param: @param defaultPage
    * @param: @return
    * @param: @throws IOException
    * @return: String
    */
   @RequestMapping(value = "perfect_professional")
   public String perfectProfessional(HttpServletRequest request, Supplier supplier, String flag,Model model) throws IOException {


     boolean info=true;
     boolean sale=true;
     boolean pro=true;
     boolean server=true;
     boolean project=true;

     String[] str = supplier.getSupplierTypeIds().trim().split(",");

     for(String s:str){
       if (s.equals("PRODUCT")) {
         pro = validatePro(request, supplier.getSupplierMatPro(), model);
         if(info==true){
           supplierMatProService.saveOrUpdateSupplierMatPro(supplier);
         }
       } 
       if (s.equals("SALES")) {
         sale = validateSale(request, supplier.getSupplierMatSell(), model);
         if(info==true){
           supplierMatSellService.saveOrUpdateSupplierMatSell(supplier);
         }
       }
       if (s.equals("PROJECT")) {
         project = validateEng(request, supplier.getSupplierMatEng(), model);
         if(info==true){
           supplierMatEngService.saveOrUpdateSupplierMatPro(supplier);
         }
       }
       if (s.equals("SERVICE")) {
         server = validateServer(request, supplier.getSupplierMatSe(), model);
         if(info==true){
           supplierMatSeService.saveOrUpdateSupplierMatSe(supplier);
         }
       }
     }
     supplierTypeRelateService.saveSupplierTypeRelate(supplier);
     //		 supplier = supplierService.get(supplier.getId());
     String[] split = supplier.getSupplierTypeIds().split(",");
     int length = split.length;
     model.addAttribute("length", length);
     model.addAttribute("supplierTypeIds",  supplier.getSupplierTypeIds());
     model.addAttribute("currSupplier", supplier);
     if(pro==true&&server==true&&project==true&&sale==true){
       return "ses/sms/supplier_register/items";
     }else{
       List<DictionaryData> list = DictionaryDataUtil.find(6);
       for(int i=0;i<list.size();i++){
         String code = list.get(i).getCode();
         if(code.equals("GOODS")){
           list.remove(list.get(i));
         }
       }
       model.addAttribute("supplieType", list);
       List<DictionaryData> wlist = DictionaryDataUtil.find(8);
       model.addAttribute("wlist", wlist);

       return "ses/sms/supplier_register/supplier_type";	
     }

   }

   /**
    * @Title: perfectDep
    * @author: Wang Zhaohua
    * @date: 2016-11-2 下午4:28:53
    * @Description: 完善审核机构信息
    * @param: @param request
    * @param: @param supplier
    * @param: @param jsp
    * @param: @return
    * @param: @throws IOException
    * @return: String
    */
   @RequestMapping(value = "perfect_dep")
   public String perfectDep(HttpServletRequest request, Supplier supplier, String flag,Model model,String supplierTypeIds) {

     if(flag.equals("next")){
       supplierService.updateSupplierProcurementDep(supplier);
       supplier = supplierService.get(supplier.getId());
       model.addAttribute("currSupplier", supplier);
       model.addAttribute("supplierTypeIds", supplierTypeIds);
       return "ses/sms/supplier_register/template_download";
     }else if(flag.equals("store")){
       supplierService.updateSupplierProcurementDep(supplier);
       supplier = supplierService.get(supplier.getId());
       String orgId = supplier.getProcurementDepId();
       //			orgnizationServiceI.findOrgnizationList(map)
       model.addAttribute("currSupplier", supplier);
       HashMap<String, Object> map1 = new HashMap<String, Object>();
       map1.put("typeName", "1");
       List<PurchaseDep> list = purchaseOrgnizationService
         .findPurchaseDepList(map1);  
       for (PurchaseDep purchaseDep : list) {
         if(purchaseDep.equals(orgId)){
           list.remove(purchaseDep);
         }
       }
       model.addAttribute("allPurList", list);
       return "ses/sms/supplier_register/procurement_dep";
     }else{
       supplier = supplierService.get(supplier.getId());
       model.addAttribute("currSupplier", supplier);
       return "ses/sms/supplier_register/items";
     }

   }

   /**
    * @Title: perfectDownload
    * @author: Wang Zhaohua
    * @date: 2016-11-2 下午4:42:17
    * @Description: 模板下载
    * @param: @param request
    * @param: @param supplier
    * @param: @param jsp
    * @param: @return
    * @return: String
    */
   @RequestMapping(value = "perfect_download")
   public String perfectDownload(HttpServletRequest request, Supplier supplier, String jsp,String flag,Model model,String supplierTypeIds) {
     supplier = supplierService.get(supplier.getId());

     if ("next".equals(flag)) {
       Integer sysKey = Constant.SUPPLIER_SYS_KEY;
       model.addAttribute("sysKey", sysKey);
       model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
       model.addAttribute("currSupplier", supplier);

       model.addAttribute("supplierTypeIds", supplierTypeIds);

       return "ses/sms/supplier_register/template_upload";
     } else{
       supplier = supplierService.get(supplier.getId());
       HashMap<String, Object> map = new HashMap<String, Object>();
       // 采购机构
       List<PurchaseDep> depList = null;
       if(supplier.getProcurementDepId()!=null){
         map.put("id", supplier.getProcurementDepId());
         map.put("typeName", "1");

         depList = purchaseOrgnizationService .findPurchaseDepList(map);

         if (depList != null && depList.size() >0){
           Orgnization orgnization = depList.get(0);
           List<Area> city = areaService.findAreaByParentId(orgnization.getProvinceId());
           model.addAttribute("orgnization", orgnization);
           model.addAttribute("city", city);
           model.addAttribute("listOrgnizations1", depList);

         }else{

         }

       }
       List<Area> privnce = areaService.findRootArea();
       model.addAttribute("privnce", privnce);

       model.addAttribute("currSupplier", supplier);
       model.addAttribute("supplierTypeIds", supplierTypeIds);

       HashMap<String, Object> map1 = new HashMap<String, Object>();
       map1.put("typeName", "1");

       List<PurchaseDep> list = purchaseOrgnizationService
         .findPurchaseDepList(map1);  
       if(depList != null && depList.size() != 0){
         for (PurchaseDep purchaseDep : list) {
           for (Orgnization org : depList) {
             if(purchaseDep.getOrgnization().getId().equals(org.getId())){
               list.remove(org);
             }
           }
         }
       }
       model.addAttribute("allPurList", list);


       return "ses/sms/supplier_register/procurement_dep";
     }

   }

   /**
    * @Title: perfectUpload
    * @author: Wang Zhaohua
    * @date: 2016-11-2 下午4:43:20
    * @Description: TODO
    * @param: @param request
    * @param: @param supplier
    * @param: @param jsp
    * @param: @return
    * @return: String
    * @throws IOException 
    */
   @RequestMapping(value = "perfect_upload")
   public String perfectUpload(HttpServletRequest request, Supplier supplier, String jsp,String flag,Model model,String supplierTypeIds) throws IOException {
     //		this.setSupplierUpload(request, supplier);

     boolean bool = validateUpload(model,supplier.getId());

     if (!"commit".equals(jsp)) {
       supplierService.perfectBasic(supplier);
       supplier = supplierService.get(supplier.getId());
       model.addAttribute("currSupplier", supplier);
       model.addAttribute("supplierTypeIds", supplierTypeIds);
       model.addAttribute("jump.page", jsp);
       return "ses/sms/supplier_register/template_download";
     }
     if(bool!=true){
       return "ses/sms/supplier_register/template_upload";
     }
     supplierService.commit(supplier);
     request.getSession().removeAttribute("currSupplier");
     request.getSession().removeAttribute("sysKey");
     request.getSession().removeAttribute("supplierDictionaryData");
     request.getSession().removeAttribute("listOrgnizations1");
     request.getSession().removeAttribute("listOrgnizations2");
     return "redirect:../index/selectIndexNews.html";
   }
   
   @ResponseBody
   @RequestMapping("/isCommit")
   public String isCommit(Model model, Supplier supplier) {
       boolean bool = validateUpload(model,supplier.getId());
       if(bool!=true){
           // 返回
           return "1";
       } else {
           return "0";
       }
   }

   /**
    * @Title: page_jump
    * @author: Wang Zhaohua
    * @date: 2016-9-7 下午5:43:49
    * @Description: page_jump
    * @param: @param request
    * @param: @return
    * @return: String
    */
   @RequestMapping(value = "page_jump")
   public String pageJump(HttpServletRequest request) {
     String page = (String) request.getSession().getAttribute("jump.page");
     if (page == null || "".equals(page)) {
       page = "registration";
     }
     return "ses/sms/supplier_register/" + page;
   }

   @RequestMapping(value = "check_login_name")
   public void checkLoginName(HttpServletResponse response, String loginName) {
     boolean flag = supplierService.checkLoginName(loginName);
     String msg = "";
     if (flag) {
       msg = "{\"msg\":\"success\"}";
     } else {
       msg = "{\"msg\":\"fail\"}";
     }
     super.writeJson(response, msg);
   }

   @RequestMapping(value = "return_edit")
   public String returnEdit(HttpServletRequest request, Supplier supplier,Model model ) {
     supplier = supplierService.get(supplier.getId());
     model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
     model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
     model.addAttribute("currSupplier", supplier);
     request.getSession().setAttribute("jump.page", "basic_info");
     return "redirect:page_jump.html";
   }

   /**
    * @Title: checkReferer
    * @author: Wang Zhaohua
    * @date: 2016-9-5 下午4:55:59
    * @Description: 检查 referer
    * @param: @param request
    * @param: @param spaceAndRequest
    * @param: @return
    * @return: boolean
    */
   public boolean checkReferer(HttpServletRequest request, String spaceAndRequest) {
     String referer = request.getHeader("referer");
     String serverName = request.getServerName();// 获取主机名
     int serverPort = request.getServerPort();// 获取端口号
     String contextPath = request.getContextPath();// 获取项目路径
     String url = "http://" + serverName + ":" + serverPort + contextPath + spaceAndRequest;
     if (referer != null && url.equals(referer)) {
       return true;
     }
     return false;
   }




   /**
    * @Title: initBinder
    * @author: Wang Zhaohua
    * @date: 2016-9-7 下午5:44:05
    * @Description: initBinder
    * @param: @param binder
    * @return: void
    */
   @InitBinder
   public void initBinder(ServletRequestDataBinder binder) {
     binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
   }

   //注册登记校验
   public boolean validateRegister(HttpServletRequest request, Model model, Supplier supplier) {
     String identifyCode = (String) request.getSession().getAttribute("img-identity-code");// 验证码
     int count = 0;
     if (supplier.getLoginName() == null || !supplier.getLoginName().matches("^\\w{6,20}$")) {
       model.addAttribute("err_msg_loginName", "登录名由6-20位字母数字和下划线组成 !");
       count++;
     }
     if (supplier.getPassword() == null || !supplier.getPassword().matches("^\\w{6,20}$")) {
       model.addAttribute("err_msg_password", "密码由6-20位字母数字和下划线组成 !");
       count++;
     }
     if (supplier.getConfirmPassword()==null||!supplier.getPassword().equals(supplier.getConfirmPassword())) {
       model.addAttribute("err_msg_ConfirmPassword", "密码和重复密码不一致 !");
       count++;
     }
     if (supplier.getMobile() == null || !supplier.getMobile().matches("^1[0-9]{10}$")) {
       model.addAttribute("err_msg_mobile", "手机格式不正确 !");
       count++;
     }


     /*if (supplier.getMobileCode() == null) {
       model.addAttribute("err_msg_mobileCode", "手机验证码错误 !");
       count++;
     }*/
     /*if (supplier.getIdentifyCode() == null || !supplier.getIdentifyCode().equals(identifyCode)) {
       model.addAttribute("err_msg_code", "验证码错误 !");
       count++;
     }*/
     if (StringUtils.isNotBlank(supplier.getMobile())){
       Integer mobileCount = supplierService.getCountMobile(supplier.getMobile());
       if(mobileCount > 0){
         count++;
         model.addAttribute("err_msg_mobile", "手机号已存在 !");
       }
     }
     if (count > 0) {
       return false;
     }
     return true;
   }

   //基本信息校验
   public boolean validateBasicInfo(HttpServletRequest request, Model model, Supplier supplier) {
     int count = 0;
     if (supplier.getSupplierName() == null || !supplier.getSupplierName().trim().matches("^.{1,80}$")) {
       model.addAttribute("err_msg_supplierName", "不能为空!");
       count++;
     }
     //		if (supplier.getWebsite() == null || !ValidateUtils.Url(supplier.getWebsite())) {
     //			model.addAttribute("err_msg_website", "格式错误 !");
     //			count++;
     //		}
     if (supplier.getFoundDate() == null) {
       model.addAttribute("err_msg_foundDate", "不能为空 !");
       count++;
     }
     if(supplier.getFoundDate()!=null){
       Date date = supplierService.addDate(supplier.getFoundDate(), 1, 3);
       Date now=new Date();
       if(date.getTime()>now.getTime()){
         model.addAttribute("err_msg_foundDate", "成立日期必须大于三年!");
         count++;
       }
     }

     //		supplierService.addDate(supplier.getFoundDate(), 1, -3);
     if (supplier.getAddress() == null) {
       model.addAttribute("err_msg_address", "不能为空!");
       count++;
     }
     if (supplier.getBankName() == null || !supplier.getBankName().trim().matches("^.{1,80}$")) {
       model.addAttribute("err_msg_bankName", "不能为空 !");
       count++;
     }
     if (supplier.getBankAccount() == null ) {
       model.addAttribute("err_msg_bankAccount", "格式不正确 !");
       count++;
     }
     if (supplier.getPostCode() == null || supplier.getPostCode().length()!=6) {
       model.addAttribute("err_msg_postCode", "格式不正确 !");
       count++;
     }
     if(supplier.getDetailAddress()==null||supplier.getDetailAddress().length()>80){
       model.addAttribute("err_detailAddress", "详细地址不能为空!");
       count++;
     }

     if(supplier.getLegalName()==null||supplier.getLegalName().length()>20){
       model.addAttribute("err_legalName", "不能为空 或者名字过长!");
       count++;
     }
     if(supplier.getLegalIdCard()==null ){
       model.addAttribute("err_legalCard", "不能为空 !");
       count++;
     }
     if(supplier.getLegalIdCard()!=null && !supplier.getLegalIdCard().matches("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$")){
       model.addAttribute("err_legalCard", "身份证号码格式不正确 !");
       count++;
     }
     if(supplier.getConcatCity()==null){
       model.addAttribute("err_city", "地址不能为空!");
       count++;
     }
     if(supplier.getArmyBusinessName()==null){
       model.addAttribute("err_armName", "不能为空!");
       count++;
     }
     if(supplier.getArmyBusinessFax()==null){
       model.addAttribute("err_armFax", "不能为空!");
       count++;
     }
     if(supplier.getArmyBuinessMobile()==null){
       model.addAttribute("err_armMobile", "不能为空!");
       count++;
     }
     if(supplier.getArmyBuinessTelephone()==null){
       model.addAttribute("err_armTelephone", "不能为空!");
       count++;
     }
     if(supplier.getArmyBuinessEmail()==null){
       model.addAttribute("err_armEmail", "不能为空!");
       count++;
     }
     if(supplier.getArmyBuinessCity()==null){
       model.addAttribute("err_armCity", "不能为空!");
       count++;
     }
     if(supplier.getArmyBuinessAddress()==null){
       model.addAttribute("err_armAddress", "不能为空!");
       count++;
     }


     //		supplier2.setLegalIdCard(supplier.getLegalIdCard());
     //		map.put("legalIdCard", supplier.getLegalIdCard());
     //		List<Supplier> list3 = supplierService.query(map);
     //		if(list3!=null&&list3.size()>0){
     //			model.addAttribute("err_legalCard", "身份证号码已存在");
     //			count++;
     //		}

     if(supplier.getLegalMobile()==null){
       model.addAttribute("err_legalMobile", "不能为空 !");
       count++;
     }
     /*	if(supplier.getLegalMobile()!=null&&!supplier.getLegalMobile().matches("^(0[1-9]{2})-\\d{8}$|^(0[1-9]{3}-(\\d{7,8}))$")){
  			model.addAttribute("err_legalMobile", "固话格式不正确 !");
  			count++;
  		}*/
     if(supplier.getLegalTelephone()==null||!supplier.getLegalTelephone().matches("^1[0-9]{10}$")||supplier.getLegalTelephone().length()>11){
       model.addAttribute("err_legalPhone", "格式不正确 !");
       count++;
     }
     //		map.put("legalTelephone", supplier.getLegalTelephone());
     //		List<Supplier> list4= supplierService.query(map);
     //		if(list4!=null&&list4.size()>1){
     //			model.addAttribute("err_legalCard", "身份证号码已存在");
     //			count++;
     //		}

     if(supplier.getContactName()==null||supplier.getContactName().length()>20){
       model.addAttribute("err_conName", "不能为空 或者字符串过长!");
       count++;
     }

     if(supplier.getContactFax()==null||supplier.getContactFax().length()>15){
       model.addAttribute("err_fax", "格式不正确 !");
       count++;
     }

     if(supplier.getContactMobile()==null||supplier.getContactMobile().length()>12){
       model.addAttribute("err_catMobile", "格式不正确 或是字符过长!");
       count++;
     }
     //		if(supplier.getContactTelephone()==null||!supplier.getContactTelephone().matches("^1[0-9]{10}$")||supplier.getContactTelephone().length()>12){
     //			model.addAttribute("err_catTelphone", "格式不正确 !");
     //			count++;
     //		}
     if(supplier.getContactEmail()==null||!supplier.getContactEmail().matches("^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$")){
       model.addAttribute("err_catEmail", "格式不正确 !");
       count++;
     }
     /*	if(supplier.getContactAddress()==null||supplier.getContactAddress().length()>35){
  			model.addAttribute("err_conAddress", "不能为空或是字符过长!");
  			count++;
  		}*/

     //		supplier1.setCreditCode(supplier.getCreditCode());
     //		map.put("creditCode", supplier.getCreditCode());
     //		List<Supplier> list2 = supplierService.query(map);
     //		if(list2!=null&&list2.size()>1){
     //			model.addAttribute("err_creditCide", "信用代码已存在!");
     //		}

     if(supplier.getCreditCode()==null||supplier.getCreditCode().length()>36){
       model.addAttribute("err_creditCide", "不能为空或是字符过长!");
       count++;
     }

     if(supplier.getRegistAuthority()==null||supplier.getRegistAuthority().length()>20){
       model.addAttribute("err_reAuthoy", "不能为空 或是编码过长!");
       count++;
     }
     if(supplier.getRegistFund()==null){
       model.addAttribute("err_fund", "不能为空 !");
       count++;
     }
     if(supplier.getRegistFund()!=null&&!supplier.getRegistFund().toString().matches("^[0-9].*$")){
       model.addAttribute("err_fund", "资金不能小于0或者是格式不正确 !");
       count++;
     }
     if(supplier.getBusinessStartDate()==null&&supplier.getBranchName()==null){
       model.addAttribute("err_sDate", "营业有效期不能为空 !");
       //			count++;
     }
     /*	if(supplier.getBusinessEndDate()==null){
  			model.addAttribute("err_eDate", "营业截至时间不能为空 !");
  			count++;
  		}*/
     //		if(supplier.getBusinessAddress()==null){
     //			model.addAttribute("err_bAddress", "经营地址不能为空!");
     //			count++;
     //		}
     if(supplier.getBusinessPostCode()==null){
       model.addAttribute("err_bCode", "不能为空!");
       count++;
     }
     if(supplier.getBusinessPostCode()!=null&&!ValidateUtils.Zipcode(supplier.getBusinessPostCode().toString())){
       model.addAttribute("err_bCode", "邮编格式不正确!");
       count++;
     }
     if(supplier.getBusinessScope()!=null&&supplier.getBusinessScope().length()>80){
       model.addAttribute("err_scope", "字符串不超过80个");
       count++;
     }

     if(supplier.getBranchCountry()!=null&&supplier.getBusinessScope().length()>12){
       model.addAttribute("err_country", "字符串不超过12个");
       count++;
     }
     if(supplier.getBranchAddress()!=null&&supplier.getBranchAddress().length()>80){
       model.addAttribute("err_address", "字符串不超过80个");
       count++;
     }
     if(supplier.getBranchName()!=null&&supplier.getBranchName().length()>12){
       model.addAttribute("err_BranName", "字符串不超过12个");
       count++;
     }
     if(supplier.getBranchBusinessScope()!=null&&supplier.getBranchBusinessScope().length()>80){
       model.addAttribute("err_branchScope", "字符串不超过80个");
       count++;
     }


     SupplierDictionaryData supplierDictionary = dictionaryDataServiceI.getSupplierDictionary();
     //* 近三个月完税凭证
     List<UploadFile> tlist = uploadService.getFilesOther(supplier.getId(), supplierDictionary.getSupplierTaxCert(), Constant.SUPPLIER_SYS_KEY.toString());
     if(tlist!=null&&tlist.size()<=0){
       count++;
       model.addAttribute("err_taxCert", "请上传文件!");
     }
     //* 近三年银行基本账户年末对账单
     List<UploadFile> blist = uploadService.getFilesOther(supplier.getId(), supplierDictionary.getSupplierBillCert(), Constant.SUPPLIER_SYS_KEY.toString());
     if(blist!=null&&blist.size()<=0){
       count++;
       model.addAttribute("err_bil", "请上传文件!");
     }
     //近三个月缴纳社会保险金凭证
     List<UploadFile> slist = uploadService.getFilesOther(supplier.getId(), supplierDictionary.getSupplierSecurityCert(), Constant.SUPPLIER_SYS_KEY.toString());
     if(slist!=null&&slist.size()<=0){
       count++;
       model.addAttribute("err_security", "请上传文件!");
     }
     //近三年内无重大违法记录声明
     List<UploadFile> bearlist = uploadService.getFilesOther(supplier.getId(), supplierDictionary.getSupplierBearchCert(), Constant.SUPPLIER_SYS_KEY.toString());
     if(bearlist!=null&&bearlist.size()<=0){
       count++;
       model.addAttribute("err_bearch", "请上传文件!");
     }

     //供应商执照
     List<UploadFile> list = uploadService.getFilesOther(supplier.getId(), supplierDictionary.getSupplierBusinessCert(), Constant.SUPPLIER_SYS_KEY.toString());
     if(list!=null&&list.size()<=0){
       count++;
       model.addAttribute("err_business", "请上传文件!");
     }
     //		List<SupplierFinance> finace = supplierFinanceMapper.findFinanceBySupplierId(supplier.getId());
     //		if(finace!=null&&finace.size()<1){
     //			    count++;
     //				model.addAttribute("finace", "请添加财务信息!");
     //		}
     List<SupplierStockholder> stock = supplierStockholderMapper.findStockholderBySupplierId(supplier.getId());
     if(supplier.getListSupplierStockholders()==null||supplier.getListSupplierStockholders().size()<1){
       count++;
       model.addAttribute("stock", "请添加股东信息!");
     }

     if (count > 0) {
       return false;
     }
     return true;
   }

   //生产信息校验
   public boolean validatePro(HttpServletRequest request,SupplierMatPro supplierMatPro,Model model){
     boolean bool=true;
     if(supplierMatPro.getOrgName()==null||supplierMatPro.getOrgName().length()>12){
       model.addAttribute("org", "不能为空或者字符串过长");
       bool=false;
     }
     if(supplierMatPro.getTotalPerson()==null){
       model.addAttribute("person", "不能为空");
       bool=false;
     }
     if(supplierMatPro.getTotalPerson()!=null&&!supplierMatPro.getTotalPerson().toString().matches("^[0-9]*$")){
       model.addAttribute("person", "人员必须是整数");
       bool=false;
     }
     if(supplierMatPro.getTotalMange()==null){
       model.addAttribute("mange", "不能为空");
       bool=false;
     }
     if(supplierMatPro.getTotalMange()!=null&&!supplierMatPro.getTotalMange().toString().matches("^[0-9]*$")){
       model.addAttribute("mange", "人员必须是整数");
       bool=false;
     }
     if(supplierMatPro.getTotalTech()==null){
       model.addAttribute("tech", "不能为空");
       bool=false;
     }
     if(supplierMatPro.getTotalTech()!=null&&!supplierMatPro.getTotalTech().toString().matches("^[0-9]*$")){
       model.addAttribute("tech", "格式不正确");
       bool=false;
     }
     if(supplierMatPro.getTotalWorker()==null){
       model.addAttribute("work", "不能为空");
       bool=false;
     }
     if(supplierMatPro.getTotalWorker()!=null&&!supplierMatPro.getTotalWorker().toString().matches("^[0-9]*$")){
       model.addAttribute("work", "格式不正确");
       bool=false;
     }
     if(supplierMatPro.getScaleTech()==null){
       model.addAttribute("stech", "不能为空");
       bool=false;
     }
     if(supplierMatPro.getScaleTech()!=null&&!supplierMatPro.getScaleTech().matches("^[-+]?\\d+(\\.\\d+)?$")){
       model.addAttribute("stech", "格式不正确");
       bool=false;
     }
     if(supplierMatPro.getScaleHeightTech()==null){
       model.addAttribute("height", "格式不正确");
       bool=false;
     }
     if(supplierMatPro.getScaleHeightTech()!=null&&!supplierMatPro.getScaleHeightTech().matches("^[-+]?\\d+(\\.\\d+)?$")){
       model.addAttribute("height", "格式不正确");
       bool=false;
     }
     if(supplierMatPro.getResearchName()==null){
       model.addAttribute("reName", "不能为空");
       bool=false;
     }
     if(supplierMatPro.getTotalResearch()==null){
       model.addAttribute("tRe", "不能为空");
       bool=false;
     }

     if(supplierMatPro.getTotalResearch()!=null&&!supplierMatPro.getTotalResearch().toString().matches("^[0-9]*$")){
       model.addAttribute("tRe", "只能输入整数");
       bool=false;
     }
     if(supplierMatPro.getResearchLead()==null||supplierMatPro.getResearchLead().length()>12){
       model.addAttribute("leader", "不能为空或者字符串过长");
       bool=false;
     }
     if(supplierMatPro.getCountryPro()==null||supplierMatPro.getCountryPro().length()>80){
       model.addAttribute("contry", "不能为空或者字符串过长");
       bool=false;
     }
     if(supplierMatPro.getCountryReward()==null||supplierMatPro.getCountryPro().length()>80){
       model.addAttribute("reward", "不能为空或者字符串过长");
       bool=false;
     }
     if(supplierMatPro.getTotalBeltline()==null){
       model.addAttribute("line", "不能为空或者字符串过长");
       bool=false;	
     }
     if(supplierMatPro.getTotalBeltline()!=null&&!supplierMatPro.getTotalBeltline().toString().matches("^[0-9]*$")){
       model.addAttribute("line", "只能输入整数");
       bool=false;	
     }
     if(supplierMatPro.getTotalDevice()==null){
       model.addAttribute("device", "不能为空");
       bool=false;	
     }
     if(supplierMatPro.getTotalDevice()!=null&&!supplierMatPro.getTotalDevice().toString().matches("^[0-9]*$")){
       model.addAttribute("device", "格式正确");
       bool=false;	
     }
     if(supplierMatPro.getQcName()==null||supplierMatPro.getQcName().length()>12){
       model.addAttribute("qcName", "不能为空");
       bool=false;	
     }
     if(supplierMatPro.getTotalQc()==null){
       model.addAttribute("tQc", "不能为空");
       bool=false;	
     }
     /*	if(supplierMatPro.getTotalQc()!=null&&!supplierMatPro.getTotalDevice().toString().matches("^[0-9]*$")){
  			model.addAttribute("tQc", "格式不正确");
  			bool=false;	
  		}*/
     if(supplierMatPro.getQcLead()==null||supplierMatPro.getQcLead().length()>12){
       model.addAttribute("tqcLead", "不能为空");
       bool=false;	
     }
     if(supplierMatPro.getQcDevice()==null||supplierMatPro.getQcLead().length()>12){
       model.addAttribute("tqcDevice", "不能为空");
       bool=false;	
     }
     //		List<SupplierCertPro> list = supplierMatPro.getListSupplierCertPros();
     //		if(list==null||list.size()<1){
     //			model.addAttribute("cert_pro", "请添加生产资质证书信息");
     //			bool=false;	
     //		}

     return bool;
   }

   //销售信息校验
   public boolean validateSale(HttpServletRequest request,SupplierMatSell supplierMatPro,Model model){
     boolean bool=true;
     if(supplierMatPro.getOrgName()==null||supplierMatPro.getOrgName().length()>12){
       model.addAttribute("sale_org", "不能为空或者字符串过长");
       bool=false;
     }
     if(supplierMatPro.getTotalPerson()==null){
       model.addAttribute("sale_person", "不能为空");
       bool=false;
     }
     if(supplierMatPro.getTotalPerson()!=null&&!supplierMatPro.getTotalPerson().toString().matches("^[0-9]*$")){
       model.addAttribute("sale_person", "人员必须是整数");
       bool=false;
     }
     if(supplierMatPro.getTotalMange()==null){
       model.addAttribute("sale_mange", "不能为空");
       bool=false;
     }
     if(supplierMatPro.getTotalMange()!=null&&!supplierMatPro.getTotalMange().toString().matches("^[0-9]*$")){
       model.addAttribute("sale_mange", "人员必须是整数");
       bool=false;
     }
     if(supplierMatPro.getTotalTech()==null){
       model.addAttribute("sale_tech", "不能为空");
       bool=false;
     }
     if(supplierMatPro.getTotalTech()!=null&&!supplierMatPro.getTotalTech().toString().matches("^[0-9]*$")){
       model.addAttribute("sale_tech", "格式不正确");
       bool=false;
     }
     if(supplierMatPro.getTotalWorker()==null){
       model.addAttribute("sale_work", "不能为空");
       bool=false;
     }
     if(supplierMatPro.getTotalWorker()!=null&&!supplierMatPro.getTotalWorker().toString().matches("^[0-9]*$")){
       model.addAttribute("sale_work", "格式不正确");
       bool=false;
     }
     //		List<SupplierCertSell> list = supplierMatPro.getListSupplierCertSells();
     //		if(list==null||list.size()<1){
     //			model.addAttribute("sale_cert", "资质证书不能为空");
     //			bool=false;
     //		}
     return bool;
   }
   //工程信息校验
   public boolean validateEng(HttpServletRequest request,SupplierMatEng supplierMatPro,Model model){
     boolean bool=true;
     if(supplierMatPro.getOrgName()==null||supplierMatPro.getOrgName().length()>12){
       model.addAttribute("eng_org", "不能为空或者字符串过长");
       bool=false;
     }
     if(supplierMatPro.getTotalTech()==null||!supplierMatPro.getTotalTech().toString().matches("^[0-9]*$")){
       model.addAttribute("eng_tech", "不能为空或者不是数字类型");
       bool=false;
     }
     if(supplierMatPro.getTotalGlNormal()==null||!supplierMatPro.getTotalGlNormal().toString().matches("^[0-9]*$")){
       model.addAttribute("eng_normal", "不能为空或者不是数字类型");
       bool=false;
     }

     if(supplierMatPro.getTotalMange()==null||!supplierMatPro.getTotalMange().toString().matches("^[0-9]*$")){
       model.addAttribute("eng_manage", "不能为空或者不是数字类型");
       bool=false;
     }
     if(supplierMatPro.getTotalTechWorker()==null||!supplierMatPro.getTotalTechWorker().toString().matches("^[0-9]*$")){
       model.addAttribute("eng_worker", "不能为空或者不是数字类型");
       bool=false;
     }
     //	   List<SupplierAptitute> aptitutes = supplierMatPro.getListSupplierAptitutes();
     //	   if(aptitutes==null||aptitutes.size()<1){
     //		  model.addAttribute("eng_aptitutes", "请添加资格资质证书信息");
     //		  bool=false;
     //	   }
     //	   List<SupplierCertEng> certEngs = supplierMatPro.getListSupplierCertEngs();
     //	   if(certEngs==null||certEngs.size()<1){
     //		      model.addAttribute("eng_cert", "请添加证书信息");
     //			  bool=false;
     //	   }
     //	   List<SupplierRegPerson> persons = supplierMatPro.getListSupplierRegPersons();
     //	   if(persons==null||persons.size()<1){
     //		   model.addAttribute("eng_persons", "请添加证书信息");
     //		   bool=false;
     //	   }
     return bool;
   }
   //服务信息校验
   public boolean validateServer(HttpServletRequest request,SupplierMatServe supplierMatPro,Model model){
     boolean bool=true;
     if(supplierMatPro.getOrgName()==null||supplierMatPro.getOrgName().length()>12){
       model.addAttribute("fw_org", "不能为空");
       bool=false;
     }
     if(supplierMatPro.getTotalPerson()==null){
       model.addAttribute("fw_person", "不能为空");
       bool=false;
     }
     if(supplierMatPro.getTotalPerson()!=null&&!supplierMatPro.getTotalPerson().toString().matches("^[0-9]*$")){
       model.addAttribute("fw_person", "人员必须是整数");
       bool=false;
     }
     if(supplierMatPro.getTotalMange()==null){
       model.addAttribute("fw_mange", "不能为空");
       bool=false;
     }
     if(supplierMatPro.getTotalMange()!=null&&!supplierMatPro.getTotalMange().toString().matches("^[0-9]*$")){
       model.addAttribute("fw_mange", "人员必须是整数");
       bool=false;
     }
     if(supplierMatPro.getTotalTech()==null){
       model.addAttribute("fw_tech", "不能为空");
       bool=false;
     }
     if(supplierMatPro.getTotalTech()!=null&&!supplierMatPro.getTotalTech().toString().matches("^[0-9]*$")){
       model.addAttribute("fw_tech", "格式不正确");
       bool=false;
     }
     if(supplierMatPro.getTotalWorker()==null){
       model.addAttribute("fw_work", "不能为空");
       bool=false;
     }
     if(supplierMatPro.getTotalWorker()!=null&&!supplierMatPro.getTotalWorker().toString().matches("^[0-9]*$")){
       model.addAttribute("fw_work", "格式不正确");
       bool=false;
     }
     //		List<SupplierCertServe> list = supplierMatPro.getListSupplierCertSes();
     //		if(list==null||list.size()<1){
     //			model.addAttribute("fw_cert", "请添加服务证书信息");
     //			bool=false;
     //		}

     return bool;
   }


   public boolean validateUpload(Model model,String supplierId){
     boolean bool=true;
     SupplierDictionaryData supplierDictionary = dictionaryDataServiceI.getSupplierDictionary();
     //* 近三个月完税凭证
     List<UploadFile> tlist = uploadService.getFilesOther(supplierId, supplierDictionary.getSupplierRegList(), Constant.SUPPLIER_SYS_KEY.toString());
     if(tlist!=null&&tlist.size()<=0){
       bool=false;
       model.addAttribute("err_geglist", "请上传文件!");
     }
     List<UploadFile> plist = uploadService.getFilesOther(supplierId, supplierDictionary.getSupplierPledge(), Constant.SUPPLIER_SYS_KEY.toString());
     if(plist!=null&&plist.size()<=0){
       bool=false;
       model.addAttribute("err_pledge", "请上传文件!");
     }


     return bool;
   }
   /**
    * 
    * @Title: queryByPid
    * @Description: TODO 
    * author: Li Xiaoxiao 
    * @param @param id
    * @param @param model
    * @param @return     
    * @return String     
    * @throws
    */
   @RequestMapping("/category")
   public String  queryByPid(String id,Model model,String sid,Integer page){
     Map<String,Object> map=new HashMap<String,Object>();
     map.put("isDeleted", 0);
     map.put("isPublish", 0);
     map.put("paramStatus", 4);
     List<Category> cateList=new LinkedList<Category>();
     if(id.equals("PRODUCT")){
       map.put("product", "s");
       List<Category> list = categoryService.findCategory(map,page==null?1:page);
       cateList.addAll(list);
     }
     if(id.equals("SALES")){
       map.put("sale", "s");
       List<Category> list = categoryService.findCategory(map,page==null?1:page);
       cateList.addAll(list);
     }  
     if(id.equals("SERVICE")){
       String pid = DictionaryDataUtil.getId(id);
       map.put("parentId", pid);
       List<Category> list = categoryService.findCategory(map,page==null?1:page);
       cateList.addAll(list);
     } 
     if(id.equals("PROJECT")){
       String pid = DictionaryDataUtil.getId(id);
       map.put("parentId", pid);
       List<Category> list = categoryService.findCategory(map,page==null?1:page);
       cateList.addAll(list);
     } 

     //		String[] str = id.split(",");
     //		if(str.length>0){
     //			for(String s:str){
     //				String pid = DictionaryDataUtil.getId(s);
     //				 PageHelper.startPage(page==null?1:page,30);
     //				List<Category> list = categoryService.listByParent(pid);
     //				
     //				cateList.addAll(list);
     //			}
     //		}
     List<SupplierItem> itemList = supplierItemService.getSupplierId(sid);

     List<Category> chose=new LinkedList<Category>();
     List<String> choseId=new LinkedList<String>();
     StringBuffer sb=new StringBuffer();
     String pid = DictionaryDataUtil.getId(id);
     if(itemList!=null&&itemList.size()>0){
       for(SupplierItem s:itemList){
         if(s.getSupplierTypeRelateId().equals(pid)){
           Category category = categoryService.selectByPrimaryKey(s.getCategoryId());
           chose.add(category);
           //					choseId.add(category.getId());
           sb.append(category.getId()).append(",");
         }
       }
     }

     PageInfo<Category> info = new PageInfo<>(cateList);

     String cid = DictionaryDataUtil.getId(id);
     model.addAttribute("info", info);
     model.addAttribute("sid", sid);
     model.addAttribute("code", cid);
     model.addAttribute("chose", chose);
     model.addAttribute("choseId", sb);
     model.addAttribute("id", id);
     return "ses/sms/supplier_register/category";	
   }





   @RequestMapping("login")
   public String login(HttpServletRequest request, Model model,String name) {
     Supplier supp = supplierMapper.queryByName(name);
     //		Supplier supplier = supplierService.get("61a6b3713e754c7c8efdc7d942eb7834");
     Supplier supplier = supplierService.get(supp.getId());

     if(supplier.getAddress()!=null){
       Area area = areaService.listById(supplier.getAddress());
       List<Area> city = areaService.findAreaByParentId(area.getParentId());
       model.addAttribute("city", city);
       model.addAttribute("area", area);
     }
     List<DictionaryData> foregin = DictionaryDataUtil.find(24);

     List<Area> privnce = areaService.findRootArea();
     if(supplier.getListSupplierFinances()!=null&&supplier.getListSupplierFinances().size()<1){
       List<SupplierFinance> list = supplierFinanceService.getYear();
       supplier.setListSupplierFinances(list);
     }else{

       SupplierFinance finance1 = supplierFinanceService.getFinance(supplier.getId(), String.valueOf(oneYear()));
       if(finance1==null){
         SupplierFinance fin1=new SupplierFinance();
         String id = UUID.randomUUID().toString().replaceAll("-", "");
         fin1.setId(id);
         fin1.setYear( String.valueOf(oneYear()));
         supplier.getListSupplierFinances().add(fin1);	
       } 
       SupplierFinance finance2 = supplierFinanceService.getFinance(supplier.getId(), String.valueOf(twoYear()));
       if(finance2==null){
         SupplierFinance fin2=new SupplierFinance();
         String id = UUID.randomUUID().toString().replaceAll("-", "");
         fin2.setId(id);
         fin2.setYear( String.valueOf(twoYear()));
         supplier.getListSupplierFinances().add(fin2);	
       } 
       SupplierFinance finance3 = supplierFinanceService.getFinance(supplier.getId(), String.valueOf(threeYear()));
       if(finance3==null){
         SupplierFinance fin3=new SupplierFinance();
         String id = UUID.randomUUID().toString().replaceAll("-", "");
         fin3.setId(id);
         fin3.setYear( String.valueOf(threeYear()));
         supplier.getListSupplierFinances().add(fin3);	
       } 
     }


     model.addAttribute("company", DictionaryDataUtil.find(17));
     model.addAttribute("currSupplier", supplier);
     model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
     model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
     model.addAttribute("supplierId", supplier.getId());
     model.addAttribute("privnce", privnce);
     SupplierAudit s=new SupplierAudit();
     s.setSupplierId(supplier.getId());;
     List<SupplierAudit> auditLists = supplierAuditService.selectByPrimaryKey(s);
     // 所有的不通过字段的名字
     StringBuffer errorField = new StringBuffer();
     for (SupplierAudit audit : auditLists) {
       errorField.append(audit.getAuditField() + ",");
     }

     model.addAttribute("foregin",foregin);
     model.addAttribute("audit",errorField);
     return "ses/sms/supplier_register/basic_info";
   }

   /**
    * 
    *〈简述〉加载品目树
    *〈详细描述〉
    * @author myc
    * @param id 当前节点Id
    * @param code 编码
    * @param supplierId 供应商Id
    * @param status 状态
    * @return
    */
   @ResponseBody
   @RequestMapping(value="/category_type", produces = "application/json;charset=UTF-8")
   public List<CategoryTree> getCategory(String id,String code,String supplierId, Integer status,String  stype, String shenhe){
     List<CategoryTree> categoryList=new ArrayList<CategoryTree>();
     List<CategoryTree> cateList=new ArrayList<CategoryTree>();
     String typeId ="";
     //初始化跟节点
     if (StringUtils.isEmpty(id)){
       if(StringUtils.isNotBlank(code)) {
         DictionaryData type = DictionaryDataUtil.get(code);
         CategoryTree ct = new CategoryTree();
         if (type != null ) {
           if(type.getCode().equals("PRODUCT")){
             DictionaryData dd = DictionaryDataUtil.get("GOODS");
             ct.setCode("PRODUCT");
             typeId = dd.getId();
           }else if(type.getCode().equals("SALES")){
             DictionaryData dd = DictionaryDataUtil.get("GOODS");
             ct.setCode("SALES");
             typeId = dd.getId();
           }  else {
             ct.setCode(code);
             typeId = type.getId();
           }
         }  

         ct.setName(type.getName());
         ct.setId(typeId);
         List<SupplierItem> s =  supplierItemService.getSupplierIdCategoryId(supplierId,typeId,code);
         if (s != null && s.size() > 0){
           ct.setChecked(true);
         }
         ct.setIsParent("true");
         categoryList.add(ct);
       }
     }
     //加载子集节点
     if (StringUtils.isNotBlank(id)){
       List<Category> child = categoryService.findPublishTree(id,status);
       for(Category c:child){
         CategoryTree ct1 = new CategoryTree();
         ct1.setName(c.getName());
         ct1.setParentId(c.getParentId());
         ct1.setId(c.getId());
         List<SupplierItem> items =  supplierItemService.getSupplierIdCategoryId(supplierId, c.getId(),code);
         if (items != null && items.size() > 0){
           ct1.setChecked(true);
         }
         List<Category> cList = categoryService.findTreeByPid(c.getId());
         if (cList != null && cList.size() > 0){
           ct1.setIsParent("true");
         } else {
           ct1.setIsParent("false");
         }
         categoryList.add(ct1);
       }
     }
     for (CategoryTree catet : categoryList) {
       if(catet.getChecked() == true) {
         cateList.add(catet);
       }
     }
     if ("true".equals(shenhe)){
       return cateList;
     } else {
       return categoryList;
     }
   }

   @RequestMapping("/audit_org")
   public String audit_org(Model model,String name){
     Supplier supp = supplierMapper.queryByName(name);
     Supplier supplier = supplierService.get(supp.getId());
     Orgnization orgnization = orgnizationServiceI.getOrgByPrimaryKey(supplier.getProcurementDepId());
     model.addAttribute("purchaseDep", orgnization);
     return "ses/sms/supplier_register/audit_org";
   }

   public Integer oneYear() {
     //	List<Integer> yearList=new ArrayList<Integer>();

     Calendar cale = Calendar.getInstance();
     int year = cale.get(Calendar.YEAR);
     int year2=year-2;//2014
     return year2;
   }

   public Integer twoYear() {
     //	List<Integer> yearList=new ArrayList<Integer>();

     Calendar cale = Calendar.getInstance();
     int year = cale.get(Calendar.YEAR);
     int year3=year-3;//2013
     return year3;
   }


   public Integer threeYear(){
     Date date=new Date();
     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
     String mont=sdf.format(date).split("-")[1];
     Integer month=Integer.valueOf(mont);
     Calendar cale = Calendar.getInstance();
     int year = cale.get(Calendar.YEAR);
     Integer yearThree=0;
     if(month<6){
       yearThree=year-4;//2012

     }else{
       yearThree=year-1;//2015

     }
     return yearThree;
   }


   @RequestMapping(value="/audit",produces = "text/html;charset=UTF-8")
   @ResponseBody
   public String auditMsg(String id,String fieldName){
     SupplierAudit supplierAudit=new SupplierAudit();
     supplierAudit.setSupplierId(id);
     supplierAudit.setAuditField(fieldName);
     List<SupplierAudit> list = supplierAuditService.selectByPrimaryKey(supplierAudit);

     return JSON.toJSONString(list.get(0));
   }

   public List<String> getAuditFiled(String id){
     List<String> list=new LinkedList<String>();
     SupplierAudit supplierAudit=new SupplierAudit();
     supplierAudit.setSupplierId(id);
     List<SupplierAudit> audit = supplierAuditService.selectByPrimaryKey(supplierAudit);
     for(SupplierAudit s:audit){
       list.add(s.getAuditField());
     }
     return list;
   }


   /**
    * 
    * @Title: contractUp
    * @Description: 品目合同上传
    * author: Li Xiaoxiao 
    * @param @return     
    * @return String     
    * @throws
    */
   @RequestMapping(value="/contract")
   public String contractUp(String supplierId,Model model,String supplierTypeIds,String flag){
     List<ContractBean> contract=new LinkedList<ContractBean>();

     List<ContractBean> saleBean=new LinkedList<ContractBean>();

     List<ContractBean> projectBean=new LinkedList<ContractBean>();
     List<ContractBean> serverBean=new LinkedList<ContractBean>();

     //合同
     String id1 = DictionaryDataUtil.getId("CATEGORY_ONE_YEAR");
     String id2 = DictionaryDataUtil.getId("CATEGORY_TWO_YEAR");
     String id3 = DictionaryDataUtil.getId("CATEGORY_THRE_YEAR");
     //账单
     String id4 = DictionaryDataUtil.getId("CATEGORY_ONE_BIL");
     String id5 = DictionaryDataUtil.getId("CATEGORY_TWO_BIL");
     String id6 = DictionaryDataUtil.getId("CATEGORY_THREE_BIL");
     int count=0;
     StringBuffer sbUp=new StringBuffer("");
     StringBuffer sbShow=new StringBuffer("");
     String[] strs = supplierTypeIds.split(",");
     List<Category> product=new ArrayList<Category>();
     List<Category> sale=new ArrayList<Category>();
     List<Category> project=new ArrayList<Category>();
     List<Category> server=new ArrayList<Category>();
     for(String type:strs){
       if(type.equals("PRODUCT")){
         List<Category> list = supplierItemService.getCategory(supplierId,"PRODUCT");
         product.addAll(list);
       }
       if(type.equals("SALES")){
         List<Category> list = supplierItemService.getCategory(supplierId,"SALES");
         sale.addAll(list);
       }
       if(type.equals("PROJECT")){
         List<Category> list = supplierItemService.getCategory(supplierId,"PROJECT");
         project.addAll(list);
       }
       if(type.equals("SERVICE")){
         List<Category> list = supplierItemService.getCategory(supplierId,"SERVICE");
         server.addAll(list);
       }
     }

     for(Category ca:product){
       ContractBean con=new ContractBean();
       con.setId(ca.getId());
       con.setName(ca.getName());


       sbUp.append("pUp"+count+",");
       sbShow.append("pShow"+count+",");
       con.setOneContract(id1);
       count++;


       sbUp.append("pUp"+count+",");
       sbShow.append("pShow"+count+",");
       con.setTwoContract(id2);
       count++;


       sbUp.append("pUp"+count+",");
       sbShow.append("pShow"+count+",");
       con.setThreeContract(id3);
       count++;


       sbUp.append("pUp"+count+",");
       sbShow.append("pShow"+count+",");
       con.setOneBil(id4);
       count++;


       sbUp.append("pUp"+count+",");
       sbShow.append("pShow"+count+",");
       con.setTwoBil(id5);
       count++;


       sbUp.append("pUp"+count+",");
       sbShow.append("pShow"+count+",");
       con.setTwoBil(id6);
       count++;

       contract.add(con);
     }

     int sales=0;
     for(Category ca:sale){
       ContractBean con=new ContractBean();
       con.setId(ca.getId());
       con.setName(ca.getName());


       sbUp.append("saleUp"+sales+",");
       sbShow.append("saleShow"+sales+",");
       con.setOneContract(id1);
       sales++;


       sbUp.append("saleUp"+sales+",");
       sbShow.append("saleShow"+sales+",");
       con.setTwoContract(id2);
       sales++;


       sbUp.append("saleUp"+sales+",");
       sbShow.append("saleShow"+sales+",");
       con.setThreeContract(id3);
       sales++;


       sbUp.append("saleUp"+sales+",");
       sbShow.append("saleShow"+sales+",");
       con.setOneBil(id4);
       sales++;


       sbUp.append("saleUp"+sales+",");
       sbShow.append("saleShow"+sales+",");
       con.setTwoBil(id5);
       sales++;


       sbUp.append("saleUp"+sales+",");
       sbShow.append("saleShow"+sales+",");
       con.setTwoBil(id6);
       sales++;

       saleBean.add(con);
     }

     int projects=0;
     for(Category ca:project){
       ContractBean con=new ContractBean();
       con.setId(ca.getId());
       con.setName(ca.getName());


       sbUp.append("projectUp"+projects+",");
       sbShow.append("projectShow"+projects+",");
       con.setOneContract(id1);
       projects++;


       sbUp.append("projectUp"+projects+",");
       sbShow.append("projectShow"+projects+",");
       con.setTwoContract(id2);
       projects++;


       sbUp.append("projectUp"+projects+",");
       sbShow.append("projectShow"+projects+",");
       con.setThreeContract(id3);
       projects++;


       sbUp.append("projectUp"+projects+",");
       sbShow.append("projectShow"+projects+",");
       con.setOneBil(id4);
       projects++;


       sbUp.append("projectUp"+projects+",");
       sbShow.append("projectShow"+projects+",");
       con.setTwoBil(id5);
       projects++;


       sbUp.append("projectUp"+projects+",");
       sbShow.append("projectShow"+projects+",");
       con.setTwoBil(id6);
       projects++;

       projectBean.add(con);
     }

     int servers=0;
     for(Category ca:server){
       ContractBean con=new ContractBean();
       con.setId(ca.getId());
       con.setName(ca.getName());


       sbUp.append("serUp"+servers+",");
       sbShow.append("serpShow"+servers+",");
       con.setOneContract(id1);
       servers++;


       sbUp.append("serUp"+servers+",");
       sbShow.append("serpShow"+servers+",");
       con.setTwoContract(id2);
       servers++;


       sbUp.append("serUp"+servers+",");
       sbShow.append("serpShow"+servers+",");
       con.setThreeContract(id3);
       servers++;


       sbUp.append("serUp"+servers+",");
       sbShow.append("serpShow"+servers+",");
       con.setOneBil(id4);
       servers++;


       sbUp.append("serUp"+servers+",");
       sbShow.append("serpShow"+servers+",");
       con.setTwoBil(id5);
       servers++;


       sbUp.append("serUp"+servers+",");
       sbShow.append("serpShow"+servers+",");
       con.setTwoBil(id6);
       servers++;

       serverBean.add(con);
     }


     model.addAttribute("serverBean", serverBean);
     model.addAttribute("projectBean", projectBean);
     model.addAttribute("saleBean", saleBean); 
     model.addAttribute("contract", contract);	
     model.addAttribute("sbUp", sbUp);
     model.addAttribute("sbShow", sbShow);
     List<Integer> years = supplierService.getThressYear();
     model.addAttribute("years", years);
     model.addAttribute("supplierTypeIds", supplierTypeIds);
     model.addAttribute("supplierId", supplierId);

     if(flag.equals("1")){
       Supplier supplier = supplierService.get(supplierId);
       model.addAttribute("currSupplier", supplier);
       return "ses/sms/supplier_register/items";

     }
     return "ses/sms/supplier_register/contract";


   }




   public  void record(String operationInfo, Object obj1,Object obj2,String supplierId) throws Exception {
     if(obj1!=null&&obj2!=null){	
       Class clazz1 = obj1.getClass();
       Field[] fields = clazz1.getDeclaredFields();
       StringBuffer sb=new StringBuffer();
       sb.append("");
       Method m=null;
       Method	m2=null;
       String upperCase=null;
       for(Field f : fields) {
         String str="";
         if(!f.getName().contains("serialVersionUID")){
           upperCase = "get" +f.getName().substring(0,1).toUpperCase()+ f.getName().substring(1);
           m=(Method) obj1.getClass().getMethod(upperCase); 
           m2 =(Method) obj2.getClass().getMethod(upperCase);
           if(m.equals(m2)){
             Object obj3 = m.invoke(obj1);
             Object obj4 = m2.invoke(obj2);
             if(obj3!=null&&obj4!=null){
               if(!obj3.toString().equals(obj4.toString())){
                 str=f.getName()+","+obj3+","+obj4+";";
               } 
             }

             sb.append(str);
           }

         }
       }
       String[] spl= sb.toString().split(";");
       if(spl[0].trim().length()!=0){
         for(String sss:spl){
           SupplierHistory sh=new SupplierHistory();
           String[] ss = sss.split(",");
           String id = UUID.randomUUID().toString().replaceAll("-", "");
           sh.setId(id);
           sh.setSupplierId(supplierId);
           sh.setBeforeField(ss[0]);
           sh.setBeforeContent(ss[1]);
           sh.setAfterContent(ss[2]);
           supplierHistoryService.add(sh);
         }

       }


     }

   }

   /**
    *〈简述〉
    * 判断提交审核后有没有超过45天以及查询初审机构信息
    *〈详细描述〉
    * @author WangHuijie
    * @param userId
    * @return
    * @throws Exception 
    */
   @RequestMapping(value = "validateAuditTime", produces = "application/json;charset=UTF-8")
   @ResponseBody
   public String validateAuditTime(String userId) throws Exception{
     HashMap<String, Object> allInfo = new HashMap<String, Object>();
     // 根据userId查询出Expert
     Supplier supplier = supplierService.selectById(userService.getUserById(userId).getTypeId());   
     Date submitDate = supplier.getAuditDate();
     allInfo.put("submitDate", new SimpleDateFormat("yyyy年MM月dd日").format(submitDate));
     // 判断有没有超过45天
     String isok;
     int betweenDays = expertService.daysBetween(submitDate);
     if (betweenDays > 45) {
       isok = "0";
     } else {
       isok = "1";
     }
     allInfo.put("isok", isok);
     // 查询初审机构信息
     HashMap<String, Object> map = new HashMap<String, Object>();
     map.put("id", supplier.getProcurementDepId());
     map.put("typeName", "1");
     List<PurchaseDep> depList = purchaseOrgnizationService.findPurchaseDepList(map);
     if (depList != null && depList.size() > 0) {
       PurchaseDep purchaseDep = depList.get(0);
       allInfo.put("contact", purchaseDep.getContact() == null ? "暂无" : purchaseDep.getContact());
       allInfo.put("contactTelephone", purchaseDep.getContactTelephone() == null ? "暂无" : purchaseDep.getContactTelephone());
     }
     return JSON.toJSONString(allInfo);
   }

 }
