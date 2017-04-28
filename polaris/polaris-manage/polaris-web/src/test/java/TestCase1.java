import java.util.LinkedHashSet;

import com.polaris.common.utils.DateUtil;
import com.polaris.common.utils.ReflectionUtils;
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
		
		/*Order order2 = new Order();
		order2.setId("2");
		order2.setCompleteTime(DateUtil.timestamp());
		order2.setCreateTime(DateUtil.timestamp());
		order2.setPaymentAmount(78.88d);
		order2.setSaleChannel("WISH");
		order2.setStatus(OrderStatus.STEP_SHIPPING_FINISHED.getStatus());
		order2.setTotalPrice(78.88d);
		Order[] orders = new Order[]{order1, order2};
		System.out.println(Arrays.deepToString(orders));
		IFormatter formatter = new ArrayFormatter();
		System.out.println(formatter.format(orders));*/
		
		LinkedHashSet<String> list = new LinkedHashSet<>();
		list.add("a");
		System.out.println(ReflectionUtils.isList(list.getClass()));
		
	}
	
}
