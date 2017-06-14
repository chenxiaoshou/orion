package common.builder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.orion.common.utils.RandomUtil;
import com.orion.manage.model.tools.dic.order.GenderEnum;
import com.orion.manage.web.vo.auth.Auth4Register;

public class BuildUserData extends BaseBuiler {

	public static void main(String[] args) {
		addUser();
	}

	public static void addUser() {
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 100; i++) {
						try {
							createUser();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}

	protected static void createUser() throws Exception {
		Auth4Register register = new Auth4Register();
		register.setEmail(RandomUtil.randomEmail());
		register.setEnable(true);
		register.setGender(GenderEnum.valueOf(RandomUtil.randomGender()));
		register.setIdCard(RandomUtil.randomIdCard());
		register.setMobile(RandomUtil.randomMobile());
		register.setPassword(new BCryptPasswordEncoder().encode("1990912"));
		register.setRealName(RandomUtil.randomFullName());
		register.setUsername(RandomUtil.randomString(6, 12));
		register.setRoles(RandomUtil.randomFromArray(new String[] { "ROOT", "CS", "PM", "CKDDY" }));
		executeHttpPost(register);
	}

}
