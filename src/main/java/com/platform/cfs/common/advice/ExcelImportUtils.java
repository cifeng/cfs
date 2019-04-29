package com.platform.cfs.common.advice;


import com.platform.cfs.common.annotation.ImportExcel;
import com.platform.cfs.common.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Administrator
 * @ClassName
 * @Description
 * @date 2017/12/28
 */
public class ExcelImportUtils {
    private static Logger log = LoggerFactory.getLogger(ExcelImportUtils.class);


    public static List<Object> importExcelInUserModel(Class<?> pojoClass,Sheet sheet,int start,int end) {
        List<Object> lists = new ArrayList<Object>();
        try {
            // 得到所有字段
            Field fileds[] = pojoClass.getDeclaredFields();
            List<ImportExcel>  fieldList = new ArrayList<ImportExcel>();
            //设置一个map 用于存储 字段名称和excel注解名称的对应关系
            Map<String,String> map = new HashMap<String,String>();
            Row row0 = sheet.getRow(0);
            int num =0;
            for (int i = 0; i < fileds.length; i++) {
                Field field = fileds[i];
                ImportExcel excel = field.getAnnotation(ImportExcel.class);
                // 如果设置了annottion
                if (excel != null) {
                    fieldList.add(excel);
                    map.put(excel.fieldName(),field.getName());
                }
            }
            if(fieldList.size()<=0){
                log.info("【导入数据】时获取带有注解的field长度为0");
                return null;
            }
            //排序 根据注解中的order字段
            Collections.sort(fieldList, new Comparator<ImportExcel>() {
                @Override
                public int compare(ImportExcel o1, ImportExcel o2) {
                    int i = o1.order() - o2.order();
                    return i;
                }
            });
            //判断注解的顺序和 excel的顺序是否一致
            for(int i = 0 ; i < fieldList.size();i++){
                //获取注解名称
                String fieldName = fieldList.get(i).fieldName();
                if(!row0.getCell(i).getStringCellValue().equals(fieldName)){
                    log.info("【导入数据】时模版的名称 和注解的名称对应不上 模版的名称为："+row0.getCell(i).getStringCellValue()+"  注解的名称为："+fieldName);
                    return  null;
                }
            }
            /**************** 操作不是绑定列的数据 ******************************************************************/
            for (int rowNum = start; rowNum <= end; rowNum++) { // 循环获取excel里的数据
                Row row = sheet.getRow(rowNum);
                if(null != row) {
                    Object o = pojoClass.newInstance();
                    for (int i = 0; i < fieldList.size(); i++) {
                        Field field = o.getClass().getDeclaredField(map.get(fieldList.get(i).fieldName()));
                        field.setAccessible(true);
                        setFieldValue(row, o, i, field);

                    }
                    lists.add(o);
                }else{
                    break;
                }
            }
        } catch (Exception e) {
            log.error("【导入数据】时发生异常",e);
            e.printStackTrace();
        }
        return lists;
    }

    private static void setFieldValue(Row row, Object o, int i, Field field) throws IllegalAccessException {
        // 如果类型是String
        if ("class java.lang.String".equals(field.getGenericType().toString())) {
            // 如果type是类类型，则前面包含"class "，后面跟类名
            if(Cell.CELL_TYPE_STRING==row.getCell(i).getCellType()){
                field.set(o,row.getCell(i).getStringCellValue());
            }else if(Cell.CELL_TYPE_NUMERIC==row.getCell(i).getCellType()){
                Double d = row.getCell(i).getNumericCellValue();
                BigDecimal bd = new BigDecimal(d);
                field.set(o,bd.toPlainString());
            }
        }
        // 如果类型是Integer
        if ("class java.lang.Integer".equals(field.getGenericType().toString())) {
            if(Cell.CELL_TYPE_STRING==row.getCell(i).getCellType()){
                int d = Integer.parseInt(row.getCell(i).getStringCellValue());
                field.set(o,d);
            }else if(Cell.CELL_TYPE_NUMERIC==row.getCell(i).getCellType()){
                int d = (int)row.getCell(i).getNumericCellValue();
                field.set(o,d);
            }
        }
        // 如果类型是Double
        if ("class java.lang.Double".equals(field.getGenericType().toString())) {
            Double d = row.getCell(i).getNumericCellValue();
            field.set(o,d);

        }
        // 如果类型是Boolean 是封装类
        if ("class java.lang.Boolean".equals(field.getGenericType().toString())) {
            field.set(o,row.getCell(i).getBooleanCellValue());
        }
        // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
        // 反射找不到getter的具体名
        if ("boolean".equals(field.getGenericType().toString())) {
            field.set(o,row.getCell(i).getBooleanCellValue());
        }
        // 如果类型是Date
        if ("class java.util.Date".equals(field.getGenericType().toString())) {
            String val = row.getCell(i).getStringCellValue();
            field.set(o, DateUtils.getDate(val,"yyyy-MM-dd HH:mm"));
        }
        // 如果类型是Short
        if ("class java.lang.Short".equals(field.getGenericType().toString())) {
            field.set(o,row.getCell(i).getStringCellValue());
        }
    }

    public static void main(String[] args) throws Exception {
        // 声明一个工作薄
        String path ="D:\\me\\考勤相关\\4月份考勤.xls";
        String name="刘非";
        String exportPath ="D:\\me\\考勤相关\\"+name+"4月份考勤.xls";
        String title=name+"考勤";
        File file = new File(path);
        InputStream stream = new FileInputStream(file);
        POIFSFileSystem  f = new POIFSFileSystem(stream);
        HSSFWorkbook workbook = new HSSFWorkbook(f);
        // 获取用户上传excel的表头 判断是否一致
        Sheet sheet = workbook.getSheetAt(0);
        List<Object> lists = ExcelImportUtils.importExcelInUserModel(ClockVO.class,sheet,0,Integer.MAX_VALUE);
        List<ClockVO> results = new ArrayList<ClockVO>();
        for(int i= 0;i<lists.size();i++){
            ClockVO vo  = (ClockVO) lists.get(i);
            if(i!=0){
                if(StringUtils.isNotBlank(name)){
                    if(name.equals(vo.getName())){
                        results.add(vo);
                    }
                }else{


                    results.add(vo);
                }
            }
        }
        OutputStream exportstream = new FileOutputStream(exportPath);
        ExcelExportUtils.exportExcel("title",ClockVO.class,results,exportstream);
        System.out.println(results);

    }

}
