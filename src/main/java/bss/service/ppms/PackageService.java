/**
 * 
 */
package bss.service.ppms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 
     * @Title: deleteByPrimaryKey
     * @author ZhaoBo
     * @date 2016-10-18 下午3:04:26  
     * @Description: 根据Id删除 
     * @param @param id
     * @param @return      
     * @return int
     */
    int deleteByPrimaryKey(String id);

    /**
     * 
     * @Title: selectAllByIsWon
     * @author ZhaoBo
     * @date 2016-10-18 下午3:04:26  
     * @Description: 按项目条件查询 
     * @param @param id
     * @param @return      
     * @return int
     */
    List<Packages> selectAllByIsWon(Map<String, Object> map);
    /**
     * 
     * @Title: findPackageById
     * @author: Tian Kunfeng
     * @date: 2016-11-1 上午10:59:17
     * @Description: TODO
     * @param: @param map
     * @param: @return
     * @return: List<Packages>
     */
    List<Packages> findPackageAndBidMethodById(HashMap<String,Object> map);

    /**
     * 
     *〈简述〉根据包返回抽取专家
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    List<Packages> listResultExpert(String projectId);
}
