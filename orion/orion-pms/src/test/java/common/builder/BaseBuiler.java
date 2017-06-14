package common.builder;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.orion.common.constant.SecurityConstants;
import com.orion.common.dic.SourceTypeEnum;
import com.orion.common.utils.JsonUtil;

public class BaseBuiler {

	protected static final String SAVE_ORDER_URL = "http://localhost:8080/orion/api/v1/order";

	protected static final String TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjEwMSwiYXVkIjoiMTI3LjAuMC4xIiwidWlkIjoiMSIsInJlZiI6MTQ5ODA0MjExMTg2NSwicm9sZXMiOiIiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvb3Jpb24vYXBpL3YxL2F1dGgvbG9naW4iLCJleHAiOjE0OTc0ODczMjI1NzYsImlhdCI6MTQ5NzQ0NDEyMjU3NiwidXNlcm5hbWUiOiJ6aGFuZ3EifQ.fq4LbTdzcipu7POD53cCLnoyGzwQm0iOEtjsi5_DxglNpXGV053oR8dikkUxatn9gYv5aaTgYBJkVAZtBNTNw1YGgNn5DPPavFiPmG7JRh9n9BexIduBHcQnb4ABz02MvwOHdfdsacnjF88gKKvCs7UmPCbKzRudI44K6H74Bkg";

	protected static <T> void executeHttpPost(T t) throws Exception {
		String saveRequestJson = JsonUtil.toJSON(t);
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(SAVE_ORDER_URL);
		httpPost.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
		httpPost.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
		httpPost.addHeader(SecurityConstants.HEADER_AUTH_TOKEN, TOKEN);
		httpPost.addHeader(SecurityConstants.HEADER_SOURCE, "" + SourceTypeEnum.Desktop.getCode());
		httpPost.setEntity(new StringEntity(saveRequestJson, StandardCharsets.UTF_8));
		CloseableHttpResponse resp = client.execute(httpPost);
		System.out.println(resp.getStatusLine().getStatusCode());
	}

}
