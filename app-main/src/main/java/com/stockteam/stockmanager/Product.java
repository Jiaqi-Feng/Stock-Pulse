package com.stockteam.stockmanager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Product {

	private String barcode;
	private String productName;
	private String brand;
	private String size;
	private List<String> suppliers = new ArrayList<>();
	
	public Product(String barcode, String productName, String brand, String size) {
	
		this.barcode = barcode;
		this.productName = productName;
		this.brand = brand;
		this.size = size;
		
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	

	public String getBarcode() {
		return barcode;
	}
	
	

	public List<String> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(List<String> suppliers) {
		this.suppliers = suppliers;
	}
	
	public String productDescription() {
		return brand + " " + productName + " " + size;
	}
	
	public String getSuppliersDescription() {
		
		return "" + suppliers;
	}

	
}
