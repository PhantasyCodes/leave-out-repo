module com.example.leaveoutsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.leaveoutsystem to javafx.fxml;
    exports com.example.leaveoutsystem;
}