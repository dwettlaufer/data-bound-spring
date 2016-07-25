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

import com.frankmoley.services.pii.data.repository.PersonEntityRepository;

@Configuration
@PropertySource(name = "sqlProperties", value = {"classpath:person.properties"})
public class PiiDataServicesConfiguration {
	
	@Autowired
	private Environment env;

	@Bean 
	public PersonEntityRepository personEntityRepository () {
		return new PersonEntityRepository(dataSource(), env);
	}

	@Bean
	public DataSource dataSource() {
		// no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
			.setType(EmbeddedDatabaseType.H2) //.H2 or .DERBY
			.addScript("databaseCreate.sql")
			.addScript("preloadData.sql")
			.build();
		return db;
	}
}
