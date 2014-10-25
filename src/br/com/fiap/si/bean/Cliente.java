package br.com.fiap.si.bean;

public class Cliente {
	private Integer id;
	private String nome;
	private Integer cpf;
	private ContaBancaria contaCorrente;
	private ContaBancaria contaPoupanca;
	
	public Cliente() { }
	
	public Cliente(Integer id, String nome, Integer cpf) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
	}
	
	public Integer getCpf() {
		return cpf;
	}

	public void setCpf(Integer cpf) {
		this.cpf = cpf;
	}

	public ContaBancaria getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(ContaBancaria contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public ContaBancaria getContaPoupanca() {
		return contaPoupanca;
	}

	public void setContaPoupanca(ContaBancaria contaPoupanca) {
		this.contaPoupanca = contaPoupanca;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
}
