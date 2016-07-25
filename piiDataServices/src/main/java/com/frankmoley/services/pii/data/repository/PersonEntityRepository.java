package com.frankmoley.services.pii.data.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.frankmoley.services.pii.data.entity.PersonEntity;
import com.frankmoley.services.pii.data.mapper.PersonEntityRowMapper;

/**
 * @author Frank Moley
 */
@Component
public class PersonEntityRepository extends PiiRepository {
	static Logger log = Logger.getLogger(PersonEntityRepository.class.getName());

	public PersonEntityRepository(DataSource dataSource, Environment env) {
		super(dataSource, env);
	}

    public PersonEntity addPerson(PersonEntity model) {
    	String personId = UUID.randomUUID().toString();
    	PreparedStatement preparedStatement = null;
    	Connection dbConnection = null;
    	String statement = this.sqlProperties.getProperty(INSERT);
    	try {
    		dbConnection = datasource.getConnection();
    		dbConnection.setAutoCommit(false);
			preparedStatement = dbConnection.prepareStatement(statement);
			preparedStatement.setString(1, personId);
			preparedStatement.setString(2, StringUtils.trimToNull(model.getPrefix()));
			preparedStatement.setString(3, StringUtils.trimToNull(model.getFirstName()));
			preparedStatement.setString(4, StringUtils.trimToNull(model.getMiddleName()));
			preparedStatement.setString(5, StringUtils.trimToNull(model.getLastName()));
			preparedStatement.setString(6, StringUtils.trimToNull(model.getSuffix()));
			
			preparedStatement.execute();
			dbConnection.commit();
		} catch (SQLException e) {
			log.error("Threw exception in addPerson()", e);
			if (dbConnection != null) {
				try {
					log.error("Transaction is being rolled back");
					dbConnection.rollback();
				} catch (SQLException sqe) {
					log.error("Transaction wasn't rolled back", sqe);
				}
			}
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) { 
					log.error("PreparedStatement wasn't closed", e);
				}
			}

			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException e) { 
					log.error("Connection wasn't closed", e);
				}
			}
		}
		
        return this.getPerson(personId);
    }

    public PersonEntity getPerson(String personId) {
    	PersonEntity personEntity = null;
    	ResultSet resultSet = null;
    	PreparedStatement preparedStatement = null;
    	Connection dbConnection = null;
    	String statement = this.sqlProperties.getProperty(GET);
		try {
			dbConnection = datasource.getConnection();
			preparedStatement = dbConnection.prepareStatement(statement);
			preparedStatement.setString(1, personId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				personEntity = PersonEntityRowMapper.mapRow(resultSet);
			}
		} catch (SQLException e) {
			log.error("Threw exception in getPerson()", e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) { 
					log.error("PreparedStatement wasn't closed", e);
				}
			}

			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) { 
					log.error("ResultSet wasn't closed", e);
				}
			}

			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException e) { 
					log.error("Connection wasn't closed", e);
				}
			}
		}
		
        return personEntity;
    }

    public PersonEntity updatePerson(String personId, PersonEntity model) {
    	PreparedStatement preparedStatement = null;
    	Connection dbConnection = null;
    	String statement = this.sqlProperties.getProperty(UPDATE);
    	try {
    		dbConnection = datasource.getConnection();
    		dbConnection.setAutoCommit(false);
			preparedStatement = dbConnection.prepareStatement(statement);
			preparedStatement.setString(1, StringUtils.trimToNull(model.getPrefix()));
			preparedStatement.setString(2, StringUtils.trimToNull(model.getFirstName()));
			preparedStatement.setString(3, StringUtils.trimToNull(model.getMiddleName()));
			preparedStatement.setString(4, StringUtils.trimToNull(model.getLastName()));
			preparedStatement.setString(5, StringUtils.trimToNull(model.getSuffix()));
			preparedStatement.setString(6, personId);
			preparedStatement.execute();
			dbConnection.commit();
		} catch (SQLException e) {
			log.error("Threw exception in updatePerson()", e);
			if (dbConnection != null) {
				try {
					log.error("Transaction is being rolled back");
					dbConnection.rollback();
				} catch (SQLException sqe) {
					log.error("Transaction wasn't rolled back", sqe);
				}
			}
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) { 
					log.error("PreparedStatement wasn't closed", e);
				}
			}

			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException e) { 
					log.error("Connection wasn't closed", e);
				}
			}
		}

        return this.getPerson(personId);
    }

    public void deletePerson(String personId) {
    	PreparedStatement preparedStatement = null;
    	Connection dbConnection = null;
    	try {
    		dbConnection = datasource.getConnection();
    		dbConnection.setAutoCommit(false);
			String deleteStatement = this.sqlProperties.getProperty(DELETE);
			preparedStatement = dbConnection.prepareStatement(deleteStatement);
			preparedStatement.setString(1, personId);
			preparedStatement.execute();
			dbConnection.commit();
		} catch (SQLException e) {
			log.error("Threw exception in deletePerson()", e);
			if (dbConnection != null) {
				try {
					log.error("Transaction is being rolled back");
					dbConnection.rollback();
				} catch (SQLException sqe) {
					log.error("Transaction wasn't rolled back", sqe);
				}
			}
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) { 
					log.error("PreparedStatement wasn't closed", e);
				}
			}

			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException e) { 
					log.error("Connection wasn't closed", e);
				}
			}
		}
    }

    public List<PersonEntity> getAll() {
    	List<PersonEntity> persons = new ArrayList<PersonEntity>();
    	ResultSet resultSet = null;
    	PreparedStatement preparedStatement = null;
    	Connection dbConnection = null;
    	String statement = sqlProperties.getProperty(FIND_ALL);
		try {
			dbConnection = datasource.getConnection();
			preparedStatement = dbConnection.prepareStatement(statement);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				PersonEntity person = PersonEntityRowMapper.mapRow(resultSet);
				persons.add(person);
			}
		} catch (SQLException e) {
			log.error("Threw exception in getAll()", e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) { 
					log.error("PreparedStatement wasn't closed", e);
				}
			}

			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) { 
					log.error("ResultSet wasn't closed", e);
				}
			}

			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException e) { 
					log.error("Connection wasn't closed", e);
				}
			}
		}
		
        return persons;
    }
}