/**
 * 
 */
package bss.controller.ppms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;

import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.service.ppms.SaleTenderService;


import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierExtRelate;
import ses.service.sms.SupplierExtRelateService;

/**
 * @Description: 发售标书
 *	 
 * @author Wang Wenshuai
 * @version 2016年10月19日下午2:27:04
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/saleTender")
public class SaleTenderController {
	@Autowired
	private SupplierExtRelateService extRelateService; //关联表
	@Autowired
	private SaleTenderService saleTenderService; //关联表
	/**
	 * @Description:展示发售标书列表
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月19日 下午2:39:16  
	 * @param @param prjectId      
	 * @return void
	 */
	@RequestMapping("/list")
	public String  list(Model model,String projectId,String page){
		List<SaleTender> list = saleTenderService.list(new SaleTender(projectId),page==null?1:Integer.valueOf(page));
		model.addAttribute("list", new PageInfo<>(list));
		model.addAttribute("projectId", projectId);
		return "bss/ppms/sall_tender/list";
	}


	/**
	 * @Description:展示供应商列表
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月19日 下午2:41:09  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/showSupplier")
	public  String showSupplier(Model model, String projectId,String page,String supplierName){
		SupplierExtRelate pExtract= new SupplierExtRelate();
//		pExtract.setProjectId(projectId);
		//占用字段保存状态类型
		pExtract.setReason("1");
		Supplier supplier=new Supplier();
		supplier.setSupplierName(supplierName);
		pExtract.setSupplier(supplier);
		List<SupplierExtRelate> ProjectExtract = extRelateService.list(pExtract,page==null||page==""?"1":page); 
		model.addAttribute("list", new PageInfo<>(ProjectExtract));
		model.addAttribute("projectId", projectId);
		model.addAttribute("supplierName", supplierName);
		return "bss/ppms/sall_tender/supplier_list";
	}

	/**
	 * 
	 * @Description:修改状态
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月19日 下午2:43:04  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/uploadDeposit")
	public String uploadDeposit(){
		return null;
	}

	/**
	 * @Description:打开upload
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月20日 下午1:39:13  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/showUpload")
	public String showUpload(String projectId,Model model,String id){
		model.addAttribute("projectId", projectId);
		model.addAttribute("saleId", id);
		return "bss/ppms/sall_tender/upload";
	}
	/**
	 * @Description:缴费
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月20日 下午1:56:17  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/upload")
	public String paymentUpload(@RequestParam(value = "bill", required = false) MultipartFile bill,@RequestParam(value = "voucher", required = false) MultipartFile voucher,String projectId,String saleId){
		saleTenderService.upload(bill,voucher,projectId,saleId);
		return "redirect:list.html?projectId="+projectId;
	}
	/**
	 * @Description:保存供应商信息
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月20日 下午2:39:12  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/save")
	public String save(String ids,String status,HttpServletRequest sq,String projectId){
		User attribute = (User) sq.getSession().getAttribute("loginUser");
		if(attribute!=null){
			saleTenderService.insert(new SaleTender(projectId, (short)0, ids, (short)0, attribute.getId()));
		}
		return "redirect:list.html?projectId="+projectId;
	}
	
	/**
	 * @Description:下载
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月21日 上午9:59:02  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/download")
	public String download(String projectId){
		
		
		return null;
	}
}
