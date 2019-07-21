import entity.Keywords;
import entity.ProxyIpInfo;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 类名 ClassName  Main
 * 项目 ProjectName  AutomaticAccess
 * 作者 Author  郑添翼 Taky.Zheng
 * 邮箱 E-mail 275158188@qq.com
 * 时间 Date  2019-07-03 15:26 ＞ω＜
 * 描述 Description TODO
 */
public class Main extends Application {

    private static Logger logger = LogManager.getLogger("Main");



    ObservableList<Keywords> keywords = FXCollections.observableArrayList(); //记录关键词个数
    ObservableList<ProxyIpInfo> proxyIpInfos = null; //代理IP的信息
    String apiUrl = "http://api.qingtingip.com/ip?app_key=5a1252bd2c534e3e8f79c5832598b476&num=5&ptc=http&fmt=json&lb=\\r\\n&port=0&mr=2&";

    //标记
    int count = 0; //添加关键词时记录总个数
    int index = -1; //记录关键词列表选择的下标
    int keywordsCount = 0; //记录关键词总数
    int tempCount = 0; //记录临时下标
    int keywordsIndex = 0; // 记录当前关键词的执行下标

    Integer time = 1;   //运行间隔

    MyTimingTask myTimingTask = null; //定时任务


    @Override
    public void start(Stage primaryStage) throws Exception {

        logger.info("Hello, World!");

        //======================== 中间 =========================================

        TextField textField_Right11 = new TextField(apiUrl);
        HBox.setHgrow(textField_Right11, Priority.ALWAYS);
        Button selCityBtn = new Button("筛选城市");
        Button getIpsBtn = new Button("提取IP");
        HBox hBox_Right1 = new HBox(10,textField_Right11,selCityBtn,getIpsBtn);


        TableView<ProxyIpInfo> tableView_Right = new TableView<>();

        TableColumn<ProxyIpInfo, String> t1_Right = new TableColumn<>("代理IP");
        t1_Right.setCellValueFactory(new PropertyValueFactory<>("ip"));

        TableColumn<ProxyIpInfo, String> t2_Right = new TableColumn<>("端口");
        t2_Right.setCellValueFactory(new PropertyValueFactory<>("port"));

        TableColumn<ProxyIpInfo, String> t3_Right = new TableColumn<>("过期时间");
        t3_Right.setCellValueFactory(new PropertyValueFactory<>("expire_time"));

        TableColumn<ProxyIpInfo, String> t4_Right = new TableColumn<>("城市");
        t4_Right.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn<ProxyIpInfo, String> t5_Right = new TableColumn<>("运营商");
        t5_Right.setCellValueFactory(new PropertyValueFactory<>("isp"));

        TableColumn<ProxyIpInfo, String> t6_Right = new TableColumn<>("状态");
        t6_Right.setCellValueFactory(new PropertyValueFactory<>("result"));

        tableView_Right.getColumns().addAll(t1_Right, t2_Right,t3_Right,t4_Right,t5_Right,t6_Right);
        tableView_Right.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);



        VBox vBox_Right = new VBox(10,hBox_Right1,tableView_Right);
        BorderPane.setMargin(vBox_Right,new Insets(0,0,0,10));
        //======================== 左侧 =========================================
        Button addBtn = new Button("添加");
        addBtn.setDisable(true);
        Button delBtn = new Button("删除");
        delBtn.setDisable(true);
        TextField textField = new TextField();
        HBox hBox = new HBox(10,textField,addBtn,delBtn);

        Button startSeleBtn = new Button("启动搜索程序");
        Label label = new Label("刷新速度(秒):");
        TextField textField1 = new TextField("1");
        textField1.setPrefWidth(50);

        HBox hBox1 = new HBox(10,startSeleBtn,label,textField1);
        hBox1.setAlignment(Pos.CENTER_LEFT);

        TableView<Keywords> tableView = new TableView<>(keywords);

        TableColumn<Keywords, Integer> t1 = new TableColumn<Keywords, Integer>("序号");
        t1.setCellValueFactory(new PropertyValueFactory<Keywords, Integer>("id"));

        TableColumn<Keywords, String> t2 = new TableColumn<>("关键词");
        t2.setCellValueFactory(new PropertyValueFactory<Keywords,String>("name"));

        tableView.getColumns().addAll(t1, t2);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);




        //===========================================================================

        //==========================顶部=======================================

        Button testBtn1 = new Button("测试1");
        Button testBtn2 = new Button("测试2");
        Button testBtn3 = new Button("测试3");
        HBox temphbox = new HBox(10,testBtn1,testBtn2,testBtn3);
        BorderPane.setMargin(temphbox,new Insets(0,0,10,0));

        VBox vBox = new VBox(10,hBox,tableView,hBox1);

        //===========================================================================



        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setCenter(vBox_Right);
        root.setLeft(vBox);
        root.setTop(temphbox);
        primaryStage.setScene(new Scene(root, 1300, 600));
        primaryStage.setTitle("百度自动搜索关键词工具 1.0 Taky");
        primaryStage.show();


        textField_Right11.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                apiUrl = newValue;
            }
        });


        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() != 0){
                addBtn.setDisable(false);
            }else{
                addBtn.setDisable(true);
            }
        });

        tableView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null && newValue.intValue() >= 0 ) {
                index = newValue.intValue();
                delBtn.setDisable(false);
            }else {
                delBtn.setDisable(true);
            }

        });

        tableView.setOnKeyPressed(p -> {
            if (p.getCode() == KeyCode.ESCAPE) {
                index = -1;
                tableView.getSelectionModel().clearSelection();
            }
        });


        addBtn.setOnAction(p -> {
            keywords.add(new Keywords(count++, textField.getText()));
            textField.clear();
        });

        delBtn.setOnAction(p ->{
            keywords.remove(index);
        });



        testBtn1.setOnAction(p -> {

        });

        testBtn2.setOnAction(p -> {
            ProxyUtil.HttpsProxy("https://www.baidu.com/s?ie=UTF-8&wd=" + "ip", "213.86.5.211", 8080);
        });


        testBtn3.setOnAction(p -> {


        });

        startSeleBtn.setOnAction(p -> {
            startSele();
        });


        getIpsBtn.setOnAction(p -> {
            proxyIpInfos = ProxyIpHandle.getIps(apiUrl);
            tableView_Right.setItems(proxyIpInfos);
        });


        selCityBtn.setOnAction(p -> {
            proxyIpInfos.forEach(p1 -> {
                p1.getIsCheckedProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                });
            });
            SeleCityDialog seleCityDialog = new SeleCityDialog(proxyIpInfos);
            seleCityDialog.show();
        });


    }

    public static void main(String[] args) {
        launch(args);
    }


    class IpReplaceTask extends ScheduledService<Number> {
        @Override
        protected Task<Number> createTask() {
            return new Task<Number>() {
                @Override
                protected Number call() throws Exception {
                    startSele();
                    return null;
                }
            };
        }
    }


    public void startSele(){
        myTimingTask = new MyTimingTask(proxyIpInfos.size());
        myTimingTask.setDelay(Duration.ZERO);
        myTimingTask.setPeriod(Duration.seconds(5));
        myTimingTask.start();

        ProxyIpHandle.cons(keywords.get(0).getName(),proxyIpInfos);

        myTimingTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            //如果执行完一个关键词
            if (newValue != null && newValue) {
                ProxyIpHandle.count = 0; //清零执行关键词的计数器
                keywordsIndex++; //关键词下标+1

                //判断,如果关键词全部搜索完,关闭定时任务
                if (keywordsIndex == keywords.size()){
                    keywordsIndex = 0; //下标清零
                    myTimingTask.cancel(); //关闭定时任务
                    System.out.println("再次提取IP..");
                    return;
                }
                String tempKeyword = keywords.get(keywordsIndex).getName(); //拿出关键词
                ProxyIpHandle.cons(tempKeyword,proxyIpInfos);
                System.out.println("当前关键词:" + tempKeyword);
            }
        });
    }



}
