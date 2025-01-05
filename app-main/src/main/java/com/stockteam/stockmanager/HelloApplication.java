package com.stockteam.stockmanager;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.controlsfx.control.CheckComboBox;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private Database database = new Database("suppliersData.txt","productsData.txt","stockData.txt");
    private BorderPane root;

	/**
	 * The main entry point for all JavaFX applications.
	 *
	 * @param primaryStage the primary stage for this application, onto which the
	 *                     application scene caset. Applications may create
	 *                     other stages, if needed, but they will not be primary
	 *                     stages.
	 * @throws IOException
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		root = new BorderPane();
		HBox navBar = createNavBar();
		root.setTop(navBar);

		// Start on the home page
		switchPage("Dashboard");

		Scene scene = new Scene(root, 900, 600);
		primaryStage.setTitle("Manage Your Inventory");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(event -> {
			database.saveSuppliers();
			database.saveProducts();
			database.saveStock();
		});
	}

	/**
	 * Switch the page content based on the button pressed
	 *
	 * @param page The page to switch to
	 */
	private void switchPage(String page) {
		Node pageContent;
		switch (page) {
		case "Dashboard":
			pageContent = createHomePageContent();
			break;
		case "Inventory":
			pageContent = createInventoryPageContent();
			break;
		case "Products":
			pageContent = createProductPageContent();
			break;
		case "Add Supplier":
			pageContent = setupSupplierPane();
			break;
		case "Suppliers":
			pageContent = createSuppliersPageContent();
			break;
		case "Add Product":
			pageContent = setupProductPane();
			break;
		case "Add Stock":
			pageContent = createAddStockContent();
			break;
		default:
			return;
		}

		root.setCenter(pageContent);
	}

	/**
	 * Create the navigation bar
	 *
	 * @return HBox The navigation bar
	 */
	private HBox createNavBar() {
		HBox topPane = new HBox();
		topPane.setPadding(new Insets(15));
		topPane.setStyle("-fx-background-color: linear-gradient(to right, #1A203B, #27304C);");

//        topPane.setStyle("-fx-background-color: #2A2A2A;"); // Professional dark background
		topPane.setSpacing(10); // Add spacing for visual separation

		HBox links = new HBox(15);
		links.setAlignment(Pos.CENTER_LEFT);

		// Create buttons
		Button btnHome = createStyledButton("Dashboard");
		Button btnInventory = createStyledButton("Inventory");
		Button btnProducts = createStyledButton("Products");
		Button btnSuppliers = createStyledButton("Suppliers");
		Button btnAddSupplier = createStyledButton("Add Supplier");
		Button btnAddProduct = createStyledButton("Add Product");
		Button addStock = createStyledButton("Add Stock");

		links.getChildren().addAll(btnHome, btnInventory, btnProducts, btnSuppliers, btnAddSupplier, btnAddProduct,
				addStock);

		// Set logo to the right side
		ImageView imageView = createLogoImageView();
		HBox logoContainer = new HBox(imageView);
		logoContainer.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(logoContainer, Priority.ALWAYS); // This will push the logo to the right

		topPane.getChildren().addAll(links, logoContainer);

		return topPane;
	}

	/**
	 * Create the logo image view
	 *
	 * @return ImageView The logo image view
	 */
	private ImageView createLogoImageView() {
		InputStream stream = getClass().getResourceAsStream("/images/logo.png");
		assert stream != null;
		Image image = new Image(stream);
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(80); // Slightly bigger logo
		imageView.setFitHeight(80);
		HBox.setMargin(imageView, new Insets(0, 20, 0, 0)); // Add some margin to the right of the logo
		return imageView;
	}

	/**
	 * Create a button for the navigation bar
	 *
	 * @param text
	 * @return Button A button with the given text
	 */
	private Button createStyledButton(String text) {
		Button button = new Button(text);
		button.setOnAction(e -> switchPage(text));
		button.setStyle(
				"-fx-background-color: transparent; -fx-underline: false; -fx-text-fill: white; -fx-font-size: 16px;");
		// Adding a shadow effect on hover for a modern look
		DropShadow dropShadow = new DropShadow();
		dropShadow.setColor(Color.valueOf("#039ED3"));
		button.setOnMouseEntered(e -> {
			button.setEffect(dropShadow);
			button.setTextFill(Color.valueOf("#039ED3"));
		});
		button.setOnMouseExited(e -> {
			button.setEffect(null);
			button.setTextFill(Color.WHITE);
		});
		return button;
	}

	/**
	 * Set up add/edit product page.
	 *
	 * @return Node containing the page content of the product page
	 */
	private Node setupProductPane() {
		// Set the overall padding and spacing for the page content
		VBox pageContent = new VBox(20); // Use spacing to separate elements vertically
		pageContent.setPadding(new Insets(20)); // Set padding around the entire content

		// Create the header text
		Text headerText = new Text("Add/Edit Product");
		headerText.setStyle("-fx-font-size: 24pt;"); // Set the header text size

		// Add the header text to the top of the page content
		pageContent.getChildren().add(headerText);

		GridPane productPane = new GridPane();

		Text errorMessage = new Text("");
		errorMessage.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));

		Text barcode = new Text("SKU");
		TextField barcodeField = new TextField();
		barcodeField.setMaxWidth(300);

		Text nameLabel = new Text("Product Name");
		TextField nameField = new TextField();
		nameField.setMaxWidth(300);

		Text brandLabel = new Text("Brand");
		TextField brandField = new TextField();
		brandField.setMaxWidth(300);

		Text sizeLabel = new Text("Size");
		TextField sizeField = new TextField();
		brandField.setMaxWidth(300);

		Text suppliersLabel = new Text("Supplier(s)");

		ObservableList<String> items = FXCollections.observableArrayList(database.allSuppliersNames());
		CheckComboBox<String> checkComboBox = new CheckComboBox<>(items);

		checkComboBox.setPrefWidth(150);

		// display alert when field is blurred
		barcodeField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (!newValue) { // when focus lost
				if (barcodeField.getText().trim().isEmpty()) {
					barcodeField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
					// add a message saying SKU cannot be empty
					errorMessage.setFill(Color.RED);
					errorMessage.setText("SKU cannot be empty");
				} else {
					String sku = barcodeField.getText().trim();
					Product product = database.productExist(sku);
					barcodeField.setStyle("-fx-border-color: green ; -fx-border-width: 2px ;");

					errorMessage.setFill(Color.BLUE);

					// Product exist
					if (product != null) {
						errorMessage.setText("Edit existing product details");
						nameField.setText(product.getProductName());
						brandField.setText(product.getBrand());
						sizeField.setText(product.getSize());
						List<String> supplierNames = product.getSuppliers();
						for (String s : supplierNames) {
							checkComboBox.getCheckModel().check(s);

						}
					} else {
						errorMessage.setText("New product");
					}
				}
			}
		});

		Button saveProductBtn = new Button();
		saveProductBtn.setPrefSize(150, 30);
		saveProductBtn.setText("Save Product");
		saveProductBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String sku = barcodeField.getText().trim();

				if (!sku.isEmpty()) {
					Product product = database.productExist(sku);
					String productName = nameField.getText().trim();
					String brand = brandField.getText().trim();
					String size = sizeField.getText().trim();
					List<String> suppliers = new ArrayList<>();
					suppliers.addAll(checkComboBox.getCheckModel().getCheckedItems());
					// if product exists already
					if (product != null) {
						product.setProductName(productName);
						product.setBrand(brand);
						product.setSize(size);
						product.setSuppliers(suppliers);
						errorMessage.setFill(Color.GREEN);
						errorMessage.setText("Product details updated");

					} else {
						// if new product
						product = new Product(sku, productName, brand, size);
						product.setSuppliers(suppliers);
						database.addProduct(product);
						errorMessage.setFill(Color.GREEN);
						errorMessage.setText("New product added");
					}

					barcodeField.setText("");
					nameField.setText("");
					brandField.setText("");
					sizeField.setText("");
					checkComboBox.getCheckModel().clearChecks();
				}
			}
		});

		productPane.setVgap(10);
		productPane.setHgap(10);
		productPane.add(errorMessage, 0, 0, 2, 1);

		productPane.add(barcode, 0, 1);
		productPane.add(barcodeField, 1, 1);
		productPane.add(nameLabel, 0, 2);
		productPane.add(nameField, 1, 2);
		productPane.add(brandLabel, 0, 3);
		productPane.add(brandField, 1, 3);
		productPane.add(sizeLabel, 0, 4);
		productPane.add(sizeField, 1, 4);
		productPane.add(suppliersLabel, 0, 5);
		productPane.add(checkComboBox, 1, 5);

		productPane.add(saveProductBtn, 0, 6);
		productPane.setAlignment(Pos.CENTER);

		pageContent.getChildren().add(productPane);

		return pageContent;
	}

	/**
	 * Set up add/edit supplier page.
	 *
	 * @return Node containing the page content
	 */
	private Node setupSupplierPane() {
		// Set the overall padding and spacing for the page content
		VBox pageContent = new VBox(20); // Use spacing to separate elements vertically
		pageContent.setPadding(new Insets(20)); // Set padding around the entire content

		// Create the header text
		Text headerText = new Text("Add/Edit Supplier");
		headerText.setStyle("-fx-font-size: 24pt;"); // Set the header text size
		// Add the header text to the top of the page content
		pageContent.getChildren().add(headerText);

		GridPane supplierPane = new GridPane();

		Text errorMessage = new Text("");
		errorMessage.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));

		Text companyName = new Text("Company Name");
		TextField companyNameField = new TextField();
		companyNameField.setMaxWidth(300);

		Text emailLabel = new Text("Email");
		TextField emailField = new TextField();
		emailField.setMaxWidth(300);

		Text phoneLabel = new Text("Phone");
		TextField phoneField = new TextField();
		phoneField.setMaxWidth(300);

		// display alert when field is blurred
		companyNameField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (!newValue) { // when focus lost
				if (companyNameField.getText().trim().isEmpty()) {
					companyNameField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
					// add a message saying company name is cannot be empty
					errorMessage.setFill(Color.RED);
					errorMessage.setText("Company name cannot be empty");

				} else {
					companyNameField.setStyle("-fx-border-color: green ; -fx-border-width: 2px ;");

					errorMessage.setFill(Color.BLUE);
					String supplierName = companyNameField.getText().trim();
					Supplier supplier = database.supplierExist(supplierName);
					// if supplier exists already
					if (supplier != null) {
						errorMessage.setText("Edit exiting supplier details");
						emailField.setText(supplier.getEmail());
						phoneField.setText(supplier.getPhone());
					} else {
						// if new supplier
						errorMessage.setText("New supplier");
					}
				}

			}
		});

		Button saveSupplierBtn = new Button();
		saveSupplierBtn.setPrefSize(150, 30);
		saveSupplierBtn.setText("Save Supplier");

		saveSupplierBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String supplierName = companyNameField.getText().trim();

				if (!supplierName.isEmpty()) {
					Supplier supplier = database.supplierExist(supplierName);
					String email = emailField.getText().trim();
					String phone = phoneField.getText().trim();
					// if supplier exists already
					if (supplier != null) {
						supplier.setEmail(email);
						supplier.setPhone(phone);
						errorMessage.setFill(Color.GREEN);
						errorMessage.setText("Supplier details updated");

					} else {
						// if new supplier
						supplier = new Supplier(supplierName, email, phone);
						database.addSupplier(supplier);
						errorMessage.setFill(Color.GREEN);
						errorMessage.setText("New supplier added");

					}

					companyNameField.setText("");
					emailField.setText("");
					phoneField.setText("");
				}
			}
		});

		supplierPane.setVgap(10);
		supplierPane.setHgap(10);
		supplierPane.add(errorMessage, 0, 0, 2, 1);
		supplierPane.add(companyName, 0, 1);
		supplierPane.add(companyNameField, 1, 1);
		supplierPane.add(emailLabel, 0, 2);
		supplierPane.add(emailField, 1, 2);
		supplierPane.add(phoneLabel, 0, 3);
		supplierPane.add(phoneField, 1, 3);
		supplierPane.add(saveSupplierBtn, 0, 4);
		supplierPane.setAlignment(Pos.CENTER);

		pageContent.getChildren().add(supplierPane);

		return pageContent;
	}

	/**
	 * Create the page content for the add stock page
	 *
	 * @return Node containing the page content for the stock page
	 */
	private Node createAddStockContent() {
		// Set the overall padding and spacing for the page content
		VBox pageContent = new VBox(20); // Use spacing to separate elements vertically
		pageContent.setPadding(new Insets(20)); // Set padding around the entire content

		// Create the header text
		Text headerText = new Text("Add Stock");
		headerText.setStyle("-fx-font-size: 24pt;"); // Set the header text size

		// Add the header text to the top of the page content
		pageContent.getChildren().add(headerText);

		// Create a form that creates new stock with Barcode, Quantity Expire Date
		// Arrival Date Supplier
		GridPane stockPane = new GridPane();

		Text errorMessage = new Text("");
		errorMessage.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));

		Text barcode = new Text("Barcode");
		ObservableList<String> items1 = FXCollections.observableArrayList(database.allProductsSkus());
		ComboBox<String> barcodeField = new ComboBox<>(items1);
		barcodeField.setMaxWidth(300);

		Text quantityLabel = new Text("Quantity");
		TextField quantityField = new TextField();

		quantityField.setMaxWidth(300);

		Text expireLabel = new Text("Expire Date");
//        TextField expireField = new TextField();
		// ControlsFX datepicker
		DatePicker expireField = new DatePicker();
		expireField.setEditable(false);
//        expireField.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
//        expireField.getCalendarView().todayButtonTextProperty().set("Today");
//        expireField.getCalendarView().setShowWeeks(false);
//        expireField.getStylesheets().add("org/jfxtras/scene/control/css/jfxtras.css");

		expireField.setMaxWidth(300);

		Text arrivalLabel = new Text("Arrival Date");
//        TextField arrivalField = new TextField();
		DatePicker arrivalField = new DatePicker();

		arrivalField.setMaxWidth(300);
		arrivalField.setEditable(false);
		Text supplierLabel = new Text("Supplier");
//        TextField supplierField = new TextField();

		ObservableList<String> items = FXCollections.observableArrayList();
		ComboBox<String> supplierField = new ComboBox<>(items);
		supplierField.setMaxWidth(300);

		// display alert when field is blurred
		barcodeField.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				// Your action here
				String sku = barcodeField.getSelectionModel().getSelectedItem();
				Product product = database.productExist(sku);
				barcodeField.setStyle("-fx-border-color: green ; -fx-border-width: 2px ;");
				supplierField.getSelectionModel().clearSelection();
				errorMessage.setFill(Color.BLACK);
				errorMessage.setText(product.productDescription());
				items.clear();
				items.addAll(product.getSuppliers());

			}

//            else {
//                barcodeField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
//                // You can also set the error message here if the new value is null
//                errorMessage.setFill(Color.RED);
//                errorMessage.setText("SKU cannot be empty");
//            }
		});

//        barcodeField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
//            if (!newValue) { // when focus lost
//                if (barcodeField.getSelectionModel().getSelectedItem() == null) {
//                    barcodeField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
//                    // add a message saying SKU cannot be empty
//                    errorMessage.setFill(Color.RED);
//                    errorMessage.setText("Must select a product");
//                } else {
//                    String sku = barcodeField.getSelectionModel().getSelectedItem();
//                    Product product = database.productExist(sku);
//                    barcodeField.setStyle("-fx-border-color: green ; -fx-border-width: 2px ;");
//
//                    errorMessage.setFill(Color.BLACK);
//                    errorMessage.setText(product.productDescription());
//                    items.clear();
//                    items.addAll(product.getSuppliers());
//
//                }
//            }
//        });

		Button saveStockBtn = new Button();
		saveStockBtn.setPrefSize(150, 30);
		saveStockBtn.setText("Save Stock");

		saveStockBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String sku = barcodeField.getSelectionModel().getSelectedItem();
				Product product = database.productExist(sku);
				String quantity = quantityField.getText().trim();
				String supplier = supplierField.getValue();
				LocalDate localDate1 = expireField.getValue();
				LocalDate localDate2 = arrivalField.getValue();

				if (sku == null) {
//                	barcodeField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
					// add a message saying SKU cannot be empty
					errorMessage.setFill(Color.RED);
					errorMessage.setText("Must select a product");
				} else if (quantity.isEmpty()) {
//                	quantityField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
					errorMessage.setFill(Color.RED);
					errorMessage.setText("Quantity cannot be empty");
				} else if (localDate1 == null) {
					errorMessage.setFill(Color.RED);
					errorMessage.setText("Must select an expire date");
				} else if (localDate2 == null) {
					errorMessage.setFill(Color.RED);
					errorMessage.setText("Must select an arrival date");
				} else if (supplier == null) {
					errorMessage.setFill(Color.RED);
					errorMessage.setText("Must select a supplier");
				} else {
					try {
						Integer.parseInt(quantity);
						if (Integer.parseInt(quantity) <= 0) {
							errorMessage.setFill(Color.RED);
							errorMessage.setText("Quantity must be a positive integer");
						} else {
							Instant instant1 = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
							Date expire = Date.from(instant1);
//			                    String expire = expireField.getText().trim().isEmpty() ? "N/A" : expireField.getText().trim();

							Instant instant2 = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
							Date arrival = Date.from(instant2);

//			                    String arrival = arrivalField.getText().trim();
//			                    String supplier = supplierField.getText().trim();

							Stock stock = new Stock(sku, product.productDescription(), Integer.parseInt(quantity),
									expire, arrival, supplier);
							database.addStock(stock);
							errorMessage.setFill(Color.GREEN);
							errorMessage.setText("Stock added");
							// if product exists already

							// After added stock to database
							barcodeField.getSelectionModel().clearSelection();
							quantityField.setText("");

							expireField.setValue(null);
							arrivalField.setValue(null);
							supplierField.getSelectionModel().clearSelection();
//			                    supplierField.setText("");
						}

					} catch (NumberFormatException e) {
						// TODO: handle exception
						errorMessage.setFill(Color.RED);
						errorMessage.setText("Quantity must be an integer");
					}

				}
			}
		});

		stockPane.setVgap(10);
		stockPane.setHgap(10);
		stockPane.add(errorMessage, 0, 0, 2, 1);
		stockPane.add(barcode, 0, 1);
		stockPane.add(barcodeField, 1, 1);
		stockPane.add(quantityLabel, 0, 2);
		stockPane.add(quantityField, 1, 2);
		stockPane.add(expireLabel, 0, 3);
		stockPane.add(expireField, 1, 3);
		stockPane.add(arrivalLabel, 0, 4);
		stockPane.add(arrivalField, 1, 4);
		stockPane.add(supplierLabel, 0, 5);
		stockPane.add(supplierField, 1, 5);
		stockPane.add(saveStockBtn, 0, 6);
		stockPane.setAlignment(Pos.CENTER);

		pageContent.getChildren().add(stockPane);

		return pageContent;
	}

	
	/**
	 * Create the page content for the home page
	 *
	 * @return Node containing the page content
	 */
	private Node createHomePageContent() {
		// Set the overall padding and spacing for the page content
		VBox pageContent = new VBox(); // Use spacing to separate elements vertically
		pageContent.setPadding(new Insets(20)); // Set padding around the entire content

		// Create the header text
		Text headerText = new Text("Dashboard");
		headerText.setStyle("-fx-font-size: 24pt;"); // Set the header text size

		// Add the header text to the top of the page content
		pageContent.getChildren().add(headerText);

		// Use a VBox as the main container to allow for vertical centering
		VBox content = new VBox(20); // Add vertical spacing between children
		content.setAlignment(Pos.CENTER); // Center the children both vertically and horizontally

		// Style the main container with padding and a background color
		content.setStyle("-fx-padding: 40; -fx-background-color: #F2F2F2;");

		// Calculate the stock counts for the alerts
		int lowStockLimit = 20;
		int lowStockCount = 0;
		for (Stock s : database.getStock()) {
			if (s.getQuantity() < lowStockLimit) {
				lowStockCount++;
			}
		}

//        int lowStockCount = (int) database.getStock().stream().filter(stock -> stock.getQuantity() < 5).count();
		int expiringStockLimit = 7; // days
		int expiringStockCount = 0;
		LocalDate localToday = LocalDate.now();
		LocalDate expireCheckDay = localToday.plusDays(expiringStockLimit);
		Date expireCheckDayDF = Date.from(expireCheckDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
		for (Stock s : database.getStock()) {
			if (s.getExpireDate().before(expireCheckDayDF)) {
				expiringStockCount++;
			}
		}

//        int expiringStockCount = (int) database.getStock().stream().filter(stock -> stock.isExpiringWithinAWeek()).count();

		// Create alert boxes for low stock and expiring stock with distinct colors and
		// styles
		HBox lowStockAlertBox = createAlertBox("Low Stock Alert", "Items with stock less than " + lowStockLimit + ": ",
				lowStockCount, "#FFCCBC", "#BF360C");
		HBox expiringStockAlertBox = createAlertBox("Expiring Stock Alert", "Items expiring within a week: ",
				expiringStockCount, "#B3E5FC", "#01579B");

		// Horizontal Box to hold the info boxes side by side
		HBox infoBoxes = new HBox(20); // Add spacing between children
		infoBoxes.setAlignment(Pos.CENTER); // Center the children horizontally

		// Calculate total stock
		int totalStock = database.getStock().stream().mapToInt(Stock::getQuantity).sum();

		// Create info boxes
		VBox stockBox = createInfoBox("Stock", totalStock);
		VBox productBox = createInfoBox("Products", database.getProducts().size());
		VBox supplierBox = createInfoBox("Suppliers", database.getSuppliers().size());

		// Add the info boxes to the Horizontal Box
		infoBoxes.getChildren().addAll(stockBox, supplierBox, productBox);

		// Add the alert boxes and the Horizontal Box to the main content VBox
		content.getChildren().addAll(lowStockAlertBox, expiringStockAlertBox, infoBoxes);

		pageContent.getChildren().add(content);

		return pageContent;
	}

	// Helper method to create styled alert boxes with different colors and styles
	// for the title and number
	private HBox createAlertBox(String alertTitle, String alertMessage, int count, String backgroundColor,
			String borderColor) {
		HBox alertBox = new HBox(10); // Add some spacing between title and message
		alertBox.setAlignment(Pos.CENTER_LEFT); // Align the text to the left
		alertBox.setStyle("-fx-padding: 15; -fx-background-color: " + backgroundColor + "; -fx-border-color: "
				+ borderColor
				+ "; -fx-border-width: 1; -fx-background-radius: 5; -fx-border-radius: 5; -fx-pref-width: 300;");

		// Create and style the alert title
		Text title = new Text(alertTitle);
		title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: " + borderColor + ";");

		// Create and style the alert number
		Text number = new Text(String.valueOf(count));
		number.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + borderColor + ";");

		// Create and style the alert message (text part)
		Text message = new Text(alertMessage);
		message.setStyle("-fx-font-size: 16px;");

		// Add the title, number, and message to the alert box
		alertBox.getChildren().addAll(title, message, number);

		return alertBox;
	}

	/**
	 * Create a VBox to display information
	 *
	 * @param title
	 * @param count
	 * @return VBox A box containing the title and count
	 */
	private VBox createInfoBox(String title, int count) {
		VBox box = new VBox(10); // Add vertical spacing between children
		box.setAlignment(Pos.CENTER); // Center the children vertically and horizontally

		// Style the VBox with padding, border, and background color
		box.setStyle("-fx-padding: 50; -fx-border-style: solid inside;" + "-fx-border-width: 2; -fx-border-insets: 5;"
				+ "-fx-border-radius: 5; -fx-border-color: #333;");

		// Create and style the title text
		Text boxTitle = new Text(title);
		boxTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

		// Create and style the count text
		Text countText = new Text(Integer.toString(count));
		countText.setStyle("-fx-font-size: 14px;");

		// Add the title and count to the VBox
		box.getChildren().addAll(boxTitle, countText);

		return box;
	}

	/**
	 * Create the page content for the inventory page
	 *
	 * @return Node containing the page content
	 */
	private Node createInventoryPageContent() {
		TableView<Stock> table = new TableView<>();
		ObservableList<Stock> data = FXCollections.observableArrayList(database.getStock());

		// Set the overall padding and spacing for the page content
		VBox pageContent = new VBox(20); // Use spacing to separate elements vertically
		pageContent.setPadding(new Insets(20)); // Set padding around the entire content

		// Create the header text
		Text headerText = new Text("Inventory");
		headerText.setStyle("-fx-font-size: 24pt;"); // Set the header text size

		// Add the header text to the top of the page content
		pageContent.getChildren().add(headerText);

		// Clear existing columns before adding new ones
		table.getColumns().clear();

		TableColumn<Stock, String> barcodeCol = new TableColumn<>("Barcode");
		TableColumn<Stock, String> descriptionCol = new TableColumn<>("Description");
		TableColumn<Stock, Integer> quantityCol = new TableColumn<>("Quantity");
		TableColumn<Stock, Date> expireCol = new TableColumn<>("Expire Date");
		TableColumn<Stock, Date> arrivalCol = new TableColumn<>("Arrival Date");
		TableColumn<Stock, String> supplierNameCol = new TableColumn<>("Supplier");

		barcodeCol.setCellValueFactory(new PropertyValueFactory<Stock, String>("barcode"));
		descriptionCol.setCellValueFactory(new PropertyValueFactory<Stock, String>("description"));
		quantityCol.setCellValueFactory(new PropertyValueFactory<Stock, Integer>("quantity"));

		expireCol.setCellFactory(column -> {
			TableCell<Stock, Date> cell = new TableCell<Stock, Date>() {
				private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						this.setText(format.format(item));

					}
				}
			};

			return cell;
		});

		expireCol.setCellValueFactory(new PropertyValueFactory<Stock, Date>("expireDate"));
		arrivalCol.setCellFactory(column -> {
			TableCell<Stock, Date> cell = new TableCell<Stock, Date>() {
				private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						this.setText(format.format(item));

					}
				}
			};

			return cell;
		});

		arrivalCol.setCellValueFactory(new PropertyValueFactory<Stock, Date>("arrivalDate"));
		supplierNameCol.setCellValueFactory(new PropertyValueFactory<Stock, String>("supplierName"));

		table.setItems(data);
		table.getColumns().addAll(barcodeCol, descriptionCol, quantityCol, expireCol, arrivalCol, supplierNameCol);

		// Set the table to use the full width of the page content
		table.setItems(data);
		table.setPrefHeight(500);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Makes the columns to fill the space

		// Add the table to a container with padding to align with the header
		VBox tableContainer = new VBox();
		tableContainer.getChildren().add(table);

		// Add the table container to the page content
		pageContent.getChildren().add(tableContainer);

		// Return the complete page content
		return pageContent;
	}

	/**
	 * Create the page content for the products page
	 *
	 * @return Node containing the page content of the products page
	 */
	private Node createProductPageContent() {
		TableView<Product> productTable = new TableView<>();
		ObservableList<Product> productData = FXCollections.observableArrayList(database.getProducts());
		// Set the overall padding and spacing for the page content
		VBox pageContent = new VBox(20); // Use spacing to separate elements vertically
		pageContent.setPadding(new Insets(20)); // Set padding around the entire content

		// Create the header text
		Text headerText = new Text("Products");
		headerText.setStyle("-fx-font-size: 24pt;"); // Set the header text size

		// Add the header text to the top of the page content
		pageContent.getChildren().add(headerText);

		// Clear existing columns before adding new ones
		productTable.getColumns().clear();

		TableColumn<Product, String> barcodeCol = new TableColumn<>("Barcode");
		TableColumn<Product, String> nameCol = new TableColumn<>("Product Name");
		TableColumn<Product, String> brandCol = new TableColumn<>("Brand");
		TableColumn<Product, String> sizeCol = new TableColumn<>("Size");
		TableColumn<Product, String> suppliersCol = new TableColumn<>("Suppliers");

		barcodeCol.setCellValueFactory(new PropertyValueFactory<Product, String>("barcode"));
		nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
		brandCol.setCellValueFactory(new PropertyValueFactory<Product, String>("brand"));
		sizeCol.setCellValueFactory(new PropertyValueFactory<Product, String>("size"));
		suppliersCol.setCellValueFactory(new PropertyValueFactory<Product, String>("suppliersDescription"));

		productTable.setItems(productData);
		productTable.getColumns().addAll(barcodeCol, nameCol, brandCol, sizeCol, suppliersCol);

		// Set the table to use the full width of the page content
		productTable.setItems(productData);
		productTable.setPrefHeight(500);
		productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Makes the columns to fill the space

		// Add the table to a container with padding to align with the header
		VBox tableContainer = new VBox();
		tableContainer.getChildren().add(productTable);

		// Add the table container to the page content
		pageContent.getChildren().add(tableContainer);

		// Return the complete page content
		return pageContent;
	}

	
	/**
	 * Create the page content for the suppliers page
	 *
	 * @return Node containing the page content
	 */
	private Node createSuppliersPageContent() {
		TableView<Supplier> supplierTable = new TableView<>();
		ObservableList<Supplier> supplierData = FXCollections.observableArrayList(database.getSuppliers());
		// Set the overall padding and spacing for the page content
		VBox pageContent = new VBox(20); // Use spacing to separate elements vertically
		pageContent.setPadding(new Insets(20)); // Set padding around the entire content

		// Create the header text
		Text headerText = new Text("Suppliers");
		headerText.setStyle("-fx-font-size: 24pt;"); // Set the header text size

		// Add the header text to the top of the page content
		pageContent.getChildren().add(headerText);

		// Clear existing columns before adding new ones
		supplierTable.getColumns().clear();

		TableColumn<Supplier, String> nameCol = new TableColumn<>("Supplier Name");
		TableColumn<Supplier, String> emailCol = new TableColumn<>("Email Address");
		TableColumn<Supplier, String> phoneCol = new TableColumn<>("Phone Number");

		nameCol.setCellValueFactory(new PropertyValueFactory<Supplier, String>("supplierName"));
		emailCol.setCellValueFactory(new PropertyValueFactory<Supplier, String>("email"));
		phoneCol.setCellValueFactory(new PropertyValueFactory<Supplier, String>("phone"));

		supplierTable.setItems(supplierData);
		supplierTable.getColumns().addAll(nameCol, emailCol, phoneCol);

		// Set the table to use the full width of the page content
		supplierTable.setItems(supplierData);
		supplierTable.setPrefHeight(500);
		supplierTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Makes the columns to fill the space

		// Add the table to a container with padding to align with the header
		VBox tableContainer = new VBox();
		tableContainer.getChildren().add(supplierTable);

		// Add the table container to the page content
		pageContent.getChildren().add(tableContainer);

		// Return the complete page content
		return pageContent;
	}

	public static void main(String[] args) {
		launch();
	}
}