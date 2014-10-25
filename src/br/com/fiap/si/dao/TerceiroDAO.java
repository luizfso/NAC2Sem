package br.com.fiap.si.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.fiap.si.bean.Terceiro;
import br.com.fiap.si.factory.ConnectionFactory;

public class TerceiroDAO {
	Connection conn = null;
	
	public TerceiroDAO() {
		try{
			conn = ConnectionFactory.getConnection();
		}
		catch(SQLException ex){ }
	}
	
	public void insert(Terceiro t) {
		String sql = "INSERT INTO tb_terceiros (id_conta, id_contaTerceiro) VALUES (?, ?)";
		
		try{
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, t.getIdConta());
			stmt.setInt(2, t.getIdContaTerceiro());
			
			stmt.executeUpdate();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		finally{ }
	}
	
	public Terceiro getByID(Integer id) {
		String sql = "SELECT id_contaTerceiro, agencia, conta "
				+ "FROM test.tb_contas co INNER JOIN test.tb_terceiros t " 
				+ "ON id_contaterceiro = co.id "
				+ "WHERE id_conta = ?";
		
		Terceiro t = null;
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				t = new Terceiro();
				t.setId(rs.getInt("t.id"));
				t.setIdConta(id);
				t.setIdContaTerceiro(rs.getInt("id_contaTerceiro"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally { }
		
		return t;
	}
}
