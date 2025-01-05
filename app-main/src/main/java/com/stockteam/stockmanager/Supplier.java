package com.stockteam.stockmanager;

/**
 * The Supplier class represents a supplier entity in the system. It contains
 * information about the supplier, such as their name, email, and phone number.
 */
public class Supplier {

	private String supplierName;
	private String email;
	private String phone;

	/**
	 * Creates a new Supplier with the specified details.
	 *
	 * @param supplierName The name of the supplier.
	 * @param email        The email address of the supplier.
	 * @param phone        The phone number of the supplier.
	 */
	public Supplier(String supplierName, String email, String phone) {
		this.supplierName = supplierName;
		this.email = email;
		this.phone = phone;
	}

	/**
	 * Returns the name of the supplier.
	 *
	 * @return The name of the supplier.
	 */
	public String getSupplierName() {
		return supplierName;
	}

	/**
	 * Returns the email address of the supplier.
	 *
	 * @return The email address of the supplier.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address of the supplier.
	 *
	 * @param email The new email address for the supplier.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the phone number of the supplier.
	 *
	 * @return The phone number of the supplier.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone number of the supplier.
	 *
	 * @param phone The new phone number for the supplier.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
