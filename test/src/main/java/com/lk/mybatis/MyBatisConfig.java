package com.lk.mybatis;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages={"com.lk.dao"})
public class MyBatisConfig {
	
	@Autowired
	private Environment env;

	/**
	 * 创建数据源(数据源的名称：方法名可以取为XXXDataSource(),XXX为数据库名称,该名称也就是数据源的名称)
	 */
	@Bean(destroyMethod = "shutdown")
	public DataSource dataSource_main() throws Exception {
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName(env.getProperty("jdbc.driverClassName"));
		config.addDataSourceProperty("url",env.getProperty("jdbc.test.url"));
		config.setUsername(env.getProperty("jdbc.test.username"));
		config.setPassword(env.getProperty("jdbc.test.password"));
		HikariDataSource ds = new HikariDataSource(config);
		return ds;
		
	}

	@Bean(destroyMethod = "shutdown")
	public DataSource dataSource_605() throws Exception {
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName(env.getProperty("jdbc.driverClassName"));
		config.addDataSourceProperty("url",env.getProperty("jdbc.shanghai.url"));
		config.setUsername(env.getProperty("jdbc.shanghai.username"));
		config.setPassword(env.getProperty("jdbc.shanghai.password"));
		HikariDataSource ds = new HikariDataSource(config);
		return ds;
	}
	

	/**
	 * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
	 * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
	 */
	@Bean
	@Primary
	public DynamicDataSource dataSource(
			@Qualifier("dataSource_main") DataSource dataSource_main,
			@Qualifier("dataSource_605") DataSource dataSource_605) {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DatabaseType.main, dataSource_main);
		targetDataSources.put(DatabaseType.shanghai, dataSource_605);

		DynamicDataSource dataSource = new DynamicDataSource();
		dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
		dataSource.setDefaultTargetDataSource(dataSource_main);// 默认的datasource设置为myTestDbDataSource

		return dataSource;
	}

	/**
	 * 根据数据源创建SqlSessionFactory
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory(DynamicDataSource ds)
			throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(ds);// 指定数据源(这个必须有，否则报错)
		sessionFactory.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
						.getResources(env
								.getProperty("mybatis.mapperLocations")));
		sessionFactory.setConfigLocation(new DefaultResourceLoader()
						.getResource(env
								.getProperty("mybatis.configLocation")));

		return sessionFactory.getObject();
	}

	/**
	 * 配置事务管理器
	 */
	@Bean
	public DataSourceTransactionManager transactionManager(
			DynamicDataSource dataSource) throws Exception {
		return new DataSourceTransactionManager(dataSource);
	}

}
