package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
	private DataSource dataSource;
	
	public ConnectionFactory () {
		var poolDataSource= new ComboPooledDataSource();
		poolDataSource.setJdbcUrl("jdbc:mysql://localhost/control_stock?useTimeZone=true&serverTimeZone=UTC");
		poolDataSource.setUser("root");
		poolDataSource.setPassword("mahi22");
		poolDataSource.setMaxPoolSize(10);
		
		
		this.dataSource=poolDataSource;
	}

	public Connection recuperaConexion() throws SQLException {
		return this.dataSource.getConnection();
		
	}
	
}
