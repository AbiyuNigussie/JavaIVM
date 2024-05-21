package com.example.gui_final_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Menbere
 */
public class HelloController extends Application {

    @Override
    public void start(Stage primaryStage) {

        Label lblI=new Label("Id");
        Label lblN=new Label("Name");
        TextField txtI=new TextField();
        TextField txtN=new TextField();
        Button btn = new Button();
        btn.setText("Save&Retrive");
        TableView<Student> tblV=new TableView<>();
        TableColumn<Student,String> tblC1=new TableColumn<>("ID_Number");
        tblC1.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Student,String> tblC2=new TableColumn<>("First Name");
        tblC2.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblV.getColumns().addAll(tblC1,tblC2);
        VBox vb=new VBox(tblV);
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost/ureg","root","password");//establish connection
                    Class.forName("com.mysql.cj.jdbc.Driver");//loads driver
                    Statement stmt=con.createStatement();//create statement
                    String s1="insert into student values('"+txtI.getText()+"','"+txtN.getText()+"')";
                    stmt.executeUpdate(s1);
                    String s2="select * from student";
                    ResultSet rst=stmt.executeQuery(s2);

                    while(rst.next())
                    {
                        tblV.getItems().add(new Student(rst.getString("id"),rst.getString("name")));

                    }

                } catch (Exception ex) {
                    Logger.getLogger(InventoryManagementSystem.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        FlowPane root = new FlowPane();
        root.setPadding(new Insets(20,20,20,20));
        root.setHgap(20);
        root.setVgap(20);
        root.setStyle("-fx-background-color:lightgray;");
        root.getChildren().addAll(lblI,txtI,lblN,txtN,btn,vb);

        Scene scene = new Scene(root, 500, 300);

        primaryStage.setTitle("Stud Info");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static class Student
    {
        private String idNum;
        private String fName;

        public Student(String id, String name) {
            this.idNum = id;
            this.fName= name;
        }

        public String getId() {
            return idNum;
        }

        public String getName() {
            return fName;
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}
