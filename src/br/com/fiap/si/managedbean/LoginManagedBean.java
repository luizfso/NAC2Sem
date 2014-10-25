package br.com.fiap.si.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.fiap.si.bean.Cliente;
import br.com.fiap.si.bean.ContaBancaria;
import br.com.fiap.si.bean.Movimentacao;
import br.com.fiap.si.bean.Sessao;
import br.com.fiap.si.dao.ClienteDAO;
import br.com.fiap.si.dao.ContaDAO;
import br.com.fiap.si.dao.ExtratoDAO;

@ManagedBean
@SessionScoped
public class LoginManagedBean {
	private Cliente cliente;
	private ContaBancaria contaBancaria;
	private String agencia;
	private String conta;
	private String senha;
	private List<Movimentacao> ultimosLanc;
	private Boolean isContaCorrente = false;
	private String msg;
	
	public LoginManagedBean() {
		cliente = new Cliente();
		contaBancaria = new ContaBancaria();
		ultimosLanc = new ArrayList<Movimentacao>();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Boolean getIsContaCorrente() {
		return isContaCorrente;
	}

	public void setIsContaCorrente(Boolean isContaCorrente) {
		this.isContaCorrente = isContaCorrente;
	}

	public List<Movimentacao> getUltimosLanc() {
		return ultimosLanc;
	}

	public void setUltimosLanc(List<Movimentacao> ultimosLanc) {
		this.ultimosLanc = ultimosLanc;
	}

	public ContaBancaria getContaBancaria() {
		return contaBancaria;
	}

	public void setContaBancaria(ContaBancaria contaBancaria) {
		this.contaBancaria = contaBancaria;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public String verificarConta() {
		setMsg("");
		ContaDAO daoConta = new ContaDAO();
		
		contaBancaria = daoConta.getByAgCon(agencia, conta);
		
		if(contaBancaria != null) {
			ClienteDAO daoCliente = new ClienteDAO();
			
			cliente = daoCliente.getByConta(contaBancaria);
			
			if(cliente != null)
				return "pagina-senha";
			
			setMsg("Cliente não encontrado!");
			return "login";
		}
		
		setMsg("Conta bancária inválida!");
		return "login";
	}
	
	public String efetuarLogout() {
		Sessao sessao = new Sessao();
		
		if(sessao.getSessao().getAttribute("sessao") != null) {
			sessao.getSessao().invalidate();
			sessao.setSessao(null);
		}
		
		setMsg("Sessão finalizada!");
		return "login";
	}
	
	public String efetuarLogin() {
		if(contaBancaria.getSenha().equals(senha)) {
			Sessao autenticacao = new Sessao();
			
			autenticacao.getSessao().setAttribute("sessao", this);
			ultimosLanc = new ExtratoDAO().getUltimosLanc(contaBancaria.getId());
			
			if(contaBancaria.getTipo() == 1)
				isContaCorrente = true;
			
			return "pagina-principal";
		}
		
		return "senha";
	}
	
	public void validaSenha(FacesContext context, UIComponent componentToValidate, Object value) throws ValidatorException {
		String senhaV = (String) value;
		FacesMessage message;
		
		if(!contaBancaria.getSenha().equals(senhaV)) {
			message = new FacesMessage("Senha inválida!");
			throw new ValidatorException(message);
		}
	}
}
