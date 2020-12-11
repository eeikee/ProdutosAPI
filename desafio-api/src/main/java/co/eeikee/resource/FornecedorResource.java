package co.eeikee.resource;

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
import co.eeikee.model.Fornecedor;
import co.eeikee.model.FornecedorProdutoDTO;
import co.eeikee.repository.FornecedorRepository;
import co.eeikee.service.FornecedorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@Api(tags = "Fornecedores")
@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorResource {

	@Autowired
	private FornecedorRepository fr;

	@Autowired
	private FornecedorService fs;

	@Autowired
	private ApplicationEventPublisher aep;

	@GetMapping
	@ApiOperation("Listar todos os fornecedores")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<List<FornecedorProdutoDTO>> listarFornecedores() {
		return !fr.findAll().isEmpty() ? ResponseEntity.ok(fs.getFornecedorProduto(fr.findAll()))
				: ResponseEntity.notFound().build();
	}

	@PostMapping
	@ApiOperation("Inserir fornecedor")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<FornecedorProdutoDTO> criar(
			@ApiParam(name = "Corpo", value = "Representação de um novo fornecedor") @Valid @RequestBody Fornecedor fornecedor,
			HttpServletResponse response) {
		fr.save(fornecedor);
		aep.publishEvent(new RecursoCriadoEvent(this, response, fornecedor.getId()));
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(fs.convertToFornecedorProdutoDTO(fr.getOne(fornecedor.getId())));
	}

	@ApiOperation("Buscar por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/{id}")
	public ResponseEntity<FornecedorProdutoDTO> buscarPeloId(
			@ApiParam(value = "Id de um fornecedor", example = "1") @PathVariable Long id) {
		return !fr.findById(id).isEmpty() ? ResponseEntity.ok(fs.convertToFornecedorProdutoDTO(fr.getOne(id)))
				: ResponseEntity.notFound().build();
	}

	@ApiOperation("Exclui fornecedor")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "Id de um fornecedor", example = "1") @PathVariable Long id) {
		fr.deleteById(id);
	}

	@ApiOperation("Atualizar fornecedor")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PutMapping("/{id}")
	public ResponseEntity<FornecedorProdutoDTO> atualizar(
			@ApiParam(value = "Id de um fornecedor", example = "1") @PathVariable Long id,
			@ApiParam(name = "Corpo", value = "Representação de um fornecedor") @Validated @RequestBody Fornecedor fornecedor) {
		return !fr.findById(id).isEmpty()
				? ResponseEntity.ok(fs.convertToFornecedorProdutoDTO(fs.atualizarFornecedor(id, fornecedor)))
				: ResponseEntity.notFound().build();
	}

	@GetMapping("/asc")
	@ApiOperation("Listar os fornecedores em ordem alfabética crescente por nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<List<FornecedorProdutoDTO>> listarCrescenteAlfabetico() {
		return !fs.getFornecedorProduto(fr.findAllByOrderByNomeAsc()).isEmpty()
				? ResponseEntity.ok(fs.getFornecedorProduto(fr.findAllByOrderByNomeAsc()))
				: ResponseEntity.notFound().build();
	}

	@GetMapping("/desc")
	@ApiOperation("Lista os fornecedores em ordem alfabética decrescente por nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<List<FornecedorProdutoDTO>> listarDecrescenteAlfabetico() {
		return !fs.getFornecedorProduto(fr.findAllByOrderByNomeDesc()).isEmpty()
				? ResponseEntity.ok(fs.getFornecedorProduto(fr.findAllByOrderByNomeDesc()))
				: ResponseEntity.notFound().build();
	}

	@ApiOperation("Buscar fornecedor por nome")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<FornecedorProdutoDTO>> buscarPeloNome(
			@ApiParam(value = "Nome de um fornecedores", example = "Empresa") @PathVariable String nome) {
		return !fs.getFornecedorProduto(fr.findByNomeContaining(nome)).isEmpty()
				? ResponseEntity.ok(fs.getFornecedorProduto(fr.findByNomeContaining(nome)))
				: ResponseEntity.notFound().build();
	}
}
