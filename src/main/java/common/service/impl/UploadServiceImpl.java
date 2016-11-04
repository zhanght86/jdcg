package common.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropUtil;
import common.bean.MultipartFileBean;
import common.constant.Constant;
import common.dao.FileUploadMapper;
import common.model.UploadFile;
import common.service.UploadService;
import common.utils.MultipartFileUploadUtil;
import common.utils.UploadUtil;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>文件上传service
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class UploadServiceImpl implements UploadService {
    
    /** 上传文件日志 */
    private final static Logger log = Logger.getLogger(UploadServiceImpl.class);
    /** 上传失败提示 */
    private final static String IS_ERR = "找不到系统路径";
    /** 删除成功 */
    private final static String OK = "ok";
    /** 删除失败 */
    private final static String ERROR ="err";
    /** 基础单位 */
    private final static int UNIT = 1024;
    
    /** 计数器  */
    private static AtomicInteger counter = new AtomicInteger(0);
    
    /** 文件上传Mapper */
    @Autowired
    private FileUploadMapper uploadDao;
    
    
    
    /**
     * 
     * @see common.service.UploadService#upload(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public String upload(HttpServletRequest request) {
        String  path = uploadFile(request);
        if (StringUtils.isNotBlank(path)){
            return path;
        }
        return "";
    }


    /**
     * 
     * @see common.service.UploadService#saveFile(java.lang.String, int, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public String saveFile(HttpServletRequest request) {
        String filePath = request.getParameter("path");
        String fileRealName  = request.getParameter("fileName");
        String businessId = request.getParameter("businessId");
        String fileTypeId = request.getParameter("typeId") == null ? "" : request.getParameter("typeId");
        boolean mutiple = Boolean.parseBoolean(request.getParameter("mutiple"));
        int systemKey = Integer.parseInt(request.getParameter("key"));
        String tableName = Constant.fileSystem.get(systemKey);
        
        /**
         * 如果是单个文件上传,自动删除之前上传的文件
         */
        if (!mutiple){
           List<UploadFile> files =  uploadDao.getFileByBusinessId(businessId, fileTypeId, tableName);
           if (files != null && files.size() > 0){
               for (UploadFile loadFile : files) {
                   uploadDao.updateFile(tableName, loadFile.getId());
               }
           }
        }
        
        
        String[] file = moveFile(systemKey, filePath, fileRealName);
        
        if (file != null && file.length == 2){
            UploadFile model = new UploadFile();
            model.setBusinessId(businessId);
            model.setTypeId(fileTypeId);
            model.setSize(Long.parseLong(file[1]));
            model.setPath(file[0]);
            model.setName(fileRealName);
            model.setCreateDate(new Date());
            model.setUpdateDate(new Date());
            model.setIsDelete(0);
            model.setTableName(tableName);
            uploadDao.insertFile(model);
            return OK;
        }
        return IS_ERR;
    }
    
    
    /**
     * 
     * @see common.service.UploadService#updateFile(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public String updateFile(HttpServletRequest request) {
        String id = request.getParameter("id");
        Integer systemKey = Integer.parseInt(request.getParameter("key"));
        String tableName = Constant.fileSystem.get(systemKey);
        try {
            uploadDao.updateFile(tableName, id);
            return OK;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR;
    }

    /**
     * 
     * @see common.service.UploadService#viewPicture(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void viewPicture(HttpServletRequest request ,HttpServletResponse response) {
       
        try {
            String fileName = request.getParameter("path");
            if (StringUtils.isNotBlank(fileName)){
                File file = new File(fileName);
                response.setContentType("image/*");
                InputStream fis = new BufferedInputStream(new FileInputStream(file));   
                OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
                UploadUtil.writeFile(fis, toClient);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }


    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author myc
     * @param type 对应系统key
     * @param request {@link HttpServletRequest}
     * @return 文件路径
     */
    public String  uploadFile(HttpServletRequest request){
        String prefix = "req_count:" + counter.incrementAndGet() + ":";
        log.info(prefix + "start...");
        try {
            MultipartFileBean param = MultipartFileUploadUtil.parse(request);
            log.info(prefix + "chunks= " + param.getChunks());
            log.info(prefix + "chunk= " + param.getChunk());
            
            long chunkSize = Long.parseLong(PropUtil.getProperty("file.upload.chunk.fileSize")) * UNIT;
            if (param.isMultipart()) {

                String finalDirPath = PropUtil.getProperty("file.base.path");
                String tempDirPath = finalDirPath  + PropUtil.getProperty("file.temp.path")  + File.separator + param.getId();
                String tempFileName = param.getFileName() + "_tmp";
                UploadUtil.createDir(tempDirPath);
                File confFile = UploadUtil.getFile(tempDirPath, param.getFileName() + ".conf");
                File tmpFile = UploadUtil.getFile(tempDirPath, tempFileName);

                RandomAccessFile accessTmpFile = new RandomAccessFile(tmpFile, "rw");
                RandomAccessFile accessConfFile = new RandomAccessFile(confFile, "rw");

                long offset = chunkSize * param.getChunk();
                
                accessTmpFile.seek(offset);
                accessTmpFile.write(param.getFileItem().get());

                log.info(prefix + "set part " + param.getChunk() + " complete");
                accessConfFile.setLength(param.getChunks());
                accessConfFile.seek(param.getChunk());
                accessConfFile.write(Byte.MAX_VALUE);

                byte[] completeList = FileUtils.readFileToByteArray(confFile);
                byte isComplete = Byte.MAX_VALUE;
                for (int i = 0; i < completeList.length && isComplete==Byte.MAX_VALUE; i++) {
                    isComplete = (byte)(isComplete & completeList[i]);
                    log.info(prefix + "check part " + i + " complete?:" + completeList[i]);
                }

                if (isComplete == Byte.MAX_VALUE) {
                    log.info(prefix + "upload complete !!");
                }
                
                accessTmpFile.close();
                accessConfFile.close();
                
                return tmpFile.getPath();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    
    /**
     * 
     * @see common.service.UploadService#getFiles(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public List<UploadFile> getFiles(HttpServletRequest request) {
        String businessId = request.getParameter("businessId");
        String typeId = request.getParameter("typeId");
        Integer systemKey = Integer.parseInt(request.getParameter("key"));
        String tableName = Constant.fileSystem.get(systemKey);
        List<UploadFile> list = uploadDao.getFiles(tableName, businessId, typeId);
        if (list != null && list.size() > 0) {
            return list;
        }
        return new ArrayList<UploadFile>();
    }


    /**
     * 
     *〈简述〉将合并后的临时文件move到正式目录下
     *〈详细描述〉
     * @author myc
     * @param type 对应的附件系统
     * @param srcFilePath 待移动文件路径
     * @param fileRealName 文件名称
     * @return 返回数组,依次为文件路径,文件大小
     */
    public String[] moveFile(int type ,String srcFilePath , String fileRealName){
        String finalPath = PropUtil.getProperty("file.base.path");
        String fileSysPath = getFileDir(type);
        String [] obj = new String[2];
        if (StringUtils.isNotBlank(fileSysPath)){
            finalPath = finalPath + fileSysPath + File.separator + UploadUtil.getDataFilePath();
            UploadUtil.createDir(finalPath);
            String targetFileName = System.currentTimeMillis()+ "." + fileRealName.substring(fileRealName.lastIndexOf(".")+1) ;
            File tagetFile = new File(finalPath, targetFileName);
            File srcFile = new File(srcFilePath);
            if (!srcFile.exists()){
                return null;
            }
            boolean res = UploadUtil.moveFile(srcFile, tagetFile);
            if (res){
                String filePath = tagetFile.getPath().replaceAll("\\\\","/");
                obj[0] = filePath;
                obj[1] = tagetFile.length() + "";
            }
        }
        return obj;
    }
    
    
    /**
     * 
     *〈简述〉获取上传文件对应的系统路径
     *〈详细描述〉
     * @author myc
     * @param type
     * @return 系统路径
     */
    public String getFileDir(int type){
        String path = "";
        switch (type){
            case 1 : path = PropUtil.getProperty("file.supplier.system.path"); break;
            case 2 : path = PropUtil.getProperty("file.tender.system.path"); break;
            case 3 : path = PropUtil.getProperty("file.expert.system.path"); break;
            case 4 : path = PropUtil.getProperty("file.forum.system.path"); break;
        }
        return path;
    }
    
}