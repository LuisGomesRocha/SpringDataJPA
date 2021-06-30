package br.com.zup.SpringDataJPA.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.SpringDataJPA.domain.Cadastro;
import br.com.zup.SpringDataJPA.dto.CadastroRequest;
import br.com.zup.SpringDataJPA.dto.CadastroResponse;
import br.com.zup.SpringDataJPA.service.DevCadastroService;
import br.com.zup.SpringDataJPA.service.EmailService;

@RestController
@RequestMapping(value = "/api/cadastro")
@Profile("dev")
public class DevCadastroController {

	@Autowired
	DevCadastroService cadastroService;

	@Autowired
	EmailService emailService;

	// Criar Cadastro
	@PostMapping
	public ResponseEntity<CadastroResponse> novoCadastro(@Validated @RequestBody CadastroRequest cadastroRequest)
			throws Exception {

		Cadastro cadastro = cadastroRequest.toModel();
		cadastroService.cadastrar(cadastro);
		String Text = "Cadastro efetuado com sucesso! Seu login: " + (String) cadastro.getEmail() + ". E sua senha é: "
				+ (String) cadastro.getSenha();

		System.out.print(Text);

		CadastroResponse cadastroResponse = cadastro.toResponse();
		return ResponseEntity.status(HttpStatus.OK).body(cadastroResponse);

	}

	// Buscar Cadastro
	@GetMapping("/{id}")
	public ResponseEntity<CadastroResponse> buscar(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(cadastroService.buscar(id).toResponse());

	}

	// Listar Cadastro
	@GetMapping("/listar")
	@Cacheable(value = "buscarTudo")
	public ResponseEntity<Page<Cadastro>> buscarTudo(@RequestParam int pagina, @RequestParam int qtd) {
		Pageable cadastro = (Pageable) PageRequest.of(pagina, qtd);
		return ResponseEntity.status(HttpStatus.OK).body(cadastroService.buscarTudo(cadastro));

	}

	// Deletar Cadastro
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<CadastroResponse> remover(@PathVariable Long id) {

		cadastroService.remover(id);
		return ResponseEntity.ok().build();

	}

}
