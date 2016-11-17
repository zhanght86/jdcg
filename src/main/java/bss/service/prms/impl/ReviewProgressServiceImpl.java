package bss.service.prms.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.prms.PackageExpertMapper;
import bss.dao.prms.ReviewProgressMapper;
import bss.model.prms.PackageExpert;
import bss.model.prms.ReviewProgress;
import bss.service.prms.ReviewProgressService;
import ses.util.WfUtil;
@Service("reviewProgressService")
public class ReviewProgressServiceImpl implements ReviewProgressService {

	@Autowired
	private ReviewProgressMapper mapper;
	@Autowired
	private PackageExpertMapper packageExpertMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int save(ReviewProgress record) {
		// TODO Auto-generated method stub
		record.setId(WfUtil.createUUID());
		return mapper.insert(record);
	}

	@Override
	public ReviewProgress getById(String id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(ReviewProgress record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(ReviewProgress record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public void updateByMap(ReviewProgress record) {
		// TODO Auto-generated method stub
		mapper.updateByMap(record);

	}

	@Override
	public List<ReviewProgress> selectByMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		return mapper.selectByMap(map);
	}
	  /**
     * 
      * @Title: saveProgress
      * @author ShaoYangYang
      * @date 2016年11月16日 下午5:59:01  
      * @Description: TODO 保存初审信息 更新初审进度
      * @param @param projectId
      * @param @param packageId
      * @param @param expertId      
      * @return void
     */
    public void saveProgress(String projectId,String packageId,String expertId){
    	 List<PackageExpert> packageExpertList2 = null;
				Map<String,Object> map1 = new HashMap<String,Object>(); 
				  map1.put("projectId", projectId);
				  map1.put("packageId", packageId);
				  map1.put("expertId", expertId);
				  List<PackageExpert> selectList = packageExpertMapper.selectList(map1);
				
				  if(selectList!=null && selectList.size()>0){
					  PackageExpert packageExpert = selectList.get(0);
					  packageExpert.setIsAudit((short) 1);
					  packageExpertMapper.updateByBean(packageExpert);
				  }
				  Map<String, Object> map2 = new HashMap<String, Object>();
					map2.put("expertId", expertId);
					map2.put("projectId", projectId);
					map2.put("packageId", packageId);
					map2.put("isAudit", 0);
					//查询出关联表中已经评审的数据
					packageExpertList2 = packageExpertMapper.selectList(map2);
		  
		  Map<String,Object> map = new HashMap<String,Object>(); 
		  map.put("projectId", projectId);
		  map.put("packageId", packageId);
		  List<PackageExpert> packageExpertList = packageExpertMapper.selectList(map);
		  //查询改项目的进度信息
		  List<ReviewProgress> reviewProgressList = selectByMap(map);
		  //初审进度
		  double firstProgress = 0;
		  //总进度
		  double totalProgress = 0;
		  //评分进度
		  double scoreProgress = 0;
		  ReviewProgress reviewProgress = new ReviewProgress();
		  
		  
		  //集合为空证明没有进度信息
		  if(reviewProgressList==null || reviewProgressList.size()==0){
			  if(packageExpertList!=null&& packageExpertList.size()>0){
				  double first =  1/(double)packageExpertList.size();
				  BigDecimal b = new BigDecimal(first); 
				  firstProgress  = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				  //初审进度
				  reviewProgress.setFirstAuditProgress(firstProgress);
				  totalProgress = (firstProgress+scoreProgress)/2;
				  //总进度
				  reviewProgress.setTotalProgress(totalProgress);
				  //评分进度
				  reviewProgress.setScoreProgress(scoreProgress);
				  //状态
				  reviewProgress.setAuditStatus("评审中");
				  reviewProgress.setPackageId(packageId);
				  reviewProgress.setProjectId(projectId);
				  //新增
				  save(reviewProgress);
			  }
		  }else{
			//判断关联集合不为空 从而确定该项目下有多少专家
			  if(packageExpertList!=null&& packageExpertList.size()>0){
				  ReviewProgress reviewProgress2 = reviewProgressList.get(0);
				 // Double firstAuditProgress = reviewProgress2.getFirstAuditProgress();
				  double first = 0;
				  if(packageExpertList2!=null && packageExpertList2.size()>0){
					  first = (double)packageExpertList2.size()/(double)packageExpertList.size()+1/(double)packageExpertList.size();
				  }else{
					  first = 1/(double)packageExpertList.size();
				  }
				  
				  BigDecimal b = new BigDecimal(first); 
				  firstProgress  = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				  //初审进度更新
				  reviewProgress2.setFirstAuditProgress(firstProgress);
				  //总进度更新
				  Double scoreProgress2 = reviewProgress2.getScoreProgress();
				 double total2 =  (firstProgress+scoreProgress2)/2;
				 BigDecimal t = new BigDecimal(total2); 
				 totalProgress  = t.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				  //总进度更新
				 reviewProgress2.setTotalProgress(totalProgress);
				 //修改进度
				 updateByMap(reviewProgress2);
			  }
		  }
    }
    
    /**
     * 
     * @Title: saveGrade
     * @author ShaoYangYang
     * @date 2016年11月16日 下午5:59:01  
     * @Description: TODO 更新评分进度
     * @param @param projectId
     * @param @param packageId
     * @param @param expertId      
     * @return void
     */
    public void saveGrade(String projectId,String packageId,String expertId){
    	 List<PackageExpert> packageExpertList2 = null;
			Map<String,Object> map1 = new HashMap<String,Object>(); 
			  map1.put("projectId", projectId);
			  map1.put("packageId", packageId);
			  map1.put("expertId", expertId);
			  List<PackageExpert> selectList = packageExpertMapper.selectList(map1);
			
			  if(selectList!=null && selectList.size()>0){
				  PackageExpert packageExpert = selectList.get(0);
				  //设置为已评分
				  packageExpert.setIsGrade((short) 1);
				  packageExpertMapper.updateByBean(packageExpert);
			  }
			  Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("expertId", expertId);
				map2.put("projectId", projectId);
				map2.put("packageId", packageId);
				map2.put("isAudit", 0);
				//查询出关联表中已经评审的数据
				packageExpertList2 = packageExpertMapper.selectList(map2);
	  
	  Map<String,Object> map = new HashMap<String,Object>(); 
	  map.put("projectId", projectId);
	  map.put("packageId", packageId);
	  List<PackageExpert> packageExpertList = packageExpertMapper.selectList(map);
	  //查询改项目的进度信息
	  List<ReviewProgress> reviewProgressList = selectByMap(map);
	  //总进度
	  double totalProgress = 0;
	  //评分进度
	  double scoreProgress = 0;
	  ReviewProgress reviewProgress = new ReviewProgress();
	  
	  
	  //集合为空证明没有进度信息
	  if(reviewProgressList==null || reviewProgressList.size()==0){
		  if(packageExpertList!=null&& packageExpertList.size()>0){
			  double first =  1/(double)packageExpertList.size();
			  BigDecimal b = new BigDecimal(first); 
			  scoreProgress  = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			  //评分进度
			  reviewProgress.setScoreProgress(scoreProgress);
			  totalProgress = scoreProgress/2;
			  //总进度
			  reviewProgress.setTotalProgress(totalProgress);
			  //状态
			  reviewProgress.setAuditStatus("评审中");
			  reviewProgress.setPackageId(packageId);
			  reviewProgress.setProjectId(projectId);
			  //新增
			  save(reviewProgress);
		  }
	  }else{
		//判断关联集合不为空 从而确定该项目下有多少专家
		  if(packageExpertList!=null&& packageExpertList.size()>0){
			  ReviewProgress reviewProgress2 = reviewProgressList.get(0);
			 // Double firstAuditProgress = reviewProgress2.getFirstAuditProgress();
			  double score = 0;
			  if(packageExpertList2!=null && packageExpertList2.size()>0){
				  score = (double)packageExpertList2.size()/(double)packageExpertList.size()+1/(double)packageExpertList.size();
			  }else{
				  score = 1/(double)packageExpertList.size();
			  }
			  
			  BigDecimal b = new BigDecimal(score); 
			  scoreProgress  = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			  //初审进度更新
			  reviewProgress2.setScoreProgress(scoreProgress);
			  //总进度更新
			  Double firstProgress2 = reviewProgress2.getFirstAuditProgress();
			 double total2 =  (scoreProgress+firstProgress2)/2;
			 BigDecimal t = new BigDecimal(total2); 
			 totalProgress  = t.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			  //总进度更新
			 reviewProgress2.setTotalProgress(totalProgress);
			 if(totalProgress==1){
				 reviewProgress2.setAuditStatus("评审完成");
			 }
			 //修改进度
			 updateByMap(reviewProgress2);
		  }
	  }
    }
}
