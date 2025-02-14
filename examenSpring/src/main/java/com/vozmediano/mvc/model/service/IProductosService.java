package com.vozmediano.mvc.model.service;

import java.util.List;

import com.vozmediano.mvc.model.entity.Productos;

public interface IProductosService {
	public List<Productos> findAll();
	public Productos findById(int id);
	public void save(Productos p);
	public void delete(Productos p);
	
}
