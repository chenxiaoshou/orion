package testcase;

import com.polaris.manage.model.dic.OrderStatus;
import com.polaris.manage.web.vo.order.Order4Create;

public class TestCase {

	public static void main(String[] args) {
		Order4Create order = new Order4Create();
		order.setPaymentAmount(19.9d);
		order.setSaleChannel("SMT");
		order.setStatus(OrderStatus.STEP_AWAIT_PROCESSING.getStatus());
		order.setTotalPrice(19.9d);
		
		System.out.println(OrderStatus.STEP_AWAIT_PROCESSING.name());
		
		
	}

}
