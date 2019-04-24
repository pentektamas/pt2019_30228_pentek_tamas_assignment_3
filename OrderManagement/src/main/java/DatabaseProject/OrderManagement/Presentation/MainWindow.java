package DatabaseProject.OrderManagement.Presentation;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import DatabaseProject.OrderManagement.DataAccess.ClientDAC;
import DatabaseProject.OrderManagement.DataAccess.ProductDAC;
import DatabaseProject.OrderManagement.Model.Client;
import DatabaseProject.OrderManagement.Model.Product;

/**
 * 
 * @author Pentek Tamas
 * 
 *         Aceasta clasa extinde JFrame si reprezinta fereastra care deschide
 *         cand pornim aplicatia, aceasta reprezinta fereastra principala
 *
 */
public class MainWindow extends JFrame {

	ClientWindow cw = new ClientWindow();
	ProductWindow pw = new ProductWindow();
	OrderWindow ow = new OrderWindow();
	private JButton client = new JButton("Client Operations");
	private JButton product = new JButton("Product Operations");
	private JButton order = new JButton("Create an order");

	public MainWindow() {
		JPanel p = new JPanel();

		p.add(client);
		p.add(product);
		p.add(order);
		this.addClientButtonListener();
		this.addProductButtonListener();
		this.addOrderButtonListener();
		this.setTitle("Order Management Company");
		this.pack();
		this.setContentPane(p);
		this.setSize(new Dimension(600, 150));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(500, 200);

	}

	/**
	 * Metoda adauga un listener la butonul Client Operations
	 */
	public void addClientButtonListener() {
		client.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cw.setVisible(true);
				pw.setVisible(false);
				ow.setVisible(false);

			}
		});
	}

	/**
	 * Metoda adauga un listener la butonul Product Operations
	 */
	public void addProductButtonListener() {
		product.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cw.setVisible(false);
				pw.setVisible(true);
				ow.setVisible(false);
			}
		});
	}

	/**
	 * Metoda adauga un listener la butonul Create an order
	 */
	public void addOrderButtonListener() {
		order.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cw.setVisible(false);
				pw.setVisible(false);
				ow.setVisible(true);
			}
		});
	}

	/**
	 * Metoda creeaza un tabel din date primite ca si argument
	 * 
	 * @param values este o lista care contine datele tabelului
	 * @return tabelul creat
	 */
	public static <T> JTable createTable(List<T> values) {
		List<String> headers = new ArrayList<String>();
		List<String> ok = new ArrayList<String>();
		Object[][] object = null;
		Object[] columnNames = null;
		Object obj = values.get(0);
		if (obj instanceof Client) {
			ClientDAC cDAC = new ClientDAC();
			headers = cDAC.retrieveProperties();
			columnNames = headers.toArray();
			object = new Object[values.size()][headers.size()];
			for (int i = 0; i < values.size(); i++) {
				ok = cDAC.retrievePropertiesValues((Client) values.get(i));
				for (int j = 0; j < headers.size(); j++) {
					object[i][j] = ok.get(j);
				}
			}
		}

		if (obj instanceof Product) {
			ProductDAC pDAC = new ProductDAC();
			headers = pDAC.retrieveProperties();
			columnNames = headers.toArray();
			object = new Object[values.size()][headers.size()];
			for (int i = 0; i < values.size(); i++) {
				ok = pDAC.retrievePropertiesValues((Product) values.get(i));
				for (int j = 0; j < headers.size(); j++) {
					object[i][j] = ok.get(j);
				}
			}
		}
		JTable table = new JTable(object, columnNames);
		return table;
	}

	/**
	 * Metoda afiseaza un JOptionPane de informare cu mesajul dorit
	 * 
	 * @param type    este tipul operatiei
	 * @param message este mesajul care va fi afisat
	 */
	public static void displayGoodMessage(String type, String message) {
		JOptionPane.showMessageDialog(null, message, type, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Metoda afiseaza un JOptionPane de eroare cu mesajul dorit
	 * 
	 * @param type    este tipul operatiei
	 * @param message este mesajul care va fi afisat
	 */
	public static void displayBadMessage(String type, String message) {
		JOptionPane.showMessageDialog(null, message, type, JOptionPane.ERROR_MESSAGE);
	}
}
