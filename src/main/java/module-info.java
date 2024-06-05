module com.example.salesagents {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.apache.logging.log4j;
    opens com.example.salesagents to javafx.fxml;
    opens com.example.salesagents.GUI to javafx.fxml;
    exports com.example.salesagents;
    opens com.example.salesagents.Domain to javafx.base;
}
