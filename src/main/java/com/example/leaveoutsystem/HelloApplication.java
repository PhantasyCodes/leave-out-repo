package com.example.leaveoutsystem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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

    static Account account = new Account();
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

                        ResultSet resultSet = statement.executeQuery("SELECT userId, firstName, lastName, email, phone, type, leaveDays, password FROM users WHERE email='"+emailField.getText()+"'");

                        while (resultSet.next()) {
                            String password = resultSet.getString("password");
                            if (!Objects.equals(passwordField.getText(), password)) {
                                errorText.setText("Wrong Credentials");
                            } else {
                                account.id = resultSet.getInt("userId");
                                account.firstName = resultSet.getString("firstName");
                                account.lastName = resultSet.getString("lastName");
                                account.email = resultSet.getString("email");
                                account.phone = resultSet.getInt("phone");
                                account.type = resultSet.getString("type");
                                account.leaveDays = resultSet.getInt("leaveDays");
                                System.out.println(account.email);
                                stage.hide();
                                FormApplication.display();
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

    public class FormApplication {

        public static void display() {
            Stage stage = new Stage();
            //Employee id
            Text idText = new Text("Employee Id:");
            Text employeeId = new Text(String.valueOf(account.id));
            //Heading
            Text formHeader = new Text("Leave Out Form");
            //Reason
            Text reasonText = new Text("Reason:");
            TextField reasonField = new TextField();
            //Date Pickers
            Text startingDateText = new Text("Starting Date:");
            DatePicker startingDate = new DatePicker();
            Text endDateText = new Text("Ending Date:");
            DatePicker endDate = new DatePicker();
            //Buttons
            Button submit = new Button("Submit:");
            //Error message
            Text formErrorText = new Text();
            //GridPane
            GridPane formGridPane = new GridPane();
            formGridPane.setMinSize(400, 200);
            formGridPane.setVgap(5);
            formGridPane.setHgap(5);
            formGridPane.setAlignment(Pos.CENTER);
            //Adding nodes to grip
            formGridPane.add(idText, 0, 0);
            formGridPane.add(employeeId, 1, 0);
            formGridPane.add(formHeader, 0, 1);
            formGridPane.add(reasonText, 0, 2);
            formGridPane.add(reasonField, 1, 2);
            formGridPane.add(startingDateText, 0, 3);
            formGridPane.add(startingDate, 1, 3);
            formGridPane.add(endDateText, 0, 4);
            formGridPane.add(endDate, 1, 4);
            formGridPane.add(submit, 0, 5);
            formGridPane.add(formErrorText, 1, 5);
            //Setting the stage
            Scene scene = new Scene(formGridPane);
            stage.setTitle("Leave Out Form");
            stage.setScene(scene);
            stage.show();

            submit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int startDate = startingDate.getValue().getDayOfMonth();
                    int endingDate = endDate.getValue().getDayOfMonth();
                    int duration = endingDate-startDate;
                    if (duration <= 0 ) {
                        formErrorText.setText("Please set a duration that makes sense");
                    } else if (duration > account.leaveDays) {
                        formErrorText.setText("You do not have enough leave days left");
                    }
                    else {
                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/leave_out_db", "root", "");

                            Statement statement = connection.createStatement();

                            statement.execute("INSERT INTO leave_out_requests (userId, duration, status) VALUES ("+account.id+", "+duration+", 'Under review');");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        launch();
    }
}