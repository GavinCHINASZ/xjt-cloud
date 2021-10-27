package com.xjt.cloud.iot.core.service.impl.fire;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.Dict;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.dao.iot.fire.FireAlarmEventDao;
import com.xjt.cloud.iot.core.dao.iot.vesa.VesaDeviceRecordDao;
import com.xjt.cloud.iot.core.entity.fire.FireAlarmEvent;
import com.xjt.cloud.iot.core.entity.vesa.VesaRecord;
import com.xjt.cloud.iot.core.service.service.fire.FireAlarmEventIoService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * 快照报表导出表格
 *
 * @author huanggc
 * @date 2020/07/10
 */
@Service
public class FireAlarmEventIoServiceImpl extends AbstractService implements FireAlarmEventIoService {
    @Autowired
    private VesaDeviceRecordDao vesaDeviceRecordDao;
    @Autowired
    private FireAlarmEventDao fireAlarmEventDao;

    private static String PICTURE_URL;

    @Override
    public void findFireAlarmIo(String json, HttpServletRequest request, HttpServletResponse response) {
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);
        VesaRecord vesaRecord = JSONObject.parseObject(json, VesaRecord.class);

        PICTURE_URL = fireAlarmEvent.getPictureUrl();

        Date startDate = fireAlarmEvent.getStartTime();
        Calendar c = Calendar.getInstance();
        Date endTime;
        Date endTimes;
        Date startTime;
        if(startDate != null){
            startTime = startDate;
            c.setTime(startDate);
            c.set(Calendar.HOUR_OF_DAY,23);
            c.set(Calendar.MINUTE,59);
            c.set(Calendar.SECOND,59);
            endTime = c.getTime();
            // 下午5点
            c.set(Calendar.HOUR_OF_DAY, 17);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            endTimes = c.getTime();
        }else{
            c.set(Calendar.HOUR_OF_DAY,0);
            c.set(Calendar.MINUTE,0);
            c.set(Calendar.SECOND,0);
            startTime = c.getTime();
            c.set(Calendar.HOUR_OF_DAY,18);
            endTime = c.getTime();
            endTimes = c.getTime();
        }

        fireAlarmEvent.setEndTime(endTime);
        fireAlarmEvent.setStartTime(startTime);
        fireAlarmEvent.setRecoverStatus(2);
        int[] eventTypeArr2 = {1, 2, 7};
        fireAlarmEvent.setEventTypeArr(eventTypeArr2);
        List<FireAlarmEvent> fList = fireAlarmEventDao.findFireAlarmEventLists(fireAlarmEvent);

        vesaRecord.setStartTime(startTime);
        vesaRecord.setEndTime(endTimes);
        vesaRecord.setRecoverStatus(0);
        Integer[] eventTypeArr = {0, 1, 2, 3, 4};
        vesaRecord.setEventTypeArr(eventTypeArr);
        List<VesaRecord> vList = vesaDeviceRecordDao.findVesaDeviceEventLists(vesaRecord);

        JSONObject jsonObject = JSONObject.parseObject(json);
        Long projectId = fireAlarmEvent.getProjectId();
        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        // 表格名称
        jsonObject.put("title", projectName + "--快照列表导出");

        String[] keyArray = {"eventTypeDesc", "dateNum", "createTimeDesc", "unit", "rowNum", "alarmPositionDesc", "monitorTypeDesc",
                "completeTimeDesc", "delayTimeDesc", "recoverTimeDesc", "remarks"};

        Integer headindex = 41;
        Integer rowNum = 0;
        Integer[] headindexArray = new Integer[2];
        headindexArray[0] = headindex;
        headindexArray[1] = headindex + fList.size();

        // 表格模板路径
        String filePath = ConstantsIot.PC_SNAPSHOT_REPORT_EXCEL_PATH;

        List<CellRangeAddress> cellRangeAddressList = new ArrayList<CellRangeAddress>();
        // 合并日期占两行(4个参数，分别为起始行，结束行，起始列，结束列)
        // 行和列都是从 0 开始计数，且起始结束都会合并

        if (CollectionUtils.isNotEmpty(fList)){
            int row = 1;// 序号
            String eventTypeDesc = fList.get(0).getEventTypeDesc();
            for (int i = 0; i < fList.size(); i++) {
                FireAlarmEvent entity = fList.get(i);
                if (eventTypeDesc.equals(entity.getEventTypeDesc())){
                    entity.setRowNum(row);
                    row++;
                    eventTypeDesc = entity.getEventTypeDesc();
                }else{
                    row = 1;
                    entity.setRowNum(row);
                    row++;
                    eventTypeDesc = entity.getEventTypeDesc();
                }
            }

            // 遍历,用于算出需要合并的表格数据
            String eventTypeMemo = fList.get(0).getEventTypeDesc();
            for (int i = 0; i < fList.size(); ) {
                FireAlarmEvent entity = fList.get(i);
                Integer dateNum = entity.getDateNum();
                if (eventTypeMemo.equals(entity.getEventTypeDesc())){
                    CellRangeAddress cra1 = new CellRangeAddress(headindex, headindex + dateNum - 1, 0, 0);
                    CellRangeAddress cra2 = new CellRangeAddress(headindex, headindex + dateNum - 1, 1, 1);
                    cellRangeAddressList.add(cra1);
                    cellRangeAddressList.add(cra2);
                    headindex += dateNum;

                    rowNum += dateNum;
                    i += dateNum;
                    if (i == fList.size()){
                        eventTypeMemo = fList.get(i - 1).getEventTypeDesc();
                    }else {
                        eventTypeMemo = fList.get(i).getEventTypeDesc();
                    }
                }
            }
        }

        if (CollectionUtils.isNotEmpty(vList)){
            int row = 1;
            String eventTypeDesc = vList.get(0).getEventTypeDesc();

            for (int i = 0; i < vList.size(); i++) {
                VesaRecord entity = vList.get(i);
                if (eventTypeDesc.equals(entity.getEventTypeDesc())){
                    entity.setRowNum(row);
                    row++;
                    eventTypeDesc = entity.getEventTypeDesc();
                }else{
                    row = 1;
                    entity.setRowNum(row);
                    row++;
                    eventTypeDesc = entity.getEventTypeDesc();
                }
            }

            // 遍历,用于算出需要合并的表格数据
            String eventTypeMemo = vList.get(0).getEventTypeDesc();
            for (int i = 0; i < vList.size(); ) {
                VesaRecord entity = vList.get(i);
                Integer dateNum = entity.getDateNum();
                if (eventTypeMemo.equals(entity.getEventTypeDesc())){
                    if (headindex != (headindex + dateNum - 1)){
                        CellRangeAddress cra1 = new CellRangeAddress(headindex, headindex + dateNum - 1, 0, 0);
                        CellRangeAddress cra2 = new CellRangeAddress(headindex, headindex + dateNum - 1, 1, 1);
                        cellRangeAddressList.add(cra1);
                        cellRangeAddressList.add(cra2);
                    }
                    headindex += dateNum;
                    rowNum += dateNum;
                    i += dateNum;
                    if (i == vList.size()){
                        eventTypeMemo = vList.get(i - 1).getEventTypeDesc();
                    }else {
                        eventTypeMemo = vList.get(i).getEventTypeDesc();
                    }
                }
            }
        }
        createAndDownloadExcel(response, fList, vList, keyArray, filePath, headindexArray, null, jsonObject, "1:0", cellRangeAddressList);
    }

    //报表模版配置key
    private static String REPORT_MODEL_CONFIG = "REPORT_MODEL_CONFIG";

    /**
     * 功能描述:生成excel表格
     *
     * @param resp HttpServletResponse
     * @param list 数据列表
     * @param lists List<VesaRecord>
     * @param keys {"orderId","orderTime","productType","productName","chargingPriceDesc"} 与列表的实体类属性对应
     * @param filePath 下载的文件名
     * @param headIndex 标题行号
     * @param dictItemCode 非列表信息数据词典模版配置key
     * @param modelValueJson 非列表信息对象json
     * @param titleIndex 标题所在位置
     * @author huanggc
     * @date 2019/8/7 14:08
     */
    public static <T> void createAndDownloadExcel(HttpServletResponse resp, List<FireAlarmEvent> list, List<VesaRecord> lists, String[] keys, String filePath,
                                  Integer[] headIndex, String dictItemCode, JSONObject modelValueJson, String titleIndex, List<CellRangeAddress> cellRangeAddressList) {
        try {
            String fileType = filePath.substring(filePath.lastIndexOf("."));
            String path = System.getProperty("user.dir");
            File file = new File(path + filePath);
            String fileName = StringUtils.urlEncode(file.getName());
            InputStream in = new FileInputStream(file);
            HSSFWorkbook writeWB;
            if (".xls".equals(fileType)) {
                writeWB = new HSSFWorkbook(in);
            } else {
                if (!".xlsx".equals(fileType)) {
                    return;
                }
                writeWB = new HSSFWorkbook(in);
            }

            HSSFSheet writeSheet = writeWB.getSheetAt(0);
            CellStyle cellStyle;
            if (StringUtils.isNotEmpty(titleIndex)) {
                cellStyle = ((Workbook)writeWB).createCellStyle();
                cellStyle.setAlignment((short)2);
                cellStyle.setVerticalAlignment((short)1);
                cellStyle.setBorderBottom((short)1);
                cellStyle.setBorderLeft((short)1);
                cellStyle.setBorderTop((short)1);
                cellStyle.setBorderRight((short)1);

                Font font = ((Workbook)writeWB).createFont();
                font.setFontHeightInPoints((short)10);
                font.setBoldweight((short)700);
                font.setFontName("宋体");
                cellStyle.setFont(font);
                String[] rowCell = titleIndex.split(":");
            }

            cellStyle = getCellStyle((Workbook)writeWB);
            if (StringUtils.isNotEmpty(dictItemCode)) {
                setNotListByIndex(writeSheet, dictItemCode, modelValueJson, cellStyle);
            }

            if (keys != null) {
                setListByList(writeSheet, writeWB, list, lists, keys, headIndex, cellStyle, cellRangeAddressList);
            }

            resp.setContentType("application/msexcel");
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(modelValueJson.getString("title") + fileType, "UTF-8"));
            OutputStream os = resp.getOutputStream();
            ((Workbook)writeWB).write(os);
            os.flush();
            os.close();
        } catch (Exception var18) {
            SysLog.error(var18);
        }
    }

    /**
     * 功能描述: 填充非列表信息
     *
     * @param writeSheet Sheet
     * @param dictItemCode String
     * @param modelValueJson JSONObject
     * @author huanggc
     * @date 2019/9/10 11:06
     */
    private static void setNotListByIndex(Sheet writeSheet, String dictItemCode, JSONObject modelValueJson, CellStyle cellStyle){
        Row writeRow = null;
        int rowIndex;
        int cellIndex = 0;
        Dict dict = DictUtils.getDictByDictAndItemCode(REPORT_MODEL_CONFIG, dictItemCode);// 得到模版配置数据词典对象
        String[] keys = dict.getMemo().split(",");// 得到要取值的key
        JSONObject json = JSONObject.parseObject(dict.getItemDescription());// 得到与key对应的表格坐标
        JSONArray jsonArray = json.getJSONArray("cell");// 得到列的坐标数组
        List<Integer> cellList = JSONObject.parseArray(jsonArray.toString(),Integer.class);// 把列的坐标数组转化成list
        String value;
        if ("true".equals(dict.getItemValue())){// 判断报表模版是否有一定格式
            rowIndex = json.getInteger("row");
            writeRow = getRow(writeSheet, rowIndex);// 设置每一行的值

            for (String key : keys){
                value = modelValueJson.getString(key);
                value = StringUtils.isEmpty(value) ? "/" : value;
                getCell(writeRow, cellList.get(cellIndex), cellStyle).setCellValue(value);// 设置每一列的值
                if (cellIndex == cellList.size() - 1 ){// 判断列表的坐标数组是否取完，需要换到下一行
                    cellIndex = 0;// 列的坐标重新取值
                    rowIndex++;// 下一行坐标
                    writeRow = getRow(writeSheet, rowIndex);// 得到下一行
                }else {
                    cellIndex++;
                }
            }
        }else {
            // 非一定格式的，坐标数组为一一对应关系
            //得到行的坐标数组
            jsonArray = json.getJSONArray("row");
            List<Integer> rowList = JSONObject.parseArray(jsonArray.toString(),Integer.class);
            for (int i = 0; i < keys.length; i++){
                rowIndex = rowList.get(i);
                cellIndex = cellList.get(i);
                if (i == 0 || (i > 0 && rowIndex != rowList.get(i - 1))){
                    // 判断是否是第一行，或取得的行坐标与上一行不同，则重新获取一行
                    // 设置每一行的值
                    writeRow = getRow(writeSheet, rowIndex);
                }
                value = modelValueJson.getString(keys[i]);
                value = StringUtils.isEmpty(value) ? "/" : value;
                // 设置每一列的值
                getCell(writeRow, cellIndex, cellStyle).setCellValue(value);
            }
        }
    }

    /**
     * 功能描述:填充列表的值
     *
     * @param writeSheet HSSFSheet
     * @param writeWB HSSFWorkbook
     * @param list
     * @param lists
     * @param keys
     * @param headIndex
     * @author wangzhiwen
     * @date 2019/9/10 11:10
     */
    private static <T> void setListByList(HSSFSheet writeSheet, HSSFWorkbook writeWB, List<FireAlarmEvent> list, List<VesaRecord> lists, String[] keys, Integer[] headIndex,
                                          CellStyle cellStyle, List<CellRangeAddress> cellRangeAddressList) throws Exception {

        if(StringUtils.isNotEmpty(PICTURE_URL)) {
            // 获取图上片的地址 "http://192.168.0.200:6020/qrcode/2020/07/17/1594956662114.jpg"
            URL url = new URL(PICTURE_URL);
            // 打开链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                // 设置请求方式为"GET"
                conn.setRequestMethod("GET");
                // 超时响应时间为3秒
                conn.setConnectTimeout(3 * 1000);
                // 通过输入流获取图片数据
                InputStream inStream = conn.getInputStream();
                // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
                byte[] data = readInputStream(inStream);
                // anchor主要用于设置图片的属性
                HSSFClientAnchor anchor = new HSSFClientAnchor(80, 80, 0, 0, (short) 0, 0, (short) 10, 39);
                // Sets the anchor type （图片在单元格的位置）
                // 0 = Move and size with Cells, 2 = Move but don't size with cells, 3 = Don't
                // move or size with cells.
                anchor.setAnchorType(0);
                // 向表格中插入图片
                HSSFPatriarch patriarch = writeSheet.createDrawingPatriarch();
                patriarch.createPicture(anchor, writeWB.addPicture(data, HSSFWorkbook.PICTURE_TYPE_JPEG));
            }
        }

        /*HSSFFont headFont = writeWB.createFont();// 表格字体
        short bold = 650;
        short fontHeight = 11;
        headFont.setBoldweight(bold);
        headFont.setFontHeightInPoints(fontHeight);
        cellStyle.setFont(headFont);*/

        List<String> keyList = getMethodNames(keys);
        String key;
        Row writeRow;
        Cell cell;
        T t;
        try {
            Integer h = headIndex[0];
            // 写内容
            for (int i = 0, l = list.size(); i < l; i++) {
                t = (T) list.get(i);
                //10-最后一行，向下移动一行
                writeSheet.shiftRows(h + i, writeSheet.getLastRowNum(), 1);
                writeRow = writeSheet.createRow(h + i);// 设置每一行的值
                for (int k = 0, n = keyList.size(); k < n; k++) {
                    key = keyList.get(k);
                    cell = writeRow.createCell(k);
                    cell.setCellStyle(cellStyle);
                    if (k == 0 && "ROWNUM".equals(key.toUpperCase())){// 判断第一列是否是序号列
                        cell.setCellValue(i + 1);// 设置每一列的值
                    }else{
                        Method m = t.getClass().getMethod("get" + key);
                        Object val = m.invoke(t);// 调用getter方法获取属性值
                        cell.setCellValue(val == null ? "/" : val.toString());// 设置每一列的值
                    }
                }
            }

            if (CollectionUtils.isNotEmpty(lists)){
                Integer he = headIndex[1];
                for (int i = 0, l = lists.size(); i < l; i++) {
                    // 写内容
                    t = (T) lists.get(i);
                    // 10-最后一行，向下移动一行
                    writeSheet.shiftRows(he + i, writeSheet.getLastRowNum(), 1);
                    // 设置每一行的值
                    writeRow = writeSheet.createRow(he + i);
                    for (int k = 0, n = keyList.size(); k < n; k++) {
                        key = keyList.get(k);
                        cell = writeRow.createCell(k);
                        cell.setCellStyle(cellStyle);
                        if (k == 0 && "ROWNUM".equals(key.toUpperCase())){
                            // 判断第一列是否是序号列
                            // 设置每一列的值
                            cell.setCellValue(i + 1);
                        }else{
                            Method m = t.getClass().getMethod("get" + key);
                            // 调用getter方法获取属性值
                            Object val = m.invoke(t);
                            // 设置每一列的值
                            cell.setCellValue(val == null ? "/" : val.toString());
                        }
                    }
                }
            }
            // 有需要合并的
            if (CollectionUtils.isNotEmpty(cellRangeAddressList)){
                for (CellRangeAddress car: cellRangeAddressList) {
                    writeSheet.addMergedRegion(car);
                }
            }
        }catch (Exception ex){
            SysLog.error(ex);
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
     * 功能描述:合建单元格样式
     *
     * @param workbook Workbook
     * @return org.apache.poi.ss.usermodel.CellStyle
     * @author wangzhiwen
     * @date 2019/9/11 16:01
     */
    private static CellStyle getCellStyle(Workbook workbook){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
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
     * 功能描述: 得到属性的方法名称
     *
     * @param keys String[]
     * @return java.util.List<java.lang.String>
     * @author wangzhiwen
     * @date 2019/9/10 11:06
     */
    private static List<String> getMethodNames(String[] keys){
        List<String> list = new ArrayList<>();
        for (String key:keys){
            // 将属性的首字符大写，方便构造get，set方法)
            list.add(key.substring(0, 1).toUpperCase() + key.substring(1));
        }
        return list;
    }

    private static byte[] readInputStream(InputStream inStream) throws Exception {
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
        // 关闭输入流
        inStream.close();
        // 把outStream里的数据写入内存
        return outStream.toByteArray();
    }

}
