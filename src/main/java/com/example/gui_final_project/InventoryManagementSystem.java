package com.example.gui_final_project;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class InventoryManagementSystem extends Application {

    @Override
    public void start(Stage primaryStage) {

        Label loginLbl= new Label("Login");
        loginLbl.setFont(new Font(22));

        Label usernameLbl = new Label("Username");
        TextField usernameField = new TextField();

        Label passwordLbl = new Label("Password");
        TextField passwordField = new TextField();

        Button loginBtn = new Button("Login");
        loginBtn.setFont(new Font(14));
        loginBtn.setOnAction(e  -> handleLogin(primaryStage, usernameField.getText(), passwordField.getText()));

        GridPane grid = new GridPane();
        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setAlignment(Pos.CENTER);
        grid.setColumnSpan(loginLbl,2);
        grid.add(loginLbl, 0,0);
        grid.setHalignment(loginLbl, HPos.CENTER);
        grid.add(usernameLbl,0,1);
        grid.add(usernameField,1,1);
        grid.add(passwordLbl,0,2);
        grid.add(passwordField,1,2);
        grid.add(loginBtn, 0,3);
        grid.setColumnSpan(loginBtn, 2);
        grid.setHalignment(loginBtn, HPos.CENTER);


        Scene scene = new Scene(grid,640, 480);

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();



    }

    private void handleLogin(Stage primaryStage, String username, String password){
        if(username.isEmpty() || password.isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Form Error!", "Please enter your username and password");
            return;
        }

        if(validateLogin(username, password)){
            new HomePage().start(primaryStage);
        }else {
            showAlert(Alert.AlertType.ERROR, "Login Failed!", "Incorrect username or password");
        }



    }

    private boolean validateLogin(String username, String password){
        boolean isValid = false;
        try(Connection conn =DBConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?")){
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) isValid = true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return isValid;

    }
    private void showAlert(Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
