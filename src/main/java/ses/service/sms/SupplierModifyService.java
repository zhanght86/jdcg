package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierModify;

public interface SupplierModifyService {
    
    /**
     * @Title: insertSelective
     * @author XuQing 
     * @date 2017-2-15 下午4:22:06  
     * @Description:插入审核退回后供应商修改记录
     * @param @param supplierModify      
     * @return void
     */
    void insertModifyRecord (SupplierModify supplierModify);
    
	/**
	 * @Title: selectField
	 * @author XuQing 
	 * @date 2017-2-16 下午4:22:14  
	 * @Description:查询
	 * @param @param supplierModify
	 * @param @return      
	 * @return List<SupplierHistory>
	 */
    List<SupplierModify> selectBySupplierId (SupplierModify supplierModify);
	
	/**
	 * @Title: delete
	 * @author XuQing 
	 * @date 2017-2-16 下午4:24:18  
	 * @Description:删除
	 * @param @param supplierModify      
	 * @return void
	 */
    void delete (SupplierModify supplierModify);
    
    /**
	 * @Title: findBySupplierId
	 * @author XuQing 
	 * @date 2017-2-17 上午10:21:59  
	 * @Description:查询
	 * @param @param supplierModify
	 * @param @return      
	 * @return SupplierModify
	 */
	SupplierModify findBySupplierId (SupplierModify supplierModify);
	
	/**
     * @Title: add
     * @author XuQing 
     * @date 2017-2-17 下午2:48:40  
     * @Description:插入基本信息
     * @param @param supplierModify      
     * @return void
     */
    void add (SupplierModify supplierModify);

}
