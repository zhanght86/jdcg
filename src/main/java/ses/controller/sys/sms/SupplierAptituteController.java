package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.service.sms.SupplierAptituteService;
import ses.service.sms.SupplierService;

@Controller
@Scope("prototype")
@RequestMapping(value = "supplier_aptitute")
public class SupplierAptituteController extends BaseSupplierController {
	@Autowired
	private SupplierService supplierService;// 供应商基本信息

	@Autowired
	private SupplierAptituteService supplierAptituteService;
	
	@RequestMapping(value = "add_aptitute")
	public String addAptitute(Model model, String matEngId, String supplierId) {
		model.addAttribute("matEngId", matEngId);
		model.addAttribute("supplierId", supplierId);
		return "ses/sms/supplier_register/add_aptitute";
	}

	@RequestMapping(value = "save_or_update_aptitute")
	public String saveOrUpdateAptitute(HttpServletRequest request, SupplierAptitute supplierAptitute, String supplierId) throws IOException {
		this.setAptituteUpload(request, supplierAptitute);
		supplierAptituteService.saveOrUpdateAptitute(supplierAptitute);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}

	@RequestMapping(value = "back_to_professional")
	public String backToSellfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}

	@RequestMapping(value = "delete_aptitute")
	public String deleteAptitute(HttpServletRequest request, String aptituteIds, String supplierId) {
		supplierAptituteService.deleteAptitute(aptituteIds);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	public void setAptituteUpload(HttpServletRequest request, SupplierAptitute supplierAptitute) throws IOException {
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
				String fileName = file.getOriginalFilename();
				fileName = UUID.randomUUID().toString().replace("-", "").toUpperCase().toString() + "_" + fileName;
				if (file != null && file.getSize() > 0) {
					String path = super.getFilePath(request) + fileName;
					file.transferTo(new File(path));
					if (str.equals("attachCertFile")) {
						supplierAptitute.setAttachCert(fileName);
					} 
				}
			}
		}
	}
}
