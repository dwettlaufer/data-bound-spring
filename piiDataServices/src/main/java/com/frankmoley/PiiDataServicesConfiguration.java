package com.frankmoley;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.frankmoley.services.pii.data.repository.PersonEntityRepository;

@Configuration
@EnableTransactionManagement
@Transactional("dataSourceTransactionManager")
@PropertySource(name = "sqlProperties", value = {"classpath:com.frankmoley.services.pii.data.repository/person.properties"})
public class PiiDataServicesConfiguration {
	
	@Autowired
	private Environment env;

	@Bean 
	public PersonEntityRepository personEntityRepository () {
		return new PersonEntityRepository(dataSource(), env);
	}

	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
			.setType(EmbeddedDatabaseType.H2)
			.addScript("databaseCreate.sql")
			.addScript("preloadData.sql")
			.build();
		return db;
	}
}
