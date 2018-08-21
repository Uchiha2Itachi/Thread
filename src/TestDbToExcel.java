
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import java.util.Date;


//import com.javen.entity.StuEntity;
//import com.javen.service.StuService;
//

/**
 * Created by A on 2018/3/2.
 */
public class TestDbToExcel {
    public static void main(String[] args) {
        try {
            long time = System.currentTimeMillis();
            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMDDhhmmss");
            String now = dateFormat.format(new Date());
            //导出文件路径
            String basePath = "D:/";
            //文件名
            String exportFileName = "数据_"+now+".xlsx";
            String[] cellTitle = {"序号","姓名","学号","性别","入学日期"};
            //需要导出的数据

            // 声明一个工作薄
            SXSSFWorkbook workBook = null;
            workBook = new SXSSFWorkbook(10000);
            // 生成一个表格
            SXSSFSheet sheet = workBook.createSheet();
            workBook.setSheetName(0,"学生信息");
            // 创建表格标题行 第一行
            SXSSFRow titleRow = sheet.createRow(0);
            for(int i=0;i<cellTitle.length;i++){
                titleRow.createCell(i).setCellValue(cellTitle[i]);
            }
            //插入需导出的数据
            for(int i=0;i<1000;i++){
                SXSSFRow row = sheet.createRow(i+1);
                row.createCell(0).setCellValue(i+1);
                row.createCell(1).setCellValue("zhangsan");
                row.createCell(2).setCellValue("老师");
                row.createCell(3).setCellValue("109班");

            }
            File  file = new File(basePath+exportFileName);
            //文件输出流
            FileOutputStream outStream = new FileOutputStream(file);
            workBook.write(outStream);
            outStream.flush();
            outStream.close();
            System.out.println("导出2007文件成功！文件导出路径：--"+basePath+exportFileName);
            System.out.println(System.currentTimeMillis()-time+"ms");
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
