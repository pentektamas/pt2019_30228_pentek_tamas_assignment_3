package DatabaseProject.OrderManagement.BusinessLayer;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.awt.TextField;
import java.io.*;

import DatabaseProject.OrderManagement.DataAccess.ClientDAC;
import DatabaseProject.OrderManagement.DataAccess.CompanyOrderDAC;
import DatabaseProject.OrderManagement.DataAccess.ProductDAC;
import DatabaseProject.OrderManagement.Model.Client;
import DatabaseProject.OrderManagement.Model.Product;

public class ProductOrder {

	private List<Client> clientsList;
	private List<Product> productsList;
	private static int nrBill = 1;
	private static int OrderID = 100;

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
		ProductDAC pDAC = new ProductDAC();
		int rez = pDAC.getStockNr(name);
		return rez;
	}

	public int updateStock(int newValue, String name) {
		ProductDAC pDAC = new ProductDAC();
		int rez = pDAC.stockUpdate(newValue, name);
		return rez;
	}

	public void createBill(String name, List<JComboBox> names, List<JTextField> textfields,List<Integer> prices) {
		Writer writer = null;
		String text = ("\t\t\tBILL\n\nOrderManagement Company\t\t\t" + name
				+ "\nWashington DC, Broadway 12\t\tClient's Address\n\n");
		try {
			writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream("bill" + nrBill++ + ".txt"), "utf-8"));
			writer.write(text+"\n");
			writer.write("Product name\t\tQuantity\tPrice\tTotal\n\n");
			for (int i = 0; i < names.size(); i++) {
				writer.write((String) names.get(i).getSelectedItem());
				String x=(String) names.get(i).getSelectedItem();
				if(names.get(i).getSelectedItem().toString().length()<7)
					writer.write("\t\t\t   ");
				else
				{
					if(names.get(i).getSelectedItem().toString().length()>15)
						writer.write("\t   ");
					else
						writer.write("\t\t   ");
				}
				writer.write(textfields.get(i).getText());
				writer.write("\t\t ");
				writer.write(prices.get(i)+"");
				writer.write("\t ");
				writer.write(Integer.parseInt(textfields.get(i).getText())*prices.get(i)+"");
				writer.write("\n\n");
				System.out.println("LENGT: "+names.get(i).getSelectedItem().toString().length());
			}
		} catch (IOException ex) {
			System.out.println("IO EXCEPTION - OPEN!");
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				System.out.println("IO EXCEPTION - CLOSE!");
				e.printStackTrace();
			}
		}
	}

	public int generateIDOrder() {
		List<Integer> IDs = new ArrayList<Integer>();
		CompanyOrderDAC coDAC = new CompanyOrderDAC();
		int i = 0;
		IDs = coDAC.getIDOrder();
		while (i < 5) {
			for (Integer integer : IDs) {
				if (integer == OrderID) {
					OrderID++;
				}
			}
			i++;
		}
		return OrderID;
	}

	public int getID(String name) {
		ProductDAC pDAC = new ProductDAC();
		int ID = pDAC.getByNamesID(name);
		return ID;
	}

	public int getprice(String name) {
		ProductDAC pDAC = new ProductDAC();
		int price = pDAC.getByNamesPrice(name);
		return price;
	}

	public void addOrder(int ID, int IDClient, String payment_type, String date) {
		CompanyOrderDAC coDAC = new CompanyOrderDAC();
		coDAC.insert(ID, IDClient, payment_type, date);
	}

	public int getIDClient(String name) {
		ClientDAC cDAC = new ClientDAC();
		int rez = cDAC.getClientID(name);
		return rez;
	}

}
