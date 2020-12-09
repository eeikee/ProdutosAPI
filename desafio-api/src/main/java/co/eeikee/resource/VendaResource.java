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
import co.eeikee.model.Venda;
import co.eeikee.model.VendaDTO;
import co.eeikee.repository.VendaRepository;
import co.eeikee.service.VendaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Vendas")
@RestController
@RequestMapping("/api/vendas")
public class VendaResource {

	@Autowired
	private VendaRepository vr;

	@Autowired
	private VendaService vs;

	@Autowired
	private ApplicationEventPublisher aep;

	@GetMapping
	@ApiOperation("Listar todas as vendas")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public List<VendaDTO> listarProdutos() {
		return vs.getVendas(vr.findAll());
	}

	@PostMapping
	@ApiOperation("Inserir venda")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<VendaDTO> criar(
			@ApiParam(name = "Corpo", value = "Representação de uma nova venda") @Valid @RequestBody Venda venda,
			HttpServletResponse response) {
		vs.quantidadeProduto(venda);
		vr.save(venda);
		aep.publishEvent(new RecursoCriadoEvent(this, response, venda.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(vs.convertTVendaDTO(vr.getOne(venda.getId())));
	}

	@ApiOperation("Buscar por ID")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/{id}")
	public ResponseEntity<VendaDTO> buscarPeloId(
			@ApiParam(value = "Id de uma venda", example = "1") @PathVariable Long id) {
		return vr.getOne(id) != null ? ResponseEntity.ok(vs.convertTVendaDTO(vr.getOne(id))) : ResponseEntity.notFound().build();
	}

	@ApiOperation("Exclui venda")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "Id de uma venda", example = "1") @PathVariable Long id) {
		vr.deleteById(id);
	}

	@ApiOperation("Atualizar venda")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PutMapping("/{id}")
	public ResponseEntity<VendaDTO> atualizar(@ApiParam(value = "Id de uma venda", example = "1") @PathVariable Long id,
			@ApiParam(name = "Corpo", value = "Representação de uma venda") @Validated @RequestBody Venda venda) {
		return !vr.findById(id).isEmpty() ? ResponseEntity.ok(vs.convertTVendaDTO(vs.atualizarProduto(id, venda))) : ResponseEntity.notFound().build();
	}

	@GetMapping("/asc")

	@ApiOperation("Listar as vendas em ordem alfabética crescente por nome do cliente")

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<List<VendaDTO>> listarCrescenteAlfabetico() {
		return !vr.findAllByOrderByDataCompraAsc().isEmpty() ? ResponseEntity.ok(vs.getVendas(vr.findAllByOrderByDataCompraAsc()))
				: ResponseEntity.notFound().build();
	}

	@GetMapping("/desc")

	@ApiOperation("Lista as vendas em ordem alfabética decrescente por nome do cliente")

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<List<VendaDTO>> listarDecrescenteAlfabetico() {
		return !vr.findAllByOrderByDataCompraDesc().isEmpty() ? ResponseEntity.ok(vs.getVendas(vr.findAllByOrderByDataCompraDesc()))
				: ResponseEntity.notFound().build();
	}

	@ApiOperation("Buscar vendas por nome")

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")

	@GetMapping("/data/{dia}/{mes}/{ano}")
	public ResponseEntity<List<VendaDTO>> buscarPeloNome(
			@ApiParam(value = "Dia da venda", example = "01") @PathVariable String dia, @ApiParam(value = "Mês da venda", example = "01")@PathVariable String mes, @ApiParam(value = "Ano da venda", example = "2020") @PathVariable String ano) {   
		return !vr.findByDataCompra(vs.dataCompra(dia,mes,ano)).isEmpty() ? ResponseEntity.ok(vs.getVendas(vr.findByDataCompra(vs.dataCompra(dia, mes, ano))))
				: ResponseEntity.notFound().build();
	}

}
