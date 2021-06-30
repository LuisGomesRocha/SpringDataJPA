package br.com.zup.SpringDataJPA.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.SpringDataJPA.domain.Cadastro;

@Repository
public interface CadastroRepository extends JpaRepository<Cadastro, Long> {

	Optional<Cadastro> findByEmail(String email);
	Page<Cadastro> findAll(Pageable pageable);




}