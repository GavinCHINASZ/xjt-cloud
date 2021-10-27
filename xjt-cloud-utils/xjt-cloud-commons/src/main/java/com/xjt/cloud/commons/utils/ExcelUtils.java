package com.xjt.cloud.commons.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.dict.Dict;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.exception.BaseServiceException;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wangzhiwen
 * @date 2019/7/18 10:58
 * : Excel解析与填充下载工具类
 */
public class ExcelUtils {
    /**
     * 读取excel 只能读取第一个sheet
     *
     * @param path     excel路径
     * @param rowIndex 获取数据开始的行
     * @param beginCel 获取数据开始的列
     * @param sheetNum 从0开始
     * @return list<T>
     */
    public static <T> List<T> readyExcel(MultipartFile path, Integer rowIndex, Integer beginCel, int sheetNum, String[] keys, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try {
            String fileName = path.getOriginalFilename();
            String type = null;
            if (fileName != null) {
                type = fileName.substring(fileName.lastIndexOf("."));
            }
            // 创建工作簿
            Workbook wb;
            if (".xls".equals(type)) {
                wb = new HSSFWorkbook(path.getInputStream());
            } else if (".xlsx".equals(type)) {
                wb = new XSSFWorkbook(path.getInputStream());
            } else {
                return null;
            }
            // 获取有多少sheets
            int sheet = wb.getNumberOfSheets();
            // 获取第一个sheet
            Sheet hs;
            if (sheet >= sheetNum) {
                hs = wb.getSheetAt(sheetNum);
            } else {
                hs = wb.getSheetAt(0);
            }

            //判断是否存在合并单元格
            //boolean isMerged = hasMerged(hs);
            List<CellRangeAddress> rangeList = new ArrayList<CellRangeAddress>();
            /*if(isMerged){
                // 获得一个 sheet 中合并单元格的数量
                int sheetMergerCount = hs.getNumMergedRegions();
                for (int i = 0; i < sheetMergerCount; i++) {
                    // 获得合并单元格加入list中
                    CellRangeAddress ca = hs.getMergedRegion(i);
                    rangeList.add(ca);
                }
            }*/

            //总行数
            int num = hs.getPhysicalNumberOfRows();
            //获取循环的列
            int cellNum = keys.length;
            Integer[] cellIndexes = new Integer[cellNum];
            for (int b = beginCel < 0 ? 0 : beginCel; b < cellNum; b++) {
                cellIndexes[b] = b;
            }
            // 获取属性类型，并将属性首字母转成大写，以便填值
            List<String> propertyList = getProperties(keys, clazz);

            //int range = rangeList.size();
            T obj;
            Row hr;
            //int flag;
            String value;
            boolean isBreak = false;
            //开始循环行
            for (; rowIndex <= num; rowIndex++) {
                obj = clazz.newInstance();
                //获取行信息
                hr = hs.getRow(rowIndex);
                if (hr == null) {
                    continue;
                }
                //判断是否有合并单元格
                /*if(isMerged){
                    //当循环的当前行i在合并单元格中则先清空list再装入到list中避免上一次的数据还存在
                    List<CellRangeAddress> getCellList = new ArrayList<>();
                    getCellList.clear();
                    for(int j = 0;j < range;j++){
                        //可以自己打印下 firstrow表示开始行，lastrow表示结束行 FirstColumn表示开始列 LastColumn表示结束列
                        //把属于当前行的信息提取出来
                        if(rowIndex >= rangeList.get(j).getFirstRow() && rowIndex<= rangeList.get(j).getLastRow()){
                            getCellList.add(rangeList.get(j));
                        }
                    }
                    //控制重复读取
                    flag = 0;
                    //循环列读取信息
                    for (Integer cellIndex : cellIndexes) {
                        for(int n = beginCel;n < getCellList.size();n++){
                            if(cellIndex == getCellList.get(n).getFirstColumn()){
                                setObjValue(keys[cellIndex], propertyList.get(cellIndex),
                                        getMergedRegionValue(hs,getCellList.get(n).getFirstRow(),getCellList.get(n).getFirstColumn()), clazz, obj);
                                flag = 1; //当该列读取过一次跳出循环后不读取
                                break;
                            }
                        }
                        if(flag == 0){
                            setObjValue(keys[cellIndex], propertyList.get(cellIndex), getStringCellValue(hr.getCell(cellIndex)), clazz, obj);
                        }else{
                            flag = 0;
                        }
                    }
                }else{*/

                // 获取行信息
                for (int cellIndex = beginCel < 0 ? 0 : beginCel; cellIndex < cellIndexes.length; cellIndex++) {
                    value = getStringCellValue(hr.getCell(cellIndex));
                    // 判断第一列是否是序号，如不是则跳出
                    if (cellIndex == beginCel && (StringUtils.isEmpty(value) || !StringUtils.isInteger(value))) {
                        isBreak = true;
                        break;
                    }
                    if (!"/".equals(value) && StringUtils.isNotEmpty(value) && value.trim().length() > 0) {
                        setObjValue(keys[cellIndex], propertyList.get(cellIndex), value, clazz, obj);
                    }
                }
                //}
                if (isBreak) {
                    // 判断第一列是否是序号，如不是则跳出
                    break;
                }
                list.add(obj);
            }
        } catch (Exception ex) {
            SysLog.error(ex);
            throw BaseServiceException.initException(ConstantsMsg.UPLOAD_FILE_READ_FAIL, ServiceErrCode.REQ_ERR.getCode());
        }
        return list;
    }

    /**
     * 功能描述:获取属性类型，并将属性首字母转成大写，以便填值
     *
     * @param keys 列名
     * @param clazz 数据
     * @return java.util.List<java.lang.String>
     * @author wangzhiwen
     * @date 2019/8/5 10:57
     */
    private static <T> List<String> getProperties(String[] keys, Class<T> clazz) {
        String property;
        List<String> propertyList = new ArrayList<>();
        try {
            // 获取属性类型，并将属性首字母转成大写，以便填值
            for (int i = 0; i < keys.length; i++) {
                property = keys[i];
                // 获取属性的类型
                String propertyType = clazz.getDeclaredField(property).getGenericType().toString();
                propertyList.add(propertyType);
                // 将属性的首字符大写，方便构造get，set方法
                property = property.substring(0, 1).toUpperCase() + property.substring(1);
                keys[i] = property;
            }
        } catch (Exception ex) {
            SysLog.error(ex);
            throw BaseServiceException.initException(ConstantsMsg.UPLOAD_FILE_READ_FAIL, ServiceErrCode.REQ_ERR.getCode());
        }
        return propertyList;
    }

    /**
     * 功能描述: 取得表格中的值，填入bean对象中
     *
     * @param property     属性名称
     * @param propertyType 属性类型
     * @param value        表格值
     * @param clazz        bean类型
     * @param t            bean对象
     * @return T
     * @author wangzhiwen
     * @date 2019/7/18 14:41
     */
    private static <T> T setObjValue(String property, String propertyType, String value, Class<T> clazz, T t) {
        try {
            Method m;
            // 如果type是类类型，则前面包含"class "，后面跟类名
            if ("class java.lang.Integer".equals(propertyType)) {
                m = clazz.getMethod("set" + property, Integer.class);
                Integer v = Integer.parseInt(value);
                m.invoke(t, v);
            } else if ("class java.lang.Boolean".equals(propertyType)) {
                m = clazz.getMethod("set" + property, Boolean.class);
                Boolean b = Boolean.parseBoolean(value);
                m.invoke(t, b);
            } else if ("class java.util.Date".equals(propertyType)) {
                m = clazz.getMethod("set" + property, Date.class);
                Date date = DateUtils.parseDate(value);
                if (null != date) {
                    m.invoke(t, date);
                }
            } else if ("class java.lang.Long".equals(propertyType)) {
                m = clazz.getMethod("set" + property, Long.class);
                Long v = Long.parseLong(value);
                m.invoke(t, v);
            } else {
                // 默认为字符串
                m = clazz.getMethod("set" + property, String.class);
                m.invoke(t, value);
            }
        } catch (Exception e) {
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.UPLOAD_FILE_READ_FAIL, ServiceErrCode.REQ_PARAM_ERR.getCode());
        }
        return t;
    }

    /**
     * 读取excel数据 不能读取公式
     *
     * @param cell Cell
     * @return String
     */
    private static String getStringCellValue(Cell cell) {
        String strCell;
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    if (date != null) {
                        strCell = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    } else {
                        strCell = "";
                    }
                } else {
                    strCell = new DecimalFormat("#.#########").format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_FORMULA:
                strCell = cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            default:
                strCell = "";
                break;
        }
        if (StringUtils.isEmpty(strCell)) {
            return "";
        }

        return strCell.trim();
    }

    /**
     * 获取合并单元格的值
     *
     * @param sheet Sheet
     * @param row int
     * @param column int
     * @return String
     */
    private static String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getStringCellValue(fCell);
                }
            }
        }

        return null;
    }

    /**
     * 判断sheet页中是否含有合并单元格
     *
     * @param sheet Sheet
     * @return boolean
     */
    private static boolean hasMerged(Sheet sheet) {
        return sheet.getNumMergedRegions() > 0 ? true : false;
    }


    /**
     * 功能描述:生成excel表格
     *
     * @param resp HttpServletResponse
     * @param list      数据列表
     * @param heads     {"订单号","下单时间","产品类型","产品名称","商品价格"}
     * @param keys      {"orderId","orderTime","productType","productName","chargingPriceDesc"} 与列表的实体类属性对应
     * @param fileName  下载的文件名
     * @param headIndex 标题行号
     * @author wangzhiwen
     * @date 2019/8/7 14:08
     */
    public static <T> void createAndDownloadExcel(HttpServletResponse resp, List<T> list, String[] heads, String[] keys, String fileName, int headIndex) {
        Workbook writeWB;
        Row writeRow;
        try {
            fileName = fileName + ".xls";
            writeWB = new HSSFWorkbook();
            Sheet writeSheet = writeWB.createSheet();
            T t;
            /**
             * 合并单元格
             *    第一个参数：第一个单元格的行数（从0开始）
             *    第二个参数：第二个单元格的行数（从0开始）
             *    第三个参数：第一个单元格的列数（从0开始）
             *    第四个参数：第二个单元格的列数（从0开始）
             */
            /*CellRangeAddress range = new CellRangeAddress(1, 2, num -1, num);
            writeSheet.addMergedRegion(range);*/

            //设置单元格样式
            /*CellStyle columnHeadStyle = writeWB.createCellStyle();
            Font font = writeWB.createFont();
            font.setFontName("宋体");
            font.setFontHeightInPoints((short) 18);
            columnHeadStyle.setFont(font);
            columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
            columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
            columnHeadStyle.setFillForegroundColor(HSSFColor.GREEN.index);//设置背景色为绿色*/

            //初使化，必须初使化所有合并的单元格
            /*writeRow = writeSheet.createRow(1);
            Cell cell = writeRow.createCell(num - 1);
            cell.setCellValue("订单");
            cell.setCellStyle(columnHeadStyle);
            writeRow.createCell(num);
            writeRow = writeSheet.createRow(2);
            writeRow.createCell(num - 1);
            writeRow.createCell(num);*/

            //设置第headIndex行为标题行，表格行索引是从0开始
            writeRow = writeSheet.createRow(headIndex - 1);
            //写头
            for (int i = 0, n = heads.length; i < n; i++) {
                //设置每一列的值
                writeRow.createCell(i).setCellValue(heads[i]);
                // 设置列宽
                writeSheet.setColumnWidth(i, 5000);
            }

            setListByList(writeSheet, list, keys, headIndex, getCellStyle(writeWB));
            //设置生成的文件类型
            resp.setContentType("application/msexcel");
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            OutputStream os = resp.getOutputStream();
            writeWB.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            SysLog.error(e);
        }
    }

    // 报表模版配置key
    private static String REPORT_MODEL_CONFIG = "REPORT_MODEL_CONFIG";

    /**
     * 功能描述: 得到excel表格
     *
     * @param filePath 文件路径
     * @param fileType 文件类型
     * @return org.apache.poi.ss.usermodel.Workbook
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
     * @param titleIndex 标题在表格中的位置
     * @param workbook Workbook
     * @param writeSheet Sheet
     * @param modelValueJson JSONObject
     * @return org.apache.poi.ss.usermodel.Sheet
     * @author wangzhiwen
     * @date 2020/7/22 13:51
     */
    private static Sheet setExcelTitle(String titleIndex, Workbook workbook, Sheet writeSheet, JSONObject modelValueJson) {
        // 判断是否有标题信息
        if (StringUtils.isNotEmpty(titleIndex)) {
            // 生成一个样式
            CellStyle style = workbook.createCellStyle();
            // 设置这些样式
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
            Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 14);
            //font.setColor(Color.RED.index);
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setFontName("黑体");
            // 把字体 应用到当前样式
            style.setFont(font);

            String[] rowCell = titleIndex.split(":");
            //设置每一列的值
            if (modelValueJson.getString("beginTimeStrEndTimeStr") != null) {
                //  beginTimeStrEndTimeStr="2020年12月1日 至 2020年12月31日"
                getCell(getRow(writeSheet, Integer.parseInt(rowCell[0])), Integer.parseInt(rowCell[1]), style).setCellValue(modelValueJson.getString("beginTimeStrEndTimeStr"));
            } else {
                getCell(getRow(writeSheet, Integer.parseInt(rowCell[0])), Integer.parseInt(rowCell[1]), style).setCellValue(modelValueJson.getString("title"));
            }
        }
        return writeSheet;
    }

    /**
     * 功能描述:下载文件
     *
     * @param resp HttpServletResponse
     * @param modelValueJson JSONObject
     * @param fileType 文件类型
     * @param workbook Workbook
     * @author wangzhiwen
     * @date 2020/7/22 13:55
     */
    private static void downloadFile(HttpServletResponse resp, JSONObject modelValueJson, String fileType, Workbook workbook) {
        try {
            resp.setContentType("application/msexcel");//设置生成的文件类型
            resp.setCharacterEncoding("UTF-8");//设置文件头编码方式和文件名
            resp.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(modelValueJson.getString("title") + fileType, "UTF-8"));
            OutputStream os = resp.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception ex) {
            SysLog.info(ex);
        }
    }

    /**
     * 功能描述:生成excel表格
     *
     * @param resp HttpServletResponse
     * @param list           数据列表
     * @param keys           {"orderId","orderTime","productType","productName","chargingPriceDesc"} 与列表的实体类属性对应
     * @param filePath       下载的文件名
     * @param headIndex      标题行号
     * @param dictItemCode   非列表信息数据词典模版配置key
     * @param modelValueJson 非列表信息对象json
     * @param titleIndex     标题所在位置
     * @author wangzhiwen
     * @date 2019/8/7 14:08
     */
    public static <T> void createAndDownloadExcel(HttpServletResponse resp, List<T> list, String[] keys,
                                                  String filePath, Integer headIndex, String dictItemCode, JSONObject modelValueJson, String titleIndex) {
        try {
            //得到文件类型
            String fileType = filePath.substring(filePath.lastIndexOf("."));
            Workbook workbook = getWorkbook(filePath, fileType);
            //得到第一个表单
            Sheet writeSheet = workbook != null ? workbook.getSheetAt(0) : null;
            writeSheet = setExcelTitle(titleIndex, workbook, writeSheet, modelValueJson);

            CellStyle cellStyle = null;
            if (workbook != null) {
                cellStyle = getCellStyle(workbook);
            }
            //判断是否有非列表信息
            if (StringUtils.isNotEmpty(dictItemCode)) {
                //填充非列表信息
                setNotListByIndex(writeSheet, dictItemCode, modelValueJson, cellStyle);
            }
            //填充列表信息对象
            if (keys != null) {
                setListByList(writeSheet, list, keys, headIndex, cellStyle);
            }
            downloadFile(resp, modelValueJson, fileType, workbook);
        } catch (Exception e) {
            SysLog.error(e);
        }
    }


    /**
     * 功能描述:生成excel表格
     *
     * @param resp HttpServletResponse
     * @param list           数据列表
     * @param keys           {"orderId","orderTime","productType","productName","chargingPriceDesc"} 与列表的实体类属性对应
     * @param filePath       下载的文件名
     * @param headIndex      标题行号
     * @param dictItemCode   非列表信息数据词典模版配置key
     * @param modelValueJson 非列表信息对象json
     * @param titleIndex     标题所在位置
     * @author wangzhiwen
     * @date 2019/8/7 14:08
     */
    public static <T> void createAndDownloadExcelNotStyle(HttpServletResponse resp, List<T> list, String[] keys,
                                                          String filePath, Integer headIndex, String dictItemCode, JSONObject modelValueJson, String titleIndex) {
        try {
            //得到文件类型
            String fileType = filePath.substring(filePath.lastIndexOf("."));
            Workbook workbook = getWorkbook(filePath, fileType);
            Sheet writeSheet = workbook != null ? workbook.getSheetAt(0) : null;
            writeSheet = setExcelTitle(titleIndex, workbook, writeSheet, modelValueJson);

            if (keys != null) {
                //填充列表信息对象
                setListByList(writeSheet, list, keys, headIndex);
            }
            downloadFile(resp, modelValueJson, fileType, workbook);
        } catch (Exception e) {
            SysLog.error(e);
        }
    }

    /**
     * 功能描述:生成excel表格
     *
     * @param resp HttpServletResponse
     * @param dataList        数据列表
     * @param keysList        与列表的实体类属性对应
     * @param filePath       下载的文件名
     * @param headIndexList      标题行号
     * @param dictItemCodeList   非列表信息数据词典模版配置key
     * @param modelValueJsonList 非列表信息对象json
     * @param titleIndexList     标题所在位置
     * @param filesList     表格中的图片
     * @param pictRowIndexs       表格中的图片所在的位置
     * @param pictColIndexs       表格中的图片所在的位置
     * @author wangzhiwen
     * @date 2019/8/7 14:08
     */
    public static <T> void createAndDownloadImgAndListExcelSheetsNotStyle(HttpServletResponse resp, List<List<T>> dataList, List<String[]> keysList,
                                                                    String filePath, List<Integer> headIndexList, List<String> dictItemCodeList,
                                                                          List<JSONObject> modelValueJsonList, List<String> titleIndexList,
                                                                          int[][] pictRowIndexs,int[][] pictColIndexs,List<MultipartFile[]> filesList) {
        try {
            //得到文件类型
            String fileType = filePath.substring(filePath.lastIndexOf("."));
            Workbook workbook = getWorkbook(filePath, fileType);
            CellStyle cellStyle = workbook.createCellStyle();
            for (int i = 0; i < dataList.size(); i++) {
                createImgAndListExcelSheetNotStyle(dataList.get(i), keysList.get(i), filesList.get(i), headIndexList.get(i), dictItemCodeList.get(i),
                        modelValueJsonList.get(i), titleIndexList.get(i), pictRowIndexs[i],pictColIndexs[i], workbook, i, cellStyle);
            }
            downloadFile(resp, modelValueJsonList.get(0), fileType, workbook);
        } catch (Exception e) {
            SysLog.error(e);
        }
    }

    /**
     * 功能描述:生成excel表格
     *
     * @param list        数据列表
     * @param keys        与列表的实体类属性对应
     * @param files       生成图片的数组
     * @param headIndex      标题行号
     * @param dictItemCode   非列表信息数据词典模版配置key
     * @param modelValueJson 非列表信息对象json
     * @param titleIndex     标题所在位置
     * @param pictRowIndexs       表格中的图片所在的位置
     * @param pictColIndexs       表格中的图片所在的位置
     * @param sheetIndex       表单索引
     * @param cellStyle       表单样式
     * @author wangzhiwen
     * @date 2019/8/7 14:08
     */
    private static <T> void createImgAndListExcelSheetNotStyle(List<T> list, String[] keys,
                                                              MultipartFile[] files, int headIndex, String dictItemCode, JSONObject modelValueJson, String titleIndex,
                                                                              int[] pictRowIndexs,int[] pictColIndexs,Workbook workbook,int sheetIndex,CellStyle cellStyle) {
        try {
            // 得到第一个表单
            Sheet writeSheet = workbook != null ? workbook.getSheetAt(sheetIndex) : null;
            writeSheet = setExcelTitle(titleIndex, workbook, writeSheet, modelValueJson);

            // 表格中的图片
                if (files != null) {
                    CreationHelper helper = workbook.getCreationHelper();
                    ClientAnchor anchor;
                    Drawing  drawing = writeSheet.createDrawingPatriarch();
                    if (files.length > 0) {
                        for (int i = 0; i < files.length; i++) {
                            anchor = helper.createClientAnchor();
                            anchor.setCol1(pictColIndexs[i]);
                            anchor.setRow1(pictRowIndexs[i]);
                            // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
                            byte[] data = files[i].getBytes();
                            int pictureIdx = workbook.addPicture(data, Workbook.PICTURE_TYPE_PNG);
                            Picture pict = drawing.createPicture(anchor, pictureIdx);
                            pict.resize();
                        }
                    }
                }

            if (StringUtils.isNotEmpty(dictItemCode)) {
                // 判断是否有非列表信息   填充非列表信息
                setNotListByIndex(writeSheet, dictItemCode, modelValueJson, cellStyle);
            }

            if (keys != null) {
                // 填充列表信息对象
                setListByList(writeSheet, list, keys, headIndex, cellStyle);
            }
        } catch (Exception e) {
            SysLog.error(e);
        }
    }

    /**
     * 功能描述:生成excel表格
     *
     * @param resp HttpServletResponse
     * @param list        数据列表
     * @param keys        与列表的实体类属性对应
     * @param filePath       下载的文件名
     * @param headIndex      标题行号
     * @param dictItemCode   非列表信息数据词典模版配置key
     * @param modelValueJson 非列表信息对象json
     * @param titleIndex     标题所在位置
     * @param files     表格中的图片
     * @param pictRowIndexs       表格中的图片所在的位置
     * @param pictRowIndexs       表格中的图片所在的位置
     * @author wangzhiwen
     * @date 2019/8/7 14:08
     */
    public static <T> void createAndDownloadImgAndListExcelSingleSheetNotStyle(HttpServletResponse resp, List<T> list, String[] keys,
                                                                    String filePath, int headIndex, String dictItemCode, JSONObject modelValueJson, String titleIndex,
                                                                              MultipartFile[] files, int[] pictRowIndexs,int[] pictColIndexs) {
        try {
            //得到文件类型
            String fileType = filePath.substring(filePath.lastIndexOf("."));
            Workbook workbook = getWorkbook(filePath, fileType);
            CellStyle cellStyle = workbook.createCellStyle();
            createImgAndListExcelSheetNotStyle(list, keys, files, headIndex, dictItemCode, modelValueJson, titleIndex, pictRowIndexs,pictColIndexs,workbook,0,cellStyle);
            downloadFile(resp, modelValueJson, fileType, workbook);
        } catch (Exception e) {
            SysLog.error(e);
        }
    }

    /**
     * 功能描述:生成excel表格
     *
     * @param resp HttpServletResponse
     * @param list           数据列表
     * @param keys           {"orderId","orderTime","productType","productName","chargingPriceDesc"} 与列表的实体类属性对应
     * @param filePath       下载的文件名
     * @param headIndex      标题行号
     * @param dictItemCode   非列表信息数据词典模版配置key
     * @param modelValueJson 非列表信息对象json
     * @param titleIndex     标题所在位置
     * @author wangzhiwen
     * @date 2019/8/7 14:08
     */
    public static <T> void createAndDownloadExcelLoc(HttpServletResponse resp, List<T> list, String[] keys,
                             String filePath, String fileUrl, Integer headIndex, String dictItemCode, JSONObject modelValueJson, String titleIndex) {
        Workbook writeWB;
        InputStream in = null;
        OutputStream os = null;
        try {
            String fileType = filePath.substring(filePath.lastIndexOf("."));
            String path = System.getProperty("user.dir");
            File file = new File(path + filePath);
            in = new FileInputStream(file);
            if (".xls".equals(fileType)) {
                writeWB = new HSSFWorkbook(in);
            } else if (".xlsx".equals(fileType)) {
                writeWB = new XSSFWorkbook(in);
            } else {
                return;
            }
            //得到第一个表单
            Sheet writeSheet = writeWB.getSheetAt(0);
            //判断是否有标题信息
            if (StringUtils.isNotEmpty(titleIndex)) {
                // 生成一个样式
                CellStyle style = writeWB.createCellStyle();
                // 设置这些样式
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
                Font font = writeWB.createFont();
                font.setFontHeightInPoints((short) 14);
                //font.setColor(Color.RED.index);
                font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                font.setFontName("黑体");
                // 把字体 应用到当前样式
                style.setFont(font);

                String[] rowCell = titleIndex.split(":");
                getCell(getRow(writeSheet, Integer.parseInt(rowCell[0])), Integer.parseInt(rowCell[1]), style).
                        setCellValue(modelValueJson.getString("title"));//设置每一列的值
            }
            CellStyle cellStyle = getCellStyle(writeWB);
            //判断是否有非列表信息
            if (StringUtils.isNotEmpty(dictItemCode)) {
                setNotListByIndex(writeSheet, dictItemCode, modelValueJson, cellStyle);//填充非列表信息
            }

            if (keys != null) {//填充列表信息对象
                setListByList(writeSheet, list, keys, headIndex, cellStyle);
            }
            //resp.setContentType("application/msexcel");//设置生成的文件类型
            //resp.setCharacterEncoding("UTF-8");//设置文件头编码方式和文件名
            //resp.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(modelValueJson.getString("title") + fileType, "UTF-8"));

            os = new FileOutputStream(fileUrl + modelValueJson.getString("titleName") + fileType);
            writeWB.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            SysLog.error(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * 功能描述:一个表格中多个Sheet
     *
     * @param resp           HttpServletResponse
     * @param map            数据列表
     * @param keysMap        {"orderId","orderTime","productType","productName","chargingPriceDesc"} 与列表的实体类属性对应
     * @param filePath       下载的文件名
     * @param fileUrl        文件名URL
     * @param headIndex      标题行号
     * @param dictItemCode   非列表信息数据词典模版配置key
     * @param modelValueJson 非列表信息对象json
     * @param titleIndex     标题所在位置
     * @author huaggc
     * @date 2021/01/13
     */
    public static <T> void createAndDownloadExcelLocSheets(HttpServletResponse resp, Map<String, T> map, Map<String, String[]> keysMap,
                               String filePath, String fileUrl, Integer headIndex, String dictItemCode, JSONObject modelValueJson, String titleIndex) {

        Workbook writeWB;
        InputStream in = null;
        OutputStream os = null;
        try {
            // 得到文件类型(后缀名)
            String fileType = filePath.substring(filePath.lastIndexOf("."));
            writeWB = getWorkbook(filePath, fileType);

            for (Map.Entry<String, T> entry : map.entrySet()) {
                String dataKey = entry.getKey().toString();
                int index = Integer.parseInt(dataKey);
                List<T> list = (List<T>) entry.getValue();

                // 得到某个表单 从0开始
                Sheet writeSheet = writeWB.getSheetAt(index);
                String[] rowCell = titleIndex.split(":");
                writeSheet = setExcelTitle(titleIndex, writeWB, writeSheet, modelValueJson);

                CellStyle cellStyle = getCellStyle(writeWB);
                if (StringUtils.isNotEmpty(dictItemCode)) {
                    // 判断是否有非列表信息  填充非列表信息
                    setNotListByIndex(writeSheet, dictItemCode, modelValueJson, cellStyle);
                }

                String[] keys = keysMap.get(dataKey);
                if (keys != null) {
                    // 填充列表信息对象
                    setListByList(writeSheet, list, keys, headIndex, cellStyle);
                }
            }

            downloadFile(resp, modelValueJson, fileType, writeWB);
        } catch (Exception e) {
            SysLog.error(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * 功能描述: 生成excel表格
     *
     * @param resp HttpServletResponse
     * @param list           数据列表
     * @param keysList       keys {"orderId","orderTime","productType","productName","chargingPriceDesc"} 与列表的实体类属性对应
     * @param filePath       下载的文件名
     * @param headIndexArray 标题行号
     * @param dictItemCode   非列表信息数据词典模版配置key
     * @param modelValueJson 非列表信息对象json
     * @param titleIndex     标题所在位置
     * @param mergeList      要合并的例
     * @author huanggc
     * @date 2020-04-30
     */
    public static <T> void createAndDownloadExcels(HttpServletResponse resp, List<List<T>> list, List<String[]> keysList, String filePath, String fileUrl,
                                                   Integer[] headIndexArray, String dictItemCode, JSONObject modelValueJson, String titleIndex, List<Integer[]> mergeList) {
        Workbook writeWB;
        File file;
        InputStream in = null;
        OutputStream os = null;
        try {
            String fileType = filePath.substring(filePath.lastIndexOf("."));
            String path = System.getProperty("user.dir");
            file = new File(path + filePath);
            in = new FileInputStream(file);
            // 判断文件类型
            if (".xls".equals(fileType)) {
                writeWB = new HSSFWorkbook(in);
            } else if (".xlsx".equals(fileType)) {
                writeWB = new XSSFWorkbook(in);
            } else {
                return;
            }
            // 得到第一个表单
            Sheet writeSheet = writeWB.getSheetAt(0);
            // 判断是否有标题信息
            if (StringUtils.isNotEmpty(titleIndex)) {
                // 生成一个样式
                CellStyle style = writeWB.createCellStyle();
                // 设置这些样式
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
                Font font = writeWB.createFont();
                font.setFontHeightInPoints((short) 14);
                //font.setColor(Color.RED.index);
                font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                font.setFontName("黑体");
                // 把字体 应用到当前样式
                style.setFont(font);

                String[] rowCell = titleIndex.split(":");
                getCell(getRow(writeSheet, Integer.parseInt(rowCell[0])), Integer.parseInt(rowCell[1]), style).
                        setCellValue(modelValueJson.getString("title"));//设置每一列的值
            }
            CellStyle cellStyle = getCellStyle(writeWB);
            if (StringUtils.isNotEmpty(dictItemCode)) {//判断是否有非列表信息
                setNotListByIndex(writeSheet, dictItemCode, modelValueJson, cellStyle);//填充非列表信息
            }

            if (CollectionUtils.isNotEmpty(keysList)) {//填充列表信息对象
                setListByLists(writeSheet, list, keysList, headIndexArray, cellStyle, mergeList);
            }


            if (StringUtils.isEmpty(fileUrl)) {
                resp.setContentType("application/msexcel");//设置生成的文件类型
                resp.setCharacterEncoding("UTF-8");//设置文件头编码方式和文件名
                resp.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(modelValueJson.getString("title") + fileType, "UTF-8"));

                os = resp.getOutputStream();
            } else {
                os = new FileOutputStream(fileUrl + modelValueJson.getString("titleName") + fileType);
            }

            writeWB.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            SysLog.error(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    /**
     * 功能描述: 生成excel表格
     *
     * @param resp HttpServletResponse
     * @param list           数据列表
     * @param keysList       keys {"orderId","orderTime","productType","productName","chargingPriceDesc"} 与列表的实体类属性对应
     * @param filePath       下载的文件名
     * @param headIndexArray 标题行号
     * @param dictItemCode   非列表信息数据词典模版配置key
     * @param modelValueJson 非列表信息对象json
     * @param titleIndex     标题所在位置
     * @param mergeList      要合并的例
     * @author huanggc
     * @date 2020-06-30
     */
    public static <T> void createAndDownloadExcelMerger(HttpServletResponse resp, List<List<T>> list, List<String[]> keysList, String filePath, String fileUrl,
                                                        Integer[] headIndexArray, String dictItemCode, JSONObject modelValueJson, String titleIndex, List<Integer[]> mergeList) {

        Workbook workbook;
        OutputStream os = null;
        InputStream in = null;
        try {
            // 得到文件类型
            String fileType = filePath.substring(filePath.lastIndexOf("."));
            String path = System.getProperty("user.dir");
            File file = new File(path + filePath);
            String fileName = StringUtils.urlEncode(file.getName());
            in = new FileInputStream(file);
            if (".xls".equals(fileType)) {//判断文件类型
                workbook = new HSSFWorkbook(in);
            } else if (".xlsx".equals(fileType)) {
                workbook = new XSSFWorkbook(in);
            } else {
                return;
            }

            Sheet writeSheet = workbook.getSheetAt(0);//得到第一个表单
            if (StringUtils.isNotEmpty(titleIndex)) {//判断是否有标题信息
                // 生成一个样式
                CellStyle style = workbook.createCellStyle();
                // 设置这些样式
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
                style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
                Font font = workbook.createFont();
                font.setFontHeightInPoints((short) 14);
                //font.setColor(Color.RED.index);
                font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                font.setFontName("黑体");
                // 把字体 应用到当前样式
                style.setFont(font);

                String[] rowCell = titleIndex.split(":");
                getCell(getRow(writeSheet, Integer.parseInt(rowCell[0])), Integer.parseInt(rowCell[1]), style).setCellValue(modelValueJson.getString("title"));
            }
            CellStyle cellStyle = getCellStyle(workbook);
            if (StringUtils.isNotEmpty(dictItemCode)) {//判断是否有非列表信息
                setNotListByIndex(writeSheet, dictItemCode, modelValueJson, cellStyle);//填充非列表信息
            }

            if (CollectionUtils.isNotEmpty(keysList)) {// 填充列表信息对象
                setListByListMerger(writeSheet, list, keysList, headIndexArray, cellStyle, mergeList);
            }


            if (StringUtils.isEmpty(fileUrl)) {
                resp.setContentType("application/msexcel");//设置生成的文件类型
                resp.setCharacterEncoding("UTF-8");//设置文件头编码方式和文件名
                resp.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(modelValueJson.getString("title") + fileType, "UTF-8"));

                os = resp.getOutputStream();
            } else {
                os = new FileOutputStream(fileUrl + modelValueJson.getString("titleName") + fileType);
            }

            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            SysLog.error(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception ex) {
            }
        }
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
     * @param index int
     * @return org.apache.poi.ss.usermodel.Row
     * @author wangzhiwen
     * @date 2019/9/10 17:32
     */
    private static Row getRow(Sheet sheet, int index) {
        Row row = sheet.getRow(index);//设置每一行的值
        if (null == row) {
            row = sheet.createRow(index);
        }
        return row;
    }

    /**
     * 功能描述:合建单元格样式
     *
     * @param workbook Workbook
     * @return org.apache.poi.ss.usermodel.CellStyle
     * @author wangzhiwen
     * @date 2019/9/11 16:01
     */
    private static CellStyle getCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        return cellStyle;
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
     * @param writeSheet
     * @param list
     * @param keys
     * @param headIndex
     * @author wangzhiwen
     * @date 2019/9/10 11:10
     */
    private static <T> void setListByList(Sheet writeSheet, List<T> list, String[] keys, int headIndex) {
        String key;
        Row writeRow;
        T t;
        try {
            int keysSize = keys.length;
            for (int i = 0, l = list.size(); i < l; i++) {//写内容
                writeRow = writeSheet.getRow(headIndex + i);//设置每一行的值
                if (null == writeRow) {
                    writeRow = writeSheet.createRow(headIndex + i);//设置每一行的值
                }
                for (int k = 0, n = keysSize; k < n; k++) {
                    if (writeRow.getCell(k) == null) {
                        writeRow.createCell(k);
                    }
                }
            }
            int cellIndex = 0;
            JSONObject jsonObject;
            for (int i = 0; i < list.size(); i++) {//写内容
                t = list.get(i);
                jsonObject = (JSONObject) JSONObject.toJSON(t);
                writeRow = writeSheet.getRow(headIndex + i);//设置每一行的值
                key = keys[0];//设置第一列表的值
                //判断第一列是否是序号列
                if ("ROWNUM".equals(key.toUpperCase())) {
                    //设置每一列的值
                    writeRow.getCell(0).setCellValue(i + 1);
                    cellIndex = 1;
                }
                for (int k = cellIndex; k < keysSize; k++) {
                    //设置每一列的值
                    writeRow.getCell(k).setCellValue(jsonObject.getString(keys[k]));
                }
            }
        } catch (Exception ex) {
            SysLog.error(ex);
        }
    }

    /**
     * 功能描述:填充列表的值
     *
     * @param sheet Sheet
     * @param list 数据
     * @param keysList 列名
     * @param headIndexArray headIndexArray
     * @param cellStyle CellStyle样式
     * @param mergeList mergeList合并的列
     * @author huanggc
     * @date 2020-04-30
     */
    private static <T> void setListByLists(Sheet sheet, List<List<T>> list, List<String[]> keysList, Integer[] headIndexArray, CellStyle cellStyle, List<Integer[]> mergeList) {
        // 列字段(默认先取第一个list的)
        List<String> keyList = getMethodNames(keysList.get(0));

        String key;
        Row row;
        Cell cell;
        T t;
        try {
            int size = list.size();
            int mergeSize = mergeList.size();
            for (int x = 0; x < size; x++) {// 多个list
                List<T> tT = list.get(x);

                int headIndex = headIndexArray[x];// 从第几行开始写
                for (int i = 0, l = tT.size(); i < l; i++) {// 写内容
                    t = tT.get(i);
                    sheet.shiftRows(headIndex + i, sheet.getLastRowNum(), 1);// 10-最后一行，向下移动一行
                    row = sheet.createRow(headIndex + i);// 设置每一行的值

                    for (int k = 0, n = keyList.size(); k < n; k++) {
                        key = keyList.get(k);
                        cell = row.createCell(k);
                        cell.setCellStyle(cellStyle);
                        if (k == 0 && "GETROWNUM".equals(key.toUpperCase())) {
                            // 判断第一列是否是序号列
                            cell.setCellValue(i + 1);// 设置每一列的值
                        } else {
                            Method m = t.getClass().getMethod(key);
                            Object val = m.invoke(t);// 调用getter方法获取属性值
                            cell.setCellValue(val == null ? "/" : val.toString());// 设置每一列的值

                            if (0 != x && k < mergeSize + 1) {
                                // 第二个list有要合并的例
                                Integer[] integers = mergeList.get(k - 1);
                                Integer a = integers[0];
                                Integer b = integers[1];
                                setHeaderStyle(cell, cellStyle, row, val == null ? "/" : val.toString(), a, b);
                            }
                        }
                    }
                }

                if (x < size - 1) {
                    keyList = getMethodNames(keysList.get(x + 1));// 每个list对应的表头/列字段
                }
            }

            List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();
            CellRangeAddress cra1 = null;
            for (int x = 1; x < size; x++) {// 多少个list; 第一个list没有合并的
                List<T> tT = list.get(x);
                Integer integer = headIndexArray[x];
                for (int i = 0; i < tT.size(); i++) {
                    for (int j = 0; j < mergeList.size(); j++) {
                        Integer[] integers = mergeList.get(j);
                        Integer a = integers[0];
                        Integer b = integers[1];
                        // 合并日期占两行(4个参数，分别为起始行，结束行，起始列，结束列)
                        // 行和列都是从0开始计数，且起始结束都会合并
                        cra1 = new CellRangeAddress(integer, integer, a, b);
                        cellRangeAddressList.add(cra1);
                    }
                    integer++;
                }
            }

            // 合并
            for (CellRangeAddress car : cellRangeAddressList) {
                sheet.addMergedRegion(car);
            }
        } catch (Exception ex) {
            SysLog.error(ex);
        }
    }

    /**
     * 功能描述:填充列表的值
     *
     * @param sheet Sheet
     * @param list 数据
     * @param keysList 列名
     * @param headIndexArray
     * @param cellStyle
     * @param mergeList
     * @author huanggc
     * @date 2020-06-30
     */
    private static <T> void setListByListMerger(Sheet sheet, List<List<T>> list, List<String[]> keysList, Integer[] headIndexArray, CellStyle cellStyle,
                                                List<Integer[]> mergeList) {

        String key;
        Row row;
        Cell cell;
        T t;
        try {
            int size = list.size();
            //int mergeSize = mergeList.size();
            // 多个list
            for (int x = 0; x < size; x++) {
                // 列字段
                List<String> keyList = getMethodNames(keysList.get(x));

                List<T> tT = list.get(x);
                // 从第几行开始写
                int headIndex = headIndexArray[x];

                String keyValue = "";// 用于记录行的第2例值
                int rowNum = 1;
                int rowNums = 0;
                for (int i = 0, l = tT.size(); i < l; i++) {// 写内容
                    t = tT.get(i);
                    sheet.shiftRows(headIndex + i, sheet.getLastRowNum(), 1);// 10-最后一行，向下移动一行
                    row = sheet.createRow(headIndex + i);// 设置每一行的值

                    if (rowNums == 3) {
                        rowNums = 0;
                        rowNum++;
                    }
                    rowNums++;

                    for (int k = 0, n = keyList.size(); k < n; k++) {
                        key = keyList.get(k);
                        cell = row.createCell(k);
                        cell.setCellStyle(cellStyle);

                        if (k == 0 && "GETROWNUM".equals(key.toUpperCase())) {// 判断第一列是否是序号列
                            cell.setCellValue(rowNum);// 设置每一列的值
                        } else {
                            Method m = t.getClass().getMethod(key);
                            Object val = m.invoke(t);// 调用getter方法获取属性值
                            cell.setCellValue(val == null ? "/" : val.toString());// 设置每一列的值

                            /*if (0 != x && k < mergeSize + 1){// 第二个list有要合并的例
                                Integer[] integers = mergeList.get(k - 1);
                                Integer a = integers[0];
                                Integer b = integers[1];
                                setHeaderStyle(cell, cellStyle, row, val == null ? "/" : val.toString(), a, b);
                            }*/
                        }
                    }
                }
            }

            List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();
            for (int x = 0; x < size; x++) {// 多个list
                List<T> tT = list.get(x);
                Integer integer = headIndexArray[x];

                /*for (int i = 0; i < tT.size(); i++) {
                    for (int j = 0; j < mergeList.size(); j++) {
                        Integer[] integers = mergeList.get(j);
                        Integer a = integers[0];
                        Integer b = integers[1];
                        // 合并日期占两行(4个参数，分别为起始行，结束行，起始列，结束列)
                        // 行和列都是从0开始计数，且起始结束都会合并
                        cra1 = new CellRangeAddress(integer, integer, a, b);
                        cellRangeAddressList.add(cra1);
                    }
                    integer++;
                }*/

                if (CollectionUtils.isNotEmpty(tT)) {
                    int size1 = tT.size() / 3;
                    for (int i = 0; i < size1; i++) {
                        CellRangeAddress cra0 = new CellRangeAddress(integer, integer + 2, 0, 0);
                        CellRangeAddress cra1 = new CellRangeAddress(integer, integer + 2, 1, 1);
                        CellRangeAddress cra2 = new CellRangeAddress(integer, integer + 2, 2, 2);
                        CellRangeAddress cra3 = new CellRangeAddress(integer, integer + 2, 3, 3);
                        CellRangeAddress cra9 = new CellRangeAddress(integer, integer + 2, 9, 9);
                        CellRangeAddress cra10 = new CellRangeAddress(integer, integer + 2, 10, 10);
                        CellRangeAddress cra11 = new CellRangeAddress(integer, integer + 2, 11, 11);
                        CellRangeAddress cra12 = new CellRangeAddress(integer, integer + 2, 12, 12);

                        cellRangeAddressList.add(cra0);
                        cellRangeAddressList.add(cra1);
                        cellRangeAddressList.add(cra2);
                        cellRangeAddressList.add(cra3);
                        cellRangeAddressList.add(cra9);
                        cellRangeAddressList.add(cra10);
                        cellRangeAddressList.add(cra11);
                        cellRangeAddressList.add(cra12);

                        integer += 3;
                    }
                }
            }

            // 合并
            for (CellRangeAddress car : cellRangeAddressList) {
                sheet.addMergedRegion(car);
            }
        } catch (Exception ex) {
            SysLog.error(ex);
        }
    }

    /**
     * @param cell Cell
     * @param headerStyle CellStyle
     * @param row Row
     * @param tableName 表名
     * @param start 第几例开始
     * @param end 第几例结束
     */
    private static void setHeaderStyle(Cell cell, CellStyle headerStyle, Row row, String tableName, Integer start, Integer end) {
        for (int j = start; j <= end; j++) {
            cell = row.createCell(j);
            // style为带边框的样式 上面有定义
            cell.setCellStyle(headerStyle); 
            if (StringUtils.isNotEmpty(tableName)) {
                cell.setCellValue(tableName);
            } else {
                cell.setCellValue("/");
            }
        }
    }

    /**
     * @param wb HSSFWorkbook
     * @return HSSFCellStyle
     */
    public static HSSFCellStyle getHeaderStyle(HSSFWorkbook wb) {
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
     * 功能描述: 填充非列表信息
     *
     * @param writeSheet Sheet
     * @param dictItemCode dictItemCode
     * @param modelValueJson JSONObject
     * @param cellStyle CellStyle样式
     * @author wangzhiwen
     * @date 2019/9/10 11:06
     */
    private static void setNotListByIndex(Sheet writeSheet, String dictItemCode, JSONObject modelValueJson, CellStyle cellStyle) {
        Row writeRow = null;
        int rowIndex;
        int cellIndex = 0;
        //得到模版配置数据词典对象
        Dict dict = DictUtils.getDictByDictAndItemCode(REPORT_MODEL_CONFIG, dictItemCode);
        //得到要取值的key
        String[] keys = dict != null ? dict.getMemo().split(",") : new String[0];
        //得到与key对应的表格坐标
        JSONObject json = JSONObject.parseObject(dict != null ? dict.getItemDescription() : null);
        JSONArray jsonArray = json.getJSONArray("cell");//得到列的坐标数组
        //把列的坐标数组转化成list
        List<Integer> cellList = JSONObject.parseArray(jsonArray.toString(), Integer.class);
        String value;

        //判断报表模版是否有一定格式
        if ("true".equals(dict != null ? dict.getItemValue() : null)) {
            rowIndex = json.getInteger("row");
            writeRow = getRow(writeSheet, rowIndex);//设置每一行的值

            for (String key : keys) {
                value = modelValueJson.getString(key);
                value = StringUtils.isEmpty(value) ? "/" : value;
                getCell(writeRow, cellList.get(cellIndex), cellStyle).setCellValue(value);//设置每一列的值
                if (cellIndex == cellList.size() - 1) {//判断列表的坐标数组是否取完，需要换到下一行
                    cellIndex = 0;//列的坐标重新取值
                    rowIndex++;//下一行坐标
                    writeRow = getRow(writeSheet, rowIndex);//得到下一行
                } else {
                    cellIndex++;
                }
            }
        } else {
            //非一定格式的，坐标数组为一一对应关系
            //得到行的坐标数组
            jsonArray = json.getJSONArray("row");
            List<Integer> rowList = JSONObject.parseArray(jsonArray.toString(), Integer.class);
            for (int i = 0; i < keys.length; i++) {
                rowIndex = rowList.get(i);
                cellIndex = cellList.get(i);
                //判断是否是第一行，或取得的行坐标与上一行不同，则重新获取一行
                if (i == 0 || (rowIndex != rowList.get(i - 1))) {
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
     * 功能描述:模板下载
     *
     * @param resp HttpServletResponse
     * @author dwt
     * @date 2019/8/7 14:08
     */
    public static <T> void DownloadExcelModel(HttpServletResponse resp, String filePath, JSONObject modelValueJson) {
        Workbook workbook;
        InputStream in = null;
        OutputStream os = null;
        try {
            // 得到文件类型
            String fileType = filePath.substring(filePath.lastIndexOf("."));
            String path = System.getProperty("user.dir");
            File file = new File(path + filePath);
            in = new FileInputStream(file);
            if (".xls".equals(fileType)) {
                workbook = new HSSFWorkbook(in);
            } else if (".xlsx".equals(fileType)) {
                workbook = new XSSFWorkbook(in);
            } else {
                return;
            }
            //设置生成的文件类型
            resp.setContentType("application/msexcel");
            //设置文件头编码方式和文件名
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(modelValueJson.getString("title") + fileType, "UTF-8"));
            os = resp.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            SysLog.error(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    /**
     *  返回Excel文件
     *
     * @author dwt
     * @date 2020-07-20 16:40
     * @return File
     */
    public static <T> File getExcelFile(List<T> list, String[] keys, String filePath, Integer headIndex, String dictItemCode, JSONObject modelValueJson, String titleIndex) {
        Workbook writeWB;
        // 得到文件类型
        String fileType = filePath.substring(filePath.lastIndexOf("."));
        String path = System.getProperty("user.dir");
        File file = new File(path + filePath);
        File temp;
        String title = modelValueJson.getString("title");
        FileInputStream fin = null;
        FileOutputStream fout = null;
        InputStream in = null;
        FileOutputStream out = null;
        try {
            temp = File.createTempFile(title, ".xlsx");
            fin = new FileInputStream(file);
            fout = new FileOutputStream(temp);
            byte[] buffer = new byte[2097152];
            int readByte = 0;
            while ((readByte = fin.read(buffer)) != -1) {
                fout.write(buffer, 0, readByte);
            }
            fin.close();
            fout.close();
            file = temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // String fileName = StringUtils.urlEncode(file.getName());
            in = new FileInputStream(file);
            // 判断文件类型
            if (".xls".equals(fileType)) {
                writeWB = new HSSFWorkbook(in);
            } else if (".xlsx".equals(fileType)) {
                writeWB = new XSSFWorkbook(in);
            } else {
                return null;
            }
            // 得到第一个表单
            Sheet writeSheet = writeWB.getSheetAt(0);

            writeSheet = setExcelTitle(titleIndex, writeWB, writeSheet, modelValueJson);

            CellStyle cellStyle = getCellStyle(writeWB);
            // 判断是否有非列表信息
            if (StringUtils.isNotEmpty(dictItemCode)) {
                // 填充非列表信息
                setNotListByIndex(writeSheet, dictItemCode, modelValueJson, cellStyle);
            }
            //填充列表信息对象
            if (keys != null) {
                setListByList(writeSheet, list, keys, headIndex, cellStyle);
            }
            out = new FileOutputStream(file.getPath());
            writeWB.write(out);
            out.close();
        } catch (Exception e) {
            SysLog.error(e);
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
                if (fout != null) {
                    fout.close();
                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception ex) {
            }
        }
        return file;
    }

}
