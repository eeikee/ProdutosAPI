package co.eeikee.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Representação parcial de um fornecedor (DTO)")
public class FornecedorDTO {

	@ApiModelProperty(value = "Nome do fornecedor", example = "Amazon")
	private String nome;

	@ApiModelProperty(value = "CNPJ do fornecedor", example = "15.436.940/0001-03")
	private String cnpj;


	public String getNome() {
		return nome;
	}

	public FornecedorDTO(String nome, String cnpj) {
		this.nome = nome;
		this.cnpj = cnpj;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
}
