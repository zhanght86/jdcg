/**
 * 
 */
package ses.service.ems;

import java.util.HashMap;
import java.util.List;

import ses.model.ems.ExamUserScore;
import ses.model.ems.Expert;


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
    * @Title: updateIsMaxById
    * @author ZhaoBo
    * @date 2016-9-28 下午2:42:33  
    * @Description: 根据用户ID和考卷ID更新采购人成绩的ISMAx状态 
    * @param @param examUserScore
    * @param @return      
    * @return int
     */
    int updateIsMaxById(ExamUserScore examUserScore);
    
    /**
     * 
    * @Title: selectExpertResultByCondition
    * @author ZhaoBo
    * @date 2016-9-23 下午4:08:52  
    * @Description: 专家考试成绩按条件查询  
    * @param @param map
    * @param @return      
    * @return List<ExamUserScore>
     */
    List<ExamUserScore> selectExpertResultByCondition(HashMap<String,Object> map);
    
    /**
     * 
    * @Title: selectPurchaserResultByCondition
    * @author ZhaoBo
    * @date 2016-9-22 上午11:13:51  
    * @Description: 采购人考试成绩按条件查询 
    * @param @param map
    * @param @return      
    * @return List<ExamUserScore>
     */
    List<ExamUserScore> selectPurchaserResultByCondition(HashMap<String,Object> map);
    
    /**
     * 
    * @Title: findByUserId
    * @author ZhaoBo
    * @date 2016-9-28 上午11:31:17  
    * @Description: 根据userId查找属于用户的成绩
    * @param @param examUserScore
    * @param @return      
    * @return List<ExamUserScore>
     */
    List<ExamUserScore> findByUserId(HashMap<String,Object> map);
    
    /**
     * 
    * @Title: findAllExpert
    * @author ZhaoBo
    * @date 2016-9-29 下午5:21:46  
    * @Description: 查询所有的专家 
    * @param @return      
    * @return List<Expert>
     */
    List<Expert> findAllExpert();
    
    List<ExamUserScore> findByUserIdAndCode(HashMap<String,Object> map);
    
    /**
     * 
    * @Title: findPurchaserScore
    * @author ZhaoBo
    * @date 2016-11-17 下午11:45:08  
    * @Description: 查看采购人已考成绩  
    * @param @param map
    * @param @return      
    * @return List<ExamUserScore>
     */
    List<ExamUserScore> findPurchaserScore(HashMap<String,Object> map);
    
    /**
     * 
    * @Title: findMaxScoreOfUser
    * @author ZhaoBo
    * @date 2016-11-28 下午2:17:13  
    * @Description: 根据userId查找当前专家最高成绩 
    * @param @param userId
    * @param @return      
    * @return List<ExamUserScore>
     */
    List<ExamUserScore> findMaxScoreOfUser(String userId);
}
