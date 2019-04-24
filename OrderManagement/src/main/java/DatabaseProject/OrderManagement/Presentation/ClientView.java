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

/**
 * 
 * @author Pentek Tamas
 * 
 *         Aceasta clasa extinde clasa JPanel, creeaza un panel care este
 *         folosit cand afisam tabelul Client
 *
 */
public class ClientView extends JPanel {

	private JTable clientsTable = new JTable();
	ClientOperation cop = new ClientOperation();
	List<Client> clients = new ArrayList<Client>();
	JButton refresh = new JButton("Refresh");
	JScrollPane scrollPane;
	JPanel p = new JPanel();
	JPanel p1 = new JPanel();

	public ClientView() {
		p1.add(refresh);
		clients = cop.viewAllClients();
		clientsTable = MainWindow.createTable(clients);
		scrollPane = new JScrollPane(clientsTable);
		scrollPane.setPreferredSize(new Dimension(780, 400));
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
				clients = cop.viewAllClients();
				clientsTable = MainWindow.createTable(clients);
				scrollPane = new JScrollPane(clientsTable);
				scrollPane.setPreferredSize(new Dimension(780, 400));
				p.add(scrollPane);
				p.updateUI();
			}
		});
	}
}
