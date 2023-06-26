module com.read.reader {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.read.reader to javafx.fxml;
    exports com.read.reader;
}