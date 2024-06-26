package com.soumya.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.soumya.model.User;

@Configuration
@EnableWebMvc
@ComponentScan({"com.soumya"})
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
public class SpringConfig {
	
	    @Autowired
	    private Environment environment;
	    @Autowired
	    private SessionFactory sessionfactory;
//	
	 	@Bean
	    public InternalResourceViewResolver viewResolver() {
	 		
	        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//	        viewResolver.setViewClass(JstlView.class);
	        viewResolver.setPrefix("/WEB-INF/views/");
	        viewResolver.setSuffix(".jsp");
	        
	        return viewResolver;
	    }
	     
	    @Bean
	    public MessageSource messageSource() {
	    	
	        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	        messageSource.setBasename("messages");
	        
	        return messageSource;
	    }
	    
	  
	 
	    @Bean
	    public LocalSessionFactoryBean sessionFactory() {
	    	
	        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	        sessionFactory.setDataSource(dataSource());
	        sessionFactory.setPackagesToScan(new String[] {"com.soumya"});
	        sessionFactory.setAnnotatedClasses(User.class);
	        sessionFactory.setHibernateProperties(hibernateProperties());
	        return sessionFactory;
	     }
	    @Bean
	    public HibernateTemplate hibernateTemplate() {
	    	
	    	HibernateTemplate hibernateTemplate = new HibernateTemplate();
	    	hibernateTemplate.setSessionFactory(sessionfactory);

	    	return hibernateTemplate;
	    	
	    }
	     
	    @Bean
	    public DataSource dataSource() {
	    	
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        
	        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
	        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
	        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
	        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
	        


	        return dataSource;
	    }
	    
	    @Bean
	    public Properties hibernateProperties() {
	        Properties properties = new Properties();
	        
	        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
	        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
	        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
	        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));


	        return properties;        
	    }
	     
	    @Bean
	   
	    public HibernateTransactionManager transactionManager() {
	       HibernateTransactionManager txManager = new HibernateTransactionManager();
	       txManager.setSessionFactory(sessionfactory);
	       


	       return txManager;
	    }
	

}
