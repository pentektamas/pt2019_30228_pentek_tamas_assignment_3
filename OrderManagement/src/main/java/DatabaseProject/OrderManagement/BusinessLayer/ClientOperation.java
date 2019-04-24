package DatabaseProject.OrderManagement.BusinessLayer;

import java.util.ArrayList;
import java.util.List;
import DatabaseProject.OrderManagement.DataAccess.ClientDAC;
import DatabaseProject.OrderManagement.Model.Client;

/**
 * 
 * @author Pentek Tamas
 * 
 *         Clasa contine metode folosite pentru gestionarea datelor din tabelul
 *         Client
 *
 */
public class ClientOperation {

	private ClientDAC clientData;

	public ClientOperation() {
		clientData = new ClientDAC();
	}

	/**
	 * Metoda apeleaza o alta metoda care insereaza un nou client in tabelul Client
	 * 
	 * @param c este clientul care dorim sa introducem in tabel
	 * @return 1, daca inserarea s-a efectuat cu succes, altfel 0
	 */
	public int addClient(Client c) {
		boolean ok = clientData.insert(c.getIDClient(), c.getFirst_name(), c.getLast_name(), c.getAddress(),
				c.getPhone_number(), c.getIban(), c.getEmail_address());
		if (ok)
			return 1;
		else
			return 0;
	}

	/**
	 * Metoda updateaza unu sau mai multe randuri in tabelul Client
	 * 
	 * @param what  este coloana care vrem sa modificam
	 * @param value este noua valoare care vrem sa introducem
	 * @param where este conditia modificarii
	 * @return rez(numarul randurilor in care s-a facut update), daca updateul s-a
	 *         efectuat cu succes, altfel -1
	 */
	public int editClient(String what, String value, String where) {
		int rez = clientData.update(what, value, where);
		if (rez != -1)
			return rez;
		else
			return -1;
	}

	/**
	 * Metoda apeleaza o alta metoda care face stergerea randurilor din tabelul
	 * Client
	 * 
	 * @param where este conditia stergerii
	 * @return rez(numarul randurilor in care s-a facut stergere), daca stergerea
	 *         s-a efectuat cu succes, altfel -1
	 */
	public int deleteClient(String where) {
		int rez = clientData.delete(where);
		if (rez != -1)
			return rez;
		else
			return -1;
	}

	/**
	 * Metoda salveaza intr-o lista toate datele din tabelul Client
	 * 
	 * @return lista care contine toate datele din tabelul Client
	 */
	public List<Client> viewAllClients() {
		List<Client> clients = new ArrayList<Client>();
		clients = clientData.findAll();
		return clients;
	}

	/**
	 * Metoda salveaza intr-o lista anteturile tabelului
	 * 
	 * @return lista cu anteturi
	 */
	public List<String> getFieldsName() {
		List<String> fields = new ArrayList<String>();
		fields = clientData.retrieveProperties();
		return fields;
	}
}
