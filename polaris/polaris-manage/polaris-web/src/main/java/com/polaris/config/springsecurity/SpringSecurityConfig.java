package com.polaris.config.springsecurity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.polaris.security.filter.AuthenticationTokenFilter;
import com.polaris.security.handler.EntryPointUnauthorizedHandler;
import com.polaris.security.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String PARAMETER_USERNAME = "username";

	private static final String PARAMETER_PASSWORD = "password";

	@Autowired
	private EntryPointUnauthorizedHandler unauthorizedHandler;

	/**
	 * 使用自定义的userDetailsService
	 */
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().exceptionHandling().authenticationEntryPoint(this.unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 不创建session
				.and().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**")
				.permitAll().antMatchers(HttpMethod.GET, "/static/**", "/*.html", "/favicon.ico", "/**/*.html",
						"/**/*.css", "/**/*.js")
				.permitAll().antMatchers("/auth/**").permitAll().anyRequest().authenticated();
		httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
		authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
		authenticationTokenFilter.setUsernameParameter(PARAMETER_USERNAME);
		authenticationTokenFilter.setPasswordParameter(PARAMETER_PASSWORD);
		return authenticationTokenFilter;
	}

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
    @Bean
	@Override
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * ???
	 * 
	 * @return
	 */
	@Bean
	public org.springframework.security.authentication.event.LoggerListener loggerListener() {
		return new org.springframework.security.authentication.event.LoggerListener();
	}

	/**
	 * ???
	 * 
	 * @return
	 */
	@Bean
	public org.springframework.security.access.event.LoggerListener eventLoggerListener() {
		return new org.springframework.security.access.event.LoggerListener();
	}

	/*
	 * 
	 * 这里可以增加自定义的投票器
	 */
	@Bean(name = "accessDecisionManager")
	public AccessDecisionManager accessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
		decisionVoters.add(new RoleVoter());
		decisionVoters.add(new AuthenticatedVoter());
		decisionVoters.add(webExpressionVoter());// 启用表达式投票器
		return new AffirmativeBased(decisionVoters);
	}

	/*
	 * 表达式控制器
	 */
	@Bean(name = "expressionHandler")
	public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
		return new DefaultWebSecurityExpressionHandler();
	}

	/*
	 * 表达式投票器
	 */
	@Bean(name = "expressionVoter")
	public WebExpressionVoter webExpressionVoter() {
		WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
		webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());
		return webExpressionVoter;
	}

}
