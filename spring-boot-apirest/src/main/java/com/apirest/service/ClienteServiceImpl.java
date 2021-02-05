package com.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apirest.dao.IClienteDao;
import com.apirest.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService{

	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Cliente> getAll() {
		return (List<Cliente>) this.clienteDao.findAll();
	}

	@Override
	@Transactional
	public Cliente findById(Long id) {
		return this.clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		return this.clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		this.clienteDao.deleteById(id);
	}

}
