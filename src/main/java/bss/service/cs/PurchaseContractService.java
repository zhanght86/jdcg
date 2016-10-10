package bss.service.cs;

import java.util.List;

import bss.model.cs.PurchaseContract;

/*
 *@Title:PurchaseContractService
 *@Description:采购合同管理service类
 *@author QuJie
 *@date 2016-9-23上午9:32:05
 */
public interface PurchaseContractService {
	/**
	 * 
	* @Title: insert
	* @author QuJie 
	* @date 2016-9-22 下午2:48:07  
	* @Description: 添加采购合同 
	* @param @param record
	* @param @return      
	* @return int
	 */
    int insert(PurchaseContract record);
    
    /**
     * 
    * @Title: insertSelective
    * @author QuJie 
    * @date 2016-9-22 下午2:48:23  
    * @Description: 根据条件添加采购合同 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insertSelective(PurchaseContract record);
    
    /**
     * 
    * @Title: selectAllPurchaseContract
    * @author QuJie 
    * @date 2016-9-23 下午1:36:54  
    * @Description: 查询所有采购合同 
    * @param @return      
    * @return List<PurchaseContract>
     */
    List<PurchaseContract> selectAllPurchaseContract();
    
    /**
     * 
    * @Title: selectByCode
    * @author QuJie 
    * @date 2016-9-23 下午4:13:53  
    * @Description: 根据合同编号查询 
    * @param @param code
    * @param @return      
    * @return PurchaseContract
     */
    PurchaseContract selectByCode(String code);
    
    /**
     * 
    * @Title: selectById
    * @author QuJie 
    * @date 2016-10-9 下午1:54:17  
    * @Description: 通过id查询合同 
    * @param @param id
    * @param @return      
    * @return PurchaseContract
     */
    PurchaseContract selectById(String id);
    
    /**
     * 
    * @Title: selectDraftContract
    * @author QuJie 
    * @date 2016-10-9 下午1:10:54  
    * @Description: 查询所有合同草稿 
    * @param @return      
    * @return PurchaseContract
     */
    List<PurchaseContract>  selectDraftContract();
    
    /**
     * 
    * @Title: selectDraftById
    * @author QuJie 
    * @date 2016-10-10 上午10:13:58  
    * @Description: 根据id查询合同草稿
    * @param @param id
    * @param @return      
    * @return List<PurchaseContract>
     */
    PurchaseContract selectDraftById(String id);
}
