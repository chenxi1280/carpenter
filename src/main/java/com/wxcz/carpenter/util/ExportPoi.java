package com.wxcz.carpenter.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author by cxd
 * @Classname ExportPoi
 * @Description TODO
 * @Date 2020/11/24 16:19
 */
public class ExportPoi {

    public static void export(Class<?> clz, List<?> ls, String fileName, String filePath, String[] titls, String[] fieldName) throws IllegalArgumentException, IllegalAccessException, IOException, IOException {
        //创建工作簿对象
        Workbook wb = new HSSFWorkbook();

        //创建流对象
        FileOutputStream fs = new FileOutputStream(filePath+ File.separator+fileName);

        //创建sheet
        Sheet sheet = wb.createSheet(fileName.substring(0,fileName.indexOf(".")));

        //创建row
        Row rowTitle = sheet.createRow(0);//表头

        //创建单元格
        for(int i = 0;i<titls.length;i++) {
            //创建单元格
            Cell cell = rowTitle.createCell(i);
            //填充值
            cell.setCellValue(titls[i]);
        }

        //添加数 据 循环创建List长度个row
        for(int i =0;i<ls.size();i++) {
            //创建row
            Row rowData = sheet.createRow(i+1);//行数据
            Field[] f = clz.getDeclaredFields();
            for(int j = 0;j<titls.length;j++) {
                //创建单元格
                Cell cell = rowData.createCell(j);
                //获取属性的名称
                f[j].setAccessible(true);
                cell.setCellValue(f[j].get(ls.get(i)).toString());
                f[j].setAccessible(false);
            }
        }


        //写出
        wb.write(fs);
        //关闭
        fs.close();
        wb.close();
    }


}
