package DatabaseProject.OrderManagement.Presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import DatabaseProject.OrderManagement.BusinessLayer.ClientOperation;
import DatabaseProject.OrderManagement.Model.Client;

public class ClientWindow extends JFrame {

	private JRadioButton add = new JRadioButton("Add");
	private JRadioButton editing = new JRadioButton("Edit");
	private JRadioButton delete = new JRadioButton("Delete");
	private JRadioButton view = new JRadioButton("View All");
	private ButtonGroup buttonGroup = new ButtonGroup();
	private BorderLayout layout = new BorderLayout();
	private ClientInsert insert = new ClientInsert();
	private ClientEdit edit = new ClientEdit();
	private ClientDelete del = new ClientDelete();
	private ClientView viewAll = new ClientView();
	private JLabel mainText = new JLabel("Select an operation: ");
	private JPanel p0 = new JPanel();
	private JPanel p1 = new JPanel();

	public ClientWindow() {
		buttonGroup.add(add);
		buttonGroup.add(editing);
		buttonGroup.add(delete);
		buttonGroup.add(view);
		p0.add(mainText);
		p0.add(add);
		p0.add(editing);
		p0.add(delete);
		p0.add(view);
		p1.setLayout(layout);
		insert.setVisible(false);
		edit.setVisible(false);
		del.setVisible(false);
		viewAll.setVisible(false);
		p1.add(p0, BorderLayout.PAGE_START);
		this.setTitle("Client Operations");
		this.pack();
		this.setContentPane(p1);
		this.setSize(new Dimension(600, 400));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p1.add(insert, BorderLayout.CENTER);
				insert.setVisible(true);
				edit.setVisible(false);
				del.setVisible(false);
				viewAll.setVisible(false);
			}
		});

		editing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p1.add(edit, BorderLayout.CENTER);
				insert.setVisible(false);
				edit.setVisible(true);
				del.setVisible(false);
				viewAll.setVisible(false);
			}
		});

		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p1.add(del, BorderLayout.CENTER);
				insert.setVisible(false);
				edit.setVisible(false);
				del.setVisible(true);
				viewAll.setVisible(false);
			}
		});

		view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ClientView viewAll = new ClientView();
				p1.add(viewAll, BorderLayout.CENTER);
				insert.setVisible(false);
				edit.setVisible(false);
				del.setVisible(false);
				viewAll.setVisible(true);
				//ClientView viewAll = new ClientView();
			//	ClientOperation c=new ClientOperation();
			//	List<Client> clients=new ArrayList<Client>();
			//	clients=c.viewAllClients();
			//	viewAll.createTable(clients);
			}
		});
	}

	public static void displayGoodMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Client Operation", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void displayBadMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Client Operation", JOptionPane.ERROR_MESSAGE);
	}
}
