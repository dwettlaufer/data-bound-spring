package com.frankmoley.services.pii.data.repository;

import javax.sql.DataSource;

import org.springframework.core.env.Environment;

/**
 * @author Frank Moley
 */
public abstract class PiiRepository {

    protected Environment sqlProperties;
    protected DataSource datasource;
    protected final static String INSERT = "insert";
    protected final static String GET = "get";
    protected final static String UPDATE = "update";
    protected final static String DELETE = "delete";
    protected final static String FIND_ALL = "get_all";

    PiiRepository (DataSource dataSource, Environment env) {
        super();
        this.datasource = dataSource;
        this.sqlProperties = env;
    }

}