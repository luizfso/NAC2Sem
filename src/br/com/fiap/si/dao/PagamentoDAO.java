package br.com.fiap.si.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.fiap.si.bean.Pagamento;
import br.com.fiap.si.factory.ConnectionFactory;

public class PagamentoDAO {
Connection conn = null;
	
	public PagamentoDAO() {
		try{
			conn = ConnectionFactory.getConnection();
		}
		catch(SQLException ex){ }
	}
	
	public Pagamento getByBarcode(String barcode) {
		String sql = "SELECT ID, BARCODE, ID_CONTACEDENTE, ID_CONTASACADO, VALOR, VENCIMENTO "
				+ "FROM test.tb_pagamentos "
				+ "WHERE BARCODE = ?";
		
		Pagamento p = null;
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, barcode);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				p = new Pagamento();
				p.setId(rs.getInt("id"));
				p.setBarcode(rs.getString("barcode"));
				p.setIdContaCedente(rs.getInt("id_contaCedente"));
				p.setIdContaSacado(rs.getInt("id_contaSacado"));
				p.setValor(rs.getDouble("valor"));
				p.setVencimento(rs.getDate("vencimento"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally { }
		
		return p;
	}
}
