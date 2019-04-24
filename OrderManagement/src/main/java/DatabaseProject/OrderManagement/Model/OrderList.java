package DatabaseProject.OrderManagement.Model;

/**
 * 
 * @author Pentek Tamas
 * 
 *         Clasa reprezinta un rand din tabelul Orderlist
 *
 */
public class OrderList {

	private int IDOrder;
	private int IDProduct;
	private int product_price;
	private int quantity;
	private int price;

	public int getIDOrder() {
		return IDOrder;
	}

	public void setIDOrder(int iDOrder) {
		IDOrder = iDOrder;
	}

	public int getIDProduct() {
		return IDProduct;
	}

	public void setIDProduct(int iDProduct) {
		IDProduct = iDProduct;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getProduct_price() {
		return product_price;
	}

	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}

}
