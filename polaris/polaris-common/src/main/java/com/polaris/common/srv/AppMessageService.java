package com.polaris.common.srv;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.polaris.common.exception.AppMessage;
import com.polaris.common.utils.JsonUtil;

@Component
public class AppMessageService {

	@Autowired
	private MessageSource messageSource;

	public AppMessage getAppMessage(String messageKey, Object[] params) {
		String message = this.messageSource.getMessage(messageKey, null, null);
 		String finalMessage = "";
		int tag = 0;
		if (JsonUtil.isJsonStr(message)) {
			finalMessage = message;
		} else if (message.matches(".*\\{\\d+\\}.*")) {
			if (message.startsWith("{")) {
				message = message.substring(1);
			}
			if (message.endsWith("}")) {
				message = message.substring(0, message.length() - 1);
			}
			finalMessage = message;
			tag = 1;
		}
		if (tag == 1) {
			finalMessage = MessageFormat.format(finalMessage, params);
		}
		finalMessage = new StringBuilder("{").append(finalMessage).append("}").toString();
		return JsonUtil.fromJSON(finalMessage, AppMessage.class);
	}

}
