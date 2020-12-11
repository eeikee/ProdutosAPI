package co.eeikee.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;


public class ProdutoFornecedorDTO {
	
	@ApiModelProperty(value = "ID do produto", example = "1")
	private Long id;

	@ApiModelProperty(value = "Nome do produto", example = "Headset")
	private String nome;
	
	@ApiModelProperty(value = "Codigo do produto", example = "55648-945")
	private String codigoProduto;
	
	@ApiModelProperty(value = "Valor do produto", example = "100.00")
	private BigDecimal valor;
	
	@ApiModelProperty(value = "Status de promoção do produto", example = "true")
	private boolean promocao;
	
	@ApiModelProperty(value = "Valor do produto em promoção", example = "80.00")
	private BigDecimal valorPromo;
	
	@ApiModelProperty(value = "Categoria do produto", example = "Periféricos")
	private String categoria;
	
	@ApiModelProperty(value = "Codigo do produto", example = "headset_amazon_2020.jpg")
	private String imagem;
	
	@ApiModelProperty(value = "Quantidade do produto", example = "1")
	private Long quantidade;
	
	@ApiModelProperty(value = "Fornecedor do produto", example = "1")
	private FornecedorDTO fornecedor;

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

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public boolean isPromocao() {
		return promocao;
	}

	public void setPromocao(boolean promocao) {
		this.promocao = promocao;
	}

	public BigDecimal getValorPromo() {
		return valorPromo;
	}

	public void setValorPromo(BigDecimal valorPromo) {
		this.valorPromo = valorPromo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public FornecedorDTO getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(FornecedorDTO fornecedor) {
		this.fornecedor = fornecedor;
	}
}
