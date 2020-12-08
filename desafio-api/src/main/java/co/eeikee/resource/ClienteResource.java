package co.eeikee.resource;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.eeikee.event.RecursoCriadoEvent;
import co.eeikee.model.Cliente;
import co.eeikee.repository.ClienteRepository;
import co.eeikee.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Clientes")
@RestController
@RequestMapping("/api/clientes")
public class ClienteResource {

	@Autowired
	private ClienteRepository cr;

	@Autowired
	private ClienteService cs;

	@Autowired
	private ApplicationEventPublisher aep;

	@GetMapping
	@ApiOperation("Listar todos os clientes")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public List<Cliente> listarClientes() {
		return cr.findAll();
	}

	@PostMapping
	@ApiOperation("Inserir cliente")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<Cliente> criar(
			@ApiParam(name = "Corpo", value = "Representação de um novo cliente") @Valid @RequestBody Cliente cliente,
			HttpServletResponse response) {
		cliente.setDataCadastro(new Date());
		Cliente clienteSalvo = cr.save(cliente);
		aep.publishEvent(new RecursoCriadoEvent(this, response, cliente.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
	}

	@ApiOperation("Buscar por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarPeloId(
			@ApiParam(value = "Id de um cliente", example = "1") @PathVariable Long id) {
		return !cr.findById(id).isEmpty() ? ResponseEntity.ok(cr.getOne(id)) : ResponseEntity.notFound().build();
	}

	@ApiOperation("Exclui cliente")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "Id de um cliente", example = "1") @PathVariable Long id) {
		cr.deleteById(id);
	}

	@ApiOperation("Atualizar cliente")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizar(@ApiParam(value = "Id de um cliente", example = "1") @PathVariable Long id,
			@ApiParam(name = "Corpo", value = "Representação de um cliente") @Validated @RequestBody Cliente cliente) {
		return ResponseEntity.ok(cs.atualizarCategoria(id, cliente));
	}

	@GetMapping("/asc")
	@ApiOperation("Listar os clientes em ordem alfabética crescente por nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<List<Cliente>> listarCrescenteAlfabetico() {
		return !cr.findAllByOrderByNomeAsc().isEmpty() ? ResponseEntity.ok(cr.findAllByOrderByNomeAsc())
				: ResponseEntity.notFound().build();
	}

	@GetMapping("/desc")
	@ApiOperation("Lista os clientes em ordem alfabética decrescente por nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<List<Cliente>> listarDecrescenteAlfabetico() {
		return !cr.findAllByOrderByNomeDesc().isEmpty() ? ResponseEntity.ok(cr.findAllByOrderByNomeDesc())
				: ResponseEntity.notFound().build();
	}

	@ApiOperation("Buscar clientes por nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Cliente>> buscarPeloNome(
			@ApiParam(value = "Nome de um cliente", example = "fulano") @PathVariable String nome) {
		return !cr.findByNomeContaining(nome).isEmpty() ? ResponseEntity.ok(cr.findByNomeContaining(nome))
				: ResponseEntity.notFound().build();
	}
}
