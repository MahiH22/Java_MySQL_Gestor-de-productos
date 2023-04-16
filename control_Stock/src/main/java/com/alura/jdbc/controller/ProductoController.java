package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer cantidadProducoto, Integer id) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		
		PreparedStatement statement = con.prepareStatement("UPDATE producto SET "
			    + " nombre = ? "
			    + ", descripcion = ? "
			    + ", cantidad = ? " 
			    + " WHERE id = ?");
		
		System.out.println(descripcion);
		
		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3, cantidadProducoto);
		statement.setInt(4, id);
		
		System.out.println(statement);
		
		
		statement.execute();
		
		int updateCount = statement.getUpdateCount();
		
		con.close();
		
		return updateCount;
	}

	public int eliminar(Integer id) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		
		PreparedStatement statement = con.prepareStatement("DELETE FROM producto WHERE id = ?");
		statement.setInt(1, id);
		
		statement.execute();
		
		int updateCount = statement.getUpdateCount();
		
		con.close();
		
		return updateCount;
	}

	public List<Map<String,String>> listar() throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		
		PreparedStatement statement = con.prepareStatement("SELECT id,Nombre,Descripcion,Cantidad FROM PRODUCTO");
		
		statement.execute();
		
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
		
		PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO PRODUCTO (nombre,descripcion,cantidad) VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS);
		
		preparedStatement.setString(1, producto.get("NOMBRE"));
		preparedStatement.setString(2, producto.get("DESCRIPCION"));
		preparedStatement.setInt(3, Integer.valueOf(producto.get("CANTIDAD")));
		
		preparedStatement.execute();
		
		ResultSet resultset = preparedStatement.getGeneratedKeys();
		
		while(resultset.next()) {
			System.out.println(String.format("Fue inserta el producto de ID %d", resultset.getInt(1)));
		}
		con.close();
		
		
	}



}
