package com.github.sarxos.bjug.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface SampleDao {

	@SqlQuery("SHOW DATABASES")
	List<String> getDatabases();
}
