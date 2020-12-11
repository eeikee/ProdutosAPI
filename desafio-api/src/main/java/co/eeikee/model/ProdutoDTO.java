package co.eeikee.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Representação parcial de um produto (DTO)")
public class ProdutoDTO {

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

	public ProdutoDTO(String nome, String codigoProduto, BigDecimal valor, boolean promocao,
			BigDecimal valorPromo, String categoria, String imagem) {
		this.nome = nome;
		this.codigoProduto = codigoProduto;
		this.valor = valor;
		this.promocao = promocao;
		this.valorPromo = valorPromo;
		this.categoria = categoria;
		this.imagem = imagem;
	}
	
}
