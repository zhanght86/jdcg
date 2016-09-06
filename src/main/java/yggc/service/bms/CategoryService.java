package yggc.service.bms;

import java.util.List;



import yggc.model.bms.Category;
   /**
   * 
   * <p>Title:CategoryMapper</p>
   * <p>Description: 采购目录管理接口</p>
   * <p>Company: yggc </p> 
   * @author javazxf
   * @date 
   */
	public interface CategoryService {
	/**   
	* @Title: selectAll
	* @author yyyml
	* @date
	* @Description: 查询目录信息
	* @param @param List<Category>
	* 
	*/
	public List<Category> listByParent(String pid);
	/**   
	* @Title: insertsertSelective
	* @author yyyml
	* @date 2016-7-27 下午4:52:29  
	* @Description: 新增目录
	* @param @param category
	* 
	*/
	public void insertSelective(Category category);
	
	/**
	 * 
	* @Title: findTreeByPid
	* @author MRlovablee
	* @date 2016-5-18 上午9:26:10  
	* @Description: 根据父节点找出子节点 
	* @param @param pid
	* @param @param pager
	* @param @return
	 */
	public List<Category> findTreeByPid(String id);
	/**
	 * 
	* @Title: findTreeByPid
	* @author MRlovablee
	* @date 2016-5-18 上午9:26:10  
	* @Description: 根据父节点找出子节点 
	* @param @param pid
	* @param @param pager
	* @param @return
	*/
	public void updateByPrimaryKey(Category category);
	/**
	 * 
	* @Title: selectByPrimaryKey
	* @author MRlovablee
	* @date 2016-5-18 上午9:26:10  
	* @Description: 根据id查询目录信息
	* @param @param pid
	* @param @param pager
	* @param @return
	*/
	public Category selectByPrimaryKey(String id);
}
