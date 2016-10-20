/**
 * 
 */
package ses.service.bms.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.NoticeDocumentMapper;
import ses.model.bms.NoticeDocument;
import ses.service.bms.NoticeDocumentService;
import ses.util.PropertiesUtil;

/**
 * @Title:NoticeDocumentServiceImpl 
 * @Description: 须知文档管理实现类
 * @author Liyi
 * @date 2016-10-18下午3:58:01
 *
 */
@Service("noticeDocumentService")
public class NoticeDocumentServiceImpl implements NoticeDocumentService{

	@Resource
	private NoticeDocumentMapper noticeDocumentMapper;
	
	@Override
	public List<NoticeDocument> getAll(Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return noticeDocumentMapper.queryByList();
	}

	
	@Override
	public void save(NoticeDocument noticeDocument) {
		noticeDocument.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		noticeDocumentMapper.insertSelective(noticeDocument);
	}

	@Override
	public void update(NoticeDocument noticeDocument) {
		noticeDocument.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		noticeDocumentMapper.updateByPrimaryKeySelective(noticeDocument);
	}

	@Override
	public NoticeDocument get(String id) {
		
		return noticeDocumentMapper.selectByPrimaryKey(id);
	}

	
	@Override
	public void delete(String id) {
		noticeDocumentMapper.deleteByPrimaryKey(id);
		
	}

	
	@Override
	public List<NoticeDocument> search(Integer pageNum,
			NoticeDocument noticeDocument) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return noticeDocumentMapper.selectByType(noticeDocument);
	}

}