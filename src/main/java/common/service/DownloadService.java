package common.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 * 版权：(C) 版权所有 
 * <简述>文件下载service
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface DownloadService {

    /**
     * 
     *〈简述〉文件下载
     *〈详细描述〉
     * @author myc
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     */
    public void download(HttpServletRequest request, HttpServletResponse response);
}