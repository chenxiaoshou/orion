package common.builder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.orion.common.utils.JsonUtil;
import com.orion.manage.model.tools.dic.order.OrderStatusEnum;
import com.orion.manage.model.tools.dic.order.SaleChannelEnum;
import com.orion.manage.web.vo.order.Order4Add;

/**
 * 构造postmane接口测试时使用的json数据
 * 
 * @author John
 *
 */
public class BuildPostManeData extends BaseBuiler {

	public static void main(String[] args) {
		buildOrder4Add();
		buildBcryptPwd();
	}

	private static void buildBcryptPwd() {
		System.out.println(new BCryptPasswordEncoder().encode("1990912"));
	}

	private static void buildOrder4Add() {
		Order4Add order = new Order4Add();
		order.setPaymentAmount(19.9d);
		order.setSaleChannel(SaleChannelEnum.Amazon);
		order.setStatus(OrderStatusEnum.STEP_AWAIT_PROCESSING);
		order.setTotalPrice(19.9d);
		System.out.println(getJson(order));
	}

	private static String getJson(Object obj) {
		return JsonUtil.toJSON(obj);
	}

}
