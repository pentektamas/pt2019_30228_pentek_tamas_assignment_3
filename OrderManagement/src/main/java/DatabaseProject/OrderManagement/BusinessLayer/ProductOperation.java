package DatabaseProject.OrderManagement.BusinessLayer;

import java.util.ArrayList;
import java.util.List;

import DatabaseProject.OrderManagement.DataAccess.ClientDAC;
import DatabaseProject.OrderManagement.DataAccess.ProductDAC;
import DatabaseProject.OrderManagement.Model.Client;
import DatabaseProject.OrderManagement.Model.Product;

public class ProductOperation {

	private Product product;
	private ProductDAC productData;

	public ProductOperation() {
		product = new Product();
		productData = new ProductDAC();
	}

	public int addProduct(Product p) {
		boolean ok = productData.insert(p.getIDProduct(), p.getName(), p.getPrice(), p.getStock());
		if (ok)
			return 1;
		else
			return 0;
	}

	public int editProduct(String what, String value, String where) {
		int rez = productData.update(what, value, where);
		if (rez != -1)
			return rez;
		else
			return -1;
	}

	public int deleteProduct(String where) {
		int rez = productData.delete(where);
		if (rez != -1)
			return rez;
		else
			return -1;
	}

	public List<Product> viewAllProducts() {
		List<Product> products = new ArrayList<Product>();
		products = productData.findAll();
		return products;
	}

	public List<String> getFieldsName() {
		List<String> fields = new ArrayList<String>();
		fields = productData.retrieveProperties();
		return fields;
	}

	public Product getFieldsValues(Product product) {
		Product value = new Product();
		value = productData.retrievePropertiesValues(product);
		return value;

	}
}
