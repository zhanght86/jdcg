package ses.dao.ems;

import java.util.List;

import ses.model.ems.ExpertAudit;

public interface ExpertAuditMapper {
    /**
	 * 
	  * @Title: deleteByPrimaryKey
	  * @author ShaoYangYang
	  * @date 2016年9月26日 下午2:26:23  
	  * @Description: TODO 根据主键删除
	  * @param @param id
	  * @param @return      
	  * @return int
	 */
    void deleteByPrimaryKey(String id);
    /**
     * 
      * @Title: insert
      * @author ShaoYangYang
      * @date 2016年9月26日 下午2:26:34  
      * @Description: TODO 增加  可为空
      * @param @param record
      * @param @return      
      * @return int
     */
    int insert(ExpertAudit record);
    /**
     * 
      * @Title: insertSelective
      * @author ShaoYangYang
      * @date 2016年9月26日 下午2:27:05  
      * @Description: TODO 新增不为空的
      * @param @param record
      * @param @return      
      * @return int
     */
    void insertSelective(ExpertAudit record);
    /**
     * 
      * @Title: selectByPrimaryKey
      * @author ShaoYangYang
      * @date 2016年9月26日 下午2:27:18  
      * @Description: TODO 根据主键查询
      * @param @param id
      * @param @return      
      * @return ExpertAudit
     */
    ExpertAudit selectByPrimaryKey(String id);
    /**
     * 
      * @Title: updateByPrimaryKeySelective
      * @author ShaoYangYang
      * @date 2016年9月26日 下午2:27:33  
      * @Description: TODO 修改不为空的数据
      * @param @param record
      * @param @return      
      * @return int
     */
    int updateByPrimaryKeySelective(ExpertAudit record);
    /**
     * 
      * @Title: updateByPrimaryKey
      * @author ShaoYangYang
      * @date 2016年9月26日 下午2:27:47  
      * @Description: TODO 修改全部
      * @param @param record
      * @param @return      
      * @return int
     */
    int updateByPrimaryKey(ExpertAudit record);
    /**
     * 
      * @Title: selectByExpertId
      * @author ShaoYangYang
      * @date 2016年9月26日 下午3:59:32  
      * @Description: TODO 根据专家id查询一个集合
      * @param @param expertId
      * @param @return      
      * @return List<ExpertAudit>
     */
    List<ExpertAudit> selectByExpertId(String expertId);
    
    /**
     * 
    * @Title: findResultByExpertId
    * @author ZhaoBo
    * @date 2016-11-28 下午12:45:59  
    * @Description: 根据专家ID查询审核通过的专家 
    * @param @param expertId
    * @param @return      
    * @return List<ExpertAudit>
     */
    List<ExpertAudit> findResultByExpertId(String expertId);
    
    /**
     * 
    * @Title: findAllPassExpert
    * @author ZhaoBo
    * @date 2016-11-28 下午3:08:25  
    * @Description: 查找所有审核通过的专家 
    * @param @return      
    * @return List<ExpertAudit>
     */
    List<ExpertAudit> findAllPassExpert();
    
    List<ExpertAudit> selectFailByExpertId (ExpertAudit expertAudit);
    
    /**
     * @Title: updateByExpertId
     * @author XuQing 
     * @date 2016-12-27 上午11:00:46  
     * @Description:更新isdelete
     * @param @param expertId      
     * @return void
     */
    void updateIsDeleteByExpertId (String expertId); 
    
    /**
     * @Title: deleteByExpertId
     * @author XuQing 
     * @date 2017-2-14 下午5:05:58  
     * @Description:删除记录
     * @param @param expertId      
     * @return void
     */
    void deleteByExpertId (String expertId);
}