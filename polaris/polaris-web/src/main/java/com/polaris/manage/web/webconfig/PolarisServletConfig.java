package com.polaris.manage.web.webconfig;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.utils.Charsets;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.castor.CastorMarshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.polaris.common.utils.JsonUtil;

/**
 * SpringMVC 相关配置
 * 
 * @author John
 * @description :
 * 
 *              <pre class="brush:java;">
 *  * 　　不使用 {@link EnableWebMvc} 注解而是直接继承于 {@link WebMvcConfigurationSupport} 或者
 * {@link DelegatingWebMvcConfiguration}，之后通过覆盖方法可以实现更多可选功能。
 * 如果使用EnableWebMvc注解就可以解决需求的话，那直接继承WebMvcConfigurationAdapter就可以了。
 *              </pre>
 *
 */
@Configuration
@ComponentScan(basePackages = { "org.polaris.project.*.web" }, useDefaultFilters = false, includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Aspect.class, Controller.class,
				ControllerAdvice.class }) })
@EnableAspectJAutoProxy(proxyTargetClass = true) // 启用Springmvc层面的切面自动代理，用于AOP,并指定使用CGLIB代理
public class PolarisServletConfig extends WebMvcConfigurationSupport {

	private static final String VIEW_JSP_PREFIX = "/WEB-INF/jsp/";

	private static final String VIEW_JSP_SUFFIX = ".jsp";

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	private static final String RESOURCE_HANDLER = "/static/**";

	private static final String RESOURCE_LOCATION = "/static/";

	private static final String VIEW_FREEMARKER_SUFFIX = ".ftl";

	private static final String VIEW_FREEMARKER_TEMPLATE_LOADER_PATH = "/WEB-INF/freemarker/";

	private static final String DEFAULT_ENCODING = "UTF-8";
	
	private static final String MESSAGE_SOURCE = "message_zh";

	/**
	 * JSP视图解析器
	 * 
	 * @return
	 */
	@Bean(name = "viewResolver")
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix(VIEW_JSP_PREFIX);
		viewResolver.setSuffix(VIEW_JSP_SUFFIX);
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setContentType(CONTENT_TYPE);
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
		freeMarkerViewResolver.setContentType(CONTENT_TYPE);
		freeMarkerViewResolver.setCache(true);
		freeMarkerViewResolver.setRequestContextAttribute("basePath");
		freeMarkerViewResolver.setSuffix(VIEW_FREEMARKER_SUFFIX);
		freeMarkerViewResolver.setOrder(1); // 设置视图优先级
		return freeMarkerViewResolver;
	}

	/**
	 * FreeMarker模板配置
	 */
	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		freeMarkerConfigurer.setTemplateLoaderPath(VIEW_FREEMARKER_TEMPLATE_LOADER_PATH);
		freeMarkerConfigurer.setDefaultEncoding(DEFAULT_ENCODING);
		return freeMarkerConfigurer;
	}

	/**
	 * 消息转换器,Spring默认是注册了以下转换器的，但是为了让json转换器使用我们自定义的日期格式，所以需要全部重新配置
	 */
	private List<HttpMessageConverter<?>> createMessageConverters() {
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		converters.add(new ByteArrayHttpMessageConverter());
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setWriteAcceptCharset(false);
		stringConverter.setDefaultCharset(Charsets.UTF_8);
		// 文本消息转换器所支持的文本类型
		List<MediaType> textTypes = new ArrayList<MediaType>();
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
		// converters.add(new Jaxb2RootElementHttpMessageConverter());
		converters.add(new MarshallingHttpMessageConverter(new CastorMarshaller(), new CastorMarshaller()));
		// 待测 converters.add(new MarshallingHttpMessageConverter(new
		// Jaxb2Marshaller(), new Jaxb2Marshaller()));
		// Json消息转换器
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		jsonConverter.setObjectMapper(JsonUtil.createMapper());
		jsonConverter.setDefaultCharset(Charsets.UTF_8);
		List<MediaType> jsonTypes = new ArrayList<MediaType>();
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
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		return super.requestMappingHandlerMapping();
	}

	/**
	 * 为映射请求处理器配置消息转换器
	 * 
	 * @return
	 */
	@Bean
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
		requestMappingHandlerAdapter.setMessageConverters(createMessageConverters());
		return requestMappingHandlerAdapter;
	}

	/**
	 * 消息国际化
	 * @return
	 */
	@Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(MESSAGE_SOURCE);
        messageSource.setCacheSeconds(5);
        return messageSource;
    }
	
	/**
	 * 添加静态资源映射
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(RESOURCE_HANDLER).addResourceLocations(RESOURCE_LOCATION);
	}

	/**
	 * 因为将spring的拦截模式设置为"/"时会对静态资源进行拦截, 将对于静态资源的请求转发到Servlet容器的默认处理静态资源的servlet
	 */
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

}
