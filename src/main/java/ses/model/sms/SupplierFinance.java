package ses.model.sms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SupplierFinance implements Serializable {
	private static final long serialVersionUID = -8432362368196241325L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 会计事务所名称
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.NAME
	 * </pre>
	 */
	private String name;

	/**
	 * <pre>
	 * 年份
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.YEAR
	 * </pre>
	 */
	private Date year;

	/**
	 * <pre>
	 * 事务所联系电话
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.MOBILE
	 * </pre>
	 */
	private Long mobile;

	/**
	 * <pre>
	 * 审计人姓名
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.AUDITORS
	 * </pre>
	 */
	private String auditors;

	/**
	 * <pre>
	 * 指标
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.QUOTA
	 * </pre>
	 */
	private String quota;

	/**
	 * <pre>
	 * 资产总额
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.TOTAL_ASSETS
	 * </pre>
	 */
	private BigDecimal totalAssets;

	/**
	 * <pre>
	 * 负债总额
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.TOTAL_LIABILITIES
	 * </pre>
	 */
	private BigDecimal totalLiabilities;

	/**
	 * <pre>
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.TOTAL_NET_ASSETS
	 * </pre>
	 */
	private BigDecimal totalNetAssets;

	/**
	 * <pre>
	 * 营业收入
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.TAKING
	 * </pre>
	 */
	private BigDecimal taking;

	/**
	 * <pre>
	 * 近三年财务审计报告的审计意见
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.AUDIT_OPINION
	 * </pre>
	 */
	private String auditOpinion;

	/**
	 * <pre>
	 * 资产负债表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.LIABILITIES_LIST
	 * </pre>
	 */
	private String liabilitiesList;

	/**
	 * <pre>
	 * 近三年利润表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.PROFIT_LIST
	 * </pre>
	 */
	private String profitList;

	/**
	 * <pre>
	 * 近三年现金流量表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.CASH_FLOW_STATEMENT
	 * </pre>
	 */
	private String cashFlowStatement;

	/**
	 * <pre>
	 * 所有者权益变动表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.CHANGE_LIST
	 * </pre>
	 */
	private String changeList;

	/**
	 * <pre>
	 * 供应商ID T_SES_SMS_SUPPLIER_INFO
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.SUPPLIER_ID
	 * </pre>
	 */
	private String supplierId;

	/**
	 * <pre>
	 * 创建时间格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.CREATED_AT
	 * </pre>
	 */
	private Date createdAt;

	/**
	 * <pre>
	 * 更新时间格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.UPDATED_AT
	 * </pre>
	 */
	private Date updatedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getYear() {
		return year;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public String getAuditors() {
		return auditors;
	}

	public void setAuditors(String auditors) {
		this.auditors = auditors;
	}

	public String getQuota() {
		return quota;
	}

	public void setQuota(String quota) {
		this.quota = quota;
	}

	public BigDecimal getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(BigDecimal totalAssets) {
		this.totalAssets = totalAssets;
	}

	public BigDecimal getTotalLiabilities() {
		return totalLiabilities;
	}

	public void setTotalLiabilities(BigDecimal totalLiabilities) {
		this.totalLiabilities = totalLiabilities;
	}

	public BigDecimal getTotalNetAssets() {
		return totalNetAssets;
	}

	public void setTotalNetAssets(BigDecimal totalNetAssets) {
		this.totalNetAssets = totalNetAssets;
	}

	public BigDecimal getTaking() {
		return taking;
	}

	public void setTaking(BigDecimal taking) {
		this.taking = taking;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public String getLiabilitiesList() {
		return liabilitiesList;
	}

	public void setLiabilitiesList(String liabilitiesList) {
		this.liabilitiesList = liabilitiesList;
	}

	public String getProfitList() {
		return profitList;
	}

	public void setProfitList(String profitList) {
		this.profitList = profitList;
	}

	public String getCashFlowStatement() {
		return cashFlowStatement;
	}

	public void setCashFlowStatement(String cashFlowStatement) {
		this.cashFlowStatement = cashFlowStatement;
	}

	public String getChangeList() {
		return changeList;
	}

	public void setChangeList(String changeList) {
		this.changeList = changeList;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}