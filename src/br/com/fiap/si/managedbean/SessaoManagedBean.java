package br.com.fiap.si.managedbean;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import br.com.fiap.si.bean.Sessao;

@ManagedBean
public class SessaoManagedBean {
	private String botaoClicado;
	private Sessao sessaoAtual;
	private Boolean isLogado;
	
	public SessaoManagedBean() {
		sessaoAtual = new Sessao();
	}

	public Boolean getIsLogado() {
		isLogado = false;
		
		if(sessaoAtual.getSessao() != null)
			if(sessaoAtual.getSessao().getAttribute("sessao") != null)
				isLogado = true;
		
		return isLogado;
	}
	
	public void setIsLogado(Boolean isLogado) {
		this.isLogado = isLogado;
	}

	public Sessao getSessaoAtual() {
		return sessaoAtual;
	}

	public void setSessaoAtual(Sessao sessaoAtual) {
		this.sessaoAtual = sessaoAtual;
	}

	public String getBotaoClicado() {
		return botaoClicado;
	}

	public void setBotaoClicado(String botaoClicado) {
		this.botaoClicado = botaoClicado;
	}
	
	public void irPara(ActionEvent e) {
		botaoClicado = e.getComponent().getId();
	}
	
	public String verificarSessao() {
		if(isLogado != null)
			return botaoClicado;
		
		return "login";
	}
}
