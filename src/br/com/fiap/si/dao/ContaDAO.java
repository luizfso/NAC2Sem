package br.com.fiap.si.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.fiap.si.bean.ContaBancaria;
import br.com.fiap.si.factory.ConnectionFactory;

public class ContaDAO {
	Connection conn = null;
	
	public ContaDAO() {
		try{
			conn = ConnectionFactory.getConnection();
		}
		catch(SQLException ex){ }
	}
	
	public void update(ContaBancaria c) {
		String sql = "UPDATE tb_contas SET saldo = ? WHERE id = ?";
		
		try{
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setDouble(1, c.getSaldo());
			stmt.setInt(2, c.getId());
			
			stmt.executeUpdate();

		}
		catch(SQLException ex){ 
			ex.printStackTrace();
		}
		finally{ }
	}
	
	public ContaBancaria getByID(Integer id) {
		String sql = "SELECT id, agencia, conta, saldo, senha, tipo FROM tb_contas WHERE id = ?";
		
		ContaBancaria c = null;
		
		try{
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				c = new ContaBancaria();
				c.setId(rs.getInt("id"));
				c.setAgencia(rs.getString("agencia"));
				c.setConta(rs.getString("conta"));
				c.setSaldo(rs.getDouble("saldo"));
				c.setSenha(rs.getString("senha"));
				c.setTipo(rs.getInt("tipo"));
			}
			
		}
		catch(SQLException ex){ 
			ex.printStackTrace();
		}
		finally{ }
		
		return c;
	}
	
	public ContaBancaria getByAgCon(String agencia, String conta) {
		String sql = "SELECT id, agencia, conta, saldo, senha, tipo FROM tb_contas WHERE agencia = ? AND conta = ?";
		
		ContaBancaria c = null;
		
		try{
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, agencia);
			stmt.setString(2, conta);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				c = new ContaBancaria();
				c.setId(rs.getInt("id"));
				c.setAgencia(rs.getString("agencia"));
				c.setConta(rs.getString("conta"));
				c.setSaldo(rs.getDouble("saldo"));
				c.setSenha(rs.getString("senha"));
				c.setTipo(rs.getInt("tipo"));
			}
			
		}
		catch(SQLException ex){ 
			ex.printStackTrace();
		}
		finally{ }
		
		return c;
	}
}
