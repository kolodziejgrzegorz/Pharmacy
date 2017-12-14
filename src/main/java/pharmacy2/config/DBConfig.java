package pharmacy2.config;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
public class DBConfig {

	@Bean
	HibernateTemplate hibernateTemplate() {
		return new HibernateTemplate(sessionFactory());
	}
	
	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBean lsfb = new  LocalSessionFactoryBean();
		lsfb.setDataSource(getDataSource());
		lsfb.setPackagesToScan("pharmacy2.entity");
		lsfb.setHibernateProperties(hibernateProperties());
		
		try {
			lsfb.afterPropertiesSet();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return lsfb.getObject();
	}
	
	@Bean
	public DataSource getDataSource(){
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {

			cpds.setDriverClass("com.mysql.jdbc.Driver");
			cpds.setJdbcUrl("jdbc:mysql://localhost:3306/apteka?useSSL=false");
			cpds.setUser("hbstudent");
			cpds.setPassword("hbstudent");
			
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cpds;
	}
	
	@Bean
	public HibernateTransactionManager hibernateTransactionManager() throws PropertyVetoException {
		return new HibernateTransactionManager(sessionFactory());
	}
	
	private Properties hibernateProperties() {
		Properties properties = new Properties();
		
		properties.put("hibernate.dialect","org.hibernate.dialect.MySQLDialect" );
		properties.put("hibernate.show_sql","true" );
		properties.put("hibernate.format_sql","true" );

		return properties;
	}
}
