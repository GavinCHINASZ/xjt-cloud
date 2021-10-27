package com.xjt.cloud.report.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.Dict;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.report.core.common.utils.ConstantsReport;
import com.xjt.cloud.report.core.dao.report.ReportFromDao;
import com.xjt.cloud.report.core.dao.report.ReportFromRecordDao;
import com.xjt.cloud.report.core.dao.report.ReportFromTitleDao;
import com.xjt.cloud.report.core.entity.report.*;
import com.xjt.cloud.report.core.service.service.ReportFromService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.*;

/**
 * 维保日报
 * 
 * @author huanggc
 * @date 2019/11/12
 */
@Service
public class ReportFromServiceImpl extends AbstractService implements ReportFromService {
    @Autowired
    private ReportFromDao reportFromDao;
    @Autowired
    private ReportFromTitleDao reportFromTitleDao;
    @Autowired
    private ReportFromRecordDao reportFromRecordDao;

    /**
     * 功能描述: 查看 日报报表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/06
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @Override
    public Data findReportFromList(String json, HttpServletRequest request, HttpServletResponse response) {
        ReportFrom reportFrom = JSONObject.parseObject(json, ReportFrom.class);

        ReportFrom report = reportFromDao.findReportFromOne(reportFrom);
        Map<String, Object> map = new HashMap<>(3);
        map.put("reportFrom", report);

        if (report != null){
            ReportFromRecord reportFromRecord = new ReportFromRecord();
            reportFromRecord.setReportFromId(report.getId());
            List<ReportFromRecord> reportFromRecordList = reportFromRecordDao.findReportFromRecordList(reportFromRecord);

            Long projectId = reportFrom.getProjectId();
            // 标题
            ReportFromTitle reportFromTitle = new ReportFromTitle();
            reportFromTitle.setProjectId(projectId);
            List<ReportFromTitle> reportFromTitleList = reportFromTitleDao.findReportFromTitleList(reportFromTitle);
            map.put("reportFromTitleList", reportFromTitleList);

            // 导出功能
            if (null != reportFrom.getDownType()){
                // 内容
                String content;
                int size = reportFromRecordList.size();
                for (int i = 0; i < reportFromTitleList.size(); i++) {
                    ReportFromTitle title = reportFromTitleList.get(i);
                    long reportFromTitleId = title.getId();
                    title.setSort(i + 1);

                    for (int j = 0; j < size; j++) {
                        ReportFromRecord entity = reportFromRecordList.get(j);
                        if (reportFromTitleId == entity.getReportFromTitleId()){
                            content = entity.getContent();
                            if (StringUtils.isNotEmpty(content)){
                                title.setPageIndex(content.split("\n").length);
                            }
                            // \n 换行
                            title.setContent(title.getContent() + entity.getContent() + "\n");
                        }
                    }
                }
                // 导出Excel表格方法
                downReportFromExcel(report, reportFromTitleList, response);
                return asseData(200);
            }else {
                // 页面数据
                map.put("reportFromRecordList", reportFromRecordList);
            }
        }
        return asseData(map);
    }

    /**
     * 功能描述: 日报报表 导出表格
     *
     * @param json 参数
     * @param response HttpServletResponse
     * @author huanggc
     * @date 2020/08/25
     */
    @Override
    public void downReportFromExcel(String json, HttpServletResponse response) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        ReportFrom reportFrom = JSONObject.parseObject(json, ReportFrom.class);
        
        List<ReportFromRecord> reportFromRecordList = null;
        if (CollectionUtils.isEmpty(reportFromRecordList)){
            return ;
        }

        Long projectId = reportFrom.getProjectId();
        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        // 表名
        jsonObject.put("title", projectName + "--声光报警表");
        String[] keys = {"rowNum", "checkPointName", "checkPointQrNo", "sensorId", "sountActionDesc"};
        //createAndDownloadExcel(response, reportFromRecordList, keys, ConstantsReport.REPORT_FROM_EXCEL_PATH, 4, null, jsonObject, "1:0");
    }

    /**
     * 维保日报导出表格方法
     *
     * @author huanggc
     * @param reportFrom ReportFrom
     * @param reportFromRecordList List<ReportFromTitle>
     * @param response HttpServletResponse
     */
    private void downReportFromExcel(ReportFrom reportFrom, List<ReportFromTitle> reportFromRecordList, HttpServletResponse response) {
        Long projectId = reportFrom.getProjectId();
        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        // 表名
        reportFrom.setTitle(reportFrom.getCreateTimeDescs() + projectName + "--维保日报表");
        reportFrom.setProjectName(projectName + "--消防系统维保");
        reportFrom.setWeather("天气:" + reportFrom.getWeather());

        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(reportFrom);

        String[] keys = {"rowNum", "titleName", "titleName", "titleName", "titleName"};

        createAndDownloadExcel(response, reportFromRecordList, keys, ConstantsReport.REPORT_FROM_EXCEL_PATH, 4,
                ConstantsReport.REPORT_FROM_EXCEL_DICT_CODE, jsonObject, "0:0");
    }

    /**
     * 功能描述:生成excel表格
     *
     * @param resp HttpServletResponse
     * @param list List<ReportFromTitle>
     * @param keys String[]
     * @param filePath 文件路径
     * @param headIndex int
     * @param dictItemCode String
     * @param modelValueJson JSONObject
     * @param titleIndex 标题行号
     * @author wangzhiwen
     * @date 2019/8/7 14:08
     */
    private static <T> void createAndDownloadExcel(HttpServletResponse resp, List<ReportFromTitle> list, String[] keys,
                                                  String filePath, Integer headIndex, String dictItemCode, JSONObject modelValueJson, String titleIndex ){
        try{
            // 得到文件类型
            String fileType = filePath.substring(filePath.lastIndexOf("."));
            Workbook writeWB = getWorkbook(filePath, fileType);
            // 得到第一个表单
            Sheet writeSheet = writeWB.getSheetAt(0);
            writeSheet = setExcelTitle(titleIndex, writeWB, writeSheet, modelValueJson);

            CellStyle cellStyle = getCellStyle(writeWB);
            // 判断是否有非列表信息
            if (StringUtils.isNotEmpty(dictItemCode)) {
                // 填充非列表信息
                setNotListByIndex(writeSheet, writeWB, dictItemCode, modelValueJson, cellStyle);
            }

            if (keys != null) {
                // 填充列表信息对象
                setListByList(writeSheet, writeWB, list, keys, headIndex, cellStyle);
            }
            downloadFile(resp, modelValueJson, fileType, writeWB);
        }catch (Exception e) {
            SysLog.error(e);
        }
    }

    /**
     * 报表模版配置key
     */
    private static String REPORT_MODEL_CONFIG = "REPORT_MODEL_CONFIG";

    /**
     * 功能描述: 填充非列表信息
     *
     * @param writeSheet Sheet
     * @param writeWB Workbook
     * @param dictItemCode dictItemCode
     * @param modelValueJson JSONObject
     * @param cellStyle CellStyle
     * @author wangzhiwen
     * @date 2019/9/10 11:06
     */
    private static void setNotListByIndex(Sheet writeSheet, Workbook writeWB, String dictItemCode, JSONObject modelValueJson, CellStyle cellStyle){
        Row writeRow = null;
        int rowIndex;
        int cellIndex = 0;

        // 得到模版配置数据词典对象
        Dict dict = DictUtils.getDictByDictAndItemCode(REPORT_MODEL_CONFIG, dictItemCode);
        // 得到要取值的key
        String[] keys = dict.getMemo().split(",");
        // 得到与key对应的表格坐标
        JSONObject json = JSONObject.parseObject(dict.getItemDescription());
        // 得到列的坐标数组
        JSONArray jsonArray = json.getJSONArray("cell");
        // 把列的坐标数组转化成list
        List<Integer> cellList = JSONObject.parseArray(jsonArray.toString(),Integer.class);
        String value;

        // 垂直居中
        //cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
        // 水平居中
        //cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

        // 判断报表模版是否有一定格式
        if ("true".equals(dict.getItemValue())){
            rowIndex = json.getInteger("row");
            // 设置每一行的值
            writeRow = getRow(writeSheet, rowIndex);

            for (String key : keys){
                value = modelValueJson.getString(key);
                value = StringUtils.isEmpty(value) ? "/" : value;
                // 设置每一列的值
                getCell(writeRow, cellList.get(cellIndex), cellStyle).setCellValue(value);
                // 判断列表的坐标数组是否取完，需要换到下一行
                if (cellIndex == cellList.size() - 1 ){
                    // 列的坐标重新取值
                    cellIndex = 0;
                    // 下一行坐标
                    rowIndex++;
                    // 得到下一行
                    writeRow = getRow(writeSheet, rowIndex);
                }else {
                    cellIndex++;
                }
            }
        }else {
            // 非一定格式的，坐标数组为一一对应关系
            // 生成一个样式
            CellStyle style = writeWB.createCellStyle();
            // 设置这些样式
            // 下边框
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            // 左边框
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            // 上边框
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            // 右边框
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);

            Font font = writeWB.createFont();
            // 字体大小
            font.setFontHeightInPoints((short) 11);
            font.setFontName("宋体");
            // 把字体 应用到当前样式
            style.setFont(font);
            // 得到行的坐标数组
            jsonArray = json.getJSONArray("row");
            List<Integer> rowList = JSONObject.parseArray(jsonArray.toString(),Integer.class);
            for (int i = 0; i < keys.length; i++){
                rowIndex = rowList.get(i);
                cellIndex = cellList.get(i);
                // 判断是否是第一行，或取得的行坐标与上一行不同，则重新获取一行
                if (i == 0 || (rowIndex != rowList.get(i - 1))){
                    // 设置每一行的值
                    writeRow = getRow(writeSheet, rowIndex);
                }

                value = modelValueJson.getString(keys[i]);
                value = StringUtils.isEmpty(value) ? "" : value;

                String key = keys[i];
                if (i > 1){
                    style = writeWB.createCellStyle();
                    // 设置这些样式
                    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                    style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                    style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                    // 垂直居中
                    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                }else {
                    // 水平居中
                    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    // 垂直居中
                    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                }
                // 设置每一列的值
                getCell(writeRow, cellIndex, style).setCellValue(value);
            }
        }
    }

    /**
     * 功能描述:填充列表的值
     *
     * @param writeSheet Sheet
     * @param writeWB Workbook
     * @param list List<ReportFromTitle>
     * @param keys String[]
     * @param headIndex int
     * @param cellStyle CellStyle
     * @author wangzhiwen
     * @date 2019/9/10 11:10
     */
    private static <T> void setListByList(Sheet writeSheet, Workbook writeWB, List<ReportFromTitle> list, String[] keys, int headIndex, CellStyle cellStyle){
        List<String> keyList = getMethodNames(keys);
        String key;
        Row writeRow;
        Cell cell;
        ReportFromTitle t;

        try {
            // 生成一个样式
            CellStyle style = writeWB.createCellStyle();
            int headIndexNum = 0;
            // 写内容
            for (int i = 0, l = list.size(); i < l; i++) {
                t = list.get(i);
                // 10-最后一行，向下移动一行
                writeSheet.shiftRows(headIndex + i + headIndexNum, writeSheet.getLastRowNum(), 1);
                // 设置每一行的值
                writeRow = writeSheet.createRow(headIndex + i + headIndexNum);

                for (int k = 0, n = keyList.size(); k < n; k++) {
                    key = keyList.get(k);
                    cell = writeRow.createCell(k);

                    // 判断第一列是否是序号列
                    if (k == 0 && "GETROWNUM".equals(key.toUpperCase())){
                        style = writeWB.createCellStyle();
                        // 设置这些样式
                        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

                        cell.setCellStyle(style);
                        cell.setCellValue(ArabicToChineseUtils.int2chineseNum(i + 1));
                    }else{
                        style = writeWB.createCellStyle();
                        // 设置这些样式
                        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

                        Font font = writeWB.createFont();
                        font.setFontHeightInPoints((short) 12);
                        // 加粗
                        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                        font.setFontName("宋体");
                        // 把字体 应用到当前样式
                        style.setFont(font);
                        cell.setCellStyle(style);

                        Method m = t.getClass().getMethod(key);
                        // 调用getter方法获取属性值
                        Object val = m.invoke(t);
                        // 设置每一列的值
                        cell.setCellValue(val == null ? "" : val.toString());
                    }
                }

                headIndexNum++;
                // 10-最后一行，向下移动一行
                writeSheet.shiftRows(headIndex + i + headIndexNum, writeSheet.getLastRowNum(), 1);
                // 设置每一行的值
                writeRow = writeSheet.createRow(headIndex + i + headIndexNum);

                for (int w = 0, n = keyList.size(); w < n; w++) {
                    key = keyList.get(w);
                    cell = writeRow.createCell(w);
                    // 判断第一列是否是序号列
                    if (w == 0 && "GETROWNUM".equals(key.toUpperCase())){
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(ArabicToChineseUtils.int2chineseNum(i + 1));
                    }else{
                        if (t.getPageIndex() > 1){
                            // 目的是想把行高设置成xxpx
                            writeRow.setHeightInPoints(t.getPageIndex() * 14 + 30);
                        } else {
                            writeRow.setHeightInPoints(30);
                        }
                        // 自动换行
                        cellStyle.setWrapText(true);
                        cell.setCellStyle(cellStyle);
                        // 内容
                        Method m = t.getClass().getMethod("getContent");
                        Object val = m.invoke(t);
                        cell.setCellValue(val == null ? "/" : val.toString());
                    }
                }
            }

            List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();
            CellRangeAddress cra1;
            for (int i = 0; i < list.size() * 2; i++) {
                // 合并日期占两行(4个参数，分别为起始行，结束行，起始列，结束列)
                // 行和列都是从0开始计数，且起始结束都会合并
                cra1 = new CellRangeAddress(headIndex + i, headIndex + i + 1, 0, 0);
                cellRangeAddressList.add(cra1);
                i++;
            }

            for (int i = 0; i < list.size() * 2; i++) {
                cra1 = new CellRangeAddress(headIndex + i, headIndex + i, 1, 4);
                cellRangeAddressList.add(cra1);
            }

            // 合并
            for (CellRangeAddress car: cellRangeAddressList) {
                writeSheet.addMergedRegion(car);
            }
        }catch (Exception ex){
            SysLog.error(ex);
        }
    }

    /**
     * 功能描述: 得到属性的方法名称
     *
     * @param keys String[]
     * @return java.util.List<java.lang.String>
     * @author wangzhiwen
     * @date 2019/9/10 11:06
     */
    private static List<String> getMethodNames(String[] keys){
        List<String> list = new ArrayList<>(keys.length);
        for (String key : keys){
            // 将属性的首字符大写，方便构造get，set方法)
            list.add("get" + key.substring(0, 1).toUpperCase() + key.substring(1));
        }
        return list;
    }

    /**
     * 功能描述:合建单元格样式
     *
     * @param writeWB Workbook
     * @return CellStyle
     * @author wangzhiwen
     * @date 2019/9/11 16:01
     */
    private static CellStyle getCellStyle(Workbook writeWB){
        CellStyle cellStyle = writeWB.createCellStyle();
        // 垂直居中
        cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
        // 水平居中
        //cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        return cellStyle;
    }

    /**
     * 功能描述: 得到excel表格
     *
     * @param filePath 文件路径
     * @param fileType 文件类型
     * @return org.apache.poi.ss.usermodel.Workbook
     * @author wangzhiwen
     * @date 2020/7/22 13:46
     */
    private static Workbook getWorkbook(String filePath, String fileType){
        InputStream in = null;
        try {
            String path = System.getProperty("user.dir");
            File file = new File(path + filePath);
            in = new FileInputStream(file);
            if (".xls".equals(fileType)) {
                return new HSSFWorkbook(in);
            } else if (".xlsx".equals(fileType)) {
                return new XSSFWorkbook(in);
            }
        }catch (Exception ex){
            SysLog.error(ex);
        }finally {
            if (in != null){
                try {
                    in.close();
                }catch (Exception e){ }
            }
        }
        return null;
    }

    /**
     * 功能描述: 填写表格标题
     *
     * @param titleIndex String
     * @param writeWB Workbook
     * @param writeSheet Sheet
     * @param modelValueJson JSONObject
     * @return org.apache.poi.ss.usermodel.Sheet
     * @author wangzhiwen
     * @date 2020/7/22 13:51
     */
    private static Sheet setExcelTitle(String titleIndex, Workbook writeWB, Sheet writeSheet, JSONObject modelValueJson){
        //判断是否有标题信息
        if(StringUtils.isNotEmpty(titleIndex)){
            // 生成一个样式
            CellStyle style = writeWB.createCellStyle();
            // 设置这些样式
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);

            Font font = writeWB.createFont();
            font.setFontHeightInPoints((short) 14);
            //font.setColor(Color.RED.index);
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setFontName("黑体");
            // 把字体 应用到当前样式
            style.setFont(font);

            String[] rowCell = titleIndex.split(":");
            // 设置每一列的值
            getCell(getRow(writeSheet, Integer.parseInt(rowCell[0])), Integer.parseInt(rowCell[0]), style)
                    .setCellValue(modelValueJson.getString("projectName"));
        }
        return writeSheet;
    }

    /**
     * 功能描述: 得到cell
     *
     * @param row Row
     * @param index int
     * @param cellStyle CellStyle
     * @return org.apache.poi.ss.usermodel.Cell
     * @author wangzhiwen
     * @date 2019/9/10 17:31
     */
    private static Cell getCell(Row row, int index, CellStyle cellStyle){
        Cell cell = row.getCell(index);
        if (cell == null){
            cell = row.createCell(index);
        }
        cell.setCellStyle(cellStyle);
        return cell;
    }

    /**
     * 功能描述: 获取row
     *
     * @param sheet Sheet
     * @param index int
     * @return org.apache.poi.ss.usermodel.Row
     * @author wangzhiwen
     * @date 2019/9/10 17:32
     */
    private static Row getRow(Sheet sheet, int index){
        // 设置每一行的值
        Row row = sheet.getRow(index);
        if (null == row){
            row = sheet.createRow(index);
        }
        return row;
    }

    /**
     * 功能描述:下载文件
     *
     * @param resp HttpServletResponse
     * @param modelValueJson JSONObject
     * @param fileType String
     * @param writeWB Workbook
     * @author wangzhiwen
     * @date 2020/7/22 13:55
     */
    private static void downloadFile(HttpServletResponse resp, JSONObject modelValueJson, String fileType, Workbook writeWB){
        try{
            // 设置生成的文件类型
            resp.setContentType("application/msexcel");
            // 设置文件头编码方式和文件名
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(modelValueJson.getString("title") + fileType,"UTF-8"));
            OutputStream os = resp.getOutputStream();
            writeWB.write(os);
            os.flush();
            os.close();
        }catch (Exception ex){
            SysLog.error(ex);
        }
    }

    /**
     * 功能描述: 保存/新增 日报报表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/06
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveReportFrom(String json) {
        ReportFrom reportFrom = JSONObject.parseObject(json, ReportFrom.class);
        // 内容
        List<ReportFromRecord> reportFromRecords = reportFrom.getReportFromRecordList();

        // 保存 日报
        reportFromDao.saveReportFrom(reportFrom);
        Long reportFromId = reportFrom.getId();

        Map<String, Object> map = new HashMap<>(2);
        if (CollectionUtils.isNotEmpty(reportFromRecords)){
            List<ReportFromRecord> reportFromRecordList = new ArrayList<>();

            for (ReportFromRecord r : reportFromRecords) {
                Long reportFromTitleId = r.getReportFromTitleId();
                String titleName = r.getTitleName();

                String[] split = r.getContent().split("↵");
                for (int i = 0; i < split.length; i++) {
                    ReportFromRecord record = new ReportFromRecord();
                    record.setReportFromId(reportFromId);
                    record.setReportFromTitleId(reportFromTitleId);
                    record.setTitleName(titleName);

                    // 前端输入框无法处理 %分号
                    if (split[i].contains("%25")){
                        split[i] = split[i].replace("%25", "%");
                    }

                    if (split[i].contains("%26")){
                        split[i] = split[i].replace("%26", "&");
                    }
                    record.setContent(split[i]);

                    reportFromRecordList.add(record);
                }
            }
            // 保存内容
            reportFromRecordDao.saveReportFromRecordList(reportFromRecordList);
            map.put("reportFromRecordList", reportFromRecordList);
        }

        ReportFrom report = reportFromDao.findReportFromById(reportFrom.getId());
        map.put("reportFrom", report);
        return asseData(map);
    }

    /**
     * 功能描述: 查询 填写过的日报 日期
     *
     * @param json 参数 参数
     * @author huanggc
     * @date 2020/07/07
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findReportFromDate(String json) {
        ReportFrom reportFrom = JSONObject.parseObject(json, ReportFrom.class);
        List<ReportFrom> reportFromList = reportFromDao.findReportFromDate(reportFrom);
        return asseData(reportFromList);
    }

    /**
     * 功能描述: 导出公共方法抽取
     *
     * @param fileUrl 文件生成的路径
     * @param fileName 导出的文件名+文件后缀
     * @param map 数据
     * @param modelName 模板名
     * @author huanggc
     * @date 2019/11/04
     */
    public static void downModels(HttpServletResponse response, String fileUrl, String fileName, Map<String, Object> map, Configuration configuration, String modelName){
        OutputStreamWriter oWriter = null;
        BufferedWriter writer = null;
        try {
            OutputStream outt = new FileOutputStream(fileUrl + fileName + ".doc");
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
        docToPdf(fileUrl + fileName + ".doc", fileUrl + fileName + ".pdf");
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
    private static Boolean docToPdf(String wordPath, String pdfPath) {
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
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @author huanggc
     * @date 2020-04-12
     * @param path 文件路径
     * @return boolean
     */
    public boolean deleteFile(String path, String docName, String pdfName) {
        boolean flag = false;
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
            if (temp.isDirectory()) {
                // 递归
                //deleteFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                flag = true;
            }
        }
        return flag;
    }

    /**
     * pdf转Word
     *
     * @param fileUrl 文件路径
     */
    private void pdfToWord(String fileUrl){
        try{
            // String fileUrl = "C:/xxxxx.pdf";
            PDDocument doc = PDDocument.load(new File(fileUrl));
            int pageNum = doc.getNumberOfPages();
            fileUrl = fileUrl.substring(0, fileUrl.lastIndexOf("."));
            String fileName = fileUrl + ".doc";
            File file = new File(fileName);
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(fileName);
            Writer writer = new OutputStreamWriter(fos, "UTF-8");

            PDFTextStripper stripper = new PDFTextStripper();
            // 排序
            stripper.setSortByPosition(true);
            // 设置转换的开始页
            stripper.setStartPage(1);
            // 设置转换的结束页
            stripper.setEndPage(pageNum);
            stripper.writeText(doc, writer);
            writer.close();
            doc.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
