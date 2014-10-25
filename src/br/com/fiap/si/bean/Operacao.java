package br.com.fiap.si.bean;

public class Operacao {
	private Integer id;
	private String descricao;
	
	public Operacao() { }
	
	public Operacao(Integer id) {
		this.id = id;
	}
	
	public Operacao(String descricao) {
		this.descricao = descricao;
	}
	
	public Operacao(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
