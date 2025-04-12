module com.goosesq.zoomir {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.goosesq.zoomir to javafx.fxml;
    exports com.goosesq.zoomir;
}