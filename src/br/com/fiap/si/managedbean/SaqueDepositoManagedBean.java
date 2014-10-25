package br.com.fiap.si.managedbean;

import java.sql.Date;

import br.com.fiap.si.bean.Movimentacao;
import br.com.fiap.si.bean.Sessao;
import br.com.fiap.si.dao.ContaDAO;
import br.com.fiap.si.dao.ExtratoDAO;
import br.com.fiap.si.dao.MovimentacaoDAO;
import br.com.fiap.si.dao.OperacaoDAO;

public class SaqueDepositoManagedBean {
	private Movimentacao mov;
	private String senha;
	
	public SaqueDepositoManagedBean() {
		mov = new Movimentacao();
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Movimentacao getMov() {
		return mov;
	}

	public void setMov(Movimentacao mov) {
		this.mov = mov;
	}
	
	public void gravaBanco(String operacao, LoginManagedBean login) {
		ContaDAO daoConta = new ContaDAO();
		OperacaoDAO daoOper = new OperacaoDAO();
		MovimentacaoDAO daoMov = new MovimentacaoDAO();
		ExtratoDAO daoExtrato = new ExtratoDAO();
		
		mov.setData(new Date(new java.util.Date().getTime()));
		mov.setOperacao(daoOper.getByDescricao(operacao));
		mov.setContaOrigem(login.getContaBancaria());
		
		if(operacao.equals("Saque"))
			login.getContaBancaria().saque(mov.getValor());
		else if(operacao.equals("Depósito"))
			login.getContaBancaria().deposito(mov.getValor());
		
		mov.setStatus(1);
		System.out.println(mov.getData());
		System.out.println(mov.getContaOrigem().getId());
		System.out.println(mov.getOperacao().getDescricao());
		System.out.println(mov.getValor());
		
		daoConta.update(login.getContaBancaria());
		daoMov.insert(mov);
		login.setUltimosLanc(daoExtrato.getUltimosLanc(login.getContaBancaria().getId()));
	}
	
	public String sacar() {
		Sessao sessao = new Sessao();
		
		if(sessao.getSessao().getAttribute("sessao") != null) {
			LoginManagedBean login = (LoginManagedBean) sessao.getSessao().getAttribute("sessao");
			
			if(login.getContaBancaria().getSenha().equals(senha)) {
				gravaBanco("Saque", login);
				return "comprovante";
			}
			else
				return "sacar";
		}
		
		return "login";
	}
	
	public String depositar() {
		Sessao sessao = new Sessao();
		
		if(sessao.getSessao().getAttribute("sessao") != null) {
			LoginManagedBean login = (LoginManagedBean) sessao.getSessao().getAttribute("sessao");
			
			if(login.getContaBancaria().getSenha().equals(senha)) {
				gravaBanco("Depósito", login);
				return "comprovante";
			}
			else
				return "depositar";
		}
		
		return "login";
	}
}
