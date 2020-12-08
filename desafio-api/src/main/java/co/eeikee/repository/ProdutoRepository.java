package co.eeikee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.eeikee.model.Fornecedor;
import co.eeikee.model.Produto;
import co.eeikee.model.ProdutoDTO;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	public List<Produto> findByFornecedor(Fornecedor fornecedor);

	public List<Produto> findAllByOrderByNomeAsc();

	public List<Produto> findAllByOrderByNomeDesc();

	public List<Produto> findByNomeContaining(String nome);
	
}
