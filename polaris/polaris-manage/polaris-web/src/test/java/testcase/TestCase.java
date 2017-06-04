package testcase;

import com.polaris.manage.model.mysql.order.dic.OrderStatus;
import com.polaris.manage.web.vo.order.Order4Add;

public class TestCase {

	public static void main(String[] args) {
		Order4Add order = new Order4Add();
		order.setPaymentAmount(19.9d);
		order.setSaleChannel("SMT");
		order.setStatus(OrderStatus.STEP_AWAIT_PROCESSING.getStatus());
		order.setTotalPrice(19.9d);

		System.out.println(OrderStatus.STEP_AWAIT_PROCESSING.name());

	}

}
