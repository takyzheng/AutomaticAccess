package entity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.apache.commons.text.StringEscapeUtils;

/**
 * 类名 ClassName  ProxyIpInfo
 * 项目 ProjectName  AutomaticAccess
 * 作者 Author  郑添翼 Taky.Zheng
 * 邮箱 E-mail 275158188@qq.com
 * 时间 Date  2019-07-04 14:35 ＞ω＜
 * 描述 Description TODO
 */
public class ProxyIpInfo {

    private String ip; //ip
    private String port; //端口
    private String expire_time; //过期时间
    private String city;    //城市
    private String isp; //运营商

    private SimpleBooleanProperty isChecked = null;
    private SimpleStringProperty result = new SimpleStringProperty("");


    public String getResult() {
        return result.get();
    }

    public SimpleStringProperty resultProperty() {
        return result;
    }

    public void setResult(String result) {
        this.result.set(result);
    }

    public boolean isIsChecked() {
        return isChecked.get();
    }

    public SimpleBooleanProperty getIsCheckedProperty() {
        if (this.isChecked == null) {
            this.isChecked = new SimpleBooleanProperty(false);
        }
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        if (this.isChecked == null) {
            this.isChecked = new SimpleBooleanProperty(isChecked);
            return;
        }
        this.isChecked.set(isChecked);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = StringEscapeUtils.unescapeJava(city);
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = StringEscapeUtils.unescapeJava(isp);
    }

    @Override
    public String toString() {
        return "ProxyIpInfo{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", expire_time='" + expire_time + '\'' +
                ", city='" + city + '\'' +
                ", isp='" + isp + '\'' +
                '}';
    }
}
