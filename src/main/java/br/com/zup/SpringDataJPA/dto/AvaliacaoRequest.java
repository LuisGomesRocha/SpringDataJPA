package br.com.zup.SpringDataJPA.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import br.com.zup.SpringDataJPA.domain.Avaliacao;
import br.com.zup.SpringDataJPA.domain.TipoPerfil;

public class AvaliacaoRequest {

	@NotBlank(message = "Título da avaliação em branco!")
	@Size(min = 3, max = 250, message = "Título deve conter de 3 a 250 caracteres!")
	@Column(unique = true)
	private String tituloAvaliacao;

	@NotBlank(message = "Corpo da avaliação em branco!")
	@Size(min = 3, max = 500, message = "Corpo deve conter de 3 a 500 caracteres!")
	@Column(unique = true)
	private String corpoAvaliacao;

	@NotBlank
	private TipoPerfil tipoPerfil;

	public Avaliacao toModel() {
		return new Avaliacao(this.tituloAvaliacao, this.corpoAvaliacao, this.tipoPerfil);
	}

	public String getTituloAvaliacao() {
		return tituloAvaliacao;
	}

	public void setTituloAvaliacao(String tituloAvaliacao) {
		this.tituloAvaliacao = tituloAvaliacao;
	}

	public String getCorpoAvaliacao() {
		return corpoAvaliacao;
	}

	public void setCorpoAvaliacao(String corpoAvaliacao) {
		this.corpoAvaliacao = corpoAvaliacao;
	}

	public TipoPerfil getTipoPerfil() {
		return tipoPerfil;
	}

	public void setTipoPerfil(TipoPerfil tipoPerfil) {
		this.tipoPerfil = tipoPerfil;
	}

}
