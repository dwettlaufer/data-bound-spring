package com.frankmoley;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.frankmoley.services.pii.data.repository.PersonEntityRepository;

@Configuration
@EnableTransactionManagement
@PropertySource(name = "sqlProperties", value = {"classpath:com.frankmoley.services.pii.data.repository/person.properties"})
public class PiiDataServicesConfiguration {
	
	@Autowired
	private Environment env;

	@Bean 
	public PersonEntityRepository personEntityRepository () {
		return new PersonEntityRepository(env);
	}
}
