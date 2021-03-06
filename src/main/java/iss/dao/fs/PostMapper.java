package iss.dao.fs;

import iss.model.fs.Post;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
* @Title:PostMapper 
* @Description: 帖子持久化接口
* @author Peng Zhongjun
* @date 2016-9-7下午6:23:44
 */
public interface PostMapper {	
	/**   
	* @Title: queryByCount
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:57:10  
	* @Description: 根据版块ID查询记录数
	* @param:parkId
	* @return BigDecimal     
	*/
	BigDecimal queryByCount(Post post);
	
	/**   
	* @Title: queryByList
	* @author Peng Zhongjun
	* @date 2016-8-4 下午4:55:58  
	* @Description: 条件查询
	* @param post
	* @return List<Post>     
	*/
	List<Post> queryByList(Map<String,Object> map);
	
	/**   
	* @Title: selectByPrimaryKey
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:51:54  
	* @Description: 根据Id查询
	* @param id
	* @return Post
	*/
    Post selectByPrimaryKey(String id);
    

    
    /**   
	* @Title: deleteByPrimaryKey
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:51:54  
	* @Description: 根据Id删除
	* @param  id
	*/
    void deleteByPrimaryKey(String id);
    /**   
	* @Title: insertSelective
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:51:54  
	* @Description: 新增帖子
	* @param post
	*/
    void insertSelective(Post post);
    
    /**   
	* @Title: updateByPrimaryKeySelective
	* @author Peng Zhongjun
	* @date 2016-8-4下午4:51:54  
	* @Description: 更新
	* @param  post
	*/
    void updateByPrimaryKeySelective(Post post);
    
    /**   
	* @Title: selectByPrimaryKey
	* @author Peng Zhongjun
	* @date 2016-8-22下午20:03:12
	* @Description: 根据主题Id查询
	* @param id
	* @return List<Post> 
	*/
    List<Post> selectByTopicID(String topicID);
    /**   
	* @Title: selectByPrimaryKey
	* @author Peng Zhongjun
	* @date 2016-8-22下午20:03:12
	* @Description: 根据版块Id查询5条记录
	* @param id
	* @return List<Post> 
	*/
    List<Post> selectByParkID(String parkID);
    /**   
	* @Title: queryHotPost
	* @author Peng Zhongjun
	* @date 2016-9-23下午20:03:12
	* @Description: 查询热门帖子
	* @param 
	* @return List<Post> 
	*/
    List<Post> queryHotPost();
    /**
     * 
    * @Title: queryAllHotPost
    * @author Peng Zhongjun
    * @date 2016-10-4 上午9:51:05  
    * @Description: 查询所有热门帖子 
    * @param @return      
    * @return List<Post>
     */
    List<Post> queryAllHotPost();
    
    /**
     * 
    * @Title: selectParkTopPost
    * @author Peng Zhongjun
    * @date 2016-10-18 下午5:09:50  
    * @Description: 查询该版块下的置顶帖子 
    * @param @param parkId
    * @param @return      
    * @return Post
     */
    Post selectParkTopPost(String parkId);
    /**
     * 
    * @Title: queryMyPost
    * @author Peng Zhongjun
    * @date 2016-11-24 下午2:34:49  
    * @Description: 查询自己发表的帖子 
    * @param @param map
    * @param @return      
    * @return List<Post>
     */
    List<Post> queryMyPost(Map<String,Object> map);
    
    /**
     * 
    * @Title: selectByTopicIdAndName
    * @author ZhaoBo
    * @date 2016-12-7 下午2:31:51  
    * @Description: 查询主题下的帖子 
    * @param @param map
    * @param @return      
    * @return List<Post>
     */
    List<Post> selectByTopicIdAndName(Map<String,Object> map);
}