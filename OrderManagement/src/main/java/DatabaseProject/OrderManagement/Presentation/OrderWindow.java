package DatabaseProject.OrderManagement.Presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import DatabaseProject.OrderManagement.BusinessLayer.ProductOrder;
import DatabaseProject.OrderManagement.DataAccess.OrderListDAC;

/**
 * 
 * @author Pentek Tamas
 * 
 *         Aceasta clasa extinde JFrame si reprezinta fereastra care deschide
 *         cand vrem sa facem o comanda
 *
 */
public class OrderWindow extends JFrame {

	private JLabel create = new JLabel("Create a new order!");
	private JLabel client = new JLabel("Select the client: ");
	private JLabel product = new JLabel("Add new product(s): ");
	private JLabel payment = new JLabel("Select the payment type: ");
	private JRadioButton cash = new JRadioButton("Cash");
	private JRadioButton card = new JRadioButton("Credit card");
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel quantity = new JLabel("                            Quantity:");
	private JLabel space = new JLabel("        ");
	private JButton plus = new JButton("+");
	private JTextField quantityText = new JTextField();
	private GridLayout layout = new GridLayout(0, 1);
	private JComboBox clients;
	private JComboBox products;
	private JButton finish = new JButton("Finish!");
	JPanel p10 = new JPanel();
	List<JTextField> textfields = new ArrayList<JTextField>();
	List<JComboBox> comboboxes = new ArrayList<JComboBox>();
	List<Integer> prices = new ArrayList<Integer>();
	List<Integer> currentStock = new ArrayList<Integer>();
	String clientName;
	ProductOrder po = new ProductOrder();
	// boolean stockFlag = false;

	public OrderWindow() {
		JPanel p = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();
		JPanel p6 = new JPanel();
		JPanel p7 = new JPanel();
		JPanel p8 = new JPanel();
		buttonGroup.add(card);
		buttonGroup.add(cash);
		GridLayout layout2 = new GridLayout(0, 1);
		quantityText.setPreferredSize(new Dimension(100, 20));
		p6.setLayout(layout2);
		p1.setLayout(layout);
		List<String> clientNames = new ArrayList<String>();
		List<String> productNames = new ArrayList<String>();
		ProductOrder po = new ProductOrder();
		clientNames = po.getClientsName();
		productNames = po.getProductsName();
		clients = new JComboBox(clientNames.toArray());
		products = new JComboBox(productNames.toArray());
		create.setFont(new Font("Arial", Font.BOLD, 22));
		p7.add(products);
		p7.add(quantity);
		p7.add(quantityText);
		p7.add(space);
		p10.setLayout(layout);
		p2.add(create);
		p3.add(client);
		p3.add(clients);
		p4.add(product);
		p4.add(plus);
		p8.add(payment);
		p8.add(card);
		p8.add(cash);
		p1.add(p2);
		p1.add(p3);
		p1.add(p4);
		p1.add(p8);
		p5.add(finish);
		p.setLayout(new BorderLayout());
		p.add(p1, BorderLayout.PAGE_START);
		p.add(p10, BorderLayout.CENTER);
		p.add(p5, BorderLayout.PAGE_END);
		clients.setSelectedItem(null);
		products.setSelectedItem(null);
		this.setTitle("Order");
		this.addPlusListener();
		this.addFinishListener();
		this.pack();
		this.setContentPane(p);
		this.setSize(new Dimension(600, 400));
		this.setLocation(500, 200);
	}

	/**
	 * Metoda adauga un listener la butonul +
	 */
	public void addPlusListener() {
		plus.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				List<String> productNames2 = new ArrayList<String>();
				ProductOrder po2 = new ProductOrder();
				productNames2 = po2.getProductsName();
				JComboBox products2 = new JComboBox(productNames2.toArray());
				products2.setSelectedItem(null);
				JLabel quantity2 = new JLabel("                  Quantity: ");
				JTextField quantityText2 = new JTextField();
				comboboxes.add(products2);
				textfields.add(quantityText2);
				JPanel pTemp = new JPanel();
				quantityText2.setPreferredSize(new Dimension(100, 20));
				pTemp.add(products2);
				pTemp.add(quantity2);
				pTemp.add(quantityText2);
				p10.add(pTemp);
				p10.updateUI();
			}
		});
	}

	/**
	 * Metoda verifica daca datele introduse au fost corecte, daca sunt corencte
	 * returneaza true, daca nu sunt corecte afiseaza un mesaj de erorare
	 * 
	 * @return true daca datele sunt corecte, altfel false
	 */
	public boolean dataCheck() {
		ProductOrder po2 = new ProductOrder();
		if (comboboxes.size() == 0) {
			MainWindow.displayBadMessage("Please introduce at least one product!");
			return true;
		}
		for (JComboBox j : comboboxes) {
			if (j.getSelectedItem() == null) {
				MainWindow.displayBadMessage("Please introduce a product!");
				return true;
			}
		}
		for (JTextField tf : textfields) {
			if (tf.getText() == null || tf.getText().equals("")) {
				MainWindow.displayBadMessage("Please introduce a quantity!");
				return true;
			}
		}

		for (int i = 0; i < comboboxes.size(); i++) {
			int rez = po2.getStock((String) comboboxes.get(i).getSelectedItem());
			currentStock.add(rez);
			if (rez < Integer.parseInt(textfields.get(i).getText())) {
				MainWindow.displayBadMessage(
						"Error! We have only " + rez + " '" + comboboxes.get(i).getSelectedItem() + "' on STOCK!");
				// stockFlag = true;
				return true;
			}
		}
		return false;
	}

	/**
	 * Metoda adauga un listener la butonul Finish
	 */
	public void addFinishListener() {
		finish.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (clients.getSelectedItem() == null) {
					MainWindow.displayBadMessage("Please select a CLIENT!");
					return;
				}
				boolean rezult = dataCheck();
				if (rezult == true)
					return;
				// if (stockFlag == true)
				// return;
				int orderId = po.generateIDOrder();
				clientName = (String) clients.getSelectedItem();
				int index = clientName.indexOf(' ');
				String firstName = clientName.substring(0, index);
				int IDClient = po.getIDClient(firstName);
				String payment = null;
				if (cash.isSelected())
					payment = cash.getText();
				if (card.isSelected())
					payment = card.getText();
				String date = po.getDate();
				if (payment == null) {
					MainWindow.displayBadMessage("Error! Payment type UNSELECTED!");
					return;

				}
				po.addOrder(orderId, IDClient, payment, date);
				for (int i = 0; i < textfields.size(); i++) {
					int newValue = currentStock.get(i) - Integer.parseInt(textfields.get(i).getText());
					int rez = po.updateStock(newValue, (String) comboboxes.get(i).getSelectedItem());
					if (rez != 1) {
						MainWindow.displayBadMessage("UPDATE FAILED");
						return;
					}
				}

				for (int j = 0; j < textfields.size(); j++) {
					OrderListDAC olDAC = new OrderListDAC();
					Integer ID = 0;
					Integer price = 0;
					ID = po.getID((String) comboboxes.get(j).getSelectedItem());
					price = po.getprice((String) comboboxes.get(j).getSelectedItem());
					prices.add(price);
					olDAC.insert(orderId, ID, price, Integer.parseInt(textfields.get(j).getText()));
				}

				po.createBill(clientName, comboboxes, textfields, prices, payment, orderId);
				MainWindow.displayGoodMessage("Order Completed Successfully!\n Bill Created!");
				prices.clear();
				currentStock.clear();
			}
		});
	}
}
