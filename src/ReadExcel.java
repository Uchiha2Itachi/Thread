import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by A on 2018/3/7.
 */
public class ReadExcel {
    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        /**
         * 使用线程池进行线程管理。
         */
        ExecutorService es = Executors.newCachedThreadPool();
        /**
         * 使用计数栅栏
         */
        CountDownLatch doneSignal = new CountDownLatch(2);
        try {
            es.submit(new PoiReader(doneSignal,  1, 5000));
            es.submit(new PoiReader(doneSignal,  5001, 10000));
//            es.submit(new PoiReader(doneSignal,  200001, 300000));
//            es.submit(new PoiReader(doneSignal,  300001, 400000));
//            es.submit(new PoiReader(doneSignal,  400001, 500000));
//            es.submit(new PoiReader(doneSignal,  500001, 600000));
//            es.submit(new PoiReader(doneSignal,  600001, 700000));
//            es.submit(new PoiReader(doneSignal,  700001, 800000));
//            es.submit(new PoiReader(doneSignal,  800001, 900000));
//            es.submit(new PoiReader(doneSignal,  900001, 1000000));
            doneSignal.await();
            es.shutdown();
            System.out.println(System.currentTimeMillis()-time+"ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    protected  static class PoiReader implements Runnable{
        private final CountDownLatch doneSignal;
        private int start;

        private int end;

        public PoiReader(CountDownLatch doneSignal,int start, int end) {
            this.doneSignal = doneSignal;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            try {
                synchronized (this){
                    XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File("D:\\数据4.xlsx")));
                    XSSFSheet sheet = null;

                    sheet = workbook.getSheetAt(0);
                    //System.out.println(sheet.getLastRowNum());
                    for (int j = start; j < end + 1; j++) {
                        // 获取最后一行的行标
                        XSSFRow row = sheet.getRow(j);
                        if (row != null) {
                            for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum
                                // 是获取最后一个不为空的列是第几个
                                if (row.getCell(k) != null) { // getCell 获取单元格数据
                                    System.out.print(row.getCell(k) + "\t");
                                } else {
                                    System.out.print("\t");
                                }
                            }
                        }
                        System.out.println("");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
