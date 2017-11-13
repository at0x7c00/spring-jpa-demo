package com.example.demo.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@PropertySource("classpath:jdbc.properties")
@EnableJpaRepositories(basePackages = "com.example.demo.repository")
//@ImportResource(value = "classpath:application-dao.xml")
@EnableTransactionManagement(proxyTargetClass = true)
public class JpaConfiguration {

	@Value("${jdbc.driverClassName}")
	private String driverClass;
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String username;
	@Value("${jdbc.password}")
	private String password;
	@Value("${preferredTestQuery}")
	private String preferredTestQuery;
	@Value("${idleConnectionTestPeriod}")
	private int idleConnectionTestPeriod;
	@Value("${testConnectionOnCheckout}")
	private boolean testConnectionOnCheckout;
	
	@Bean
	public DataSource dataSource()throws Exception {
		ComboPooledDataSource dataSource= new ComboPooledDataSource();
		dataSource.setDriverClass(driverClass);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUser(username);
		dataSource.setPassword(password);
		dataSource.setPreferredTestQuery(preferredTestQuery);
		dataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
		dataSource.setTestConnectionOnCheckout(testConnectionOnCheckout);
	    return dataSource;
	}
	
	@Bean
    @Autowired
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(false);
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
        vendorAdapter.setDatabase(Database.MYSQL);
 
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.example.demo.entity");
        factory.setDataSource(dataSource);
 
        Properties properties = new Properties();
        properties.setProperty("hibernate.cache.use_second_level_cache", "false");
        //properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
        properties.setProperty("hibernate.cache.use_query_cache", "false");
        properties.setProperty("hibernate.generate_statistics", "false");
 
        factory.setJpaProperties(properties);
 
        factory.afterPropertiesSet();
 
        return factory.getObject();
    }
	/**
	 * <bean id="sessionFactory"
		class="org.springframework.orm.hibernate4.LocalSessionFactoryBean">
		<property name="dataSource" ref="dataSource"/>
		<property name="hibernateProperties">
			<props>
				<prop key="hibernate.dialect">${hibernate.dialect}</prop>
				<prop key="hibernate.hbm2ddl.auto">${hibernate.hbm2ddl.auto}</prop>
				<prop key="hibernate.max_fetch_depth">${hibernate.maxFetchDepth}</prop>
				<prop key="hibernate.show_sql">${hibernate.show_sql}</prop>
				<prop key="hibernate.format_sql">${hibernate.format_sql}</prop>
				<prop key="hibernate.jdbc.batch_size">${hibernate.jdbc.batch_size}</prop>
				<prop key="hibernate.cache.use_query_cache">${cache.use_query_cache}</prop>
				<prop key="hibernate.cache.use_second_level_cache">${cache.use_second_level_cache}</prop>
				<prop key="hibernate.cache.region.factory_class">${hibernate.cache.region.factory_class}</prop>
				<prop key="hibernate.temp.use_jdbc_metadata_defaults">${hibernate.temp.use_jdbc_metadata_defaults}</prop>
			</props>
		</property>
		<property name="packagesToScan" value="me.huqiao.smallcms.**.entity"/>
	</bean>
	 * @param entityManagerFactory
	 * @return
	 */
	
	/*@Bean
	public LocalSessionFactoryBean sessionFactoryBean() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setPackagesToScan("com.example.demo.entity");
		return sessionFactoryBean;
	}
	*/
	
	@Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
		HibernateExceptionTranslator t = new HibernateExceptionTranslator();
		return t;
    }
 
	@Bean
    @Autowired
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        JpaDialect jpaDialect = new HibernateJpaDialect();
        txManager.setEntityManagerFactory(entityManagerFactory);
        txManager.setJpaDialect(jpaDialect);
        return txManager;
    }
	
	@Bean
	@Autowired
	public TransactionInterceptor transactionInterceptor(JpaTransactionManager transactionManager) {
		TransactionInterceptor t = new TransactionInterceptor();
		t.setTransactionManager(transactionManager);
		Properties transactionAttributes = new Properties();
		InputStream is = JpaConfiguration.class.getResourceAsStream("/tx.properties");
		try {
			transactionAttributes.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		t.setTransactionAttributes(transactionAttributes);
		return t;
	}

}
