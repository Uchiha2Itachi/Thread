/**
 * Created by A on 2018/3/7.
 */
public class StuClass {
    private String id;
    private String stuName;
    private String teaName;
    private String className;

    public StuClass() {

    }

    public StuClass(String id, String stuName, String teaName, String className) {
        this.id = id;
        this.stuName = stuName;
        this.teaName = teaName;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
