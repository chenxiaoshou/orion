package common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.orion.common.utils.JsonUtil;
import com.orion.manage.model.tools.dic.order.OrderStatusEnum;
import com.orion.manage.model.tools.dic.order.SaleChannelEnum;
import com.orion.manage.web.vo.order.Order4Add;

/**
 * 构造测试使用的json/xml数据
 * 
 * @author John
 *
 */
public class BuildTestData {

	public static void main(String[] args) {
		Order4Add order = new Order4Add();
		order.setPaymentAmount(19.9d);
		order.setSaleChannel(SaleChannelEnum.Amazon);
		order.setStatus(OrderStatusEnum.STEP_AWAIT_PROCESSING);
		order.setTotalPrice(19.9d);
		System.out.println(getJson(order));
		
		System.out.println(new BCryptPasswordEncoder().encode("1990912"));

	}

	private static String getJson(Object obj) {
		return JsonUtil.toJSON(obj);
	}

}
