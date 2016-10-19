package ses.service.ems;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import ses.model.bms.User;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.Expert;
import ses.model.ems.ExtConType;


/**
 * 
  * <p>Title:ExpertService </p>
  * <p>Description: </p> 评审专家服务接口
  * <p>Company: ses </p> 
  * @author lkzx
  * @date 2016年8月31日下午6:11:07
 */
public interface ExpertService {
	/**
	 * 
	  * @Title: deleteByPrimaryKey
	  * @author lkzx 
	  * @date 2016年8月31日 下午6:15:50  
	  * @Description: TODO 根据主键删除
	  * @param @param id      
	  * @return void
	 */
	    void deleteByPrimaryKey(String id);
	 /**
	  * 
	   * @Title: insert
	   * @author lkzx 
	   * @date 2016年8月31日 下午6:16:17  
	   * @Description: TODO 新增评审专家信息
	   * @param @param record
	   * @param @return      
	   * @return int
	  */
	    int insertSelective(Expert record);
	    /**
	     * 
	      * @Title: selectByPrimaryKey
	      * @author lkzx 
	      * @date 2016年8月31日 下午6:16:47  
	      * @Description: TODO 根据id查询评审专家
	      * @param @param id
	      * @param @return      
	      * @return Expert
	     */
	    Expert selectByPrimaryKey(String id);
	    
	    /**
	      * @Title: updateByPrimaryKey
	      * @author lkzx 
	      * @date 2016年8月31日 下午6:17:46  
	      * @Description: TODO 修改评审专家信息
	      * @param @param record      
	      * @return void
	     */
	    void updateByPrimaryKeySelective(Expert record);
	    /**
	     * 
	      * @Title: selectLoginNameList
	      * @author lkzx 
	      * @date 2016年9月1日 下午4:51:03  
	      * @Description: TODO 查询所有登录名
	      * @param @return      
	      * @return List<String>
	     */
	   // List<Expert> selectLoginNameList(String loginName);
	    /**
	     * 
	      * @Title: selectAllExpert
	      * @author lkzx 
	      * @date 2016年9月2日 下午5:42:05  
	      * @Description: TODO 查询所有专家
	      * @param @return      
	      * @return List<Expert>
	     */
	    List<Expert> selectAllExpert(Integer pageNum,Expert expert);
	    /***
	     * 
	      * @Title: getCount
	      * @author ShaoYangYang
	      * @date 2016年9月12日 下午4:00:10  
	      * @Description: TODO 查询审核专家数量
	      * @param @param expert
	      * @param @return      
	      * @return Integer
	     */
	    Integer getCount(Expert expert);
	    /**
	     * 
	      * @Title: getUserById
	      * @author ShaoYangYang
	      * @date 2016年9月13日 下午6:13:59  
	      * @Description: TODO 根据用户id查询用户
	      * @param @param userId
	      * @param @return      
	      * @return User
	     */
	    User getUserById(String userId);
	    /**
	     * 
	      * @Title: uploadFile
	      * @author ShaoYangYang
	      * @date 2016年9月22日 下午1:53:44  
	      * @Description: TODO 文件上传
	      * @param @param files
	      * @param @param realPath      
	      * @return void
	     */
	    public void uploadFile(MultipartFile[] files, String realPath,String expertId);
	    /**
	     * 
	      * @Title: downloadFile
	      * @author ShaoYangYang
	      * @date 2016年9月22日 下午2:07:23  
	      * @Description: TODO 文件下载
	      * @param @param fileName
	      * @param @param filePath
	      * @param @return      
	      * @return ResponseEntity<byte[]>
	     */
		ResponseEntity<byte[]> downloadFile(String fileName, String filePath, String downFileName); 
		/**
		 * 
		  * @Title: editBasicInfo
		  * @author ShaoYangYang
		  * @date 2016年9月27日 下午1:51:24  
		  * @Description: TODO 修改个人信息
		  * @param @param expert
		  * @param @param user      
		  * @return void
		 */
		void editBasicInfo(Expert expert,User user);
		/**
		 * 
		  * @Title: loginRedirect
		  * @author ShaoYangYang
		  * @date 2016年9月30日 下午2:47:43  
		  * @Description: TODO 登录判断跳转
		  * @param @param user
		  * @param @throws Exception      
		  * @return Map<String,Object>
		 */
		Map<String,Object> loginRedirect(User user) throws Exception;
		/**
		 * 
		  * @Title: zanCunInsert
		  * @author ShaoYangYang
		  * @date 2016年9月30日 下午5:11:37  
		  * @Description: TODO 暂存方法提取
		  * @param @param expert
		  * @param @throws Exception      
		  * @return void
		 */
		void zanCunInsert(Expert expert,String expertId,MultipartFile[] files,String realPath,String categoryIds) throws Exception;
		/**
		 * 
		  * @Title: saveOrUpdate
		  * @author ShaoYangYang
		  * @date 2016年9月30日 下午5:46:47  
		  * @Description: TODO controller调用 逻辑
		  * @param @param expert
		  * @param @param expertId
		  * @param @param files
		  * @param @param realPath
		  * @param @throws Exception      
		  * @return void
		 */
		void saveOrUpdate(Expert expert,String expertId,MultipartFile[] files,String realPath,String categoryIds) throws Exception;
		/**
		 * 
		  * @Title: userManager
		  * @author ShaoYangYang
		  * @date 2016年9月30日 下午6:04:36  
		  * @Description: TODO 处理用户信息
		  * @param @throws Exception      
		  * @return void
		 */
		void userManager(User user,String userId,Expert expert,String expertId) throws Exception;
		
		/**
		 * @Description:抽取专家
		 *
		 * @author Wang Wenshuai
		 * @version 2016年10月14日 下午2:57:12  
		 * @param @param conType      
		 * @return void
		 */
		List<Expert> listExtractionExpert(ExpExtCondition conType);
}
