import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

/**
 * 类名 ClassName  MyTimingTask
 * 项目 ProjectName  AutomaticAccess
 * 作者 Author  郑添翼 Taky.Zheng
 * 邮箱 E-mail 275158188@qq.com
 * 时间 Date  2019-07-06 09:48 ＞ω＜
 * 描述 Description TODO 定时判断每个关键词的访问是否都执行完毕
 */
public class MyTimingTask extends ScheduledService<Boolean> {
    int size = 0;

    //传入提取IP的总数
    public MyTimingTask(int size){
        this.size = size;
    }
    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return ProxyIpHandle.count >= size;
            }
        };
    }
}
