package ses.service.bms;

import java.util.List;

import ses.model.bms.Templet;


/**
 * 
 * @Title:TempletService 
 * @Description: 模板管理接口
 * @author Liyi
 * @date 2016-9-7下午6:00:53
 *
 */
public interface TempletService {
	
	/**
	 * 1.获取所有模板对象
	 */
	List<Templet> getAll(Integer pageNum);
	
	/**
	 * 2.添加模板
	 */
	void save(Templet t);
	
	/**
	 * 3.更新模板
	 */
	void update(Templet t);

	/**
	 * 4.根据主键查询模板
	 */
	Templet get(String id);
	
	/**
	 * 5.根据主键删除模板
	 */
	void delete(String id);

	/**
	 * 6.查询模板条数
	 */
	Integer queryByCount(); 
	
	/**
	 * 7.条件搜索
	 */
	List<Templet> search(Integer pageNum,Templet t);
	
	/**
     * 
    * @Title: searchByTemType
    * @author Liyi 
    * @date 2016-10-29 下午12:55:03  
    * @Description: TODO 
    * @param @param temType
    * @param @return      
    * @return Templet
     */
    Templet searchByTemType(String temType);
}
