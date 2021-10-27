package com.xjt.cloud.task.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.task.core.common.ConstantsDevice;
import com.xjt.cloud.task.core.dao.task.SummaryDetailsDao;
import com.xjt.cloud.task.core.dao.task.TaskSummaryDao;
import com.xjt.cloud.task.core.entity.SummaryDetails;
import com.xjt.cloud.task.core.entity.TaskSummary;
import com.xjt.cloud.task.core.service.service.TaskSummaryIoService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *  任务汇总 任务导出表格
 *
 * @author huanggc
 * @date 2019-11-25
 */
@Service
public class TaskSummaryIoServiceImpl extends AbstractService implements TaskSummaryIoService {
    @Autowired
    private SummaryDetailsDao summaryDetailsDao;
    @Autowired
    private TaskSummaryDao taskSummaryDao;
    // 1任务管理导出详情, 2任务工单导出详情, 3月任务汇总—任务概览表导出详情
    private static Integer downType = 3;
    // private static String fileUrl = "F:\\opt\\web\\task\\";// 本地调试
    String fileUrl = System.getProperty("user.dir") + ConstantsDevice.ZIP_FILE_URL;

    // 表头
    private Map<String, String> mapNames = new HashMap<>(8);

    private void addMap() {
        mapNames.put("number", "序号");
        mapNames.put("deviceName", "设备名称");
        mapNames.put("deviceQrNo", "设备ID");
        mapNames.put("checkPointName", "巡查点名称");
        mapNames.put("checkPointQrNo", "巡查点ID");
        mapNames.put("buildingName", "建筑");
        mapNames.put("floor", "楼层");
        mapNames.put("checkStatus", "巡检状态");
    }

    private List<String> getPropertyNames() {
        List<String> propertyNames = new ArrayList<>(8);
        propertyNames.add("number");
        propertyNames.add("deviceName");
        propertyNames.add("deviceQrNo");
        propertyNames.add("checkPointName");
        propertyNames.add("checkPointQrNo");
        propertyNames.add("buildingName");
        propertyNames.add("floor");
        propertyNames.add("checkStatus");
        return propertyNames;
    }

    /**
     * PC月任务汇总--任务概览表 导出详情
     *
     * @author huanggc
     * @date 2019-11-28
     * @param json String 参数,json格式
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    public void downTaskTableDetails(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
        TaskSummary taskSummary = JSONObject.parseObject(json, TaskSummary.class);

        downType = taskSummary.getDownType();
        // 压缩包名
        String compressPackage = "任务概览表导出详情.zip";
        List<TaskSummary> taskSummaryList;
        if (downType == 1){
            compressPackage = "任务管理导出详情.zip";
            taskSummary.setTypeTask(0);
            taskSummary.setDelete(0);
            taskSummaryList = taskSummaryDao.findTaskDetails(taskSummary);
        }else if (downType == 2){
            compressPackage = "任务工单导出详情.zip";
            taskSummary.setTypeTask(1);
            taskSummary.setDelete(0);
            taskSummaryList = taskSummaryDao.findTaskDetails(taskSummary);
        }else {
            // PC月任务汇总--任务概览表导出详情
            if (null == taskSummary.getPeriodEndTime()){
                // 查询上个月
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                // 月份减一
                cal.add(Calendar.MONTH, -1);
                taskSummary.setPeriodEndTime(cal.getTime());
            }
            // 任务截止时间
            Date periodEndTime = taskSummary.getPeriodEndTime();
            taskSummary.setPeriodStartTime(DateUtils.monthStarDate(periodEndTime));
            taskSummary.setPeriodEndTime(DateUtils.monthEndDate(periodEndTime));
            taskSummaryList = taskSummaryDao.findTaskSummaryList(taskSummary);
        }

        if (CollectionUtils.isNotEmpty(taskSummaryList)){
            int tab = taskSummaryList.size();
            // 类名
            //String className = "TaskSummary";
            for (TaskSummary entity : taskSummaryList) {
                String fileName = entity.getProjectName() + "--"+ entity.getTaskName() + "任务导出表.xls";
                addMap();
                response.setHeader("Cache-Control", "cache");
                response.setHeader("Cache-Control", "must-revalidate");
                response.setHeader("Pragma", "public");
                response.setHeader("Content-Disposition",
                        "attachment;fileName=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
                HSSFWorkbook wb;
                OutputStream out;
                wb = new HSSFWorkbook();
                // CcColumnDefineHolder.init(className, LocaleUtils.getLocale());// ???
                addFormSheets(wb, entity);
                if(tab == 1) {
                    out = response.getOutputStream();
                }else {
                    // path=fileUrl
                    ArrayList<String> list = getFile(fileUrl,1);
                    fileName = checkFileName(list, fileName, 1);
                    // name=fileUrl      向d://test.xls中写数据
                    out = new FileOutputStream(fileUrl + fileName);
                }
                wb.write(out);
                out.flush();
                out.close();
            }

            if(tab > 1) {
                try {
                    zipFiles(compressPackage, request, response);
                } catch (IOException e) {
                    SysLog.error(e);
                }
            }
        }
    }

    /**
     * @param fileName 压缩包名
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException IOException
     */
    private void zipFiles(String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());

        String path = TaskSummary.class.getResource("").getPath();
        // fileUrl ???
        File file = new File(fileUrl);
        List<String> subs = ergodic(file, new ArrayList<>());
        for (String string : subs) {
            File f = new File(string);
            zos.putNextEntry(new ZipEntry("" + f.getName()));
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = fis.read(buffer)) != -1) {
                zos.write(buffer, 0, r);
            }
            fis.close();
        }
        zos.flush();
        zos.close();
        deleteFile(fileUrl);
    }

    /**
     * 删除文件
     *
     * @param path 文件路径
     * @return boolean
     */
    private Boolean deleteFile(String path) {
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
        for (String tempString : tempList) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempString);
            } else {
                temp = new File(path + File.separator + tempString);
            }

            if (temp.isFile()) {
                temp.delete();
            }

            if (temp.isDirectory()) {
                // 递归
                // 先删除文件夹里面的文件
                deleteFile(path + "/" + tempString);
                flag = true;
            }
        }
        return flag;
    }

    /**
     * @param wb HSSFWorkbook
     * @param entity TaskSummary 任务汇总实体
     */
    private void addFormSheets(HSSFWorkbook wb, TaskSummary entity) {
        List<String> propertyNames = getPropertyNames();
        // String className = getInterfaceClass().getSimpleName();// ???
        String className = "TaskSummary";
        // className = className.substring(0, 1).toLowerCase() + className.substring(1);
        String sheetName = entity.getTaskName();
        sheetName = WorkbookUtil.createSafeSheetName(sheetName);
        HSSFSheet sheet = wb.createSheet(sheetName);

        Row row;
        Cell cell;
        HSSFCellStyle headerStyle = getHeaderStyle(wb);
        String celString;
        // 下边框
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // 左边框
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        // 上边框
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 右边框
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

        // Map<String,Object> map = mtMaterialLocService.findByTaskMt(entity.getId(), checkState);// ???
        List<Map<String, Object>> list = getDeviceList(entity);
        try {
            creHead(sheet, wb, entity, headerStyle);
        } catch (Exception e) {
            SysLog.error(e);
        }
        // HSSFCellStyle normalStyle = getNormalStyle(wb);

        int rowNum = 0;
        if (downType == 1){
            rowNum = 1;
        }
        row = createRow(sheet, 8 + rowNum);
        for (int i = 0; i < propertyNames.size(); i++) {
            cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            celString = getPropertyLabel(propertyNames.get(i));
            cell.setCellValue(celString);
        }

        HSSFCellStyle normalStyle = getNormalStyle(wb);
        // 下边框
        normalStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // 左边框
        normalStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        // 上边框
        normalStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 右边框
        normalStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 遍历写数据
        for (int k = 0; k < list.size(); k++) {
            Map<String, Object> mtMap = list.get(k);
            // index:从第几行开始写数据
            row = createRow(sheet, k + 9 + rowNum);
            for (int i = 0; i < propertyNames.size(); i++) {
                cell = row.createCell(i);
                cell.setCellStyle(normalStyle);
                if(i == 0) {
                    cell.setCellValue(k + 1);
                }else {
                    setCellValue(cell, mtMap, propertyNames.get(i), k + 1);
                }
            }
        }

        for (int i = 0; i < 11; i++) {
            // 设置表格列宽
            if (i == 0 || i == 6 || i == 7) {
                sheet.setColumnWidth(i, 256 * 18 + 184);
            } else {
                sheet.setColumnWidth(i, 256 * 30 + 184);
            }
        }
    }

    /**
     * 查询任务下的设备
     *
     * @author huanggc
     * @date 2019-11-28
     * @param entity TaskSummary 任务汇总 实体
     * @return List<Map<String, Object>>
     */
    private List<Map<String, Object>> getDeviceList(TaskSummary entity) {
        List<Map<String, Object>> list = new ArrayList<>();

        SummaryDetails summaryDetails = new SummaryDetails();
        summaryDetails.setTaskId(entity.getTaskId());

        List<SummaryDetails> summaryDetailsList;
        if (downType == 3){
            summaryDetailsList = summaryDetailsDao.findSummaryDetailsList(summaryDetails);
        }else {
            summaryDetailsList = summaryDetailsDao.findTaskDeviceList(summaryDetails);
        }

        if (CollectionUtils.isNotEmpty(summaryDetailsList)){
            Map<String, Object> map;
            for (SummaryDetails details: summaryDetailsList){
                map = new HashMap<>(7);
                map.put("deviceName", details.getDeviceName());
                map.put("deviceQrNo", details.getDeviceQrNo());
                map.put("checkPointName", details.getCheckPointName());
                map.put("checkPointQrNo", details.getCheckPointQrNo());
                map.put("buildingName", details.getBuildingName());
                map.put("floor", details.getFloorName());
                Integer checkStatus = details.getCheckStatus();
                if (checkStatus == 1){
                    map.put("checkStatus", "故障");
                }else if (checkStatus == 2){
                    map.put("checkStatus", "正常");
                }else {
                    map.put("checkStatus", "未检");
                }
                list.add(map);
            }
        }
        return list;
    }

    /**
     * @param sheet HSSFSheet
     * @param wb HSSFWorkbook
     * @param taskSummary 任务汇总 实体
     * @param headerStyle HSSFCellStyle
     * @throws Exception Exception
     */
    private void creHead(HSSFSheet sheet, HSSFWorkbook wb, TaskSummary taskSummary, HSSFCellStyle headerStyle) throws Exception {
        /*
        // 旧系统
        URL url = new URL("http://127.0.0.1:7084/config/static/images/xjtLogo.png");// 获取照片的地址
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 打开链接
        conn.setRequestMethod("GET");// 设置请求方式为"GET"
        conn.setConnectTimeout(3 * 1000);// 超时响应时间为5秒
        InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
        // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
        byte[] data = readInputStream(inStream);
        */

        FileOutputStream fileOut = null;
        BufferedImage bufferImg = null;
        // 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        bufferImg = ImageIO.read(new File("config/static/images/xjtLogo.png"));// 图片路径
        ImageIO.write(bufferImg, "png", byteArrayOut);

        // anchor主要用于设置图片的属性
        HSSFClientAnchor anchor = new HSSFClientAnchor(60, 30, 250, 240, (short) 3, 0, (short) 3, 0);
        anchor.setAnchorType(0);

        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
        HSSFFont headFont = wb.createFont();
        short bold = 650;
        short fontHeight = 16;
        headFont.setBoldweight(bold);
        headFont.setFontHeightInPoints(fontHeight);
        headerStyle.setFont(headFont);

        Row row = createRow(sheet, 0, 45);
        Cell cell;
        cell = row.createCell(0);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("消检通智能管理平台");
        setHeaderStyle(cell, headerStyle, row, "消检通智能管理平台", 0, 7);

        row = createRow(sheet, 1, 30);
        cell = row.createCell(0);
        cell.setCellStyle(headerStyle);
        String tableName = taskSummary.getProjectName() + "--" + taskSummary.getTaskName() + "任务导出表";
        cell.setCellValue(tableName);
        setHeaderStyle(cell, headerStyle, row, tableName, 0, 7);

        row = createRow(sheet, 2, 30);
        cell = row.createCell(0);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("任务基本信息");
        setHeaderStyle(cell, headerStyle, row, "任务基本信息", 0, 7);

        row = createRow(sheet, 3, 22);
        cell = row.createCell(0);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("任务名称");
        setHeaderStyle(cell, headerStyle, row, "任务名称", 0, 0);
        cell = row.createCell(1);
        cell.setCellStyle(headerStyle);
        cell.setCellValue(taskSummary.getTaskName());
        setHeaderStyle(cell, headerStyle, row, taskSummary.getTaskName(), 1, 1);

        cell = row.createCell(2);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("任务周期");
        setHeaderStyle(cell, headerStyle, row, "任务周期", 2, 2);
        cell = row.createCell(3);
        cell.setCellStyle(headerStyle);
        cell.setCellValue(taskSummary.getTaskPeriodTypeDesc());
        setHeaderStyle(cell, headerStyle, row, taskSummary.getTaskPeriodTypeDesc(), 3, 3);

        cell = row.createCell(5);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("任务状态");
        setHeaderStyle(cell, headerStyle, row, "任务状态", 5, 5);
        cell = row.createCell(6);
        cell.setCellStyle(headerStyle);
        cell.setCellValue(taskSummary.getTaskOverviewStatusDesc());
        setHeaderStyle(cell, headerStyle, row, taskSummary.getTaskOverviewStatusDesc(), 6, 7);


        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        //String format = sdf.format(date);
        row = createRow(sheet, 4, 22);
        cell = row.createCell(0);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("开始时间");
        setHeaderStyle(cell, headerStyle, row, "开始时间", 0, 0);
        cell = row.createCell(1);
        cell.setCellStyle(headerStyle);
        cell.setCellValue(sdf.format(taskSummary.getPeriodStartTime()));
        setHeaderStyle(cell, headerStyle, row, sdf.format(taskSummary.getPeriodStartTime()), 1, 1);
        cell = row.createCell(2);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("结束时间");
        setHeaderStyle(cell, headerStyle, row, "结束时间", 2, 2);
        cell = row.createCell(3);
        cell.setCellStyle(headerStyle);
        cell.setCellValue(sdf.format(taskSummary.getPeriodEndTime()));
        setHeaderStyle(cell, headerStyle, row, sdf.format(taskSummary.getPeriodEndTime()), 3, 4);
        cell = row.createCell(5);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("任务提醒时间");
        setHeaderStyle(cell, headerStyle, row, "任务提醒时间", 5, 5);
        cell = row.createCell(6);
        cell.setCellStyle(headerStyle);
        cell.setCellValue(sdf.format(taskSummary.getRemindTime()));
        setHeaderStyle(cell, headerStyle, row, sdf.format(taskSummary.getRemindTime()), 6, 7);

        //
        row = createRow(sheet, 5, 22);
        cell = row.createCell(0);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("执行人");
        setHeaderStyle(cell, headerStyle, row, "执行人", 0, 0);
        cell = row.createCell(1);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("/");
        setHeaderStyle(cell, headerStyle, row, "/", 1, 7);

        //
        row = createRow(sheet, 6, 22);
        cell = row.createCell(0);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("审核人");
        setHeaderStyle(cell, headerStyle, row, "审核人", 0, 0);
        cell = row.createCell(1);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("/");
        setHeaderStyle(cell, headerStyle, row, "/", 1, 7);


        if (downType == 1){
            row = createRow(sheet, 7, 22);
            cell = row.createCell(0);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("任务完成情况");
            setHeaderStyle(cell, headerStyle, row, "任务完成情况", 0, 0);
            cell = row.createCell(1);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("任务完成情况");
            setHeaderStyle(cell, headerStyle, row, "共1个任务，已完成0，剩余1", 1, 7);

            row = createRow(sheet, 8, 30);
        }else {
            row = createRow(sheet, 7, 30);
        }
        cell = row.createCell(0);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("设备详情");
        setHeaderStyle(cell, headerStyle, row, "设备详情", 0, 7);

        // 在sheet里增加合并单元格
        CellRangeAddress cra1 = new CellRangeAddress(0, 0, 0, 7);
        CellRangeAddress cra2 = new CellRangeAddress(1, 1, 0, 7);
        CellRangeAddress cra3 = new CellRangeAddress(2, 2, 0, 7);
        // 任务周期
        CellRangeAddress cra4 = new CellRangeAddress(3, 3, 3, 4);
        // 任务状态
        CellRangeAddress cra5 = new CellRangeAddress(3, 3, 6, 7);
        // 结束时间
        CellRangeAddress cra6 = new CellRangeAddress(4, 4, 3, 4);
        // 任务提醒时间
        CellRangeAddress cra7 = new CellRangeAddress(4, 4, 6, 7);
        // 执行人
        CellRangeAddress cra8 = new CellRangeAddress(5, 5, 1, 7);
        // 审核人
        CellRangeAddress cra9 = new CellRangeAddress(6, 6, 1, 7);

        if (downType == 1){
            // 任务完成情况
            CellRangeAddress cra11 = new CellRangeAddress(7, 7, 1, 7);
            // 设备详情
            CellRangeAddress cra10 = new CellRangeAddress(8, 8, 0, 7);
            sheet.addMergedRegion(cra11);
            sheet.addMergedRegion(cra10);
        }else {
            // 设备详情
            CellRangeAddress cra10 = new CellRangeAddress(7, 7, 0, 7);
            sheet.addMergedRegion(cra10);
        }

        sheet.addMergedRegion(cra1);
        sheet.addMergedRegion(cra2);
        sheet.addMergedRegion(cra3);
        sheet.addMergedRegion(cra4);
        sheet.addMergedRegion(cra5);
        sheet.addMergedRegion(cra6);
        sheet.addMergedRegion(cra7);
        sheet.addMergedRegion(cra8);
        sheet.addMergedRegion(cra9);
    }

    /**
     * @param sheet HSSFSheet
     * @param index 第几行
     * @param height 行高
     * @return Row
     */
    private static Row createRow(HSSFSheet sheet, Integer index, Integer height) {
        Row row = sheet.createRow(index);
        row.setHeightInPoints(height);
        return row;
    }

    /**
     * @param cell Cell
     * @param headerStyle HSSFCellStyle
     * @param row Row
     * @param tableName 表名
     * @param start 第几例开始
     * @param end 第几例结束
     */
    private void setHeaderStyle(Cell cell, HSSFCellStyle headerStyle, Row row, String tableName, Integer start, Integer end) {
        for (int j = start; j <= end; j++) {
            cell = row.createCell(j);
            // style为带边框的样式 上面有定义
            cell.setCellStyle(headerStyle);
            if(StringUtils.isNotEmpty(tableName)) {
                cell.setCellValue(tableName);
            }else {
                cell.setCellValue("/");
            }
        }
    }

    /**
     * @param file File
     * @param resultFileName List<String>
     * @return List<String>
     */
    private List<String> ergodic(File file, List<String> resultFileName) {
        File[] files = file.listFiles();
        if (files == null){
            // 判断目录下是不是空的
            return resultFileName;
        }

        for (File f : files) {
            // 判断是否文件夹
            if (f.isDirectory()) {
                resultFileName.add(f.getPath());
                // 调用自身,查找子目录
                ergodic(f, resultFileName);
            } else {
                resultFileName.add(f.getPath());
            }
        }
        return resultFileName;
    }

    /**
     * @param path 需要遍历的路径
     * @param deep deep
     * @return  ArrayList<String> 路径下文件的名称集合
     */
    private ArrayList<String> getFile(String path, int deep){
        // 获得指定文件对象
        File file = new File(path);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();
        ArrayList<String> list = new ArrayList<>();
        if (null != array){
            for(File files : array){
                // 如果是文件
                if(files.isFile()){
                    for (int j = 0; j < deep; j++) {
                        list.add(files.getName());
                    }
                    // 只输出文件名字
                }
            }
        }
        return list;
    }

    /**
     * 判断文件名是否重复
     *
     * @param names 文件下文件名的集合
     * @param name 存入的文件名
     * @param index 索引的开始位置
     * @return 符合要求的文件名
     */
    private String checkFileName(ArrayList<String> names, String name, int index) {
        while (names.contains(name)) {
            name = name.replace(".", "1.");
        }
        return name;
    }

    /**
     * @param wb HSSFWorkbook
     * @return HSSFCellStyle
     */
    private static HSSFCellStyle getHeaderStyle(HSSFWorkbook wb){
        HSSFCellStyle alignStyle = wb.createCellStyle();
        // 设置单元格内容水平对其方式
        // XSSFCellStyle.ALIGN_CENTER       居中对齐
        // XSSFCellStyle.ALIGN_LEFT         左对齐
        // XSSFCellStyle.ALIGN_RIGHT        右对齐
        alignStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

        // 设置单元格内容垂直对其方式
        // XSSFCellStyle.VERTICAL_TOP       上对齐
        // XSSFCellStyle.VERTICAL_CENTER    中对齐
        // XSSFCellStyle.VERTICAL_BOTTOM    下对齐
        alignStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        HSSFFont headFont = wb.createFont();
        short bold = 500;
        short fontHeight = 14;
        headFont.setBoldweight(bold);
        headFont.setFontHeightInPoints(fontHeight);
        alignStyle.setFont(headFont);

        return alignStyle;
    }

    /**
     * @param sheet HSSFSheet
     * @param index 序号是第几行
     * @return Row
     */
    private static Row createRow(HSSFSheet sheet, int index) {
        Row row = sheet.createRow(index);
        // 行高
        row.setHeightInPoints(22);
        return row;
    }

    /**
     * @param propertyName propertyName
     * @return String
     */
    private String getPropertyLabel(String propertyName) {
        return mapNames.get(propertyName);
    }

    /**
     * @param wb HSSFWorkbook
     * @return HSSFCellStyle
     */
    private static HSSFCellStyle getNormalStyle(HSSFWorkbook wb){
        HSSFCellStyle alignStyle = wb.createCellStyle();
        alignStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        alignStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        HSSFFont headFont = wb.createFont();
        short fontHeight = 14;
        headFont.setFontHeightInPoints(fontHeight);
        alignStyle.setFont(headFont);

        return alignStyle;
    }

    /**
     * @param cell Cell
     * @param entity Map<String, Object>
     * @param propertyName String
     * @param number Integer
     */
    private void setCellValue(Cell cell, Map<String, Object> entity, String propertyName, Integer number) {
        if (!"number".equals(propertyName)) {
            Object cellValue = getPropertyValue(entity, propertyName);
            if (cellValue != null) {
                if(!"".equals(cellValue)) {
                    cell.setCellValue(cellValue.toString());
                }else {
                    cell.setCellValue("/");
                }
            }
        } else {
            cell.setCellValue(number);
        }
    }

    /**
     * @param entity Map<String, Object>
     * @param propertyName String
     * @return Object
     */
    private Object getPropertyValue(Map<String, Object> entity, String propertyName) {
        if ("mtCheckState".equals(propertyName)) {
            if(entity.get("fdException") != null) {
                Boolean mtCheckState = Boolean.valueOf(entity.get("fdException").toString());
                if(mtCheckState) {
                    return "故障";
                }else {
                    return "正常";
                }
            }else{
                return "未检";
            }
        }
        return entity.get(propertyName);
    }

    /**
     * @param inStream 输入流
     * @return byte[]
     * @throws Exception Exception
     */
    private byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        // 每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        // 使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        // 把outStream里的数据写入内存
        return outStream.toByteArray();
    }
}
