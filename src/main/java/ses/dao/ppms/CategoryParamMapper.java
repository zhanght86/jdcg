package ses.dao.ppms;

import java.util.List;
import java.util.Map;

import ses.model.bms.Category;
import ses.model.ppms.CategoryParam;

    /**
     * <p>Title:CategoryParamMapper</p>
     * <p>Description: 产品参数接口</p>
     * @author zhangxuefeng
     * @date 
     */
public interface CategoryParamMapper {
	/**   
	* @Title: selectAll
	* @author zhangxuefeng
	* @date 2016-7-27 下午4:52:29  
	* @Description:获取所有数据
	* @param @param list<Category>
	*/
	public List<CategoryParam> selectAll();
	
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author Zhang XueFeng 
	* @date 2016-8-25 下午3:35:40  
	* @Description: 通过id删除信息 
	* @param @param id
	* @param @return      
	* @return void
	 */
    void deleteByPrimaryKey(String id);
    /**
     * 
    * @Title: selectByPrimaryKey
    * @author Zhang XueFeng
    * @date 2016-8-25 下午3:36:18  
    * @Description: 根据id查
    * @param @param record
    * @param @return      
    * @return CategoryParam
     */
    CategoryParam selectByPrimaryKey(String id);
    /**
     * 
    * @Title: updateByPrimaryKeySelective
    * @author Zhang XueFeng
    * @date 2016-8-25 下午3:37:21  
    * @Description: 根据条件修改信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    void updateByPrimaryKeySelective(CategoryParam categoryParam);
    /**   
	* @Title: listByParent
	* @author zhangxuefeng
	* @date 2016-7-27 下午4:52:29  
	* @Description: 根据pid获取所有子栏目
	* @param @param list<Category>
	*/
	 List<Category> listByParent(String pid);
	
	/**   
	* @Title: insertSelective
	* @author zhangxuefeng
	* @date 2016-7-27 下午4:52:29  
	* @Description: 根据id新增
	* @param @param void
	*/
	 void insertSelective(CategoryParam categoryParam);
	 /**   
		* @Title: findListByCategoryId
		* @author zhangxuefeng
		* @date 2016-7-27 下午4:52:29  
		* @Description: 根据品目id查询参数信息
		* @param @param CategoryParam
		*/

	CategoryParam findByCategoryId(String id);
	List<CategoryParam> findListByCategoryId(String categoryId);

	List<CategoryParam> findParamByCategoryIdAndProductsId(Map<String, String> param);
}
