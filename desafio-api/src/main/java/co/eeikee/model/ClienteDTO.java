package co.eeikee.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Representação de um cliente (DTO)")
public class ClienteDTO {

	@ApiModelProperty(value = "Nome do cliente", example = "Fulano")
	private String nome;

	@ApiModelProperty(value = "Email do cliente", example = "email@host.com")
	private String email;

	@ApiModelProperty(value = "Senha do cliente", example = "Sn2020c1")
	private String senha;

	@ApiModelProperty(value = "Documento do cliente", example = "123.456.789-10")
	private String documento;

	@ApiModelProperty(value = "Data de cadastro do cliente", example = "01/01/2020")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	public String getNome() {
		return nome;
	}

	public ClienteDTO(String nome, String email, String senha, String documento, Date dataCadastro) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.documento = documento;
		this.dataCadastro = dataCadastro;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

}
