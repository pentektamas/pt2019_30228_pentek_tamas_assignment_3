package DatabaseProject.OrderManagement.Presentation;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DatabaseProject.OrderManagement.BusinessLayer.ProductOperation;

public class ProductEdit extends JPanel {
	private JLabel column = new JLabel("Select a column: ");
	String[] columnNames = new String[] { "IDProduct", "Name", "Price", "Stock" };
	private JComboBox columns = new JComboBox(columnNames);
	private JLabel value = new JLabel("Insert the value:       ");
	private JTextField valueText = new JTextField();
	private JLabel condition = new JLabel("Insert the condition: ");
	private JTextField conditionText = new JTextField();
	private GridLayout layout = new GridLayout(0, 1);
	private JButton ok = new JButton("OK");

	public ProductEdit() {
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();

		p1.add(column);
		p1.add(columns);
		p2.add(value);
		p2.add(valueText);
		p3.add(condition);
		p3.add(conditionText);
		p4.add(ok);
		valueText.setPreferredSize(new Dimension(200, 20));
		conditionText.setPreferredSize(new Dimension(200, 20));
		this.setLayout(layout);
		this.add(p1);
		this.add(p2);
		this.add(p3);
		this.add(p4);
		this.addOKListener();
	}

	public void addOKListener() {
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductOperation productOp = new ProductOperation();
				try {
					String column = (String) columns.getSelectedItem();
					String value = valueText.getText();
					String condition = conditionText.getText();
					int rez = productOp.editProduct(column, value, condition);
					if (rez != -1)
						ClientWindow.displayGoodMessage("Updated Successfully " + rez + " Field(s)!");
					else
						ClientWindow.displayBadMessage("Something Went Wrong!");
				} catch (NumberFormatException ex) {
					ClientWindow.displayBadMessage("Please insert correct values!!!");
				}
			}
		});
	}
}
