package co.eeikee.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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

	public Date dataCompraCompleta(String dia, String mes, String ano) {
		String data = dia+"/"+mes+"/"+ano;
		Date dataCompra = null;
		try {
			dataCompra = new SimpleDateFormat("dd/MM/yyyy").parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dataCompra;  
	}

	public List<VendaDTO> dataCompraMesDia(String dia, String mes) {
		List<VendaDTO> buscaDiaMes = new ArrayList<>();
		for (Venda venda : vr.findAll()) {
			String[] diaMes = venda.getDataCompra().toString().split("-");
			if (diaMes[2].equals(dia) || diaMes[2].equals("0"+dia) && diaMes[1].equals(mes) || diaMes[1].equals("0"+mes)) {
				buscaDiaMes.add(convertTVendaDTO(venda));
			}
		}
		return buscaDiaMes;
	}

	public List<VendaDTO> dataCompraDia(String dia) {
		List<VendaDTO> buscaDia = new ArrayList<>();
		for (Venda venda : vr.findAll()) {
			String[] diaMes = venda.getDataCompra().toString().split("-");
			if (diaMes[2].equals(dia) || diaMes[2].equals("0"+dia)) {
				buscaDia.add(convertTVendaDTO(venda));
			}
		}
		return buscaDia;
	}
}
