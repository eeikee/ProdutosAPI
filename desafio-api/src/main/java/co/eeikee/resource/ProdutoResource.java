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
import co.eeikee.model.Produto;
import co.eeikee.model.ProdutoFornecedorDTO;
import co.eeikee.repository.ProdutoRepository;
import co.eeikee.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Produtos")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoRepository pr;

	@Autowired
	private ProdutoService ps;

	@Autowired
	private ApplicationEventPublisher aep;

	@GetMapping
	@ApiOperation("Listar todos os produtos")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<List<ProdutoFornecedorDTO>> listarProdutos() {
		return !pr.findAll().isEmpty() ? ResponseEntity.ok(ps.getProdutoFornecedor(pr.findAll()))
				: ResponseEntity.notFound().build();
	}

	@PostMapping
	@ApiOperation("Inserir produto")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<ProdutoFornecedorDTO> criar(
			@ApiParam(name = "Corpo", value = "Representação de um novo fornecedor") @Valid @RequestBody Produto produto,
			HttpServletResponse response) {
		ps.validaFornecedor(produto);
		produto.setCodigoProduto();
		pr.save(produto);
		aep.publishEvent(new RecursoCriadoEvent(this, response, produto.getId()));
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ps.convertTProdutoFornecedorDTO(pr.getOne(produto.getId())));
	}

	@ApiOperation("Buscar por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoFornecedorDTO> buscarPeloId(
			@ApiParam(value = "Id de um produto", example = "1") @PathVariable Long id) {
		return ps.convertTProdutoFornecedorDTO(pr.getOne(id)) != null
				? ResponseEntity.ok(ps.convertTProdutoFornecedorDTO(pr.getOne(id)))
				: ResponseEntity.notFound().build();
	}

	@ApiOperation("Exclui produto")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "Id de um produto", example = "1") @PathVariable Long id) {
		pr.deleteById(id);
	}

	@ApiOperation("Atualizar produto")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoFornecedorDTO> atualizar(
			@ApiParam(value = "Id de um produto", example = "1") @PathVariable Long id,
			@ApiParam(name = "Corpo", value = "Representação de um produto") @Validated @RequestBody Produto produto) {
		return ResponseEntity.ok(ps.convertTProdutoFornecedorDTO(ps.atualizarProduto(id, produto)));
	}

	@GetMapping("/asc")

	@ApiOperation("Listar os produtos em ordem alfabética crescente por nome")

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<List<ProdutoFornecedorDTO>> listarCrescenteAlfabetico() {
		return !ps.getProdutoFornecedor(pr.findAllByOrderByNomeAsc()).isEmpty()
				? ResponseEntity.ok(ps.getProdutoFornecedor(pr.findAllByOrderByNomeAsc()))
				: ResponseEntity.notFound().build();
	}

	@GetMapping("/desc")

	@ApiOperation("Lista os produtos em ordem alfabética decrescente por nome")

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<List<ProdutoFornecedorDTO>> listarDecrescenteAlfabetico() {
		return !ps.getProdutoFornecedor(pr.findAllByOrderByNomeDesc()).isEmpty()
				? ResponseEntity.ok(ps.getProdutoFornecedor(pr.findAllByOrderByNomeDesc()))
				: ResponseEntity.notFound().build();
	}

	@ApiOperation("Buscar produto por nome")

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<ProdutoFornecedorDTO>> buscarPeloNome(

			@ApiParam(value = "Nome de um produto", example = "Notebook") @PathVariable String nome) {
		return !ps.getProdutoFornecedor(pr.findByNomeContaining(nome)).isEmpty()
				? ResponseEntity.ok(ps.getProdutoFornecedor(pr.findByNomeContaining(nome)))
				: ResponseEntity.notFound().build();
	}

}
