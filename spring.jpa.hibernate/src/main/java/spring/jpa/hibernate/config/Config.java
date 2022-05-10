package spring.jpa.hibernate.config;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.cj.jdbc.Driver;

@Configuration
@PropertySource(value = "classpath:database.properties")
@ComponentScan(basePackages = {"spring.jpa.hibernate"})
//@EnableTransactionManagement
public class Config {
	@Autowired
	private Environment env; 
	
	@Bean
	public DataSource dataSource(){
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName(env.getProperty("javax.persistence.jdbc.driver"));
			dataSource.setUrl(env.getProperty("javax.persistence.jdbc.url"));
			dataSource.setUsername(env.getProperty("javax.persistence.jdbc.user"));
			dataSource.setPassword(env.getProperty("javax.persistence.jdbc.password"));
			return dataSource;
	}
	
	 private Properties hibernateProperties() {
			Properties properties = new Properties();
			properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
			properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
			properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
			properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
			return properties;
	 }
	
	 
	 @Bean
	 public EntityManagerFactory entityManagerFactory(){
		 LocalContainerEntityManagerFactoryBean lcmf = new LocalContainerEntityManagerFactoryBean();
		 lcmf.setPackagesToScan("spring.jpa.hibernate");
		 lcmf.setDataSource(dataSource());
		 HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter(); 
		 lcmf.setJpaVendorAdapter(jpaVendorAdapter);
		 lcmf.setJpaProperties(hibernateProperties());
		 lcmf.afterPropertiesSet();
		 return lcmf.getObject(); 
	 }
	 
	 /*@Bean
		public JpaTransactionManager jpaTransactionManager(){
			JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(); 
			jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());
			return jpaTransactionManager;
		}*/
	
}
