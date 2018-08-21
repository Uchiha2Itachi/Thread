import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * 测试能否写入数据库
 */
public class Tes {
    /**
     * JDBC批量操作实验
     */
    public static PreparedStatement prest;

    public static Connection connection;

    static {
        String URL = "jdbc:mysql://127.0.0.1:3306/stu?useUnicode=true&amp;characterEncoding=utf-8";
        String USER = "root";
        String PASSWORD = "root";
        //1.加载驱动程序
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //2.获得数据库链接
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            prest = connection.prepareStatement(" insert into stu_class (id, stu_name, tea_name,class_name) VALUES (?,?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final long l = System.currentTimeMillis();
        final ArrayList<StuClass> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new StuClass(i + "", "何盼盼", "张三", "904"));
        }

        try {
            for (StuClass stu : list) {
                prest.setString(1, stu.getId());
                prest.setString(2, stu.getStuName());
                prest.setString(3, stu.getTeaName());
                prest.setString(4, stu.getClassName());
                prest.addBatch();
            }
            prest.executeBatch();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - l);


    }
}
