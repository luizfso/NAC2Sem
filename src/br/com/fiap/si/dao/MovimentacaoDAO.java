package br.com.fiap.si.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.fiap.si.bean.ContaBancaria;
import br.com.fiap.si.bean.Movimentacao;
import br.com.fiap.si.bean.Operacao;
import br.com.fiap.si.factory.ConnectionFactory;

public class MovimentacaoDAO {
	Connection conn = null;
	
	public MovimentacaoDAO() {
		try{
			conn = ConnectionFactory.getConnection();
		}
		catch(SQLException ex){ }
	}
	
	public void insert(Movimentacao m) {
		String sql;
		
		if(m.getOperacao().getDescricao().equals("Transferência") || m.getOperacao().getDescricao().equals("Pagamento"))
			sql = "INSERT INTO tb_movimentacoes (data, id_Operacao, valor, id_ContaOrigem, id_ContaDestino, status) VALUES (?, ?, ?, ?, ?, ?)";
		else
			sql = "INSERT INTO tb_movimentacoes (data, id_Operacao, valor, id_ContaOrigem, status) VALUES (?, ?, ?, ?, ?)";
		
		try{
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setDate(1, m.getData());
			stmt.setInt(2, m.getOperacao().getId());
			stmt.setDouble(3, m.getValor());
			stmt.setInt(4, m.getContaOrigem().getId());
			
			if(m.getOperacao().getDescricao().equals("Transferência") || m.getOperacao().getDescricao().equals("Pagamento")) {
				stmt.setInt(5, m.getContaDestino().getId());
				stmt.setInt(6, m.getStatus());
			}
			else
				stmt.setInt(5, m.getStatus());
			
			stmt.executeUpdate();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		finally{ }
	}
	
	public Movimentacao getByID(Integer id) {
		String sql = "SELECT m.ID, data, DESCRICAO, VALOR, co.AGENCIA, co.CONTA, cd.AGENCIA, cd.CONTA, STATUS "
				+ "FROM test.tb_movimentacoes m INNER JOIN test.tb_operacoes o "
				+ "ON ID_OPERACAO = o.ID "
				+ "INNER JOIN test.tb_contas co "
				+ "ON ID_CONTAORIGEM = co.ID "
				+ "LEFT JOIN test.tb_contas cd "
				+ "ON ID_CONTADESTINO = cd.ID "
				+ "WHERE m.id = ?";
		
		Movimentacao m = null;
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				m = new Movimentacao();
				m.setId(rs.getInt("m.id"));
				m.setData(rs.getDate("data"));
				m.setOperacao(new Operacao(rs.getString("descricao")));
				m.setValor(rs.getDouble("valor"));
				m.setContaOrigem(new ContaBancaria(rs.getInt("id_ContaOrigem"), rs.getString("co.agencia"), rs.getString("co.conta")));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally { }
		
		return m;
	}
}
