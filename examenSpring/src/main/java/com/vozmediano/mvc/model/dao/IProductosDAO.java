package com.vozmediano.mvc.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.vozmediano.mvc.model.entity.Productos;


public interface IProductosDAO extends CrudRepository<Productos, Integer> {

}
