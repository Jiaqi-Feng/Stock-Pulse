package com.stockteam.stockmanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Stock {

	private String barcode;
	private String description;
	private int quantity;
	private Date expireDate;
	private Date arrivalDate;
	private String supplierName;
	

	public Stock(String barcode, String description, int quantity, Date expireDate, Date arrivalDate, String supplier) {
		this.barcode = barcode;
		this.description = description;
		this.quantity = quantity;
		this.expireDate = expireDate;
		this.arrivalDate = arrivalDate;
		this.supplierName = supplier;
	}

	public String getBarcode() {
		return barcode;
	}

	public int getQuantity() {
		return quantity;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public String getDescription() {
		return description;
	}
	
	public String getExpireDateString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(expireDate);
	}
	
	public String getArrivalDateString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(arrivalDate);
	}
	
	

}
