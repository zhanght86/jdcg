package bss.controller.pms;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import ses.util.PathUtil;
import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.PurchaseRequiredService;
import bss.util.Excel;
import bss.util.ExcelUtil;

import com.github.pagehelper.PageInfo;
/**
 * 
 * @Title: PurcharseRequiredController
 * @Description:  采购需求计划类
 * @author Li Xiaoxiao
 * @date  2016年9月12日,下午1:54:34
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchaser")
public class PurchaseRequiredController extends BaseController{

	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	 
	/**
	 * 
	* @Title: queryPlan
	* @Description: 条件查询分页
	* author: Li Xiaoxiao 
	* @param @param purchaseRequired
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/list")
	public String queryPlan(PurchaseRequired purchaseRequired,Integer page,Model model){
		purchaseRequired.setIsMaster("1");
		List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired,page==null?1:page);
		PageInfo<PurchaseRequired> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("inf", purchaseRequired);
		return "bss/pms/purchaserequird/list";
	}
	/**
	 * 
	* @Title: getById
	* @Description: 根据计划编号查询明细
	* author: Li Xiaoxiao 
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/queryByNo")
	public String getById(String planNo,Model model,String type){
		PurchaseRequired p=new PurchaseRequired();
		p.setPlanNo(planNo.trim());
		List<PurchaseRequired> list = purchaseRequiredService.query(p,0);
		model.addAttribute("list", list);
		
		if(type.equals("1")){
			return "bss/pms/purchaserequird/view";
		}else{
			return "bss/pms/purchaserequird/edit";
		}
		
	}
	
	/**
	 * 
	* @Title: updateById
	* @Description: 根据id修改 
	* author: Li Xiaoxiao 
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/update")
	public String updateById(PurchaseRequiredFormBean list){
		
		if(list!=null){
			if(list.getList()!=null&&list.getList().size()>0){
				for( PurchaseRequired p:list.getList()){
					if( p.getId()!=null){
						PurchaseRequired queryById = purchaseRequiredService.queryById(p.getId());
						Integer s=Integer.valueOf(purchaseRequiredService.queryByNo(p.getPlanNo()))+1;
						queryById.setHistoryStatus(String.valueOf(s));
						purchaseRequiredService.update(queryById);
						if(p.getParentId()!=null){
							p.setParentId(p.getParentId());
						}
						String id = UUID.randomUUID().toString().replaceAll("-", "");
						p.setId(id);
						p.setHistoryStatus("0");
						purchaseRequiredService.add(p);	
					}else{
						String id = UUID.randomUUID().toString().replaceAll("-", "");
						p.setId(id);
						purchaseRequiredService.add(p);	
					}
				
					
				}
			}
		}
//		purchaseRequiredService.update(purchaseRequired);
		return "redirect:list.html";
	}
	/**
	 *   
	 * 
	* @Title: add
	* @Description: 添加跳转页面
	* author: Li Xiaoxiao 
	* @param @return     
	* @return String     
	* @throws  
	 */
	@RequestMapping("/add")
	public String add(Model model,String type) {
		model.addAttribute("type", type);
		return "bss/pms/purchaserequird/add";
	}
	/**
	 *   
	 * 
	* @Title: uploadFile
	* @Description: 导入excel表格数据
	* author: Li Xiaoxiao 
	* @param @return     
	* @return String     
	 * @throws UnsupportedEncodingException 
	* @throws Exception
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public String uploadFile(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response,String type,String planName,String planNo) throws UnsupportedEncodingException{
		response.setContentType("text/xml;charset=UTF-8");  
		User user = (User) request.getSession().getAttribute("loginUser");
		planName = new String(planName.getBytes("iso8859-1"),"UTF-8");
		
		String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = file.getOriginalFilename();  
        if(!fileName.endsWith(".xls")&&!fileName.endsWith(".xlsx")){
         
        	return "ERROR";
        }
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        
	
		List<PurchaseRequired> list=new ArrayList<PurchaseRequired>();
		try {
			list = (List<PurchaseRequired>) ExcelUtil.readExcel(targetFile);
		} catch (Exception e) {
			String str = e.getMessage();
			System.out.println("+++"+str);
			if(str!=null){
				return "exception";
			}
			
		}
		for(int i=0;i<list.size();i++){
			if(i==0){
				PurchaseRequired p = list.get(0);
					String id = UUID.randomUUID().toString().replaceAll("-", "");
					p.setGoodsType(type);
					p.setPlanNo(planNo);
					p.setPlanName(planName);
					p.setId(id);
					p.setPlanType(type);
					p.setHistoryStatus("0");
					p.setIsDelete(0);
					p.setIsMaster("1");
					p.setCreatedAt(new Date());
					p.setUserId(user.getId());
//					p.setOrganization(user.getOrg().getName());
					purchaseRequiredService.add(p);	
			}else{
					PurchaseRequired p = list.get(i);
					String id = UUID.randomUUID().toString().replaceAll("-", "");
					p.setGoodsType(type);
					p.setPlanNo(planNo);
					p.setPlanName(planName);
					p.setId(id);
					p.setPlanType(type);
					p.setHistoryStatus("0");
					p.setIsDelete(0);
					p.setIsMaster("2");
					p.setCreatedAt(new Date());
					p.setUserId(user.getId());
//					p.setOrganization(user.getOrg().getName());
					purchaseRequiredService.add(p);	
			}
		}
		targetFile.delete();
		
		return "success";
	}
	/**
	 * 
	* @Title: addReq
	* @Description: 添加数据
	* author: Li Xiaoxiao 
	* @param @param purchaseRequired
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/adddetail")
	@ResponseBody
	public String addReq(PurchaseRequiredFormBean list,String type,String planNo,String planName,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loginUser");
//		user.get
		if(list!=null){
			if(list.getList()!=null&&list.getList().size()>0){
				for(int i=0;i<list.getList().size();i++){
					if(i==0){
						PurchaseRequired p = list.getList().get(0);
							String id = UUID.randomUUID().toString().replaceAll("-", "");
//							p.setGoodsType(type);
							p.setPlanNo(planNo);
							p.setPlanName(planName);
							if(p.getId()!=null){
								p.setId(id);
							}
							
							p.setPlanType(type);
							p.setHistoryStatus("0");
							p.setIsDelete(0);
							p.setIsMaster("1");
							p.setStatus("1");
							p.setCreatedAt(new Date());
							p.setUserId(user.getId());
//							p.setOrganization(user.getOrg().getName());
							purchaseRequiredService.add(p);	
					}else{
							PurchaseRequired p = list.getList().get(i);
							String id = UUID.randomUUID().toString().replaceAll("-", "");
//							p.setGoodsType(type);
							p.setPlanNo(planNo);
							p.setPlanName(planName);
							if(p.getId()!=null){
								p.setId(id);
							}
							p.setPlanType(type);
							p.setHistoryStatus("0");
							p.setIsDelete(0);
							p.setIsMaster("2");
							p.setStatus("1");
							p.setCreatedAt(new Date());
							p.setUserId(user.getId());
//							p.setOrganization(user.getOrg().getName());
							purchaseRequiredService.add(p);	
					}
				}
			}
	}

		return "redirect:list.html";
	}
	
	
	/**
	 * 
	* @Title: excel
	* @Description: 根据计划编号导出excel表格 
	* author: Li Xiaoxiao 
	* @param @param planNo
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/exports")
	@ResponseBody
	public String excel(HttpServletResponse resp,String planNo){
		
		String headers[]={"序号","需求部门","物资类别及品种名称","规格型号","质量技术标准（技术参数）", "计量单位","采购数量","单位（元）","预算金额（万元）","交货期限","采购方式建议","供应商名称","是否申请办理免税","物资用途（进口）","使用单位（进口）","备注"};
		String attrs[]={"seq","department","platformSeq","goodsName","stand","qualitStand","item","purchaseCount","price","budget","deliverDate","purchaseType","supplier","isFreeTax","goodsUse","useUnit","memo"};
	
		String filedisplay = "明细.xls";
		
		try {
			resp.addHeader("Content-Disposition", "attachment;filename="  + new String(filedisplay.getBytes("gb2312"), "iso8859-1"));
		} catch (UnsupportedEncodingException e1) {
		 
		}
		PurchaseRequired p=new PurchaseRequired();
		p.setPlanNo(planNo.trim());
		List<PurchaseRequired> list = purchaseRequiredService.query(p,0);
		
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		Excel<PurchaseRequired> sheet = new Excel<PurchaseRequired>();
		ServletOutputStream fileOut=null;
		try{
			filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
			sheet.creatAuditSheet(workbook, "ErrInfoChannel sheet", list, headers, attrs);
			fileOut=resp.getOutputStream();
	        workbook.write(fileOut);
		}catch(Exception e){	
		}
        try {
			fileOut.close();
		} catch (IOException e) {e.printStackTrace();
		}
        
		
		return "下载成功";
	}
	/**
	 * 
	* @Title: delete
	* @Description: 逻辑删除数据
	* author: Li Xiaoxiao 
	* @param @param planNo
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String planNo){
		PurchaseRequired p=new PurchaseRequired();
		p.setPlanNo(planNo);
//		List<PurchaseRequired> list = purchaseRequiredService.query(p, 0);
//		if(list.size()>0){
//			
//			for(PurchaseRequired pp:list){
//				pp.setIsDelete(1);
				purchaseRequiredService.delete(planNo);
//			}
//		}
		
		
		return "";
	}
	/**
	 * 
	* @Title: downFile
	* @Description: 下载excel表格模板
	* author: Li Xiaoxiao 
	* @param @param path
	* @param @return     
	* @return String     
	* @throws
	 */
	  @RequestMapping("download")    
	    public ResponseEntity<byte[]> download(HttpServletRequest request,String filename) throws IOException {
//	    	filename = new String(filename.getBytes("iso8859-1"),"UTF-8");
	    	String path = PathUtil.getWebRoot() + "excel/模板.xls";;  
	        File file=new File(path);
	        
	        HttpHeaders headers = new HttpHeaders();    
	        String fileName=new String("模板.xls".getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
	        headers.setContentDispositionFormData("attachment", fileName);   
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
	        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
	                                          headers, HttpStatus.CREATED);    
	    }
	    /**
	     * 
	    * @Title: submit
	    * @Description: 提交
	    * author: Li Xiaoxiao 
	    * @param @return     
	    * @return String     
	    * @throws
	     */
	    @RequestMapping("/submit")
	    public String submit(String planNo){
	    	PurchaseRequired p=new PurchaseRequired();
	    	p.setPlanNo(planNo);
	    	p.setStatus("2");
	    	purchaseRequiredService.updateStatus(p);
//	    	purchaseRequiredService.update(planNo, "2");
	    	return "redirect:list.html";
	    }
	
	    @RequestMapping("/ztree")
	    public String ztree(String type,String planNo,String planName,Model model){
	    	model.addAttribute("type", type);
	    	model.addAttribute("planNo", planNo);
	    	model.addAttribute("planName", planName);
	    	return "bss/pms/purchaserequird/ztreeadd";
	    }
	    /**
	     * 
	    * @Title: id
	    * @Description: 生成id
	    * author: Li Xiaoxiao 
	    * @param @return     
	    * @return String     
	    * @throws
	     */
	    @RequestMapping("/getId")
	    @ResponseBody
	    public String id(){
	    	String id = UUID.randomUUID().toString().replaceAll("-", "");
	    	
	    	return id;
	    }
}
