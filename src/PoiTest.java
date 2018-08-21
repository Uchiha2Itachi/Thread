
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * @author uchiha
 * 多线程 往excel 插入100w数据
 */
public class PoiTest {
    public static void main(String[] args) {
        multiThreadWrite();
    }

    /**
     * 使用多线程进行Excel写操作，提高写入效率。
     */
    public static void multiThreadWrite() {
        /**
         * 使用线程池进行线程管理。
         */
        ExecutorService es = Executors.newCachedThreadPool();
        /**
         * 使用计数栅栏
         */
        CountDownLatch doneSignal = new CountDownLatch(10);
        long time = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMDDhhmmss");
        String now = dateFormat.format(new Date());
        //导出文件路径
        String basePath = "D:/";
        //文件名
        String exportFileName = "数据_"+now+".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workBook = null;
        workBook = new SXSSFWorkbook(1000000);
        // 生成一个表格
        SXSSFSheet sheet = workBook.createSheet();
        workBook.setSheetName(0,"学生信息");
        // 创建表格标题行 第一行
        String[] cellTitle = {"序号","姓名","老师","班级"};
        SXSSFRow titleRow = sheet.createRow(0);
        for(int i=0;i<cellTitle.length;i++){
            titleRow.createCell(i).setCellValue(cellTitle[i]);
        }
        try {
            es.submit(new PoiWriter1(doneSignal, sheet, 1, 100000));
            es.submit(new PoiWriter1(doneSignal, sheet, 100001, 200000));
            es.submit(new PoiWriter1(doneSignal, sheet, 200001, 300000));
            es.submit(new PoiWriter1(doneSignal, sheet, 300001, 400000));
            es.submit(new PoiWriter1(doneSignal, sheet, 400001, 500000));
            es.submit(new PoiWriter1(doneSignal, sheet, 500001, 600000));
            es.submit(new PoiWriter1(doneSignal, sheet, 600001, 700000));
            es.submit(new PoiWriter1(doneSignal, sheet, 700001, 800000));
            es.submit(new PoiWriter1(doneSignal, sheet, 800001, 900000));
            es.submit(new PoiWriter1(doneSignal, sheet, 900001, 1000000));
            /**
             * 使用CountDownLatch的await方法，等待所有线程完成sheet操作
             */
            doneSignal.await();
            es.shutdown();
            File  file = new File(basePath+exportFileName);
            //文件输出流
            FileOutputStream outStream = new FileOutputStream(file);
            workBook.write(outStream);
            outStream.flush();
            outStream.close();

            System.out.println("导出2007文件成功！文件导出路径：--"+basePath+exportFileName);
            System.out.println("导入Excel所需时间"+(System.currentTimeMillis()-time)+"ms");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    /**
     * sheet的row使用treeMap存储的，是非线程安全的，所以在创建row时需要进行同步操作。
     * @param sheet
     * @param rownum
     * @return
     */
    private static synchronized SXSSFRow getRow(SXSSFSheet sheet, int rownum) {
        return sheet.createRow(rownum);
    }

    /**
     * 进行sheet写操作的sheet。
     * @author alex
     *
     */
     protected  static class  PoiWriter1 implements Runnable {

        private final CountDownLatch doneSignal;

        private SXSSFSheet sheet;

        private int start;

        private int end;

        public PoiWriter1(CountDownLatch doneSignal, SXSSFSheet sheet, int start,
                         int end) {
            this.doneSignal = doneSignal;
            this.sheet = sheet;
            this.start = start;
            this.end = end;
        }

        public void run() {
            int i = start;
            try {
                    while (i <= end) {
                        SXSSFRow row = getRow(sheet, i);
                        SXSSFCell contentCell = row.getCell(0);
                        if (contentCell == null) {
                            contentCell = row.createCell(0);
                        }
                        contentCell.setCellValue(i);
                        row.createCell(1).setCellValue("张三"+i);
                        row.createCell(2).setCellValue("郭老师");
                        row.createCell(3).setCellValue("109班");
                        ++i;
                    }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                doneSignal.countDown();
                System.out.println("start: " + start + " end: " + end
                        + " Count: " + doneSignal.getCount());
            }
        }

    }
}
