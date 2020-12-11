package co.eeikee.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Representação de um fornecedor (DTO)")
public class FornecedorProdutoDTO {
	
	@ApiModelProperty(value = "ID do fornecedor", example = "1")
	private Long id;

	@ApiModelProperty(value = "Nome do fornecedor", example = "Amazon")
	private String nome;

	@ApiModelProperty(value = "CNPJ do fornecedor", example = "15.436.940/0001-03")
	private String cnpj;

	@ApiModelProperty(value = "Produtos do fornecedor")
	private List<ProdutoDTO> produtos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
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

	public List<ProdutoDTO> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoDTO> produtos) {
		this.produtos = produtos;
	}
	
	
}
