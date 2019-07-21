import com.sun.org.apache.bcel.internal.generic.Select;
import entity.Keywords;
import entity.ProxyIpInfo;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.List;

/**
 * 类名 ClassName  SeleCityDialog
 * 项目 ProjectName  AutomaticAccess
 * 作者 Author  郑添翼 Taky.Zheng
 * 邮箱 E-mail 275158188@qq.com
 * 时间 Date  2019-07-04 16:03 ＞ω＜
 * 描述 Description TODO
 */
public class SeleCityDialog  {

    ObservableList<ProxyIpInfo> proxyIpInfo = null;
    Stage primaryStage = null;

    public SeleCityDialog (ObservableList<ProxyIpInfo> proxyIpInfo){
        this.proxyIpInfo = proxyIpInfo;
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void start() throws Exception {

        TextField textField = new TextField();
        Button btn = new Button("筛选");
        HBox hBox_Top = new HBox(10,textField,btn);
        BorderPane.setMargin(hBox_Top,new Insets(0,0,10,0));

        this.primaryStage = new Stage();


        ListView<ProxyIpInfo> listView = new ListView<>(proxyIpInfo);
        StringConverter<ProxyIpInfo> stringConverter = new StringConverter<ProxyIpInfo>() {
            @Override
            public String toString(ProxyIpInfo object) {
                return object.getCity();
            }

            @Override
            public ProxyIpInfo fromString(String string) {
                return null;
            }
        };
        listView.setCellFactory(CheckBoxListCell.forListView(new Callback<ProxyIpInfo, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(ProxyIpInfo param) {
                return param.getIsCheckedProperty();
            }
        },stringConverter));


        BorderPane root = new BorderPane();
        root.setTop(hBox_Top);
        root.setCenter(listView);
        root.setPadding(new Insets(10));
        primaryStage.setScene(new Scene(root,400,400));
        primaryStage.setTitle("筛选城市");

    }


    public void show(){
        primaryStage.show();
    }

}
