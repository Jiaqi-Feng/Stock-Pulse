module com.stockteam.stockmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;


    opens com.stockteam.stockmanager to javafx.fxml;
    exports com.stockteam.stockmanager;
}