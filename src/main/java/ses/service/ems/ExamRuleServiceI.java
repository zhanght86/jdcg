/**
 * 
 */
package ses.service.ems;

import java.util.HashMap;
import java.util.List;

import ses.model.ems.ExamRule;

/**
 * @Title:ExamRuleServiceI
 * @Description: 考试规则管理Service
 * @author ZhaoBo
 * @date 2016-9-8上午10:59:11
 */
public interface ExamRuleServiceI {
	/**
	 * 
	* @Title: insertSelective
	* @author ZhaoBo
	* @date 2016-9-8 上午10:52:51  
	* @Description: 新增考试规则 
	* @param @param examRule
	* @param @return      
	* @return int
	 */
	int insertSelective(ExamRule examRule);
	
	/**
	 * 
	* @Title: updateByPrimaryKeySelective
	* @author ZhaoBo
	* @date 2016-9-8 上午10:52:59  
	* @Description: 更新考试规则 
	* @param @param examRule
	* @param @return      
	* @return int
	 */
	int updateByPrimaryKeySelective(ExamRule examRule);
	
	/**
	 * 
	* @Title: selectById
	* @author ZhaoBo
	* @date 2016-11-16 下午12:27:30  
	* @Description: 按条件查找考试规则 
	* @param @param id
	* @param @return      
	* @return ExamRule
	 */
	List<ExamRule> selectById(HashMap<String,Object> map);
}
