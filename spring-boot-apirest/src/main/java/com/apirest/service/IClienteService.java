package com.apirest.service;

import java.util.List;

import com.apirest.entity.Cliente;

public interface IClienteService {

	List<Cliente> getAll();
	
	Cliente findById(Long id);
	
	Cliente save(Cliente cliente);
	
	void deleteById(Long id);
}
