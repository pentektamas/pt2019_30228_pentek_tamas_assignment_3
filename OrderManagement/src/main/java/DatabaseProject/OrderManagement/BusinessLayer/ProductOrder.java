package DatabaseProject.OrderManagement.BusinessLayer;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.io.*;
import DatabaseProject.OrderManagement.DataAccess.ClientDAC;
import DatabaseProject.OrderManagement.DataAccess.CompanyOrderDAC;
import DatabaseProject.OrderManagement.DataAccess.ProductDAC;
import DatabaseProject.OrderManagement.Model.Client;
import DatabaseProject.OrderManagement.Model.Product;

/**
 * @author Pentek Tamas
 * 
 *         Clasa contine metode folosite pentru gestionarea datelor din
 *         tabelelor Companyorder, Orderlist si efectuarea unei comenzi
 */
public class ProductOrder {

	private List<Client> clientsList;
	private List<Product> productsList;
	private static int OrderID = 100;

	public ProductOrder() {
		clientsList = new ArrayList<Client>();
		productsList = new ArrayList<Product>();
	}

	/**
	 * Metoda salveaza continutul tabelelor Client si Product in doua liste separate
	 */
	public void getData() {
		ClientDAC clients = new ClientDAC();
		ProductDAC products = new ProductDAC();
		clientsList = clients.findAll();
		productsList = products.findAll();

	}

	/**
	 * Metoda salveaza intr-o lista numele clientilor
	 * 
	 * @return Stringul cu numele clientilor
	 */
	public List<String> getClientsName() {
		List<String> clients = new ArrayList<String>();
		ClientDAC cDAC = new ClientDAC();
		clients = cDAC.getNames();
		return clients;
	}

	/**
	 * Metoda salveaza intr-o lista numele produselor
	 * 
	 * @return Stringul cu numele produselor
	 */
	public List<String> getProductsName() {
		List<String> products = new ArrayList<String>();
		ProductDAC pDAC = new ProductDAC();
		products = pDAC.getNames();
		return products;
	}

	/**
	 * Metoda cauta numarul de produse dupa numele produsului
	 * 
	 * @param name este numele produsului
	 * @return numarul de produse
	 */
	public int getStock(String name) {
		ProductDAC pDAC = new ProductDAC();
		int rez = pDAC.getStockNr(name);
		return rez;
	}

	/**
	 * Metoda face update la numarul de produse dupa o comanda
	 *
	 * @param newValue este numarul de produse dupa comanda
	 * @param name     este numele produsului
	 * @return numarul randurilor in care s-a facut update
	 */
	public int updateStock(int newValue, String name) {
		ProductDAC pDAC = new ProductDAC();
		int rez = pDAC.stockUpdate(newValue, name);
		return rez;
	}

	/**
	 * Metoda salveaza data comenzii din tabelul Companyorder
	 * 
	 * @return data comenzii
	 */
	public String getDate() {
		CompanyOrderDAC coDAC = new CompanyOrderDAC();
		String rez = coDAC.getDate();
		return rez;
	}

	/**
	 * Metoda creeaza o factura dupa o comanda facuta
	 * 
	 * @param name       este numele clientului
	 * @param names      este o lista cu nume de produse comandate
	 * @param textfields este o lista cu cantitati de produse comandate
	 * @param prices     este o lista cu preturi de produse comandate
	 * @param payment    este tipul de plata utilizat
	 * @param OrderID    este ID-ul comenzii
	 */
	public void createBill(String name, List<JComboBox> names, List<JTextField> textfields, List<Integer> prices,
			String payment, int OrderID) {
		Writer writer = null;
		Integer totalAmount = 0;
		String text = ("\t\t\tBILL\n\nOrderManagement Company\t\t\t" + name
				+ "\nWashington DC, Broadway 12\t\tClient's Address\n\n");
		try {
			writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream("bill" + OrderID + ".txt"), "utf-8"));
			writer.write(text + "\n");
			writer.write("Product name\t\tQuantity\tPrice\tTotal\n\n");
			for (int i = 0; i < names.size(); i++) {
				writer.write((String) names.get(i).getSelectedItem());
				String x = (String) names.get(i).getSelectedItem();
				if (names.get(i).getSelectedItem().toString().length() < 7)
					writer.write("\t\t\t   ");
				else {
					if (names.get(i).getSelectedItem().toString().length() > 15)
						writer.write("\t   ");
					else
						writer.write("\t\t   ");
				}
				writer.write(textfields.get(i).getText());
				writer.write("\t\t ");
				writer.write(prices.get(i) + "");
				writer.write("\t ");
				writer.write(Integer.parseInt(textfields.get(i).getText()) * prices.get(i) + "");
				totalAmount = totalAmount + Integer.parseInt(textfields.get(i).getText()) * prices.get(i);
				writer.write("\n\n");
			}
			writer.write("\n\n");
			writer.write("\t\t\t\t");
			writer.write("Paid with: " + payment);
			writer.write("\n\n");
			writer.write("\t\t\t\t");
			writer.write("Total amount: " + totalAmount + " RON");
		} catch (IOException ex) {
			System.out.println("IO EXCEPTION - OPEN!");
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				System.out.println("IO EXCEPTION - CLOSE!");
			}
		}
	}

	/**
	 * Metoda genereaza ID-ul de comanda
	 * 
	 * @return ID de comanda
	 */
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

	/**
	 * Metoda cauta ID-ul produsului dupa nume de produs
	 * 
	 * @param name este numele produsului
	 * @return ID de produs
	 */
	public int getID(String name) {
		ProductDAC pDAC = new ProductDAC();
		int ID = pDAC.getByNamesID(name);
		return ID;
	}

	/**
	 * Metoda cauta pretul produsului dupa nume de produs
	 * 
	 * @param name este numele produsului
	 * @return pretul produsului
	 */
	public int getprice(String name) {
		ProductDAC pDAC = new ProductDAC();
		int price = pDAC.getByNamesPrice(name);
		return price;
	}

	/**
	 * Metoda cauta ID-ul clientului dupa numele clientului
	 * 
	 * @param name este numele clientului
	 * @return ID de client
	 */
	public int getIDClient(String name) {
		ClientDAC cDAC = new ClientDAC();
		int rez = cDAC.getClientID(name);
		return rez;
	}

	/**
	 * Metoda insereaza o comanda noua in tabelul Companyorder
	 * 
	 * @param ID           este ID-ul comenzii
	 * @param IDClient     este ID de client
	 * @param payment_type este tipul de plata
	 * @param date         este data comenzii
	 */
	public void addOrder(int ID, int IDClient, String payment_type, String date) {
		CompanyOrderDAC coDAC = new CompanyOrderDAC();
		coDAC.insert(ID, IDClient, payment_type, date);
	}
}
