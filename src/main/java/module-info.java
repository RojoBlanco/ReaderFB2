module com.read.reader {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ru.read.reader to javafx.fxml;
    exports ru.read.reader;
}