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
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_temas")
public class Tema {
	
	
	@Id // INDICA QUE ELA É A CHAVE PRIMARIA
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO INCREMENTA O ID
	private Long id;

	@NotNull(message = "O Atributo Descrição é obrigatório") // NÃO PODE SER EM BRANCO
	private String descricao;
	
	
	
	
	
	//@OneToMany: Diz que um tema pode ter várias postagens( 1 para muitos)
	//fetch = FetchType.LAZY: As postagens só são carregadas do banco quando você precisar delas. ( NÃO VEM TUDO DE UMA VEZ )
	// mappedBy = "tema": 
	//cascade = CascadeType.REMOVE: Se o tema for apagado, as postagens relacionadas e ele também serão apagadas.
	//@JsonIgnoreProperties("tema"): Evita que o campo tema apareça repetido nas postagens quando transformado em JSON.
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tema", cascade = CascadeType.REMOVE)// INDICA QUE A CLASSE TERA O LADO DE 1 PARA MUITOS
	@JsonIgnoreProperties("tema") // EVITAR QUE A PARTE TEMA NÃO FIQUE SE REPETINDO NA TABELA DE POSTAGEM
	private List<Postagem> postagem; // LISTA DE OBJETOS DA CLASSE POSTAGEM

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}
	
	

}
