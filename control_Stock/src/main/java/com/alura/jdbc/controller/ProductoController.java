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
		final Connection con = new ConnectionFactory().recuperaConexion();
		try(con) {
			final PreparedStatement statement = con.prepareStatement("UPDATE producto SET "
				    + " nombre = ? "
				    + ", descripcion = ? "
				    + ", cantidad = ? " 
				    + " WHERE id = ?");
			
			try(statement){
				System.out.println(descripcion);
				
				statement.setString(1, nombre);
				statement.setString(2, descripcion);
				statement.setInt(3, cantidadProducoto);
				statement.setInt(4, id);
				
				System.out.println(statement);
				
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
				
				return updateCount;
	
			}
		}
		}

	public int eliminar(Integer id) throws SQLException {
		final Connection con = new ConnectionFactory().recuperaConexion();
		try(con){
			final PreparedStatement statement = con.prepareStatement("DELETE FROM producto WHERE id = ?");
			try(statement){
				statement.setInt(1, id);
				statement.execute();
				int updateCount = statement.getUpdateCount();
				return updateCount;
				
			}
		}
	}

	public List<Map<String,String>> listar() throws SQLException {
		final Connection con = new ConnectionFactory().recuperaConexion();
		try(con){
			PreparedStatement statement = con.prepareStatement("SELECT id,Nombre,Descripcion,Cantidad FROM PRODUCTO");
			
			statement.execute();
			
			final ResultSet resultSet = statement.getResultSet();
					
			try(resultSet){
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
				//se regresa la fila de mapas
				return resultado;	
			}
			
		}
	}

    public void guardar(Map<String,String> producto) throws SQLException {
    	String nombre = producto.get("NOMBRE");
    	String descripcion = producto.get("DESCRIPCION");
    	Integer cantidad = Integer.valueOf(producto.get("CANTIDAD"));
    	
    	//cantidad maximaque s epuede guardar en un stack
    	Integer cantidadMaximo = 50;
    	
    	final Connection con = new ConnectionFactory().recuperaConexion();
    	try(con){
    		con.setAutoCommit(false);// para tomar el control de los commits a la base de datos
    		
    		final PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO PRODUCTO (nombre,descripcion,cantidad) VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS);
    		
    		try(preparedStatement){
        			do {
        				int cantidadParaGuardar = Math.min(cantidad, cantidadMaximo);
        				
        				EjecutaRegistro(nombre, descripcion, cantidadParaGuardar, preparedStatement);
        				
        				cantidad-=cantidadParaGuardar;
        				System.out.println(cantidad);
        			}while(cantidad>0);
        			con.commit();
        		}catch(Exception e) {
        			con.rollback();
        		}
    	}
	}

	private void EjecutaRegistro(String nombre, String descripcion, Integer cantidad, PreparedStatement preparedStatement)
		throws SQLException {
		preparedStatement.setString(1, nombre);
		preparedStatement.setString(2, descripcion);
		preparedStatement.setInt(3, cantidad);
		
		preparedStatement.execute();
		final ResultSet resultset = preparedStatement.getGeneratedKeys();
		try(resultset){
			while(resultset.next()) {
				System.out.println(String.format("Fue inserta el producto de ID %d", resultset.getInt(1)));
			}	
		}
		
	}



}
