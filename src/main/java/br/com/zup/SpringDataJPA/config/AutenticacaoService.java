package br.com.zup.SpringDataJPA.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.zup.SpringDataJPA.domain.Cadastro;
import br.com.zup.SpringDataJPA.repository.CadastroRepository;

@Service
public class AutenticacaoService implements UserDetailsService {
	
	@Autowired
	private CadastroRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Cadastro> cadastro = repository.findByEmail(email);
		if (cadastro.isPresent()) {
			return cadastro.get();
		}
		
		throw new UsernameNotFoundException("Dados inválidos!");
	}

}
