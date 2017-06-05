package com.polaris.manage.web.controller.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polaris.manage.web.controller.BaseController;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

	private static final Logger LOGGER = LogManager.getLogger(AuthController.class);

}
