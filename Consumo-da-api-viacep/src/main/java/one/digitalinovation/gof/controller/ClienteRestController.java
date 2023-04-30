package one.digitalinovation.gof.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import one.digitalinovation.gof.entity.Cliente;
import one.digitalinovation.gof.service.ClienteService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ClienteRestController {

	private final ClienteService clienteService;
	
	@PostMapping("/post")
	public ResponseEntity<Cliente> save(@RequestBody Cliente cliente){
		
		return new ResponseEntity<>(clienteService.save(cliente), HttpStatus.CREATED);
	}
	
	/*@PutMapping("/put")
	public ResponseEntity<Cliente> update(@RequestBody Cliente cliente){
	}*/
	
	
	@GetMapping("/{cep}")
	public ResponseEntity<Cliente> getCep(@PathVariable String cep) {

		return new ResponseEntity<>(clienteService.getCep(cep), HttpStatus.OK);
	}
	
}