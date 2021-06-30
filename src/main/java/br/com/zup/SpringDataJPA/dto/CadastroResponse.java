package br.com.zup.SpringDataJPA.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CadastroResponse {
	
	@Email(message = "email inválido")
	@Size(min = 3, max = 30)
	private String email;
	@NotBlank(message = "nome em branco!")
	private String nome;
	
	
	public CadastroResponse(@Email(message = "email inválido") @Size(min = 3, max = 30) String email,
			@NotBlank(message = "nome em branco!") String nome) {
		super();
		this.email = email;
		this.nome = nome;
	}



	public String getEmail() {
		return email;
	}

	public void setId(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	

}
