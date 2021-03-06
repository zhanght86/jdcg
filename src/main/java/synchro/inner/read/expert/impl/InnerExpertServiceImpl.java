package synchro.inner.read.expert.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertCategory;
import ses.model.ems.ExpertHistory;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertCategoryService;
import ses.service.ems.ExpertService;
import synchro.inner.read.expert.InnerExpertService;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>读取专家信息service
 * <详细描述>
 * @author   WangHuijie
 * @version  
 * @since
 * @see
 */
@Service
public class InnerExpertServiceImpl implements InnerExpertService {
    
    /** 记录service **/
    @Autowired
    private SynchRecordService synchRecordService;
    
    /** 用户service **/
    @Autowired
    private UserServiceI userService;
    
    /** 专家 service **/
    @Autowired
    private ExpertService expertService;
    
    /** 专家-品目service **/
    @Autowired
    private ExpertCategoryService expertCategoryService;
    
    /**
     * 
     * @see synchro.inner.read.expert.InnerExpertService#readNewExpertInfo(java.io.File)
     */
    @Override
    public void readNewExpertInfo(final File file) {
       List<Expert> list = getExpert(file);
       for (Expert expert : list) {
           saveUser(expert.getUser());
           saveExpert(expert);
           saveCategory(expert.getExpertCategory());
       }
       synchRecordService.importNewExpertRecord(new Integer(list.size()).toString());
    }
    
    /**
     * 
     * @see synchro.inner.read.expert.InnerExpertService#readMofidyExpertInfo(java.io.File)
     */
    @Override
    public void readModifyExpertInfo(final File file) {
        List<Expert> list = getExpert(file);
        for (Expert expert : list) {
            updateUser(expert.getUser());
            updateExpert(expert);
            updateCategory(expert.getExpertCategory());
            saveHistory(expert.getHistory());
        }
        synchRecordService.importModifyExpertRecord(new Integer(list.size()).toString());
    }
    
    /**
     *〈简述〉保存用户
     *〈详细描述〉
     * @author WangHuijie
     * @param user 用户
     */
    private void saveUser(User user) {
        if (user != null) {
            userService.saveUser(user);
        }
    }
    
    /**
     *〈简述〉保存专家基本信息
     *〈详细描述〉
     * @author WangHuijie
     * @param expert 专家
     */
    public void saveExpert(Expert expert) {
        if (expert != null) {
            expertService.insertSelective(expert);
        }
    }
    
    /**
     *〈简述〉保存专家品目信息
     *〈详细描述〉
     * @author WangHuijie
     * @param categories 品目List
     */
    public void saveCategory(List<ExpertCategory> categories) {
        if (categories != null && categories.size() > 0) {
            Expert expert = expertService.selectByPrimaryKey(categories.get(0).getExpertId());
            for (ExpertCategory expertCategory : categories) {
                expertCategoryService.save(expert, expertCategory.getCategoryId(), expertCategory.getTypeId());
            }
        }
    }
    
    /**
     *〈简述〉保存专家历史记录
     *〈详细描述〉
     * @author WangHuijie
     * @param history 专家历史信息
     */
    public void saveHistory(ExpertHistory history) {
        if (history != null) {
            expertService.insertExpertHistory(history);
        }
    }
    
    /**
     *〈简述〉修改用户信息
     *〈详细描述〉
     * @author WangHuijie
     * @param user 用户
     */
    public void updateUser(User user) {
        if (user != null) {
            userService.update(user);
        }
    }
    
    /**
     *〈简述〉修改品目信息
     *〈详细描述〉
     * @author WangHuijie
     * @param categories 品目List
     */
    public void updateCategory(List<ExpertCategory> categories) {
        if (categories != null && categories.size() > 0) {
            expertCategoryService.deleteByExpertId(categories.get(0).getExpertId());
            saveCategory(categories);
        }
    }
    
    /**
     *〈简述〉修改专家基本信息
     *〈详细描述〉
     * @author WangHuijie
     * @param expert 专家
     */
    public void updateExpert(Expert expert) {
        expertService.updateByPrimaryKeySelective(expert);
    }
    
    /**
     *〈简述〉获取专家list
     *〈详细描述〉
     * @author WangHuijie
     * @param file 文件
     * @return
     */
    private List<Expert> getExpert(final File file) {
        List<Expert> expertList = FileUtils.getBeans(file, Expert.class); 
        return expertList;
    }
    

}
