package DatabaseProject.OrderManagement.Main;

import java.sql.*;
import java.util.List;

import DatabaseProject.OrderManagement.Presentation.ClientWindow;
import DatabaseProject.OrderManagement.Presentation.OrderWindow;
import DatabaseProject.OrderManagement.Presentation.ProductWindow;

public class MainClass {

	public static void main(String[] args) throws SQLException {
		// Connection connection = DBConnection.getConnection();
		// DBConnection.close(connection);
	/*	ClientDAC c = new ClientDAC();
		Client client = new Client();
		client = c.findById(25, "IDClient");
		System.out.println("MAIN" + client.toString());
		List<Client> mylist = c.findAll();
		for (Client m : mylist)
			System.out.println(m.toString());

		ProductDAC p = new ProductDAC();
		CompanyOrderDAC d = new CompanyOrderDAC();
		OrderListDAC ol = new OrderListDAC();

		List<Product> mylist2 = p.findAll();
		for (Product a : mylist2)
			System.out.println(a.toString());

		// c.insert(30,"Pentek","Tamas","Adresa mea","0745128795");
		// c.update("IDClient", "44", "IDClient=30");
		// c.delete("IDClient=44");
		// p.insert(12, "Pufulet", 1, 100);
		// d.insert(122, 12, 17, "2018-05-03");
		// ol.insert(122, 1, 15, 34);
		// p.update("price", "231", "IDProduct=12");
		// d.update("total_price", "10757", "IDOrder=122");
		// ol.update("quantity", "234","IDOrder=122");
		// ol.delete("IDOrder=122");
		// d.delete("IDOrder=122");
		int x = ol.totalPriceForIDOrder(500);*/
		
		ClientWindow cw=new ClientWindow();
		//cw.setVisible(true);
		ProductWindow pw=new ProductWindow();
		//pw.setVisible(true);
		OrderWindow ow=new OrderWindow();
		ow.setVisible(true);
		
	}
}
