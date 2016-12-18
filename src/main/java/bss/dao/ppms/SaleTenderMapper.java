package bss.dao.ppms;

import java.util.List;

import bss.model.ppms.SaleTender;

public interface SaleTenderMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(SaleTender record);

    /**
     *
     * @param record
     */
    int insertSelective(SaleTender record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SaleTender selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SaleTender record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SaleTender record);

    /**
     * 
     *〈简述〉返回集合
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param record
     * @return
     */
    List<SaleTender> list(SaleTender record);

    /**
     *
     *〈简述〉返回上传的数量
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param businessId
     * @return
     */
    Integer uploadCount(String businessId);
    
    
    /**
     * 
    * @Title: getPackegeSupplier
    * @author Shen Zhenfei 
    * @date 2016-12-12 下午4:54:51  
    * @Description: 根据项目包名，获取供应商
    * @param @param record
    * @param @return      
    * @return List<SaleTender>
     */
    List<SaleTender> getPackegeSupplier(SaleTender record);
    
    
    /**
    * @Title: getPackegeSuppliers
    * @author Shen Zhenfei 
    * @date 2016-12-18 上午11:06:58  
    * @Description: 根据项目包名，获取对应的供应商
    * @param @param record
    * @param @return      
    * @return List<SaleTender>
     */
    List<SaleTender> getPackegeSuppliers(SaleTender record);
    
    
}