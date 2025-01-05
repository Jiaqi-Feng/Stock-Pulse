package com.stockteam.stockmanager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.Rating;


import java.io.InputStream;

public class MenuViewExample extends Application {

    com.stockteam.stockmanager.Supplier s1 = new com.stockteam.stockmanager.Supplier("Coca Cola", "1234567890", "1234567890");
    com.stockteam.stockmanager.Supplier s2 = new com.stockteam.stockmanager.Supplier("Pepsi", "0987654321", "0987654321");

    private final TableView<Product> table = new TableView<>();
    private final ObservableList<Product> data = FXCollections.observableArrayList(
            new Product("1234567890123", "Coca Cola", "Coca Cola", "1.5L"),
            new Product("1234567890124", "Pepsi", "Pepsi", "1.5L")
    );

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("StockPulse");

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 900, 600);

        HBox topPane = new HBox();
        topPane.setPadding(new Insets(15));

        InputStream stream = getClass().getResourceAsStream("/images/logo.png");
        assert stream != null;
        Image image = new Image(stream);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        topPane.getChildren().add(imageView);

        HBox links = new HBox(50);
        links.setPadding(new Insets(15));
        links.getChildren().addAll(
                new Label("Home"),
                new Label("Inventory"),
                new Label("Your Suppliers"),
                new Label("Your Customers"),
                new Label("POS Integration"),
                new Label("Your Orders")
        );

        topPane.getChildren().add(links);

        Pane mainPane = new Pane();
        VBox headerContainer = new VBox(); // Create a VBox container for the header
//        headerContainer.setPadding(new Insets(35)); // Set padding for the container

        Text header = new Text("Manage Your Inventory");
        header.setStyle("-fx-font-size: 24px;");

        headerContainer.getChildren().add(header); // Add the header to the container

        headerContainer.getChildren().add(new Text("Search for an item")); // Add a text to the mainPane
        headerContainer.getChildren().add(new TextField());

//        mainPane.getChildren().add(headerContainer); // Add the container to the mainPane

        TableColumn barcodeCol = new TableColumn("Barcode");
        TableColumn productNameCol = new TableColumn("Product Name");
        TableColumn brandCol = new TableColumn("Brand");
        TableColumn sizeCol = new TableColumn("Size");
        TableColumn supplierCol = new TableColumn("Supplier");

        barcodeCol.setCellValueFactory(new PropertyValueFactory<Product, String>("barcode"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        brandCol.setCellValueFactory(new PropertyValueFactory<Product, String>("brand"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<Product, String>("size"));
        supplierCol.setCellValueFactory(new PropertyValueFactory<Product, String>("supplier"));

        table.setItems(data);
        table.getColumns().addAll(barcodeCol, productNameCol, brandCol, sizeCol, supplierCol);
        table.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.8));
        table.prefHeight(500);

//        Rating rating = new Rating();
//        rating.setRating(3);

        ObservableList<String> items = FXCollections.observableArrayList("Item 1", "Item 2", "Item 3", "Item 4");
        CheckComboBox<String> checkComboBox = new CheckComboBox<>(items);

        // Listen for changes in the checked items
        checkComboBox.getCheckModel().getCheckedItems().addListener((javafx.beans.Observable obs) -> {
            System.out.println("Selected items: " + checkComboBox.getCheckModel().getCheckedItems());
        });

//        mainPane.getChildren().add(rating);
//        mainPane.getChildren().add(checkComboBox);

        VBox tableContainer = new VBox();
        tableContainer.setPadding(new Insets(35));
        tableContainer.getChildren().add(headerContainer);
//        tableContainer.getChildren().add(table);
        tableContainer.getChildren().add(table);

        mainPane.getChildren().add(tableContainer);

        root.setTop(topPane);
        root.setCenter(mainPane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
