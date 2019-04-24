package DatabaseProject.OrderManagement.Model;

/**
 * 
 * @author Pentek Tamas
 * 
 *         Clasa reprezinta un rand din tabelul Companyorder
 *
 */
public class CompanyOrder {

	private int IDOrder;
	private int IDCLient;
	private String payment_type;
	private String date;

	public int getIDOrder() {
		return IDOrder;
	}

	public void setIDOrder(int iDOrder) {
		IDOrder = iDOrder;
	}

	public int getIDCLient() {
		return IDCLient;
	}

	public void setIDCLient(int iDCLient) {
		IDCLient = iDCLient;
	}

	public String getTotal_price() {
		return payment_type;
	}

	public void setTotal_price(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
