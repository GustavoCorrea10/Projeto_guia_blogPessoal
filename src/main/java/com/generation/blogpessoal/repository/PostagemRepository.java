package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.blogpessoal.model.Postagem;




//COMO FUNCIONA?
//HERDADO o JpaRepository:
//A interface PostagemRepository ESTENDE (extends) a CLASSE JpaRepository. 
//ISSO QUER DIZER QUE ELA JÁ "GANHA" UM MONTE DE FUNÇÕES PRONTAS PARA ACESSAR o BANCO DE DADOS.
//EXEMPLO DE FUNÇÕES:
//
//save(): SALVAR UMA NOVA POSTAGEM.

//findById(): PROCURAR UMA POSTAGEM PELO ID.

//findAll(): LISTAR TODAS AS POSTAGEM.

//deleteById(): APAGAR UMA POSTAGEM PELO ID.

public interface PostagemRepository extends JpaRepository<Postagem, Long> {
	
	//PARÂMETROS do JpaRepository<Postagem, Long>:
	//
	//POSTAGEM: É a CLASSE QUE REPRESENTA a TABELA no BANCO DE DADOS. OU SEJA, as OPERAÇÕES VÃO FUNCIONAR NA TABELA tb_postagem.
	//
	//Long: É o TIPO do ID DA TEBELA. AQUI, o id DA POSTAGEM é do TIPO Long.
	
	
	
	
	
	
	
	//Query Methods
	 List <Postagem> findAllByTituloContainingIgnoreCase(@Param("titulo")String titulo);
}











