package synchro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.util.PropUtil;
import synchro.dao.SynchRecordMapper;
import synchro.model.SynchRecord;
import synchro.service.SynchService;


/**
 * 
 * 版权：(C) 版权所有 
 * <简述>实现类
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class SynchServiceImpl implements SynchService {
    
    /** 数据同步mapper **/
    @Autowired
    private SynchRecordMapper mapper;
    
    /**
     * 
     * @see synchro.service.SynchService#getList(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @Override
    public List<SynchRecord> getList(Integer optype, Integer page, String searchType, String startTime, String endTime) {
        if (page == null){
            page = 1;
        }
        PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("file.upload.maxFileSize")));
        return mapper.getSynchRecordByOperType(optype, searchType, startTime, endTime);
    }

}
