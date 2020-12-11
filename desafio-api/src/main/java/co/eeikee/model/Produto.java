package co.eeikee.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.apache.commons.lang3.RandomStringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "produto")
@ApiModel(description = "Representação de um produto")
public class Produto {
	@Id
	@ApiModelProperty(value = "ID do produto", example = "1")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@ApiModelProperty(value = "Nome do produto", example = "Headset")
	private String nome;
	
	@ApiModelProperty(value = "Codigo do produto", example = "55648-945")
	private String codigoProduto;
	
	@NotNull
	@PositiveOrZero
	@ApiModelProperty(value = "Valor do produto", example = "100.00")
	private BigDecimal valor;
	
	@NotNull
	@ApiModelProperty(value = "Status de promoção do produto", example = "true")
	private boolean promocao;
	
	@PositiveOrZero
	@ApiModelProperty(value = "Valor do produto em promoção", example = "80.00")
	private BigDecimal valorPromo;
	
	@NotBlank
	@ApiModelProperty(value = "Categoria do produto", example = "Periféricos")
	private String categoria;
	
	@NotBlank
	@ApiModelProperty(value = "Codigo do produto", example = "headset_amazon_2020.jpg")
	private String imagem;
	
	@NotNull
	@ApiModelProperty(value = "Quantidade do produto", example = "1")
	private Long quantidade;
	
	@ApiModelProperty(value = "Fornecedor do produto", example = "1")
	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "fornecedor_id")
	private Fornecedor fornecedor;

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

	public void setCodigoProduto() {
		this.codigoProduto = codeGenerator();
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

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public String codeGenerator() {
	  return RandomStringUtils.randomAlphanumeric(10);   
	}
	
	
}
