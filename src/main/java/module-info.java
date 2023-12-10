module ru.read.reader {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ru.read.reader to javafx.fxml;
    exports ru.read.reader;
    exports ru.read.reader.fx;
    opens ru.read.reader.fx to javafx.fxml;
}