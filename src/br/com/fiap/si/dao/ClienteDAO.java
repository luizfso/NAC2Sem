package br.com.fiap.si.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.fiap.si.bean.Cliente;
import br.com.fiap.si.bean.ContaBancaria;
import br.com.fiap.si.factory.ConnectionFactory;

public class ClienteDAO {
	Connection conn = null;
	
	public ClienteDAO() {
		try{
			conn = ConnectionFactory.getConnection();
		}
		catch(SQLException ex){ }
	}
	
	public Cliente getByID(Integer id) {
		String sql = "SELECT c.id, nome, id_ContaCorrente, cc.agencia, cc.conta, cc.saldo, id_ContaPoupanca, cp.agencia, cp.conta, cp.saldo "
				+ "FROM tb_clientes c LEFT JOIN tb_contas cc "
				+ "ON idContaCorr = cc.id "
				+ "LEFT JOIN tb_contas cp "
				+ "ON idContaPoup = cp.id "
				+ "WHERE c.id = ?";
		
		Cliente c = null;
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				c = new Cliente();
				c.setId(rs.getInt("c.id"));
				c.setNome(rs.getString("nome"));
				c.setContaCorrente(new ContaBancaria(rs.getInt("idContaCorr"), rs.getString("cc.agencia"), rs.getString("cc.conta"), rs.getDouble("cc.saldo")));
				c.setContaPoupanca(new ContaBancaria(rs.getInt("idContaPoup"), rs.getString("cp.agencia"), rs.getString("cp.conta"), rs.getDouble("cp.saldo")));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		finally { }
		
		return c;
	}
	
	public Cliente getByConta(ContaBancaria cb) {
		String sql = "";
		
		if(cb.getTipo() == 1) {
			sql = "SELECT c.id, nome, id_ContaCorrente, agencia, conta, saldo "
					+ "FROM tb_clientes c INNER JOIN tb_contas cc "
					+ "ON id_ContaCorrente = cc.id "
					+ "WHERE cc.id = ?";
		}
		else {
			sql = "SELECT c.id, nome, id_ContaPoupanca, agencia, conta, saldo "
					+ "FROM tb_clientes c INNER JOIN tb_contas cp "
					+ "ON id_ContaPoupanca = cp.id "
					+ "WHERE cp.id = ?";
		}
		
		Cliente c = null;
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, cb.getId());
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				c = new Cliente();
				c.setId(rs.getInt("c.id"));
				c.setNome(rs.getString("nome"));
				
				if(cb.getTipo() == 1)
					c.setContaCorrente(new ContaBancaria(rs.getInt("id_ContaCorrente"), rs.getString("agencia"), rs.getString("conta"), rs.getDouble("saldo")));
				else
					c.setContaPoupanca(new ContaBancaria(rs.getInt("id_ContaPoupanca"), rs.getString("agencia"), rs.getString("conta"), rs.getDouble("saldo")));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		finally { }
		
		return c;
	}
}