package ses.model.bms;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class DictionaryData implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private String id;

    @NotNull(message = "编码不能为空")  
    private String code;

    private String description;

    @NotNull(message = "名称不能为空")
    private String name;
    
    private Date createdAt;

    private Date updatedAt;

    private Integer isDeleted;
    
    private Integer kind;
    
    @NotNull(message = "排序不能为空")
    private Integer position;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
    
}