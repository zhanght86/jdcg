package common.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.model.UploadFile;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface UploadService {
    
    /***
     * 
     *〈简述〉 保存附件
     *〈详细描述〉
     * @author myc
     * @param businessId 业务id
     * @param fileTypeId 文件类型id
     * @param systemKey 对应系统key
     * @param request {@link HttpServletRequest}
     * @return 
     */
    public String saveFile(HttpServletRequest request);
    /**
     * 
     *〈简述〉文件上传
     *〈详细描述〉
     * @author myc
     * @param request  {@link HttpServletRequest}
     * @return 成功返回文件路径,失败返回空字符串
     */
    public String upload(HttpServletRequest request);
    /**
     * 
     *〈简述〉文件删除
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @return 成功返回ok,失败返回error
     */
    public String updateFile(HttpServletRequest request);
    
    /**
     * 
     *〈简述〉获取上传文件
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @return 成功返回文件对象List,否则为空list
     */
    public List<UploadFile> getFiles(HttpServletRequest request);
    
    /***
     * 
     *〈简述〉返回
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     */
    public void viewPicture(HttpServletRequest request ,HttpServletResponse response);

    /**
     * 
     *〈简述〉保存文件
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @param businessId 业务Id
     * @param typeId 业务类型Id
     * @param sysKey 系统key
     * @return
     */
    public String saveOnlineFile(HttpServletRequest request ,String businessId, String typeId, String sysKey);
}
