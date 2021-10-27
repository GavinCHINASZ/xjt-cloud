package com.xjt.cloud.iot.core.common;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.xjt.cloud.commons.utils.SysLog;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author huanggc
 * @date 2020/12/08
 */
public class PdfUtils {

    /**
     * 功能描述: 导出公共方法抽取
     *
     * @param fileUrl 文件生成的路径
     * @param fileName 导出的文件名+文件后缀
     * @param map 数据
     * @param modelName 模板名
     * @param isToPdf 是否转成PDF
     * @author huanggc
     * @date 2019/11/04
     */
    public static void downModel(HttpServletRequest request, HttpServletResponse response, String fileUrl, String fileName, Map<String, Object> map, Configuration configuration,
                          String modelName, boolean isToPdf){
        OutputStreamWriter oWriter = null;
        BufferedWriter writer = null;
        try {
            OutputStream outt = new FileOutputStream(fileUrl + fileName + ".doc");
            // out
            oWriter = new OutputStreamWriter(outt, "UTF-8");
            writer = new BufferedWriter(oWriter);
            // 获取模板
            Template template = configuration.getTemplate(modelName);
            template.setOutputEncoding("UTF-8");
            String result = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

            writer.write(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (oWriter != null) {
                    oWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String pdfName = fileName + ".pdf";
        String docName = fileName + ".doc";
        if (isToPdf) {
            boolean docToPdfBoolean = docToPdf(fileUrl + fileName + ".doc", fileUrl + pdfName);
            if (docToPdfBoolean) {
                //toDownload(response, fileName + ".pdf", fileUrl);
                boolean downloadBoolean = downloads(response, pdfName, fileUrl);
                if (downloadBoolean) {
                    // 成功后删除文件
                    deleteFile(fileUrl, docName, pdfName);
                }
            }
        }else {
            // 导出为word
            boolean downloadBoolean = downloads(response, docName, fileUrl);
            if (downloadBoolean) {
                deleteFile(fileUrl, docName, pdfName);
            }
        }
    }

    private static boolean getLicense() {
        boolean result = false;
        try {
            // license.xml应放在..\WebRoot\WEB-INF\classes路径下
            String s= "<License>\n" +
                    "    <Data>\n" +
                    "        <Products>\n" +
                    "            <Product>Aspose.Total for Java</Product>\n" +
                    "            <Product>Aspose.Words for Java</Product>\n" +
                    "        </Products>\n" +
                    "        <EditionType>Enterprise</EditionType>\n" +
                    "        <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
                    "        <LicenseExpiry>20991231</LicenseExpiry>\n" +
                    "        <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
                    "    </Data>\n" +
                    "    <Signature>\n" +
                    "        sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=\n" +
                    "    </Signature>\n" +
                    "</License>";
            //File file = new File(System.getProperty("user.dir") + ConstantsProjectMsg.PDF_LICENSE_FILE_URL);
            //InputStream is = new FileInputStream(file) ;

            ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            SysLog.error(e.fillInStackTrace());
        }
        return result;
    }

    /**
     * 功能描述: word文档 转成 pdf
     *
     * @param wordPath 需要被转换的word全路径带文件名   例: D:/logs/Java多线程大合集.doc
     * @param pdfPath 转换之后pdf的全路径带文件名  例: D:/logs/123.pdf
     * @author huanggc
     * @date 2020-04-10
     * @return com.xjt.cloud.commons.utils.Data
     */
    private static boolean docToPdf(String wordPath, String pdfPath) {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getLicense()) {
            return false;
        }

        try {
            // 新建一个pdf文档
            File file = new File(pdfPath);
            FileOutputStream os = new FileOutputStream(file);
            // Address是将要被转化的word文档
            Document doc = new Document(wordPath);
            // 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, com.aspose.words.SaveFormat.PDF);
            os.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 下载文件到浏览器
     *
     * @param request
     * @param response
     * @param filename 要下载的文件名
     * @param file     需要下载的文件对象
     * @throws IOException
     */
    public static void downFile(HttpServletRequest request, HttpServletResponse response, String filename, File file){
        //  文件存在才下载
        if (file.exists()) {
            OutputStream out = null;
            FileInputStream in = null;
            try {
                // 1.读取要下载的内容
                in = new FileInputStream(file);

                // 2. 告诉浏览器下载的方式以及一些设置
                // 解决文件名乱码问题，获取浏览器类型，转换对应文件名编码格式，IE要求文件名必须是utf-8, firefo要求是iso-8859-1编码
                String agent = request.getHeader("user-agent");
                if (agent.contains("FireFox")) {
                    filename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
                } else {
                    filename = URLEncoder.encode(filename, "UTF-8");
                }
                // 设置下载文件的mineType，告诉浏览器下载文件类型
                String mineType = request.getServletContext().getMimeType(filename);
                response.setContentType(mineType);
                // 设置一个响应头，无论是否被浏览器解析，都下载
                response.setHeader("Content-disposition", "attachment; filename=" + filename);
                // 将要下载的文件内容通过输出流写到浏览器
                out = response.getOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 下载文件
     *
     * @param response HttpServletResponse
     * @param pdfName 文件名
     * @param pdfPath 文件路径
     * @return boolean
     */
    private static boolean downloads(HttpServletResponse response, String pdfName, String pdfPath) {
        // path是指欲下载的文件的路径。
        File file = new File(pdfPath + pdfName);

        // 以流的形式下载文件。
        InputStream fis;
        try {
            fis = new BufferedInputStream(new FileInputStream(pdfPath + pdfName));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空respons
            response.reset();
            // 设置response的Header
            response.setHeader("Content-Disposition", "inline;fileName=\"" + new String((pdfName).getBytes("utf-8"),"ISO8859-1") + "\"");
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @author huanggc
     * @date 2020-04-12
     * @param path 文件路径
     * @return boolean
     */
    private static boolean deleteFile(String path, String docName, String pdfName) {
        Boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                String name = temp.getName();
                if (docName.equals(name) || pdfName.equals(name)){
                    temp.delete();
                }
            }
            if (temp.isDirectory()) {// 递归
                //deleteFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                flag = true;
            }
        }
        return flag;
    }
}