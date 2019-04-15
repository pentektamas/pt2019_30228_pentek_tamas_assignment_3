package DatabaseProject.OrderManagement.BusinessLayer;

import java.util.ArrayList;
import java.util.List;

import DatabaseProject.OrderManagement.DataAccess.ClientDAC;
import DatabaseProject.OrderManagement.DataAccess.ProductDAC;
import DatabaseProject.OrderManagement.Model.Client;
import DatabaseProject.OrderManagement.Model.Product;

public class ProductOrder {

	private List<Client> clientsList;
	private List<Product> productsList;

	public ProductOrder() {
		clientsList = new ArrayList();
		productsList = new ArrayList();
	}

	public void getData() {
		ClientDAC clients = new ClientDAC();
		ProductDAC products = new ProductDAC();
		clientsList = clients.findAll();
		productsList = products.findAll();

	}

	public List<String> getClientsName() {
		List<String> clients = new ArrayList<String>();
		ClientDAC cDAC = new ClientDAC();
		clients = cDAC.getNames();
		return clients;
	}

	public List<String> getProductsName() {
		List<String> products = new ArrayList<String>();
		ProductDAC pDAC = new ProductDAC();
		products = pDAC.getNames();
		return products;
	}
	
	public int getStock(String name) {
		ProductDAC pDAC=new ProductDAC();
		int rez=pDAC.getStockNr(name);
		return rez;
	}

	public int updateStock(int newValue, String name) {
		ProductDAC pDAC= new ProductDAC();
		int rez=pDAC.stockUpdate(newValue, name);
		return rez;
	}
}
