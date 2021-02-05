package com.apirest.dao;

import org.springframework.data.repository.CrudRepository;

import com.apirest.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente,Long>{

}
