package com.xjt.cloud.commons.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {
    /**
     * 压缩包 方法
     *
     * @param fileName 压缩包名
     * @param fileUrl 文件路径
     * @param request
     * @param response
     * @throws IOException
     */
    public static void zipFiles(String fileName, String fileUrl, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));

        ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());

        File file = new File(fileUrl);//  fileUrl:文件路径

        List<String> subs = ergodic(file, new ArrayList<String>());
        for (int i = 0; i < subs.size(); i++) {
            File f = new File(subs.get(i));
            zos.putNextEntry(new ZipEntry("" + f.getName()));
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            int r = 0;
            while ((r = fis.read(buffer)) != -1) {
                zos.write(buffer, 0, r);
            }
            fis.close();
        }
        zos.flush();
        zos.close();
    }

    /**
     *
     * @param file File
     * @param resultFileName List<String>
     * @return List<String>
     */
    public static List<String> ergodic(File file, List<String> resultFileName) {
        File[] files = file.listFiles();
        if (files == null){
            return resultFileName;// 判断目录下是不是空的
        }
        for (File f : files) {
            if (f.isDirectory()) {// 判断是否文件夹
                resultFileName.add(f.getPath());
                ergodic(f, resultFileName);// 调用自身,查找子目录
            } else
                resultFileName.add(f.getPath());
        }
        return resultFileName;
    }

    /**
     * 删除文件
     *
     * @param path 文件路径
     * @return boolean
     */
    public static boolean deleteFile(String path) {
        Boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {// 递归
                deleteFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                flag = true;
            }
        }
        return flag;
    }

    /**
     *@Author: dwt
     *@Date: 2020-07-21 9:13
     *@Description 多个文件打压缩包
     */
    /*public static void downloadZip(List<File> fileList, String zipName, HttpServletResponse response) throws IOException {
        if (null == fileList || fileList.size() <= 0) {
            return;
        }
        //将多个文件压缩成一个zip包
        String zipFilePath = zipName + ".zip";
        File filePath  = new File(zipFilePath);
        if (!filePath.isDirectory()) {
            filePath.mkdirs();
        }
        File zipFile = new File(zipFilePath);
        if (!zipFile.exists()) {
            zipFile.createNewFile();
        }
        getZip(fileList, filePath.getPath());
        downloadFile(zipFile, response);
    }*/

    /**
     *@Author: dwt
     *@Date: 2020-07-21 9:14
     *@Description 压缩为ZIP
     */
    /*private static void getZip(List<File> files, String zipFilePath) throws IOException {
        FileInputStream in = null;
        FileOutputStream out = null;
        ZipOutputStream zipOut = null;
        List<String> entryNames = new ArrayList<>();
        try {
            out = new FileOutputStream(zipFilePath);
            // 使用GBK编码可以避免压缩中文文件名乱码　
            zipOut = new ZipOutputStream(out, Charset.forName("GBK"));
            byte[] buffer = new byte[1024];
            String entryName = "";
            int seq = 0;
            for (File f : files) {
                seq++;
                in = new FileInputStream(f);
                //如果待压缩的文件名存在相同的，则先重命名
                entryName = f.getName();
                if (entryNames.contains(entryName)) {
                    int lastIndexOf = entryName.lastIndexOf(".");
                    if (lastIndexOf != -1) {
                        String name = entryName.substring(0, lastIndexOf);
                        String suffix = entryName.substring(lastIndexOf + 1, entryName.length());
                        entryName = name + "(" + seq + ")" + "." + suffix;
                    } else {
                        entryName += "(" + seq + ")";
                    }
                }
                entryNames.add(entryName);
                ZipEntry entry = new ZipEntry(entryName);
                zipOut.putNextEntry(entry);
                int nNumber;
                while ((nNumber = in.read(buffer)) != -1) {
                    zipOut.write(buffer, 0, nNumber);
                }
                zipOut.closeEntry();
            }
        } catch (Exception e) {
            System.out.println("压缩ZIP包出现异常");
            e.printStackTrace();
        } finally {
            in.close();
            out.close();
            zipOut.close();
        }
    }*/

   /**
    *@Author: dwt
    *@Date: 2020-07-21 9:14
    *@Description 下载压缩包
    */
    /*private static void downloadFile(File file, HttpServletResponse response) throws IOException {
        String fileName = file.getName();
        InputStream fis = null;
        try {
            fis = new FileInputStream(file);
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/force-download"); // 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName); // 设置文件名
            response.setHeader("Content-Length", String.valueOf(file.length()));
            byte[] b = new byte[1024];
            int len;
            while ((len = fis.read(b)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
            response.flushBuffer();
        } catch (IOException e) {
            System.out.println("下载出现异常");
            e.printStackTrace();
        } finally {
            fis.close();
        }
    }*/

   /**
    *@Author: dwt
    *@Date: 2020-07-23 9:52
    *@Description 传入对应的需要打包的file 集合对象, 文件打包下载
    */
    public static void downLoadFiles(List<File> files,String title, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            /**这个集合就是你想要打包的所有文件，
             * 这里假设已经准备好了所要打包的文件*/
            //List<File> files = new ArrayList<File>();

            /**创建一个临时压缩文件，
             * 我们会把文件流全部注入到这个文件中
             * 这里的文件你可以自定义是.rar还是.zip
             　　　　　这里的file路径发布到生产环境时可以改为
             */
            File file = new File(request.getSession().getServletContext().getRealPath(title + ".zip"));
            if (!file.exists()){
                file.createNewFile();
            }
            response.reset();
            //response.getWriter()
            //创建文件输出流
            FileOutputStream fous = new FileOutputStream(file);
            /**打包的方法我们会用到ZipOutputStream这样一个输出流,
             * 所以这里我们把输出流转换一下*/
//            org.apache.tools.zip.ZipOutputStream zipOut
//                = new org.apache.tools.zip.ZipOutputStream(fous);
            ZipOutputStream zipOut
                    = new ZipOutputStream(fous);
            /**这个方法接受的就是一个所要打包文件的集合，
             * 还有一个ZipOutputStream*/
            zipFile(files, zipOut);
            zipOut.close();
            fous.close();
            downloadZip(file,response);
        }catch (Exception e) {
            e.printStackTrace();
        }
        /**直到文件的打包已经成功了，
         * 文件的打包过程被我封装在FileUtil.zipFile这个静态方法中，
         * 稍后会呈现出来，接下来的就是往客户端写数据了*/
        // OutputStream out = response.getOutputStream();


        //return response;
    }

    /**
     *@Author: dwt
     *@Date: 2020-07-23 9:53
     *@Description 把接受的全部文件打成压缩包
     */
    public static void zipFile(List files,ZipOutputStream outputStream) {
        int size = files.size();
        for(int i = 0; i < size; i++) {
            File file = (File) files.get(i);
            zipFile(file, outputStream);
        }
    }

    public static HttpServletResponse downloadZip(File file,HttpServletResponse response) {
        try {
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();

            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
           // response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-zip-compressed");
            response.setHeader("Content-Disposition", "attachment;filename=" +  URLEncoder.encode(file.getName(), "UTF-8"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            try {
                File f = new File(file.getPath());
                f.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    /**
     * 根据输入的文件与输出流对文件进行打包
     */
    public static void zipFile(File inputFile,ZipOutputStream ouputStream) {
        try {
            if(inputFile.exists()) {
                /**如果是目录的话这里是不采取操作的，
                 * 至于目录的打包正在研究中*/
                if (inputFile.isFile()) {
                    FileInputStream IN = new FileInputStream(inputFile);
                    BufferedInputStream bins = new BufferedInputStream(IN, 512);
                    //org.apache.tools.zip.ZipEntry
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    ouputStream.putNextEntry(entry);
                    // 向压缩文件中输出数据
                    int nNumber;
                    byte[] buffer = new byte[512];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        ouputStream.write(buffer, 0, nNumber);
                    }
                    // 关闭创建的流对象
                    bins.close();
                    IN.close();
                } else {
                    try {
                        File[] files = inputFile.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            zipFile(files[i], ouputStream);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
