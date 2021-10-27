package com.xjt.cloud.ftp.utils;

import com.xjt.cloud.commons.exception.BaseServiceException;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.PropertyUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/** 
* @ClassName: UploadController 
* @Description: 上传控制 
* @author wangzhiwen
* @date 2014年6月25日 上午10:13:30 
*  
*/
@Service
public class UploadServer {
    @Autowired
    private FtpConfig ftpConfig;
    @Autowired
    private FTPUpload fTPUpload;
    /**
     * 保存本地再上传
     * @param multipartFile
     * @param projectName
     * @return
     */
    public Map<String,String> getUploadPathMap(MultipartFile multipartFile,String projectName){
        boolean flag;
        Map<String,String> map = new HashMap<String,String>();
        String path = System.getProperty("user.dir")+"/";
        String webPath = DateUtils.getFilePath();
        try {
            String uploadPath = getMkdirsFilePath(multipartFile, projectName, webPath, path);
            File files = new File(uploadPath);
            multipartFile.transferTo(files);
            BufferedImage sourceImg = ImageIO.read(new FileInputStream(files));
            flag = fTPUpload.ftpUpload(uploadPath);
            map = getImgResUrl(sourceImg, projectName,webPath, flag);
        } catch (Exception e) {
            map.put("url",ftpConfig.getWebPath()+"/error.jpg");
            SysLog.error(e);
        }
        return map;
    }

    /**
     * 不保存本地通过流上传
     * @param multipartFile
     * @param projectName
     * @return
     */
    public Map<String,String> getUploadPathMapInputSteam(MultipartFile multipartFile,String projectName){
        boolean flag;
        Map<String,String> map = new HashMap<String,String>();
        String path = System.getProperty("user.dir")+"/";
        String webPath = DateUtils.getFilePath();
        try {
            String uploadPath = getFilePath(multipartFile, projectName,webPath, path);
            BufferedImage sourceImg = ImageIO.read(multipartFile.getInputStream());
            flag = fTPUpload.ftpUpload(multipartFile.getInputStream(),uploadPath);
            map = getImgResUrl(sourceImg, projectName,webPath, flag);
        } catch (Exception e) {
            map.put("url",ftpConfig.getWebPath()+"/error.jpg");
            SysLog.error(e);
        }
        return map;
    }
	/**
	 *
	* @Title: getUploadPath
	* @Description: 保存本地再上传
	* @param multipartFile
	* @return
	* @return String
	* @throws
	 */
	public String getUploadPath(MultipartFile multipartFile,String projectName){
        boolean flag;
        String reStr;
        String webPath = DateUtils.getFilePath();
		try {
            String path = System.getProperty("user.dir")+"/";
            String uploadPath = getMkdirsFilePath(multipartFile, projectName, webPath, path);
            File files = new File(uploadPath);
            multipartFile.transferTo(files);
            flag = fTPUpload.ftpUpload(uploadPath);
            reStr = getResUrl(flag, projectName, webPath);
		} catch (Exception e) {
            reStr = ftpConfig.getWebPath()+"/error.jpg";
            SysLog.error(e);
		}

		return reStr;
	}
    /**
     *
     * @Title: getUploadPath
     * @Description: 不保存本地通过流上传
     * @param multipartFile
     * @return
     * @return String
     * @throws
     */
    public String getUploadPathInputSteam(MultipartFile multipartFile,String projectName){
        String isLocalUpload = PropertyUtils.getProperty("isLocalUpload");
        String webPath;
        if (StringUtils.isNotEmpty(isLocalUpload) && "true".equals(isLocalUpload)){//是否需要使用ftp上传 true保存本地  false ftp上传
            webPath = localSaveFile(multipartFile, projectName);
        }else{
            boolean flag;
            webPath = DateUtils.getFilePath();
            try {
                String path = System.getProperty("user.dir") + "/";
                String uploadPath = getFilePath(multipartFile, projectName,webPath, path);
                flag = fTPUpload.ftpUpload(multipartFile.getInputStream(),uploadPath);
                webPath = getResUrl(flag, projectName, uploadPath);
            } catch (Exception e) {
                webPath = ftpConfig.getWebPath()+"/error.jpg";
                SysLog.error(e);
            }
        }


        return webPath;
    }

    /**
     *
     * 功能描述:保存本地文件
     *
     * @param file
     * @param packageName
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2020/6/12 16:06
     */
    private String  localSaveFile(MultipartFile file, String packageName){
        String fileType = getFileExtName(file.getOriginalFilename());
        String uri = packageName +"/" + DateUtils.getFilePath() + System.currentTimeMillis() + "." + fileType;
        File dest = new File(ftpConfig.getPath() + "/" +  uri);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest); // 保存文件
            return ftpConfig.getWebPath() + uri;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    /**
     *
     * 功能描述:
     *
     * @param filePath
     * @param projectName
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/8/22 17:40
     */
    public String getUploadPath(String filePath,String projectName){
        String path = "";
        try {
            File file = new File(filePath);
            if(!file.isFile()){
                return "error";
            }
            path = fTPUpload.ftpUpload(filePath.replace("\\","/"),projectName);
            Thread.sleep(2000);
        } catch (Exception e) {
            SysLog.error(e);
        }
        return path;
    }
    public String getUploadPathInputSteam(File filePaht,String projectName){
        String path="";
        try {
            if(filePaht!=null&&!filePaht.isFile()){
                return path="error";
            }
            path=fTPUpload.ftpUploadFile(filePaht,projectName);
            Thread.sleep(2000);
        } catch (Exception e) {
            SysLog.error(e);
        }
        return path;
    }

    /**
     *
     * 功能描述: 得到文件类型
     *
     * @param fileName
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/8/23 11:00
     */
	public String getFileExtName(String fileName)  {
		String extName = "";
		if (fileName != null && !"".equals(fileName)) {
			int i = fileName.lastIndexOf(".");
			if (i > 0) {
				extName = fileName.substring(i + 1);
			}
		}
		return extName;
	}
    /**
     *
     * 功能描述: 组装图片上传返回值
     *
     * @param sourceImg
     * @param projectName
     * @param webPath
     * @param flag
     * @return: java.util.Map<java.lang.String,java.lang.String>
     * @auther: wangzhiwen
     * @date: 2019/8/22 17:45
     */
	private Map<String, String> getImgResUrl(BufferedImage sourceImg, String projectName, String webPath, boolean flag){
        Map<String, String> map = new HashMap<>();
        map.put("width",sourceImg.getWidth()+"");
        map.put("height",sourceImg.getHeight()+"");
        String r;
        if(projectName == null || projectName.equals("") || projectName.equals("/")){
            r = "images/" + webPath;
        }else{
            r = projectName + "/" + webPath;
        }
        if(flag == false){
            map.put("url",ftpConfig.getWebPath() + "/error.jpg");
        }else{
            map.put("url",ftpConfig.getWebPath() + r);
        }
        try {
            Thread.sleep(2000);
        }catch (Exception ex){
            SysLog.error(ex);
        }
        return map;
    }

    /**
     *
     * 功能描述:组装返回url
     *
     * @param flag
     * @param projectName
     * @param webPath
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/8/22 17:40
     */
	private String getResUrl(boolean flag, String projectName, String webPath){
        String reStr;
        if(flag == false){
            reStr = ftpConfig.getWebPath() + "/error.jpg";
        }else{
            if(projectName == null || projectName.equals("") || projectName.equals("/")){
                reStr = "images/" + webPath;
            }else{
                reStr = webPath.substring(webPath.indexOf(projectName));
            }
            reStr = ftpConfig.getWebPath() + reStr;
        }
        try {
            Thread.sleep(2000);
        }catch (Exception ex){
            SysLog.error(ex);
        }

        return reStr;
    }

    /**
     *
     * 功能描述: 获取路径
     *
     * @param multipartFile
     * @param projectName
     * @param webPath
     * @param path
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/8/22 17:32
     */
	public String getFilePath(MultipartFile multipartFile, String projectName, String webPath, String path){
        if(projectName == null || projectName.equals("") || projectName.equals("/")){
            path = path+"/images/"+ webPath;
        }else{
            path = path+"/"+projectName+"/"+ webPath;
        }
        String extName = getFileExtName(multipartFile.getOriginalFilename());
        String fileName = System.currentTimeMillis() + "." + extName;
        return path + "/" + fileName;
    }

    /**
     *
     * 功能描述:获取路径，并在本地生成文件
     *
     * @param multipartFile
     * @param projectName
     * @param webPath
     * @param path
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/8/22 17:32
     */
    private String getMkdirsFilePath(MultipartFile multipartFile, String projectName, String webPath, String path){
        try {
            if(projectName == null || projectName.equals("") || projectName.equals("/")){
                path = path + "/images/" + webPath;
            }else{
                path = path + "/" + projectName + "/" + webPath;
            }
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            String extName = getFileExtName(multipartFile.getOriginalFilename());
            String fileName = System.currentTimeMillis() + "." + extName;
            String uploadPath = path + "/" + fileName;
            return uploadPath;
        }catch (Exception ex){
            SysLog.error(ex);
        }
        return null;
    }
}
