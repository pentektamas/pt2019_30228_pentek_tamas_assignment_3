package DatabaseProject.OrderManagement.Presentation;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import DatabaseProject.OrderManagement.BusinessLayer.ClientOperation;
import DatabaseProject.OrderManagement.Model.Client;

/**
 * 
 * @author Pentek Tamas
 * 
 *         Aceasta clasa extinde clasa JPanel, creeaza un panel care este
 *         folosit cand inseram un nou client
 *
 */
public class ClientInsert extends JPanel {

	private JLabel ID = new JLabel("ID Client:            ");
	private JLabel firstName = new JLabel("First Name:       ");
	private JLabel lastName = new JLabel("Last Name:        ");
	private JLabel address = new JLabel("Address:            ");
	private JLabel phone = new JLabel("Phone Number: ");
	private JLabel iban = new JLabel("IBAN Number:   ");
	private JLabel email = new JLabel("E-mail Address: ");
	private JTextField IDText = new JTextField();
	private JTextField firstNameText = new JTextField();
	private JTextField lastNameText = new JTextField();
	private JTextField addressText = new JTextField();
	private JTextField phoneText = new JTextField();
	private JTextField ibanText = new JTextField();
	private JTextField emailText = new JTextField();
	private GridLayout layout = new GridLayout(0, 1);
	private JButton ok = new JButton("OK");

	public ClientInsert() {
		JPanel p0 = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();
		JPanel p6 = new JPanel();
		JPanel p7 = new JPanel();
		JPanel p8 = new JPanel();
		IDText.setPreferredSize(new Dimension(200, 20));
		firstNameText.setPreferredSize(new Dimension(200, 20));
		lastNameText.setPreferredSize(new Dimension(200, 20));
		addressText.setPreferredSize(new Dimension(200, 20));
		phoneText.setPreferredSize(new Dimension(200, 20));
		ibanText.setPreferredSize(new Dimension(200, 20));
		emailText.setPreferredSize(new Dimension(200, 20));
		p1.add(ID);
		p1.add(IDText);
		p2.add(firstName);
		p2.add(firstNameText);
		p3.add(lastName);
		p3.add(lastNameText);
		p4.add(address);
		p4.add(addressText);
		p5.add(phone);
		p5.add(phoneText);
		p6.add(iban);
		p6.add(ibanText);
		p7.add(email);
		p7.add(emailText);
		p8.add(ok);
//		JPanel p = new JPanel();
		this.setLayout(layout);
		this.add(p0);
		this.add(p1);
		this.add(p2);
		this.add(p3);
		this.add(p4);
		this.add(p5);
		this.add(p6);
		this.add(p7);
		this.add(p8);
		this.addOKListener();
	}

	/**
	 * Metoda adauga un listener la butonul OK
	 */
	public void addOKListener() {
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientOperation clientOp = new ClientOperation();
				Client client = new Client();
				try {
					client.setIDClient(Integer.parseInt(IDText.getText()));
					client.setFirst_name(firstNameText.getText());
					client.setLast_name(lastNameText.getText());
					client.setAddress(addressText.getText());
					client.setPhone_number(phoneText.getText());
					client.setIban(ibanText.getText());
					client.setEmail_address(emailText.getText());
					int rez = clientOp.addClient(client);
					if (rez == 1)
						MainWindow.displayGoodMessage("New Client Inserted Successfully!");
					else
						MainWindow.displayBadMessage("Something Went Wrong! Possibly duplicate values!");
				} catch (NumberFormatException ex) {
					MainWindow.displayBadMessage("Please insert correct values!!!");
				}
			}
		});
	}
}
