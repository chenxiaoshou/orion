package com.polaris.manage.web.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.polaris.manage.web.controller.BaseController;
import com.polaris.manage.web.vo.auth.Auth4Login;
import com.polaris.manage.web.vo.auth.AuthInfo;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

	private static final Logger LOGGER = LogManager.getLogger(AuthController.class);

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public AuthInfo login(@RequestBody @Valid Auth4Login auth4Login, HttpServletRequest request) {
		LOGGER.info(
				"username [" + auth4Login.getUsername() + "] password [" + auth4Login.getPassword() + "] is logined");
		AuthInfo authInfo = new AuthInfo();
		authInfo.setUsername(auth4Login.getUsername());
		authInfo.setSuccess(true);
		return authInfo;
	}

}
