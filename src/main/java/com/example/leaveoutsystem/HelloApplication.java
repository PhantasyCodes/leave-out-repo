package com.example.leaveoutsystem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

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
        Button registerBtn = new Button("Register");
        //Error Text
        Text errorText = new Text();
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
        gridPane.add(errorText, 1, 2);
        gridPane.add(registerBtn, 0, 3);
        //Setting the stage
        Scene scene = new Scene(gridPane);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
        //Creating Login button event handler
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(emailField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty()) {
                    errorText.setText("Please fill all fields");
                }
                else {
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/leave_out_db", "root", "");

                        Statement statement = connection.createStatement();

                        ResultSet resultSet = statement.executeQuery("SELECT password FROM users WHERE email='"+emailField.getText()+"'");

                        while (resultSet.next()) {
                            String password = resultSet.getString("password");
                            if (!Objects.equals(passwordField.getText(), password)) {
                                errorText.setText("Wrong Credentials");
                            } else {
                                System.out.println("Correct");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        registerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

    }

    public static void main(String[] args) {
        launch();
    }
}