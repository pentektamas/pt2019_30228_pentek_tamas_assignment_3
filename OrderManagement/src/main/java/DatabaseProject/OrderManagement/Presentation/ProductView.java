package DatabaseProject.OrderManagement.Presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import DatabaseProject.OrderManagement.BusinessLayer.ProductOperation;
import DatabaseProject.OrderManagement.Model.Product;

/**
 * 
 * @author Pentek Tamas
 * 
 *         Aceasta clasa extinde clasa JPanel, creeaza un panel care este
 *         folosit cand afisam tabelul Product
 *
 */
public class ProductView extends JPanel {
	private JTable productsTable = new JTable();
	ProductOperation pop = new ProductOperation();
	JButton refresh = new JButton("Refresh");
	JScrollPane scrollPane;
	JPanel p = new JPanel();
	JPanel p1 = new JPanel();
	List<String> headers;

	public ProductView() {
		p1.add(refresh);
		headers = new ArrayList<String>();
		headers = pop.getFieldsName();
		productsTable = this.createTable(headers);
		p.add(scrollPane);
		this.add(p1, BorderLayout.PAGE_START);
		this.add(p, BorderLayout.CENTER);
		this.addRefreshListener();

	}

	/**
	 * LFHALFKJGJE:JGJEAOGJ !!!!!!!!!!!!!!!!!!!!!!!!!!!!???????????????????????????
	 * 
	 * @param headers
	 * @return
	 */
	public JTable createTable(List<String> headers) {
		List<Product> products = pop.viewAllProducts();
		Object[] columnNames = headers.toArray();

		Object[][] object = new Object[products.size()][headers.size()];
		int i = 0;
		for (Product p : products) {
			int j = 0;
			object[i][j++] = p.getIDProduct();
			object[i][j++] = p.getName();
			object[i][j++] = p.getPrice();
			object[i][j++] = p.getStock();
			i++;
		}
		JTable table = new JTable(object, columnNames);
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(580, 400));
		return table;
	}

	/**
	 * Metoda adauga un listener la butonul Refresh
	 */
	public void addRefreshListener() {
		refresh.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				p.removeAll();
				productsTable = createTable(headers);
				p.add(scrollPane);
				p.updateUI();
			}
		});
	}
}
