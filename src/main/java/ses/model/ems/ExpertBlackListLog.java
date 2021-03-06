package ses.model.ems;

import java.util.Date;
/**
 * <p>Title:expertBlackListHistory </p>
 * <p>Description: 专家黑名单操作记录</p>
 * @author Xu Qing
 * @date 2016-10-13下午5:45:55
 */
public class ExpertBlackListLog{
	private String id; //主键
	private String operator; //操作人
	private String expertId; //专家id
	private String operationType;  //操作类型
	private Date operationDate; //操作时间
	private Date dateOfPunishment; //处罚日期
	private Integer punishType; //处罚类型
    private String punishDate; //处罚时限
    private String reason; //理由
    private String expertName; //专家名字
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getExpertId() {
		return expertId;
	}
	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public Date getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	public Date getDateOfPunishment() {
		return dateOfPunishment;
	}
	public void setDateOfPunishment(Date dateOfPunishment) {
		this.dateOfPunishment = dateOfPunishment;
	}
	public Integer getPunishType() {
		return punishType;
	}
	public void setPunishType(Integer punishType) {
		this.punishType = punishType;
	}
	public String getPunishDate() {
		return punishDate;
	}
	public void setPunishDate(String punishDate) {
		this.punishDate = punishDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getExpertName() {
		return expertName;
	}
	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}
	
	
}