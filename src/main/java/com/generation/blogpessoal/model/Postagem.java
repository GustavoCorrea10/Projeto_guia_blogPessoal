package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity// INDICA QUE ESSA CLASSE É UMA TABELA
@Table(name = "tb_postagem") // INDICA O NOME DA TABELA NO BANCO DE DADOS
public class Postagem {

	@Id  // ELE É A CHAVE PRIMARIA (PRIMARY KEY)
	@GeneratedValue(strategy = GenerationType.IDENTITY) // INDICA O AUTO INCREMENTO
 	private Long id;
		
	@NotBlank(message = "Esse campo é obrigatório!") // NAO PERMITE QUE ESPAÇOS EM BRANCOS SEJAM ADICIONADOS
	@Size(min = 5, max = 100, message = "Digite no minimo 5 e no maximo 100 caracteres") // A QUANTIDADE DE CARACTERES PERMITIDOS COM UMA MENSAGEM CASO O USUARIO NÃO USE A REGRA
	private String titulo;
	
	@NotBlank(message = "Esse campo é obrigatório!") // NAO PERMITE QUE ESPAÇOS EM BRANCOS SEJAM ADICIONADOS
	@Size(min = 10, max = 1000, message = "Digite no minimo 10 e no maximo 1000 caracteres") // A QUANTIDADE DE CARACTERES PERMITIDOS COM UMA MENSAGEM CASO O USUARIO NÃO USE A REGRA
	private String texto;
	
	@UpdateTimestamp  //FAZ COM QUE O CAMPO data RECEBA AUTOMATICAMENTE a data e HORA do MOMENTO EM QUE o REGISTRO FOI CRIADO OU ATUALIZADO.
	private LocalDateTime data; // data (10/10/25 e a HORA)
	
	
	
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	// INDICA QUE QUE NA TABELA POSTAGEM TERA UM CAMPO CHAMADO TEMA QUE É UMA REFERENCIA AO OBJETO TEMA
	
	
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
	public Tema getTema() {
		return tema;
	}
	public void setTema(Tema tema) {
		this.tema = tema;
	}
	
	
	
	
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
	
	
	
	
}
