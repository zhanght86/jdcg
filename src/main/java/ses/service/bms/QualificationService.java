package ses.service.bms;

import java.util.List;

import ses.model.bms.Qualification;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>Qualification service
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public   interface  QualificationService {
    
    /**
     * 
     *〈简述〉分页查询qualification
     *〈详细描述〉
     * @author myc
     * @param  pageNum 当前页
     * @param  name 查询条件
     * @param  type 类型
     * @return Qualification 集合
     */
    public List<Qualification> findList(Integer pageNum, String name, Integer type);
    
    /**
     * 
     *〈简述〉保存
     *〈详细描述〉
     * @author myc
     * @param type 类型
     * @param name 名称
     * @param operaType 操作类型
     * @param id 主键
     * @return Qualification 对象
     */
    public Qualification save(String type, String name, String operaType, String id);
    
    /**
     * 
     *〈简述〉更新 Qualification
     *〈详细描述〉
     * @author myc
     * @param qualification {@link Qualification}对象
     */
    public void update(Qualification qualification);
    
    
    /**
     * 
     *〈简述〉根据Id 查询
     *〈详细描述〉
     * @author myc
     * @param id 主键
     * @return Qualification 对象
     */
    public Qualification getQualification(String id);
    
    /**
     * 
     *〈简述〉根据主键Id进行删除
     *〈详细描述〉id 主键,如果是多个主键时使用逗号进行分割,如：1,2,3
     * @author myc
     * @param id 主键
     * @return 成功返回ok
     */
    public String del(String id);
}
