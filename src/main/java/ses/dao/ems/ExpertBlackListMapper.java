package ses.dao.ems;

import java.util.List;
import java.util.Map;

import ses.model.ems.ExpertBlackList;
/**
 * <p>Title:ExpertBlackListMapper </p>
 * <p>Description: 专家黑名单接口</p>
 * @author Xu Qing
 * @date 2016-9-9下午4:46:53
 */
public interface ExpertBlackListMapper {
	/**
	 * @Title: update
	 * @author Xu Qing
	 * @date 2016-9-8 下午2:41:47  
	 * @Description: 更新黑名单
	 * @param @param expertBlackList      
	 * @return void
	 */
	void updateByPrimaryKeySelective(ExpertBlackList expertBlackList);
	/**
	 * @Title: findList
	 * @author Xu Qing
	 * @date 2016-9-8 下午2:42:58  
	 * @Description: 查询黑名单,可条件查询
	 * @param @return      
	 * @return List<ExpertBlackList>
	 */
	List<ExpertBlackList> findList(ExpertBlackList expertBlackList);
	/**
	 * @Title: insert
	 * @author Xu Qing
	 * @date 2016-9-8 下午2:38:48  
	 * @Description: 新增黑名单 
	 * @param @param expertBlackList      
	 * @return void
	 */
	void insertSelective(ExpertBlackList expertBlackList);
	/**
	 * @Title: selectByPrimaryKey
	 * @author Xu Qing
	 * @date 2016-9-9 下午2:15:06  
	 * @Description: 根据id查询 
	 * @param @param id
	 * @param @return      
	 * @return ExpertBlackList
	 */
	ExpertBlackList selectByPrimaryKey(String id);
	/**
	 * @Title: updateStatus
	 * @author Xu Qing
	 * @date 2016-9-9 下午4:49:59  
	 * @Description: 根据id更新状态信息 
	 * @param @param id      
	 * @return void
	 */
	void updateStatus(ExpertBlackList expertBlackList);
}