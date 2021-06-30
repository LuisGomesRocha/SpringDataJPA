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
import br.com.zup.SpringDataJPA.domain.Avaliacao;
import br.com.zup.SpringDataJPA.dto.AvaliacaoRequest;
import br.com.zup.SpringDataJPA.dto.AvaliacaoResponse;
import br.com.zup.SpringDataJPA.service.AvaliacaoService;


@RestController
@RequestMapping(value = "/api/avaliacao")
@Profile("prod")
public class AvaliacaoController {

	@Autowired
	AvaliacaoService avaliacaoService;

	// Criar Cadastro
	@PostMapping
	public ResponseEntity<AvaliacaoResponse> novaAvaliacao(@Validated @RequestBody AvaliacaoRequest avaliacaoRequest)
			throws Exception {

		Avaliacao avaliacao = avaliacaoRequest.toModel();
		avaliacaoService.cadastrarAvaliacao(avaliacao);
		AvaliacaoResponse avaliacaoResponse = avaliacao.toResponse();
		return ResponseEntity.status(HttpStatus.OK).body(avaliacaoResponse);

	}

	// Buscar Cadastro
	@GetMapping("/titulo")
	public ResponseEntity<AvaliacaoResponse> buscar(@PathVariable Long id) {
		String titulo = null;
		return ResponseEntity.status(HttpStatus.OK).body(avaliacaoService.buscarTitulo(titulo).toResponse());

	}

	// Listar Cadastro
	@GetMapping("/listar")
	@Cacheable(value = "buscarTudo")
	public ResponseEntity<Avaliacao> buscarTudo(@RequestParam int pagina, @RequestParam int qtd) {
		Pageable avaliacao = (Pageable) PageRequest.of(pagina, qtd);
		return ResponseEntity.status(HttpStatus.OK).body(avaliacaoService.buscarTudo());

	}

	// Deletar Cadastro
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Avaliacao> remover(@PathVariable Long id) {
		avaliacaoService.remover(id);
		return ResponseEntity.ok().build();

	}

}
