module com.github.nteditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.io;

    opens com.github.nteditor to javafx.fxml;
    exports com.github.nteditor;
}
