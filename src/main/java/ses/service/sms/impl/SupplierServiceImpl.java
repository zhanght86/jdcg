package ses.service.sms.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierMapper;
import ses.model.sms.Supplier;
import ses.service.sms.SupplierService;
import ses.util.Encrypt;


/**
 * @Title: SupplierServiceImpl
 * @Description: SupplierServiceImpl 实现类
 * @author: Wang Zhaohua
 * @date: 2016-9-7下午6:14:04
 */
@Service(value = "supplierService")
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierMapper supplierMapper;
	
	@Override
	public Supplier login(String id) {
		Supplier supplier = supplierMapper.getSupplier(id);
		return supplier;
	}
	
	/**
	 * @Title: register
	 * @author: Wang Zhaohua
	 * @date: 2016-9-5 下午4:13:42
	 * @Description: 供应商注册
	 * @param: @param supplier
	 * @param: @return
	 * @return: String
	 */
	@Override
	public String register(Supplier supplier) {
		supplier.setPassword(Encrypt.e(supplier.getPassword()));// 密码 md5 加密
		supplier.setCreatedAt(new Date());
		supplierMapper.insertSelective(supplier);
		//System.out.println(1/0);
		return supplier.getId();
	}
	
	/**
	 * @Title: perfectBasic
	 * @author: Wang Zhaohua
	 * @date: 2016-9-7 下午5:51:16
	 * @Description: 供应商完善基本信息
	 * @param: @param supplier
	 * @return: void
	 */
	@Override
	public void perfectBasic(Supplier supplier) {
		Supplier oldSupplier = supplierMapper.selectByPrimaryKey(supplier.getId());
		BeanUtils.copyProperties(supplier, oldSupplier, new String[] {"serialVersionUID", "id", "loginName", "mobile", "password", "createdAt"});
		oldSupplier.setUpdatedAt(new Date());
		supplierMapper.updateByPrimaryKeySelective(oldSupplier);
	}
	
	/**
	 * @Title: selectLastInsertId
	 * @author: Wang Zhaohua
	 * @date: 2016-9-5 下午4:15:57
	 * @Description: 获取最后插入的数据的 ID
	 * @param: @return
	 * @return: int
	 */
	@Override
	public String selectLastInsertId() {
		return supplierMapper.selectLastInsertId();
	}
}
