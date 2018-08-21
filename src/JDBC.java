import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.List;

/**
 * Created by A on 2018/3/7.
 */
public class JDBC {
    private String insert_sql;
    private String charset;
    private boolean debug;

    private String connectStr;
    private String username;
    private String password;

    public JDBC(){
        connectStr = "jdbc:mysql://localhost:3306/stu";
        connectStr += "?useServerPrepStmts=false&rewriteBatchedStatements=true";
        insert_sql = "INSERT INTO stu_class (id,stu_name,tea_name,class_name) VALUES (?,?,?,?)";
        charset = "utf8";
        debug = true;
        username = "root";
        password = "root";
    }


    public void doStore(List<StuClass> list) throws ClassNotFoundException, SQLException, IOException {
        long start = System.currentTimeMillis();
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(connectStr, username,password);
        conn.setAutoCommit(false); // 设置手动提交
        int count = 0;
        PreparedStatement psts = conn.prepareStatement(insert_sql);
        for (int i = 0; i <list.size() ; i++) {
            psts.setString(1,list.get(i).getId());
            psts.setString(2,list.get(i).getStuName());
            psts.setString(3,list.get(i).getTeaName());
            psts.setString(4,list.get(i).getClassName());
            psts.addBatch();
            count++;
        }
        psts.executeBatch(); // 执行批量处理
        conn.commit();  // 提交
        System.out.println("All down : " + count);
        conn.close();
        System.out.println("导入数据库所需时间"+(System.currentTimeMillis()-start)+"ms");
    }



}
