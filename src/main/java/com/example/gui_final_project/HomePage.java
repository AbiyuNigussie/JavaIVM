package com.example.gui_final_project;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HomePage  {
    private TableView<InventoryItem> table;
    public void start(Stage primaryStage) {

        GridPane grid = new GridPane();
        grid.setVgap(20);
        grid.setHgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setAlignment(Pos.CENTER);


        Label itemNameLabel = new Label("Item Name:");
        TextField itemNameField = new TextField();

        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField();

        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();

        Button addBtn = new Button("Add Item");

        grid.add(itemNameLabel, 0, 0);
        grid.add(itemNameField, 1, 0);
        grid.add(quantityLabel, 0, 1);
        grid.add(quantityField, 1, 1);
        grid.add(priceLabel, 0, 2);
        grid.add(priceField, 1, 2);
        grid.add(addBtn, 1, 3);

        table = new TableView<>();
        table.setEditable(true);

        TableColumn<InventoryItem, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<InventoryItem, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<InventoryItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().addAll(nameCol, quantityCol, priceCol);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10,0,0,10));
        vbox.getChildren().addAll(grid, table);
        addBtn.setOnAction(e -> addItem(itemNameField.getText(), quantityField.getText(), priceField.getText()));

        loadData();

        Scene scene = new Scene(vbox, 640, 480);
        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void addItem(String itemName, String quantityStr, String priceStr){
        if(itemName.isEmpty() || quantityStr.isEmpty() || priceStr.isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all item details");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            double price = Double.parseDouble(priceStr);
            try(Connection conn = DBConnector.getConnection()){
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO inventory (item_name, quantity, price) VALUES (?,?,?)");
                stmt.setString(1, itemName);
                stmt.setInt(2, quantity);
                stmt.setDouble(3, price);

                stmt.executeUpdate();
                loadData();
            }catch (NumberFormatException e){
                showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter valid quantity and price");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void loadData(){
        ObservableList<InventoryItem> data = FXCollections.observableArrayList();
        try(Connection conn = DBConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM inventory");
            ResultSet rs = stmt.executeQuery();
        ){
            while(rs.next()){
                data.add(new InventoryItem(rs.getString("item_name"), rs.getInt("quantity"), rs.getDouble("price")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        table.setItems(data);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
