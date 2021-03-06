package common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>展示标签
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class DisPlayTld extends TagSupport {

    /**
     * @Fields serialVersionUID : TODO(目的和意义)
     */
    private static final long serialVersionUID = 3670107175836946079L;
    
    /** 一组显示 **/
    private String groups;
    
    /** 标签id */
    private String showId;
    
    /** 业务Id */
    private String businessId;
    
    /** 业务类型Id */
    private String typeId;
    
    /** 系统key */
    private Integer sysKey;
    
    /** 打包下载文件名称 **/
    private String zipFileName;
    
    /** 重新命名单文件名称 **/
    private String fileName;
    
    /** 是否删除 */
    private boolean delete = true;

    
    
    @Override
    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        JspWriter out = pageContext.getOut();
        
        try {
             out.println("<input id='showId' type=\"hidden\"  value=" + showId + " />");
             out.println("<input class='uploaded_file_show' type=\"hidden\" />");
             out.println("<input id='show_groupId' type=\"hidden\"  value=" + groups + " />");
             out.println("<input id='"+showId+"_showdel' type=\"hidden\"  value=" + delete + " />");
             out.println("<input id='"+showId+"_downBsId' type=\"hidden\"  value=" + businessId + " />");
             out.println("<input id='"+showId+"_downBstypeId'  type=\"hidden\"  value=" + typeId + " />");
             out.println("<input id='"+showId+"_downBsKeyId' type=\"hidden\"  value=" + sysKey + " />");
             out.println("<input id='"+showId+"_zipFileName' type=\"hidden\"  value=" + zipFileName + " />");
             out.println("<input id='"+showId+"_fileName' type=\"hidden\"  value=" + fileName + " />");
             out.println("<div><ul id='"+showId+"_disFileId' class='uploadFiles'></ul></div>");
             out.println("<div id='showPic' type=\"hidden\"   ></div>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    @Override
    public void release() {
        showId = null;
        businessId = null;
        typeId = null;
        sysKey = null;
        zipFileName = null;
        super.release();
    }

    
    
    
   
    
    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Integer getSysKey() {
        return sysKey;
    }

    public void setSysKey(Integer sysKey) {
        this.sysKey = sysKey;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public String getZipFileName() {
        return zipFileName;
    }

    public void setZipFileName(String zipFileName) {
        this.zipFileName = zipFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
