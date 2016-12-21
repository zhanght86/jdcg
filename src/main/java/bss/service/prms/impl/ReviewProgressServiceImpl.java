package bss.service.prms.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.PackageMapper;
import bss.dao.prms.PackageExpertMapper;
import bss.dao.prms.ReviewFirstAuditMapper;
import bss.dao.prms.ReviewProgressMapper;
import bss.model.ppms.Packages;
import bss.model.prms.PackageExpert;
import bss.model.prms.ReviewFirstAudit;
import bss.model.prms.ReviewProgress;
import bss.service.prms.ReviewProgressService;
import ses.util.WfUtil;
@Service("reviewProgressService")
public class ReviewProgressServiceImpl implements ReviewProgressService {

	@Autowired
	private ReviewProgressMapper mapper;
	@Autowired
	private PackageExpertMapper packageExpertMapper;
	@Autowired
	private PackageMapper packageMapper;
	@Autowired
	private ReviewFirstAuditMapper reviewFirstAuditMapper;
	
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
    	 if(selectList != null && selectList.size() > 0){
    		 PackageExpert packageExpert = selectList.get(0);
    		 //设置为已评审
    		 packageExpert.setIsAudit((short) 1);
    		 packageExpertMapper.updateByBean(packageExpert);
    	 }
    	//将评审结果改为退回状态
       Map<String, Object> rfaMap = new HashMap<String, Object>();
       rfaMap.put("expertId", expertId);
       rfaMap.put("packageId", packageId);
       rfaMap.put("projectId", projectId);
       List<ReviewFirstAudit> rfas =  reviewFirstAuditMapper.selectList(rfaMap);
       for (ReviewFirstAudit reviewFirstAudit : rfas) {
         //设置状态为未退回
         reviewFirstAudit.setIsBack(0);
         reviewFirstAuditMapper.update(reviewFirstAudit);
       }
    	 Map<String, Object> map2 = new HashMap<String, Object>();
		 /*map2.put("expertId", expertId);*/
		 map2.put("projectId", projectId);
		 map2.put("packageId", packageId);
		 map2.put("isAudit", 1);
		 //查询出关联表中包下已评审的数据
		 packageExpertList2 = packageExpertMapper.selectList(map2);
		 Map<String,Object> map = new HashMap<String,Object>(); 
		 map.put("projectId", projectId);
		 map.put("packageId", packageId);
		 //查询出关联表中包下所有的数据
		 List<PackageExpert> packageExpertList = packageExpertMapper.selectList(map);
		 //查询该项目该包的进度信息
		 List<ReviewProgress> reviewProgressList = selectByMap(map);
		 //初审进度
		 double firstProgress = 0;
		 //总进度
		 double totalProgress = 0;
		 //评分进度
		 double scoreProgress = 0;
		 ReviewProgress reviewProgress = new ReviewProgress();
		 //集合为空证明没有进度信息
		 if (reviewProgressList == null || reviewProgressList.size() == 0) {
			 if (packageExpertList != null && packageExpertList.size() > 0) {
			     double first =  1/(double)packageExpertList.size();
				 BigDecimal b = new BigDecimal(first); 
				 firstProgress  = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
				 //初审进度
				 reviewProgress.setFirstAuditProgress(firstProgress);
				 //总进度
				 totalProgress = (firstProgress+scoreProgress)/2;
				 BigDecimal c = new BigDecimal(totalProgress); 
				 totalProgress = c.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    			 reviewProgress.setTotalProgress(totalProgress);
    			 //评分进度
    			 reviewProgress.setScoreProgress(scoreProgress);
    			 //状态
    			 if (packageExpertList.size() == 1) {
    			     //设置状态为初审完成
    			     reviewProgress.setAuditStatus("2");
    			 } else {
    			     //设置状态为初审中
                     reviewProgress.setAuditStatus("1");
    			 }
    			 reviewProgress.setPackageId(packageId);
    			 HashMap<String, Object> packageMap = new HashMap<String, Object>();
    			 packageMap.put("projectId", projectId);
    			 packageMap.put("id", packageId);
    			 List<Packages> packages = packageMapper.findPackageById(packageMap);
    			 if (packages != null && packages.size() > 0) {
    			     reviewProgress.setPackageName(packages.get(0).getName());
    			 }
    			 reviewProgress.setProjectId(projectId);
    			 //新增
    			 save(reviewProgress);
			  }
		  } else {
		      //判断关联集合不为空 从而确定该项目下有多少专家
			  if (packageExpertList != null && packageExpertList.size() > 0) {
				  ReviewProgress reviewProgress2 = reviewProgressList.get(0);
				 // Double firstAuditProgress = reviewProgress2.getFirstAuditProgress();
				  double first = 0;
				  if (packageExpertList2 != null && packageExpertList2.size() > 0) {
					 // first = (double)packageExpertList2.size()/(double)packageExpertList.size()+1/(double)packageExpertList.size();
				      first = (double)(packageExpertList2.size())/(double)packageExpertList.size();
				  } else {
					  first = 1/(double)packageExpertList.size();
				  }
				  BigDecimal b = new BigDecimal(first); 
				  firstProgress  = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
				  //初审进度更新
				  reviewProgress2.setFirstAuditProgress(firstProgress);
				  //总进度更新
				  Double scoreProgress2 = reviewProgress2.getScoreProgress();
				  double total2 =  (firstProgress+scoreProgress2)/2;
				  BigDecimal t = new BigDecimal(total2); 
				  totalProgress  = t.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
				  //总进度更新
				  reviewProgress2.setTotalProgress(totalProgress);
				  if (packageExpertList2.size() == packageExpertList.size()) {
				      //设置状态为初审完成
				      reviewProgress2.setAuditStatus("2");
				  } else {
				      //设置状态为初审中
				      reviewProgress2.setAuditStatus("1");
				  }
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
			  //scoreProgress  = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			  scoreProgress = 1/(double)packageExpertList.size();
			  //评分进度
			  reviewProgress.setScoreProgress(scoreProgress);
			  totalProgress = scoreProgress/2;
			  //总进度
			  reviewProgress.setTotalProgress(totalProgress);
			  //状态
			  reviewProgress.setAuditStatus("1");
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
			  reviewProgress2.setPackageId(packageId);
			  if(packageExpertList2!=null && packageExpertList2.size()>0){
				  score = (double)packageExpertList2.size()/(double)packageExpertList.size()+1/(double)packageExpertList.size();
			  }else{
				  score = 1/(double)packageExpertList.size();
			  }
			  
			  BigDecimal b = new BigDecimal(score); 
			  //scoreProgress  = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			  scoreProgress = 1/(double)packageExpertList.size();
			  //初审进度更新
			  reviewProgress2.setScoreProgress(scoreProgress);
			  //总进度更新
			  Double firstProgress2 = reviewProgress2.getFirstAuditProgress();
			 double total2 =  (scoreProgress+firstProgress2)/2;
			 BigDecimal t = new BigDecimal(total2); 
			 //totalProgress  = t.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			 totalProgress = (scoreProgress + reviewProgress2.getFirstAuditProgress())/2;
			 //总进度更新
			 //reviewProgress2.setTotalProgress(totalProgress);
			 if(totalProgress==1){
				 reviewProgress2.setAuditStatus("2");
			 }
			 //修改进度
			 updateByMap(reviewProgress2);
			 
			 List<ReviewProgress> list = mapper.selectByMap(map);
			 // 判断如果所有专家都已打分,则改变scoreProgress的值为1,防止出现99.9%的情况
			 List<ReviewFirstAudit> reviewList = reviewFirstAuditMapper.selectList(map);
			 if (reviewList.size() == packageExpertList.size()) {
			     ReviewProgress review = new ReviewProgress();
			     review.setId(list.get(0).getId());
			     review.setScoreProgress((double)1);
			     updateByMap(review);
			 }
			 if(list != null && !list.isEmpty()){
			     ReviewProgress reviewProgress3 = list.get(0);
			     reviewProgress3.setTotalProgress((reviewProgress3.getFirstAuditProgress() + reviewProgress3.getScoreProgress())/2);
			     Map<String, Object> mapSearch = new HashMap<String, Object>();
			     mapSearch.put("id", reviewProgress3.getId());
			     mapSearch.put("totalProgress", reviewProgress3.getTotalProgress());
			     mapper.updateTotalProgress(mapSearch);
			 }
		  }
	  }
    }
    /**
     * 
      * @Title: updateProgress
      * @author ShaoYangYang
      * @date 2016年11月18日 下午6:24:29  
      * @Description: TODO 修改评分退回后的进度  供PackageExpertController调用
      * @param @param map      
      * @return void
     */
   public void updateProgress(Map<String,Object> map){
	   List<ReviewProgress> revList = selectByMap(map);
		for (ReviewProgress reviewProgress : revList) {
			//评分进度清零
			reviewProgress.setScoreProgress(0.0);
			//总进度重新计算
			reviewProgress.setTotalProgress(reviewProgress.getFirstAuditProgress()/2);
			updateByMap(reviewProgress);
		}
   }
   
   /**
    *〈简述〉
    * 根据包id修改评审状态
    *〈详细描述〉
    * @author WangHuijie
    * @param map
    */
    public void updateStatusByMap(Map<String, Object> map) {
        mapper.updateStatusByMap(map);
    }
   
   
}
