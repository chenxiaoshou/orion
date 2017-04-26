package com.polaris.config.springdata;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.sun.research.ws.wadl.Application;

@Configuration
@Import(value = { Application.class })
@EnableJpaRepositories(basePackages = {
		"com.polaris.manage.persist" }, queryLookupStrategy = Key.CREATE_IF_NOT_FOUND, 
				entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
public class JpaConfig {

	private static final Logger LOGGER = LogManager.getLogger(JpaConfig.class);
	
	@Autowired
	private DataSource dataSource;

	/**
	 * JPA实体转换器
	 * 
	 * @return
	 */
	@Bean
	@Primary
	public JpaVendorAdapter vendorAdapter() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.MYSQL);
		vendorAdapter.setShowSql(false);
		vendorAdapter.setGenerateDdl(false);
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
		return vendorAdapter;
	}

	/**
	 * JPA实体管理器工厂
	 * 
	 * @return
	 */
	@Bean
	@Primary
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter());
		factory.setPersistenceProvider(new HibernatePersistenceProvider());
		factory.setPackagesToScan("com.polaris.manage.model.*.mysql");
		factory.setDataSource(dataSource);
		factory.setJpaDialect(new HibernateJpaDialect());
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		properties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
		properties.put("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
		properties.put("hibernate.show_sql", false);
		properties.put("hibernate.format_sql", false);
		properties.put("hibernate.ejb.entitymanager_factory_name", "entityManagerFactory");
		properties.put("hibernate.hbm2ddl.auto", "update"); // 自动建表
		properties.put("hibernate.dialect.storage_engine", "innodb"); // 指定引擎
		factory.setJpaProperties(properties);
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	/**
	 * 配置JPA事务管理器
	 * 
	 * @return
	 */
	@Bean
	@Primary
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory());
		return transactionManager;
	}

	public static void main(String[] args) {
		LOGGER.trace("123");
		LOGGER.debug("123");
		LOGGER.info("123");
		LOGGER.warn("123");
		LOGGER.error("123");
	}
	
}