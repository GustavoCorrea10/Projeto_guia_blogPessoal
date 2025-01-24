package com.generation.blogpessoal.model;
// VAI SE BASEAR NA CLASSE USUARIO
//A CLASSE USUARIO GUARDA AS INFORMAÇOES NO BANCO DE DADOS E CONSTRUIR A TABELA NO BANCO DE DADOS
public class UsuarioLogin {
	
	private Long id;
	private String nome;
	private String usuario;
	private String senha;
	private String foto;
	private String token; //  É UM ATRIBUTO EXTRA QUE NÃO EXISTE NA CLASSE USUARIO. ELE ARMAZENA UM TOKEN DE AUTENTICAÇÃO GERADO APOS O LOGIN

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}