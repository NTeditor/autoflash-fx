module com.github.nteditor {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.github.nteditor to javafx.fxml;
    exports com.github.nteditor;
}
