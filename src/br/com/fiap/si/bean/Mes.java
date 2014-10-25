package br.com.fiap.si.bean;

public class Mes {
	private Integer id;
	private String mes;
	
	public Mes() {
		id = 0;
		mes = "";
	}
	
	public Mes(Integer id, String mes) {
		this.id = id;
		this.mes = mes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}
}
