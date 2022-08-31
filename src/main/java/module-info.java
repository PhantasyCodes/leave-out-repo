module com.example.leaveoutsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.leaveoutsystem to javafx.fxml;
    exports com.example.leaveoutsystem;
}