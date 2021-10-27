package com.xjt.cloud.iot.core.service.impl.report;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.dict.Dict;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.commons.utils.IoUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 物联设备运营报表--导出表格
 *
 * @author huanggc
 * @date 2021/01/05
 **/
public class IotReportIoServiceImpl {

    /**
     * 功能描述:生成excel表格
     *
     * @param resp HttpServletResponse
     * @param dateMap        数据列表
     * @param keysMap        与列表的实体类属性对应
     * @param filePath       下载的文件名
     * @param headIndexMap      标题行号
     * @param dictItemCode   非列表信息数据词典模版配置key
     * @param modelValueJson 非列表信息对象json
     * @param titleIndexMap     标题所在位置
     * @param imagesMap     表格中的图片
     * @param anchorsMap       表格中的图片所在的位置
     * @author wangzhiwen
     * @date 2019/8/7 14:08
     */
    public static <T> void createAndDownloadExcelNotStyle(HttpServletResponse resp, Map<Integer, T> dateMap, Map<Integer, String[]> keysMap,
                      String filePath, Map<Integer, Integer> headIndexMap, String dictItemCode, JSONObject modelValueJson, Map<Integer, String> titleIndexMap,
                      Map<Integer, String[]> imagesMap, Map<Integer, List<HSSFClientAnchor>> anchorsMap) {

        try {
            // 得到文件类型
            String fileType = filePath.substring(filePath.lastIndexOf("."));
            Workbook workbook = getWorkbook(filePath, fileType);

            for (Map.Entry<Integer, T> entry : dateMap.entrySet()) {
                int index = entry.getKey();
                List<T> list = (List<T>) entry.getValue();

                // 得到第一个表单
                Sheet writeSheet = workbook != null ? workbook.getSheetAt(index) : null;
                writeSheet = setExcelTitle(titleIndexMap.get(index), workbook, writeSheet, modelValueJson);

                CellStyle cellStyle = getCellStyle(workbook);

                // 表格中的图片
                if (imagesMap != null) {
                    String[] images = imagesMap.get(index);
                    if (images != null) {
                        HSSFPatriarch patriarch = (HSSFPatriarch) (writeSheet != null ? writeSheet.createDrawingPatriarch() : null);
                        if (images.length > 0 && patriarch != null) {
                            List<HSSFClientAnchor> anchors = anchorsMap.get(index);
                            for (int i = 0; i < images.length; i++) {
                                // 获取照片的地址
                                URL url = new URL(images[i]);
                                // 打开链接
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                // 设置请求方式为"GET"
                                conn.setRequestMethod("GET");
                                // 超时响应时间为3秒
                                conn.setConnectTimeout(3 * 1000);
                                // 通过输入流获取图片数据
                                InputStream inStream = conn.getInputStream();
                                // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
                                byte[] data = IoUtils.inputStreamToByteArray(inStream);
                                //anchor1.setAnchorType(0);
                                patriarch.createPicture(anchors.get(i), workbook.addPicture(data, HSSFWorkbook.PICTURE_TYPE_JPEG));
                            }
                        }
                    }
                }

                if (StringUtils.isNotEmpty(dictItemCode)) {
                    // 判断是否有非列表信息   填充非列表信息
                    setNotListByIndex(writeSheet, dictItemCode, modelValueJson, cellStyle);
                }

                String[] keys = keysMap.get(index);
                if (index == 0){
                    if (keys != null) {
                        // 填充列表信息对象
                        setListByLists(writeSheet, list, keys, headIndexMap.get(index), cellStyle);
                    }
                }else {
                    if (keys != null) {
                        // 填充列表信息对象
                        setListByList(writeSheet, list, keys, headIndexMap.get(index), cellStyle);
                    }
                }
            }

            downloadFile(resp, modelValueJson, fileType, workbook);
        } catch (Exception e) {
            SysLog.error(e);
        }
    }

    // 报表模版配置key
    private static String REPORT_MODEL_CONFIG = "REPORT_MODEL_CONFIG";

    /**
     * 功能描述: 填充非列表信息
     *
     * @param writeSheet Sheet
     * @param dictItemCode dictItemCode
     * @param modelValueJson JSONObject
     * @author wangzhiwen
     * @date 2019/9/10 11:06
     */
    private static void setNotListByIndex(Sheet writeSheet, String dictItemCode, JSONObject modelValueJson, CellStyle cellStyle){
        Row writeRow = null;
        int rowIndex;
        int cellIndex = 0;
        //得到模版配置数据词典对象
        Dict dict = DictUtils.getDictByDictAndItemCode(REPORT_MODEL_CONFIG, dictItemCode);
        //得到要取值的key
        String[] keys = dict.getMemo().split(",");
        //得到与key对应的表格坐标
        JSONObject json = JSONObject.parseObject(dict.getItemDescription());
        //得到列的坐标数组
        JSONArray jsonArray = json.getJSONArray("cell");
        //把列的坐标数组转化成list
        List<Integer> cellList = JSONObject.parseArray(jsonArray.toString(),Integer.class);
        String value;

        if ("true".equals(dict.getItemValue())){
            //判断报表模版是否有一定格式
            rowIndex = json.getInteger("row");
            //设置每一行的值
            writeRow = getRow(writeSheet, rowIndex);

            for (String key : keys){
                value = modelValueJson.getString(key);
                value = StringUtils.isEmpty(value) ? "/" : value;
                //设置每一列的值
                getCell(writeRow, cellList.get(cellIndex), cellStyle).setCellValue(value);
                //判断列表的坐标数组是否取完，需要换到下一行
                if (cellIndex == cellList.size() - 1 ){
                    //列的坐标重新取值
                    cellIndex = 0;
                    //下一行坐标
                    rowIndex++;
                    //得到下一行
                    writeRow = getRow(writeSheet, rowIndex);
                }else {
                    cellIndex++;
                }
            }
        }else {
            //非一定格式的，坐标数组为一一对应关系
            //得到行的坐标数组
            jsonArray = json.getJSONArray("row");
            List<Integer> rowList = JSONObject.parseArray(jsonArray.toString(),Integer.class);
            for (int i = 0; i < keys.length; i++){
                rowIndex = rowList.get(i);
                cellIndex = cellList.get(i);
                if (i == 0 || (i > 0 && rowIndex != rowList.get(i - 1))){
                    //判断是否是第一行，或取得的行坐标与上一行不同，则重新获取一行
                    //设置每一行的值
                    writeRow = getRow(writeSheet, rowIndex);
                }
                value = modelValueJson.getString(keys[i]);
                value = StringUtils.isEmpty(value) ? "/" : value;
                //设置每一列的值
                getCell(writeRow, cellIndex, cellStyle).setCellValue(value);
            }
        }
    }

    /**
     * 功能描述:合建单元格样式
     *
     * @param workbook Workbook
     * @return CellStyle
     * @author wangzhiwen
     * @date 2019/9/11 16:01
     */
    private static CellStyle getCellStyle(Workbook workbook){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // 水平居中
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 垂直居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 下边框
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // 左边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        // 上边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 右边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        return cellStyle;
    }

    /**
     * 功能描述: 得到excel表格
     *
     * @param filePath String
     * @param fileType String
     * @return Workbook
     * @author wangzhiwen
     * @date 2020/7/22 13:46
     */
    private static Workbook getWorkbook(String filePath, String fileType) {
        InputStream in = null;
        try {
            String path = System.getProperty("user.dir");
            File file = new File(path + filePath);
            in = new FileInputStream(file);
            // 判断文件类型
            if (".xls".equals(fileType)) {
                return new HSSFWorkbook(in);
            } else if (".xlsx".equals(fileType)) {
                return new XSSFWorkbook(in);
            }
        } catch (Exception ex) {
            SysLog.error(ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {

            }
        }
        return null;
    }

    /**
     * 功能描述: 填写表格标题
     *
     * @param titleIndex String
     * @param workbook Workbook
     * @param writeSheet Sheet
     * @param modelValueJson JSONObject
     * @return Sheet
     * @author wangzhiwen
     * @date 2020/7/22 13:51
     */
    private static Sheet setExcelTitle(String titleIndex, Workbook workbook, Sheet writeSheet, JSONObject modelValueJson) {
        //判断是否有标题信息
        if (StringUtils.isNotEmpty(titleIndex)) {
            // 生成一个样式
            CellStyle style = workbook.createCellStyle();
            // 设置这些样式
            // 水平居中
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            // 垂直居中
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            // 下边框
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            // 左边框
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            // 上边框
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            // 右边框
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 14);
            //font.setColor(Color.RED.index);
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setFontName("黑体");
            // 把字体 应用到当前样式
            style.setFont(font);

            String[] rowCell = titleIndex.split(":");
            if (modelValueJson.getString("beginTimeStrEndTimeStr") != null) {
                getCell(getRow(writeSheet, Integer.parseInt(rowCell[0])), Integer.parseInt(rowCell[1]), style).setCellValue(modelValueJson.getString("beginTimeStrEndTimeStr"));
            }else {
                //设置每一列的值
                getCell(getRow(writeSheet, Integer.parseInt(rowCell[0])), Integer.parseInt(rowCell[1]), style).setCellValue(modelValueJson.getString("title"));
            }

        }
        return writeSheet;
    }

    /**
     * 功能描述:填充列表的值
     *
     * @param writeSheet Sheet
     * @param list
     * @param keys
     * @param headIndex
     * @author wangzhiwen
     * @date 2019/9/10 11:10
     */
    private static <T> void setListByList(Sheet writeSheet, List<T> list, String[] keys, int headIndex, CellStyle cellStyle) {
        List<String> keyList = getMethodNames(keys);
        String key;
        Row writeRow;
        Cell cell;
        T t;
        try {
            if (CollectionUtils.isNotEmpty(list)) {
                //写内容
                for (int i = 0, l = list.size(); i < l; i++) {
                    t = list.get(i);
                    //10-最后一行，向下移动一行
                    writeSheet.shiftRows(headIndex + i, writeSheet.getLastRowNum(), 1);
                    writeRow = writeSheet.createRow(headIndex + i);//设置每一行的值
                    for (int k = 0, n = keyList.size(); k < n; k++) {
                        key = keyList.get(k);
                        cell = writeRow.createCell(k);
                        cell.setCellStyle(cellStyle);
                        //判断第一列是否是序号列
                        if (k == 0 && "GETROWNUM".equals(key.toUpperCase())) {
                            cell.setCellValue(i + 1);//设置每一列的值
                        } else {
                            Method m = t.getClass().getMethod(key);
                            Object val = m.invoke(t);// 调用getter方法获取属性值
                            cell.setCellValue(val == null ? "/" : val.toString());//设置每一列的值
                        }
                    }
                }
            }
        } catch (Exception ex) {
            SysLog.error(ex);
        }
    }

    /**
     * 功能描述:填充列表的值
     *
     * @param writeSheet Sheet
     * @param list List<T>
     * @param keys String[]
     * @param headIndex headIndex
     * @author wangzhiwen
     * @date 2019/9/10 11:10
     */
    private static <T> void setListByLists(Sheet writeSheet, List<T> list, String[] keys, int headIndex, CellStyle cellStyle) {
        String key;
        Row writeRow;
        T t;
        try {
            int keysSize = keys.length;
            for (int i = 0, l = list.size(); i < l; i++) {
                // 写内容
                // 10-最后一行，向下移动一行
                writeSheet.shiftRows(headIndex + i, writeSheet.getLastRowNum(), 1);
                //设置每一行的值
                writeRow = writeSheet.getRow(headIndex + i);
                if (null == writeRow) {
                    //设置每一行的值
                    writeRow = writeSheet.createRow(headIndex + i);
                }

                for (int k = 0, n = keysSize; k < n; k++) {
                    if (writeRow.getCell(k) == null) {
                        writeRow.createCell(k);
                    }
                }
            }

            String oneCalRowValue = "";
            // 第一个列的开始行
            int oneCalRowStar = headIndex;
            int oneCalRowEnd = headIndex;
            String twoCalRowValue = "";
            // 第二个列的开始行
            int twoCalRowStar = headIndex;
            int twoCalRowEnd = headIndex;

            List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();
            CellRangeAddress cra1;

            int cellIndex = 0;
            JSONObject jsonObject;
            for (int i = 0; i < list.size(); i++) {
                // 写内容
                t = list.get(i);
                jsonObject = (JSONObject) JSONObject.toJSON(t);
                // 设置每一行的值
                writeRow = writeSheet.getRow(headIndex + i);

                // 设置第一列表的值
                key = keys[0];

                /*if ("ROWNUM".equals(key.toUpperCase())) {
                    //判断第一列是否是序号列
                    //设置每一列的值
                    writeRow.getCell(0).setCellValue(i + 1);
                    cellIndex = 1;
                }*/

                for (int k = cellIndex; k < keysSize; k++) {
                    Cell cell = writeRow.getCell(k);
                    // 样式
                    cell.setCellStyle(cellStyle);

                    key = keys[k];
                    String val = jsonObject.getString(keys[k]);
                    if (val == null){
                        val = "";
                    }
                    if ("devicesTypeDesc".equals(key) && StringUtils.isEmpty(oneCalRowValue)){
                        oneCalRowValue = val;
                        oneCalRowEnd--;
                    }
                    if ("eventsTypeDesc".equals(key) && StringUtils.isEmpty(twoCalRowValue)){
                        twoCalRowValue = val;
                        twoCalRowEnd--;
                    }

                    // 设置每一列的值
                    cell.setCellValue(val);
                    if ("devicesTypeDesc".equals(key)) {
                        // 判断列是否 是所需要合并的列
                        if (!oneCalRowValue.equals(val)){
                            if (oneCalRowStar < oneCalRowEnd) {
                                // 起始行号，终止行号， 起始列号，终止列号
                                cra1 = new CellRangeAddress(oneCalRowStar, oneCalRowEnd, 0, 0);
                                cellRangeAddressList.add(cra1);
                                oneCalRowStar = oneCalRowEnd + 1;
                            }else {
                                oneCalRowStar++;
                            }
                            oneCalRowValue = val;
                        }
                        oneCalRowEnd++;
                    }

                    if ("eventsTypeDesc".equals(key)) {
                        // 判断列是否 是所需要合并的列
                        if (!twoCalRowValue.equals(val)){
                            if (twoCalRowStar < twoCalRowEnd) {
                                cra1 = new CellRangeAddress(twoCalRowStar, twoCalRowEnd, 1, 1);
                                cellRangeAddressList.add(cra1);
                                twoCalRowStar = twoCalRowEnd + 1;
                            }else {
                                twoCalRowStar++;
                            }
                            twoCalRowValue = val;
                        }
                        twoCalRowEnd++;
                    }
                }
            }

            // 合并
            for (CellRangeAddress car: cellRangeAddressList) {
                writeSheet.addMergedRegion(car);
            }
        } catch (Exception ex) {
            SysLog.error(ex);
        }
    }

    /**
     * 功能描述:下载文件
     *
     * @param resp HttpServletResponse
     * @param modelValueJson JSONObject
     * @param fileType String
     * @param workbook Workbook
     * @author wangzhiwen
     * @date 2020/7/22 13:55
     */
    private static void downloadFile(HttpServletResponse resp, JSONObject modelValueJson, String fileType, Workbook workbook) {
        try {
            // 设置生成的文件类型
            resp.setContentType("application/msexcel");
            // 设置文件头编码方式和文件名
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(modelValueJson.getString("title") + fileType, "UTF-8"));
            OutputStream os = resp.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception ex) {
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
    private static List<String> getMethodNames(String[] keys) {
        List<String> list = new ArrayList<>();
        for (String key : keys) {
            // 将属性的首字符大写，方便构造get，set方法)
            list.add("get" + key.substring(0, 1).toUpperCase() + key.substring(1));
        }
        return list;
    }

    /**
     * 功能描述: 得到cell
     *
     * @param row   Row
     * @param index index
     * @return org.apache.poi.ss.usermodel.Cell
     * @author wangzhiwen
     * @date 2019/9/10 17:31
     */
    private static Cell getCell(Row row, int index, CellStyle cellStyle) {
        Cell cell = row.getCell(index);
        if (cell == null) {
            cell = row.createCell(index);
        }
        cell.setCellStyle(cellStyle);
        return cell;
    }

    /**
     * 功能描述: 获取row
     *
     * @param sheet Sheet
     * @param index index
     * @return Row
     * @author wangzhiwen
     * @date 2019/9/10 17:32
     */
    private static Row getRow(Sheet sheet, int index) {
        //设置每一行的值
        Row row = sheet.getRow(index);
        if (null == row) {
            row = sheet.createRow(index);
        }
        return row;
    }

}
