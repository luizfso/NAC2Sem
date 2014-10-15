package br.com.nac2sem.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.nac2sem.beans.Cliente;
import br.com.nac2sem.factory.ConnectionFactory;

public class ClienteDAO {
	
	public void incluir(Cliente c) throws SQLException{
		
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "insert into pessoa values(?,?,?)";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, c.getNomeCliente());
		stmt.setInt(2, c.getIdade());
		stmt.setDouble(3, c.getSalario());
		
		stmt.executeUpdate();
		conn.close();

	}
	
	public void excluir(String pk) throws SQLException{
		
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "delete from pessoa where nome = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, pk);
				
		stmt.executeUpdate();
		conn.close();

	}
	
	public List<Cliente> listar()  throws SQLException{
		
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "select * from pessoa order by nome";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		ArrayList<Cliente> pessoas = new ArrayList<Cliente>();
		
		while(rs.next()){
			Cliente c = new Cliente();
			c.setNome(rs.getString("nome"));
			c.setIdade(rs.getInt("idade"));
			c.setSalario(rs.getDouble("salario"));
			
			clientes.add(c);
		}
		
		conn.close();
		
		return pessoas;
	}
}
