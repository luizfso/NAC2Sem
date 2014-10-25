package br.com.fiap.si.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.si.bean.ContaBancaria;
import br.com.fiap.si.bean.Movimentacao;
import br.com.fiap.si.bean.Operacao;
import br.com.fiap.si.factory.ConnectionFactory;

public class ExtratoDAO {
	Connection conn = null;
	
	public ExtratoDAO() {
		try{
			conn = ConnectionFactory.getConnection();
		}
		catch(SQLException ex){ }
	}
	
	public List<Movimentacao> getUltimosLanc(Integer id) {
		String sql = "SELECT m.id, data, id_Operacao, descricao, valor, id_ContaDestino, cd.agencia, cd.conta "
				+ "From	test.tb_movimentacoes m INNER JOIN test.tb_operacoes o "
				+ "On id_Operacao = o.id "
				+ "LEFT JOIN test.tb_contas cd "
				+ "On IFNULL(id_ContaDestino, 0) = cd.id "
				+ "WHERE id_ContaOrigem = ? AND status = 1 "
				+ "ORDER BY data DESC, m.id DESC LIMIT 5"; 
		
		List<Movimentacao> movs = null;
		Movimentacao m = null;
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			movs = new ArrayList<Movimentacao>();
			
			while(rs.next()) {
				m = new Movimentacao();
				m.setId(rs.getInt("m.id"));
				m.setData(rs.getDate("data"));
				m.setOperacao(new Operacao(rs.getInt("id_Operacao"), rs.getString("descricao")));
				m.setValor(rs.getDouble("valor"));
				m.setContaDestino(new ContaBancaria(rs.getInt("id_ContaDestino"), rs.getString("cd.agencia"), rs.getString("cd.conta")));
				movs.add(m);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally { }
		
		return movs;
	}
	
	public List<Movimentacao> getByData(Integer id, Date ini, Date fim) {
		String sql = "SELECT m.id, data, id_Operacao, descricao, valor, id_ContaDestino, cd.agencia, cd.conta "
				+ "From	test.tb_movimentacoes m INNER JOIN test.tb_operacoes o "
				+ "On id_Operacao = o.id "
				+ "LEFT JOIN test.tb_contas cd "
				+ "On IFNULL(id_ContaDestino, 0) = cd.id "
				+ "WHERE id_ContaOrigem = ? AND data BETWEEN ? AND ? AND status = 1 "
				+ "ORDER BY data"; 
		
		List<Movimentacao> movs = null;
		Movimentacao m = null;
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.setDate(2, ini);
			stmt.setDate(3, fim);
			
			ResultSet rs = stmt.executeQuery();
			movs = new ArrayList<Movimentacao>();
			
			while(rs.next()) {
				m = new Movimentacao();
				m.setId(rs.getInt("m.id"));
				m.setData(rs.getDate("data"));
				m.setOperacao(new Operacao(rs.getInt("id_Operacao"), rs.getString("descricao")));
				m.setValor(rs.getDouble("valor"));
				m.setContaDestino(new ContaBancaria(rs.getInt("id_ContaDestino"), rs.getString("cd.agencia"), rs.getString("cd.conta")));
				movs.add(m);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally { }
		
		return movs;
	}
	
	public List<Movimentacao> getByPeriodo(Integer id, Integer mes, Integer ano) {
		String sql = "SELECT m.id, data, id_Operacao, descricao, valor, id_ContaDestino, cd.agencia, cd.conta "
				+ "From	test.tb_movimentacoes m INNER JOIN test.tb_operacoes o "
				+ "On id_Operacao = o.id "
				+ "LEFT JOIN test.tb_contas cd "
				+ "On IFNULL(id_ContaDestino, 0) = cd.id "
				+ "WHERE id_ContaOrigem = ? AND Month(data) = ? AND Year(data) = ? AND status = 1 "
				+ "ORDER BY data";
		
		List<Movimentacao> movs = null;
		Movimentacao m = null;
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.setInt(2, mes);
			stmt.setInt(3, ano);
			
			ResultSet rs = stmt.executeQuery();
			movs = new ArrayList<Movimentacao>();
			
			while(rs.next()) {
				m = new Movimentacao();
				m.setId(rs.getInt("m.id"));
				m.setData(rs.getDate("data"));
				m.setOperacao(new Operacao(rs.getInt("id_Operacao"), rs.getString("descricao")));
				m.setValor(rs.getDouble("valor"));
				m.setContaDestino(new ContaBancaria(rs.getInt("id_ContaDestino"), rs.getString("cd.agencia"), rs.getString("cd.conta")));
				movs.add(m);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally { }
		
		return movs;
	}
}
