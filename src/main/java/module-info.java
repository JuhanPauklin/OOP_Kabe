module com.example.oop_kabe {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.oop_kabe to javafx.fxml;
    exports com.example.oop_kabe;
}