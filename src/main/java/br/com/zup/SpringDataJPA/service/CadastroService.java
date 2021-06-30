package br.com.zup.SpringDataJPA.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.zup.SpringDataJPA.domain.Cadastro;
import br.com.zup.SpringDataJPA.repository.CadastroRepository;

@Service
@Profile("prod")
public class CadastroService {

	@Autowired
	CadastroRepository cadastroRepository;
	
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	EmailService emailService;
	
		
	// Cadastrar

	public Cadastro cadastrar(Cadastro cadastro) throws Exception {
		
		cadastro.setSenha(encoder.encode(cadastro.getSenha()));
		String Text = "Cadastro efetuado com sucesso! Seu login: " + (String) cadastro.getEmail() + ". E sua senha é: " + (String) cadastro.getSenha();
		String To = "luis.augusto.gomes.rocha@gmail.com";
		String From = "luisgomesspring@gmail.com";		
		emailService.sendMail(Text, To, From);		
		return cadastroRepository.save(cadastro);

	}

	// Buscar
	public Cadastro buscar(Long id) {

		return cadastroRepository.findById(id).get();

	}

	public Page<Cadastro> buscarTudo(Pageable cadastro) {

		Page<Cadastro> paginaCadastro = cadastroRepository.findAll(cadastro);
		return paginaCadastro;

	}

	public void remover(Long id) {
		cadastroRepository.deleteById(id);
	
	}

}
