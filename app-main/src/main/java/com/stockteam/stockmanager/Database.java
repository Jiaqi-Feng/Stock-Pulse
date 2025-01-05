package com.stockteam.stockmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Database {

    List<Supplier> suppliers = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    List<Stock> stock = new ArrayList<>();
    String suppliersFile;
    String productsFile;
    String stockFile;

    /**
     * Database constructor
     *
     * @param suppliersFile
     * @param productsFile
     * @param stockFile
     */
    public Database(String suppliersFile, String productsFile, String stockFile) {
        this.suppliersFile = suppliersFile;
        this.productsFile = productsFile;
        this.stockFile = stockFile;
        this.loadSuppliers();
        this.loadProducts();
        this.loadStock();
    }

    /**
     * Add a supplier to the database
     *
     * @param supplier
     */
    public void addSupplier(Supplier supplier) {
        suppliers.add(supplier);

    }

    /**
     * Add a product to the database
     *
     * @param product
     */
    public void addProduct(Product product) {
        products.add(product);
    }

    /**
     * Add a stock to the database
     *
     * @param s
     */
    public void addStock(Stock s) {
        stock.add(s);
    }

    public void deleteSupplier(Supplier supplier) {

    }

    public void deleteProduct(Product product) {

    }

    public void deleteStock(Stock stock) {

    }

    /**
     * Get all the suppliers' names
     *
     * @return List<String> of all the suppliers' names
     */
    public List<String> allProductsSkus() {
        List<String> results = new ArrayList<>();
        for (Product p : products) {
            results.add(p.getBarcode());
        }
        return results;
    }

    /**
     * Get all the suppliers' names
     *
     * @return List<String> of all the suppliers' names
     */
    public List<String> allSuppliersNames() {
        List<String> results = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            results.add(supplier.getSupplierName());
        }
        return results;
    }

    /**
     * Get all the suppliers in the database
     *
     * @return List<Supplier> of all the suppliers
     */
    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    /**
     * Get all the products in the database
     *
     * @return List<Product> of all the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Get all the stock in the database
     *
     * @return List<Stock> of all the stock
     */
    public List<Stock> getStock() {
        return stock;
    }

    /**
     * Get a supplier by name
     *
     * @param supplierName
     * @return Supplier with the given name
     */
    public Supplier getSupplier(String supplierName) {
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierName().equals(supplierName)) {
                return supplier;
            }
        }
        return null;
    }

    /**
     * Get a supplier by name if they exist
     *
     * @param supplierName
     * @return Product with the given barcode
     */
    public Supplier supplierExist(String supplierName) {

        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierName().equals(supplierName)) {
                return supplier;
            }
        }

        return null;
    }

    /**
     * Get a product by barcode if it exists
     *
     * @param barcode
     * @return Product with the given barcode
     */
    public Product productExist(String barcode) {
        for (Product product : products) {
            if (product.getBarcode().equals(barcode)) {
                return product;
            }
        }
        return null;
    }

    /**
     * Load all the suppliers from the database
     */
    public void loadSuppliers() {
//		String fileName = "suppliersData.txt";
        try {
            Scanner scanner = new Scanner(new File(suppliersFile));
            while (scanner.hasNext()) {
                String companyName = scanner.nextLine().trim(); // .trim() removes whitespace from the ends
                String emailAddress = scanner.nextLine().trim();// of strings - takes the "\n" away that
                if (emailAddress.equals("null")) {
                    emailAddress = null;
                }
                String phoneNumber = scanner.nextLine().trim(); // .nextLine() left on the end.
                if (phoneNumber.equals("null")) {
                    phoneNumber = null;
                }
                Supplier supplier = new Supplier(companyName, emailAddress, phoneNumber);
                this.suppliers.add(supplier);
            }
            scanner.close();
        } catch (
                FileNotFoundException e) {
        }
    }

    /**
     * Save all the suppliers to the database
     */
    public void saveSuppliers() {
        try {
//			String supplierFileName = "suppliersData.txt";
            PrintStream suppliersPS = new PrintStream(new File(suppliersFile));
            for (Supplier s : suppliers) {
                String companyName = s.getSupplierName();
                String emailAddress = s.getEmail();
                if (emailAddress == null) {
                    emailAddress = "null";
                } else {
                    if (emailAddress.equals("")) {
                        emailAddress = "null";
                    }
                }
                String phoneNumber = s.getPhone();
                if (phoneNumber == null) {
                    phoneNumber = "null";
                } else {
                    if (phoneNumber.equals("")) {
                        phoneNumber = "null";
                    }
                }
                suppliersPS.println(companyName);
                suppliersPS.println(emailAddress);
                suppliersPS.println(phoneNumber);
            }

            suppliersPS.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Load all the products from the database
     */
    public void loadProducts() {
//		String fileName = "productsData.txt";
        try {
            Scanner scanner = new Scanner(new File(productsFile));
            while (scanner.hasNext()) {
                String barcode = scanner.nextLine().trim(); // .trim() removes whitespace from the ends
                String productName = scanner.nextLine().trim();// of strings - takes the "\n" away that
                if (productName.equals("null")) {
                    productName = "";
                }
                String brand = scanner.nextLine().trim(); // .nextLine() left on the end.
                if (brand.equals("null")) {
                    brand = "";
                }
                String size = scanner.nextLine().trim();
                if (size.equals("null")) {
                    size = "";
                }
                Product product = new Product(barcode, productName, brand, size);
                this.products.add(product);

                int supplierNumber = scanner.nextInt();
                scanner.nextLine();
                List<String> thisSuppliers = new ArrayList<>();
                for (int i = 0; i < supplierNumber; i++) {
                    String supplierName = scanner.nextLine().trim();
                    thisSuppliers.add(supplierName);
                }
                product.setSuppliers(thisSuppliers);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    /**
     * Save all the products to the database
     */
    public void saveProducts() {
        try {
//			String productFileName = "productsData.txt";
            PrintStream productPS = new PrintStream(new File(productsFile));
            for (Product p : products) {
                String barcode = p.getBarcode();
                String productName = p.getProductName();
                if (productName.equals("")) {
                    productName = "null";
                }
                String brand = p.getBrand();
                if (brand.equals("")) {
                    brand = "null";
                }
                String size = p.getSize();
                if (size.equals("")) {
                    size = "null";
                }
                int supplierSize = p.getSuppliers().size();
                productPS.println(barcode);
                productPS.println(productName);
                productPS.println(brand);
                productPS.println(size);
                productPS.println(supplierSize);
                for (int i = 0; i < supplierSize; i++) {
                    productPS.println(p.getSuppliers().get(i));
                }
            }

            productPS.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Load all the stock from the database
     */
    public void loadStock() {
//		String fileName = "stockData.txt";
        try {
            Scanner scanner = new Scanner(new File(stockFile));
            while (scanner.hasNext()) {
                String barcode = scanner.nextLine().trim(); // .trim() removes whitespace from the ends
                String description = scanner.nextLine();
                int quantity = scanner.nextInt();
                scanner.nextLine();
                String expireDateString = scanner.nextLine().trim();
                String arrivalDateString = scanner.nextLine().trim();
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                Date expireDate = null;
                try {
                    expireDate = df.parse(expireDateString);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Date arrivalDate = null;
                try {
                    arrivalDate = df.parse(arrivalDateString);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String supplierName = scanner.nextLine().trim();// of strings - takes the "\n" away that

                Stock stock = new Stock(barcode, description, quantity, expireDate, arrivalDate, supplierName);
                this.stock.add(stock);
            }
            scanner.close();
        } catch (
                FileNotFoundException e) {
        }
    }

    /**
     * Save all the stock to the database
     */
    public void saveStock() {
        try {
//			String stockFileName = "stockData.txt";
            PrintStream stockPS = new PrintStream(new File(stockFile));
            for (Stock s : stock) {
                String barcode = s.getBarcode();
                String description = s.getDescription();
                int quantity = s.getQuantity();
                String expireDateString = s.getExpireDateString();
                String arrivalDateString = s.getArrivalDateString();
                String supplierName = s.getSupplierName();
                stockPS.println(barcode);
                stockPS.println(description);
                stockPS.println(quantity);
                stockPS.println(expireDateString);
                stockPS.println(arrivalDateString);
                stockPS.println(supplierName);
            }
            stockPS.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
