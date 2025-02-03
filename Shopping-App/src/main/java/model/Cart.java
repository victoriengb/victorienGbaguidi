package model;

public class Cart extends Product{

	private int quantity;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		if(quantity >= 0) {
			this.quantity = quantity;			
		}
	}
}
