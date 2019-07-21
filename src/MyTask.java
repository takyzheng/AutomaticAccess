import javafx.concurrent.Task;

/**
 * 类名 ClassName  MyTask
 * 项目 ProjectName  AutomaticAccess
 * 作者 Author  郑添翼 Taky.Zheng
 * 邮箱 E-mail 275158188@qq.com
 * 时间 Date  2019-07-04 20:22 ＞ω＜
 * 描述 Description TODO
 */
public class MyTask extends Task<String> {

    String param;
    String ip;
    int port;
    int index;

    public MyTask(String param,String ip,int port,int index){
        this.param = param;
        this.ip = ip;
        this.port = port;
        this.index = index;
    }

    @Override
    protected String call() throws Exception {

        String s = ProxyUtil.HttpsProxy("https://www.baidu.com/s?ie=UTF-8&wd=" + param, ip, port);
        String value = s + "," + index;
        return value;
    }
}
