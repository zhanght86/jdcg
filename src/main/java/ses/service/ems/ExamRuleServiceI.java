/**
 * 
 */
package ses.service.ems;

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
	* @Title: select
	* @author ZhaoBo
	* @date 2016-9-8 下午1:09:51  
	* @Description: 查找数据库中的考试规则 
	* @param @return      
	* @return List<ExamRule>
	 */
	List<ExamRule> select();
	
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
}
