package com.generation.blogpessoal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.blogpessoal.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	public Optional<Usuario> findByUsuario(String usuario);
	//Optional<Usuario> ( RETORNA UM OBJETO DO TIPO OPTIONAL QUE PODE OU NÃO CONTER UM OBJETO DO TIPO USUARIO)
	//findByUsuario     ( INDICA QUE ELE FAZ BUSCAS POR USUARIO NO BANCO DE DADOS)
}   //STRING USUARIO ( É O PARAMETRO DO METODO. ELE RECEBE O VALOR QUE SERA USADO NA BUSCA, OU SEJA, O VALOR DO CAMPO USUARIO)
