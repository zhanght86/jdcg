package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierMatEngService;
import ses.service.sms.SupplierMatProService;
import ses.service.sms.SupplierMatSeService;
import ses.service.sms.SupplierMatSellService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;

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
	private SupplierTypeRelateService supplierTypeRelateService;// 供应商类型关联

	@Autowired
	private SupplierMatProService supplierMatProService;// 供应商物资生产专业信息

	@Autowired
	private SupplierMatSellService supplierMatSellService;// 供应商物资销售专业信息

	@Autowired
	private SupplierMatSeService supplierMatSeService;// 供应商服务专业信息

	@Autowired
	private SupplierMatEngService supplierMatEngService;// 供应商工程专业信息

	@Autowired
	private PurchaseOrgnizationServiceI poService;

	@RequestMapping("login")
	public String login(HttpServletRequest request, Model model) {
		Supplier supplier = supplierService.get("53BF9E64B38B46228914807B92BAE812");
		model.addAttribute("currSupplier", supplier);
		if (supplier.getListSupplierFinances() != null) {
			model.addAttribute("financeSize", supplier.getListSupplierFinances().size());
		}
		if (supplier.getListSupplierStockholders() != null) {
			model.addAttribute("stockholderSize", supplier.getListSupplierStockholders().size());
		}
		request.getSession().setAttribute("supplierId", supplier.getId());
		return "ses/sms/supplier_register/basic_info";
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
	public String registrationPage() {
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
		boolean flag = this.checkReferer(request, "/supplier/registration_page.html");
		if (flag) {
			return "ses/sms/supplier_register/register";
		}
		return "redirect:registration_page.html";
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
	public String register(HttpServletRequest request, Supplier supplier) {
		supplier = supplierService.register(supplier);
		request.getSession().setAttribute("jump.page", "basic_info");
		request.getSession().setAttribute("currSupplier", supplier);
		return "redirect:page_jump.html";
	}

	/**
	 * @Title: prevStep
	 * @author: Wang Zhaohua
	 * @date: 2016-9-12 下午2:58:40
	 * @Description: 供应商信息完善上一步
	 * @param: @param page
	 * @param: @param sign
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "prev_step")
	public String prevStep(HttpServletRequest request, String page, Integer sign, Supplier supplier) {
		request.getSession().removeAttribute("defaultPage");
		if (sign == 3) {
			// 保存供应商类型
			supplierTypeRelateService.saveSupplierTypeRelate(supplier);

			// 查询供应商基本信息
			supplier = supplierService.get(supplier.getId());
			request.getSession().setAttribute("currSupplier", supplier);

			// 跳转页面
			request.getSession().setAttribute("jump.page", "basic_info");
			return "redirect:page_jump.html";
		} else if (sign == 4) {
			// 保存供应商专业信息
			supplierMatProService.saveOrUpdateSupplierMatPro(supplier);
			supplierMatSellService.saveOrUpdateSupplierMatSell(supplier);
			supplierMatSeService.saveOrUpdateSupplierMatSe(supplier);
			supplierMatEngService.saveOrUpdateSupplierMatPro(supplier);

			// ajax 查询供应商类型树

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "supplier_type");
			return "redirect:page_jump.html";
		} else if (sign == 5) {
			// 保存供应商品目信息

			// 查询供应商信息
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "professional_info");
			return "redirect:page_jump.html";
		} else if (sign == 6) {
			// 保存供应商产品信息

			// 查询产品信息
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "items");
			return "redirect:page_jump.html";
		} else if (sign == 7) {
			// 保存供应商初审机构
			supplierService.updateSupplierProcurementDep(supplier);

			// 查询产品信息
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "products");
			return "redirect:page_jump.html";
		} else if (sign == 8) {
			// 这里不用保存申请书模板

			// 查询供应商信息
			supplier = supplierService.get(supplier.getId());
			HashMap<String, Object> map = new HashMap<String, Object>();
			List<PurchaseDep> listPurchaseDeps = poService.findPurchaseDepList(map);

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("listPurchaseDeps", listPurchaseDeps);
			request.getSession().setAttribute("jump.page", "procurement_dep");
			return "redirect:page_jump.html";
		} else if (sign == 9) {
			// 保存供应商附件

			// 查询供应商申请表

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "template_download");
			return "redirect:page_jump.html";
		}
		return null;
	}

	/**
	 * @Title: stashStep
	 * @author: Wang Zhaohua
	 * @date: 2016-9-12 下午2:57:52
	 * @Description: 供应商信息完善暂存当前步
	 * @param: @param sign
	 * @param: @return
	 * @return: String
	 * @throws IOException 
	 */
	@RequestMapping(value = "stash_step")
	public String stashStep(HttpServletRequest request, Integer sign, String defaultPage, Supplier supplier) throws IOException {
		request.getSession().setAttribute("defaultPage", defaultPage);
		if (sign == 2) {
			// 保存供应商基本信息
			this.setSupplierUpload(request, supplier);
			supplierService.perfectBasic(supplier);// 保存供应商详细信息

			// 查询供应商基本信息
			supplier = supplierService.get(supplier.getId());
			request.getSession().setAttribute("currSupplier", supplier);

			// 页面跳转
			request.getSession().setAttribute("jump.page", "basic_info");
			return "redirect:page_jump.html";
		} else if (sign == 3) {
			// 保存供应商类型
			supplierTypeRelateService.saveSupplierTypeRelate(supplier);

			// 跳转页面
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "supplier_type");
			return "redirect:page_jump.html";
		} else if (sign == 4) {
			// 保存供应商物资生产专业信息
			supplierMatProService.saveOrUpdateSupplierMatPro(supplier);
			supplierMatSellService.saveOrUpdateSupplierMatSell(supplier);
			supplierMatSeService.saveOrUpdateSupplierMatSe(supplier);
			supplierMatEngService.saveOrUpdateSupplierMatPro(supplier);

			// 查询供应商信息
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "professional_info");
			return "redirect:page_jump.html";
		} else if (sign == 5) {
			// 保存供应商品目信息

			// ajax 查询采购品目

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "items");
			return "redirect:page_jump.html";
		} else if (sign == 6) {
			// 保存供应商产品信息

			// 查询产品信息
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "products");
			return "redirect:page_jump.html";
		} else if (sign == 7) {
			// 保存供应商初审机构
			supplierService.updateSupplierProcurementDep(supplier);

			// 查询机构信息
			supplier = supplierService.get(supplier.getId());
			HashMap<String, Object> map = new HashMap<String, Object>();
			List<PurchaseDep> listPurchaseDeps = poService.findPurchaseDepList(map);

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("listPurchaseDeps", listPurchaseDeps);
			request.getSession().setAttribute("jump.page", "procurement_dep");
			return "redirect:page_jump.html";
		} else if (sign == 9) {
			// 保存供应商附件

			// 查询供应商信息
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "template_upload");
			return "redirect:page_jump.html";
		}
		return null;
	}

	/**
	 * @Title: next_step
	 * @author: Wang Zhaohua
	 * @date: 2016-9-12 下午2:57:06
	 * @Description: 供应商信息完善下一步
	 * @param: @param page
	 * @param: @param sign
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "next_step")
	public String nextStep(HttpServletRequest request, String page, Integer sign, Supplier supplier) {
		String realPath = request.getServletContext().getRealPath("/");
		System.out.println(realPath);
		request.getSession().removeAttribute("defaultPage");
		if (sign == 2) {// 保持供应商基本信息
			supplierService.perfectBasic(supplier);// 保存供应商详细信息

			// Ajax 查询供应商类型树, 这里不用写了

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "supplier_type");
			return "redirect:page_jump.html";
		} else if (sign == 3) {
			// 保存供应商类型
			supplierTypeRelateService.saveSupplierTypeRelate(supplier);

			// 查询专业信息
			supplier = supplierService.get(supplier.getId());

			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "professional_info");
			return "redirect:page_jump.html";
		} else if (sign == 4) {
			// 保存供应商专业信息
			supplierMatProService.saveOrUpdateSupplierMatPro(supplier);
			supplierMatSellService.saveOrUpdateSupplierMatSell(supplier);
			supplierMatSeService.saveOrUpdateSupplierMatSe(supplier);
			supplierMatEngService.saveOrUpdateSupplierMatPro(supplier);

			// Ajax 查询品目树, 这里不用写了

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "items");
			return "redirect:page_jump.html";
		} else if (sign == 5) {
			// 保存供应商品目信息

			// 查询产品信息
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "products");
			return "redirect:page_jump.html";
		} else if (sign == 6) {
			// 保存供应商产品信息

			// 查询机构信息
			supplier = supplierService.get(supplier.getId());
			HashMap<String, Object> map = new HashMap<String, Object>();
			List<PurchaseDep> listPurchaseDeps = poService.findPurchaseDepList(map);

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("listPurchaseDeps", listPurchaseDeps);
			request.getSession().setAttribute("jump.page", "procurement_dep");
			return "redirect:page_jump.html";
		} else if (sign == 7) {
			// 保存供应商初审机构
			supplierService.updateSupplierProcurementDep(supplier);

			// 查询申请表信息

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "template_download");
			return "redirect:page_jump.html";
		} else if (sign == 8) {
			// 这里不用保存申请书模板

			// 查询供应商信息
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "template_upload");
			return "redirect:page_jump.html";
		} else if (sign == 9) {

		}
		return null;
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
	 */
	@RequestMapping(value = "perfect_basic")
	public String perfectBasic(HttpServletRequest request, Supplier supplier) {

		String id = supplier.getId();
		if (id == null || "".equals(id)) {
			id = (String) request.getSession().getAttribute("supplierId");
			supplier.setId(id);
		}
		supplierService.perfectBasic(supplier);// 保存供应商详细信息
		request.getSession().setAttribute("supplierId", id);
		request.getSession().setAttribute("jump.page", "supplier_type");
		return "redirect:page_jump.html";
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
		return "ses/sms/supplier_register/" + page;
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

	public void setSupplierUpload(HttpServletRequest request, Supplier supplier) throws IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> its = multiRequest.getFileNames();
			while (its.hasNext()) {
				String str = its.next();
				MultipartFile file = multiRequest.getFile(str);
				if (file != null && file.getSize() > 0) {
					String path = super.getRootPath(request) + file.getOriginalFilename();
					file.transferTo(new File(path));
					if (str.equals("taxCertFile")) {
						supplier.setTaxCert(path);
					} else if (str.equals("billCertFile")) {
						supplier.setBillCert(path);
					} else if (str.equals("securityCertFile")) {
						supplier.setSecurityCert(path);
					} else if (str.equals("breachCertFile")) {
						supplier.setBreachCert(path);
					}
				}
			}
		}
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

}
