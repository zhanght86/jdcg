package ses.model.sms;

import java.util.List;

import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;

/**
 * 版权：(C) 版权所有 2011-2016
 * <简述> 供应商品目信息下载封装类
 * <详细描述>
 * @author   WangHuijie
 * @version  1.0.0
 * @since    2017年1月15日10:18:35
 * @see
 */
public class SupplierCateTree {

    /** 根节点 **/
    private String rootNode;
    
    /** 一级节点 **/
    private String firstNode;
    
    /** 二级节点 **/
    private String secondNode;
    
    /** 三级节点 **/
    private String thirdNode;
    
    /** 四级节点 **/
    private String fourthNode;
    
    /** 供应商品目ID **/
    private String itemsId;
    
    /** 等级 **/
    private DictionaryData level;
    
    /** 证书编号 **/
    private String certCode;
    
    /** 该品目所有等级 **/
    private List<Qualification> typeList;
    
    /** 工程对应附件Id **/
    private String fileId;
    
    /** 工程对应附件Id **/
    private String qualificationType;
    
    /** 自定义等级 **/
    private String diyLevel;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public DictionaryData getLevel() {
        return level;
    }

    public void setLevel(DictionaryData level) {
        this.level = level;
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }

    public String getRootNode() {
        return rootNode;
    }

    public void setRootNode(String rootNode) {
        this.rootNode = rootNode;
    }

    public String getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(String firstNode) {
        this.firstNode = firstNode;
    }

    public String getSecondNode() {
        return secondNode;
    }

    public void setSecondNode(String secondNode) {
        this.secondNode = secondNode;
    }

    public String getThirdNode() {
        return thirdNode;
    }

    public void setThirdNode(String thirdNode) {
        this.thirdNode = thirdNode;
    }

    public String getFourthNode() {
        return fourthNode;
    }

    public void setFourthNode(String fourthNode) {
        this.fourthNode = fourthNode;
    }

	public String getItemsId() {
		return itemsId;
	}

	public void setItemsId(String itemsId) {
		this.itemsId = itemsId;
	}

    public String getDiyLevel() {
        return diyLevel;
    }

    public void setDiyLevel(String diyLevel) {
        this.diyLevel = diyLevel;
    }

    public List<Qualification> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Qualification> typeList) {
        this.typeList = typeList;
    }

    public String getQualificationType() {
        return qualificationType;
    }

    public void setQualificationType(String qualificationType) {
        this.qualificationType = qualificationType;
    }
    
}
