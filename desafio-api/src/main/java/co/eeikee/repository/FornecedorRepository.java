package co.eeikee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.eeikee.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>{

	public List<Fornecedor>findAllByOrderByNomeAsc();

	public List<Fornecedor> findAllByOrderByNomeDesc();

	public List<Fornecedor> findByNomeContaining(String nome);

}
