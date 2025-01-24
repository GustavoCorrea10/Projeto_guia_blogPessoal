package com.generation.blogpessoal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity // DIZ QUE ESSA CLASSE VAI ESTAR NO BANCO DE DADOS COMO UMA TABELA
@Table(name = "tb_usuarios") // NOME DA TABELA
public class Usuario {

	@Id // DIZ QUE É A CHAVE PRINCIPAL DA TABELA
	@GeneratedValue(strategy = GenerationType.IDENTITY)// GERA O ID AUTOMATICAMENTE PARA CASA USUARIO
	private Long id;

	@NotNull(message = "O Atributo Nome é Obrigatório!") // O CAMPO NÃ PODE ESTAR VAZIO
	private String nome;

	@NotNull(message = "O Atributo Usuário é Obrigatório!")
	@Email(message = "O Atributo Usuário deve ser um email válido!")// VERIFICA SE O VALOR É UM EMAIL VALIDO( TEM UM @ E GMAIL.COM)
	private String usuario;

	@NotBlank(message = "O Atributo Senha é Obrigatório!") // O CAMPO NÃO PODE ESTAR VAZIO OU SÓ TER ESPAÇOS EM BRANCO
	@Size(min = 8, message = "A Senha deve ter no mínimo 8 caracteres")
	private String senha;

	@Size(max = 5000, message = "O link da foto não pode ser maior do que 5000 caracteres")
	private String foto;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List<Postagem> postagem;
	
	//ONETOMANY                       (UM USUARIO PARA MUITAS POSTAGENS)
	//fetch = FetchType.LAZY          ( AS POSTAGENS SÓ SERÃO CARREGADAS NO BANCO QUANDO VOCÊ PEDIR)
    //mappedBy = "usuario"            ( MOSTAR QUE NO RELACIONAMENTO, A TABELA POSTAGEM TEM UM CAMPO CHAMADO USUARIO
    //cascade = CascadeType.REMOVE    ( SE UM USUARIO FOR EXCLUIDO, TODAS AS POSTAGENS RELACIONADAS A ELE TAMBEM SERÃO)
	//@JsonIgnoreProperties           ("usuario") (EVITA O LOOP INFINITO AO CONOVERTER DADOS DO BANCO PARA JSON)
	
	
	
	
	
	
	public Usuario(Long id, String nome, String usuario, String senha, String foto) {
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.foto = foto;
	}
	
	
	
	
	public Usuario() {
	}












	// GETTERS E SETTERS = SERVEM PARA ACESSAR (GET) OU ALTERAR (SET)
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

	public List<Postagem> getPostagem() {
		return this.postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}

}