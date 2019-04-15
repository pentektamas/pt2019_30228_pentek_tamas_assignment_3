package DatabaseProject.OrderManagement.Presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ProductWindow extends JFrame {

	private JRadioButton add = new JRadioButton("Add");
	private JRadioButton editing = new JRadioButton("Edit");
	private JRadioButton delete = new JRadioButton("Delete");
	private JRadioButton view = new JRadioButton("View All");
	private ButtonGroup buttonGroup = new ButtonGroup();
	private BorderLayout layout = new BorderLayout();
	private ProductInsert insert = new ProductInsert();
	private ProductEdit edit = new ProductEdit();
	private ProductDelete del = new ProductDelete();
	private ProductView viewAll = new ProductView();
	private JLabel mainText = new JLabel("Select an operation: ");
	private JPanel p0 = new JPanel();
	private JPanel p1 = new JPanel();

	public ProductWindow() {
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
		this.setTitle("Product Operations");
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
				p1.add(viewAll, BorderLayout.CENTER);
				insert.setVisible(false);
				edit.setVisible(false);
				del.setVisible(false);
				viewAll.setVisible(true);
			}
		});
	}

	public static void displayGoodMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Product Operation", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void displayBadMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Product Operation", JOptionPane.ERROR_MESSAGE);
	}
}
