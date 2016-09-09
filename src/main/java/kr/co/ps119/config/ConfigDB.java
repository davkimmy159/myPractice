package kr.co.ps119.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
//import javax.activation.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

//@Configuration
public class ConfigDB {
/*
	@Autowired
	DataSourceProperties dataSourceProperties;
	DataSource dataSource;
	
	@Bean
//	@Primary
	DataSource realDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create(this.dataSourceProperties.getClassLoader())
															   .url(this.dataSourceProperties.getUrl())
															   .username(this.dataSourceProperties.getUsername())
															   .password(this.dataSourceProperties.getPassword());
		this.dataSource = dataSourceBuilder.build();
		return this.dataSource;
	}
*/
	
/*
	@Bean
	public EmbeddedDatabase dataSource() {
		return new EmbeddedDatabaseBuilder().
				setType(EmbeddedDatabaseType.H2).
				addScript("db-schema.sql").
				addScript("db-test-data.sql").
				build();
	}
	
*/
}
