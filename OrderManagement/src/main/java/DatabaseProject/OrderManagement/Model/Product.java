package DatabaseProject.OrderManagement.Model;

public class Product {
	private int IDProduct;
	private String name;
	private int price;
	private int stock;

	public int getIDProduct() {
		return IDProduct;
	}

	public void setIDProduct(int iDProduct) {
		IDProduct = iDProduct;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String toString() {
		return "PRODUCT: " + this.IDProduct + " NAME: " + this.name + " PRICE: " + this.price + " STOCK: " + this.stock;
	}

}
