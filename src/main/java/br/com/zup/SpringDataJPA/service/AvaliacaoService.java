package br.com.zup.SpringDataJPA.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import br.com.zup.SpringDataJPA.domain.Avaliacao;
import br.com.zup.SpringDataJPA.repository.AvaliacaoRepository;

@Service
@Profile("prod")
public class AvaliacaoService {

	@Autowired
	AvaliacaoRepository avaliacaoRepository;
	
		
	// Cadastrar

	public Avaliacao cadastrarAvaliacao(Avaliacao avaliacao) throws Exception {

		return avaliacaoRepository.save(avaliacao);

	}

	// Buscar

		public Avaliacao buscarTitulo(String titulo) {

		return avaliacaoRepository.findByTitulo(titulo).get();

	}
	
	public Avaliacao buscarTudo() {

		return (Avaliacao) avaliacaoRepository.findAll();

	}

	public void remover(Long id) {
		avaliacaoRepository.deleteById(id);
	
	}

}
