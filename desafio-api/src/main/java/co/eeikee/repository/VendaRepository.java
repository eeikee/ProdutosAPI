package co.eeikee.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.eeikee.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long>{

	public List<Venda> findAllByOrderByDataCompraAsc();

	public List<Venda> findAllByOrderByDataCompraDesc();

	public List<Venda> findByDataCompra(Date dataCompra);
	
	
}
