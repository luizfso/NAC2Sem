package br.com.fiap.si.managedbean;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.faces.bean.ManagedBean;

import br.com.fiap.si.bean.Movimentacao;
import br.com.fiap.si.bean.Sessao;
import br.com.fiap.si.dao.ContaDAO;
import br.com.fiap.si.dao.ExtratoDAO;
import br.com.fiap.si.dao.MovimentacaoDAO;
import br.com.fiap.si.dao.OperacaoDAO;
	
@ManagedBean
public class TransferenciaManagedBean {
	private Movimentacao mov;
	private Integer opcaoRadio;
	private String agencia;
	private String conta;
	private String dataStr;
	private String senha;
	private Boolean disabled = true;
	
	public TransferenciaManagedBean() {
		mov = new Movimentacao();
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDataStr() {
		return dataStr;
	}

	public void setDataStr(String dataStr) {
		this.dataStr = dataStr;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Integer getOpcaoRadio() {
		return opcaoRadio;
	}

	public void setOpcaoRadio(Integer opcaoRadio) {
		this.opcaoRadio = opcaoRadio;
		
		if(opcaoRadio == 2)
			disabled = false;
	}

	public Movimentacao getMov() {
		return mov;
	}

	public void setMov(Movimentacao mov) {
		this.mov = mov;
	}
	
	public Date converteStringToDate() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date data = (Date) formatter.parse(dataStr);
		
		return data;
	}
	
	public void gravaBanco(String operacao, LoginManagedBean login) {
		ContaDAO daoConta = new ContaDAO();
		OperacaoDAO daoOper = new OperacaoDAO();
		MovimentacaoDAO daoMov = new MovimentacaoDAO();
		ExtratoDAO daoExtrato = new ExtratoDAO();
		
		if(opcaoRadio == 2) {
			try {
				mov.setData(converteStringToDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			mov.setData(new Date(new java.util.Date().getTime()));
		
		mov.setOperacao(daoOper.getByDescricao(operacao));
		mov.setContaOrigem(login.getContaBancaria());
		mov.setContaDestino(daoConta.getByAgCon(agencia, conta));
		login.getContaBancaria().transferencia(mov.getValor(), mov.getContaDestino());
		daoConta.update(login.getContaBancaria());
		daoConta.update(mov.getContaDestino());
		daoMov.insert(mov);
		login.setUltimosLanc(daoExtrato.getUltimosLanc(login.getContaBancaria().getId()));
	}
	
	public String transferir() {
		Sessao sessao = new Sessao();
		
		if(sessao.getSessao().getAttribute("sessao") != null) {
			LoginManagedBean login = (LoginManagedBean) sessao.getSessao().getAttribute("sessao");
			
			if(agencia.equals(login.getAgencia()) && conta.equals(login.getConta()))
				return "erro-transferencia";
			
			if(login.getContaBancaria().getSenha().equals(senha)) {
				gravaBanco("Transferencia", login);
				return "comprovante";
			}
			else
				return "transferir";
		}
		
		return "login";
	}
}
