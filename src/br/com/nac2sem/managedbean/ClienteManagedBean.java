package br.com.nac2sem.managedbean;

import java.sql.SQLException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.nac2sem.beans.Cliente;
import br.com.nac2sem.DAO.ClienteDAO;

@ManagedBean
@SessionScoped
public class ClienteManagedBean {

	private Cliente cliente = new Cliente();
	private List<Cliente> listaClientes;
	
	private String pk;
	
	public void setPk(String pk) {
		this.pk = pk;
	}

	public List<Cliente> getListaPessoas() {
		return listaClientes;
	}

	public Cliente getPessoa() {
		return cliente;
	}

	public void setPessoa(Cliente cliente) {
		this.cliente = cliente;
	}

	public String incluirPessoa(){
		
		String resultado = "";
		
		ClienteDAO dao = new ClienteDAO();
		
		try{
			dao.incluir(cliente);
			
			if(cliente.getSalario() <= 2000)
				resultado = "resultado1";
			else if(cliente.getSalario() <= 5000)
				resultado = "resultado2";
			else if(cliente.getSalario() <= 10000)
				resultado = "resultado3";
			else 
				resultado = "resultado4";
			
		}catch(SQLException e){
			resultado = "erro";
		}
		
		return resultado;
	}
	
	public String listar(){
		
		String resultado = "mostrar";
		
		ClienteDAO dao = new ClienteDAO();
		
		try{
			listaClientes = dao.listar();
			
		}catch(SQLException e){
			resultado = "erro";
		}
		
		return resultado;
		
	}
	
	
	public String excluir(){
		
		String resultado = "entrada";
		
		ClienteDAO dao = new ClienteDAO();
		
		try{
			dao.excluir(pk);
			
		}catch(SQLException e){
			resultado = "erro";
		}
		
		return resultado;
		
	}
	
}
