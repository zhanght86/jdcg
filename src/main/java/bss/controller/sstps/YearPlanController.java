package bss.controller.sstps;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.model.sstps.ContractProduct;
import bss.model.sstps.YearPlan;
import bss.service.sstps.YearPlanService;

@Controller
@Scope
@RequestMapping("/yearPlan")
public class YearPlanController {
	
	@Autowired
	private YearPlanService yearPlanService;
	
	/**
	* @Title: select
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午4:19:27  
	* @Description: 列表页面
	* @param @param model
	* @param @param proId
	* @param @param yearPlan
	* @param @return      
	* @return String
	 */
	@RequestMapping("/select")
	public String select(Model model,String proId,YearPlan yearPlan){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		yearPlan.setContractProduct(contractProduct);
		List<YearPlan> list = yearPlanService.selectProduct(yearPlan);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		
		return "bss/sstps/offer/supplier/yearPlan/list";
	}
	
	/**
	* @Title: add
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午4:19:39  
	* @Description: 新增页面
	* @param @param model
	* @param @param proId
	* @param @return      
	* @return String
	 */
	@RequestMapping("/add")
	public String add(Model model,String proId){
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/yearPlan/add";
	}
	
	/**
	* @Title: edit
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午4:19:49  
	* @Description: 修改页面
	* @param @param model
	* @param @param proId
	* @param @param id
	* @param @return      
	* @return String
	 */
	@RequestMapping("/edit")
	public String edit(Model model,String proId,String id){
		YearPlan yearPlan = yearPlanService.selectById(id);
		model.addAttribute("yp", yearPlan);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/yearPlan/edit";
	}
	
	/**
	* @Title: save
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午4:19:59  
	* @Description: 保存 
	* @param @param model
	* @param @param yearPlan
	* @param @return      
	* @return String
	 */
	@RequestMapping("/save")
	public String save(Model model,YearPlan yearPlan){
		String proId = yearPlan.getContractProduct().getId();
		yearPlan.setCreatedAt(new Date());
		yearPlan.setUpdatedAt(new Date());
		yearPlanService.insert(yearPlan);
		
		List<YearPlan> list = yearPlanService.selectProduct(yearPlan);
		model.addAttribute("list", list);
		
		model.addAttribute("proId",proId);
		
		return "bss/sstps/offer/supplier/yearPlan/list";
	}
	
	/**
	* @Title: update
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午4:20:06  
	* @Description: 新增
	* @param @param model
	* @param @param yearPlan
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(Model model,YearPlan yearPlan){
		String proId = yearPlan.getContractProduct().getId();
		
		yearPlan.setUpdatedAt(new Date());
		yearPlanService.update(yearPlan);
		
		List<YearPlan> list = yearPlanService.selectProduct(yearPlan);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/yearPlan/list";
	}
	
	/**
	* @Title: delete
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午4:20:12  
	* @Description: 删除
	* @param @param model
	* @param @param proId
	* @param @param ids
	* @param @return      
	* @return String
	 */
	@RequestMapping("/delete")
	public String delete(Model model,String proId,String ids){
		
		String[] id=ids.split(",");
		
		for(String str : id){
			yearPlanService.delete(str);
		}
		
		YearPlan yearPlan = new YearPlan();
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		yearPlan.setContractProduct(contractProduct);
		
		List<YearPlan> list = yearPlanService.selectProduct(yearPlan);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/yearPlan/list";
	}
	

}
