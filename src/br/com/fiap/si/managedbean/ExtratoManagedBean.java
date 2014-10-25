package br.com.fiap.si.managedbean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;

import br.com.fiap.si.bean.Mes;
import br.com.fiap.si.bean.Movimentacao;
import br.com.fiap.si.bean.Sessao;
import br.com.fiap.si.dao.ExtratoDAO;

@ManagedBean
public class ExtratoManagedBean {
	private List<Movimentacao> extrato;
	private List<Mes> meses;
	private List<Integer> anos;
	private Integer idMes;
	private Integer ano;
	private Boolean isSelected = false;
	
	public ExtratoManagedBean() {
		extrato = new ArrayList<Movimentacao>();
		meses = new ArrayList<Mes>();
		anos = new ArrayList<Integer>();
		Calendar cal = Calendar.getInstance();
		ano = cal.get(Calendar.YEAR);
		
		meses.add(new Mes(1, "Janeiro"));
		meses.add(new Mes(2, "Fevereiro"));
		meses.add(new Mes(3, "Março"));
		meses.add(new Mes(4, "Abril"));
		meses.add(new Mes(5, "Maio"));
		meses.add(new Mes(6, "Junho"));
		meses.add(new Mes(7, "Julho"));
		meses.add(new Mes(8, "Agosto"));
		meses.add(new Mes(9, "Setembro"));
		meses.add(new Mes(10, "Outubro"));
		meses.add(new Mes(11, "Novembro"));
		meses.add(new Mes(12, "Dezembro"));
		
		anos.add(ano);
		anos.add(ano-1);
		anos.add(ano-2);
	}
	
	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public List<Integer> getAnos() {
		return anos;
	}

	public void setAnos(List<Integer> anos) {
		this.anos = anos;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public List<Mes> getMeses() {
		return meses;
	}

	public void setMeses(List<Mes> meses) {
		this.meses = meses;
	}

	public List<Movimentacao> getExtrato() {
		return extrato;
	}
	
	public void setExtrato(List<Movimentacao> extrato) {
		this.extrato = extrato;
	}
	
	public Integer getIdMes() {
		return idMes;
	}

	public void setIdMes(Integer idMes) {
		this.idMes = idMes;
	}

	public String verExtrato() {
		Sessao sessao = new Sessao();
		
		if(sessao.getSessao() != null) {
			LoginManagedBean login = (LoginManagedBean) sessao.getSessao().getAttribute("sessao");
			ExtratoDAO dao = new ExtratoDAO();
			
			extrato = dao.getByPeriodo(login.getContaBancaria().getId(), idMes, ano);
			return "verExtrato";
		}
		
		return "login";
	}
}
