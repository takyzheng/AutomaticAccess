package entity;

/**
 * 类名 ClassName  Keywords
 * 项目 ProjectName  AutomaticAccess
 * 作者 Author  郑添翼 Taky.Zheng
 * 邮箱 E-mail 275158188@qq.com
 * 时间 Date  2019-07-03 16:39 ＞ω＜
 * 描述 Description TODO
 */
public class Keywords {
    Integer id;
    String name;

    public Keywords(){
    }

    public Keywords(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
