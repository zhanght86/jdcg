package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bss.model.ppms.Project;

public interface ProjectMapper {
    int deleteByPrimaryKey(String id);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectProjectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
    
    List<Project> selectProjectByAll(Project project);
    
    List<Project> selectByList(Project project);
    
    List<Project> verifyByProject(Project project);
    
    List<Project> selectSuccessProject(Map<String,Object> map);
    
    List<Project> selectProject(HashMap<String,Object> map);
    
    List<Project> provisionalList(Project project);
    
    List<Project> selectProjectByCode(HashMap<String,Object> map);
    
    int insertId(Project record);
    
    List<Project> selectProjectsByConition(HashMap<String,Object> map);
    
    int updatePurchaseDep(Project project);
}