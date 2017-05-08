package commontest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.polaris.common.formatter.ArrayFormatter;
import com.polaris.common.formatter.BeanFormatter;
import com.polaris.common.formatter.IFormatter;
import com.polaris.common.formatter.MapFormatter;
import com.polaris.common.utils.DateUtil;
import com.polaris.common.utils.ReflectionUtils;
import com.polaris.manage.model.dic.OrderStatus;
import com.polaris.manage.model.mysql.order.Order;
import com.polaris.manage.model.mysql.order.OrderItem;

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
		Order[] orders = new Order[] { order1, order2 };
		IFormatter formatter = new BeanFormatter();
		System.out.println(formatter.toString(order1));
		
		formatter = new MapFormatter();
		Map<String, Order> map = new ConcurrentHashMap<>();
		map.put("a", order1);
		map.put("b", order2);
		System.out.println(formatter.toString(map));

	}

}
