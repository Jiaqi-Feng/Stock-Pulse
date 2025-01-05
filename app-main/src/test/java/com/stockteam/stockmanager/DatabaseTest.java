package com.stockteam.stockmanager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import com.stockteam.stockmanager.Database;

import javafx.scene.chart.PieChart.Data;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DatabaseTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
//		List<Product> products = new ArrayList<>();
//		products.add(new Product("AA123","Cola","CocaCola","250ml"));
//		products.add(new Product("BB456","A2 Milk","Fonterra","1L"));
//		products.add(new Product("CC789","Chocolate Coated Almonds Caramel","Donovans","150g"));
//		products.add(new Product("DD101112","Peanut Butter Crunchy","Pics","380g"));
//		products.add(new Product("EE131415","Chips Salted","Snacka Changi","150g"));
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void testProductExist() {
		
////		Database db = new Database();
//		
//		assertNotNull(db.productExist("AA123"));
	}

}
