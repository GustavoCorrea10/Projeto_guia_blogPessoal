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

import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;

@RestController // DEFINE AO SPRING QUE ESSA CLASSE É UMA CONTROLLER E VAI RESPONDERAS REQUISIÇÕES FEITAS A API
@RequestMapping("/temas") // DIZ QUE TODOS OS METODO DENTRO DA TemaController SERÃO ACESSADOS PELA URL /TEMAS
@CrossOrigin(origins = "*", allowedHeaders = "*") // PERMITE QUE a API SEJA ACESSADA DE QUALQUER LUGAR (QUALQUER DOMINIO OU APLICATIVO web)
public class TemaController {
    
	
    @Autowired  // O SPRING DÁ AUTONOMIA PARA A INTERFACE TemaRepository PODER INVOCAR OS METODOS
    private TemaRepository temaRepository;
    
    
    
    @GetMapping // INDICA QUE O METODO VAI RESPONDER UMA REQUISIÇÃO HTTP GET
    public ResponseEntity<List<Tema>> getAll(){ //List<Tema>: A RESPOSTA VAI SER UMA LISTA DE TEMAS( QUE SÃO OBJETOS DA CLASSE Tema)
        return ResponseEntity.ok(temaRepository.findAll()); 
      //temaRepository.findAll() É UM METODO QUE BUSCA TODOS OS REGISTROS DE TEMAS NO BANCO DE DADOS
    }
    
    
   //==================================================================================================================================================================================================== 
    
    
    
    
    @GetMapping("/{id}")// O METODO VAI RESPONDER UMA REQUISIÇÃO DO TIPO GET E ESPERA UM ID NA URL
    public ResponseEntity<Tema> getById(@PathVariable Long id){ // ESSE METODO VAI BUSCAR UM TEMA NO BANCO DE DADOS PLEO ID
                                                                //@PathVariable INDICA QUE O VALOR DO ID NA URL VAI SER ARMAZENADO NA VARIAVEL ID DO METODO
        
    	return temaRepository.findById(id)//CHAMA O REPOSITORIO E BUSCA O TEMA PELO ID
        		
            .map(resposta -> ResponseEntity.ok(resposta)) // SE FOR ENCONTRADO, RETORNA O STATUS 200 (OK)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());// SE NÃO FOR ENCONTRADO, RETORNA STATUS 404(NOT FOUND)
    }
    
    
    
    //=================================================================================================================================================================================
    
    
    @GetMapping("/descricao/{descricao}") // VAI EM DESCRIÇÃO E PROCURA A PALAVRA QUE VOCE ESCREVEU E VERIFICA SE ESSA PALAVRA TEM NA DESCRIÇÃO
                                          //EXEMPLO DE URL: /TEMAS/DESCRICAO/TECNOLOGIA, ONDE TECNOLOGIA É A PALAVRA QUE VAI SER BUSCADA DENTRO DE DESCRIÇÃO
   
    
    public ResponseEntity<List<Tema>> getByTitle(@PathVariable String descricao){ //@PathVariable O VALOR PASSADO NA URL VAI SER ARMAZENADO NA VARIAVEL descricao
    	                                                                          //E VAI TRAZER UMA LISTA COM OS OBJETOS DE TEMA QUE TENHAM A PALAVRA DOGOTADA NA DESCRIÇÃO
        return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
        // findAllByDescricaoContainingIgnoreCase = ESSE METODO BUSCA TODOS OS TEMAS ONDE A DESCRIÇÃO CONTEM A PALAVRA QUE VOCE DIGITOU.
    }
    
    
    
    
    //=================================================================================================================================================================================
    
    
    
    
    
    @PostMapping  //INDICA QUE O METODO VAI RESPONDER UMA REQUISIÇÃO HTTP POST
    public ResponseEntity<Tema> post(@Valid @RequestBody Tema tema){
    	
    	//ResponseEntity<Tema>  ESPECIFICA O TIPO DE DADO QUE SERA ENVIADO NA RESPOSTA, NO CASO, O OBJETO TEMA
    	//Tema: É O TIPO DA VARIAVEL( A CLASSE QUE DEFINE O FORMATO DOS DADOS)
    	//tema: É O NOME DA VARIAVEL QUE IRA GUARDAR  OS DADOS ENVIADOS
    	
        return ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(tema));
    //.body ENVIA DE VOLTA NA TELA O OBJETO CRIADO
    }
    
    
    
    //===================================================================================================================================================================================
    
    
    @PutMapping
    public ResponseEntity<Tema> put(@Valid @RequestBody Tema tema){
    	//ResponseEntity<Tema>
    	
        return temaRepository.findById(tema.getId())
        		//findById É UM METODOS QUE BUSCA UM REGISTRO PELO ID
        		//TEMA.GETID() PEGA O ID QUE FOI ENVIADO, E VE QUAL O TEMA TEM ESSE ID
        		
            .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(tema)))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    
    
    //====================================================================================================================================================================================
    
    
    
    @ResponseStatus(HttpStatus.NO_CONTENT) // RETORNA 204 SE A OPERAÇÃO FOR BEM SUCEDIDA
    @DeleteMapping("/{id}") // DELETA PELO ID NA REQUISIÇÃO HTTP DELETE
    
    public void delete(@PathVariable Long id) { //DELCARA O METODO DELETE QUE RETORNA VOID E RECEBE UMM PARAMETRO ID, QUE VAI SER PEGO PELO @PathVariable
        Optional<Tema> tema = temaRepository.findById(id);// PROCURAR O TEMA PELO ID
        
        if(tema.isEmpty())  //VERIFICA SE O OPTIONAL ESTÁ VAZIO (OU SEJA, NÃO ENCONTROU A POSTAGEM COM O ID).
            throw new ResponseStatusException(HttpStatus.NOT_FOUND); //SE ESTIVER, LANÇA UMA EXCEÇÃO QUE RETORNA O STATUS HTTP 404 (NOT FOUND) PARA O CLIENTE.
        //throw new É USADO PARA LANÇAR UMA EXCEÇÃO
        temaRepository.deleteById(id);  
	}  //CASO A POSTAGEM SEJA ENCONTRADA, ELA É EXCLUÍDA DO BANCO DE DADOS PELO MÉTODO DELETEBYID DO REPOSITÓRIO.

}

