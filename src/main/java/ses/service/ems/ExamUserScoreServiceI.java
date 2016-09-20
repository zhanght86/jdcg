/**
 * 
 */
package ses.service.ems;

import java.util.List;

import ses.model.ems.ExamUserScore;


/**
 * 
* @Title:ExamUserScoreServiceI
* @Description:用户成绩Service
* @author ZhaoBo
* @date 2016-9-7上午10:25:44
 */
public interface ExamUserScoreServiceI {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author ZhaoBo
	* @date 2016-9-7 下午5:57:10  
	* @Description: 根据ID删除用户成绩 
	* @param @param id
	* @param @return      
	* @return int
	 */
	int deleteByPrimaryKey(String id);
	
	/**
	 * 
	* @Title: insertSelective
	* @author ZhaoBo
	* @date 2016-9-7 下午5:57:14  
	* @Description: 新增用户成绩 
	* @param @param record
	* @param @return      
	* @return int
	 */
    int insertSelective(ExamUserScore examUserScore);
    
    /**
     * 
    * @Title: selectByPrimaryKey
    * @author ZhaoBo
    * @date 2016-9-7 下午5:57:20  
    * @Description: 根据ID查找用户成绩 
    * @param @param id
    * @param @return      
    * @return ExamUserScore
     */
    ExamUserScore selectByPrimaryKey(String id);
    
    /**
     * 
    * @Title: updateByPrimaryKeySelective
    * @author ZhaoBo
    * @date 2016-9-7 下午5:57:24  
    * @Description: 根据ID更新用户成绩 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKeySelective(ExamUserScore examUserScore);
    
    /**
     * 
    * @Title: selectExpertResultByCondition
    * @author ZhaoBo
    * @date 2016-9-19 下午8:54:53  
    * @Description: 专家考试成绩按条件查询 
    * @param @param examUserScore
    * @param @return      
    * @return List<ExamUserScore>
     */
    List<ExamUserScore> selectExpertResultByCondition(ExamUserScore examUserScore);
    
    List<ExamUserScore> selectExpertResultByTerm(ExamUserScore examUserScore);
}
