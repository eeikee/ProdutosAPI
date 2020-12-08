package co.eeikee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.eeikee.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	List<Cliente> findAllByOrderByNomeAsc();

	List<Cliente> findAllByOrderByNomeDesc();
	
	List<Cliente> findByNomeContaining(String nome);
	
}
