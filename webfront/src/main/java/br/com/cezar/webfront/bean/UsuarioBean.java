package br.com.cezar.webfront.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cezar.webfront.DAO.UsuarioDAO;
import br.com.cezar.webfront.entity.Usuario;

@ManagedBean(name="usuarioBean")
@ViewScoped
public class UsuarioBean {
	private Usuario usuario = new Usuario();

	public void cadastrar(){
		UsuarioDAO user = new UsuarioDAO();
		user.incluir(usuario);
		System.out.println(usuario.toString());
		
	}
	
	private void limpar() {
		usuario = new Usuario();

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
