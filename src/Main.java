import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static int num = 1;
    static long time1 =  System.currentTimeMillis();
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) {
//        System.out.println("Hello World!");
//        System.out.println("开始");

//        Thread thread = new Thread(){
//            public void run(){
//                long a = num*num;
//                while(true){
//                    System.out.println("线程1："+a+"="+num+"*"+num);
//                    num++;
//                    if(num>100){
//                        break;
//                    }
//                }
//
//            }
//        };
//        thread.start();
//        Thread thread1 = new Thread(){
//            public void run(){
//                while(true){
//                    long a = num*num;
//                    System.out.println("线程2："+a+"="+num+"*"+num);
//                    num++;
//                    if(num>100){
//                        break;
//                    }
//                }
//
//            }
//        };
//        thread1.start();
            //test();

//        int i = 0;
//        for(foo('A');foo('B')&&(i<2);foo('C')){
//            i++;
//            foo('D');
//        }
//    String str = "1234";
//change(str);
//        System.out.println(str);

        //select  stu_name no_name grade

        String str = "abcde";
        String a = str.substring(2,3);
        System.out.println(a.toString());
        System.out.println(str.length());
        int m = 8;
        System.out.println(-m);

    }
    static Boolean foo(char c){
        System.out.println(c);
        return true;
    }

    public static void change(String str){
        str = str+"5";
    }

    public static void test (){
        ExecutorService executorService = Executors.newFixedThreadPool(16);

        for(int i = 0; i<16; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {

                    while (true) {
                        synchronized (this){
                            long a = num * num;
                            System.out.println("线程：" + Thread.currentThread().getName() + ".........." + a + "=" + num + "*" + num);
                            //sb.append(a+"\n");
                            num++;
                            if (num > 100000000) {
                                //System.out.println("线程：" + Thread.currentThread().getName() + ".........." + a + "=" + num + "*" + num);
                                //System.out.println(sb.toString());
                                System.out.println(System.currentTimeMillis()-time1);
                                break;
                            }
                        }


                    }

                }
            });
        }
        executorService.shutdown();
    }
}
