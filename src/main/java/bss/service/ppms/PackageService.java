/**
 * 
 */
package bss.service.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.Packages;

/**
 * @Title:PackageService
 * @Description: 
 * @author ZhaoBo
 * @date 2016-10-9下午2:14:16
 */
public interface PackageService {
	/**
	 * 
	* @Title: insertSelective
	* @author ZhaoBo
	* @date 2016-10-9 下午2:13:01  
	* @Description: 新增分包 
	* @param @param pack
	* @param @return      
	* @return int
	 */
	int insertSelective(Packages pack);
	
	/**
	 * 
	* @Title: updateByPrimaryKeySelective
	* @author ZhaoBo
	* @date 2016-10-9 下午2:13:37  
	* @Description: 更新分包 
	* @param @param pack
	* @param @return      
	* @return int
	 */
	int updateByPrimaryKeySelective(Packages pack);
	
	/**
	 * 
	* @Title: findPackageById
	* @author ZhaoBo
	* @date 2016-10-9 下午2:25:15  
	* @Description: 根据项目Id查找包名 
	* @param @param map
	* @param @return      
	* @return List<Package>
	 */
	List<Packages> findPackageById(HashMap<String,Object> map);
}
