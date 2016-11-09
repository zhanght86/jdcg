package bss.controller.ppms;

import iss.model.ps.Article;
import iss.model.ps.ArticleType;
import iss.service.ps.ArticleAttachmentsService;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.main.CnUpperCaser;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.util.AjaxJsonData;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierQuoteService;
import ses.service.sms.SupplierService;
import ses.util.FtpUtil;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectAttachments;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ScoreModel;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageFirstAudit;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectAttachmentsService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ScoreModelService;
import bss.service.prms.FirstAuditService;
import bss.service.prms.PackageFirstAuditService;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.DownloadService;
import common.service.UploadService;


/**
 * 版权：(C) 版权所有 
 * 公开招标实施
 * 公开招标实施中的请求控制
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping("/open_bidding")
public class OpenBiddingController {
    
    /**
     * @Fields projectService : 引用项目业务实现接口
     */
    @Autowired
    private ProjectService projectService;
    
    /**
     * @Fields articelService :  引用文章业务实现接口
     */
    @Autowired
    private ArticleService articelService;
    
    /**
     * @Fields articelTypeService : 引用文章类型业务实现接口
     */
    @Autowired
    private ArticleTypeService articelTypeService;
    
    /**
     * @Fields attachmentsService : 引用文章附件业务实现接口
     */
    @Autowired
    private ArticleAttachmentsService attachmentsService;
    
    /**
     * @Fields detailService : 引用项目详细业务接口
     */
    @Autowired
    private ProjectDetailService detailService;
    
    /**
     * @Fields packageService : 引用分包业务逻辑接口
     */
    @Autowired
    private PackageService packageService;
    
    @Autowired
    private PackageFirstAuditService packageFirstAuditService;
    
    @Autowired 
    private DictionaryDataServiceI dataService;
    
    /**
     * @Fields projectAttachmentsService : 引用项目附件业务实现接口
     */
    @Autowired
    private ProjectAttachmentsService projectAttachmentsService;
    
    /**
     * @Fields auditService : 引用初审项业务接口
     */
    @Autowired
    private FirstAuditService auditService;
    
    /**
     * @Fields scoreModelService : 引用模型业务接口
     */
    @Autowired
    private ScoreModelService scoreModelService;
    
    @Autowired
    private UploadService uploadService;
    
    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private SupplierQuoteService supplierQuoteService;
    
    @Autowired
    private DownloadService downloadService;
    
    /**
     * @Fields jsonData : ajax返回数据封装类
     */
    private AjaxJsonData jsonData = new AjaxJsonData();
    
    
    /**
     *〈简述〉 进入招标文件页面
     *〈详细描述〉
     * @author Ye MaoLin
     * @param request
     * @param id 项目id
     * @param model
     * @param response
     * @return
     */
    @RequestMapping("/bidFile")
    public String bidFile(HttpServletRequest request, String id, Model model, HttpServletResponse response){
        Project project = projectService.selectById(id);
        
        //判断是否上传招标文件
        DictionaryData dictionaryData = new DictionaryData();
        dictionaryData.setCode("zbwj");
        List<DictionaryData> dds = dataService.find(dictionaryData);
        String typeId = "";
        if(dds != null && dds.size() > 0){
            typeId = dds.get(0).getId();
        }
        List<UploadFile> files = uploadService.getFilesOther(id, typeId, Constant.TENDER_SYS_KEY+"");
        if (files != null && files.size() > 0){
            model.addAttribute("fileId", files.get(0).getId());
        } else {
            model.addAttribute("fileId", "0");
        }
        model.addAttribute("project", project);
        return "bss/ppms/open_bidding/bid_file/add_file";
    }
    
    /**
     *〈简述〉下载附件
     *〈详细描述〉
     * @author Ye MaoLin
     * @param request
     * @param fileId 附件id
     * @param response
     */
    @RequestMapping("/loadFile")
    public void loadFile(HttpServletRequest request, String fileId, HttpServletResponse response){
        downloadService.downloadOther(request, response, fileId, Constant.TENDER_SYS_KEY+"");
    }
    
    /**
     * Description: 跳转到招标公告页面
     * 
     * @author Ye MaoLin
     * @version 2016-10-14
     * @return String
     * @exception IOException
     */
    @RequestMapping("/bidNotice")
    public String bidNotice(String projectId, Model model){
        Article article = new Article();
        article.setProjectId(projectId);
        ArticleType at = articelTypeService.selectTypeByPrimaryKey("7");
        article.setArticleType(at);
        List<Article> articles = articelService.selectArticleByProjectId(article);
        if (articles != null && articles.size() > 0){
            if (articles.get(0).getPublishedAt() != null && articles.get(0).getPublishedName() != null && !"".equals(articles.get(0).getPublishedName())){
                model.addAttribute("article", articles.get(0));
                model.addAttribute("projectId", projectId);
                return "bss/ppms/open_bidding/bid_notice/view";
            } else {
                model.addAttribute("content", articles.get(0).getContent());
                model.addAttribute("name", articles.get(0).getName());
                model.addAttribute("articleId", articles.get(0).getId());
                model.addAttribute("range", articles.get(0).getRange());
                model.addAttribute("projectId", projectId);
                return "bss/ppms/open_bidding/bid_notice/add";
            }
        } else {
            model.addAttribute("projectId", projectId);
            return "bss/ppms/open_bidding/bid_notice/add";
        }
    }
    
    public String printViewBack(String projectId, Model model, String name, String content){
        model.addAttribute("name", name);
        model.addAttribute("content", content);
        model.addAttribute("projectId", projectId);
        return "bss/ppms/open_bidding/bid_notice/add";
    }
    
    @RequestMapping("/showTime")
    @ResponseBody
    public long showTime(String projectId){
    	Project project=projectService.selectById(projectId);
    	//开标时间
    	long bidDate=0;
    	if(project.getBidDate()==null){
    	}else{
    		bidDate=project.getBidDate().getTime();
    	}
    	long nowDate=new Date().getTime();
    	long date=bidDate-nowDate;
    	return date;
    }
    
    /**
     * Description: 保存招标公告
     * 
     * @author Ye MaoLin
     * @version 2016-10-18
     * @param request
     * @param article
     * @return AjaxJsonData
     * @throws Exception
     * @exception IOException
     */
    @RequestMapping("saveBidNotice")
    @ResponseBody 
    public AjaxJsonData saveBidNotice(HttpServletRequest request, Article article) throws Exception{
        try {
            User currUser = (User) request.getSession().getAttribute("loginUser");
            article.setUser(currUser);
            Timestamp ts = new Timestamp(new Date().getTime());
            article.setCreatedAt(ts);
            Timestamp ts1 = new Timestamp(new Date().getTime());
            article.setUpdatedAt(ts1);
            ArticleType at = articelTypeService.selectTypeByPrimaryKey("7");
            article.setArticleType(at);
            article.setStatus(0);
            String[] ranges = request.getParameterValues("ranges");
            if (ranges != null && ranges.length > 0){
                if (ranges.length > 1){
                    article.setRange(2);
              } else {
                  for(int i=0;i<ranges.length;i++){
                      article.setRange(Integer.valueOf(ranges[i]));
                  }
              }
           }
            if (article.getId() != null && !"".equals(article.getId())){
                articelService.update(article);
            } else {
                articelService.addArticle(article);
            }
            jsonData.setMessage("保存成功");
            jsonData.setObj(article);
            return jsonData;
        } catch (Exception e) {
            throw new Exception("招标文件保存失败！");
        }
    }
    
    /**
     * Description: 打印预览
     * 
     * @author Ye MaoLin
     * @version 2016-10-17
     * @param request
     * @param model
     * @return String
     * @exception IOException
     */
    @RequestMapping("/printView")
    public String printView(String content, String name, String projectId, HttpServletRequest request, Model model){
        model.addAttribute("content", content);
        model.addAttribute("name", name);
        model.addAttribute("projectId", projectId);
        return "bss/ppms/open_bidding/bid_notice/print_view";
    }
    
    
    /**
     *〈简述〉
     *〈详细描述〉
     * @author yggc
     * @param request
     * @param resp
     * @return
     * @throws IOException
     */
    @RequestMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse resp) throws IOException{
        String articleName = "招标公告";
        String name = request.getParameter("name");
        if(name != null && !"".equals(name)){
            articleName = name;
        }
        String content = request.getParameter("content");
        resp.reset();  
        resp.setContentType("application/vnd.ms-word;charset=UTF-8"); 
        String guessCharset = "gb2312";  
        String strFileName = new String(articleName.getBytes(guessCharset), "ISO8859-1"); 
        resp.setHeader("Content-Disposition", "attachment;filename=" + strFileName + ".doc"); 
        OutputStream os = resp.getOutputStream();  
        os.write(content.getBytes(), 0, content.getBytes().length);  
        os.flush();  
        os.close(); 
    //      byte[] bs = content.getBytes();
    //      HttpHeaders headers = new HttpHeaders();
    //      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
    //      headers.setContentDispositionFormData("content", new String(articleName.getBytes("UTF-8"), "iso-8859-1"));  
    //      return (new ResponseEntity<byte[]>(bs, headers, HttpStatus.CREATED));  
    }
    
    @RequestMapping("/publishEdit")
    public String publishEdit(Model model, String id){
        model.addAttribute("articleId", id);
        return "bss/ppms/open_bidding/bid_notice/publish_edit";
    }
    
    /**
     * Description: 发布
     * 
     * @author Ye MaoLin
     * @version 2016-10-17
     * @param request
     * @return String
     * @throws Exception 
     */
    @RequestMapping("/publish") 
    public String publish(@RequestParam("files") MultipartFile[] files, HttpServletRequest request, HttpServletResponse response, String id) throws Exception{
        try {
            Article article = articelService.selectArticleById(id);
            Timestamp ts = new Timestamp(new Date().getTime());
            article.setPublishedAt(ts);
            User user = (User) request.getSession().getAttribute("loginUser");
            article.setPublishedName(user.getRelName());
            article.setStatus(2);
            articelService.update(article);
            //上传审批文件
            uploadFile(article, request, files);
            jsonData.setMessage("发布成功");
            return "redirect:bidNotice.html?projectId=" + article.getProjectId();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    
    /**
     * 上传附件
     * 上传项目类型附件
     * @author yggc
     * @param article
     * @param request
     * @param files
     */
    public void uploadFile(Article article, HttpServletRequest request, MultipartFile[] files){
        if (files != null){
            for (int i = 0; i < files.length; i++){
                try {
                    String url = FtpUtil.upload2("bidNotice", files[i]);
                    //截取文件名
                    String filename = url.substring(url.lastIndexOf("/") + 1);
                    //截取文件路径
                    String path = url.substring(0, url.lastIndexOf("/") + 1).replace("\\", "/");
                    ProjectAttachments attachment = new ProjectAttachments();
                    Project project = projectService.selectById(article.getProjectId());
                    attachment.setProject(project);
                    attachment.setFileName(filename);
                    attachment.setCreatedAt(new Date());
                    attachment.setUpdatedAt(new Date());
                    attachment.setContentType(files[i].getContentType());
                    attachment.setFileSize((float) files[i].getSize());
                    attachment.setKind(3);
                    attachment.setAttachmentPath(path);
                    attachment.setIsDeleted(0);
                    projectAttachmentsService.save(attachment);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     *〈简述〉保存招标文件到服务器
     *〈详细描述〉
     * @author Ye MaoLin
     * @param req
     * @param projectId 项目id
     * @throws IOException
     */
    @RequestMapping("/saveBidFile")
    public void saveBidFile(HttpServletRequest req, String projectId, Model model) throws IOException{
        String result = "保存失败";
        DictionaryData dd = new DictionaryData();
        dd.setCode("zbwj");
        List<DictionaryData> dds= dataService.find(dd);
        //判断该项目是否上传过招标文件
        String typeId = "";
        if(dds != null && dds.size() > 0){
            typeId = dds.get(0).getId();
            List<UploadFile> files = uploadService.getFilesOther(projectId, typeId, Constant.TENDER_SYS_KEY+"");
            if (files != null && files.size() > 0){
                //删除 ,表中数据假删除
                uploadService.updateFileOther(files.get(0).getId(), Constant.TENDER_SYS_KEY+"");
                result = uploadService.saveOnlineFile(req, projectId, typeId, Constant.TENDER_SYS_KEY+"");
            } else {
                result = uploadService.saveOnlineFile(req, projectId, typeId, Constant.TENDER_SYS_KEY+"");
            }
        }
        System.out.println(result);
    }
    
    /**
     *〈简述〉
     *〈详细描述〉
     * @author yggc
     * @param projectId
     * @param model
     * @return
     */
    @RequestMapping("/firstAduitView")
    public String firstAduitView(String projectId, Model model ){
        try {
            //初审项信息
            List<FirstAudit> list = auditService.getListByProjectId(projectId);
            model.addAttribute("list", list);
            model.addAttribute("projectId", projectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "bss/ppms/open_bidding/bid_file/bid_file_view";
    }
    
    /**
     *〈简述〉
     *〈详细描述〉
     * @author yggc
     * @param projectId
     * @param flag
     * @param model
     * @return
     */
    @RequestMapping("/packageFirstAuditView")
    public String packageFirstAuditView(String projectId, String flag, Model model){
        try {
            //项目分包信息
            HashMap<String,Object> pack = new HashMap<String,Object>();
            pack.put("projectId", projectId);
            List<Packages> packList = packageService.findPackageById(pack);
            if(packList.size()==0){
                Packages pg = new Packages();
                pg.setName("第一包"); 
                pg.setProjectId(projectId);
                packageService.insertSelective(pg);
            }
            List<Packages> packages = packageService.findPackageById(pack);
            Map<String,Object> list = new HashMap<String,Object>();
            //关联表集合
            List<PackageFirstAudit> idList = new ArrayList<>();
            Map<String,Object> mapSearch = new HashMap<String,Object>(); 
            for(Packages ps:packages){
                list.put("pack"+ps.getId(),ps);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("packageId", ps.getId());
                List<ProjectDetail> detailList = detailService.selectById(map);
                ps.setProjectDetails(detailList);
                //设置查询条件
                mapSearch.put("projectId", projectId);
                mapSearch.put("packageId", ps.getId());
                List<PackageFirstAudit> selectList = packageFirstAuditService.selectList(mapSearch);
                idList.addAll(selectList);
            }
            model.addAttribute("idList",idList);
            model.addAttribute("packageList", packages);
            Project project = projectService.selectById(projectId);
            model.addAttribute("project", project);
            //初审项信息
            List<FirstAudit> list2 = auditService.getListByProjectId(projectId);
            model.addAttribute("list", list2);
            model.addAttribute("projectId", projectId);
            model.addAttribute("flag", flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "bss/ppms/open_bidding/bid_file/package_first_audit_view";
    }
    
    /**
     *〈简述〉将初审项、初审项关联、详细评审、招标文件改为确认状态
     *〈详细描述〉
     * @author Ye MaoLin
     * @param response
     * @param projectId 项目id
     * @throws Exception
     */
    @RequestMapping("/confirmOk")
    @ResponseBody 
    public void confirmOk(HttpServletResponse response, String projectId) throws Exception{
        try {
            //确认初审项
            List<FirstAudit> firstAudits = auditService.getListByProjectId(projectId);
            for (FirstAudit firstAudit : firstAudits) {
                firstAudit.setIsConfirm((short)1);
                auditService.update(firstAudit);
            }
            //确认初审项关联
            PackageFirstAudit packageFirstAudit = new PackageFirstAudit();
            packageFirstAudit.setProjectId(projectId);
            packageFirstAudit.setIsConfirm((short)1);
            packageFirstAuditService.update(packageFirstAudit);
            
            //确认评分办法
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("projectId", projectId);
            List<Packages> packagesLsit = packageService.findPackageById(map);
            if (packagesLsit != null && packagesLsit.size() > 0){
                for (Packages packages : packagesLsit) {
                    ScoreModel scoreModel = new ScoreModel();
                    scoreModel.setPackageId(packages.getId());
                    List<ScoreModel> scoreModels = scoreModelService.findListByScoreModel(scoreModel);
                    if(scoreModels.size() > 0){
                        for (ScoreModel scoreModel2 : scoreModels) {
                            scoreModel2.setStatus("1");
                            scoreModelService.updateScoreModel(scoreModel2);
                        }
                    }
                }
            }
            String msg = "确认成功";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
                    .print("{\"success\": " + true + ", \"msg\": \"" + msg
                            + "\"}");
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            response.getWriter().close();
        }
        
    }
    
    /**
     * @Title: changbiao
     * @author Song Biaowei
     * @date 2016-11-7 下午8:38:08  
     * @Description: 唱标
     * @param @param projectId
     * @param @param supplierStr
     * @param @param model
     * @param @return      
     * @return String
     */
    @RequestMapping("/changbiao")
    public String changbiao(String projectId, Model model ){
       //项目信息
    	Project project=projectService.selectById(projectId);
        List<Supplier> listSupplier=supplierService.selectSupplierByProjectId(projectId);
        String supplierStr="";
        for(Supplier sup:listSupplier){
        	supplierStr+=sup.getId()+",";
        }
       //参与项目的所有供应商
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        map.put("purchaseType", "公开招标");
        List<ProjectDetail> listPd=detailService.selectByCondition(map,null);
        //每个供应商的报价明细产品
        List<List<Quote>> listQuoteList=new ArrayList<List<Quote>>();
        //暂时测试  等到有数据的时候我就删掉
        //supplierStr="8BE39E5BF23846EC93EED74F57ACF1F4,90F6C6A8544C421EB3DF67ED51185D7C";
        List<String> listsupplierId=Arrays.asList(supplierStr.split(","));
        if(listsupplierId.get(0).length()>30){
	        for(String str:listsupplierId){
	            Quote quote=new Quote();
	            quote.setSupplierId(str);
	            List<Quote> listQuote=supplierQuoteService.selectQuoteHistoryList(quote);
	            BigDecimal totalMoney=new BigDecimal(0);
	            for(Quote q: listQuote){
	            	totalMoney=totalMoney.add(q.getTotal());
	            	q.setTotalMoney(totalMoney);
	            	q.setTotalMoneyNames(new CnUpperCaser(totalMoney.toString()).getCnString());
	            }
	            listQuoteList.add(listQuote);
	        }
        }
        model.addAttribute("listSupplier", listSupplier);
        model.addAttribute("listPd", listPd);
        model.addAttribute("listQuoteList", listQuoteList);
        model.addAttribute("project", project);
        return "bss/ppms/open_bidding/bid_file/changbiao";
    }
}
