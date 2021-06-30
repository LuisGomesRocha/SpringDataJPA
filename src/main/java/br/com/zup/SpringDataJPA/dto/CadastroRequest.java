package br.com.zup.SpringDataJPA.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.zup.SpringDataJPA.domain.Cadastro;

public class CadastroRequest {
	
	@NotBlank(message = "nome em branco!")
	@Size(min = 3, max = 30, message = "Nome deve conter de 3 a 30 caracteres!")
	private String nome;

	@Size(min = 3, max = 30, message = "Email deve conter de 3 a 30 caracteres!")
	@NotBlank(message = "email em branco!")
	@Email(message = "email inválido")
	private String email;
	@NotNull(message = "Data em branco!")
	@Min(value = 18 , message = "Idade necessita ser maior ou igual a 18 anos")
	private Integer idade;
	
	private String senha;
	
	public Cadastro toModel() {
		return new Cadastro(this.nome, this.senha, this.email, this.idade);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


		public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


}
