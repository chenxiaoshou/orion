package common.builder;

import com.orion.common.utils.RandomUtil;
import com.orion.manage.model.tools.dic.order.OrderStatusEnum;
import com.orion.manage.model.tools.dic.order.SaleChannelEnum;
import com.orion.manage.web.vo.order.Order4Add;

public class BuildOrderData extends BaseBuiler {

	public static void main(String[] args) {
		addOrder();
	}

	public static void addOrder() {
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 100; i++) {
						try {
							createOrder();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}

	private static void createOrder() throws Exception {
		Order4Add order4Add = new Order4Add();
		Double price = RandomUtil.randomDouble(1.00d, 99.d);
		order4Add.setPaymentAmount(price);
		order4Add.setSaleChannel(RandomUtil.randomFromArray(SaleChannelEnum.values()));
		order4Add.setStatus(RandomUtil.randomFromArray(OrderStatusEnum.values()));
		order4Add.setTotalPrice(price);
		executeHttpPost(order4Add);
	}

}
