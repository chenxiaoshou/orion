package commontest;

import com.polaris.common.utils.DateUtil;
import com.polaris.common.utils.ToStringFormatter;
import com.polaris.manage.model.dic.OrderStatus;
import com.polaris.manage.model.mysql.order.Order;

public class TestCase1 {

	public static void main(String[] args) {
		Order order1 = new Order();
		order1.setId("1");
		order1.setCompleteTime(DateUtil.timestamp());
		order1.setCreateTime(DateUtil.timestamp());
		order1.setPaymentAmount(12.40d);
		order1.setSaleChannel("SMT");
		order1.setStatus(OrderStatus.STEP_SHIPPING_FINISHED.getStatus());
		order1.setTotalPrice(12.40d);

		Order order2 = new Order();
		order2.setId("2");
		order2.setCompleteTime(DateUtil.timestamp());
		order2.setCreateTime(DateUtil.timestamp());
		order2.setPaymentAmount(78.88d);
		order2.setSaleChannel("WISH");
		order2.setStatus(OrderStatus.STEP_SHIPPING_FINISHED.getStatus());
		order2.setTotalPrice(78.88d);
		
		System.out.println(ToStringFormatter.toString(order1));
		System.out.println(ToStringFormatter.toJson(order1));
		
		Order[] orders = new Order[]{order1, order2};
		System.out.println(ToStringFormatter.toString(orders));
		System.out.println(ToStringFormatter.toJson(orders));
		
		String[] str = new String[]{"a", "b", "c"};
		System.out.println(ToStringFormatter.toString(str));
		System.out.println(ToStringFormatter.toJson(str));
		
	}

}
