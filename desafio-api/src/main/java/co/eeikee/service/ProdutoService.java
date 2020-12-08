package co.eeikee.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.eeikee.model.FornecedorDTO;
import co.eeikee.model.Produto;
import co.eeikee.model.ProdutoFornecedorDTO;
import co.eeikee.repository.FornecedorRepository;
import co.eeikee.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository pr;
	
	@Autowired
	private FornecedorRepository fr;
	
	public Produto atualizarProduto(Long id, Produto produto) {
		BeanUtils.copyProperties(produto, pr.getOne(id), "id");
		return pr.save(pr.getOne(id));
	}
	
	public List<ProdutoFornecedorDTO> getProdutoFornecedor(List<Produto> produtos){
		return ((List<Produto>) produtos).stream().map(this::convertTProdutoFornecedorDTO).collect(Collectors.toList());
	}
	
	public ProdutoFornecedorDTO convertTProdutoFornecedorDTO(Produto produto){
		ProdutoFornecedorDTO pfdto = new ProdutoFornecedorDTO();
		pfdto.setId(produto.getId());
		pfdto.setNome(produto.getNome());
		pfdto.setCodigoProduto(produto.getCodigoProduto());
		pfdto.setValor(produto.getValor());
		pfdto.setPromocao(pfdto.isPromocao());
		pfdto.setValorPromo(produto.getValorPromo());
		pfdto.setCategoria(produto.getCategoria());
		pfdto.setImagem(produto.getImagem());
		pfdto.setQuantidade(produto.getQuantidade());
		pfdto.setFornecedor(new FornecedorDTO(fr.getOne(produto.getFornecedor().getId()).getNome(),fr.getOne(produto.getFornecedor().getId()).getCnpj()));
		return pfdto;
	}

}
