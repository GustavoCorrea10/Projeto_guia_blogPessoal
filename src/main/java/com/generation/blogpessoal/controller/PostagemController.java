package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;





@RestController    // DEFINE AO SPRING QUE ESSA CLASSE É UMA CONTROLLER E VAI RESPONDERAS REQUISIÇÕES FEITAS A API
@RequestMapping("/postagens")  // Define UM "ENDPOINT" NA API: QUANDO ALGUEM ACESSAR o ENDEREÇO /POSTAGEM (DEFINIDO POR( @RequestMapping("/postagens")), as REQUISIÇÕES SERÃO TRATADAS POR ESSA CLASSE.
@CrossOrigin(origins = "*", allowedHeaders = "*") // PERMITE QUE a API SEJA ACESSADA DE QUALQUER LUGAR (QUALQUER DOMINIO OU APLICATIVO web)
public class PostagemController {

	
	
	
	
	
	// ESSA ANOTAÇÃO INJETA AUTOMATICAMENTE O OBJETO POSTAGEMREPOSITORY. OU SEJA:

	// O SPRING CRIA UM OBJETO DE POSTAGEMREPOSITORY NOS BASTIDORES.
	// ESSE OBJETO É ATRIBUÍDO À VARIÁVEL POSTAGEMREPOSITORY.
	// ASSIM, VOCÊ PODE USAR OS MÉTODOS DO POSTAGEMREPOSITORY PARA ACESSAR O BANCO DE DADOS.


	@Autowired // O SPRING DÁ AUTONOMIA PARA A INTERFACE PODER INVOCAR OS METODOS
	private PostagemRepository postagemRepository;
	
	
	
	
	
	
	
	@GetMapping // INDICA QUE ESSE MÉTODO É CHAMADO EM VERBOS/METODOS HTTP DO TIPO GET
	public ResponseEntity<List<Postagem>> getAll(){ // O METODO GETALL QAUNDO FOR CHAMADO ELE VAI TRAZER UMA LISTA COM OS OBJETOS DE POSTAGEM
		return ResponseEntity.ok(postagemRepository.findAll()); //SELECT * FROM tb_postagem
	}
	
	
	// ANOTAÇÃO:
	// @GETMAPPING
	//
	// INDICA QUE O MÉTODO GETALL() SERÁ CHAMADO QUANDO HOUVER UMA REQUISIÇÃO HTTP DO TIPO GET NO ENDPOINT /POSTAGENS.
	// EXEMPLO: QUANDO O USUÁRIO DIGITA NO NAVEGADOR HTTP://LOCALHOST:8080/POSTAGENS.


	// ASSINATURA DO MÉTODO:

	// RESPONSEENTITY: ENVOLVE A RESPOSTA DA API. É USADO PARA:
	// RETORNAR O STATUS HTTP (EX.: 200 OK, 404 NOT FOUND, ETC.).
	
	// RETORNAR O CONTEÚDO DA RESPOSTA (NESTE CASO, UMA LISTA DE POSTAGENS EM FORMATO JSON).
	// LIST<POSTAGEM>: SIGNIFICA QUE A RESPOSTA SERÁ UMA LISTA DE OBJETOS DO TIPO POSTAGEM.


	// CORPO DO MÉTODO:
	// POSTAGEMREPOSITORY.FINDALL():

	// CHAMA O MÉTODO DO POSTAGEMREPOSITORY PARA BUSCAR TODAS AS POSTAGENS NO BANCO DE DADOS.
	// ESSE MÉTODO JÁ VEM PRONTO PORQUE O POSTAGEMREPOSITORY HERDA DO JPAREPOSITORY.
	// EQUIVALE AO COMANDO SQL: SELECT * FROM TB_POSTAGEM.
	
	// RESPONSEENTITY.OK(...):
	// ENVOLVE O RESULTADO (FINDALL()) E RETORNA COM O STATUS HTTP 200 OK.










}
