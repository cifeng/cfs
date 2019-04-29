package com.platform.cfs.common.advice;





import com.platform.cfs.common.annotation.Excel;
import com.platform.cfs.common.util.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.*;

public class ExcelExportUtils {

    public static void exportExcel(String title, Class<?> pojoClass,
                                   Collection<?> dataSet, OutputStream out) {
        // 使用userModel模式实现的，当excel文档出现10万级别的大数据文件可能导致内存溢出
        exportExcelInUserModel(title, pojoClass, dataSet, out);
//		exportExcel2007InUserModelByPage(title, pojoClass, dataSet, out);
        // 使用eventModel实现，暂时未实现
    }
    /**
     * 导出xls
     * @Description: TODO
     * @param title
     * @param pojoClass
     * @param dataSet
     * @param out
     */
    private static void exportExcelInUserModel(String title,
                                               Class<?> pojoClass, Collection<?> dataSet, OutputStream out) {
        try {
            // 首先检查数据看是否是正确的
          /*  if (dataSet == null || dataSet.size() == 0) {
                throw new Exception("导出数据为空！");
            }
            if (title == null || out == null || pojoClass == null) {
                throw new Exception("传入参数不能为空！");
            }*/
            // 声明一个工作薄
            Workbook workbook = new HSSFWorkbook();
            // 生成一个表格
            Sheet sheet = workbook.createSheet(title);
            // 标题
            List<String> exportFieldTitle = new ArrayList<String>();
            List<Integer> exportFieldWidth = new ArrayList<Integer>();
            // 拿到所有列名，以及导出的字段的get方法
            List<Method> methodObj = new ArrayList<Method>();
            List<Class<?>> filedTypes = new ArrayList<Class<?>>();
            Map<String, Method> convertMethod = new HashMap<String, Method>();
            // 得到所有字段
            Field fileds[] = pojoClass.getDeclaredFields();
            // 遍历整个filed
            for (int i = 0; i < fileds.length; i++) {
                Field field = fileds[i];
                Excel excel = field.getAnnotation(Excel.class);
                // 如果设置了annottion
                if (excel != null) {
                    // 添加到标题
                    exportFieldTitle.add(excel.fieldName());
                    // 添加标题的列宽
                    exportFieldWidth.add(excel.fieldWidth());
                    // 添加到需要导出的字段的方法
                    String fieldname = field.getName();
                    filedTypes.add(field.getType());//将类型记录下来
                    // System.out.println(i+"列宽"+excel.exportName()+" "+excel.exportFieldWidth());
                    StringBuffer getMethodName = new StringBuffer("get");
                    getMethodName.append(fieldname.substring(0, 1)
                            .toUpperCase());
                    getMethodName.append(fieldname.substring(1));

                    Method getMethod = pojoClass.getMethod(
                            getMethodName.toString(), new Class[] {});

                    methodObj.add(getMethod);
                    if (excel.exportConvertSign() == 1) {
                        StringBuffer getConvertMethodName = new StringBuffer(
                                "get");
                        getConvertMethodName.append(fieldname.substring(0, 1)
                                .toUpperCase());
                        getConvertMethodName.append(fieldname.substring(1));
                        getConvertMethodName.append("Convert");
                        Method getConvertMethod = pojoClass
                                .getMethod(getConvertMethodName.toString(),
                                        new Class[] {});
                        convertMethod.put(getMethodName.toString(),
                                getConvertMethod);
                    }
                }
            }

            int index = 0;
            // 产生表格标题行
            Row row = sheet.createRow(index);
            for (int i = 0, exportFieldTitleSize = exportFieldTitle.size(); i < exportFieldTitleSize; i++) {
                Cell cell = row.createCell(i);
                //添加样式
                //cell.setCellStyle(style);
                RichTextString text = new HSSFRichTextString(exportFieldTitle.get(i));
                cell.setCellValue(text);
            }

            // 设置每行的列宽
            for (int i = 0; i < exportFieldWidth.size(); i++) {
                // 256=65280/255
                sheet.setColumnWidth(i, 256 * exportFieldWidth.get(i));
            }
            Iterator<?> its = dataSet.iterator();
            // 循环插入剩下的集合
            while (its.hasNext()) {
                // 从第二行开始写，第一行是标题
                index++;
                row = sheet.createRow(index);
                Object t = its.next();
                for (int k = 0, methodObjSize = methodObj.size(); k < methodObjSize; k++) {
                    Cell cell = row.createCell(k);
                    Method getMethod = methodObj.get(k);
                    Class<?> filedTypeClass=filedTypes.get(k);
                    Object value = null;
                    if (convertMethod.containsKey(getMethod.getName())) {
                        Method cm = convertMethod.get(getMethod.getName());
                        value = cm.invoke(t, new Object[] {});
                    } else {
                        value = getMethod.invoke(t, new Object[] {});
                    }
                    if(value!=null) {
                        if(filedTypeClass.equals(Date.class)){
                            value= DateUtils.getFormatDate((Date)value,"yyyy-MM-dd HH:mm");
                            cell.setCellValue(value.toString());
                        }else {
                            cell.setCellValue(value.toString());
                        }
                    }
                }
            }

            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * xlsx导出
     * @Description: TODO
     * @param title
     * @param pojoClass
     * @param dataSet
     * @param out
     */
    private static void exportExcel2007InUserModel(String title,
                                                   Class<?> pojoClass, Collection<?> dataSet, OutputStream out) {

        try {
            // 首先检查数据看是否是正确的
            if (dataSet == null || dataSet.size() == 0) {
                throw new Exception("导出数据为空！");
            }
            if (title == null || out == null || pojoClass == null) {
                throw new Exception("传入参数不能为空！");
            }
            // 声明一个工作薄
            Workbook workbook = new XSSFWorkbook();
            // 生成一个表格
            Sheet sheet = workbook.createSheet(title);

            // 标题
            List<String> exportFieldTitle = new ArrayList<String>();
            List<Integer> exportFieldWidth = new ArrayList<Integer>();
            // 拿到所有列名，以及导出的字段的get方法
            List<Method> methodObj = new ArrayList<Method>();
            Map<String, Method> convertMethod = new HashMap<String, Method>();
            // 得到所有字段
            Field fileds[] = pojoClass.getDeclaredFields();
            // 遍历整个filed
            for (int i = 0; i < fileds.length; i++) {
                Field field = fileds[i];
                Excel excel = field.getAnnotation(Excel.class);
                // 如果设置了annottion
                if (excel != null) {
                    // 添加到标题
                    exportFieldTitle.add(excel.fieldName());
                    // 添加标题的列宽
                    exportFieldWidth.add(excel.fieldWidth());
                    // 添加到需要导出的字段的方法
                    String fieldname = field.getName();
                    // System.out.println(i+"列宽"+excel.exportName()+" "+excel.exportFieldWidth());
                    StringBuffer getMethodName = new StringBuffer("get");
                    getMethodName.append(fieldname.substring(0, 1)
                            .toUpperCase());
                    getMethodName.append(fieldname.substring(1));

                    Method getMethod = pojoClass.getMethod(
                            getMethodName.toString(), new Class[] {});

                    methodObj.add(getMethod);
                    if (excel.exportConvertSign() == 1) {
                        StringBuffer getConvertMethodName = new StringBuffer(
                                "get");
                        getConvertMethodName.append(fieldname.substring(0, 1)
                                .toUpperCase());
                        getConvertMethodName.append(fieldname.substring(1));
                        getConvertMethodName.append("Convert");
                        Method getConvertMethod = pojoClass
                                .getMethod(getConvertMethodName.toString(),
                                        new Class[] {});
                        convertMethod.put(getMethodName.toString(),
                                getConvertMethod);
                    }
                }
            }

            int index = 0;
            // 产生表格标题行
            Row row = sheet.createRow(index);
            for (int i = 0, exportFieldTitleSize = exportFieldTitle.size(); i < exportFieldTitleSize; i++) {
                Cell cell = row.createCell(i);
                //添加样式
                //cell.setCellStyle(style);
                RichTextString text = new XSSFRichTextString(exportFieldTitle.get(i));
                cell.setCellValue(text);
            }

            // 设置每行的列宽
            for (int i = 0; i < exportFieldWidth.size(); i++) {
                // 256=65280/255
                sheet.setColumnWidth(i, 256 * exportFieldWidth.get(i));
            }
            Iterator<?> its = dataSet.iterator();
            // 循环插入剩下的集合
            while (its.hasNext()) {
                // 从第二行开始写，第一行是标题
                index++;
                row = sheet.createRow(index);
                Object t = its.next();
                for (int k = 0, methodObjSize = methodObj.size(); k < methodObjSize; k++) {
                    Cell cell = row.createCell(k);
                    Method getMethod = methodObj.get(k);
                    Object value = null;
                    if (convertMethod.containsKey(getMethod.getName())) {
                        Method cm = convertMethod.get(getMethod.getName());
                        value = cm.invoke(t, new Object[] {});
                    } else {
                        value = getMethod.invoke(t, new Object[] {});
                    }
                    if(value!=null) {
                        cell.setCellValue(value.toString());
                    }
                }
            }

            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * xlsx导出，使用SXSSFWorkbook处理大数据
     * @Description: TODO
     * @param title
     * @param pojoClass
     * @param out
     */
    private static void exportExcel2007InUserModelByPages(String title,
                                                          Class<?> pojoClass, Collection<?> dataSetList0, OutputStream out) {
        try {
            List<ArrayList<?>> dataSetList=(ArrayList<ArrayList<?>>) dataSetList0;
            // 首先检查数据看是否是正确的
            if (dataSetList == null || dataSetList.size() == 0  ) {
                throw new Exception("导出数据为空！");
            }
            if (title == null || out == null || pojoClass == null) {
                throw new Exception("传入参数不能为空！");
            }
            // 声明一个工作薄,内存中保留 10000 条数据，以免内存溢出
            Workbook workbook = new SXSSFWorkbook(10000);
            // 生成一个表格
            Sheet sheet = workbook.createSheet(title);
            // 标题
            List<String> exportFieldTitle = new ArrayList<String>();
            List<Integer> exportFieldWidth = new ArrayList<Integer>();
            // 拿到所有列名，以及导出的字段的get方法
            List<Method> methodObj = new ArrayList<Method>();
            Map<String, Method> convertMethod = new HashMap<String, Method>();
            // 得到所有字段
            Field fileds[] = pojoClass.getDeclaredFields();
            // 遍历整个filed
            for (int i = 0; i < fileds.length; i++) {
                Field field = fileds[i];
                Excel excel = field.getAnnotation(Excel.class);
                // 如果设置了annottion
                if (excel != null) {
                    // 添加到标题
                    exportFieldTitle.add(excel.fieldName());
                    // 添加标题的列宽
                    exportFieldWidth.add(excel.fieldWidth());
                    // 添加到需要导出的字段的方法
                    String fieldname = field.getName();
                    // System.out.println(i+"列宽"+excel.exportName()+" "+excel.exportFieldWidth());
                    StringBuffer getMethodName = new StringBuffer("get");
                    getMethodName.append(fieldname.substring(0, 1)
                            .toUpperCase());
                    getMethodName.append(fieldname.substring(1));

                    Method getMethod = pojoClass.getMethod(
                            getMethodName.toString(), new Class[] {});

                    methodObj.add(getMethod);
                    if (excel.exportConvertSign() == 1) {
                        StringBuffer getConvertMethodName = new StringBuffer(
                                "get");
                        getConvertMethodName.append(fieldname.substring(0, 1)
                                .toUpperCase());
                        getConvertMethodName.append(fieldname.substring(1));
                        getConvertMethodName.append("Convert");
                        Method getConvertMethod = pojoClass
                                .getMethod(getConvertMethodName.toString(),
                                        new Class[] {});
                        convertMethod.put(getMethodName.toString(),
                                getConvertMethod);
                    }
                }
            }

            int index = 0;
            // 产生表格标题行
            Row row = sheet.createRow(index);
            for (int i = 0, exportFieldTitleSize = exportFieldTitle.size(); i < exportFieldTitleSize; i++) {
                Cell cell = row.createCell(i);
                //添加样式
                //cell.setCellStyle(style);
                RichTextString text = new XSSFRichTextString(exportFieldTitle.get(i));
                cell.setCellValue(text);
            }

            // 设置每行的列宽
            for (int i = 0; i < exportFieldWidth.size(); i++) {
                // 256=65280/255
                sheet.setColumnWidth(i, 256 * exportFieldWidth.get(i));
            }


            for (int i = 0; i < dataSetList.size(); i++) {

                ArrayList<?>  dataSet=dataSetList.get(i);
                if(null==dataSet) {
                    break;
                }
                Iterator<?> its = dataSet.iterator();
                // 循环插入剩下的集合
                while (its.hasNext()) {
                    // 从第二行开始写，第一行是标题
                    index++;
                    row = sheet.createRow(index);
                    Object t = its.next();
                    for (int k = 0, methodObjSize = methodObj.size(); k < methodObjSize; k++) {
                        Cell cell = row.createCell(k);
                        Method getMethod = methodObj.get(k);
                        Object value = null;
                        if (convertMethod.containsKey(getMethod.getName())) {
                            Method cm = convertMethod.get(getMethod.getName());
                            value = cm.invoke(t, new Object[] {});
                        } else {
                            value = getMethod.invoke(t, new Object[] {});
                        }
                        if(value!=null) {
                            cell.setCellValue(value.toString());
                        }
                    }
                }
            }

            workbook.write(out);

        }catch(Exception e){
            e.printStackTrace();
        }

    }


    public static void exportExcelFn(HttpServletResponse response ,List<?> list,Class<?> pojoClass,String workName,String sheetName){
        try {
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(workName+".xls", "UTF-8"));// 转码
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            ExcelExportUtils.exportExcel(sheetName,pojoClass, list,response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }







}
