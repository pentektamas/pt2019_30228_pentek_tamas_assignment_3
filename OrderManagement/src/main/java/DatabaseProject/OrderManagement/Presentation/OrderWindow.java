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

public class OrderWindow extends JFrame {

	private JLabel create = new JLabel("Create a new order!");
	private JLabel client = new JLabel("Select the client: ");
	private JLabel product = new JLabel("Add new product(s): ");
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
	List<Integer> currentStock = new ArrayList<Integer>();
	String clientName;
	ProductOrder po = new ProductOrder();
	boolean stockFlag = false;

	public OrderWindow() {
		JPanel p = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();
		JPanel p6 = new JPanel();
		JPanel p7 = new JPanel();
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
		// p7.add(plus);
		p10.setLayout(layout);
		// p10.add(p7);

		p2.add(create);
		p3.add(client);
		p3.add(clients);
		p4.add(product);
		p4.add(plus);
		p1.add(p2);
		p1.add(p3);
		p1.add(p4);
		p5.add(finish);
		p.setLayout(new BorderLayout());
		p.add(p1, BorderLayout.PAGE_START);
		p.add(p10, BorderLayout.CENTER);
		p.add(p5, BorderLayout.PAGE_END);
		clients.setSelectedItem(null);
		products.setSelectedItem(null);
		this.setTitle("Order");
		// this.addProductsListener();
		this.addPlusListener();
		this.addFinishListener();
		this.pack();
		this.setContentPane(p);
		this.setSize(new Dimension(600, 400));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/*
	 * public void addProductsListener() { products.addActionListener(new
	 * ActionListener() {
	 * 
	 * public void actionPerformed(ActionEvent e) { for(int i=0;i<5;i++) { if
	 * (products.getSelectedItem() != null) { List<String> productNames2 = new
	 * ArrayList<String>(); ProductOrder po2 = new ProductOrder(); productNames2 =
	 * po2.getProductsName(); JComboBox products2 = new
	 * JComboBox(productNames2.toArray()); products2.setSelectedItem(null); JLabel
	 * quantity2 = new JLabel("         Quantity: "); JTextField quantityText2 = new
	 * JTextField(); quantityText2.setText("AVC"+i); //un LIST, ARRAYLIST DE TEXT,
	 * si ARRAYLIST DE JCOMBOBOX SELECTED ITEM JPanel pTemp = new JPanel();
	 * quantityText2.setPreferredSize(new Dimension(100, 20)); pTemp.add(products2);
	 * pTemp.add(quantity2); pTemp.add(quantityText2); p10.add(pTemp);
	 * p10.updateUI(); } } }
	 * 
	 * }); }
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
				// quantityText2.setText("AVC");
				// un LIST, ARRAYLIST DE TEXT, si ARRAYLIST DE JCOMBOBOX SELECTED ITEM
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

	public void dataCheck() {
		ProductOrder po2 = new ProductOrder();
		for (JComboBox j : comboboxes) {
			System.out.println(j.getSelectedItem() + " ITEM");
		}
		for (JTextField tf : textfields) {
			System.out.println(tf.getText() + " TEXT");
		}

		for (int i = 0; i < comboboxes.size(); i++) {
			int rez = po2.getStock((String) comboboxes.get(i).getSelectedItem());
			currentStock.add(rez);
			if (rez < Integer.parseInt(textfields.get(i).getText())) {
				OrderWindow.displayBadMessage(
						"Error! We have only " + rez + " '" + comboboxes.get(i).getSelectedItem() + "' on STOCK!");
				stockFlag = true;
			}
		}

		// int rez = po2.getStock((String) comboboxes.get(1).getSelectedItem());
		// System.out.println("REZ IS : " + rez);
	}

	public void addFinishListener() {
		// ProductOrder po=new ProductOrder();
		finish.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dataCheck();
				if (stockFlag == true)
					return;
				for (int i = 0; i < textfields.size(); i++) {
					int newValue = currentStock.get(i) - Integer.parseInt(textfields.get(i).getText());
					int rez = po.updateStock(newValue, (String) comboboxes.get(i).getSelectedItem());
					if (rez != 1)
						OrderWindow.displayBadMessage("UPDATE FAILED");
					System.out.println("NEW VALUE: " + newValue);

				}
				clientName = (String) clients.getSelectedItem();
				System.out.println("CLIENT: " + clientName);
			}

		});
	}

	public static void displayGoodMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Order Operation", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void displayBadMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Order Operation", JOptionPane.ERROR_MESSAGE);
	}
}
