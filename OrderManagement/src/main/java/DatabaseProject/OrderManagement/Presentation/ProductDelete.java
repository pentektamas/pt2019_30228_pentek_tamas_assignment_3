package DatabaseProject.OrderManagement.Presentation;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import DatabaseProject.OrderManagement.BusinessLayer.ProductOperation;

/**
 * 
 * @author Pentek Tamas
 * 
 *         Aceasta clasa extinde clasa JPanel, creeaza un panel care este
 *         folosit cand stergem produsele
 *
 */
public class ProductDelete extends JPanel {
	private JLabel condition = new JLabel("Insert the condition: ");
	private JTextField conditionText = new JTextField();
	private GridLayout layout = new GridLayout(0, 1);
	private JButton ok = new JButton("OK");

	public ProductDelete() {
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		conditionText.setPreferredSize(new Dimension(200, 20));
		p1.add(condition);
		p1.add(conditionText);
		p2.add(ok);
		this.setLayout(layout);
		this.add(p1);
		this.add(p2);
		this.addOKListener();
	}

	/**
	 * Metoda adauga un listener la butonul OK
	 */
	public void addOKListener() {
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductOperation productOp = new ProductOperation();
				try {
					String condition = conditionText.getText();
					int rez = productOp.deleteProduct(condition);
					if (rez != -1)
						MainWindow.displayGoodMessage("Product Operation",
								"Deleted Successfully " + rez + " Field(s)!");
					else
						MainWindow.displayBadMessage("Product Operation", "Something Went Wrong!");
				} catch (NumberFormatException ex) {
					MainWindow.displayBadMessage("Product Operation", "Please insert correct values!!!");
				}
			}
		});
	}
}
