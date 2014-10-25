package br.com.fiap.si.managedbean;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.fiap.si.bean.Movimentacao;
import br.com.fiap.si.bean.Pagamento;
import br.com.fiap.si.bean.Sessao;
import br.com.fiap.si.dao.ContaDAO;
import br.com.fiap.si.dao.ExtratoDAO;
import br.com.fiap.si.dao.MovimentacaoDAO;
import br.com.fiap.si.dao.OperacaoDAO;
import br.com.fiap.si.dao.PagamentoDAO;
	
@ManagedBean
public class MovimentacaoManagedBean {
	private Movimentacao mov;
	private Pagamento pagamento;
	private Integer opcaoRadio;
	private String agencia;
	private String conta;
	private String dataStr;
	private String senha;
	private String barcode;
	private Boolean disabled = true;
	
	public MovimentacaoManagedBean() {
		mov = new Movimentacao();
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
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

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
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
		PagamentoDAO daoPagto = new PagamentoDAO();
		
		mov.setOperacao(daoOper.getByDescricao(operacao));
		mov.setContaOrigem(login.getContaBancaria());
		
		if(operacao.equals("Saque") || operacao.equals("Depósito") || opcaoRadio == 1) {
			mov.setData(new Date(new java.util.Date().getTime()));
			mov.setStatus(1);
		}
		else if((operacao.equals("Transferência") || operacao.equals("Pagamento")) && opcaoRadio == 2){
			try {
				mov.setData(converteStringToDate());
				mov.setStatus(2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(operacao.equals("Saque"))
			login.getContaBancaria().saque(mov.getValor());
		else if(operacao.equals("Depósito"))
			login.getContaBancaria().deposito(mov.getValor());
		else if(operacao.equals("Pagamento")) {
			Pagamento p = daoPagto.getByBarcode(barcode);
			mov.setContaDestino(daoConta.getByID(p.getIdContaCedente()));
			login.getContaBancaria().transferencia(mov.getValor(), mov.getContaDestino());
			daoConta.update(mov.getContaDestino());
		}
		else if(operacao.equals("Transferência")) {
			mov.setContaDestino(daoConta.getByAgCon(agencia, conta));
			login.getContaBancaria().transferencia(mov.getValor(), mov.getContaDestino());
			daoConta.update(mov.getContaDestino());
		}
		
		daoConta.update(login.getContaBancaria());
		daoMov.insert(mov);
		login.setUltimosLanc(daoExtrato.getUltimosLanc(login.getContaBancaria().getId()));
	}
	
	public String pesquisarBarcode() {
		PagamentoDAO dao = new PagamentoDAO();
		Pagamento p = dao.getByBarcode(barcode);
		
		mov.setValor(p.getValor());
		return "pagar2";
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
	
	public String pagar() {
		Sessao sessao = new Sessao();
		
		if(sessao.getSessao().getAttribute("sessao") != null) {
			LoginManagedBean login = (LoginManagedBean) sessao.getSessao().getAttribute("sessao");
			
			if(login.getContaBancaria().getSenha().equals(senha)) {
				gravaBanco("Pagamento", login);
				return "comprovante";
			}
			else
				return "pagar";
		}
		
		return "login";
	}
	
	public void validaBarcode(FacesContext context, UIComponent componentToValidate, Object value) throws ValidatorException {
		Sessao sessao = new Sessao();
		
		if(sessao.getSessao().getAttribute("sessao") != null) {
			LoginManagedBean login = (LoginManagedBean) sessao.getSessao().getAttribute("sessao");
			String codigoBarras = (String) value;
			FacesMessage message;
			PagamentoDAO dao = new PagamentoDAO();
			Pagamento p = dao.getByBarcode(codigoBarras);
			
			if(p == null) {
				message = new FacesMessage("Código de barras inválido!");
				throw new ValidatorException(message);
			}
			
			if(p.getIdContaCedente() == login.getContaBancaria().getId()) {
				message = new FacesMessage("Beneficiário não pode ser o mesmo que o pagador!");
				throw new ValidatorException(message);
			}
		}
	}
}
