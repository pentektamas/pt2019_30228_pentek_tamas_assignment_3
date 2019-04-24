package DatabaseProject.OrderManagement.Presentation;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.swing.*;
import DatabaseProject.OrderManagement.DataAccess.AbstractDAC;

/**
 * 
 * @author Pentek Tamas
 * 
 *         Aceasta clasa extinde JFrame si reprezinta fereastra care deschide
 *         cand pornim aplicatia, aceasta reprezinta fereastra principala
 *
 */
public class MainWindow extends JFrame {

	ClientWindow cw = new ClientWindow();
	ProductWindow pw = new ProductWindow();
	OrderWindow ow = new OrderWindow();
	private JButton client = new JButton("Client Operations");
	private JButton product = new JButton("Product Operations");
	private JButton order = new JButton("Create an order");

	public MainWindow() {
		JPanel p = new JPanel();

		p.add(client);
		p.add(product);
		p.add(order);
		this.addClientButtonListener();
		this.addProductButtonListener();
		this.addOrderButtonListener();
		this.setTitle("Order Management Company");
		this.pack();
		this.setContentPane(p);
		this.setSize(new Dimension(600, 150));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(500, 200);

	}

	/**
	 * Metoda adauga un listener la butonul Client Operations
	 */
	public void addClientButtonListener() {
		client.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cw.setVisible(true);
				pw.setVisible(false);
				ow.setVisible(false);

			}
		});
	}

	/**
	 * Metoda adauga un listener la butonul Product Operations
	 */
	public void addProductButtonListener() {
		product.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cw.setVisible(false);
				pw.setVisible(true);
				ow.setVisible(false);
			}
		});
	}

	/**
	 * Metoda adauga un listener la butonul Create an order
	 */
	public void addOrderButtonListener() {
		order.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				cw.setVisible(false);
				pw.setVisible(false);
				ow.setVisible(true);
			}
		});
	}

	/**
	 * KSDJG:DKDSLGJSDO:GJDLGKNDALKGJADKNV
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * ?????????????????????????????????????????????????
	 * ?????????????????????????????????????????????????
	 * 
	 * @param values
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 */
	public static <T> JTable createTable(List<T> values)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		AbstractDAC<Object> aDAC = new AbstractDAC<Object>();
		List<String> headers = aDAC.retrieveProperties();
		String[] columnNames = (String[]) headers.toArray();

		Object element = new Object();
		Object[][] object = new Object[values.size()][headers.size()];
		for (int i = 0; i < values.size(); i++) {
			// element=aDAC.retrievePropertiesValues(values.get(i));
			for (Field f : object.getClass().getDeclaredFields()) {
				int j = 0;
				PropertyDescriptor propertyDescriptor = new PropertyDescriptor(f.getName(), object.getClass());
				Method method = propertyDescriptor.getReadMethod();
				object[i][j++] = method.invoke(element);
				System.out.println("EL: " + object[i][j++] + "");
			}
		}
		JTable table = new JTable(object, columnNames);
		// scrollPane = new JScrollPane(table);
		// scrollPane.setPreferredSize(new Dimension(780, 400));
		return table;
	}

	/**
	 * Metoda afiseaza un JOptionPane de informare cu mesajul dorit
	 * 
	 * @param message este mesajul
	 */
	public static void displayGoodMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Order Operation", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Metoda afiseaza un JOptionPane de eroare cu mesajul dorit
	 * 
	 * @param message este mesajul
	 */
	public static void displayBadMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Order Operation", JOptionPane.ERROR_MESSAGE);
	}
}
