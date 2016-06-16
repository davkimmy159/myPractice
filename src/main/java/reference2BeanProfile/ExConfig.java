package reference2BeanProfile;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
@Profile("classProfile")
public class ExConfig {

	@Bean(destroyMethod = "shutdown")
	@Profile("methodProfile1")
	public DataSource dataSource1() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:schema.sql")
				.addScript("classpath:test-data.sql")
				.build();
	}

	@Bean
	@Profile("methodProfile2")
	public DataSource dataSource2() {
		JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
		jndiObjectFactoryBean.setJndiName("jdbc/myDS");
		jndiObjectFactoryBean.setResourceRef(true);
		jndiObjectFactoryBean.setProxyInterface(javax.sql.DataSource.class);
		return (DataSource) jndiObjectFactoryBean.getObject();
	}
	
	@Bean(destroyMethod = "close")
	@Profile("methodProfile3")
	public DataSource dataSource3() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:h2:tcp://dbserver/~/test");
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUsername("sa");
		dataSource.setPassword("password");
		dataSource.setInitialSize(20);
		dataSource.setMaxActive(30);
		return dataSource;
	}
	
	@Bean
	@Conditional(ExConditionClass.class)
	public void ConditionMethod() {
		System.out.println("Condition is accomplished and this method is in operation!");
	}
	
}

/*
@Configuration
@Profile("anotherClassProfile")
class DBConfig1 {
}
*/