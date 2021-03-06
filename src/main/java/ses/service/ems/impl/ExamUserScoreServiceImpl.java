/**
 * 
 */
package ses.service.ems.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExamUserScoreMapper;
import ses.dao.ems.ExpertMapper;
import ses.model.ems.ExamUserScore;
import ses.model.ems.Expert;
import ses.service.ems.ExamUserScoreServiceI;

/**
 * @Title:ExamUserScoreServiceImpl 
 * @Description:用户成绩ServiceImpl类
 * @author ZhaoBo
 * @date 2016-8-30下午1:31:49
 */
@Service("examUserScoreService")
public class ExamUserScoreServiceImpl implements ExamUserScoreServiceI {
	@Autowired
	private ExamUserScoreMapper examUserScoreMapper;
	@Autowired
	private ExpertMapper expertMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return examUserScoreMapper.deleteByPrimaryKey(id);
	}

	
	@Override
	public int insertSelective(ExamUserScore examUserScore) {
		return examUserScoreMapper.insertSelective(examUserScore);
	}

	
	@Override
	public ExamUserScore selectByPrimaryKey(String id) {
		return examUserScoreMapper.selectByPrimaryKey(id);
	}

	
	@Override
	public int updateByPrimaryKeySelective(ExamUserScore examUserScore) {
		return examUserScoreMapper.updateByPrimaryKeySelective(examUserScore);
	}

	
	@Override
	public List<ExamUserScore> selectExpertResultByCondition(HashMap<String, Object> map) {
		return examUserScoreMapper.selectExpertResultByCondition(map);
	}

	
	@Override
	public List<ExamUserScore> selectPurchaserResultByCondition(HashMap<String,Object> map) {
		return examUserScoreMapper.selectPurchaserResultByCondition(map);
	}

	
	@Override
	public List<ExamUserScore> findByUserId(HashMap<String,Object> map) {
		return examUserScoreMapper.findByUserId(map);
	}

	
	@Override
	public int updateIsMaxById(ExamUserScore examUserScore) {
		return examUserScoreMapper.updateIsMaxById(examUserScore);
	}

	
	@Override
	public List<Expert> findAllExpert() {
		return expertMapper.selectAllExpert(null);
	}

	
	@Override
	public List<ExamUserScore> findByUserIdAndCode(HashMap<String, Object> map) {
		return examUserScoreMapper.findByUserIdAndCode(map);
	}

	
	@Override
	public List<ExamUserScore> findPurchaserScore(HashMap<String, Object> map) {
		return examUserScoreMapper.findPurchaserScore(map);
	}

	
	@Override
	public List<ExamUserScore> findMaxScoreOfUser(String userId) {
		return examUserScoreMapper.findMaxScoreOfUser(userId);
	}
}
