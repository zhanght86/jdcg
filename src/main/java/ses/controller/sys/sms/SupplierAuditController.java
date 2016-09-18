package ses.controller.sys.sms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierStockholder;
import ses.service.sms.SupplierAuditServlice;

/**
 * <p>Title:SupplierAuditController </p>
 * <p>Description: 供应商审核控制类</p>
 * @author Xu Qing
 * @date 2016-9-12下午5:14:36
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplierAudit")
public class SupplierAuditController {
	@Autowired
	private SupplierAuditServlice supplierAuditServlice;
	
	/**
	 * @Title: daiBan
	 * @author Xu Qing
	 * @date 2016-9-13 下午2:12:29  
	 * @Description: 待办
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("daiBan")
	public String daiBan() {
		
		return "ses/sms/supplier_audit/daiban";
	}
	
	/**
	 * @Title: SupplierList
	 * @author Xu Qing
	 * @date 2016-9-12 下午5:19:07  
	 * @Description: 所有供应商 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("supplierList")
	public String supplierList(HttpServletRequest request) {
		List<Supplier> supplierList =supplierAuditServlice.supplierList();
		request.setAttribute("supplierList", supplierList);
		return "ses/sms/supplier_audit/supplier_list";
	}
	
	/**
	 * @Title: essentialInformation
	 * @author Xu Qing
	 * @date 2016-9-12 下午7:14:09  
	 * @Description: 基本信息 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("essential")
	public String essentialInformation(HttpServletRequest request,Model model) {
		String supplierId = request.getParameter("supplierId");
		if(supplierId==null ){
			supplierId = (String) request.getSession().getAttribute("supplierId");
		}
		Supplier supplier = supplierAuditServlice.supplierById(supplierId);
		request.setAttribute("supplier", supplier);
		request.getSession().setAttribute("supplierId", supplierId);
		return "ses/sms/supplier_audit/essential";
	}
	
	/**
	 * @Title: financialInformation
	 * @author Xu Qing
	 * @date 2016-9-13 上午10:51:15  
	 * @Description:财务信息
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("financial")
	public String financialInformation(HttpServletRequest request) {
		String supplierId = (String) request.getSession().getAttribute("supplierId");
		List<SupplierFinance> supplierFinance = supplierAuditServlice.supplierFinanceBySupplierId(supplierId);
		request.setAttribute("supplier", supplierFinance);
		return "ses/sms/supplier_audit/financial";
	}
	
	/**
	 * @Title: shareholderInformation
	 * @author Xu Qing
	 * @date 2016-9-13 上午11:19:37  
	 * @Description: 股东信息 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("shareholder")
	public String shareholderInformation(HttpServletRequest request) {
		String supplierId = (String) request.getSession().getAttribute("supplierId");
		List<SupplierStockholder> supplierStockholder = supplierAuditServlice.ShareholderBySupplierId(supplierId);
		request.setAttribute("shareholder", supplierStockholder);
		return "ses/sms/supplier_audit/shareholder";
	}
	
	/**
	 * @Title: materialProduction
	 * @author Xu Qing
	 * @date 2016-9-13 下午4:32:12  
	 * @Description: 物资生产型专业信息 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("materialProduction")
	public String materialProduction() {
		return "ses/sms/supplier_audit/material_production";
	}
	
	/**
	 * @Title: materialSales
	 * @author Xu Qing
	 * @date 2016-9-18 下午8:05:15  
	 * @Description: 物资销售专业信息 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("materialSales")
	public String materialSales(){
		return "ses/sms/supplier_audit/material_sales";
	}
	
	/**
	 * @Title: engineeringInformation
	 * @author Xu Qing
	 * @date 2016-9-18 下午8:13:24  
	 * @Description: 工程专业信息 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("engineering")
	public String engineeringInformation(){
		return "ses/sms/supplier_audit/engineering";
	}
	
	/**
	 * @Title: auditReasons
	 * @author Xu Qing
	 * @date 2016-9-18 下午5:55:44  
	 * @Description: 审核记录 
	 * @param @param supplierAudit      
	 * @return void
	 */
	@RequestMapping("auditReasons")
	public void auditReasons(SupplierAudit supplierAudit){
		supplierAuditServlice.auditReasons(supplierAudit);
	}
}
