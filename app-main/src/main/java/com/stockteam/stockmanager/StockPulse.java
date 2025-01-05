package com.stockteam.stockmanager;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;

public class StockPulse extends Application {
    private Button btnHome;
    private Button btnInventory;
    private Button btnSuppliers;
    private Button btnAddSupplier;
    private Button btnAddProduct;
    private Button addStock;

    private BorderPane root;

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        HBox navBar = createNavBar();
        root.setTop(navBar);

        // Start on the home page
        switchPage("Home");

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Navigation App");
        primaryStage.show();
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setOnAction(e -> switchPage(text));
        button.setStyle("-fx-background-color: transparent; -fx-underline: true;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: transparent; -fx-underline: true; -fx-text-fill: #039ED3;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-underline: true; -fx-text-fill: black;"));
        return button;
    }

    private HBox createNavBar() {

        HBox topPane = new HBox();
        topPane.setPadding(new Insets(15));

        InputStream stream = getClass().getResourceAsStream("/images/logo.png");
        assert stream != null;
        Image image = new Image(stream);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        topPane.getChildren().add(imageView);

        HBox links = new HBox(20);
        links.setPadding(new Insets(15));

        // Create buttons and apply the hyperlink style
        btnHome = createStyledButton("Home");
        btnInventory = createStyledButton("Inventory");
        btnSuppliers = createStyledButton("Suppliers");
        btnAddSupplier = createStyledButton("Add Supplier");
        btnAddProduct = createStyledButton("Add Product");
        addStock = createStyledButton("Add Stock");

        links.getChildren().addAll(
                btnHome, btnInventory, btnSuppliers, btnAddSupplier, btnAddProduct, addStock
        );

        topPane.getChildren().add(links);

        return topPane;
    }

    private void switchPage(String page) {

// Update all button styles back to plain before resetting them later

//        btnHome.setStyle("");
//        btnInventory.setStyle("");
//        btnSuppliers.setStyle("");
//        btnAddProduct.setStyle("");
//        addStock.setStyle("");

        // Display the appropriate content
        Node pageContent;
        switch (page) {
            case "Home":
                pageContent = createHomePageContent();
                break;
            case "Inventory":
                pageContent = createInventoryPageContent();
//                btnPage2.setStyle("-fx-font-weight: bold");
                break;
            case "Add Supplier":
                pageContent = createAddSupplierPageContent();
//                btnPage3.setStyle("-fx-font-weight: bold");
                break;
            case "Suppliers":
                pageContent = createSuppliersPageContent();
//                btnPage3.setStyle("-fx-font-weight: bold");
                break;
            case "Add Product":
                pageContent = createAddProductContent();
//                btnPage3.setStyle("-fx-font-weight: bold");
                break;
            case "Add Stock":
                pageContent = createAddStockContent();
//                btnPage3.setStyle("-fx-font-weight: bold");
                break;
            default:
                return;
        }

        root.setCenter(pageContent);
    }

    private Node createAddSupplierPageContent() {
        HBox content = new HBox();
        content.getChildren().add(new Text("Add Supplier"));
        // Additional layout code for Page 1...

        VBox fields = new VBox(10);
        fields.setPadding(new Insets(15));

        fields.getChildren().add(new Text("Supplier Name"));
        fields.getChildren().add(new Text("Supplier Address"));
        fields.getChildren().add(new Text("Supplier Phone"));
        fields.getChildren().add(new Text("Supplier Email"));

        content.getChildren().add(fields);


        return content;
    }

    private Node createAddStockContent() {
        HBox content = new HBox();
        content.getChildren().add(new Text("Add Stock"));
        // Additional layout code for Page 1...
        return content;
    }

    private Node createAddProductContent() {
        HBox content = new HBox();
        content.getChildren().add(new Text("Add Product"));
        // Additional layout code for Page 1...
        // Fields for adding a new product

        VBox fields = new VBox(10);
        fields.setPadding(new Insets(15));

        fields.getChildren().add(new Text("Product Name"));
        fields.getChildren().add(new Text("Product Description"));
        fields.getChildren().add(new Text("Product Price"));
        fields.getChildren().add(new Text("Product Quantity"));

        content.getChildren().add(fields);

        return content;
    }

    private Node createHomePageContent() {
        // Create and return the content for Page 1
        HBox content = new HBox();
        content.getChildren().add(new Text("Home"));
        // Additional layout code for Page 1...
        return content;
    }

    private Node createInventoryPageContent() {
        // Create and return the content for Page 2
        HBox content = new HBox();
        content.getChildren().add(new Text("Inventory"));
        // Additional layout code for Page 2...

        ArrayList<String> products = new ArrayList<>();

        products.add("Product 1");
        products.add("Product 2");

        for (String product : products) {
            content.getChildren().add(new Text(product));
        }

        return content;
    }

    private Node createSuppliersPageContent() {
        // Create and return the content for Page 3
        HBox content = new HBox();
        content.getChildren().add(new Text("All Suppliers"));
        // Additional layout code for Page 3...
        ArrayList<String> suppliers = new ArrayList<>();
        suppliers.add("Supplier 1");
        suppliers.add("Supplier 2");

        for (String supplier : suppliers) {
            content.getChildren().add(new Text(supplier));
        }

        return content;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
