module com.example.paint {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.paint to javafx.fxml;
    exports com.example.paint;
}