package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierBranchMapper;
import ses.model.sms.SupplierBranch;
import ses.service.sms.SupplierBranchService;
import ses.util.WfUtil;

@Service(value = "supplierBranchService")
public class SupplierBranchServiceImpl implements SupplierBranchService{

	@Autowired
	private SupplierBranchMapper supplierBranchMapper;
	
	@Override
	public List<SupplierBranch> findSupplierBranch(String supplierId) {
		
		return supplierBranchMapper.queryBySupplierId(supplierId);
	}
	
	
	@Override
	public void addBatch(List<SupplierBranch> list,String supplierId) {
		supplierBranchMapper.deleteBySupplierId(supplierId);
		 for(SupplierBranch s:list){
			 String id = WfUtil.createUUID();
			 s.setId(id);
			 s.setSupplierId(supplierId);
			 supplierBranchMapper.insertSelective(s); 
		 }
		
	}

}
