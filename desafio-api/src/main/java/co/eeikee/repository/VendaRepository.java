package co.eeikee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.eeikee.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long>{

	public List<Venda> findAllByOrderByClienteAsc();

	public List<Venda> findAllByOrderByClienteDesc();

	public List<Venda> findByClienteContaining(String nome);
	
}
