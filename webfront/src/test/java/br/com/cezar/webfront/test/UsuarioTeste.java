package br.com.cezar.webfront.test;

import java.util.List;

import br.com.cezar.webfront.DAO.UsuarioDAO;
import br.com.cezar.webfront.entity.Usuario;

public class UsuarioTeste {
 
	public static void main(String[] args) {
		new UsuarioTeste().incluirUsuario();
	}
 
 
 
	public void incluirUsuario() {
		System.out.println(this.getClass() + "Inicio");
 
		try {
			// Cria a instância de um classe de acesso a Dados
			UsuarioDAO dao = new UsuarioDAO();
 
 
			//Cria um objeto usuario informando apenas o nome
			Usuario usuario1 = new Usuario();
			usuario1.setNome("Jose da Silva");
			usuario1.setLogin("jose");
			usuario1.setSenha("abcd@1234");
 
			//Cria um objeto usuario informando apenas o nome
			Usuario usuario2 = new Usuario();
			usuario2.setNome("Maria da Silva");
			usuario2.setLogin("maria");
			usuario2.setSenha("neuro#5678");
 
			//Realiza a inclusão de um usuario
			dao.incluir(usuario1);
 
			//Realiza a inclusão de um usuario
			dao.incluir(usuario2);
 
 
			// Consulta a lista de usuários cadastrados no Banco
			List<Usuario> listaUsuario = dao.listar();
 
 
 
			// Realiza um loop para exibir todos os registro existentes no Banco de dados
			System.out.println("==================  Usuarios Cadastrados ============================");
			for (Usuario a : listaUsuario) {
				System.out.println(a);
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(this.getClass() + "Fim");
	}
}
