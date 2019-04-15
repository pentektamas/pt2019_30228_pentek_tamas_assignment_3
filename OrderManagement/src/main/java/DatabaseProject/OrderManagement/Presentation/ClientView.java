package DatabaseProject.OrderManagement.Presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import DatabaseProject.OrderManagement.BusinessLayer.ClientOperation;
import DatabaseProject.OrderManagement.Model.Client;

public class ClientView extends JPanel {

	private JTable clientsTable = new JTable();
	ClientOperation cop = new ClientOperation();
	JButton refresh = new JButton("Refresh");
	JScrollPane scrollPane;
	JPanel p = new JPanel();
	JPanel p1 = new JPanel();
	List<String> headers;

	public ClientView() {
		p1.add(refresh);
		headers = new ArrayList<String>();
		headers = cop.getFieldsName();
		clientsTable = this.createTable(headers);
		p.add(scrollPane);
		this.add(p1, BorderLayout.PAGE_START);
		this.add(p, BorderLayout.CENTER);
		this.addRefreshListener();

	}

	public JTable createTable(List<String> headers) {
		List<Client> clients = cop.viewAllClients();
		// List<String> headers = new ArrayList<String>();
		// headers = cop.getFieldsName();
		Object[] columnNames = headers.toArray();

		Object[][] object = new Object[clients.size()][headers.size()];
		int i = 0;
		for (Client c : clients) {
			int j = 0;
			object[i][j++] = c.getIDClient();
			object[i][j++] = c.getFirst_name();
			object[i][j++] = c.getLast_name();
			object[i][j++] = c.getAddress();
			object[i][j++] = c.getPhone_number();
			object[i][j++] = c.getIban();
			object[i][j++] = c.getEmail_address();
			i++;
		}
		JTable table = new JTable(object, columnNames);
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(580, 400));
		return table;
	}

	public void addRefreshListener() {
		refresh.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				p.removeAll();
				clientsTable = createTable(headers);
				p.add(scrollPane);
				p.updateUI();
			}

		});
	}

}