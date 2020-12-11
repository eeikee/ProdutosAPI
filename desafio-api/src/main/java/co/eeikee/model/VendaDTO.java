package co.eeikee.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Representação de uma venda (DTO)")
public class VendaDTO {

	@ApiModelProperty(value = "ID da venda", example = "1")
	private Long id;
	
	@ApiModelProperty(value = "Fornecedor da venda", example = "1")
	private FornecedorDTO fornecedor;

	@ApiModelProperty(value = "Cliente da venda", example = "1")
	private ClienteDTO cliente;
	
	@ApiModelProperty(value = "Produtos da venda", example = "1")
	private List<ProdutoDTO> produtos;
	
	@ApiModelProperty(value = "Valor total da venda", example = "180.00")
	private BigDecimal totalCompra; 
	
	@ApiModelProperty(value = "Data da venda", example = "1")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dataCompra;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FornecedorDTO getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(FornecedorDTO fornecedor) {
		this.fornecedor = fornecedor;
	}

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	public List<ProdutoDTO> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoDTO> produtos) {
		this.produtos = produtos;
	}

	public BigDecimal getTotalCompra() {
		return totalCompra;
	}

	public void setTotalCompra(BigDecimal totalCompra) {
		this.totalCompra = totalCompra;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}

}
