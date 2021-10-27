package com.xjt.cloud.project.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.PropertyUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.project.core.common.ConstantsProjectMsg;
import com.xjt.cloud.project.core.dao.project.ProjectFileDao;
import com.xjt.cloud.project.core.entity.ProjectFile;
import com.xjt.cloud.project.core.service.service.ProjectFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * @ClassName ProjectFileServiceImpl 项目文件实现类
 * @Author zhangZaiFa
 * @Date 2020-05-18 15:15
 * @Description
 */
@Service
public class ProjectFileServiceImpl extends AbstractService implements ProjectFileService {

    @Autowired
    private ProjectFileDao projectFileDao;


    /**
     * @MethodName: findProjectFileList
     * @Description: 查询项目文件列表
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/18 14:28
     **/
    @Override
    public Data findProjectFileList(String json) {
        ProjectFile projectFile = JSONObject.parseObject(json, ProjectFile.class);
        projectFile = addSearch(projectFile);
        List<ProjectFile> list = projectFileDao.findProjectFileList(projectFile);
        Integer totalCount = projectFile.getTotalCount();
        Integer pageSize = projectFile.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {//判断是否存在总数，如没有，则查询总记录数
            totalCount = projectFileDao.findProjectFileListCount(projectFile);
        }
        return asseData(totalCount, list);
    }

    /**
     * @MethodName: addSearch
     * @Description: 添加搜索条件
     * @Param: [projectFile]
     * @Return: com.xjt.cloud.project.core.entity.Log
     * @Author: zhangZaiFa
     * @Date:2020/6/1 17:53
     **/
    private ProjectFile addSearch(ProjectFile projectFile) {
        try {
            if (projectFile.getStartTime() != null) {
                projectFile.setStartTime(projectFile.getStartTime().replaceAll("/", "-"));
            }
            if (projectFile.getEndTime() != null) {
                Date endTime = DateUtils.parseDate(projectFile.getEndTime().replaceAll("/", "-"));
                endTime = DateUtils.add24Hours(endTime);
                projectFile.setEndTime(DateUtils.formatDate(endTime));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (projectFile.getOrderCols() == null || projectFile.getOrderCols().length == 0) {
            String[] orderCols = {"createTime"};
            projectFile.setOrderCols(orderCols);
            projectFile.setOrderDesc(true);
        }
        return projectFile;
    }

    /**
     * @MethodName: delProjectFile
     * @Description:删除项目文件
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/18 14:30
     **/
    @Override
    public Data delProjectFile(String json) {
        ProjectFile projectFile = JSONObject.parseObject(json, ProjectFile.class);
        projectFile.setParentId(projectFile.getId());
        List<ProjectFile> list = projectFileDao.findProjectFileParentIds(projectFile.getIds());
        if (list.size() > 0) {
            return asseData(600, "删除失败，请先删除文件夹内容！");
        }
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        projectFile.setUpdateUserId(userId);
        projectFile.setUpdateUserName(userName);
        projectFile.setDeleted(true);
        projectFileDao.updateProjectFile(projectFile);
        return Data.isSuccess();
    }

    /**
     * @MethodName: updateProjectFile
     * @Description: 修改文件
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/28 13:50
     **/
    @Override
    public Data updateProjectFile(String json) {
        ProjectFile projectFile = JSONObject.parseObject(json, ProjectFile.class);
        ProjectFile oldProjectFile = projectFile = projectFileDao.findProjectFileId(projectFile.getId());
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        projectFile.setUpdateUserId(userId);
        projectFile.setUpdateUserName(userName);
        //修改当前文件信息
        projectFileDao.updateProjectFile(projectFile);
        //修改文件夹目录下文件目录信息
        if (projectFile.getIsFile() == 0) {
            projectFile.setOldFileName(projectFile.getFileName());
            projectFile.setOldFileDirectory("/[_" + projectFile.getId() + "_]" + oldProjectFile.getFileName());
            projectFile.setFileDirectory("/[_" + projectFile.getId() + "_]" + projectFile.getFileName());

            projectFileDao.updateProjectFileChild(projectFile);
        }
        return Data.isSuccess();
    }

    /**
     * @MethodName: saveProjectFile
     * @Description: 保存项目文件
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/18 14:31
     **/
    @Override
    public Data saveProjectFile(String json) {
        ProjectFile projectFile = JSONObject.parseObject(json, ProjectFile.class);
        if (projectFile.getParentId() != 0) {
            ProjectFile parent = projectFileDao.findProjectFileId(projectFile.getParentId());
            if (parent.getFileDirectory() != null) {
                projectFile.setOldFileDirectory(parent.getOldFileDirectory() + "/[_" + projectFile.getParentId() + "_]" + parent.getFileName());
            } else {
                projectFile.setOldFileDirectory("/[_" + projectFile.getParentId() + "_]" + parent.getFileName());
            }
        }
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        projectFile.setCreateUserId(userId);
        projectFile.setCreateUserName(getOrgUserName(userId, projectFile.getProjectId()));
        projectFileDao.saveProjectFile(projectFile);
        return Data.isSuccess();
    }


    /**
     * @MethodName: downProjectFile
     * @Description: 下载项目文件
     * @Param: [response, request, json]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/6/16 9:21
     **/
    @Override
    public void downProjectFile(HttpServletResponse response, HttpServletRequest request, String json) {
        ProjectFile projectFile = JSONObject.parseObject(json, ProjectFile.class);
        try {
            if (projectFile.getChild().size() > 1) { //多个文件打压缩包
                downloadFiles(projectFile.getChild(), "资料文档", request, response);
            } else if (projectFile.getChild().size() == 1) {//单个文件直接返回
                String fileName = projectFile.getChild().get(0).getFileName() + getFileTypeByUrl(projectFile.getChild().get(0).getFileUrl());

                File file = getFileByUrl(projectFile.getChild().get(0).getFileUrl(), fileName);
                InputStream fis = new BufferedInputStream(new FileInputStream(file));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                // 清空response
                response.reset();
                // 设置response的Header
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));

                response.addHeader("Content-Length", "" + file.length());
                OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");
                toClient.write(buffer);
                toClient.flush();
                toClient.close();
            } else {
                return;
            }
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        }

    }


    /**
     * @MethodName: downloadFiles
     * @Description: 下载文件打压缩包
     * @Param: [list, zipName, request, response]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/6/16 9:20
     **/
    public void downloadFiles(List<ProjectFile> list, String zipName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置压缩包的名字
        // 解决不同浏览器压缩包名字含有中文时乱码的问题
        String downloadName = System.currentTimeMillis() + ".zip";
        String agent = request.getHeader("USER-AGENT");
        try {
            if (agent.contains("MSIE") || agent.contains("Trident")) {
                downloadName = java.net.URLEncoder.encode(downloadName, "UTF-8");
            } else {
                downloadName = new String(downloadName.getBytes("UTF-8"), "ISO-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition", "attachment;fileName=\"" + downloadName + "\"");
        // 设置压缩流：直接写入response，实现边压缩边下载
        ZipOutputStream zipos = null;
        try {
            zipos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
            zipos.setMethod(ZipOutputStream.DEFLATED); // 设置压缩方法
        } catch (Exception e) {
            e.printStackTrace();
        }
        OutputStream os = null;
        // 循环将文件写入压缩流
        for (int i = 0; i < list.size(); i++) {
            String fileName = list.get(i).getFileName() + getFileTypeByUrl(list.get(i).getFileUrl());
            File file = getFileByUrl(list.get(i).getFileUrl(), fileName);
            try {
                // 添加ZipEntry，并ZipEntry中写入文件流
                // 这里，加上i是防止要下载的文件有重名的导致下载失败
                zipos.putNextEntry(new ZipEntry(fileName));
                os = new DataOutputStream(zipos);
                InputStream is = new FileInputStream(file);
                byte[] b = new byte[100];
                int length = 0;
                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }
                is.close();
                zipos.closeEntry();
            } catch (IOException e) {
                SysLog.info(e.fillInStackTrace());
            }
        }
        // 关闭流
        try {
            os.flush();
            os.close();
            zipos.close();
        } catch (IOException e) {
            SysLog.info(e.fillInStackTrace());
        }
    }

    //url转file
    private File getFileByUrl(String fileUrl, String suffix) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        BufferedOutputStream stream = null;
        InputStream inputStream = null;
        File file = null;
        try {
            URL imageUrl = new URL(fileUrl);
            if("true".equals(ConstantsProjectMsg.PROJECT_DOWNLOAD_IS_NATIVE_FILE)){
                fileUrl = fileUrl.replace(imageUrl.getHost(),"127.0.0.1");
                imageUrl = new URL(fileUrl);
            }
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            inputStream = conn.getInputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            file = new File(PropertyUtils.getProperty("downloadFilePrefix"));
            file = File.createTempFile("file", suffix,file);
            SysLog.error("临时文件路径＝＝＝＝＝＝＝＝＝＝＝" + file.getPath());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fileOutputStream);
            stream.write(outStream.toByteArray());
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (stream != null)
                    stream.close();
                outStream.close();
            } catch (Exception e) {
                SysLog.info(e.fillInStackTrace());
            }
        }
        return file;
    }

    //根据获取文件后缀
    private String getFileTypeByUrl(String url) {

        String suffixes = "3fr|arw|bmp|cr2|crw|dcr|dng|eps|erf|gif|icns|ico|jpeg|jpg|mos|mrw|nef|odd|orf|pdf|pef|png|ppm|ps|psd|raf|raw|svg|svgz|tif|tiff|webp|x3f|xcf|xps|		7z|ace|alz|arc|arj|bz|bz2|cab|cpio|deb|dmg|eml|gz|img|iso|jar|lha|lz|lzma|lzo|rar|rpm|rz|tar|tar.7z|tar.bz|tar.bz2|tar.gz|tar.lzo|tar.xz|tar.Z|tbz|tbz2|tgz|tZ|tzo|xz|z|zip|aac|ac3|aif|aifc|aiff|amr|caf|flac|m4a|m4b|mp3|oga|ogg|sf2|sfark|voc|wav|weba|wma|		3g2|3gp|3gpp|avi|cavs|dv|dvr|flv|gif|m2ts|m4v|mkv|mod|mov|mp4|mpeg|mpg|mts|mxf|ogg|rm|rmvb|swf|ts|vob|webm|wmv|wtv|		abw|djvu|doc|docm|docx|html|lwp|md|odt|pages|pages.zip|pdf|rst|rtf|sdw|tex|txt|wpd|wps|zabw|eps|html|key|key.zip|odp|pdf|pps|ppsx|ppt|pptm|pptx|ps|sda|swf|		csv|html|numbers|numbers.zip|ods|pdf|sdc|xls|xlsm|xlsx|azw|azw3|azw4|cbc|cbr|cbz|chm|docx|epub|fb2|htm|html|htmlz|lit|lrf|mobi|odt|oeb|pdb|pdf|pml|prc|rb|rtf|snb|tcr|txt|txtz|eot|otf|ttf|woff|dwg|dxf|ai|cdr|cgm|emf|eps|pdf|ps|sk|sk1|svg|svgz|vsd|wmf|website";
        Pattern pat = Pattern.compile("[\\w]+[\\.](" + suffixes + ")");// 正则判断
        Matcher mc = pat.matcher(url);// 条件匹配
        String substring = "";
        while (mc.find()) {
            substring = mc.group();// 截取文件名后缀名
        }
        String[] arr = substring.split("\\.");
        if (arr.length != 0) {
            return "." + arr[arr.length - 1];
        } else {
            return substring;
        }
    }


}
