package co.eeikee.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import co.eeikee.exceptionhandler.ProdutoExceptionHnadler.Erro;
import co.eeikee.model.Cliente;
import co.eeikee.model.ClienteDTO;
import co.eeikee.model.FornecedorDTO;
import co.eeikee.model.Produto;
import co.eeikee.model.ProdutoDTO;
import co.eeikee.model.Venda;
import co.eeikee.model.VendaDTO;
import co.eeikee.repository.ClienteRepository;
import co.eeikee.repository.FornecedorRepository;
import co.eeikee.repository.ProdutoRepository;
import co.eeikee.repository.VendaRepository;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vr;
	
	@Autowired
	private ProdutoRepository pr;
	
	@Autowired
	private FornecedorRepository fr;
	
	@Autowired
	private ClienteRepository cr;
	
	public Venda atualizarProduto(Long id, Venda venda) {
		BeanUtils.copyProperties(venda, vr.getOne(id), "id");
		return vr.save(vr.getOne(id));
	}
	
	public BigDecimal precoTotal(Venda venda) {
		BigDecimal valorTotal = new BigDecimal("10");
		for (Produto produtoId : venda.getProdutos()) {
			valorTotal = valorTotal.add(pr.getOne(produtoId.getId()).isPromocao() ? pr.getOne(produtoId.getId()).getValorPromo() : pr.getOne(produtoId.getId()).getValor());
		}
		return valorTotal;
	}
	
	public List<VendaDTO> getVendas(List<Venda> vendas){
		return ((List<Venda>) vendas).stream().map(this::convertTVendaDTO).collect(Collectors.toList());
	}
	
	public VendaDTO convertTVendaDTO(Venda venda){
		VendaDTO vdto = new VendaDTO();
		List<ProdutoDTO> produtoDTOs = new ArrayList<ProdutoDTO>();
		vdto.setId(venda.getId());
		vdto.setDataCompra(venda.getDataCompra());
		vdto.setCliente(new ClienteDTO(cr.getOne(venda.getCliente().getId()).getNome(),cr.getOne(venda.getCliente().getId()).getEmail(),cr.getOne(venda.getCliente().getId()).getSenha(),cr.getOne(venda.getCliente().getId()).getDocumento(),cr.getOne(venda.getCliente().getId()).getDataCadastro()));
		for (Produto produto : venda.getProdutos()) {
			Produto backup = pr.getOne(produto.getId());
			ProdutoDTO pdto = new ProdutoDTO(backup.getNome(), backup.getCodigoProduto(), backup.getValor(),backup.isPromocao(), backup.getValorPromo(), backup.getCategoria(), backup.getImagem());
			produtoDTOs.add(pdto);
		}
		vdto.setProdutos(produtoDTOs);
		vdto.setFornecedor(new FornecedorDTO(fr.getOne(venda.getFornecedor().getId()).getNome(),fr.getOne(venda.getFornecedor().getId()).getCnpj()));
		vdto.setTotalCompra(precoTotal(venda));
		return vdto;
	}
	
	public void quantidadeProduto(Venda venda) {
		for (Produto produto : venda.getProdutos()) {
			Produto backup = pr.getOne(produto.getId());
			if (backup.getQuantidade() <= 0) {
				throw new DataIntegrityViolationException("Venda não efetuada: produto indisponível (" + backup.getNome() + ")");
			}
			backup.setQuantidade(backup.getQuantidade()-1);
			pr.save(backup);
		}
	}

	public Date dataCompra(String dia, String mes, String ano) {
		String data = dia+"/"+mes+"/"+ano;
		Date dataCompra = null;
		try {
			dataCompra = new SimpleDateFormat("dd/MM/yyyy").parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dataCompra;  
	}
}
