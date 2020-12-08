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

	public Cliente atualizarCategoria(Long id, Cliente cliente) {
		if (cr.getOne(id) == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(cliente, cr.getOne(id), "id");
		return cr.save(cr.getOne(id));
	}
}
