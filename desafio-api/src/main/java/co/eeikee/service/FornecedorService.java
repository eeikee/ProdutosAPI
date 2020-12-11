package co.eeikee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	
	public Fornecedor atualizarFornecedor(Long id, Fornecedor fornecedor) {
		Fornecedor fornecedorSalvo = fr.getOne(id);
		fornecedorSalvo.setNome(fornecedor.getNome());
		fornecedorSalvo.setCnpj(fornecedor.getCnpj());
		return fr.save(fornecedorSalvo);
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
			ProdutoDTO pdto = new ProdutoDTO(produto.getNome(), produto.getCodigoProduto(), produto.getValor(),produto.isPromocao(), produto.getValorPromo(), produto.getCategoria(), produto.getImagem());
			produtoDTOs.add(pdto);
		}
		fpdto.setProdutos(produtoDTOs);
		return fpdto;
	}
}
