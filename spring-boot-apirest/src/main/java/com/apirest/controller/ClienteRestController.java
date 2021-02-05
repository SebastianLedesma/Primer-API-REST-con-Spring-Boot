package com.apirest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.entity.Cliente;
import com.apirest.service.IClienteService;

@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/clientes")
	public List<Cliente> showIndex(){
		return this.clienteService.getAll();
	}
	
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<Cliente> showById(@PathVariable Long id){
		Cliente clienteEncontrado = this.clienteService.findById(id);
		
		if(clienteEncontrado != null) {
			return  ResponseEntity.ok(clienteEncontrado);
		}else {
			return ResponseEntity.badRequest().build();
		}
		
	}
	
	
	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente create(@RequestBody Cliente cliente) {
		return this.clienteService.save(cliente);
	}
	
	@PutMapping("/clientes/{id}")
	public ResponseEntity<Cliente> update(@RequestBody Cliente cliente,@PathVariable Long id) {
		Cliente clienteEncontrado = this.clienteService.findById(id);
		
		if(clienteEncontrado != null) {
			clienteEncontrado.setNombre(cliente.getNombre());
			clienteEncontrado.setApellido(cliente.getApellido());
			clienteEncontrado.setEmail(cliente.getEmail());
			
			return ResponseEntity.ok(this.clienteService.save(clienteEncontrado));
		}else {
			return ResponseEntity.badRequest().build();
		}
		
	}
	
	
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		Cliente clienteEncontrado = this.clienteService.findById(id);
		
		if(clienteEncontrado != null) {
			this.clienteService.deleteById(id);
			return ResponseEntity.ok(null);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}
