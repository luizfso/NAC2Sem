package br.com.fiap.si.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.fiap.si.bean.Operacao;
import br.com.fiap.si.factory.ConnectionFactory;

public class OperacaoDAO {
	Connection conn = null;
	
	public OperacaoDAO() {
		try{
			conn = ConnectionFactory.getConnection();
		}
		catch(SQLException ex){ }
	}
	
	public Operacao getByID(Integer id) {
		String sql = "SELECT id, descricao FROM tb_operacoes WHERE id = ?";
		Operacao op = null;
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				op = new Operacao();
				op.setId(rs.getInt("id"));
				op.setDescricao(rs.getString("descricao"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		finally { }
		
		return op;
	}
	
	public Operacao getByDescricao(String descricao) {
		String sql = "SELECT id, descricao FROM tb_operacoes WHERE descricao = ?";
		Operacao op = null;
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, descricao);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				op = new Operacao();
				op.setId(rs.getInt("id"));
				op.setDescricao(rs.getString("descricao"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		finally { }
		
		return op;
	}
}
