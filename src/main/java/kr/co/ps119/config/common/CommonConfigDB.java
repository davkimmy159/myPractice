package kr.co.ps119.config.common;

import org.springframework.core.annotation.Order;

@Order(1)
//@Configuration
public class CommonConfigDB {
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
