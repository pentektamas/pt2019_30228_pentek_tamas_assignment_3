package DatabaseProject.OrderManagement.BusinessLayer;

import java.util.ArrayList;
import java.util.List;

import DatabaseProject.OrderManagement.DataAccess.ClientDAC;
import DatabaseProject.OrderManagement.Model.Client;

public class ClientOperation {

	private Client client;
	private ClientDAC clientData;

	public ClientOperation() {
		client = new Client();
		clientData = new ClientDAC();
	}

	public int addClient(Client c) {
		boolean ok = clientData.insert(c.getIDClient(), c.getFirst_name(), c.getLast_name(), c.getAddress(),
				c.getPhone_number(), c.getIban(), c.getEmail_address());
		if (ok)
			return 1;
		else
			return 0;

	}

	public int editClient(String what, String value, String where) {
		int rez = clientData.update(what, value, where);
		if (rez != -1)
			return rez;
		else
			return -1;
	}

	public int deleteClient(String where) {
		int rez = clientData.delete(where);
		if (rez != -1)
			return rez;
		else
			return -1;
	}

	public List<Client> viewAllClients() {
		List<Client> clients = new ArrayList();
		clients = clientData.findAll();
		return clients;
	}

	public List<String> getFieldsName() {
		List<String> fields = new ArrayList<String>();
		fields = clientData.retrieveProperties();
		return fields;
	}

	public Client getFieldsValues(Client client) {
		Client value = new Client();
		value = clientData.retrievePropertiesValues(client);
		return value;

	}
}
