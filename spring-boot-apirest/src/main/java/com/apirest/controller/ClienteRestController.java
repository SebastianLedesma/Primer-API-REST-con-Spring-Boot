package com.apirest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
	public ResponseEntity<?> showById(@PathVariable Long id){
		Cliente clienteEncontrado = null;
		Map<String,Object> response = new HashMap<String,Object>();
		
		try {
			clienteEncontrado = this.clienteService.findById(id);
		}catch(DataAccessException e) {
			response.put("Error", "Error al realizar la consulta en la base de datos.");
			response.put("Mensaje", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(clienteEncontrado == null) {
			response.put("mensaje", "El cliente con ID ".concat(id.toString()).concat(" no existe."));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Cliente>(clienteEncontrado,HttpStatus.OK);
	}
	
	
	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {
		Map<String,Object> response = new HashMap<String,Object>();
		Cliente nuevoCliente = null;
		
		if(result.hasErrors()) {
			List<String> errors = new ArrayList<String>();
			for(FieldError err : result.getFieldErrors()) {
				errors.add("El campo '".concat(err.getField().concat("' ").concat(err.getDefaultMessage())));
			}
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		try {
			nuevoCliente = this.clienteService.save(cliente);
		}catch(DataAccessException e) {
			response.put("Error", "Error al realizar la inserción en la base de datos.");
			response.put("Mensaje", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido creado con éxito.");
		response.put("cliente", nuevoCliente);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	
	
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente,BindingResult result,@PathVariable Long id) {
		Cliente clienteEncontrado = this.clienteService.findById(id);
		Cliente clienteActualizado = null;
		Map<String,Object> response = new HashMap<String,Object>();
		
		if(result.hasErrors()) {
			List<String> errors = new ArrayList<String>();
			for(FieldError err : result.getFieldErrors()) {
				errors.add("El campo '".concat(err.getField().concat("' ").concat(err.getDefaultMessage())));
			}
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		if(clienteEncontrado == null) {
			response.put("error", "El cliente con el ID ".concat(id.toString()).concat(" no existe en la base de de datos."));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			clienteEncontrado.setNombre(cliente.getNombre());
			clienteEncontrado.setApellido(cliente.getNombre());
			clienteEncontrado.setEmail(cliente.getEmail());
			clienteEncontrado.setCreateAt(cliente.getCreateAt());
			clienteActualizado = this.clienteService.save(clienteEncontrado);
		}catch(DataAccessException e) {
			response.put("Error", "Error al actualizar el cliente en la base de datos.");
			response.put("Mensaje", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente se ha actualizado con éxito.");
		response.put("cliente", clienteActualizado);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		
	}
	
	
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Cliente cliente = null;
		Map<String,Object> response = new HashMap<String,Object>();
		
		try {
			cliente = this.clienteService.findById(id);
		}catch(DataAccessException e) {
			response.put("Error", "Error al eliminar al cliente de la base de datos.");
			response.put("Mensaje", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Se ha eliminado el cliente con éxito.");
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
}
