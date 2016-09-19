package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.model.bms.User;
import ses.model.oms.PurchaseDep;
import ses.model.sms.ImportSupplierAud;
import ses.model.sms.ImportSupplierWithBLOBs;
import ses.service.bms.UserServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.ImportSupplierAudService;
import ses.service.sms.ImportSupplierService;
import ses.service.sms.SupplierAgentsService;
import ses.util.Encrypt;
import ses.util.WfUtil;
import ses.util.WordUtil;

import com.github.pagehelper.PageInfo;

/**
 * @Title: ImportSupplierController
 * @Description: 进口供应商注册审核控制层
 * @author: Song Biaowei
 * @date: 2016-9-7下午6:09:03
 */
@Controller
@Scope("prototype")
@RequestMapping("/importSupplier")
public class ImportSupplierController {
	@Autowired
	private ImportSupplierService importSupplierService;
	@Autowired
	private UserServiceI userService;
	@Autowired
	private ImportSupplierAudService importSupplierAudService;
	@Autowired
	private SupplierAgentsService supplierAgentService;
	@Autowired
	private PurchaseOrgnizationServiceI poService;

	/**
	* @Title: beforeRegister
	* @author Song Biaowei
	* @date 2016-9-6 上午11:31:17  
	* @Description:点击进口供应商注册 
	* @param @return      
	* @return String
	 */
	@RequestMapping("registerStart")
	public String registerStart(){
		return "ses/sms/import_supplier/register_start";
	}
	
	/**
	 * @Title: register
	 * @author Song Biaowei
	 * @date 2016-9-9 下午5:15:47  
	 * @Description:注册第一步 
	 * @param @param user
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("register")
	public String register(ImportSupplierWithBLOBs is,HttpServletRequest request,Model model){
		//保存基本信息返回 id作为外键保存到user用户表里面去
		importSupplierService.register(is);
		User user=new User();
		String psw=Encrypt.md5AndSha(is.getLoginName()+is.getPassword());
		user.setLoginName(is.getLoginName());
		user.setPassword(psw);
		user.setMobile(is.getMobile());
		user.setTypeId(is.getId());
		user.setTypeName(6);
		userService.save(user, null);
		//待到页面，保存基本信息的时候是 修改动作
		model.addAttribute("id", is.getId());
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<PurchaseDep> findPurchaseDepList = poService.findPurchaseDepList(map);
		model.addAttribute("findPurchaseDepList", findPurchaseDepList);
		return "ses/sms/import_supplier/register";
	}
	
	/**
	 * @Title: registerEnd
	 * @author Song Biaowei
	 * @date 2016-9-8 上午10:25:06  
	 * @Description: 注册最后一步
	 * @param @param is
	 * @param @return      
	 * @return String
	 * @throws IOException 
	 */
	@RequestMapping("registerEnd")
	public String registerEnd(ImportSupplierWithBLOBs is,@RequestParam("files") MultipartFile[] files,HttpServletRequest request) throws IOException{
		if(files!=null && files.length>0){
			 for(MultipartFile myfile : files){  
		            if(myfile.isEmpty()){  
		            	
		            }else{  
		                String filename = myfile.getOriginalFilename();
		                String uuid = WfUtil.createUUID();
		                filename=uuid+filename;
		                String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");  
		                FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, filename)); 
		                is.setRegList(realPath+"/"+filename); 
		            }  
		        }  
			}
		is.setStatus((short)0);
		is.setCreatedAt(new Date());
		importSupplierService.updateRegisterInfo(is);
		/*SupplierAgents sa=new SupplierAgents();
		//自己的id
		sa.setOperatorId("");
		//代办人id
		sa.setUsersId("");
		//待办类型0 未审核 1 已审核 2 审核中
		sa.setUndoType((short)0);
		//标题
		sa.setTitle("");
		//逻辑删除 0未删除 1已删除
		sa.setIsDeleted((short)0);
		sa.setCreatedAt(new Date());
		supplierAgentService.insert(sa);*/
		return "redirect:daiban.html";
	}
	
	/**
	 * @Title: updateRegister
	 * @author Song Biaowei
	 * @date 2016-9-12 下午2:40:57  
	 * @Description: 审核退回后修改 
	 * @param @param is
	 * @param @param files
	 * @param @param request
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("updateRegister")
	public String updateRegister(ImportSupplierWithBLOBs is,Model model,HttpServletRequest request){
		String id=(String) request.getSession().getAttribute("importSupplierId");
		is.setId(id);
		ImportSupplierWithBLOBs importSupplierWithBLOBs = importSupplierService.selectByPrimaryKey(is);
		model.addAttribute("is", importSupplierWithBLOBs);	
		//审核理由：id可以从登录信息里面去里面取
		ImportSupplierAud isa=importSupplierAudService.findById(id);
		model.addAttribute("isa", isa);	
		return "ses/sms/import_supplier/register";
	}
	
	/**
	 * @Title: toUpdateRegister
	 * @author Song Biaowei
	 * @date 2016-9-12 下午3:50:46  
	 * @Description: 退回修改后保存
	 * @param @param is
	 * @param @param files
	 * @param @param request
	 * @param @return
	 * @param @throws IOException      
	 * @return String
	 */
	@RequestMapping("toUpdateRegister")
	public String toUpdateRegister(ImportSupplierWithBLOBs is,@RequestParam("files") MultipartFile[] files,HttpServletRequest request) throws IOException{
		if(files!=null && files.length>0){
			 for(MultipartFile myfile : files){  
		            if(myfile.isEmpty()){  
		            	
		            }else{  
		                String filename = myfile.getOriginalFilename();
		                String uuid = WfUtil.createUUID();
		                filename=uuid+filename;
		                String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");  
		                FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, filename));  
		                is.setRegList(realPath+"/"+filename);
		            }  
		        }  
			}
		is.setStatus((short)0);
		is.setCreatedAt(new Date());
		importSupplierService.updateRegisterInfo(is);
		/*SupplierAgents sa=new SupplierAgents();
		//自己的id
		sa.setOperatorId("");
		//代办人id
		sa.setUsersId("");
		//待办类型0 未审核 1 已审核 2 审核中
		sa.setUndoType((short)0);
		//标题
		sa.setTitle("");
		//逻辑删除 0未删除 1已删除
		sa.setIsDeleted((short)0);
		sa.setCreatedAt(new Date());
		supplierAgentService.insert(sa);*/
		return "redirect:daiban.html";
	}

	/**
	 * @Title: checkLoginName
	 * @author Song Biaowei
	 * @date 2016-9-9 上午9:04:40  
	 * @Description: 验证用户名
	 * @param @param is
	 * @param @return
	 * @param @throws Exception      
	 * @return boolean
	 */
	@RequestMapping("checkLoginName")
	@ResponseBody
	public boolean checkLoginName(ImportSupplierWithBLOBs is) throws Exception {
		List<ImportSupplierWithBLOBs> isList=importSupplierService.selectByFsInfo(is,1);
		boolean flag=false;
		if(isList!=null&&isList.size()==0){
			flag =true;
		}
		return flag;
	}
	
	/**
	 * @Title: checkMobile
	 * @author Song Biaowei
	 * @date 2016-9-9 上午9:04:53  
	 * @Description: 验证电话号码 
	 * @param @param is
	 * @param @return
	 * @param @throws Exception      
	 * @return boolean
	 */
	@RequestMapping("checkMobile")
	@ResponseBody
	public boolean checkMobile(ImportSupplierWithBLOBs is) throws Exception {
		List<ImportSupplierWithBLOBs> isList=importSupplierService.selectByFsInfo(is,1);
		boolean flag=false;
		if(isList!=null&&isList.size()==0){
			flag =true;
		}
		return flag;
	}
	
	/**
	* @Title: daiban
	* @author Song Biaowei
	* @date 2016-9-6 上午11:34:12  
	* @Description: 供应商审核点击我的待办
	* @param @param is
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("daiban")
	public String daiban(ImportSupplierWithBLOBs is,Model model){
		//未审核 就等于初审
		is.setStatus((short)0);
		int weishenhe=importSupplierService.getCount(is);
		//审核中 就等于 复审
		is.setStatus((short)1);
		int fushen=importSupplierService.getCount(is);
		//审核通过
		is.setStatus((short)2);
		int yishenhe=importSupplierService.getCount(is);
		model.addAttribute("weishenhe", weishenhe);
		model.addAttribute("shenhezhong",fushen);
		model.addAttribute("yishenhe", yishenhe);
		return "ses/sms/import_supplier/todos";
	}
	
	/**
	* @Title: daibanList
	* @author Song Biaowei
	* @date 2016-9-6 上午11:35:35  
	* @Description: 点击我的待办里面的选项进入的页面
	* @param @param is
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("auditList")
	public String daibanList(ImportSupplierWithBLOBs is,Integer page,Model model,String supName,String supType,Short status,HttpServletRequest request){
		if(supName!=null&&!supName.equals("")){
			is.setName(supName);
		}
		if(supType!=null&&!supType.equals("")){
			is.setSupplierType(supType);
		}
		if(status!=null){
			request.getSession().setAttribute("status", status);
		}
		Short status1=(Short)request.getSession().getAttribute("status");
		//如果不等于空就不用从session里面取
		if(status!=null){
			is.setStatus(status);
		}else{
			is.setStatus(status1);
		}
		List<ImportSupplierWithBLOBs> isList=importSupplierService.selectByFsInfo(is,page==null?1:page);
		request.setAttribute("isList", new PageInfo<>(isList));
		model.addAttribute("name", is.getName());
		model.addAttribute("supplierType", is.getSupplierType());
		return "ses/sms/import_supplier/audit_list";
	}
	
	
	
	/**
	* @Title: auditShow
	* @author Song Biaowei
	* @date 2016-9-6 上午11:37:14  
	* @Description: 点击初审 复审进入的方法 
	* @param @param is
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("audit")
	public String auditShow(ImportSupplierWithBLOBs is,Model model){
		ImportSupplierWithBLOBs importSupplierWithBLOBs = importSupplierService.selectByPrimaryKey(is);
		model.addAttribute("is", importSupplierWithBLOBs);
		//给待办删除因为审核完毕
		
		return "ses/sms/import_supplier/first_audit";
	}
	
	/**
	 * @Title: saveReason
	 * @author Song Biaowei
	 * @date 2016-9-12 上午9:19:58  
	 * @Description: 初审进口供应商注册
	 * @param @param isa
	 * @param @param is
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("auditReason")
	public String saveReason(ImportSupplierAud isa,ImportSupplierWithBLOBs is,String sfiId){
		is.setId(sfiId);
		//初审后改变状态
		ImportSupplierWithBLOBs importSupplierWithBLOBs = importSupplierService.selectByPrimaryKey(is);
		importSupplierWithBLOBs.setStatus(is.getStatus());
		importSupplierService.updateRegisterInfo(importSupplierWithBLOBs);
		//给审核不通过的理由存到表里
		isa.setImportSupplierId(importSupplierWithBLOBs.getId());
		if(is.getStatus()==1){
			importSupplierAudService.register(isa);
		}else if(is.getStatus()==4){
			importSupplierAudService.updateRegisterInfo(isa);
		}
		
		//初审复审需要判断
		/*SupplierAgents sa=new SupplierAgents();
		//自己的id
		sa.setOperatorId("");
		//代办人id
		sa.setUsersId("");
		//待办类型0 未审核 1 已审核 2 审核中
		sa.setUndoType((short)0);
		//标题
		sa.setTitle("");
		//逻辑删除 0未删除 1已删除
		sa.setIsDeleted((short)0);
		sa.setCreatedAt(new Date());
		supplierAgentService.insert(sa);*/
		return "redirect:daiban.html";
	}
	
	@RequestMapping("dayin")
	public ResponseEntity<byte[]> guidang(ImportSupplierAud cmt,HttpServletRequest request) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//文件名称
		String fileName =new String(("进口供应商注册信息表.docx").getBytes("UTF-8"), "UTF-8");
		String name = WordUtil.createWord(dataMap, "importSupplier.ftl",fileName, request);
		String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/");
		File file=new File(filePath+"/"+name);  
        HttpHeaders headers = new HttpHeaders(); 
        String downFileName=new String("进口供应商注册信息表.doc".getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
        headers.setContentDispositionFormData("attachment", downFileName);   
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED); 
        file.delete();
        return entity;
	} 
	
	@RequestMapping("highmaps")
	public String highmaps(Model model){
		StringBuffer sb = new StringBuffer("");
		Map<String,String> myMap= new HashMap<String,String>(40);
		myMap.put("吉林省","cn-jl");        
		myMap.put("天津市","cn-tj");        
		myMap.put("安徽省","cn-ah");        
		myMap.put("山东省","cn-sd");        
		myMap.put("山西省","cn-sx");        
		myMap.put("新疆维吾尔自治区","cn-xj");
		myMap.put("河北省","cn-hb");        
		myMap.put("河南省","cn-he");        
		myMap.put("湖南省","cn-hn");        
		myMap.put("甘肃省","cn-gs");        
		myMap.put("福建省","cn-fj");        
		myMap.put("贵州省","cn-gz");        
		myMap.put("重庆市","cn-cq");        
		myMap.put("江苏省","cn-js");        
		myMap.put("湖北省","cn-hu");        
		myMap.put("内蒙古自治区","cn-nm");  
		myMap.put("广西壮族自治区","cn-gx"); 
		myMap.put("黑龙江省","cn-hl");      
		myMap.put("云南省","cn-yn");        
		myMap.put("辽宁省","cn-ln");        
		myMap.put("香港特别行政区","cn-6668"); 
		myMap.put("浙江省","cn-zj");        
		myMap.put("上海市","cn-sh");        
		myMap.put("北京市","cn-bj");        
		myMap.put("广东省","cn-gd");        
		myMap.put("澳门特别行政区","cn-3681"); 
		myMap.put("西藏自治区","cn-xz");    
		myMap.put("陕西省","cn-sa");        
		myMap.put("四川省","cn-sc");        
		myMap.put("海南省","cn-ha");        
		myMap.put("宁夏回族自治区","cn-nx"); 
		myMap.put("青海省","cn-qh");        
		myMap.put("江西省","cn-jx");        
		myMap.put("台湾省","tw-tw");        
		
		//  {"hc-key": "cn-sh","value": 0},
		Random random = new Random();
		String value="";
		for (Object o : myMap.keySet()) { 
			value= random.nextInt(100)+1+"" ;
			sb.append("{'hc-key':'").
			append(myMap.get(o)).
			append("','value':").
			append(value).
			append("},").append("\n");
			;
			}
		
		String highMapStr=sb.deleteCharAt(sb.length()-1).toString();
		model.addAttribute("data", highMapStr);
		return "ses/sms/supplier_query/all_supplier";
	}
}
