package com.generation.blogpessoal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.security.JwtService;



@Service //SERVE PARA MOSTRAR QUE ESSA CLASSE VAI REALIZAR A LOGICA DE NEGOCIOS PARA USUARIOS
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository; // RESPONSAVEL POR ACESSAR E MANIPULAR OS DADOS DE USUARIOS NO BANCO DE DADOS

	@Autowired
    private JwtService jwtService; // REPONSAVEL POR GERAR TOKEN JWT

    @Autowired
    private AuthenticationManager authenticationManager; // USADO PARA AUTENTICAR USUARIOS

    
//======================================================================================================================================
    
    
	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();

		usuario.setSenha(criptografarSenha(usuario.getSenha()));

		return Optional.of(usuarioRepository.save(usuario));
	
	}	
	//1 LINHA:
	
	//Optional<Usuario>   (O METODO VAI RETORNAR UM USUARIO OU NADA)
	//cadastrarUsuario    (ESSE É O NOME DO METODO. O NOME DIZ QUE ELE VAI CADASTRAR UM USUARIO)
	//Usuario             ( É O TIPO DE INFORMAÇÃO QUE ESTAMOS ESPERANDO. NO CASO, É UM OBJETO DE UM USUARIO: COM DADOS COMO NOME, SENHA, ETC)
	//usuario             (É O NOME QUE DAMOS A ESSA INFORMAÇÃO. ESSE NOME SERÁ USADO DENTRO DO METODO PARA ACESSAR OS DADOS DO USUARIO)
	
	//2 LINHA:
	
	//usuarioRepository            (METODO QUE INTERAGI COM O BANCO DE DADOS, PERMITE BUSCAR, SALVAR OU ATUALIZAR USUARIOS
	//findByUsuario                ( ESTAMOS PROCURANDO UM USUARIO PELO NOME FORNECIDO " QUE NO CASO USUARIO É O EMAIL ")
	//usuario.getUsuario           ( BUSCA O VALOR DO CAMPO " USUARIO " QUE NO CASO, USUARIO É O EMAIL)
	//isPresent()                  ( VERIFICA SE O OPTIONAL CONTEM UM VALOR OU NÃO)
	//return Optional.empty()      (SE CAIR NO IF, SEGNIFICA QUE JA EXISTE UM USUARIO COM O EMAIL CADASTRADO, ENTAO ELE RETORNA UM VALOR VAZIO)
	
	//3 LINHA: 
	
	//setSenha               ( USADO PARA DEFINIR OU ALTERAR A SENHA DO OBJETO usuario)
	//criptografarSenha      ( TRANFORMA UMA SENHA COMUN EM UMA SENHA CRIPTOGRAFA)
	//usuario.getSenha()     (RETORNA A SENHA ATUAL)
	//RESUMO:                (ELE PEGA A SENHA ATUAL, CRIPTOGRAFA E RETRONA ELA CRIPTOGRAFADA USANDO O setSenha))
	
	//ULTIMA LINHA:
	
	//Optional.of                            (É UMA FORMA SEGURA DE GARANTIR QUE O VALOR NÃO É NULO, EVITANDO O ERRO NULL)
	//usuarioRepository.save(usuario))       ( SAVE(USUARIO) INSERE OU ATUALIZA O OBJETO USUARIO NO BANCO DE DADOS)
	
	
	
	
//=====================================================================================================================================
	
	
	
	
	// RETORNA OU NÃO UM OBJETO DE USUARIO
	public Optional<Usuario> atualizarUsuario(Usuario usuario) {
		
		//PROCURA UM USUARIO COM O ID FORNECIDO COM O GETID E DEPOIS USA O .ISPRESENT PARA VERIFICAR SE A BUSCA EXISTE
		if(usuarioRepository.findById(usuario.getId()).isPresent()) {

			
			//ESE CODIGO BUSCA NO BANCO DE DADOS O USUARIO COM O NOME INFORMADO E ARMAZENA MO OPTIONAL<USUARIO>
			//O buscaUsuario É UMA VARIAVEL QUE PODE CONTER O OBJETO DO USUARIO ENCONTRADO
			Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());

			
			//VERIFICA SE JA EXISTE UM USUARIO IGUAL NO BANCO DE DADOS
			//buscaUsuario                        (É O OPTIONAL, ELE CONTEM O RESULTADO DA BUSCA POR UM USUARIO
			//isPresent                           (VERIFICA SE O OPTIONAL ESTÁ VAZIO OU TEM ALGUMA COISA: SE FOR VERDADEIRO, ISSO SIGNIFICA QUE JA EXISTE UM USUARIO COM O NOME DO USUARIO FORNECIDO
			// buscaUsuario.get().getId()         (É O ID DO USUARIO ENCONTRADO NO BANCO DE DADOS)
			//usuario.getId())                    (É O ID DO USUARIO QUE EU PASSEI COMO PARAMETRO NO CODIGO)
			
			//RESUMO:                              (O ID DO USUARIO QUE EU ENCONTREI NO ANCO DE DADOS É DIFERENTE DO ID DO USUARIO QUE EU PASSEI)
			//SE FOR DIFERENTE, O CODIGO DENTRO DO IF VAI RODAR
		
			if ( (buscaUsuario.isPresent()) && ( buscaUsuario.get().getId() != usuario.getId()))
				
				//CASO O USUARIO JÁ EXISTA, ELE VAI LANÇAR UMA EXCEÇÃO 400
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

			//CRIPTOGRAFA A SENHA ANTES DE SALVAR (EXPLICAÇÃO NO METODO DE CIMA)
			usuario.setSenha(criptografarSenha(usuario.getSenha()));

			//SALVA O USUARIO
			//O ofNullable CRIA UM OPTIONAL PODENDO TER UM VALOR NULO OU NÃO E ARMAZENA DENTRO DO OPTIONAL
			return Optional.ofNullable(usuarioRepository.save(usuario));
			
		}

		//RETORNA UM OPTIONAL VAZIO
		return Optional.empty();
	
	}	
	
	
	
	//RESUMO E LOGICA DESSE CODIGO: 
	
	// O CÓDIGO VERIFICA SE O USUÁRIO EXISTE NO BANCO DE DADOS E SE O NOME DE USUÁRIO FORNECIDO JÁ ESTÁ SENDO USADO POR OUTRO USUÁRIO.
	// SE O NOME JÁ ESTIVER EM USO, ELE LANÇA UM ERRO.
	// CASO CONTRÁRIO, ELE CRIPTOGRAFA A SENHA, SALVA O USUÁRIO NO BANCO DE DADOS E RETORNA O USUÁRIO SALVO.
	// SE O USUÁRIO NÃO FOR ENCONTRADO, RETORNA UM OPTIONAL VAZIO.

	
	
	
	
	
	
	
	
	
	
	
//=====================================================================================================================================================
	
	
	// MÉTODO QUE AUTENTICA UM USUÁRIO E RETORNA UM OPTIONAL CONTENDO OS DADOS OU VAZIO EM CASO DE FALHA.

	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {
        
		var credenciais = new UsernamePasswordAuthenticationToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha());
		//VAR                                           (VAR É UMA PALAVRA CAHVE, ELA VAI ADIVINHAR O TIPO DA VARIAVEL COM BASE NO VALOR QUE ESTÁ SENDO ATRIBUIDA A ELA)
		//CREDENCIAIS                                   (NOME DA VARIAVEL: ELA IRA ARMAZENAR O OBJETO CRIADO PELA LINHA, QUE CONTÉM O NOME DO USUARIO E A SENHA PARA AUTENTICAÇÃO)
		//NEW                                           (CRIA UMA NOVA INSTANCIA DESSA CLASSE)
		//UsernamePasswordAuthenticationToken           (É UMA CLASSE DO SPRING SECURITY: ELA ARMAZENA O NOME DO USUARIO E A SENHA DO USUARIO QUE ESTA TENTANDO SE AUTENTICAR NO SISTEMA)
		//usuarioLogin.get().getUsuario()               (ESSE METODO ACESSA O NOME DE USUARIO DO OBJETO)
		//usuarioLogin.get().getSenha()                 (ESSE METODO ACESSA A SENHA DO OBJETO USUARIOLOGIN)

		//RESUMO: ELA ESTÁ CRIANDO UM OBJETO DE AUTENTICAÇÃO COM O NOME DE USUARIO E A SENHA DE UM OBJETO usuarioLogin
		
		
		
		Authentication authentication = authenticationManager.authenticate(credenciais);
        //Authentication        (É UMA INTERFACE QUE REPRESENTA OS DADOS DE AUTENTICAÇÃO DE UM USUARIO, COMO NOME DE USUARIO, SENHA E OUTRAS INFORMAÇÕES RELACIONADAS A SEGURANÇA)
		//Authentication        (É A VARIAVEL ONDE O RESULTADO VAI SER ARMAZENADO, ELA CONTERÁ UM OBJETO QUE REPRESENTA O USUARIO AUTENTICADO)
		//=                     (SINAL DE ATRIBUIÇÃO)
		//authenticationManager (ELE É RESPONSAVEL POR VERIFICAR NOME DE USUARIO E SENHA) E GARANTIR QUE O UUSARIO SEJA AUTENTICADO CORRETAMENTE)
		//authenticate()        (É UM METÓDO DO authenticationManager QUE REALIZA A AUTENTICAÇÃO. ELE RECEBE O PARAMETRO CREDENCIAIS, QUE NORMALMENTE CONTEM INFORMAÇÕES COM NOME DE USUARIO E A SENHA)
		
		//RESUMO: 
		
		// REALIZA A AUTENTICAÇÃO DO USUÁRIO, VERIFICA AS CREDENCIAIS E RETORNA UM OBJETO Authentication SE FOR VÁLIDO.

		
		
		
		
		
		
        // Se a autenticação foi efetuada com sucesso
		if (authentication.isAuthenticated()) {

            // Busca os dados do usuário
			Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

            // Se o usuário foi encontrado
			if (usuario.isPresent()) {

                // Preenche o Objeto usuarioLogin com os dados encontrados 
			    usuarioLogin.get().setId(usuario.get().getId());
                usuarioLogin.get().setNome(usuario.get().getNome());
                usuarioLogin.get().setFoto(usuario.get().getFoto());
                usuarioLogin.get().setToken(gerarToken(usuarioLogin.get().getUsuario()));
                usuarioLogin.get().setSenha("");
				
                 // Retorna o Objeto preenchido
			   return usuarioLogin;
			
			}

        } 
            
		return Optional.empty();

    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//==========================================================================================================================
	
	

	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(senha);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//=============================================================================================================================
	private String gerarToken(String usuario) {
		return "Bearer " + jwtService.generateToken(usuario);
	}

}