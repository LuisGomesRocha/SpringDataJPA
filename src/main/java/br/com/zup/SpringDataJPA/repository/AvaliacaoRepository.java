package br.com.zup.SpringDataJPA.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.SpringDataJPA.domain.Avaliacao;
import br.com.zup.SpringDataJPA.domain.Cadastro;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

	Optional<Avaliacao> findByTitulo(String titulo );

	Avaliacao save(Avaliacao avaliacao);
	




}