package com.github.sarxos.bjug.dao;

import java.util.concurrent.TimeUnit;

import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;

/**
 * This is integration test because it begins from IT.
 */
public class SampleDaoITCase {

	static EmbeddedMysql mysqld;

	Jdbi dbi;

	@BeforeClass
	public static void prepareDb() {
		mysqld = EmbeddedMysql
			.anEmbeddedMysql(MysqldConfig
				.aMysqldConfig(Version.v5_7_latest)
				.withPort(2215)
				.withUser("foo", "secret")
				.withTimeZone("Europe/Warsaw")
				.withTimeout(2, TimeUnit.MINUTES)
				.withServerVariable("max_connect_errors", 666)
				.build())
			.addSchema("megazord", ScriptResolver.classPathScript("db/megazord.sql"))
			.start();
	}

	@AfterClass
	public static void teardownDb() {
		mysqld.stop();
	}

	@Before
	public void setup() {
		dbi = Jdbi.create("jdbc:mysql://localhost:2215/megazord", "foo", "secret");
		dbi.installPlugin(new SqlObjectPlugin());
	}

	@Test
	public void test_something() {

		SampleDao dao = dbi.onDemand(SampleDao.class);

		Assertions.assertThat(dao.getDatabases()).contains("megazord");
	}
}
