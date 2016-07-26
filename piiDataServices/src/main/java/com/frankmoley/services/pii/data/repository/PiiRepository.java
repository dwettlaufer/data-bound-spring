package com.frankmoley.services.pii.data.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author Frank Moley
 */
public abstract class PiiRepository {
	@Autowired
    protected NamedParameterJdbcTemplate jdbcTemplate;
    protected Environment sqlProperties;
    protected final static String INSERT = "insert";
    protected final static String GET = "get";
    protected final static String UPDATE = "update";
    protected final static String DELETE = "delete";
    protected final static String FIND_ALL = "get_all";

    PiiRepository(Environment sqlProperties){
        super();
        this.sqlProperties = sqlProperties;
    }


}
