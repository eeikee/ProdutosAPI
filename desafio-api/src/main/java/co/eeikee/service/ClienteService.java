package co.eeikee.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import co.eeikee.model.Cliente;
import co.eeikee.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository cr;

	public Cliente atualizarCliente(Long id, Cliente cliente) {
		try {
			Cliente clienteSalvo = cr.getOne(id);
			BeanUtils.copyProperties(cliente, clienteSalvo, "id");
			return cr.save(clienteSalvo);
		} catch (Exception e) {
			throw new EmptyResultDataAccessException(1);
		}	
	}
}