package br.com.fiap.si.bean;

public class ContaBancaria {
	private Integer id;
	private String agencia;
	private String conta;
	private Double saldo;
	private String senha;
	private Integer tipo;
	
	public ContaBancaria() { }
	
	public ContaBancaria(Integer id, String agencia, String conta) {
		this.id = id;
		this.agencia = agencia;
		this.conta = conta;
	}
	
	public ContaBancaria(Integer id, String agencia, String conta, Double saldo) {
		this.id = id;
		this.agencia = agencia;
		this.conta = conta;
		this.saldo = saldo;
	}
	
	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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
	
	public Double getSaldo() {
		return saldo;
	}
	
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public boolean saque(Double valor) {
		if(valor > 0 && saldo-valor >= 0) {
			saldo -= valor;
			return true;
		}
		
		return false;
	}
	
	public boolean deposito(Double valor) {
		if(valor > 0) {
			saldo += valor;
			return true;
		}
		
		return false;
	}
	
	public boolean transferencia(Double valor, ContaBancaria c) {
		if(valor > 0) {
			boolean sacado = this.saque(valor);
			
			if(sacado) {
				c.deposito(valor);
				return true;
			}
			else
				return false;
		}
		
		return false;
	}
}
