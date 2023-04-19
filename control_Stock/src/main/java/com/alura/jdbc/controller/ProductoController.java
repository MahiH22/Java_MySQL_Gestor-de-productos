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

import com.alura.jdbc.DAO.ProductoDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {
	private ProductoDAO ProductoDAO=new ProductoDAO(new ConnectionFactory().recuperaConexion());
public ProductoController() {
	ProductoDAO ProductoDAO=new ProductoDAO(new ConnectionFactory().recuperaConexion());
}

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

	public List<Producto> listar() {
		return ProductoDAO.listar();

	}

    public void guardar(Producto producto) {
    	ProductoDAO ProductoDAO=new ProductoDAO(new ConnectionFactory().recuperaConexion());
    	
    	ProductoDAO.guardar(producto);
    	
	}



}
