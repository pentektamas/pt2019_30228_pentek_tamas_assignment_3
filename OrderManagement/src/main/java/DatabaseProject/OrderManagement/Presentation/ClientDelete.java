package DatabaseProject.OrderManagement.Presentation;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import DatabaseProject.OrderManagement.BusinessLayer.ClientOperation;
import DatabaseProject.OrderManagement.DataAccess.ClientDAC;
import DatabaseProject.OrderManagement.Model.Client;

public class ClientDelete extends JPanel {

	private JLabel condition = new JLabel("Insert the condition: ");
	private JTextField conditionText = new JTextField();
	private GridLayout layout = new GridLayout(0, 1);
	private JButton ok = new JButton("OK");

	public ClientDelete() {
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

	public void addOKListener() {
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientOperation clientOp = new ClientOperation();
				ClientDAC cd = new ClientDAC();
				Client c = new Client();
				c.setIDClient(123);
				cd.retrieveProperties();
				cd.retrievePropertiesValues(c);
				try {
					String condition = conditionText.getText();
					int rez = clientOp.deleteClient(condition);
					if (rez != -1)
						ClientWindow.displayGoodMessage("Deleted Successfully " + rez + " Field(s)!");
					else
						ClientWindow.displayBadMessage("Something Went Wrong!");
				} catch (NumberFormatException ex) {
					ClientWindow.displayBadMessage("Please insert correct values!!!");
				}

			}
		});
	}
}
