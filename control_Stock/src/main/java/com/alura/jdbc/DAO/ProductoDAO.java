package com.alura.jdbc.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;
import com.mysql.cj.conf.url.ReplicationDnsSrvConnectionUrl;

public class ProductoDAO {
	final private Connection con;
	public ProductoDAO(Connection con){
		this.con=con;
	}
	public void guardar(Producto producto) {
    	try(con){
    		final PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO PRODUCTO (nombre,descripcion,cantidad) VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS);
    		try(preparedStatement){
        			EjecutaRegistro(producto, preparedStatement);
        		}catch(SQLException e) {
        			throw new RuntimeException(e);
        		}
    	}catch(SQLException e) {
    		throw new RuntimeException();
    	}
	}
	private void EjecutaRegistro(Producto producto, PreparedStatement preparedStatement)
			throws SQLException {
			preparedStatement.setString(1, producto.getNombre());
			preparedStatement.setString(2, producto.getDescripcion());
			preparedStatement.setInt(3, producto.getCantidad());
			
			preparedStatement.execute();
			final ResultSet resultset = preparedStatement.getGeneratedKeys();
			try(resultset){
				while(resultset.next()) {
					producto.setId(resultset.getInt(1));
					System.out.println(String.format("Fue inserta el producto %s", producto ));
				}	
			}
			
		}
    public List<Producto> listar() {
        List<Producto> resultado = new ArrayList<>();

        try {
            final PreparedStatement statement = con
                    .prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
    
            try (statement) {
                statement.execute();
    
                final ResultSet resultSet = statement.getResultSet();
    
                try (resultSet) {
                    while (resultSet.next()) {
                        resultado.add(new Producto(
                                resultSet.getInt("ID"),
                                resultSet.getString("NOMBRE"),
                                resultSet.getString("DESCRIPCION"),
                                resultSet.getInt("CANTIDAD")));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }
    
}
