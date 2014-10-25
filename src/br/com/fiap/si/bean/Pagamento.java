package br.com.fiap.si.bean;

import java.sql.Date;

public class Pagamento {
	private Integer id;
	private String barcode;
	private Integer idContaCedente;
	private Integer idContaSacado;
	private Double valor;
	private Date vencimento;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		/*Date hoje = new Date(new java.util.Date().getTime());
		long fatorVenc = (hoje.getTime() - getVencimento().getTime())/86400000; //86400000 milissegundos = 1 dia
		String valorStr = valor.toString();
		String valorBol = "";
		String zeros = "";
		int qtdZeros = 10-(valorStr.length()-1);
		
		for(int i=0; i<valorStr.length(); i++) {
			if(valorStr.charAt(i) != '.')
				valorBol += valorStr.charAt(i);
		}
		
		for(int i=1; i<=qtdZeros; i++)
			zeros += '0';
		
		this.barcode = "1239000005000000000600000000078" + fatorVenc + zeros + valorBol;*/
		this.barcode = barcode;
	}

	public Integer getIdContaCedente() {
		return idContaCedente;
	}

	public void setIdContaCedente(Integer idContaCedente) {
		this.idContaCedente = idContaCedente;
	}

	public Integer getIdContaSacado() {
		return idContaSacado;
	}

	public void setIdContaSacado(Integer idContaSacado) {
		this.idContaSacado = idContaSacado;
	}

	public Double getValor() {
		return valor;
	}
	
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public Date getVencimento() {
		return vencimento;
	}
	
	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}
}
