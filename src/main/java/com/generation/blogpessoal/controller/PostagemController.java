package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;





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
	
	@Autowired
	private TemaRepository temaRepository;
	
	
	
	//==========================================================================================================
	
	
	
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

	
	@GetMapping // INDICA QUE ESSE MÉTODO É CHAMADO EM VERBOS/METODOS HTTP DO TIPO GET
	public ResponseEntity<List<Postagem>> getAll(){ // O METODO GETALL QUANDO FOR CHAMADO ELE VAI TRAZER UMA LISTA COM OS OBJETOS DE POSTAGEM
		return ResponseEntity.ok(postagemRepository.findAll()); //SELECT * FROM tb_postagem
	}
	
	
	
	
	//====================================================================================================
	
	//@GETMAPPING: INDICA QUE ESTE MÉTODO SERÁ CHAMADO QUANDO HOUVER UMA REQUISIÇÃO HTTP DO TIPO GET.  
	//("/{ID}"): DEFINE QUE O ENDPOINT VAI ESPERAR UM ID NA URL. POR EXEMPLO:  
	//HTTP://LOCALHOST:8080/POSTAGENS/1  
	//NESSE CASO, O VALOR 1 SERÁ PASSADO PARA O MÉTODO COMO O ID DA POSTAGEM.
	
	
	
	//RESPONSEENTITY<POSTAGEM>: O MÉTODO RETORNA UM OBJETO RESPONSEENTITY, 
	//QUE É USADO PARA MONTAR A RESPOSTA HTTP. ELE INCLUI:
	
	//O CORPO DA RESPOSTA (UM OBJETO POSTAGEM, SE ENCONTRADO).  
	//O STATUS DA RESPOSTA (COMO 200 OK OU 404 NOT FOUND).
	
	
	//(@PATHVARIABLE LONG ID): LÊ O VALOR DO ID QUE FOI PASSADO NA URL E ASSOCIA ESSE VALOR AO PARÂMETRO ID DO MÉTODO.  
	//POR EXEMPLO, SE O ENDPOINT É ACESSADO EM /POSTAGENS/1,  
	//O VALOR 1 SERÁ ATRIBUÍDO À VARIÁVEL ID.



	
	@GetMapping("/{id}") // POSTAGEM/1 => id = 1
	public ResponseEntity<Postagem> getById(@PathVariable long id){ //id = 1
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta)) //if
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); //else
	}
	
	//postagemRepository.findById(id): 
	
	//AQUI, O CÓDIGO ESTÁ PEDINDO PARA O REPOSITÓRIO DE POSTAGENS (POSTAGEMREPOSITORY) PROCURAR UMA POSTAGEM NO BANCO DE DADOS UTILIZANDO O ID FORNECIDO.  
	//O MÉTODO FINDBYID(ID) TENTA LOCALIZAR UMA POSTAGEM QUE TENHA O ID IGUAL AO VALOR DE ID.


	//.MAP(RESPOSTA -> RESPONSEENTITY.OK(RESPOSTA)):  
	
	//O QUE É O .MAP?  
	//O MÉTODO MAP É UTILIZADO EM OBJETOS DO TIPO OPTIONAL PARA TRANSFORMAR O VALOR CONTIDO DENTRO DO OPTIONAL. ELE SÓ É EXECUTADO SE O OPTIONAL NÃO ESTIVER VAZIO. OU SEJA, SE O VALOR FOI ENCONTRADO NO BANCO DE DADOS.  
	//NO CASO, O MAP ESTÁ SENDO USADO PARA TRANSFORMAR O VALOR ENCONTRADO (QUE SERIA O OBJETO DA POSTAGEM) EM UMA RESPOSTA HTTP.

	
	//O QUE É RESPONSEENTITY.OK()?  

	//RESPONSEENTITY.OK() É UM MÉTODO QUE CRIA UMA RESPOSTA HTTP COM O STATUS 200 OK. O STATUS 200 SIGNIFICA "SUCESSO".  
	//O RESPOSTA QUE É PASSADO COMO PARÂMETRO PARA O RESPONSEENTITY.OK(RESPOSTA) É O OBJETO DA POSTAGEM ENCONTRADO. ESTE OBJETO SERÁ COLOCADO NO CORPO DA RESPOSTA.


	
	//O QUE É RESPOSTA?  
	//RESPOSTA É O NOME DADO AO VALOR DENTRO DO OPTIONAL. NESSE CONTEXTO, É O OBJETO POSTAGEM QUE FOI ENCONTRADO PELO MÉTODO FINDBYID(ID) NO BANCO DE DADOS.  
	//ENTÃO, SE O ID FORNECIDO EXISTIR NO BANCO DE DADOS, O RESPOSTA SERÁ O OBJETO DA POSTAGEM CORRESPONDENTE.


	
	//CASO O ID NÃO SEJA ENCONTRADO :
	
	//.ORELSE(...): É CHAMADO SE O OPTIONAL ESTIVER VAZIO (NENHUMA POSTAGEM FOI ENCONTRADA PARA O ID FORNECIDO).  
	//RESPONSEENTITY.STATUS(HTTPSTATUS.NOT_FOUND).BUILD():  
	//CRIA UMA RESPOSTA HTTP COM O STATUS 404 NOT FOUND.  
	//NÃO INCLUI NENHUM CORPO NA RESPOSTA (APENAS O CÓDIGO DE ERRO).
	
	//.BUILD PODE SER TRADUZIDO COMO CONSTRUIR, ELE CONSTROI O CODIGO 404 CASO NÃO ENCONTRE O ID

	
	
	
	
	
	//========================================================================================================
	
	
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
		
	//@GETMAPPING("/TITULO/{TITULO}"):  
	//O QUE É ISSO?  

	//ESSE É UM MAPEAMENTO DE REQUISIÇÃO HTTP. A ANOTAÇÃO @GETMAPPING INDICA QUE ESSE MÉTODO SERÁ RESPONSÁVEL POR ATENDER REQUISIÇÕES DO TIPO GET (QUE GERALMENTE SÃO USADAS PARA OBTER DADOS).  
	//A URL ASSOCIADA A ESSE MÉTODO SERÁ /TITULO/{TITULO}. O /{TITULO} É UMA VARIÁVEL DE CAMINHO QUE SERÁ PASSADA COMO PARÂMETRO PARA O MÉTODO.  
	//O QUE ISSO SIGNIFICA?  

	//QUANDO ALGUÉM FIZER UMA REQUISIÇÃO GET PARA UMA URL QUE SIGA ESSE PADRÃO, COMO /TITULO/ALGUM-TITULO, O MÉTODO SERÁ CHAMADO.  
	//O VALOR QUE FOR COLOCADO NO LUGAR DE {TITULO} SERÁ PASSADO COMO ARGUMENTO PARA O MÉTODO COMO O PARÂMETRO TITULO.  
	//EXEMPLO DE REQUISIÇÃO:  

	//SE A REQUISIÇÃO FOR PARA A URL HTTP://LOCALHOST:8080/TITULO/JAVA, O VALOR "JAVA" SERÁ ATRIBUÍDO AO PARÂMETRO TITULO NO MÉTODO.

	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	//PUBLIC RESPONSEENTITY<LIST<POSTAGEM>> GETBYTITULO(@PATHVARIABLE STRING TITULO):  
	//O QUE ISSO FAZ?  

	//ESSE É O MÉTODO DO CONTROLADOR QUE SERÁ EXECUTADO QUANDO UMA REQUISIÇÃO GET FOR FEITA PARA A URL /TITULO/{TITULO}.  
	//O MÉTODO TEM O TIPO DE RETORNO RESPONSEENTITY<LIST<POSTAGEM>>. OU SEJA, ELE RETORNA UMA RESPOSTA HTTP COM UMA LISTA DE POSTAGENS (LIST<POSTAGEM>) E UM CÓDIGO DE STATUS.  
	//O PARÂMETRO @PATHVARIABLE STRING TITULO INDICA QUE O VALOR PASSADO NA URL (NO LUGAR DE {TITULO}) SERÁ CAPTURADO E ATRIBUÍDO À VARIÁVEL TITULO.  

	//EXPLICAÇÃO DO @PATHVARIABLE:  

	//@PATHVARIABLE É UMA ANOTAÇÃO QUE INDICA QUE O VALOR DE {TITULO} NA URL DEVE SER ATRIBUÍDO AO PARÂMETRO TITULO DO MÉTODO. OU SEJA, O SPRING VAI PEGAR O VALOR QUE FOI PASSADO NA URL E USAR ELE COMO ENTRADA DO MÉTODO.  
	//EXEMPLO:  

	//SE A URL DA REQUISIÇÃO FOR /TITULO/JAVA, O PARÂMETRO TITULO SERÁ "JAVA".
	
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	//FINDALLBYTITULOCONTAININGIGNORECASE(TITULO):
	
	//ESTE É UM MÉTODO DE CONSULTA (QUERY) DO POSTAGEMREPOSITORY QUE É USADO PARA BUSCAR POSTAGENS NO BANCO DE DADOS
	//DE ACORDO COM O TÍTULO.
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	//findAllByTitulo: 
	
	//ISSO SIGNIFICA "ENCONTRE TODAS AS POSTAGENS BASEADAS NO CAMPO TITULO". O TITULO É UM ATRIBUTO DA ENTIDADE POSTAGEM, QUE PROVAVELMENTE É UMA COLUNA NO BANCO DE DADOS.  
	//OU SEJA, ESSE MÉTODO VAI PROCURAR TODAS AS POSTAGENS QUE TENHAM ALGUM VALOR NO CAMPO TITULO.
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	
	//Containing: 
	
	//O CONTAINING INDICA QUE A BUSCA DEVE SER PARCIAL, OU SEJA, ELE VAI PROCURAR POR POSTAGENS QUE CONTÊM A PALAVRA FORNECIDA NO PARÂMETRO TITULO.  
	//POR EXEMPLO, SE VOCÊ PASSAR "JAVA", ELE VAI RETORNAR TODAS AS POSTAGENS CUJOS TÍTULOS CONTÊM "JAVA", COMO "APRENDENDO JAVA", "JAVA PARA INICIANTES", ETC.
	
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	
	//IgnoreCase:
	
	//ISSO SIGNIFICA QUE A BUSCA NÃO VAI SE IMPORTAR SE O TEXTO ESTÁ EM MAIÚSCULAS OU MINÚSCULAS. OU SEJA, "JAVA", "java", "JAVA" SERIAM CONSIDERADOS IGUAIS DURANTE A BUSCA.

	
	
	
	
	
	
	//=======================================================================================================================================================================================================
	
	
	
	//@POSTMAPPING É UMA ANOTAÇÃO DO SPRING QUE INDICA QUE ESTE MÉTODO IRÁ TRATAR REQUISIÇÕES HTTP DO TIPO POST. O HTTP POST É GERALMENTE USADO PARA CRIAR NOVOS RECURSOS (DADOS) NO SERVIDOR
	
	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem){
		//<Postagem> CORPO DA RESPOSTA, NESSE CASO O CORPO SERA UM OBJETO DO TIPO POSTAGEM
		
		if (temaRepository.existsById(postagem.getTema().getId()))
		//existsById() RETORNA TRUE SE O TEMA COM O ID EXISTIR OU FALSE SE NÃO EXISTIR
		  //POSTAGEM: REPRESENTA O OBJETO POSTAGEM, QUE CONTÉM OS DADOS DA PUBLICAÇÃO
			  //postagem.getTema(): RETORNA O TEMA ASSOCIADO AO OBJETO
			       //.getId(): RETORNA O ID DO OBJETO
			  
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
		//.body ENVIA DE VOLTA NA TELA O OBJETO CRIADO
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe!", null);//
		//throw new É USADO PARA LANÇAR UMA EXCEÇÃO
		// O NULL É USADO PARA INDICAR QUE NÃO HÁ CAUSA ADICIONAL OU DETALHES EXTRAS A SEREM ENVIADOS COM A EXCEÇÃO.

		
	}
	

	//=====================================================================================================================================================================================================
	
	
	@PutMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem){
		if (postagemRepository.existsById(postagem.getId())) {
			
		
			if(temaRepository.existsById(postagem.getTema().getId()))
	             return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe", null);
		
			}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
	}
	
	
	
	
	
	
	
	
	
	//=========================================================================================================
	
	
	@ResponseStatus(HttpStatus.NO_CONTENT) //  RETORNA o status HTTP 204
	@DeleteMapping("{id}") // DEFINE QUE ESSE METODO VAI SER CHAMADO PELA REQUISIÇÃO HTTP DELETE E QUE INCLUI O ID 
	public void delete(@PathVariable long id) {  //DELCARA O METODO DELETE QUE RETORNA VOID E RECEBE UMM PARAMETRO ID, QUE VAI SER PEGO PELO @PathVariable
		Optional<Postagem> postagem = postagemRepository.findById(id); // USA O REPOSITORIO postagemRepository PARA BUSCAR NO BANCO DE DADOS UMA POSTAGEM COM O ID FORNECIDO.
		//O RESULTADO É UM OPTIONAL, QUE PODE ENCONTRAR O OBJETO OU NÃO CASO O ID NÃO EXISTA
		
		if(postagem.isEmpty())  //VERIFICA SE O OPTIONAL ESTÁ VAZIO (OU SEJA, NÃO ENCONTROU A POSTAGEM COM O ID). 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND); //SE ESTIVER, LANÇA UMA EXCEÇÃO QUE RETORNA O STATUS HTTP 404 (NOT FOUND) PARA O CLIENTE.

		
		postagemRepository.deleteById(id);
	}  //CASO A POSTAGEM SEJA ENCONTRADA, ELA É EXCLUÍDA DO BANCO DE DADOS PELO MÉTODO DELETEBYID DO REPOSITÓRIO.

	
	
	









}
