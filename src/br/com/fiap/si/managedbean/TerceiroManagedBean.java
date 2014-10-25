package br.com.fiap.si.managedbean;

import br.com.fiap.si.bean.ContaBancaria;
import br.com.fiap.si.bean.Sessao;
import br.com.fiap.si.bean.Terceiro;
import br.com.fiap.si.dao.ContaDAO;
import br.com.fiap.si.dao.TerceiroDAO;

public class TerceiroManagedBean {
	private Terceiro terceiro;
	private String agencia;
	private String conta;
	private String msg;
	
	public TerceiroManagedBean() {
		terceiro = new Terceiro();
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

	public Terceiro getTerceiro() {
		return terceiro;
	}

	public void setTerceiro(Terceiro terceiro) {
		this.terceiro = terceiro;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String cadastrar() {
		Sessao sessao = new Sessao();
		
		if(sessao.getSessao() != null) {
			LoginManagedBean login = (LoginManagedBean) sessao.getSessao().getAttribute("sessao");
			ContaDAO daoConta = new ContaDAO();
			ContaBancaria contaTerceiro = daoConta.getByAgCon(agencia, conta);
			
			if(contaTerceiro != null) {
				if(contaTerceiro.getId() != login.getContaBancaria().getId()) {
					terceiro.setIdConta(login.getContaBancaria().getId());
					terceiro.setIdContaTerceiro(contaTerceiro.getId());
					TerceiroDAO daoTerceiro = new TerceiroDAO();
					
					daoTerceiro.insert(terceiro);
					setMsg("Cadastro realizado com sucesso!");
				}
				else
					setMsg("Conta de terceiro não pode ser a mesma que a sua!");
			}
			else
				setMsg("Conta bancária não existe!");
			
			return "cadastroTerceiros";
		}
		
		return "login";
	}
}
