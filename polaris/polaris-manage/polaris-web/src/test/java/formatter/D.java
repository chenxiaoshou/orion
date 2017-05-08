package formatter;

import java.io.Serializable;

public class D implements Serializable {

	private static final long serialVersionUID = -7379317795104889641L;

	private Status status = Status.a;
	
	private Double price = 15.6d;
	
	private enum Status {
		a, b, c;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}
