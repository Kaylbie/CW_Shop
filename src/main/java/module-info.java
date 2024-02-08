module com.kursinis.prif4kursinis {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;

    opens com.kursinis.cwshop to javafx.fxml;
    exports com.kursinis.cwshop;
    opens com.kursinis.cwshop.model to javafx.fxml, org.hibernate.orm.core;
    exports com.kursinis.cwshop.model;
    opens com.kursinis.cwshop.fxControllers to javafx.fxml;
    opens com.kursinis.cwshop.fxControllers.tableviewparameters to javafx.base;
    exports com.kursinis.cwshop.fxControllers;
    exports com.kursinis.cwshop.fxControllers.windowControllers;
    opens com.kursinis.cwshop.fxControllers.windowControllers to javafx.fxml;
    exports com.kursinis.cwshop.fxControllers.oldControllers;
    opens com.kursinis.cwshop.fxControllers.oldControllers to javafx.fxml;
    exports com.kursinis.cwshop.fxControllers.regLog;
    opens com.kursinis.cwshop.fxControllers.regLog to javafx.fxml;
    opens com.kursinis.cwshop.fxControllers.createControllers to javafx.fxml;
    exports com.kursinis.cwshop.fxControllers.userWindowControllers;
    opens com.kursinis.cwshop.fxControllers.userWindowControllers to javafx.fxml;
}