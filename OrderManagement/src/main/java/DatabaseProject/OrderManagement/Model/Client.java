package DatabaseProject.OrderManagement.Model;

/**
 * 
 * @author Pentek Tamas
 * 
 *         Clasa reprezinta un rand din tabelul Client
 *
 */
public class Client {

	private int IDClient;
	private String first_name;
	private String last_name;
	private String address;
	private String phone_number;
	private String iban;
	private String email_address;

	/**
	 * 
	 * Metode getter si setter pentru a accesa si a seta datele
	 */
	public int getIDClient() {
		return IDClient;
	}

	public void setIDClient(int iDClient) {
		IDClient = iDClient;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getEmail_address() {
		return email_address;
	}

	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}

}