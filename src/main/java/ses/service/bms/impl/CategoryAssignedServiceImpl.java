package ses.service.bms.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.CategoryAssignedMapper;
import ses.model.bms.CategoryAssigned;
import ses.service.bms.CategoryAssignedService;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *  产品分配service
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */

@Service
public class CategoryAssignedServiceImpl implements CategoryAssignedService {
    
    /** 分割符号 */
    private static final String SPLIT_SYMBOL = ",";
    /** 需求部门提示  */
    private static final String ORG_TIPS ="请选择需求部门";
    /** 产品分类提示  */
    private static final String CATE_TIPS ="请选择产品分类";
    /** 成功提示 */
    private static final String SUCCESS = "ok";
    
    /** 阈值 */
    private static final int COUNT_COMMIT = 10;
    
    /** 产品分配持久层 */
    @Autowired
    private CategoryAssignedMapper mapper;
    
    /** 注册 SqlSessionFactory */
    @Autowired
    private SqlSessionFactory sqlSessionFactory; 
    
    
    /**
     * 
     * @see ses.service.bms.CategoryAssignedService#assigned(java.lang.String, java.lang.String)
     */
    @Override
    public String assigned(String orgIds, String cateIds) {
        
        String resMsg = "";
        
        if (!StringUtils.isNotBlank(orgIds)){
            resMsg = ORG_TIPS;
            return resMsg;
        }
        
        if (!StringUtils.isNotBlank(cateIds)) {
            resMsg = CATE_TIPS;
            return resMsg;
        }
        
        List<String> orgList = new ArrayList<String>();
        if (orgIds.indexOf(SPLIT_SYMBOL) != -1) {
            String [] argArry = orgIds.split(SPLIT_SYMBOL);
            orgList.addAll(Arrays.asList(argArry));
        } else {
            orgList.add(orgIds);
        }
        
        List<String> cateList = new ArrayList<String>();
        if (cateIds.indexOf(SPLIT_SYMBOL) != -1) {
            String [] cateArry = cateIds.split(SPLIT_SYMBOL);
            cateList.addAll(Arrays.asList(cateArry));
        } else {
            cateList.add(cateIds);
        }
        
        SqlSession batchSqlSession = null;
        try {
            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            int index = 0;
            List<CategoryAssigned> caList = new ArrayList<CategoryAssigned>();
            for (String cateId : cateList) {
                for (String orgId : orgList) {
                    batchSqlSession.getMapper(CategoryAssignedMapper.class).batchaDelete(cateId, orgId);
                    index ++ ;
                    CategoryAssigned ca = new CategoryAssigned();
                    ca.setOrgId(orgId);
                    ca.setCateId(cateId);
                    ca.setCreatedAt(new Date());
                    ca.setUpdatedAt(new Date());
                    caList.add(ca);
                    
                    if (index % COUNT_COMMIT == 0) {
                        batchSqlSession.commit();
                    }
                }
            }
            batchSqlSession.commit();
            batchSave(caList, batchSqlSession);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (batchSqlSession != null){
                batchSqlSession.close();
            }
        }
        
       
        
        return SUCCESS;
    }
    
    
    /**
     * 
     *〈简述〉
     *  批量保存
     *〈详细描述〉
     * @author myc
     * @param list  {@link CategoryAssigned } 集合
     * @param batchSqlSession {@link SqlSession}
     */
    private void batchSave(List<CategoryAssigned> list, SqlSession batchSqlSession){
        
        int count = 0;
        for (CategoryAssigned ca : list){
             batchSqlSession.getMapper(CategoryAssignedMapper.class).batchInsert(ca);
             count ++ ;
            
             if (count % COUNT_COMMIT == 0) {
                batchSqlSession.commit();
             }
        }
        batchSqlSession.commit();
    }

}