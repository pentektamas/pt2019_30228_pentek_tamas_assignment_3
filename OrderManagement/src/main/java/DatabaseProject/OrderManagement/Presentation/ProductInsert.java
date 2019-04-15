package DatabaseProject.OrderManagement.Presentation;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DatabaseProject.OrderManagement.BusinessLayer.ClientOperation;
import DatabaseProject.OrderManagement.BusinessLayer.ProductOperation;
import DatabaseProject.OrderManagement.Model.Client;
import DatabaseProject.OrderManagement.Model.Product;

public class ProductInsert extends JPanel {
	private JLabel ID = new JLabel("ID Product:            ");
	private JLabel name = new JLabel("Name:                     ");
	private JLabel price = new JLabel("Price:                     ");
	private JLabel stock = new JLabel("Stock:                    ");
	private JTextField IDText = new JTextField();
	private JTextField nameText = new JTextField();
	private JTextField priceText = new JTextField();
	private JTextField stockText = new JTextField();
	private GridLayout layout = new GridLayout(0, 1);
	private JButton ok = new JButton("OK");

	public ProductInsert() {
		JPanel p0 = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		// JPanel p5 = new JPanel();
		// JPanel p6 = new JPanel();
		// JPanel p7 = new JPanel();
		JPanel p8 = new JPanel();
		IDText.setPreferredSize(new Dimension(200, 20));
		nameText.setPreferredSize(new Dimension(200, 20));
		priceText.setPreferredSize(new Dimension(200, 20));
		stockText.setPreferredSize(new Dimension(200, 20));
		p1.add(ID);
		p1.add(IDText);
		p2.add(name);
		p2.add(nameText);
		p3.add(price);
		p3.add(priceText);
		p4.add(stock);
		p4.add(stockText);
		p8.add(ok);
		JPanel p = new JPanel();
		this.setLayout(layout);
		this.add(p0);
		this.add(p1);
		this.add(p2);
		this.add(p3);
		this.add(p4);
//		this.add(p5);
//		this.add(p6);
//		this.add(p7);
		this.add(p8);
		this.addOKListener();
	}

	public void addOKListener() {
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductOperation productOp = new ProductOperation();
				Product product = new Product();
				try {
					product.setIDProduct(Integer.parseInt(IDText.getText()));
					product.setName(nameText.getText());
					product.setPrice(Integer.parseInt(priceText.getText()));
					product.setStock(Integer.parseInt(priceText.getText()));
					int rez = productOp.addProduct(product);
					if (rez == 1)
						ProductWindow.displayGoodMessage("New Product Inserted Successfully!");
					else
						ProductWindow.displayBadMessage("Something Went Wrong! Possibly duplicate values!");
				} catch (NumberFormatException ex) {
					ProductWindow.displayBadMessage("Please insert correct values!!!");
				}
			}
		});
	}
}
