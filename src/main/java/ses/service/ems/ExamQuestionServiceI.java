/**
 * 
 */
package ses.service.ems;

import java.util.HashMap;
import java.util.List;
import ses.model.ems.ExamQuestion;



/**
 * 
* @Title:ExamPoolServiceI 
* @Description: 
* @author ZhaoBo
* @date 2016-9-7上午10:01:08
 */
public interface ExamQuestionServiceI {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author ZhaoBo
	* @date 2016-9-7 上午9:57:29  
	* @Description: 根据主键ID删除题目
	* @param @param id
	* @param @return      
	* @return int
	 */
	int deleteByPrimaryKey(String id);
	
	/**
	 * 
	* @Title: insertSelective
	* @author ZhaoBo
	* @date 2016-9-7 上午9:57:49  
	* @Description: 新增题目 
	* @param @param examQuestion
	* @param @return      
	* @return int
	 */
    int insertSelective(ExamQuestion examQuestion);
    
    /**
     * 
    * @Title: selectByPrimaryKey
    * @author ZhaoBo
    * @date 2016-9-7 上午9:58:02  
    * @Description: 根据主键ID查找题目 
    * @param @param id
    * @param @return      
    * @return examQuestion
     */
    ExamQuestion selectByPrimaryKey(String id);
    
    /**
     * 
    * @Title: updateByPrimaryKeySelective
    * @author ZhaoBo
    * @date 2016-9-7 上午9:58:20  
    * @Description:	更新题目
    * @param @param examQuestion
    * @param @return      
    * @return int
     */
    int updateByPrimaryKeySelective(ExamQuestion examQuestion);
    
    /**
     * 
    * @Title: findExpertQuestionList
    * @author zb
    * @date 2016-8-24 上午10:20:30  
    * @Description: 按条件查询各专家题目
    * @param @return      
    * @return List<examQuestion>
     */
    List<ExamQuestion> findExpertQuestionList(HashMap<String,Object> map);
    
    /**
     * 
    * @Title: selectQuestionRandom
    * @author ZhaoBo
    * @date 2016-10-17 上午9:22:18  
    * @Description: 专家类题库中根据当前登录专家的类型随机抽题 
    * @param @param map
    * @param @return      
    * @return List<ExamQuestion>
     */
    List<ExamQuestion> selectQuestionRandom(HashMap<String,Object> map);
    
    /**
     * 
    * @Title: queryPurchaserByTerm
    * @author zb
    * @date 2016-8-31 下午5:34:43  
    * @Description: 采购人题库按条件查询 
    * @param @param examPool
    * @param @return      
    * @return List<examQuestion>
     */
    List<ExamQuestion> queryPurchaserByTerm(HashMap<String, Object> map);
    
    /**
     * 
    * @Title: selectPurchaserQuestionRandom
    * @author ZhaoBo
    * @date 2016-11-22 上午10:08:00  
    * @Description: 采购人随机抽题 
    * @param @param map
    * @param @return      
    * @return List<ExamQuestion>
     */
    List<ExamQuestion> selectPurchaserQuestionRandom(HashMap<String,Object> map);
    
    /**
     * 
    * @Title: searchExpertPool
    * @author ZhaoBo
    * @date 2016-9-21 上午8:52:30  
    * @Description: 查询专家题库中有没有题目 
    * @param @return      
    * @return List<ExamQuestion>
     */
    List<ExamQuestion> searchExpertPool();
    
    /**
     * 
    * @Title: selectByTopic
    * @author ZhaoBo
    * @date 2016-10-11 下午4:42:14  
    * @Description: 根据题干查询题目 
    * @param @return      
    * @return List<ExamQuestion>
     */
    List<ExamQuestion> selectByTopic(HashMap<String,Object> map);
    
    /**
     * 
    * @Title: queryQuestionCount
    * @author ZhaoBo
    * @date 2016-10-12 上午10:55:42  
    * @Description: 查询各专家题库的数量 
    * @param @param map
    * @param @return      
    * @return int
     */
    int queryQuestionCount(HashMap<String,Object> map); 
    
    /**
     * 
    * @Title: queryPurchaserQuestionCount
    * @author ZhaoBo
    * @date 2016-11-2 下午1:56:14  
    * @Description: 查询采购人题库中题目的数量 
    * @param @param map
    * @param @return      
    * @return int
     */
    int queryPurchaserQuestionCount(HashMap<String,Object> map);
    
    /**
     * 
    * @Title: selectByTecTopic
    * @author ZhaoBo
    * @date 2016-11-2 下午1:57:10  
    * @Description:  
    * @param @param map
    * @param @return      
    * @return List<ExamQuestion>
     */
    List<ExamQuestion> selectByTecTopic(HashMap<String,Object> map);
}
