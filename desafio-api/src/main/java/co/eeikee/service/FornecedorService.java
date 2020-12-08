package co.eeikee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.eeikee.model.Fornecedor;
import co.eeikee.model.FornecedorProdutoDTO;
import co.eeikee.model.Produto;
import co.eeikee.model.ProdutoDTO;
import co.eeikee.repository.FornecedorRepository;
import co.eeikee.repository.ProdutoRepository;

@Service
public class FornecedorService {

	@Autowired
	private FornecedorRepository fr;
	
	@Autowired
	private ProdutoRepository pr;
	
	public Fornecedor atualizarCategoria(Long id, Fornecedor fornecedor) {
		BeanUtils.copyProperties(fornecedor, fr.getOne(id), "id");
		return fr.save(fr.getOne(id));
	}
	
	public List<FornecedorProdutoDTO> getFornecedorProduto(List<Fornecedor> fornecedores){
		return ((List<Fornecedor>) fornecedores).stream().map(this::convertToFornecedorProdutoDTO).collect(Collectors.toList());
	}

	public FornecedorProdutoDTO convertToFornecedorProdutoDTO(Fornecedor fornecedor) {
		FornecedorProdutoDTO fpdto = new FornecedorProdutoDTO();
		List<ProdutoDTO> produtoDTOs = new ArrayList<ProdutoDTO>();
		fpdto.setId(fornecedor.getId());
		fpdto.setNome(fornecedor.getNome());
		fpdto.setCnpj(fornecedor.getCnpj());
		for (Produto produto : pr.findByFornecedor(fornecedor)) {
			ProdutoDTO pdto = new ProdutoDTO(produto.getNome(), produto.getCodigoProduto(), produto.getValor(),produto.isPromocao(), produto.getValorPromo(), produto.getCategoria(), produto.getImagem(), produto.getQuantidade());
			produtoDTOs.add(pdto);
		}
		fpdto.setProdutos(produtoDTOs);
		return fpdto;
	}
}
