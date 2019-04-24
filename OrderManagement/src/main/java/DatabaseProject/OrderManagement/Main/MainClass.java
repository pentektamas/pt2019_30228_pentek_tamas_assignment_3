package DatabaseProject.OrderManagement.Main;

import java.sql.*;
import DatabaseProject.OrderManagement.Presentation.MainWindow;

/**
 * 
 * @author Pentek Tamas
 * 
 *         Clasa contine metoda main de unde este pornita aplicatia
 *
 */
public class MainClass {

	public static void main(String[] args) throws SQLException {
		MainWindow mw = new MainWindow();
		mw.setVisible(true);
	}
}
