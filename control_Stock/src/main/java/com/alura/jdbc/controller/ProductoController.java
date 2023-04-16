package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		
		Statement statement = con.createStatement();
		
		statement.execute("UPDATE PRODUCTO SET "
			    + " NOMBRE = '" + nombre + "'"
			    + ", DESCRIPCION = '" + descripcion + "'"
			    + ", CANTIDAD = " + cantidad
			    + " WHERE ID = " + id);
		int updateCount = statement.getUpdateCount();
		con.close();
		return updateCount;
	}

	public int eliminar(Integer id) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		
		Statement statement = con.createStatement();
		
		
		statement.execute("DELETE FROM producto WHERE id = "+ id);
		
		int updateCount = statement.getUpdateCount();
		
		con.close();
		
		return updateCount;
	}

	public List<Map<String,String>> listar() throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		
		Statement statement = con.createStatement();
		
		statement.execute("SELECT id,Nombre,Descripcion,Cantidad FROM PRODUCTO");
		
		ResultSet resultSet = statement.getResultSet();
				
		List<Map<String,String>> resultado = new ArrayList();
		// se crea una lista de mapas 
		
		while(resultSet.next()) {
			Map<String,String> fila = new HashMap<>();
			//cada mapa guarda la informacion de las respuestas de las filas de java
			fila.put("ID", String.valueOf(resultSet.getString("id")));
			fila.put("NOMBRE", resultSet.getString("nombre"));
			fila.put("DESCRIPCION", resultSet.getString("descripcion"));
			fila.put("CANTIDAD", resultSet.getString("cantidad"));
			//System.out.println(fila);
			resultado.add(fila);
		}
		con.close();
		
		//se regresa la fila de mapas
		return resultado;
	}

    public void guardar(Map<String,String> producto) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		Statement statement = con.createStatement();
		
		statement.execute("INSERT INTO PRODUCTO (nombre,descripcion,cantidad) "+" VALUES('"+ producto.get("NOMBRE") +"', '"+ producto.get("DESCRIPCION")+"', "+ producto.get("CANTIDAD")+")",Statement.RETURN_GENERATED_KEYS);
		
		ResultSet resultset = statement.getGeneratedKeys();
		
		while(resultset.next()) {
			System.out.println(String.format("Fue inserta el producto de ID %d", resultset.getInt(1)));
		}
		con.close();
		
		
	}



}
