package br.com.zup.SpringDataJPA.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.zup.SpringDataJPA.dto.AvaliacaoResponse;

@Entity
public class Avaliacao {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Avaliacao other = (Avaliacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Deprecated
	public Avaliacao() {

	}

	public Avaliacao(
			@NotBlank(message = "Título da avaliação em branco!") @Size(min = 3, max = 250, message = "Título deve conter de 3 a 250 caracteres!") String tituloAvaliacao,
			@NotBlank(message = "Corpo da avaliação em branco!") @Size(min = 3, max = 500, message = "Corpo deve conter de 3 a 500 caracteres!") String corpoAvaliacao,
			@NotBlank TipoPerfil tipoPerfil) {
		super();
		this.tituloAvaliacao = tituloAvaliacao;
		this.corpoAvaliacao = corpoAvaliacao;
		this.tipoPerfil = tipoPerfil;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public AvaliacaoResponse toResponse() {
		return new AvaliacaoResponse(this.tituloAvaliacao, this.tipoPerfil);
	}

}