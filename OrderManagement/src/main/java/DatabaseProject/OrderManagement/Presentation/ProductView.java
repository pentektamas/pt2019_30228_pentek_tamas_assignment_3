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
	List<Product> products = new ArrayList<Product>();
	JButton refresh = new JButton("Refresh");
	JScrollPane scrollPane;
	JPanel p = new JPanel();
	JPanel p1 = new JPanel();

	public ProductView() {
		p1.add(refresh);
		products = pop.viewAllProducts();
		productsTable = MainWindow.createTable(products);
		scrollPane = new JScrollPane(productsTable);
		scrollPane.setPreferredSize(new Dimension(580, 400));
		p.add(scrollPane);
		this.add(p1, BorderLayout.PAGE_START);
		this.add(p, BorderLayout.CENTER);
		this.addRefreshListener();

	}

	/**
	 * Metoda adauga un listener la butonul Refresh
	 */
	public void addRefreshListener() {
		refresh.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				p.removeAll();
				products = pop.viewAllProducts();
				productsTable = MainWindow.createTable(products);
				scrollPane = new JScrollPane(productsTable);
				scrollPane.setPreferredSize(new Dimension(580, 400));
				p.add(scrollPane);
				p.updateUI();
			}
		});
	}
}
