package com.github.sarxos.bjug.dao;

import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.Before;
import org.junit.Test;

/**
 * This is integration test because it begins from IT.
 */
public class SampleDaoITCase {

	Jdbi dbi;

	@Before
	public void setup() {
		dbi = Jdbi.create("jdbc:mysql://localhost:13306/megazord", "root", "secret");
		dbi.installPlugin(new SqlObjectPlugin());
	}

	@Test
	public void test_something() {

		SampleDao dao = dbi.onDemand(SampleDao.class);

		Assertions
			.assertThat(dao.getDatabases())
			.contains("megazord");
	}
}
