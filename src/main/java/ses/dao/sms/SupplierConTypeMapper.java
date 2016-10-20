package ses.dao.sms;

import ses.model.sms.SupplierConType;

public interface SupplierConTypeMapper {
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
    int insert(SupplierConType record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierConType record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierConType selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierConType record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierConType record);
    
    /**
     * @Description:删除数据
     *
     * @author Wang Wenshuai
     * @version 2016年10月12日 下午3:40:50  
     * @param @param id
     * @param @return      
     * @return int
     */
    int deleteConditionId(String id);
}