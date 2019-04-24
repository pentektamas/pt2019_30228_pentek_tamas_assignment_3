package DatabaseProject.OrderManagement.BusinessLayer;

import java.util.ArrayList;
import java.util.List;
import DatabaseProject.OrderManagement.DataAccess.ProductDAC;
import DatabaseProject.OrderManagement.Model.Product;

/**
 * 
 * @author Pentek Tamas
 * 
 *         Clasa contine metode folosite pentru gestionarea datelor din tabelul
 *         Product
 *
 */
public class ProductOperation {

	private ProductDAC productData;

	public ProductOperation() {
		productData = new ProductDAC();
	}

	/**
	 * Metoda apeleaza o alta metoda care insereaza un nou produs in tabelul Product
	 * 
	 * @param p este produsul care dorim sa introducem in tabel
	 * @return 1, daca inserarea s-a efectuat cu succes, altfel 0
	 */
	public int addProduct(Product p) {
		boolean ok = productData.insert(p.getIDProduct(), p.getName(), p.getPrice(), p.getStock());
		if (ok)
			return 1;
		else
			return 0;
	}

	/**
	 * Metoda updateaza unu sau mai multe randuri in tabelul Product
	 * 
	 * @param what  este coloana care vrem sa modificam
	 * @param value este noua valoare care vrem sa introducem
	 * @param where este conditia modificarii
	 * @return rez(numarul randurilor in care s-a facut update), daca updateul s-a
	 *         efectuat cu succes, altfel -1
	 */
	public int editProduct(String what, String value, String where) {
		int rez = productData.update(what, value, where);
		if (rez != -1)
			return rez;
		else
			return -1;
	}

	/**
	 * Metoda apeleaza o alta metoda care face stergerea randurilor din tabelul
	 * Product
	 * 
	 * @param where este conditia stergerii
	 * @return rez(numarul randurilor in care s-a facut stergere), daca stergerea
	 *         s-a efectuat cu succes, altfel -1
	 */
	public int deleteProduct(String where) {
		int rez = productData.delete(where);
		if (rez != -1)
			return rez;
		else
			return -1;
	}

	/**
	 * Metoda salveaza intr-o lista toate datele din tabelul Product
	 * 
	 * @return lista care contine toate datele din tabelul Product
	 */
	public List<Product> viewAllProducts() {
		List<Product> products = new ArrayList<Product>();
		products = productData.findAll();
		return products;
	}

	/**
	 * Metoda salveaza intr-o lista anteturile tabelului
	 * 
	 * @return lista cu anteturi
	 */
	public List<String> getFieldsName() {
		List<String> fields = new ArrayList<String>();
		fields = productData.retrieveProperties();
		return fields;
	}

	/*
	 * public Product getFieldsValues(Product product) { Product value = new
	 * Product(); value = productData.retrievePropertiesValues(product); return
	 * value;
	 * 
	 * }
	 */
}
