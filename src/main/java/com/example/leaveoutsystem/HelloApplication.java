package com.example.leaveoutsystem;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        //Email and Password labels
        Text emailText = new Text("Email:");
        Text passwordText = new Text("Password:");
        //Email and Password text-fields
        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();
        //Buttons
        Button loginBtn = new Button("Login");
        //GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(400, 200);
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        //Adding nodes into grid
        gridPane.add(emailText, 0, 0);
        gridPane.add(emailField, 1, 0);
        gridPane.add(passwordText, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginBtn, 0, 2);
        //Setting the stage
        Scene scene = new Scene(gridPane);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}