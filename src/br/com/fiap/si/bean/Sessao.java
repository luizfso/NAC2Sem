package br.com.fiap.si.bean;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class Sessao {
	private HttpSession sessao;

	public Sessao() {
		sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	
	public HttpSession getSessao() {
		return sessao;
	}

	public void setSessao(HttpSession sessao) {
		this.sessao = sessao;
	}
	
	public boolean existeSessao() {
		if(sessao != null)
			return true;
		
		return false;
	}
}
