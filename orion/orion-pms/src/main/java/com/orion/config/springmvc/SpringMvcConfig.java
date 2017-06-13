package com.orion.config.springmvc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Charsets;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.orion.common.constant.AppConstants;
import com.orion.common.exception.AppExceptionResolver;
import com.orion.common.utils.JsonUtil;

/**
 * SpringMVC 相关配置, 相当于xml时代的polaris-servlet.xml配置文件
 * 
 * @author John
 * @description :
 * 
 *              <pre class="brush:java;">
 * 不使用 {@link EnableWebMvc} 注解而是直接继承于 {@link WebMvcConfigurationSupport} 或者
 * {@link DelegatingWebMvcConfiguration}，之后通过覆盖方法可以实现更多可选功能。
 * 如果使用EnableWebMvc注解就可以解决需求的话，那直接继承WebMvcConfigurationAdapter就可以了。
 *              </pre>
 *
 */
@Configuration
@ComponentScan(basePackages = { "com.orion.manage.web" }, useDefaultFilters = false, includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class, RestController.class,
				Aspect.class, ControllerAdvice.class, RestControllerAdvice.class }) })
@EnableAspectJAutoProxy(proxyTargetClass = true) // 启用Springmvc层面的切面自动代理，用于AOP,并指定使用CGLIB代理
public class SpringMvcConfig extends WebMvcConfigurationSupport {

	@Autowired
	private Environment env;

	/**
	 * JSP视图解析器
	 * 
	 * @return
	 */
	@Bean(name = "viewResolver")
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix(AppConstants.VIEW_JSP_PREFIX);
		viewResolver.setSuffix(AppConstants.VIEW_JSP_SUFFIX);
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setContentType(AppConstants.CONTENT_TYPE);
		viewResolver.setOrder(0); // 设置视图优先级
		return viewResolver;
	}

	/**
	 * FreeMarker视图解析器
	 */
	@Bean
	public FreeMarkerViewResolver freeMarkerViewResolver() {
		FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
		freeMarkerViewResolver.setViewClass(FreeMarkerView.class);
		freeMarkerViewResolver.setContentType(AppConstants.CONTENT_TYPE);
		freeMarkerViewResolver.setCache(true);
		freeMarkerViewResolver.setRequestContextAttribute("basePath");
		freeMarkerViewResolver.setSuffix(AppConstants.VIEW_FREEMARKER_SUFFIX);
		freeMarkerViewResolver.setOrder(1); // 设置视图优先级
		return freeMarkerViewResolver;
	}

	/**
	 * FreeMarker模板配置
	 */
	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		freeMarkerConfigurer.setTemplateLoaderPath(AppConstants.VIEW_FREEMARKER_TEMPLATE_LOADER_PATH);
		freeMarkerConfigurer.setDefaultEncoding(AppConstants.CHAESET_UTF_8);
		return freeMarkerConfigurer;
	}

	/**
	 * 消息转换器,Spring默认是注册了以下转换器的，但是为了让json转换器使用我们自定义的日期格式，所以需要全部重新配置
	 */
	private List<HttpMessageConverter<?>> createMessageConverters() {
		List<HttpMessageConverter<?>> converters = new ArrayList<>();
		converters.add(new ByteArrayHttpMessageConverter());
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setWriteAcceptCharset(false);
		stringConverter.setDefaultCharset(Charsets.UTF_8);
		// 文本消息转换器所支持的文本类型
		List<MediaType> textTypes = new ArrayList<>();
		// 原生格式
		textTypes.add(MediaType.TEXT_PLAIN);
		// HTML格式
		textTypes.add(MediaType.TEXT_HTML);
		// 以us-ascii编码XML内容
		textTypes.add(MediaType.TEXT_XML);
		// 以指定字符集编码XML内容
		textTypes.add(MediaType.APPLICATION_XML);
		stringConverter.setSupportedMediaTypes(textTypes);
		converters.add(stringConverter);
		// XML消息转换器，两种方式:Jaxb 和 Marshalling，任选其一
		converters.add(new Jaxb2RootElementHttpMessageConverter());
		// Json消息转换器
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		jsonConverter.setObjectMapper(JsonUtil.createMapper());
		jsonConverter.setDefaultCharset(Charsets.UTF_8);
		List<MediaType> jsonTypes = new ArrayList<>();
		jsonTypes.add(MediaType.APPLICATION_JSON);
		jsonTypes.add(MediaType.APPLICATION_JSON_UTF8);
		jsonTypes.add(MediaType.TEXT_HTML);// 避免IE出现下载JSON文件的情况
		jsonConverter.setSupportedMediaTypes(jsonTypes);
		converters.add(jsonConverter);
		return converters;
	}

	/**
	 * 为映射请求处理器配置消息映射器
	 * 
	 * @return
	 */
	@Bean
	@Override
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		return super.requestMappingHandlerMapping();
	}

	/**
	 * 为映射请求处理器配置消息转换器
	 * 
	 * @return
	 */
	@Bean
	@Override
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		super.requestMappingHandlerAdapter();
		RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
		requestMappingHandlerAdapter.setMessageConverters(createMessageConverters());
		requestMappingHandlerAdapter.setWebBindingInitializer(getConfigurableWebBindingInitializer());
		return requestMappingHandlerAdapter;
	}

	/**
	 * validator
	 */
	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
		validatorFactoryBean.setProviderClass(HibernateValidator.class);
		validatorFactoryBean.setValidationMessageSource(messageSource());
		validatorFactoryBean.afterPropertiesSet();
		return validatorFactoryBean;
	}

	/**
	 * 消息国际化
	 * 
	 * @return
	 */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		String messagePath = env.getProperty("message_source", AppConstants.MESSAGE_SOURCE);
		messageSource.setBasenames(messagePath.split(","));
		messageSource.setCacheSeconds(300);
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	/**
	 * 添加静态资源映射
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(AppConstants.RESOURCE_HANDLER).addResourceLocations(AppConstants.RESOURCE_LOCATION);
	}

	/**
	 * 因为将spring的拦截模式设置为"/"时会对静态资源进行拦截, 将对于静态资源的请求转发到Servlet容器的默认处理静态资源的servlet
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * 注册servlet适配器，只需要在自定义的servlet上用@Controller("映射路径")标注即可
	 * 
	 * @return
	 */
	@Bean
	public HandlerAdapter servletHandlerAdapter() {
		return new SimpleServletHandlerAdapter();
	}

	/**
	 * 基于cookie的本地化资源处理器
	 * 
	 * @return
	 */
	@Bean
	public CookieLocaleResolver cookieLocaleResolver() {
		return new CookieLocaleResolver();
	}

	/**
	 * 本地化拦截器
	 * 
	 * @return
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		return new LocaleChangeInterceptor();
	}

	/**
	 * 添加Spring提供的客户端设备的解析拦截器
	 * 
	 * @return
	 */
	@Bean
	public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
		return new DeviceResolverHandlerInterceptor();
	}

	/**
	 * 客户端站点偏好拦截器
	 * 
	 * @return
	 */
	@Bean
	public SitePreferenceHandlerInterceptor sitePreferenceHandlerInterceptor() {
		return new SitePreferenceHandlerInterceptor();
	}

	/**
	 * 添加拦截器
	 * 
	 * @param registry
	 */
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor()); // 本地化拦截器
		registry.addInterceptor(deviceResolverHandlerInterceptor());
		registry.addInterceptor(sitePreferenceHandlerInterceptor());
	}

	/**
	 * 文件上传处理器
	 * 
	 * @return
	 */
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver commonsMultipartResolver() {
		return new CommonsMultipartResolver();
	}

	/**
	 * 复写父类方法，增加自定义异常处理类或者删除spring默认指定的异常处理类
	 * 
	 * @return
	 */
	@Override
	protected void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		super.extendHandlerExceptionResolvers(exceptionResolvers);
		if (CollectionUtils.isNotEmpty(exceptionResolvers)) {
			int targetIndex = 0;
			for (int i = 0; i < exceptionResolvers.size(); i++) {
				if (exceptionResolvers.get(i) instanceof DefaultHandlerExceptionResolver) {
					targetIndex = i;
					break;
				}
			}
			// 在DefaultHandlerExceptionResolver异常解析器之前插入自定义的异常解析器，使得我们自定义的异常解析器优先执行
			exceptionResolvers.add(targetIndex, appExceptionResolver());
		}
	}

	@Bean
	public AppExceptionResolver appExceptionResolver() {
		return new AppExceptionResolver();
	}

	/**
	 * 注册通用属性编辑器
	 * 
	 * @return
	 */
	// @Override
	// protected ConfigurableWebBindingInitializer
	// getConfigurableWebBindingInitializer() {
	// ConfigurableWebBindingInitializer initializer =
	// super.getConfigurableWebBindingInitializer();
	// AppEditorRegistrar register = new AppEditorRegistrar();
	// register.setFormat("yyyy-MM-dd");
	// initializer.setPropertyEditorRegistrar(register);
	// return initializer;
	// }

}
