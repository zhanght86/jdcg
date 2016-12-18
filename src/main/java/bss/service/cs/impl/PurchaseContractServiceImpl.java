package bss.service.cs.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.oms.OrgnizationMapper;
import ses.model.oms.Orgnization;
import ses.util.PathUtil;
import ses.util.PropertiesUtil;
import bss.dao.cs.PurchaseContractMapper;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.service.cs.PurchaseContractService;

import com.github.pagehelper.PageHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service("purchaseContractService")
public class PurchaseContractServiceImpl implements PurchaseContractService {
	
	@Autowired
	private PurchaseContractMapper purchaseContractMapper;
	
	@Autowired
	private OrgnizationMapper orgnizationMapper;
	
	@Override
	public int insert(PurchaseContract record) {
		return 0;
	}

	@Override
	public int insertSelective(PurchaseContract record) {
		purchaseContractMapper.insertSelective(record);
		return 0;
	}
	
	@Override
	public List<PurchaseContract> selectRoughContract(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return purchaseContractMapper.selectRoughContract(map);
	}

	@Override
	public PurchaseContract selectRoughById(String id) {
		return purchaseContractMapper.selectRoughById(id);
	}

	@Override
	public List<PurchaseContract> selectAllPurchaseContract() {
		List<PurchaseContract> contractList = purchaseContractMapper.selectAllPurchaseContract();
		return contractList;
	}

	@Override
	public PurchaseContract selectByCode(String code) {
		PurchaseContract purchaseContract = purchaseContractMapper.selectByCode(code);
		return purchaseContract;
	}

	@Override
	public PurchaseContract selectById(String id) {
		return purchaseContractMapper.selectContractByid(id);
	}

	@Override
	public List<PurchaseContract> selectDraftContract(Map<String,Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return purchaseContractMapper.selectDraftContract(map);
	}

	@Override
	public PurchaseContract selectDraftById(String id) {
		return purchaseContractMapper.selectDraftById(id);
	}

	@Override
	public void updateByPrimaryKeySelective(PurchaseContract record) {
		purchaseContractMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public void deleteDraftByPrimaryKey(String id) {
		purchaseContractMapper.deleteDraftByPrimaryKey(id);
	}
	
	@Override
	public void deleteRoughByPrimaryKey(String id) {
		purchaseContractMapper.deleteRoughByPrimaryKey(id);
	}

	@Override
	public List<PurchaseContract> selectFormalContract(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return purchaseContractMapper.selectFormalContract(map);
	}

	@Override
	public PurchaseContract selectFormalById(String id) {
		return purchaseContractMapper.selectFormalById(id);
	}

	@Override
	public String createWord(PurchaseContract pur,List<ContractRequired> requList,HttpServletRequest request) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(pur.getName()!=null){
			dataMap.put("contractname", pur.getName());
		}else{
			dataMap.put("contractname", "");
		}
		if(pur.getCode()!=null){
			dataMap.put("contractCode", pur.getCode());
		}else{
			dataMap.put("contractCode", "");
		}
		if(pur.getPurchaseDepName()!=null){
			dataMap.put("purchaseDepName", pur.getPurchaseDepName());
		}else{
			dataMap.put("purchaseDepName", "");
		}
		if(pur.getPurchaseLegal()!=null){
			dataMap.put("purchaseL", pur.getPurchaseLegal());
		}else{
			dataMap.put("purchaseL", "");
		}
		if(pur.getPurchaseAgent()!=null){
			dataMap.put("purchaseAgent", pur.getPurchaseAgent());
		}else{
			dataMap.put("purchaseAgent", "");
		}
		if(pur.getPurchaseContact()!=null){
			dataMap.put("purchaseContact", pur.getPurchaseContact());
		}else{
			dataMap.put("purchaseContact", "");
		}
		if(pur.getPurchaseContactTelephone()!=null){
			dataMap.put("purchaseContactTelephone", pur.getPurchaseContactTelephone());
		}else{
			dataMap.put("purchaseContactTelephone", "");
		}
		if(pur.getPurchaseContactAddress()!=null){
			dataMap.put("purchaseContactAddress", pur.getPurchaseContactAddress());
		}else{
			dataMap.put("purchaseContactAddress", "");
		}
		if(pur.getPurchaseUnitpostCode()!=null){
			dataMap.put("purchaseUnitp", pur.getPurchaseUnitpostCode());
		}else{
			dataMap.put("purchaseUnitp", "");
		}
		if(pur.getPurchasePayDep()!=null){
			dataMap.put("purchasePayDep", pur.getPurchasePayDep());
		}else{
			dataMap.put("purchasePayDep", "");
		}
		if(pur.getPurchaseBank()!=null){
			dataMap.put("purchaseBank", pur.getPurchaseBank());
		}else{
			dataMap.put("purchaseBank", "");
		}
		if(pur.getPurchaseBankAccount()!=null){
			dataMap.put("purchaseBankAccount", pur.getPurchaseBankAccount());
		}else{
			dataMap.put("purchaseBankAccount", "");
		}
		if(pur.getSupplierDepName()!=null){
			dataMap.put("supplierDepName", pur.getSupplierDepName());
		}else{
			dataMap.put("supplierDepName", "");
		}
		if(pur.getSupplierLegal()!=null){
			dataMap.put("supplierLegal", pur.getSupplierLegal());
		}else{
			dataMap.put("supplierLegal", "");
		}
		if(pur.getSupplierAgent()!=null){
			dataMap.put("supplierAgent", pur.getSupplierAgent());
		}else{
			dataMap.put("supplierAgent", "");
		}
		if(pur.getSupplierContact()!=null){
			dataMap.put("supplierContact", pur.getSupplierContact());
		}else{
			dataMap.put("supplierContact", "");
		}
		if(pur.getSupplierContactTelephone()!=null){
			dataMap.put("supplierContactTelephone", pur.getSupplierContactTelephone());
		}else{
			dataMap.put("supplierContactTelephone", "");
		}
		if(pur.getSupplierContactAddress()!=null){
			dataMap.put("supplierContactAddress", pur.getSupplierContactAddress());
		}else{
			dataMap.put("supplierContactAddress", "");
		}
		if(pur.getSupplierUnitpostCode()!=null){
			dataMap.put("supplierUnitpostCode", pur.getSupplierUnitpostCode());
		}else{
			dataMap.put("supplierUnitpostCode", "");
		}
		if(pur.getSupplierBankName()!=null){
			dataMap.put("supplierBankName", pur.getSupplierBankName());
		}else{
			dataMap.put("supplierBankName", "");
		}
		if(pur.getSupplierBank()!=null){
			dataMap.put("supplierBank", pur.getSupplierBank());
		}else{
			dataMap.put("supplierBank", "");
		}
		if(pur.getSupplierBankAccount()!=null){
			dataMap.put("supplierBankAccount", pur.getSupplierBankAccount());
		}else{
			dataMap.put("supplierBankAccount", "");
		}
		if(pur.getDocumentNumber()!=null){
			dataMap.put("documentNumber", pur.getDocumentNumber());
		}else{
			dataMap.put("documentNumber", "");
		}
		if(pur.getApprovalNumber()!=null){
			dataMap.put("approvalNumber", pur.getApprovalNumber());
		}else{
			dataMap.put("approvalNumber", "");
		}
		if(pur.getQuaCode()!=null){
			dataMap.put("quaCode", pur.getQuaCode());
		}else{
			dataMap.put("quaCode", "");
		}
		if(pur.getMoney()!=null){
			dataMap.put("sum", pur.getMoney());
		}else{
			dataMap.put("sum", "");
		}
		if(pur.getMoney()!=null){
			dataMap.put("money", pur.getMoney());
		}else{
			dataMap.put("money", "");
		}
		
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if(requList!=null){
			for(int i=0;i<requList.size();i++){
				if(requList.get(i).getGoodsName()==null){
					break;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("number", i+1);
				if(requList.get(i).getPlanNo()!=null && requList.get(i).getPlanNo()!=""){
					map.put("planNo", requList.get(i).getPlanNo());
				}else{
					map.put("planNo", "");
				}
				if(requList.get(i).getGoodsName()!=null && requList.get(i).getGoodsName()!=""){
					map.put("goodsName", requList.get(i).getGoodsName());
				}else{
					map.put("goodsName", "");
				}
				if(requList.get(i).getBrand()!=null && requList.get(i).getBrand()!=""){
					map.put("brand", requList.get(i).getBrand());
				}else{
					map.put("brand", "");
				}
				if(requList.get(i).getStand()!=null && requList.get(i).getStand()!=""){
					map.put("stand", requList.get(i).getStand());
				}else{
					map.put("stand", "");
				}
				if(requList.get(i).getItem()!=null && requList.get(i).getItem()!=""){
					map.put("item", requList.get(i).getItem());
				}else{
					map.put("item", "");
				}
				if(requList.get(i).getPurchaseCount()!=null){
					map.put("purchaseCount", requList.get(i).getPurchaseCount());
				}else{
					map.put("purchaseCount", "");
				}
				if(requList.get(i).getPrice()!=null){
					map.put("price", requList.get(i).getPrice());
				}else{
					map.put("price", "");
				}
				if(requList.get(i).getAmount()!=null){
					map.put("mount", requList.get(i).getAmount());
				}else{
					map.put("mount", "");
				}
				if(requList.get(i).getDeliverDate()!=null){
					map.put("deliverDate", requList.get(i).getDeliverDate());
				}else{
					map.put("deliverDate", "");
				}
				if(requList.get(i).getMemo()!=null){
					map.put("mem", requList.get(i).getMemo());
				}else{
					map.put("mem", "");
				}
				list.add(map);
			}
		}
		dataMap.put("list", list);
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		configuration.setServletContextForTemplateLoading(request.getSession().getServletContext(), "/template");
//		configuration.setClassForTemplateLoading(this.getClass(), "/template");
//		System.out.println(this.getClass());
		Template t = null;
		try {
			if(pur.getStatus()==2){
				t=configuration.getTemplate("formalcontract.ftl");
			}
			t = configuration.getTemplate("contract.ftl");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String rootpath = (PathUtil.getWebRoot() + "contract/").replace("\\", "/");
		File rootFile = new File(rootpath);
		if(!rootFile.exists()){
			rootFile.mkdirs();
		}
		String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_" + pur.getName()+".doc";
		File outFile = new File(rootpath+"/"+fileName);
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			t.process(dataMap, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	@Override
	public List<PurchaseContract> selectFormalByContractType(
			Integer contractType) {
		return purchaseContractMapper.selectFormalByContractType(contractType);
	}
	
	/**
     * @Title: findPurchaseContractByMap
     * @author: Wang Zhaohua
     * @date: 2016-11-2 下午8:02:07
     * @Description: 根据条件查询合同
     * @param: @param param
     * @param: @return
     * @return: List<PurchaseContract>
     */
	@Override
	public List<PurchaseContract> findPurchaseContractByMap(PurchaseContract purchaseContract, int page) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("isDeleted", 0);// 未删除
		param.put("status", 2);// 正常合同
		param.put("isDeclare", purchaseContract.getIsDeclare());// 待报
		param.put("projectName", purchaseContract.getProjectName());// 项目名称
		
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page, Integer.parseInt(config.getString("pageSize")));
		
		return purchaseContractMapper.findPurchaseContractByMap(param);
	}

	@Override
	public List<PurchaseContract> selectAllContract() {
		return purchaseContractMapper.selectAllContract();
	}

	@Override
	public void insertSelectiveById(PurchaseContract record) {
		purchaseContractMapper.insertSelectiveById(record);
	}

	@Override
	public List<Orgnization> findAllUsefulOrg() {
		return orgnizationMapper.findAllUsefulOrg();
	}

	@Override
	public List<PurchaseContract> selectAllContractByStatus(
			Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return purchaseContractMapper.selectAllContractByStatus(map);
	}
}
