package bss.service.ppms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.http.ResponseEntity;

import ses.model.bms.User;

import bss.model.ppms.Project;

/**
 * 
 * @Title:ProjectService
 * @Description: 项目管理业务逻辑接口 
 * @author FengTian
 * @date 2016-9-27上午10:19:28
 */
public interface ProjectService {
    /**
     * 
     * @Title: add
     * @author FengTian
     * @date 2016-9-27 上午10:20:15  
     * @Description: 添加 
     * @param @param project      
     * @return void
     */
    void add(Project project);
    /**
     * 	
     * @Title: update
     * @author FengTian
     * @date 2016-9-27 上午10:21:04  
     * @Description: 修改 
     * @param @param project      
     * @return void
     */
    void update(Project project);
    /**
     * 	
     * @Title: delete
     * @author FengTian
     * @date 2016-9-27 上午10:21:47  
     * @Description: 删除 
     * @param @param id      
     * @return void
     */
    void delete(String id);
    /**
     * 	
     * @Title: selectById
     * @author FengTian
     * @date 2016-9-27 上午10:22:36  
     * @Description: 根据id查询 
     * @param @param id
     * @param @return      
     * @return Project
     */
    Project selectById(String id);
    /**
     * 	
     * @Title: list
     * @author FengTian
     * @date 2016-9-27 上午10:23:49  
     * @Description: 分页查询 
     * @param @param page
     * @param @param project
     * @param @return      
     * @return List<Project>
     */
    List<Project> list(Integer page,Project project);

    List<Project> lists(Integer page,Project project);

    List<Project> selectSuccessProject(Map<String,Object> map);

    boolean SameNameCheck( Project project);

    /**
     * 
     *〈简述〉查询临时项目
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param page 分页
     * @param project 项目
     * @return   List<Project>
     */
    List<Project>  provisionalList(Integer page, Project project);
    
    List<Project> selectProjectByCode(HashMap<String,Object> map);
    
    
    void insert(Project project);
    
    ResponseEntity<byte[]> downloadFile(String fileName,String filePath,String downFileName);
    
    List<Project> selectProjectsByConition(HashMap<String,Object> map);
    
    int updatePurchaseDep(Project project);
    
    /**
     *〈简述〉获取下一流程环节
     *〈详细描述〉
     * @author Ye MaoLin
     * @param user 
     * @param projectId
     * @param flowDefineId
     * @return
     */
    JSONObject getNextFlow(User user, String projectId, String flowDefineId);
    
    /**
     *〈简述〉变更当前环节经办人
     *〈详细描述〉
     * @author Ye MaoLin
     * @param currFlowDefineId
     * @param currUpdateUserId
     * @param currUpdateUserId2 
     * @return
     */
    JSONObject updateCurrOperator(String currFlowDefineId, String currUpdateUserId, String currUpdateUserId2);
    
    /**
     *〈简述〉更新项目状态
     *〈详细描述〉
     * @author Ye MaoLin
     * @param project
     * @param code
     */
    void updateStatus(Project project, String code);
}
