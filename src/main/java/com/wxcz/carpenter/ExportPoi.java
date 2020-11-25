package com.wxcz.carpenter;

import com.wxcz.carpenter.pojo.dto.StatisticUserExcl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by cxd
 * @Classname ExportPoi
 * @Description TODO
 * @Date 2020/11/24 15:44
 */
public class ExportPoi {
    /**
     *
     * @param clz  导出集合对象中每个元素的Class对象
     * @param ls  导出的集合对象
     * @param fileName  导出的文件名称
     * @param filePath  导出的文件路径
     * @param titls  导出文件的标题
     * @param fieldName  导出集合中对象的每个属性名称(英文)
     * @throws IllegalAccessException 属性的是不可见 抛出该异常
     * @throws IllegalArgumentException  非法参数异常
     * @throws IOException 连接错误
     */
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
                if (f[j].get(ls.get(i))!= null) {
                    cell.setCellValue(f[j].get(ls.get(i)).toString());
                }
                f[j].setAccessible(false);
            }
        }
//        Integer a = 1==1?0:1;


        //写出
        wb.write(fs);
        //关闭
        fs.close();
        wb.close();
    }

    public static void main(String[] args) throws Exception {

        List<StatisticUserExcl> ls = new ArrayList<>();
        ls.add(new StatisticUserExcl("2020/11/24",0,0.1,0.1,0.1,100.00,0.2));
        ls.add(new StatisticUserExcl("2020/11/25",0,0.1,0.1,0.1,100.00,0.2));
        ls.add(new StatisticUserExcl("2020/11/26",0,0.1,0.1,0.1,100.00,0.2));
        ls.add(new StatisticUserExcl("2020/11/27",0,0.1,0.1,0.1,100.00,0.2));
        ls.add(new StatisticUserExcl("2020/11/28",0,0.1,0.1,0.1,100.00,0.2));
        ls.add(new StatisticUserExcl("2020/11/29",0,0.1,0.1,0.1,100.00,0.2));

        export(StatisticUserExcl.class,ls,"学生成绩单1.xls","D:\\javaexcl",new String[] {"时间","新增用户","7日留存率","15日留存率","30日留存率","人均观看数","平均完播率"},new String[] {"name","id","gender","score"});

    }

}
class Student {
    private String name;
    private int id;
    private int gender;
    private int score;
    public Student(String name, int id, int gender, int score) {
        super();
        this.name = name;
        this.id = id;
        this.gender = gender;
        this.score = score;
    }
    public Student() {
        // TODO Auto-generated constructor stub
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getGender() {
        return gender;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
